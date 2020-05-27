package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreen implements Initializable {

    private Inventory inv;

    @FXML
    private TextField partSearchBox;
    @FXML
    private TextField productSearchBox;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableView<Product> productsTable;
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Product> productInventorySearch = FXCollections.observableArrayList();

    public MainScreen(Inventory inv) {
        this.inv = inv;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createPartsTable();
        createProductsTable();
    }

    private void createPartsTable() {
        partInventory.setAll(inv.getAllParts());
        TableColumn<Part, Integer> partIdCol = composePartId();
        TableColumn<Part, String> nameCol = composeName();
        TableColumn<Part, Integer> partStockCol = composePartStock();
        TableColumn<Part, Double> costCol = composePrice();
        partsTable.getColumns().addAll(partIdCol, nameCol, partStockCol, costCol);
        partsTable.setItems(partInventory);
    }

    private void createProductsTable() {
        productInventory.setAll(inv.getAllProducts());
        TableColumn<Product, Integer> productIdCol = composeProductId();
        TableColumn<Product, String> namePrCol = composePrName();
        TableColumn<Product, Integer> stockCol = composeStock();
        TableColumn<Product, Double> costCol = composePrice();
        productsTable.getColumns().addAll(productIdCol, namePrCol, stockCol, costCol);
        productsTable.setItems(productInventory);
        productsTable.refresh();
    }

    @FXML
    private void exitProgram(ActionEvent event
    ) {
        Platform.exit();
    }

    @FXML
    private void exitProgramButton(MouseEvent event
    ) {
        Platform.exit();
    }

    @FXML
    private void searchForPart(MouseEvent event) {
        if (!partSearchBox.getText().trim().isEmpty()) {
            partsInventorySearch.clear();
            for (Part i : inv.getAllParts()) {
                if (i.getName().contains(partSearchBox.getText().trim())) {
                    partsInventorySearch.add(i);
                }
            }
            partsTable.setItems(partsInventorySearch);
            partsTable.refresh();
        }
    }

    @FXML
    private void searchForProduct(MouseEvent event
    ) {
        if (!productSearchBox.getText().trim().isEmpty()) {
            productInventorySearch.clear();
            for (Product i : inv.getAllProducts()) {
                if (i.getName().contains(productSearchBox.getText().trim())) {
                    productInventorySearch.add(i);
                }
            }
            productsTable.setItems(productInventorySearch);
            productsTable.refresh();
        }
    }

    @FXML
    void clearText(MouseEvent event
    ) {
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
        if (partSearchBox == field) {
            if (partInventory.size() != 0) {
                partsTable.setItems(partInventory);
            }
        }
        if (productSearchBox == field) {
            if (productInventory.size() != 0) {
                productsTable.setItems(productInventory);
            }
        }
    }

    @FXML
    private void addPart(MouseEvent event
    ) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddPart.fxml"));
            AddPart controller = new AddPart(inv);

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ignored) {

        }
    }

    @FXML
    private void modifyPart(MouseEvent event
    ) {
        try {
            Part selected = partsTable.getSelectionModel().getSelectedItem();
            if (partInventory.isEmpty()) {
                errorWindow(0);
                return;
            }
            if (!partInventory.isEmpty() && selected == null) {
                errorWindow(1);
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyPart.fxml"));
                ModifyPart controller = new ModifyPart(inv, selected);

                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        } catch (IOException e) {

        }
    }

    @FXML
    private void deletePart(MouseEvent event
    ) {
        Part deletePart = partsTable.getSelectionModel().getSelectedItem();
        if (partInventory.isEmpty()) {
            errorWindow(0);
            return;
        }
        if (!partInventory.isEmpty() && deletePart == null) {
            errorWindow(1);
            return;
        } else {
            boolean confirm = confirmationWindow(deletePart.getName());
            if (!confirm) return;
            inv.deletePart(deletePart);
            partInventory.remove(deletePart);
            partsTable.refresh();

        }
    }

    @FXML
    private void deleteProduct(MouseEvent event
    ) {
        boolean deleted = false;
        Product removeProduct = productsTable.getSelectionModel().getSelectedItem();
        if (productInventory.isEmpty()) {
            errorWindow(0);
            return;
        }
        if (!productInventory.isEmpty() && removeProduct == null) {
            errorWindow(1);
            return;
        }
        if (removeProduct.getPartsListSize() > 0) {
            boolean confirm = confirmDelete(removeProduct.getName());
            if (!confirm) {
                return;
            }
        } else {
            if (removeProduct != null) {
                infoWindow(0, removeProduct.getName());
                deleted = true;
                if (deleted) {
                    return;

                } else {
                    infoWindow(1, "");
                }

            }
        }
        inv.deleteProduct(removeProduct.getProductId());
        productInventory.remove(removeProduct);
        productsTable.setItems(productInventory);
        productsTable.refresh();
    }

    @FXML
    private void modifyProduct(MouseEvent event
    ) {
        try {
            Product productSelected = productsTable.getSelectionModel().getSelectedItem();
            if (productInventory.isEmpty()) {
                errorWindow(0);
                return;
            }
            if (!productInventory.isEmpty() && productSelected == null) {
                errorWindow(1);
                return;

            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyProduct.fxml"));
                ModifyProduct controller = new ModifyProduct(inv, productSelected);

                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        } catch (IOException e) {

        }
    }

    @FXML
    private void addProduct(MouseEvent event
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddProduct.fxml"));
            AddProduct controller = new AddProduct(inv);

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {

        }
    }

    private void errorWindow(int code) {
        if (code == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Inventory empty.");
            alert.setContentText("Nothing to select.");
            alert.showAndWait();
        }
        if (code == 1) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection invalid.");
            alert.setContentText("An item must be selected.");
            alert.showAndWait();
        }

    }

    private boolean confirmationWindow(String name) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Part");
        alert.setHeaderText("Delete: " + name + "?");
        alert.setContentText("OK to confirm");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    private boolean confirmDelete(String name) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Product");
        alert.setHeaderText("Confirm deletion of: " + name + ". Product still has parts.");
        alert.setContentText("OK to confirm");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    private void infoWindow(int code, String name) {
        if (code != 1) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Confirmed");
            alert.setHeaderText(null);
            alert.setContentText(name + " deleted.");

            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("There has been an error.");
        }
    }

    private <F> TableColumn<F, Integer> composePartId() {
        TableColumn<F, Integer> partIdCol = new TableColumn("Part ID");
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        partIdCol.setPrefWidth(80);
        partIdCol.setCellFactory((TableColumn<F, Integer> column) -> {
            return new TableCell<F, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    if (!empty) {
                        setText(String.format("%d", item));
                    }
                }
            };
        });
        return partIdCol;
    }

    private <G> TableColumn<G, Integer> composeProductId() {
        TableColumn<G, Integer> productIdCol = new TableColumn("Product ID");
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productIdCol.setPrefWidth(80);
        productIdCol.setCellFactory((TableColumn<G, Integer> column) -> {
            return new TableCell<G, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    if (!empty) {
                        setText(String.format("%d", item));
                    }
                }
            };
        });
        return productIdCol;
    }

    private <H> TableColumn<H, String> composeName() {
        TableColumn<H, String> nameCol = new TableColumn("Part Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(100);
        nameCol.setCellFactory((TableColumn<H, String> column) -> {
            return new TableCell<H, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    if (!empty) {
                        setText(String.format("%s", item));
                    }
                }
            };
        });
        return nameCol;
    }

    private <I> TableColumn<I, Integer> composeStock() {
        TableColumn<I, Integer> stockCol = new TableColumn("Inventory Level");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockCol.setPrefWidth(100);
        stockCol.setCellFactory((TableColumn<I, Integer> column) -> {
            return new TableCell<I, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    if (!empty) {
                        setText(String.format("%d", item));
                    }
                }
            };
        });
        return stockCol;
    }

    private <J> TableColumn<J, Double> composePrice() {
        TableColumn<J, Double> costCol = new TableColumn("Price");
        costCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        costCol.setPrefWidth(60);
        costCol.setCellFactory((TableColumn<J, Double> column) -> {
            return new TableCell<J, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    if (!empty) {
                        setText("$" + String.format("%.2f", item));
                    }
                }
            };
        });
        return costCol;
    }

    private <K> TableColumn<K, String> composePrName() {
        TableColumn<K, String> namePrCol = new TableColumn("Product Name");
        namePrCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        namePrCol.setPrefWidth(100);
        namePrCol.setCellFactory((TableColumn<K, String> column) -> {
            return new TableCell<K, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    if (!empty) {
                        setText(String.format("%s", item));
                    }
                }
            };
        });
        return namePrCol;
    }

    private <L> TableColumn<L, Integer> composePartStock() {
        TableColumn<L, Integer> partStockCol = new TableColumn("Inventory Level");
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        partStockCol.setPrefWidth(100);
        partStockCol.setCellFactory((TableColumn<L, Integer> column) -> {
            return new TableCell<L, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    if (!empty) {
                        setText(String.format("%d", item));
                    }
                }
            };
        });
        return partStockCol;
    }

}
