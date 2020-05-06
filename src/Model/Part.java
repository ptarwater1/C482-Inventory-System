package Model;

public abstract class Part {

    protected int partID;
    protected String partName;
    protected double partPrice = 0.0;
    protected int partInStock;
    protected int min;
    protected  int max;

    public String getName() {
        return this.partName;
    }

    public void setName(String name) {
        this.partName = name;
    }

    public double getPrice() {
        return partPrice;
    }

    public void setPrice(double price) {
        this.partPrice = price;
    }

    public int getInStock(int quantity) {
       return this.partInStock = quantity;
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        this.max = max;
    }

}
