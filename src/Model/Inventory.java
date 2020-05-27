package Model;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<Product> allProducts;
    private ArrayList<Part> allParts;

    public Inventory() {
        allProducts = new ArrayList<>();
        allParts = new ArrayList<>();
    }

    public void addProduct(Product addedProduct) {
        if (addedProduct != null) {
            this.allProducts.add(addedProduct);
        }
    }

    public boolean deleteProduct(int deletedPart) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductId() == deletedPart) {
                allProducts.remove(i);
                return true;
            }
        }
        return false;
    }

    public Product findProduct(int searchedProduct) {
        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getProductId() == searchedProduct) {
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }

    public void updateProduct(Product product) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductId() == product.getProductId()) {
                allProducts.set(i, product);
                break;
            }
        }
        return;
    }

    public void addPart(Part addedPart) {
        if (addedPart != null) {
            allParts.add(addedPart);
        }
    }

    public boolean deletePart(Part deletedPart) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getPartId() == deletedPart.getPartId()) {
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }

    public Part findPart(int searchedPart) {
        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getPartId() == searchedPart) {
                    return allParts.get(i);
                }
            }

        }
        return null;
    }

    public void updatePart(Part updatedPart) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getPartId() == updatedPart.partId) {
                allParts.set(i, updatedPart);
                break;
            }
        }
        return;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public ArrayList<Part> getAllParts() {
        return allParts;
    }

    public int partListSize() {
        return allParts.size();
    }

    public int productListSize() {
        return allProducts.size();
    }

}
