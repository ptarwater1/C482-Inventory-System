package View_Controller;

import Model.Inventory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartScreen implements Initializable {

        Inventory inv;

        @FXML
        private RadioButton inHouse;

        @FXML
        private RadioButton outSourced;

        @FXML
        private Button saveButton;

        @FXML
        private Button cancelAddPartButton;

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
        void cancelAddPart(MouseEvent event) {

        }

        @FXML
        void clearTextField(MouseEvent event) {

        }

        @FXML
        void idDisabled(MouseEvent event) {

        }

        @FXML
        void saveAddPart(MouseEvent event) {

        }

        @FXML
        void selectInHouse(MouseEvent event) {

        }

        @FXML
        void selectOutSourced(MouseEvent event) {

        }

        public AddPartScreen(Inventory inv) {
            this.inv = inv;
        }

        @Override
        public void initialize(URL url, ResourceBundle rb) {
        }


}
