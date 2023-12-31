import java.util.Objects;

public class Supplier {
    private int id;
    private String supplierName;

    public Supplier(int id, String supplierName) {
        this.id = id;
        this.supplierName = supplierName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int findByName(String supName) {
        if (Objects.equals(supName, this.supplierName)) {
            return id;
        }
        return -1;
    }
}
