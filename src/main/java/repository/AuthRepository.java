package repository;

import entity.User;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthRepository {

    public User findUserById(String userId) {
        String sql = "SELECT * FROM users WHERE userId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getString("userId"));
                    user.setPassword(rs.getString("password"));
                    user.setUserName(rs.getString("userName"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("사용자 조회 실패", e);
        }
        return null;
    }
}