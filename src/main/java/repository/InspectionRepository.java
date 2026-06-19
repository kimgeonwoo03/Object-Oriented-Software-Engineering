package repository;

import entity.InspSector;
import util.JsonFileUtil;

import java.util.ArrayList;
import java.util.List;

public class InspectionRepository {

    private static final String INSP_SECTOR_FILE = "insp_sector.json";

    public int insertInspSector(InspSector inspSector) {
        List<InspSector> inspSectorList = JsonFileUtil.readList(INSP_SECTOR_FILE, InspSector.class);

        inspSectorList.add(inspSector);

        JsonFileUtil.writeList(INSP_SECTOR_FILE, inspSectorList);

        return 1;
    }

    public List<InspSector> findAllInspSectors(String filter) {
        List<InspSector> inspSectorList = JsonFileUtil.readList(INSP_SECTOR_FILE, InspSector.class);
        List<InspSector> resultList = new ArrayList<>();

        if (filter == null || filter.trim().isEmpty()) {
            return inspSectorList;
        }

        int useYnFilter = Integer.parseInt(filter);

        for (InspSector inspSector : inspSectorList) {
            if (inspSector.getUseYn() == useYnFilter) {
                resultList.add(inspSector);
            }
        }

        return resultList;
    }

    public InspSector findInspSectorById(String sectorId) {
        List<InspSector> inspSectorList = JsonFileUtil.readList(INSP_SECTOR_FILE, InspSector.class);

        for (InspSector inspSector : inspSectorList) {
            if (inspSector.getSectorId().equals(sectorId)) {
                return inspSector;
            }
        }

        return null;
    }

    public boolean existsInspSectorName(String sectorName) {
        List<InspSector> inspSectorList = JsonFileUtil.readList(INSP_SECTOR_FILE, InspSector.class);

        for (InspSector inspSector : inspSectorList) {
            if (inspSector.getSectorName() != null && inspSector.getSectorName().equals(sectorName)) {
                return true;
            }
        }

        return false;
    }

    public boolean existsInspSectorById(String sectorId) {
        return findInspSectorById(sectorId) != null;
    }
}