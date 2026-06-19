package repository;

import entity.DrawingInfo;
import entity.LabBasicInfo;
import entity.LabLocationInfo;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LabRepository {

    public int insertLab(LabBasicInfo lab) {
        String sql = "INSERT INTO laboratory (labId, labName, department, building, floor, roomNo, managerId, accessUserInfo, drawingRegistered) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            setLabParameters(pstmt, lab);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("연구실 저장 실패", e);
        }
    }

    public int updateLab(LabBasicInfo lab) {
        String sql = "UPDATE laboratory SET labName = ?, department = ?, building = ?, floor = ?, roomNo = ?, managerId = ?, accessUserInfo = ?, drawingRegistered = ? WHERE labId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, lab.getLabName());
            pstmt.setString(2, lab.getDepartment());
            pstmt.setString(3, lab.getBuilding());
            pstmt.setString(4, lab.getFloor());
            pstmt.setString(5, lab.getRoomNo());
            pstmt.setString(6, lab.getManagerId());
            pstmt.setString(7, lab.getAccessUserInfo());
            pstmt.setInt(8, lab.getDrawingRegistered());
            pstmt.setString(9, lab.getLabId());

            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("연구실 수정 실패", e);
        }
    }

    public List<LabBasicInfo> findAllLabs() {
        String sql = "SELECT * FROM laboratory";
        List<LabBasicInfo> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToLab(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("연구실 목록 조회 실패", e);
        }
        return list;
    }

    public List<LabBasicInfo> findLabsByCondition(String condition, String drawingRegistered) {
        StringBuilder sql = new StringBuilder("SELECT * FROM laboratory WHERE 1=1");
        boolean hasCondition = condition != null && !condition.trim().isEmpty();
        boolean hasDrawing = drawingRegistered != null && !drawingRegistered.trim().isEmpty();

        if (hasCondition) {
            sql.append(" AND (LOWER(labId) LIKE ? OR LOWER(labName) LIKE ? OR LOWER(department) LIKE ? OR LOWER(managerId) LIKE ? OR LOWER(building) LIKE ? OR LOWER(floor) LIKE ? OR LOWER(roomNo) LIKE ?)");
        }
        if (hasDrawing) {
            sql.append(" AND drawingRegistered = ?");
        }

        List<LabBasicInfo> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (hasCondition) {
                String keyword = "%" + condition.trim().toLowerCase() + "%";
                for (int i = 0; i < 7; i++) {
                    pstmt.setString(paramIndex++, keyword);
                }
            }
            if (hasDrawing) {
                pstmt.setInt(paramIndex, Integer.parseInt(drawingRegistered));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToLab(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("연구실 조건 검색 실패", e);
        }
        return list;
    }

    public LabBasicInfo findLabById(String labId) {
        String sql = "SELECT * FROM laboratory WHERE labId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, labId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToLab(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("연구실 조회 실패", e);
        }
        return null;
    }

    public boolean existsLabById(String labId) {
        return findLabById(labId) != null;
    }

    public boolean existsSameLabName(String labName) {
        String sql = "SELECT 1 FROM laboratory WHERE labName = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, labName);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("연구실명 중복 검사 실패", e);
        }
    }

    public boolean existsSameLocation(String building, String floor, String roomNo) {
        String sql = "SELECT 1 FROM laboratory WHERE building = ? AND floor = ? AND roomNo = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, building);
            pstmt.setString(2, floor);
            pstmt.setString(3, roomNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("연구실 위치 중복 검사 실패", e);
        }
    }

    // DrawingInfo 로직
    public int insertDrawingInfo(DrawingInfo drawingInfo) {
        String sql = "INSERT INTO drawing_info (drawingId, labId) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, drawingInfo.getDrawingId());
            pstmt.setString(2, drawingInfo.getLabId());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("도면 정보 저장 실패", e);
        }
    }

    public DrawingInfo findDrawingInfoByLabId(String labId) {
        String sql = "SELECT * FROM drawing_info WHERE labId = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, labId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    DrawingInfo info = new DrawingInfo();
                    info.setDrawingId(rs.getString("drawingId"));
                    info.setLabId(rs.getString("labId"));
                    return info;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("도면 정보 조회 실패", e);
        }
        return null;
    }

    public DrawingInfo findDrawingInfoById(String drawingId) {
        String sql = "SELECT * FROM drawing_info WHERE drawingId = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, drawingId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    DrawingInfo info = new DrawingInfo();
                    info.setDrawingId(rs.getString("drawingId"));
                    info.setLabId(rs.getString("labId"));
                    return info;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("도면 정보 조회 실패", e);
        }
        return null;
    }

    public boolean existsDrawingById(String drawingId) {
        return findDrawingInfoById(drawingId) != null;
    }

    // LabLocationInfo 로직
    public int insertLabLocationInfo(LabLocationInfo locationInfo) {
        String sql = "INSERT INTO lab_location_info (locationId, labId) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, locationInfo.getLocationId());
            pstmt.setString(2, locationInfo.getLabId());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("위치 정보 저장 실패", e);
        }
    }

    public LabLocationInfo findLabLocationInfoByLabId(String labId) {
        String sql = "SELECT * FROM lab_location_info WHERE labId = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, labId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    LabLocationInfo info = new LabLocationInfo();
                    info.setLocationId(rs.getString("locationId"));
                    info.setLabId(rs.getString("labId"));
                    return info;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("위치 정보 조회 실패", e);
        }
        return null;
    }

    public boolean existsLocationById(String locationId) {
        String sql = "SELECT 1 FROM lab_location_info WHERE locationId = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, locationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("위치 ID 중복 검사 실패", e);
        }
    }

    private void setLabParameters(PreparedStatement pstmt, LabBasicInfo lab) throws SQLException {
        pstmt.setString(1, lab.getLabId());
        pstmt.setString(2, lab.getLabName());
        pstmt.setString(3, lab.getDepartment());
        pstmt.setString(4, lab.getBuilding());
        pstmt.setString(5, lab.getFloor());
        pstmt.setString(6, lab.getRoomNo());
        pstmt.setString(7, lab.getManagerId());
        pstmt.setString(8, lab.getAccessUserInfo());
        pstmt.setInt(9, lab.getDrawingRegistered());
    }

    private LabBasicInfo mapResultSetToLab(ResultSet rs) throws SQLException {
        LabBasicInfo lab = new LabBasicInfo();
        lab.setLabId(rs.getString("labId"));
        lab.setLabName(rs.getString("labName"));
        lab.setDepartment(rs.getString("department"));
        lab.setBuilding(rs.getString("building"));
        lab.setFloor(rs.getString("floor"));
        lab.setRoomNo(rs.getString("roomNo"));
        lab.setManagerId(rs.getString("managerId"));
        lab.setAccessUserInfo(rs.getString("accessUserInfo"));
        lab.setDrawingRegistered(rs.getInt("drawingRegistered"));
        return lab;
    }
}