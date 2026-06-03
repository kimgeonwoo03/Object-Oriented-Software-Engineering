package entity;

public class Chemical {

    private String chemicalId;
    private String productName;
    private String manufacturer;
    private String catNo;
    private String casNo;
    private String capacity;
    private String unit;
    private String componentInfo;
    private String legalRegulation;
    private String ghsLabel;
    private String physicalProperties;
    private String hazardInfo;
    private int isComplex;
    private String registeredDate;

    public Chemical() {
    }

    public String getChemicalId() {
        return chemicalId;
    }

    public void setChemicalId(String chemicalId) {
        this.chemicalId = chemicalId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCatNo() {
        return catNo;
    }

    public void setCatNo(String catNo) {
        this.catNo = catNo;
    }

    public String getCasNo() {
        return casNo;
    }

    public void setCasNo(String casNo) {
        this.casNo = casNo;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getComponentInfo() {
        return componentInfo;
    }

    public void setComponentInfo(String componentInfo) {
        this.componentInfo = componentInfo;
    }

    public String getLegalRegulation() {
        return legalRegulation;
    }

    public void setLegalRegulation(String legalRegulation) {
        this.legalRegulation = legalRegulation;
    }

    public String getGhsLabel() {
        return ghsLabel;
    }

    public void setGhsLabel(String ghsLabel) {
        this.ghsLabel = ghsLabel;
    }

    public String getPhysicalProperties() {
        return physicalProperties;
    }

    public void setPhysicalProperties(String physicalProperties) {
        this.physicalProperties = physicalProperties;
    }

    public String getHazardInfo() {
        return hazardInfo;
    }

    public void setHazardInfo(String hazardInfo) {
        this.hazardInfo = hazardInfo;
    }

    public int getIsComplex() {
        return isComplex;
    }

    public void setIsComplex(int isComplex) {
        this.isComplex = isComplex;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }
}