package View_Controller;

import Model.Inventory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductScreen  implements Initializable {

    Inventory inv;

    public ModifyProductScreen(Inventory inv) {
        this.inv = inv;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField inventory;

    @FXML
    private TextField price;

    @FXML
    private TextField max;

    @FXML
    private TextField min;

    @FXML
    private TextField modifyProductSearch;

    @FXML
    private Button modProdSearchButton;

    @FXML
    private Button modProdAddButton;

    @FXML
    private TableView<?> partsTable;

    @FXML
    private TableColumn<?, ?> modProdPartIdCol;

    @FXML
    private TableColumn<?, ?> modProdPartNameCol;

    @FXML
    private TableColumn<?, ?> modProdInvCol;

    @FXML
    private TableColumn<?, ?> modProdPriceCol;

    @FXML
    private Button modProdDeleteButton;

    @FXML
    private TableColumn<?, ?> modProdPartIdColAssoc;

    @FXML
    private TableColumn<?, ?> modProdPartNameColAssoc;

    @FXML
    private TableColumn<?, ?> modProdInvColAssoc;

    @FXML
    private TableColumn<?, ?> modProdPriceColAssoc;

    @FXML
    private Button modProdSaveButton;

    @FXML
    private Button modProdCancelButton;

    @FXML
    void addParts(MouseEvent event) {

    }

    @FXML
    void cancelModify(MouseEvent event)  throws IOException

    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        MainScreen controller = new MainScreen(inv);

        loader.setController(controller);
        Parent view = loader.load();
        Scene scene = new Scene(view);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void clearTextField(MouseEvent event) {

    }

    @FXML
    void deleteParts(MouseEvent event) {

    }

    @FXML
    void saveProduct(MouseEvent event) {

    }

    @FXML
    void searchParts(MouseEvent event) {

    }

}
