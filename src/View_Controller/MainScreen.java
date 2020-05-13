package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreen implements Initializable {

    Inventory inv;

    @FXML
    private TableView<Part> partsTable;
    ObservableList<Part> partsInv = FXCollections.observableArrayList();
    ObservableList<Part> partsInvSearch = FXCollections.observableArrayList();

    @FXML
    private TableView<Product> productsTable;
    ObservableList<Product> productsInv = FXCollections.observableArrayList();
    ObservableList<Product> productsInvSearch = FXCollections.observableArrayList();


    void createPartsTable() {
        partsInv.setAll(inv.getAllParts());
        TableColumn<Part, Integer> partIdCol = composePartId();
        TableColumn<Part, String> nameCol = composeName();
        TableColumn<Part, Integer> stockCol = composeStock();
        TableColumn<Part, Double> costCol = composePrice();
        partsTable.getColumns().addAll(partIdCol, nameCol, stockCol, costCol);
        partsTable.setItems(partsInv);
    }

    void createProductsTable() {
        productsInv.setAll(inv.getAllProducts());
        TableColumn<Product, Integer> productIdCol = composeProductId();
        TableColumn<Product, String> namePrCol = composePrName();
        TableColumn<Product, Integer> stockCol = composeStock();
        TableColumn<Product, Double> costCol = composePrice();
        productsTable.getColumns().addAll(productIdCol, namePrCol, stockCol, costCol);
        productsTable.setItems(productsInv);
        productsTable.refresh();
    }

    private <F> TableColumn<F, Integer> composePartId() {
        TableColumn<F, Integer> partIdCol = new TableColumn("Part ID");
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createPartsTable();
        createProductsTable();
    }

    @FXML
    private TextField partsSearchBox;

    @FXML
    private Button searchPartsButton;

    @FXML
    private Button deletePartsButton;

    @FXML
    private Button modifyPartsButton;

    @FXML
    private Button addPartsButton;

    @FXML
    private TextField productsSearchBox;

    @FXML
    private Button searchProductsButton;

    @FXML
    private Button deleteProdcutsButton;

    @FXML
    private Button modifyProductsButton;

    @FXML
    private Button addProductsButton;

    @FXML
    private Button exitProgramButton;

    @FXML
    public void addParts(MouseEvent event) throws IOException

    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddPartScreen.fxml"));
        AddPartScreen controller = new AddPartScreen(inv);

        loader.setController(controller);
        Parent view = loader.load();
        Scene scene = new Scene(view);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void addProducts(MouseEvent event) throws IOException

    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddProductScreen.fxml"));
        AddProductScreen controller = new AddProductScreen(inv);

        loader.setController(controller);
        Parent view = loader.load();
        Scene scene = new Scene(view);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void deleteParts(MouseEvent event) {

        Part deletePart = partsTable.getSelectionModel().getSelectedItem();
        if (partsInv.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error.");
            alert.setHeaderText("Cannot remove.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Part");
            alert.setContentText("Confirm deletion.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                partsInv.remove(deletePart);
                partsTable.refresh();
            }
        }
    }

    @FXML
    void deleteProducts(MouseEvent event) {

        Product deleteProduct = productsTable.getSelectionModel().getSelectedItem();
        if (productsInv.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error.");
            alert.setHeaderText("Cannot remove.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Product");
            alert.setContentText("Confirm deletion.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                productsInv.remove(deleteProduct);
                productsTable.refresh();
            }
        }

    }

    @FXML
    void exitProgram(MouseEvent event) {
        Platform.exit();

    }

    @FXML
    void modifyParts(MouseEvent event) throws IOException {

        Part selected = partsTable.getSelectionModel().getSelectedItem();
        if (partsInv.isEmpty()) {
            errorWindow(0);
            return;
        }
        if (!partsInv.isEmpty() && selected == null) {
            errorWindow(1);
            return;
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyPartScreen.fxml"));
            ModifyPartScreen controller = new ModifyPartScreen(inv);

            loader.setController(controller);
            Parent view = loader.load();
            Scene scene = new Scene(view);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    void modifyProducts(MouseEvent event)  throws IOException {

        Product selected = productsTable.getSelectionModel().getSelectedItem();
        if (productsInv.isEmpty()) {
            errorWindow(0);
            return;
        }
        if (!productsInv.isEmpty() && selected == null) {
            errorWindow(1);
            return;
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyProductScreen.fxml"));
            ModifyProductScreen controller;
            controller = new ModifyProductScreen(inv, selected);

            loader.setController(controller);
            Parent view = loader.load();
            Scene scene = new Scene(view);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    void searchParts(MouseEvent event) {
        if (!partsSearchBox.getText().trim().isEmpty()) {
            partsInvSearch.clear();
            for (Part i : inv.getAllParts()) {
                if (i.getName().contains(partsSearchBox.getText().trim())) {
                    partsInvSearch.add(i);
                }
            }
            partsTable.setItems(partsInvSearch);
            partsTable.refresh();
        }
    }

    @FXML
    void searchProducts(MouseEvent event) {
        if (!productsSearchBox.getText().trim().isEmpty()) {
            productsInvSearch.clear();
            for (Product i : inv.getAllProducts()) {
                if (i.getName().contains(productsSearchBox.getText().trim())) {
                    productsInvSearch.add(i);
                }
            }
            productsTable.setItems(productsInvSearch);
            productsTable.refresh();
        }
    }

    @FXML
    void clearFieldParts(MouseEvent event) {
        partsSearchBox.clear();
    }

    @FXML
    void clearFieldProducts(MouseEvent event) {
        productsSearchBox.clear();
    }

    public MainScreen(Inventory inv) {
        this.inv = inv;
    }

    private void errorWindow(int code) {
        if (code == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setHeaderText("Field is Empty.");
            alert.setContentText("No Item To Be Selected.");
            alert.showAndWait();
        }
        if (code == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setHeaderText("Selection Not Valid.");
            alert.setContentText("Item Must Be Selected.");
            alert.showAndWait();
        }

    }

}
