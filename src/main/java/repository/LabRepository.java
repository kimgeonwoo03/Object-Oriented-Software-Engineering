package repository;

import entity.DrawingInfo;
import entity.LabBasicInfo;
import entity.LabLocationInfo;
import util.JsonFileUtil;

import java.util.ArrayList;
import java.util.List;

public class LabRepository {

    private static final String LAB_FILE = "lab_basic_info.json";
    private static final String DRAWING_FILE = "drawing_info.json";
    private static final String LOCATION_FILE = "lab_location_info.json";

    public int insertLab(LabBasicInfo lab) {
        List<LabBasicInfo> labList = JsonFileUtil.readList(LAB_FILE, LabBasicInfo.class);

        labList.add(lab);

        JsonFileUtil.writeList(LAB_FILE, labList);

        return 1;
    }

    public int updateLab(LabBasicInfo lab) {
        List<LabBasicInfo> labList = JsonFileUtil.readList(LAB_FILE, LabBasicInfo.class);

        for (int i = 0; i < labList.size(); i++) {
            LabBasicInfo savedLab = labList.get(i);

            if (savedLab.getLabId() != null && savedLab.getLabId().equals(lab.getLabId())) {
                labList.set(i, lab);
                JsonFileUtil.writeList(LAB_FILE, labList);
                return 1;
            }
        }

        return 0;
    }

    public List<LabBasicInfo> findAllLabs() {
        return JsonFileUtil.readList(LAB_FILE, LabBasicInfo.class);
    }

    public List<LabBasicInfo> findLabsByCondition(String condition, String drawingRegistered) {
        List<LabBasicInfo> labList = findAllLabs();
        List<LabBasicInfo> resultList = new ArrayList<>();

        String keyword = condition == null ? "" : condition.trim().toLowerCase();
        String drawingFilter = drawingRegistered == null ? "" : drawingRegistered.trim();

        for (LabBasicInfo lab : labList) {
            boolean keywordMatched = keyword.isEmpty()
                    || contains(lab.getLabId(), keyword)
                    || contains(lab.getLabName(), keyword)
                    || contains(lab.getDepartment(), keyword)
                    || contains(lab.getManagerId(), keyword)
                    || contains(lab.getBuilding(), keyword)
                    || contains(lab.getFloor(), keyword)
                    || contains(lab.getRoomNo(), keyword);

            boolean drawingMatched = drawingFilter.isEmpty()
                    || String.valueOf(lab.getDrawingRegistered()).equals(drawingFilter);

            if (keywordMatched && drawingMatched) {
                resultList.add(lab);
            }
        }

        return resultList;
    }

    private boolean contains(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }

    public LabBasicInfo findLabById(String labId) {
        List<LabBasicInfo> labList = findAllLabs();

        for (LabBasicInfo lab : labList) {
            if (lab.getLabId() != null && lab.getLabId().equals(labId)) {
                return lab;
            }
        }

        return null;
    }

    public boolean existsLabById(String labId) {
        return findLabById(labId) != null;
    }

    public boolean existsSameLabName(String labName) {
        List<LabBasicInfo> labList = findAllLabs();

        for (LabBasicInfo lab : labList) {
            if (lab.getLabName() != null && lab.getLabName().equals(labName)) {
                return true;
            }
        }

        return false;
    }

    public boolean existsSameLocation(String building, String floor, String roomNo) {
        List<LabBasicInfo> labList = findAllLabs();

        for (LabBasicInfo lab : labList) {
            boolean sameBuilding = lab.getBuilding() != null && lab.getBuilding().equals(building);
            boolean sameFloor = lab.getFloor() != null && lab.getFloor().equals(floor);
            boolean sameRoomNo = lab.getRoomNo() != null && lab.getRoomNo().equals(roomNo);

            if (sameBuilding && sameFloor && sameRoomNo) {
                return true;
            }
        }

        return false;
    }

    public int insertDrawingInfo(DrawingInfo drawingInfo) {
        List<DrawingInfo> drawingList = JsonFileUtil.readList(DRAWING_FILE, DrawingInfo.class);

        drawingList.add(drawingInfo);

        JsonFileUtil.writeList(DRAWING_FILE, drawingList);

        return 1;
    }

    public DrawingInfo findDrawingInfoByLabId(String labId) {
        List<DrawingInfo> drawingList = JsonFileUtil.readList(DRAWING_FILE, DrawingInfo.class);

        for (DrawingInfo drawingInfo : drawingList) {
            if (drawingInfo.getLabId() != null && drawingInfo.getLabId().equals(labId)) {
                return drawingInfo;
            }
        }

        return null;
    }

    public DrawingInfo findDrawingInfoById(String drawingId) {
        List<DrawingInfo> drawingList = JsonFileUtil.readList(DRAWING_FILE, DrawingInfo.class);

        for (DrawingInfo drawingInfo : drawingList) {
            if (drawingInfo.getDrawingId() != null && drawingInfo.getDrawingId().equals(drawingId)) {
                return drawingInfo;
            }
        }

        return null;
    }

    public boolean existsDrawingById(String drawingId) {
        return findDrawingInfoById(drawingId) != null;
    }

    public int insertLabLocationInfo(LabLocationInfo locationInfo) {
        List<LabLocationInfo> locationList = JsonFileUtil.readList(LOCATION_FILE, LabLocationInfo.class);

        locationList.add(locationInfo);

        JsonFileUtil.writeList(LOCATION_FILE, locationList);

        return 1;
    }

    public LabLocationInfo findLabLocationInfoByLabId(String labId) {
        List<LabLocationInfo> locationList = JsonFileUtil.readList(LOCATION_FILE, LabLocationInfo.class);

        for (LabLocationInfo locationInfo : locationList) {
            if (locationInfo.getLabId() != null && locationInfo.getLabId().equals(labId)) {
                return locationInfo;
            }
        }

        return null;
    }

    public boolean existsLocationById(String locationId) {
        List<LabLocationInfo> locationList = JsonFileUtil.readList(LOCATION_FILE, LabLocationInfo.class);

        for (LabLocationInfo locationInfo : locationList) {
            if (locationInfo.getLocationId() != null && locationInfo.getLocationId().equals(locationId)) {
                return true;
            }
        }

        return false;
    }
}
