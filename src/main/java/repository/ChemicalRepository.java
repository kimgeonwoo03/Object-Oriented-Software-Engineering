package repository;

import entity.Chemical;
import util.JsonFileUtil;

import java.util.ArrayList;
import java.util.List;

public class ChemicalRepository {

    private static final String CHEMICAL_FILE = "chemical.json";

    public int insertChemical(Chemical chemical) {
        List<Chemical> chemicalList = JsonFileUtil.readList(CHEMICAL_FILE, Chemical.class);

        chemicalList.add(chemical);

        JsonFileUtil.writeList(CHEMICAL_FILE, chemicalList);

        return 1;
    }

    public List<Chemical> findAllChemicals() {
        return JsonFileUtil.readList(CHEMICAL_FILE, Chemical.class);
    }

    public Chemical findChemicalById(String chemicalId) {
        List<Chemical> chemicalList = JsonFileUtil.readList(CHEMICAL_FILE, Chemical.class);

        for (Chemical chemical : chemicalList) {
            if (chemical.getChemicalId().equals(chemicalId)) {
                return chemical;
            }
        }

        return null;
    }

    public List<Chemical> findChemicalsByCondition(String condition) {
        List<Chemical> chemicalList = JsonFileUtil.readList(CHEMICAL_FILE, Chemical.class);
        List<Chemical> resultList = new ArrayList<>();

        if (condition == null || condition.trim().isEmpty()) {
            return chemicalList;
        }

        String keyword = condition.trim().toLowerCase();

        for (Chemical chemical : chemicalList) {
            String productName = chemical.getProductName() == null ? "" : chemical.getProductName().toLowerCase();
            String casNo = chemical.getCasNo() == null ? "" : chemical.getCasNo().toLowerCase();
            String manufacturer = chemical.getManufacturer() == null ? "" : chemical.getManufacturer().toLowerCase();

            if (productName.contains(keyword)
                    || casNo.contains(keyword)
                    || manufacturer.contains(keyword)) {
                resultList.add(chemical);
            }
        }

        return resultList;
    }

    public boolean existsChemicalById(String chemicalId) {
        return findChemicalById(chemicalId) != null;
    }

    public boolean existsChemicalByCasNo(String casNo) {
        List<Chemical> chemicalList = JsonFileUtil.readList(CHEMICAL_FILE, Chemical.class);

        for (Chemical chemical : chemicalList) {
            if (chemical.getCasNo() != null && chemical.getCasNo().equals(casNo)) {
                return true;
            }
        }

        return false;
    }
}