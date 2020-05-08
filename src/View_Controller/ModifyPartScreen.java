package View_Controller;

import Model.Inventory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ModifyPartScreen implements Initializable {

    Inventory inv;

    @FXML
    private RadioButton inHouse;

    @FXML
    private RadioButton outSourced;

    @FXML
    private ToggleGroup sourceToggle;

    @FXML
    private Label companyName;

    @FXML
    private Button modPartSaveButton;

    @FXML
    private Button modPartCancelButton;

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
    private TextField company;

    @FXML
    void cancelModifyPart(MouseEvent event) throws IOException

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
    void selectInHouse(MouseEvent event) {
        companyName.setText("Machine ID");
        company.setPromptText("Machine ID");
    }

    @FXML
    void selectOutSourced(MouseEvent event) {
        companyName.setText("Company Name");
        company.setPromptText("Company");
    }

    public ModifyPartScreen(Inventory inv) {
        this.inv = inv;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}