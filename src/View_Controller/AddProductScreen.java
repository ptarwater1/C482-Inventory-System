package View_Controller;

import Model.Inventory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class AddProductScreen implements Initializable {

    Inventory inv;

    public AddProductScreen(Inventory inv) {
        this.inv = inv;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generateProductId();
        popSearchTable();
    }

    private void generateProductId() {
        boolean match;
        Random randomNum = new Random();
        Integer num = randomNum.nextInt(500);
    }

    private void popSearchTable() {
    }

    @FXML
    private TextField name;

    @FXML
    private TextField Inventory;

    @FXML
    private TextField Price;

    @FXML
    private TextField Max;

    @FXML
    private TextField Min;

    @FXML
    private TextField addProductsSearchBox;

    @FXML
    private Button addProdSearchButton;

    @FXML
    private Button addProdAddButton;

    @FXML
    private TableView<?> partsTable;

    @FXML
    private TableColumn<?, ?> addProdPartIdCol;

    @FXML
    private TableColumn<?, ?> addProdPartNameCol;

    @FXML
    private TableColumn<?, ?> addProdInvCol;

    @FXML
    private TableColumn<?, ?> addProdPartPriceCol;

    @FXML
    private Button addProdDeleteButton;

    @FXML
    private TableColumn<?, ?> addProdPartIdColAssoc;

    @FXML
    private TableColumn<?, ?> addProdPartNameColAssoc;

    @FXML
    private TableColumn<?, ?> addProdInvColAssoc;

    @FXML
    private TableColumn<?, ?> addProdPartPriceColAssoc;

    @FXML
    private Button addProductsCancelButton;

    @FXML
    private Button addProdSaveButton;

    @FXML
    void addParts(MouseEvent event) {

    }

    @FXML
    void cancelAddProducts(MouseEvent event) {

    }

    @FXML
    void clearTextField(MouseEvent event) {

    }

    @FXML
    void deleteParts(MouseEvent event) {

    }

    @FXML
    void saveAddParts(MouseEvent event) {

    }

    @FXML
    void searchParts(MouseEvent event) {

    }



}