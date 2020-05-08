package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.application.Platform;
import javafx.beans.binding.ObjectExpression;
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

    void createPartsTable() {
        partsInv.setAll(inv.getAllParts());
        TableColumn<Part, Double> costCol = composePrice();
        partsTable.getColumns().addAll(costCol);
        partsTable.setItems(partsInv);
        partsTable.refresh();

    }

    void createProductsTable() {
        productsInv.setAll(inv.getAllProducts());
        TableColumn<Product, Double> costCol = composePrice();
        productsTable.getColumns().addAll(costCol);
        productsTable.setItems(productsInv);
        productsTable.refresh();
    }

    private <J> TableColumn<J, Double> composePrice() {
        TableColumn<J, Double> costCol = new TableColumn("Price");
        costCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        // Format as currency
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
    private TableView<Part> partsTable;
    ObservableList<Part> partsInv = FXCollections.observableArrayList();
    ObservableList<Part> partsInvSearch = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

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
    private TableView<Product> productsTable;
    ObservableList<Product> productsInv = FXCollections.observableArrayList();
    ObservableList<Product> productsInvSearch = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInvCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

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
    void modifyParts(MouseEvent event) throws IOException

    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyPartScreen.fxml"));
        ModifyPartScreen controller = new ModifyPartScreen(inv);

        loader.setController(controller);
        Parent view = loader.load();
        Scene scene = new Scene(view);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void modifyProducts(MouseEvent event)  throws IOException

    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyProductScreen.fxml"));
        ModifyProductScreen controller = new ModifyProductScreen(inv);

        loader.setController(controller);
        Parent view = loader.load();
        Scene scene = new Scene(view);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

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
}
