package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import javafx.event.Event;
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
import java.util.ResourceBundle;

public class AddPartScreen implements Initializable {

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
        void cancelAddPart(MouseEvent event)  throws IOException

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
        void idDisabled(MouseEvent event) {

        }

        @FXML
        void addPartSave(MouseEvent event) throws IOException {
            resetFieldsStyle();
            boolean end = false;
            TextField[] fieldCount = {inventory, price, min, max};
            if (inHouse.isSelected() || outSourced.isSelected()) {
                for (TextField fieldCount1 : fieldCount) {
                    boolean valueError = checkValue(fieldCount1);
                    if (valueError) {
                        end = true;
                        break;
                    }
                    boolean typeError = checkType(fieldCount1);
                    if (typeError) {
                        end = true;
                        break;
                    }
                }
                if (name.getText().trim().isEmpty() || name.getText().trim().toLowerCase().equals("Name")) {
                    AlertMessage.errorPart(3, name);
                    return;
                }
                if (Integer.parseInt(min.getText().trim()) > Integer.parseInt(max.getText().trim())) {
                    AlertMessage.errorPart(7, min);
                    return;
                }
                if (Integer.parseInt(inventory.getText().trim()) < Integer.parseInt(min.getText().trim())) {
                    AlertMessage.errorPart(5, inventory);
                    return;
                }
                if (Integer.parseInt(inventory.getText().trim()) > Integer.parseInt(max.getText().trim())) {
                    AlertMessage.errorPart(6, inventory);
                    return;
                }
                if (end) {
                    return;
                } else if (company.getText().trim().isEmpty() || company.getText().trim().toLowerCase().equals("company name")) {
                    AlertMessage.errorPart(2, company);
                    return;

                } else if (inHouse.isSelected() && !company.getText().trim().matches("[0-9]*")) {
                    AlertMessage.errorPart(2, company);
                    return;
                } else if (inHouse.isSelected()) {
                    addInHouse();

                } else if (outSourced.isSelected()) {
                    addOutSourced();

                }

            } else {
                AlertMessage.errorPart(2, null);
                return;

            }
            mainScreen(event);
        }

    private void mainScreen(Event event) throws IOException
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

    private boolean checkType(TextField field) {

        if (field == price & !field.getText().trim().matches("\\d+(\\.\\d+)?")) {
            AlertMessage.errorPart(2, field);
            return true;
        }
        if (field != price & !field.getText().trim().matches("[0-9]*")) {
            AlertMessage.errorPart(2, field);
            return true;
        }
        return false;
    }

    private boolean checkValue(TextField field) {
        boolean error = false;
        {
            if (field.getText().trim().isEmpty() | (field.getText().trim() == null)) {
                AlertMessage.errorPart(0, field);
                return true;
            }
            if (field == price && Double.parseDouble(field.getText().trim()) < 0) {
                AlertMessage.errorPart(4, field);
                error = true;
            }
        }
            error = true;
            AlertMessage.errorPart(2, field);

        return error;
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

        public AddPartScreen(Inventory inv) {
            this.inv = inv;
        }


        @Override
        public void initialize(URL url, ResourceBundle rb) {
        }


    private void resetFieldsStyle() {
        name.setStyle("-fx-border-color: silver");
        inventory.setStyle("-fx-border-color: silver");
        price.setStyle("-fx-border-color: silver");
        min.setStyle("-fx-border-color: silver");
        max.setStyle("-fx-border-color: silver");
        company.setStyle("-fx-border-color: silver");
    }

    private void addInHouse() {
        inv.addPart(new InHouse(Integer.parseInt(id.getText().trim()), name.getText().trim(),
                Double.parseDouble(price.getText().trim()), Integer.parseInt(inventory.getText().trim()),
                Integer.parseInt(min.getText().trim()), Integer.parseInt(max.getText().trim()), (Integer.parseInt(company.getText().trim()))));

    }

    private void addOutSourced() {
        inv.addPart(new OutSourced(Integer.parseInt(id.getText().trim()), name.getText().trim(),
                Double.parseDouble(price.getText().trim()), Integer.parseInt(inventory.getText().trim()),
                Integer.parseInt(min.getText().trim()), Integer.parseInt(max.getText().trim()), company.getText().trim()));
    }

    private <G> TableColumn<G, Integer> composePartId() {
        TableColumn<G, Integer> partIdCol = new TableColumn("Part Id");
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("Part ID"));
        partIdCol.setCellFactory((TableColumn<G, Integer> column) -> {
            return new TableCell<G, Integer>() {
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

}
