package entity;

public class InspSector {
    private String sectorId;
    private String sectorName;
    private String sectorDesc;
    private int useYn;
    private String regDate;

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getSectorDesc() {
        return sectorDesc;
    }

    public void setSectorDesc(String sectorDesc) {
        this.sectorDesc = sectorDesc;
    }

    public int getUseYn() {
        return useYn;
    }

    public void setUseYn(int useYn) {
        this.useYn = useYn;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}