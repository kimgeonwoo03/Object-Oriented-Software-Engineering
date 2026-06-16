package service;

import entity.DrawingInfo;
import entity.LabBasicInfo;
import entity.LabLocationInfo;
import exception.DuplicateDataException;
import exception.EmptyFieldException;
import exception.NotFoundException;
import repository.LabRepository;
import util.ValidationUtil;

import java.util.List;

public class LabService {

    private LabRepository labRepository = new LabRepository();

    public boolean createLab(LabBasicInfo lab) {
        validateLab(lab);
        checkDuplicateLab(lab);

        int result = labRepository.insertLab(lab);

        return result > 0;
    }

    public boolean createLabBasicInfo(LabBasicInfo lab,
                                      DrawingInfo drawingInfo,
                                      LabLocationInfo locationInfo) {
        validateLab(lab);
        checkDuplicateLab(lab);

        boolean hasDrawing = hasText(drawingInfo != null ? drawingInfo.getDrawingName() : null)
                || hasText(drawingInfo != null ? drawingInfo.getFilePath() : null);

        if (hasDrawing) {
            validateDrawingInfo(drawingInfo);
            lab.setDrawingRegistered(1);
        } else {
            lab.setDrawingRegistered(0);
        }

        int labResult = labRepository.insertLab(lab);

        if (hasDrawing) {
            labRepository.insertDrawingInfo(drawingInfo);

            if (locationInfo != null && hasText(locationInfo.getCoordX())) {
                validateLabLocationInfo(locationInfo);
                labRepository.insertLabLocationInfo(locationInfo);
            }
        }

        return labResult > 0;
    }

    public List<LabBasicInfo> readLabList(String condition, String drawingRegistered) {
        return labRepository.findLabsByCondition(condition, drawingRegistered);
    }

    public List<LabBasicInfo> readLabList() {
        return labRepository.findAllLabs();
    }

    public LabBasicInfo readLab(String labId) {
        ValidationUtil.require(labId, "연구실 ID");

        LabBasicInfo lab = labRepository.findLabById(labId);

        if (lab == null) {
            throw new NotFoundException("연구실 기본정보를 찾을 수 없습니다.");
        }

        return lab;
    }

    public DrawingInfo readDrawingInfo(String labId) {
        ValidationUtil.require(labId, "연구실 ID");
        return labRepository.findDrawingInfoByLabId(labId);
    }

    public LabLocationInfo readLabLocationInfo(String labId) {
        ValidationUtil.require(labId, "연구실 ID");
        return labRepository.findLabLocationInfoByLabId(labId);
    }

    public boolean createDrawingInfo(DrawingInfo drawingInfo) {
        validateDrawingInfo(drawingInfo);

        if (labRepository.existsDrawingById(drawingInfo.getDrawingId())) {
            throw new DuplicateDataException("이미 존재하는 도면 ID입니다.");
        }

        LabBasicInfo lab = readLab(drawingInfo.getLabId());
        lab.setDrawingRegistered(1);
        labRepository.updateLab(lab);

        int result = labRepository.insertDrawingInfo(drawingInfo);

        return result > 0;
    }

    public boolean createLabLocationInfo(LabLocationInfo locationInfo) {
        validateLabLocationInfo(locationInfo);

        if (labRepository.existsLocationById(locationInfo.getLocationId())) {
            throw new DuplicateDataException("이미 존재하는 위치 ID입니다.");
        }

        int result = labRepository.insertLabLocationInfo(locationInfo);

        return result > 0;
    }

    public void validateLab(LabBasicInfo lab) {
        if (lab == null) {
            throw new EmptyFieldException("연구실 기본정보가 없습니다.");
        }

        ValidationUtil.require(lab.getLabId(), "연구실 ID");
        ValidationUtil.require(lab.getLabName(), "연구실명");
        ValidationUtil.require(lab.getDepartment(), "소속");
        ValidationUtil.require(lab.getBuilding(), "건물");
        ValidationUtil.require(lab.getFloor(), "층");
        ValidationUtil.require(lab.getRoomNo(), "호실");
        ValidationUtil.require(lab.getManagerId(), "책임자 ID");
    }

    public void checkDuplicateLab(LabBasicInfo lab) {
        if (labRepository.existsLabById(lab.getLabId())) {
            throw new DuplicateDataException("이미 존재하는 연구실 ID입니다.");
        }

        if (labRepository.existsSameLabName(lab.getLabName())) {
            throw new DuplicateDataException("이미 존재하는 연구실명입니다.");
        }

        if (labRepository.existsSameLocation(lab.getBuilding(), lab.getFloor(), lab.getRoomNo())) {
            throw new DuplicateDataException("동일한 건물/층/호실에 등록된 연구실이 있습니다.");
        }
    }

    public void validateDrawingInfo(DrawingInfo drawingInfo) {
        if (drawingInfo == null) {
            throw new EmptyFieldException("도면 정보가 없습니다.");
        }

        ValidationUtil.require(drawingInfo.getDrawingId(), "도면 ID");
        ValidationUtil.require(drawingInfo.getLabId(), "연구실 ID");
        ValidationUtil.require(drawingInfo.getDrawingName(), "도면명");
        ValidationUtil.require(drawingInfo.getFileType(), "파일 형식");
        ValidationUtil.require(drawingInfo.getFilePath(), "파일 경로");
        ValidationUtil.require(drawingInfo.getBaseDate(), "기준일");

        String fileType = drawingInfo.getFileType().toLowerCase();

        if (!(fileType.equals("pdf") || fileType.equals("png") || fileType.equals("jpg")
                || fileType.equals("jpeg") || fileType.equals("dwg") || fileType.equals("cad"))) {
            throw new EmptyFieldException("도면 파일 형식은 pdf, png, jpg, jpeg, dwg, cad 중 하나여야 합니다.");
        }
    }

    public void validateLabLocationInfo(LabLocationInfo locationInfo) {
        if (locationInfo == null) {
            throw new EmptyFieldException("연구실 위치 정보가 없습니다.");
        }

        ValidationUtil.require(locationInfo.getLocationId(), "위치 ID");
        ValidationUtil.require(locationInfo.getLabId(), "연구실 ID");
        ValidationUtil.require(locationInfo.getDrawingId(), "도면 ID");
        ValidationUtil.require(locationInfo.getCoordX(), "X 좌표");
        ValidationUtil.require(locationInfo.getCoordY(), "Y 좌표");
        ValidationUtil.require(locationInfo.getWidth(), "가로 영역");
        ValidationUtil.require(locationInfo.getHeight(), "세로 영역");
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
