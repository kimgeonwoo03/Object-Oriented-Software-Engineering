package repository;

import entity.Group;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupRepository {

    public int insertGroup(Group group) {
        String sql = "INSERT INTO user_group (groupId, groupName, managerId, description, createdDate, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, group.getGroupId());
            pstmt.setString(2, group.getGroupName());
            pstmt.setString(3, group.getManagerId());
            pstmt.setString(4, group.getDescription());
            pstmt.setString(5, group.getCreatedDate());
            pstmt.setString(6, group.getStatus());

            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("그룹 저장 실패", e);
        }
    }

    public List<Group> findAllGroups() {
        String sql = "SELECT * FROM user_group";
        List<Group> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToGroup(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("그룹 전체 조회 실패", e);
        }
        return list;
    }

    public Group findGroupById(String groupId) {
        String sql = "SELECT * FROM user_group WHERE groupId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, groupId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToGroup(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("그룹 상세 조회 실패", e);
        }
        return null;
    }

    public List<Group> findGroupsByCondition(String condition) {
        if (condition == null || condition.trim().isEmpty()) {
            return findAllGroups();
        }

        String sql = "SELECT * FROM user_group WHERE LOWER(groupId) LIKE ? OR LOWER(groupName) LIKE ? OR LOWER(managerId) LIKE ?";
        String keyword = "%" + condition.trim().toLowerCase() + "%";
        List<Group> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, keyword);
            pstmt.setString(2, keyword);
            pstmt.setString(3, keyword);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToGroup(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("그룹 조건 조회 실패", e);
        }
        return list;
    }

    public boolean existsGroupById(String groupId) {
        String sql = "SELECT 1 FROM user_group WHERE groupId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, groupId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("그룹 ID 중복 검사 실패", e);
        }
    }

    private Group mapResultSetToGroup(ResultSet rs) throws SQLException {
        Group group = new Group();
        group.setGroupId(rs.getString("groupId"));
        group.setGroupName(rs.getString("groupName"));
        group.setManagerId(rs.getString("managerId"));
        group.setDescription(rs.getString("description"));
        group.setCreatedDate(rs.getString("createdDate"));
        group.setStatus(rs.getString("status"));
        return group;
    }
}