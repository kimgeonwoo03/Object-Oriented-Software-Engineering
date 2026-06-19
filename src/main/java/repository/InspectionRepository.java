package repository;

import entity.InspSector;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InspectionRepository {

    public int insertInspSector(InspSector inspSector) {
        String sql = "INSERT INTO inspection_sector (sectorId, sectorName, sectorDesc, useYn, regDate) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, inspSector.getSectorId());
            pstmt.setString(2, inspSector.getSectorName());
            pstmt.setString(3, inspSector.getSectorDesc());
            pstmt.setInt(4, inspSector.getUseYn());
            pstmt.setString(5, inspSector.getRegDate());

            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("점검 분야 저장 실패", e);
        }
    }

    public List<InspSector> findAllInspSectors(String filter) {
        String sql = "SELECT * FROM inspection_sector";
        boolean hasFilter = filter != null && !filter.trim().isEmpty();

        if (hasFilter) {
            sql += " WHERE useYn = ?";
        }

        List<InspSector> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (hasFilter) {
                pstmt.setInt(1, Integer.parseInt(filter));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToInspSector(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("점검 분야 목록 조회 실패", e);
        }
        return list;
    }

    public InspSector findInspSectorById(String sectorId) {
        String sql = "SELECT * FROM inspection_sector WHERE sectorId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sectorId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToInspSector(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("점검 분야 조회 실패", e);
        }
        return null;
    }

    public boolean existsInspSectorName(String sectorName) {
        String sql = "SELECT 1 FROM inspection_sector WHERE sectorName = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sectorName);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("점검 분야명 중복 검사 실패", e);
        }
    }

    public boolean existsInspSectorById(String sectorId) {
        String sql = "SELECT 1 FROM inspection_sector WHERE sectorId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sectorId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("점검 분야 ID 중복 검사 실패", e);
        }
    }

    private InspSector mapResultSetToInspSector(ResultSet rs) throws SQLException {
        InspSector sector = new InspSector();
        sector.setSectorId(rs.getString("sectorId"));
        sector.setSectorName(rs.getString("sectorName"));
        sector.setSectorDesc(rs.getString("sectorDesc"));
        sector.setUseYn(rs.getInt("useYn"));
        sector.setRegDate(rs.getString("regDate"));
        return sector;
    }
}