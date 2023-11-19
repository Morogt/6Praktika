public class Manufacturer {
    private int id;
    private String manName;

    public Manufacturer(int id, String manName) {
        this.id = id;
        this.manName = manName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManName() {
        return manName;
    }

    public void setManName(String manName) {
        this.manName = manName;
    }
}
