package Model;

public abstract class Part {

    public int partId;
    private String partName;
    private double partPrice = 0.0;
    private int stock;
    private int min;
    private int max;

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public void setName(String name) {
        this.partName = name;
    }

    public void setPrice(double price) {
        this.partPrice = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getPartId() {
        return this.partId;
    }

    public String getName() {
        return this.partName;
    }

    public double getPrice() {
        return this.partPrice;
    }

    public int getStock() {
       return this.stock;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }


}
