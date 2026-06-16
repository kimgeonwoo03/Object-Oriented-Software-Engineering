package entity;

public class LabBasicInfo {

    private String labId;
    private String labName;
    private String department;
    private String building;
    private String floor;
    private String roomNo;
    private String managerId;
    private String accessUserInfo;
    private int drawingRegistered;
    private String registeredDate;

    public LabBasicInfo() {
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getAccessUserInfo() {
        return accessUserInfo;
    }

    public void setAccessUserInfo(String accessUserInfo) {
        this.accessUserInfo = accessUserInfo;
    }

    public int getDrawingRegistered() {
        return drawingRegistered;
    }

    public void setDrawingRegistered(int drawingRegistered) {
        this.drawingRegistered = drawingRegistered;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }
}
