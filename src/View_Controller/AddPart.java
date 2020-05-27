package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class AddPart implements Initializable {

    private Inventory inv;

    @FXML
    private RadioButton inHouse;
    @FXML
    private RadioButton outSourced;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField amount;
    @FXML
    private TextField price;
    @FXML
    private TextField max;
    @FXML
    private TextField min;
    @FXML
    private TextField company;
    @FXML
    private Label companyLabel;

    AddPart(Inventory inv) {
        this.inv = inv;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assignPartID();
        resetFields();
    }

    private void resetFields() {
        name.setText("Part Name");
        amount.setText("Inv Count");
        price.setText("Part Price");
        max.setText("Max");
        min.setText("Min");
        company.setText("Machine ID");
        companyLabel.setText("Machine ID");
        inHouse.setSelected(true);
    }

    private void assignPartID() {
        boolean match;
        Random randomNum = new Random();
        Integer num = randomNum.nextInt(500);

        if (inv.partListSize() == 0) {
            id.setText(num.toString());

        }
        if (inv.partListSize() == 500) {
            AlertMessage.errorPart(1, null);
        } else {
            match = createNum(num);

            if (!match) {
                id.setText(num.toString());
            } else {
                assignPartID();
            }
        }
    }

    private boolean createNum(Integer num) {
        Part match = inv.findPart(num);
        return match != null;
    }

    @FXML
    private void clearTextField(MouseEvent event) {
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
    }

    @FXML
    private void selectInHouse(MouseEvent event) {
        companyLabel.setText("Machine ID");
        company.setText("Machine ID");
    }

    @FXML
    private void selectOutSourced(MouseEvent event) {
        companyLabel.setText("Company Name");
        company.setText("Company Name");
    }

    @FXML
    private void idDisabled(MouseEvent event) {
    }

    @FXML
    private void cancelAddPart(MouseEvent event) {
        boolean cancel = AlertMessage.cancel();
        if (cancel) {
            mainScreen(event);
        }
    }

    @FXML
    private void saveAddPart(MouseEvent event) {
        resetFieldsStyle();
        boolean end = false;
        TextField[] fieldCount = {amount, price, min, max};
        if (inHouse.isSelected() || outSourced.isSelected()) {
            for (TextField fieldCount0 : fieldCount) {
                boolean valueError = checkValue(fieldCount0);
                if (valueError) {
                    end = true;
                    break;
                }
                boolean typeError = checkType(fieldCount0);
                if (typeError) {
                    end = true;
                    break;
                }
            }
            if (name.getText().trim().isEmpty() || name.getText().trim().toLowerCase().equals("part name")) {
                AlertMessage.errorPart(2, name);
                return;
            }
            if (Integer.parseInt(min.getText().trim()) > Integer.parseInt(max.getText().trim())) {
                AlertMessage.errorPart(6, min);
                return;
            }
            if (Integer.parseInt(amount.getText().trim()) < Integer.parseInt(min.getText().trim())) {
                AlertMessage.errorPart(5, amount);
                return;
            }
            if (Integer.parseInt(amount.getText().trim()) > Integer.parseInt(max.getText().trim())) {
                AlertMessage.errorPart(4, amount);
                return;
            }
            if (end) {
                return;
            } else if (company.getText().trim().isEmpty() || company.getText().trim().toLowerCase().equals("company name")) {
                AlertMessage.errorPart(1, company);
                return;

            } else if (inHouse.isSelected() && !company.getText().trim().matches("[0-9]*")) {
                AlertMessage.errorPart(1, company);
                return;
            } else if (inHouse.isSelected()) {
                addInHouse();

            } else if (outSourced.isSelected()) {
                addOutSourced();

            }

        } else {
            AlertMessage.errorPart(7, null);
            return;

        }
        mainScreen(event);
    }

    private void addInHouse() {
        inv.addPart(new InHouse(Integer.parseInt(id.getText().trim()), name.getText().trim(),
                Double.parseDouble(price.getText().trim()), Integer.parseInt(amount.getText().trim()),
                Integer.parseInt(min.getText().trim()), Integer.parseInt(max.getText().trim()), (Integer.parseInt(company.getText().trim()))));

    }

    private void addOutSourced() {
        inv.addPart(new OutSourced(Integer.parseInt(id.getText().trim()), name.getText().trim(),
                Double.parseDouble(price.getText().trim()), Integer.parseInt(amount.getText().trim()),
                Integer.parseInt(min.getText().trim()), Integer.parseInt(max.getText().trim()), company.getText().trim()));

    }

    private void resetFieldsStyle() {
        name.setStyle("-fx-border-color: silver");
        amount.setStyle("-fx-border-color: silver");
        price.setStyle("-fx-border-color: silver");
        max.setStyle("-fx-border-color: silver");
        min.setStyle("-fx-border-color: silver");
        company.setStyle("-fx-border-color: silver");
    }

    private void mainScreen(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
            MainScreen controller = new MainScreen(inv);

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

    private boolean checkValue(TextField field) {
        boolean error = false;
        try {
            field.getText();
            if (field.getText().trim().isEmpty()) {
                AlertMessage.errorPart(0, field);
                return true;
            }
            if (field == price && Double.parseDouble(field.getText().trim()) < 0) {
                AlertMessage.errorPart(3, field);
                error = true;
            }
        } catch (Exception e) {
            error = true;
            AlertMessage.errorPart(1, field);
            System.out.println(e);

        }
        return error;
    }

    private boolean checkType(TextField field) {

        if (field == price & !field.getText().trim().matches("\\d+(\\.\\d+)?")) {
            AlertMessage.errorPart(1, field);
            return true;
        }
        if (field != price & !field.getText().trim().matches("[0-9]*")) {
            AlertMessage.errorPart(1, field);
            return true;
        }
        return false;
    }

}
