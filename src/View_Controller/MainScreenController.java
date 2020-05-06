package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

import javax.swing.text.TableView;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    Inventory inv;

    @FXML
    private TextField searchPartsButton;
    @FXML
    private TextField searchProductsButton;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private  TableView<Product> productsTable;
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private  ObservableList<Product> productInventory = FXCollections.observableArrayList();



    public MainScreenController(Inventory inv) { this.inv = inv; }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generatePartsTable();
        generateProductsTable();
    }

    private void generatePartsTable() {
        partInventory.setAll(inv.getAllParts());
        TableColumn<Part, Double> costCol = formatPrice();
        partsTable.getColuimns().addAll(costCol);

        partsTable.setItems(partInventory);
        partsTable.refresh();
    }

    private void generateProductsTable() {
        partInventory.setAll(inv.getAllProducts());
        TableColumn<Product, Double> costCol = formatPrice();
        productsTable.getColumns().addAll(costCol);

        partsTable.setItems(productInventory);
        partsTable.refresh();
    }

    @FXML
    private void exitProgram(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void exitProgramButton(MouseEvent event ) {
        Platform.exit();
    }

    @FXML
    private void searchPart(M)

}
