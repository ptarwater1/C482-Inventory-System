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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPart implements Initializable {

    private Inventory inv;
    private Part part;

    @FXML
    private RadioButton inHouse;
    @FXML
    private RadioButton outSourced;
    @FXML
    private Label companyLabel;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private TextField amount;
    @FXML
    private TextField max;
    @FXML
    private TextField min;
    @FXML
    private TextField company;
    @FXML
    private Button modifyPartSaveButton;

    ModifyPart(Inventory inv, Part part) {
        this.inv = inv;
        this.part = part;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listData();
    }

    private void listData() {

        if (part instanceof InHouse) {

            InHouse part0 = (InHouse) part;
            inHouse.setSelected(true);
            companyLabel.setText("Machine ID");
            this.name.setText(part0.getName());
            this.id.setText((Integer.toString(part0.getPartId())));
            this.amount.setText((Integer.toString(part0.getPartStock())));
            this.price.setText((Double.toString(part0.getPrice())));
            this.min.setText((Integer.toString(part0.getMin())));
            this.max.setText((Integer.toString(part0.getMax())));
            this.company.setText((Integer.toString(part0.getMachineId())));

        }

        if (part instanceof OutSourced) {

            OutSourced part1 = (OutSourced) part;
            outSourced.setSelected(true);
            companyLabel.setText("Company Name");
            this.name.setText(part1.getName());
            this.id.setText((Integer.toString(part1.getPartId())));
            this.amount.setText((Integer.toString(part1.getPartStock())));
            this.price.setText((Double.toString(part1.getPrice())));
            this.min.setText((Integer.toString(part1.getMin())));
            this.max.setText((Integer.toString(part1.getMax())));
            this.company.setText(part1.getCompanyName());
        }
    }

    @FXML
    private void clearTextField(MouseEvent event
    ) {
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
    }

    @FXML
    private void selectInHouse(MouseEvent event
    ) {
        companyLabel.setText("Machine ID");

    }

    @FXML
    private void selectOutSourced(MouseEvent event
    ) {
        companyLabel.setText("Company Name");

    }

    @FXML
    private void idDisabled(MouseEvent event
    ) {
    }

    @FXML
    private void cancelModifyPart(MouseEvent event
    ) {
        boolean cancel = AlertMessage.cancel();
        if (cancel) {
            mainScreen(event);
        }
    }

    @FXML
    private void saveModifyPart(MouseEvent event
    ) {
        resetFieldsStyle();
        boolean end = false;
        TextField[] fieldCount = {amount, price, min, max};
        if (inHouse.isSelected() || outSourced.isSelected()) {
            for (TextField textField : fieldCount) {
                boolean valueError = checkValue(textField);
                if (valueError) {
                    end = true;
                    break;
                }
                boolean typeError = checkType(textField);
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
            } else if (outSourced.isSelected() && company.getText().trim().isEmpty()) {
                AlertMessage.errorPart(0, company);
                return;
            } else if (inHouse.isSelected() && !company.getText().matches("[0-9]*")) {
                AlertMessage.errorPart(8, company);
                return;

            } else if (inHouse.isSelected() & part instanceof InHouse) {
                updateItemInHouse();

            } else if (inHouse.isSelected() & part instanceof OutSourced) {
                updateItemInHouse();
            } else if (outSourced.isSelected() & part instanceof OutSourced) {
                updateItemOutSourced();
            } else if (outSourced.isSelected() & part instanceof InHouse) {
                updateItemOutSourced();
            }

        } else {
            AlertMessage.errorPart(7, null);
            return;

        }
        mainScreen(event);
    }

    private void updateItemInHouse() {
        inv.updatePart(new InHouse(Integer.parseInt(id.getText().trim()), name.getText().trim(),
                Double.parseDouble(price.getText().trim()), Integer.parseInt(amount.getText().trim()),
                Integer.parseInt(min.getText().trim()), Integer.parseInt(max.getText().trim()), Integer.parseInt(company.getText().trim())));
    }

    private void updateItemOutSourced() {
        inv.updatePart(new OutSourced(Integer.parseInt(id.getText().trim()), name.getText().trim(),
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
        } catch (IOException ignored) {

        }
    }

    private boolean checkValue(TextField field) {
        boolean error = false;
        try {
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
