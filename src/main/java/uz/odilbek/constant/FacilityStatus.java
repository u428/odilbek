package uz.odilbek.constant;

public enum FacilityStatus {

    YOUTH_NOTEBOOK(1, "Yoshlar daftari"),
    FEMALE_NOTEBOOK(2, "Ayollar daftari"),
    IRON_NOTEBOOK(3, "Temir daftar");

    public int id;
    public String name;

    FacilityStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static FacilityStatus findOrdinal(int id){
        for (FacilityStatus facilityStatus: FacilityStatus.values()){
            if (facilityStatus.getId() == id){
                return facilityStatus;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
