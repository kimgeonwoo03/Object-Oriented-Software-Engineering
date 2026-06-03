package service;

import entity.Chemical;
import exception.DuplicateDataException;
import exception.EmptyFieldException;
import exception.NotFoundException;
import repository.ChemicalRepository;
import util.ValidationUtil;

import java.util.List;

public class ChemicalService {

    private ChemicalRepository chemicalRepository = new ChemicalRepository();

    public boolean createChemical(Chemical chemical) {
        validateChemical(chemical);
        checkDuplicateChemical(chemical.getChemicalId());

        int result = chemicalRepository.insertChemical(chemical);

        return result > 0;
    }

    public List<Chemical> readChemicalList(String condition) {
        return chemicalRepository.findChemicalsByCondition(condition);
    }

    public Chemical readChemical(String chemicalId) {
        ValidationUtil.require(chemicalId, "화학물질 ID");

        Chemical chemical = chemicalRepository.findChemicalById(chemicalId);

        if (chemical == null) {
            throw new NotFoundException("화학물질 정보를 찾을 수 없습니다.");
        }

        return chemical;
    }

    public Chemical searchKOSHAInfo(String casNo) {
        ValidationUtil.require(casNo, "CAS No");

        Chemical chemical = new Chemical();
        chemical.setCasNo(casNo);

        // 실제 KOSHA API 연동 전까지는 테스트용 더미 데이터로 처리
        chemical.setComponentInfo("KOSHA 조회 결과 예시 구성성분");
        chemical.setLegalRegulation("KOSHA 조회 결과 예시 법적규제");
        chemical.setGhsLabel("KOSHA 조회 결과 예시 GHS 표지");
        chemical.setPhysicalProperties("KOSHA 조회 결과 예시 물리화학적 특성");
        chemical.setHazardInfo("KOSHA 조회 결과 예시 유해·위험성 정보");

        return chemical;
    }

    public void validateChemical(Chemical chemical) {
        if (chemical == null) {
            throw new EmptyFieldException("화학물질 정보가 없습니다.");
        }

        ValidationUtil.require(chemical.getChemicalId(), "화학물질 ID");
        ValidationUtil.require(chemical.getProductName(), "제품명");
        ValidationUtil.require(chemical.getManufacturer(), "제조사");
        ValidationUtil.require(chemical.getCasNo(), "CAS No");
        ValidationUtil.require(chemical.getCapacity(), "용량");
        ValidationUtil.require(chemical.getUnit(), "단위");
        ValidationUtil.require(chemical.getRegisteredDate(), "등록일");
    }

    public void checkDuplicateChemical(String chemicalId) {
        if (chemicalRepository.existsChemicalById(chemicalId)) {
            throw new DuplicateDataException("이미 존재하는 화학물질 ID입니다.");
        }
    }
}
