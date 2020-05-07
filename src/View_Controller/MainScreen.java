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
import java.util.ResourceBundle;

public class MainScreen implements Initializable {

    Inventory inv;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
    private TableView<?> partsTable;

    @FXML
    private TableColumn<?, ?> partIdCol;

    @FXML
    private TableColumn<?, ?> partNameCol;

    @FXML
    private TableColumn<?, ?> partInvCol;

    @FXML
    private TableColumn<?, ?> partPriceCol;

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
    private TableView<?> productsTable;

    @FXML
    private TableColumn<?, ?> productIdCol;

    @FXML
    private TableColumn<?, ?> productNameCol;

    @FXML
    private TableColumn<?, ?> productInvCol;

    @FXML
    private TableColumn<?, ?> productPriceCol;

    @FXML
    private Button exitProgramButton;

    @FXML
    void addParts(MouseEvent event) {

    }

    @FXML
    void addProducts(MouseEvent event) {

    }

    @FXML
    void deleteParts(MouseEvent event) {

    }

    @FXML
    void deleteProducts(MouseEvent event) {

    }

    @FXML
    void exitProgram(MouseEvent event) {

    }

    @FXML
    void modifyParts(MouseEvent event) {

    }

    @FXML
    void modifyProducts(MouseEvent event) {

    }

    @FXML
    void searchParts(MouseEvent event) {

    }

    @FXML
    void searchProducts(MouseEvent event) {

    }

    public MainScreen(Inventory inv) {
        this.inv = inv;
    }
}
