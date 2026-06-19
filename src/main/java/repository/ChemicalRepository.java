package repository;

import entity.Chemical;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChemicalRepository {

    public int insertChemical(Chemical chemical) {
        String sql = "INSERT INTO chemical (chemicalId, productName, manufacturer, catNo, casNo, capacity, unit, componentInfo, legalRegulation, ghsLabel, physicalProperties, hazardInfo, isComplex, registeredDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, chemical.getChemicalId());
            pstmt.setString(2, chemical.getProductName());
            pstmt.setString(3, chemical.getManufacturer());
            pstmt.setString(4, chemical.getCatNo());
            pstmt.setString(5, chemical.getCasNo());
            pstmt.setString(6, chemical.getCapacity());
            pstmt.setString(7, chemical.getUnit());
            pstmt.setString(8, chemical.getComponentInfo());
            pstmt.setString(9, chemical.getLegalRegulation());
            pstmt.setString(10, chemical.getGhsLabel());
            pstmt.setString(11, chemical.getPhysicalProperties());
            pstmt.setString(12, chemical.getHazardInfo());
            pstmt.setInt(13, chemical.getIsComplex());
            pstmt.setString(14, chemical.getRegisteredDate());

            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DB 저장 실패: " + e.getMessage());
        }
    }

    public List<Chemical> findAllChemicals() {
        String sql = "SELECT * FROM chemical";
        List<Chemical> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToChemical(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("전체 목록 조회 실패: " + e.getMessage());
        }
        return list;
    }

    public Chemical findChemicalById(String chemicalId) {
        String sql = "SELECT * FROM chemical WHERE chemicalId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, chemicalId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToChemical(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ID로 조회 실패: " + e.getMessage());
        }
        return null;
    }

    public List<Chemical> findChemicalsByCondition(String condition) {
        if (condition == null || condition.trim().isEmpty()) {
            return findAllChemicals(); // 조건이 없으면 전체 반환
        }

        List<Chemical> list = new ArrayList<>();
        // 제품명, CAS No, 제조사 중 하나라도 키워드가 포함되어 있는지(LIKE) 검색
        String sql = "SELECT * FROM chemical WHERE LOWER(productName) LIKE ? OR LOWER(casNo) LIKE ? OR LOWER(manufacturer) LIKE ?";
        String keyword = "%" + condition.trim().toLowerCase() + "%";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, keyword);
            pstmt.setString(2, keyword);
            pstmt.setString(3, keyword);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToChemical(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("조건 검색 실패: " + e.getMessage());
        }
        return list;
    }

    public boolean existsChemicalById(String chemicalId) {
        String sql = "SELECT 1 FROM chemical WHERE chemicalId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, chemicalId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // 결과가 존재하면 true 반환
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ID 중복 검사 실패: " + e.getMessage());
        }
    }

    public boolean existsChemicalByCasNo(String casNo) {
        String sql = "SELECT 1 FROM chemical WHERE casNo = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, casNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // 결과가 존재하면 true 반환
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("CAS No 중복 검사 실패: " + e.getMessage());
        }
    }

    // 중복 방지를 위한 공통 매핑 메서드 (ResultSet -> Chemical 객체)
    private Chemical mapResultSetToChemical(ResultSet rs) throws SQLException {
        Chemical chemical = new Chemical();
        chemical.setChemicalId(rs.getString("chemicalId"));
        chemical.setProductName(rs.getString("productName"));
        chemical.setManufacturer(rs.getString("manufacturer"));
        chemical.setCatNo(rs.getString("catNo"));
        chemical.setCasNo(rs.getString("casNo"));
        chemical.setCapacity(rs.getString("capacity"));
        chemical.setUnit(rs.getString("unit"));
        chemical.setComponentInfo(rs.getString("componentInfo"));
        chemical.setLegalRegulation(rs.getString("legalRegulation"));
        chemical.setGhsLabel(rs.getString("ghsLabel"));
        chemical.setPhysicalProperties(rs.getString("physicalProperties"));
        chemical.setHazardInfo(rs.getString("hazardInfo"));
        chemical.setIsComplex(rs.getInt("isComplex"));
        chemical.setRegisteredDate(rs.getString("registeredDate"));
        return chemical;
    }
}