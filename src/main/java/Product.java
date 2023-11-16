import java.math.BigDecimal;

public class Product {
    private String SKU;
    private String productName;
    private int unitOfMeasurement;
    private BigDecimal cost;
    private BigDecimal maxDiscountSize;
    private int manufacturer;
    private int supplier;
    private int category;
    private BigDecimal currentDiscount;
    private int quantityInStock;
    private String description;
    private String image;

    public Product(String SKU, String productName, int unitOfMeasurement, BigDecimal cost, BigDecimal maxDiscountSize,
                   int manufacturer, int supplier, int category, BigDecimal currentDiscount, int quantityInStock,
                   String description, String image) {
        this.SKU = SKU;
        this.productName = productName;
        this.unitOfMeasurement = unitOfMeasurement;
        this.cost = cost;
        this.maxDiscountSize = maxDiscountSize;
        this.manufacturer = manufacturer;
        this.supplier = supplier;
        this.category = category;
        this.currentDiscount = currentDiscount;
        this.quantityInStock = quantityInStock;
        this.description = description;
        this.image = image;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(int unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getMaxDiscountSize() {
        return maxDiscountSize;
    }

    public void setMaxDiscountSize(BigDecimal maxDiscountSize) {
        this.maxDiscountSize = maxDiscountSize;
    }

    public int getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(int manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getSupplier() {
        return supplier;
    }

    public void setSupplier(int supplier) {
        this.supplier = supplier;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public BigDecimal getCurrentDiscount() {
        return currentDiscount;
    }

    public void setCurrentDiscount(BigDecimal currentDiscount) {
        this.currentDiscount = currentDiscount;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
