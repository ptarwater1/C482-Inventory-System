package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Product {
    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();
    public int productId;
    private String productName;
    private double productPrice = 0.0;
    private int stock = 0;
    private int min;
    private int max;

    public Product(int productId, String name, double price, int stock, int min, int max) {
        setProductId(productId);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.productName = name;
    }

    public void setPrice( double price) {
        this.productPrice = price;
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

    public int getProductId() {
        return this.productId;
    }

    public String getName() {
        return this.productName;
    }

    public double getPrice() {
        return this.productPrice;
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

    public void addAssociatedPart(Part addPart) {
        associatedPart.add(addPart);
    }

    public boolean deleteAssociatedPart(int partId) {
        for (Part i : associatedPart) {
            if (i.getPartId() == partId) {
                associatedPart.remove(i);
                return true;
            }
        }

        return false;
    }

    public Part getAllAssociatedParts(int partID) {
        for (Part i : associatedPart) {
            if (i.getPartId() == partID) {
                return i;
            }
        }

        return null;
    }

}