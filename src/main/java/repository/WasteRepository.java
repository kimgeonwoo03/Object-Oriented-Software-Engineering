package repository;

import entity.WasteDischarge;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WasteRepository {

    public int insertWaste(WasteDischarge waste) {
        String sql = "INSERT INTO waste_discharge (wasteId, labId, dischargeDate, wasteType, wasteState, quantity, unit, storageLocation, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, waste.getWasteId());
            pstmt.setString(2, waste.getLabId());
            pstmt.setString(3, waste.getDischargeDate());
            pstmt.setString(4, waste.getWasteType());
            pstmt.setString(5, waste.getWasteState());
            pstmt.setDouble(6, waste.getQuantity());
            pstmt.setString(7, waste.getUnit());
            pstmt.setString(8, waste.getStorageLocation());
            pstmt.setString(9, waste.getStatus());

            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("폐기물 정보 저장 실패", e);
        }
    }

    public List<WasteDischarge> findAllWastes() {
        String sql = "SELECT * FROM waste_discharge";
        List<WasteDischarge> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToWaste(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("폐기물 전체 목록 조회 실패", e);
        }
        return list;
    }

    public WasteDischarge findWasteById(String wasteId) {
        String sql = "SELECT * FROM waste_discharge WHERE wasteId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, wasteId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToWaste(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("폐기물 상세 조회 실패", e);
        }
        return null;
    }

    /**
     * 항목별 검색 조건으로 폐기물 목록을 조회한다.
     * 각 파라미터가 null이거나 빈 값이면 해당 항목은 검색 조건에서 제외하는 동적 쿼리 사용
     */
    public List<WasteDischarge> findWastesByFields(
            String wasteId,
            String labId,
            String dischargeDate,
            String wasteType,
            String wasteState,
            String status
    ) {
        StringBuilder sql = new StringBuilder("SELECT * FROM waste_discharge WHERE 1=1");
        List<String> params = new ArrayList<>();

        if (wasteId != null && !wasteId.trim().isEmpty()) {
            sql.append(" AND LOWER(wasteId) LIKE ?");
            params.add("%" + wasteId.trim().toLowerCase() + "%");
        }
        if (labId != null && !labId.trim().isEmpty()) {
            sql.append(" AND LOWER(labId) LIKE ?");
            params.add("%" + labId.trim().toLowerCase() + "%");
        }
        if (dischargeDate != null && !dischargeDate.trim().isEmpty()) {
            sql.append(" AND LOWER(dischargeDate) LIKE ?");
            params.add("%" + dischargeDate.trim().toLowerCase() + "%");
        }
        if (wasteType != null && !wasteType.trim().isEmpty()) {
            sql.append(" AND LOWER(wasteType) LIKE ?");
            params.add("%" + wasteType.trim().toLowerCase() + "%");
        }
        if (wasteState != null && !wasteState.trim().isEmpty()) {
            sql.append(" AND LOWER(wasteState) LIKE ?");
            params.add("%" + wasteState.trim().toLowerCase() + "%");
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND LOWER(status) LIKE ?");
            params.add("%" + status.trim().toLowerCase() + "%");
        }

        List<WasteDischarge> resultList = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            // List에 담아둔 파라미터들을 순서대로 PreparedStatement에 세팅
            for (int i = 0; i < params.size(); i++) {
                pstmt.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    resultList.add(mapResultSetToWaste(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("폐기물 조건 검색 실패", e);
        }
        return resultList;
    }

    public boolean existsWasteById(String wasteId) {
        String sql = "SELECT 1 FROM waste_discharge WHERE wasteId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, wasteId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("폐기물 ID 중복 검사 실패", e);
        }
    }

    // 중복 방지를 위한 공통 매핑 메서드 (ResultSet -> WasteDischarge 객체)
    private WasteDischarge mapResultSetToWaste(ResultSet rs) throws SQLException {
        WasteDischarge waste = new WasteDischarge();
        waste.setWasteId(rs.getString("wasteId"));
        waste.setLabId(rs.getString("labId"));
        waste.setDischargeDate(rs.getString("dischargeDate"));
        waste.setWasteType(rs.getString("wasteType"));
        waste.setWasteState(rs.getString("wasteState"));
        waste.setQuantity(rs.getDouble("quantity"));
        waste.setUnit(rs.getString("unit"));
        waste.setStorageLocation(rs.getString("storageLocation"));
        waste.setStatus(rs.getString("status"));
        return waste;
    }
}