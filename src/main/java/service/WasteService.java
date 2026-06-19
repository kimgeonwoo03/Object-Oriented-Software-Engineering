package service;

import entity.WasteDischarge;
import exception.DuplicateDataException;
import exception.EmptyFieldException;
import exception.NotFoundException;
import repository.WasteRepository;
import util.ValidationUtil;

import java.util.List;

public class WasteService {

    private WasteRepository wasteRepository = new WasteRepository();

    public boolean createWasteDischarge(WasteDischarge waste) {
        validateWaste(waste);
        checkDuplicateWaste(waste.getWasteId());

        int result = wasteRepository.insertWaste(waste);

        return result > 0;
    }

    public List<WasteDischarge> readWasteDischargeList(
            String wasteId,
            String labId,
            String dischargeDate,
            String wasteType,
            String wasteState,
            String status
    ) {
        return wasteRepository.findWastesByFields(
                wasteId, labId, dischargeDate, wasteType, wasteState, status
        );
    }

    public WasteDischarge readWasteDischarge(String wasteId) {
        ValidationUtil.require(wasteId, "폐기물 ID");

        WasteDischarge waste = wasteRepository.findWasteById(wasteId);

        if (waste == null) {
            throw new NotFoundException("폐기물 정보를 찾을 수 없습니다.");
        }

        return waste;
    }

    public boolean formalInspection(WasteDischarge waste) {
        if (waste == null) {
            return false;
        }

        try {
            validateWaste(waste);
            return true;
        } catch (EmptyFieldException e) {
            return false;
        }
    }

    public boolean searchWasteClassification(String wasteType) {
        if (wasteType == null || wasteType.trim().isEmpty()) {
            return false;
        }

        return true;
    }

    private void validateWaste(WasteDischarge waste) {
        if (waste == null) {
            throw new EmptyFieldException("폐기물 정보가 없습니다.");
        }

        ValidationUtil.require(waste.getWasteId(), "폐기물 ID");
        ValidationUtil.require(waste.getLabId(), "연구실 ID");
        ValidationUtil.require(waste.getDischargeDate(), "배출일");
        ValidationUtil.require(waste.getWasteType(), "폐기물 종류");
        ValidationUtil.require(waste.getWasteState(), "폐기물 상태");
        ValidationUtil.require(waste.getUnit(), "단위");
    }

    private void checkDuplicateWaste(String wasteId) {
        if (wasteRepository.existsWasteById(wasteId)) {
            throw new DuplicateDataException("이미 존재하는 폐기물 ID입니다.");
        }
    }
}