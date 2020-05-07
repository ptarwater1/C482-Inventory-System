package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public void addPart(Part addPart) {
        if (addPart != null) {
            this.allParts.add(addPart);
        }
    }

    public void  addProduct(Product addedProduct) {
        if (addedProduct != null) {
            this.allProducts.add(addedProduct);
        }
    }

    public Part lookupPart (int lookedupPart) {
        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getPartId() == lookedupPart) {
                    return allParts.get(i);
                }
            }
        }
        return null;
    }

    public Product lookupProduct (int lookedupProduct) {
        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getProductId() == lookedupProduct) {
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }

    public void updatePart(Part updatedPart) {
        for (int i= 0; i <allParts.size(); i++) {
            if (allParts.get(i).getPartId() == updatedPart.partId) {
                allParts.set(i, updatedPart);
                break;
            }
        }
        return;
    }

    public void updateProduct(Product updatedProduct) {
        for (int i= 0; i <allProducts.size(); i++) {
            if (allProducts.get(i).getProductId() == updatedProduct.productId) {
                allProducts.set(i, updatedProduct);
                break;
            }
        }
        return;
    }

    public boolean deletePart(Part deletedPart) {
        for (int i=0; i < allParts.size(); i++)
            if (allParts.get(i).getPartId() == deletedPart.partId) {
                allParts.remove(i);
                return true;
            }
        return false;
    }

    public boolean deleteProduct(Product deletedProduct) {
        for (int i=0; i < allProducts.size(); i++)
            if (allProducts.get(i).getProductId() == deletedProduct.productId) {
                allProducts.remove(i);
                return true;
            }
        return false;
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
