package repository;

import entity.SafetyJournal;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SafetyJournalRepository {

    public boolean insert(SafetyJournal journal) {
        String sql = "INSERT INTO safety_journal (journalId, labId, educationDate, content, attendeeCount, authorId) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, journal.getJournalId());
            pstmt.setString(2, journal.getLabId());
            pstmt.setString(3, journal.getEducationDate());
            pstmt.setString(4, journal.getContent());
            pstmt.setInt(5, journal.getAttendeeCount());
            pstmt.setString(6, journal.getAuthorId());

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("안전교육일지 저장 실패", e);
        }
    }

    public List<SafetyJournal> findByCondition(String startDate, String endDate, String labId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM safety_journal WHERE 1=1");

        boolean hasLabId = labId != null && !labId.trim().isEmpty();
        boolean hasStartDate = startDate != null && !startDate.trim().isEmpty();
        boolean hasEndDate = endDate != null && !endDate.trim().isEmpty();

        if (hasLabId) sql.append(" AND labId = ?");
        if (hasStartDate) sql.append(" AND educationDate >= ?");
        if (hasEndDate) sql.append(" AND educationDate <= ?");

        List<SafetyJournal> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (hasLabId) pstmt.setString(paramIndex++, labId);
            if (hasStartDate) pstmt.setString(paramIndex++, startDate);
            if (hasEndDate) pstmt.setString(paramIndex, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToJournal(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("안전교육일지 검색 실패", e);
        }
        return list;
    }

    public SafetyJournal findById(String journalId) {
        String sql = "SELECT * FROM safety_journal WHERE journalId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, journalId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToJournal(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("안전교육일지 조회 실패", e);
        }
        return null;
    }

    public boolean existsByDateAndLab(String educationDate, String labId) {
        String sql = "SELECT 1 FROM safety_journal WHERE educationDate = ? AND labId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, educationDate);
            pstmt.setString(2, labId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("일지 중복 검사 실패", e);
        }
    }

    private SafetyJournal mapResultSetToJournal(ResultSet rs) throws SQLException {
        SafetyJournal journal = new SafetyJournal();
        journal.setJournalId(rs.getString("journalId"));
        journal.setLabId(rs.getString("labId"));
        journal.setEducationDate(rs.getString("educationDate"));
        journal.setContent(rs.getString("content"));
        journal.setAttendeeCount(rs.getInt("attendeeCount"));
        journal.setAuthorId(rs.getString("authorId"));
        return journal;
    }
}