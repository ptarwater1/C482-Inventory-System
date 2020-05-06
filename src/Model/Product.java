package Model;

import java.util.ArrayList;

public class Product {
    private ArrayList<Part> associatedParts = new ArrayList<~>();
    private int productID;
    private String name;
    private double price = 0.0;
    private int inStock =0;
    private int min;
    private int max;
    private  double cost;

    public Product(int productID, String name, double price, int price, int inStock, int min, int max, double cost)
        setProductID(productID);
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMin(min);
        setMax(max);
}
