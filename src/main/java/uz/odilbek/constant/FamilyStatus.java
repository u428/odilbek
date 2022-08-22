package uz.odilbek.constant;

public enum FamilyStatus {

    ALL_ALIVE(1, "Xammalari tirik"),
    DIED_FATHER(2, "Otasi olamdan o'tgan"),
    DIED_MOTHER(3, "Onasi olamdan o'tgan"),
    DIED_ALL(4, "Boquvchisi yo'q"),
    FROM_ORPHANAGES(5, "Bolalar uyidan");


    public int id;
    public String name;

    FamilyStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static FamilyStatus findOrdinal(int id){
        for (FamilyStatus familyStatus: FamilyStatus.values()){
            if (familyStatus.getId() == id){
                return familyStatus;
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
