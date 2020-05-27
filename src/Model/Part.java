package Model;

public abstract class Part {

    protected int partId;
    protected String partName;
    protected double partPrice = 0.0;
    protected int partStock;
    protected int max;
    protected int min;

    public int getPartId() {
        return this.partId;
    }
    public String getName() {
        return this.partName;
    }

    public double getPrice() {
        return partPrice;
    }

    public int getPartStock() {
        return this.partStock;
    }

    public int getMax() {
        return this.max;
    }

    public int getMin() {
        return this.min;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public void setName(String name) {
        this.partName = name;
    }

    public void setPrice(double price) {
        this.partPrice = price;
    }

    public void setPartStock(int amount) {
        this.partStock = amount;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
