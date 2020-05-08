package View_Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.Optional;

public class AlertMessage {

    public static void errorPart(int code, TextField field) {
        fieldError(field);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Add Part Error.");
        alert.setHeaderText("Add Part Failed.");
        switch (code) {
            case 0: {
                alert.setContentText("Field is empty.");
                break;
            }
            case 1: {
                alert.setContentText("In House or Outsourced must be selected.");
                break;
            }
            case 2: {
                alert.setContentText("Format is invalid.");
                break;
            }
            case 3: {
                alert.setContentText("Name is invalid.");
                break;
            }
            case 4: {
                alert.setContentText("Negative Value cannot be used.");
                break;
            }
            case 5: {
                alert.setContentText("Inventory lower than min.");
                break;
            }
            case 6: {
                alert.setContentText("Inventory higher than max.");
                break;
            }
            case 7: {
                alert.setContentText("Min higher than max.");
                break;
            }
            case 8: {
                alert.setContentText("Number must be used for Machine ID.");
                break;
            }
            default: {
                alert.setContentText("Error Unknown.");
                break;
            }
        }
        alert.showAndWait();
    }

    public static void errorProduct(int code, TextField field) {
        fieldError(field);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error adding product");
        alert.setHeaderText("Cannot add product");
        switch (code) {
            case 0: {
                alert.setContentText("Field is empty.");
                break;
            }
            case 1: {
                alert.setContentText("Part associated with selected product.");
                break;
            }
            case 2: {
                alert.setContentText("Format is invalid.");
                break;
            }
            case 3: {
                alert.setContentText("Name is invalid.");
                break;
            }
            case 4: {
                alert.setContentText("Negative Value cannot be used.");
                break;
            }
            case 5: {
                alert.setContentText("Product cost cannot be lower than it's parts!");
                break;
            }
            case 6: {
                alert.setContentText("Product needs at least one part.");
                break;
            }
            case 7: {
                alert.setContentText("Inventory lower than min.");
                break;
            }
            case 8: {
                alert.setContentText("Inventory higher than max.");
                break;
            }
            case 9: {
                alert.setContentText("Min higher than max.");
                break;
            }
            default: {
                alert.setContentText("Error Unknown.");
                break;
            }
        }
        alert.showAndWait();
    }

    private static void fieldError(TextField field) {
        if (field != null) {
            field.setStyle("-fx-border-color: darkred");
        }
    }

    public static boolean confirmationWindow(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Part Delete");
        alert.setHeaderText("Delete " + name + "?");
        alert.setContentText("OK to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static boolean cancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Cancel?");
        alert.setContentText("OK to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void infoWindow(int code, String name) {
        if (code != 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmed");
            alert.setHeaderText(null);
            alert.setContentText(name + " deleted.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error has occurred.");
        }
    }

}
