package repository;

import entity.WasteDischarge;
import util.JsonFileUtil;

import java.util.ArrayList;
import java.util.List;

public class WasteRepository {

    private static final String WASTE_FILE = "waste_discharge.json";

    public int insertWaste(WasteDischarge waste) {
        List<WasteDischarge> wasteList = JsonFileUtil.readList(WASTE_FILE, WasteDischarge.class);

        wasteList.add(waste);

        JsonFileUtil.writeList(WASTE_FILE, wasteList);

        return 1;
    }

    public List<WasteDischarge> findAllWastes() {
        return JsonFileUtil.readList(WASTE_FILE, WasteDischarge.class);
    }

    public WasteDischarge findWasteById(String wasteId) {
        List<WasteDischarge> wasteList = JsonFileUtil.readList(WASTE_FILE, WasteDischarge.class);

        for (WasteDischarge waste : wasteList) {
            if (waste.getWasteId().equals(wasteId)) {
                return waste;
            }
        }

        return null;
    }

    /**
     * 항목별 검색 조건으로 폐기물 목록을 조회한다.
     * 각 파라미터가 null이거나 빈 값이면 해당 항목은 검색 조건에서 제외한다.
     */
    public List<WasteDischarge> findWastesByFields(
            String wasteId,
            String labId,
            String dischargeDate,
            String wasteType,
            String wasteState,
            String status
    ) {
        List<WasteDischarge> wasteList = JsonFileUtil.readList(WASTE_FILE, WasteDischarge.class);
        List<WasteDischarge> resultList = new ArrayList<>();

        for (WasteDischarge waste : wasteList) {
            if (!matches(waste.getWasteId(), wasteId)) continue;
            if (!matches(waste.getLabId(), labId)) continue;
            if (!matches(waste.getDischargeDate(), dischargeDate)) continue;
            if (!matches(waste.getWasteType(), wasteType)) continue;
            if (!matches(waste.getWasteState(), wasteState)) continue;
            if (!matches(waste.getStatus(), status)) continue;

            resultList.add(waste);
        }

        return resultList;
    }

    private boolean matches(String fieldValue, String condition) {
        if (condition == null || condition.trim().isEmpty()) {
            return true;
        }

        if (fieldValue == null) {
            return false;
        }

        return fieldValue.toLowerCase().contains(condition.trim().toLowerCase());
    }

    public boolean existsWasteById(String wasteId) {
        return findWasteById(wasteId) != null;
    }
}