package Model;

import java.util.ArrayList;

public class Product {

    private ArrayList<Part> assocParts = new ArrayList<Part>();
    private int productId;
    private String name;
    private double price = 0.0;
    private int stock = 0;
    private int max;
    private int min;


    public Product(int productId, String name, double price, int stock, int max, int min) {
        setProductId(productId);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    }

    public int getProductId() {
        return this.productId;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getStock() {
        return this.stock;
    }

    public int getMax() {
        return this.max;
    }

    public int getMin() {
        return this.min;
    }

    public void setProductId(int id) {
        this.productId = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int amount) {
        this.stock = amount;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }


    public void assocPartAdd(Part addedPart) {
        assocParts.add(addedPart);
    }

    public boolean assocPartDelete(int deletedPart) {
        int i;
        for (i = 0; i < assocParts.size(); i++) {
            if (assocParts.get(i).getPartId() == deletedPart) {
                assocParts.remove(i);
                return true;
            }
        }

        return false;
    }

    public Part assocPartFind(int searchedPart) {
        for (int i = 0; i < assocParts.size(); i++) {
            if (assocParts.get(i).getPartId() == searchedPart) {
                return assocParts.get(i);
            }
        }
        return null;
    }

    public int getPartsListSize() {
        return assocParts.size();
    }

}
