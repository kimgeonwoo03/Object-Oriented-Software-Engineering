package service;

import entity.InspSector;
import exception.DuplicateDataException;
import exception.EmptyFieldException;
import exception.NotFoundException;
import repository.InspectionRepository;

import java.util.List;

public class InspectionService {

    private InspectionRepository inspectionRepository = new InspectionRepository();

    public boolean createInspSector(InspSector inspSector) {
        validateInspSector(inspSector);
        checkDuplicateSectorName(inspSector.getSectorName());

        int result = inspectionRepository.insertInspSector(inspSector);

        return result > 0;
    }

    public List<InspSector> readInspSectorList(String filter) {
        return inspectionRepository.findAllInspSectors(filter);
    }

    public InspSector readInspSector(String sectorId) {
        if (sectorId == null || sectorId.isBlank()) {
            throw new EmptyFieldException("점검 분야 ID는 필수입니다.");
        }

        InspSector inspSector = inspectionRepository.findInspSectorById(sectorId);

        if (inspSector == null) {
            throw new NotFoundException("점검 분야 정보를 찾을 수 없습니다.");
        }

        return inspSector;
    }

    public void validateInspSector(InspSector inspSector) {
        if (inspSector.getSectorId() == null || inspSector.getSectorId().isBlank()) {
            throw new EmptyFieldException("점검 분야 ID는 필수입니다.");
        }

        if (inspSector.getSectorName() == null || inspSector.getSectorName().isBlank()) {
            throw new EmptyFieldException("점검 분야명은 필수입니다.");
        }

        if (inspSector.getSectorDesc() == null || inspSector.getSectorDesc().isBlank()) {
            throw new EmptyFieldException("점검 분야 설명은 필수입니다.");
        }

        if (inspSector.getUseYn() != 0 && inspSector.getUseYn() != 1) {
            throw new IllegalArgumentException("사용 여부는 1 또는 0만 입력할 수 있습니다.");
        }
    }

    public void checkDuplicateSectorName(String sectorName) {
        if (inspectionRepository.existsInspSectorName(sectorName)) {
            throw new DuplicateDataException("이미 존재하는 점검 분야명입니다.");
        }
    }
}