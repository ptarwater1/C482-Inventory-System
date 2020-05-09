package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Random;
import java.util.ResourceBundle;

public class AddProductScreen implements Initializable {

    Inventory inv;

    public AddProductScreen(Inventory inv) {
        this.inv = inv;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createProductId();
        fillSearchTable();
    }

    private void createProductId() {
        boolean match;
        Random randomNum = new Random();
        Integer num = randomNum.nextInt(500);

        if (inv.productIndexSize() == 0) {
            id.setText(num.toString());
        }
        if (inv.productIndexSize() == 500) {
            AlertMessage.errorProduct(2, null);
        } else {
            match = createNum(num);

            if(!match) {
                id.setText((num.toString()));
            } else {
                createProductId();
            }
        }
        id.setText(num.toString());
    }

    private boolean createNum(Integer num) {
        Part match = inv.lookupPart(num);
        return match != null;
    }

    private void fillSearchTable() {
        partsInv.setAll(inv.getAllParts());

        TableColumn<Part, Double> costCol = composePrice();
        partsTable.getColumns().addAll(costCol);

        partsTable.setItems(partsInv);
        partsTable.refresh();
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
    private TextField addProductsSearchBox;

    @FXML
    private Button addProdSearchButton;

    @FXML
    private Button addProdAddButton;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, ?> addProdPartIdCol;

    @FXML
    private TableColumn<Part, ?> addProdPartNameCol;

    @FXML
    private TableColumn<Part, ?> addProdInvCol;

    @FXML
    private TableColumn<Part, ?> addProdPartPriceCol;

    @FXML
    private Button addProdDeleteButton;

    @FXML
    private TableView<Part> assocPartsTable;

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
        Part addPart = partsTable.getSelectionModel().getSelectedItem();
        boolean repeatedItem = false;

        if (addPart != null) {
            int id = addPart.getPartId();
            for (int i = 0; i < assocPartIndex.size(); i++) {
                if (assocPartIndex.get(i).getPartId() == id) {
                    AlertMessage.errorProduct(1, null);
                    repeatedItem = true;
                }
            }

            if (!repeatedItem) {
                assocPartIndex.add(addPart);

            }

            TableColumn<Part, Double> costCol = composePrice();
            assocPartsTable.getColumns().addAll(costCol);

            assocPartsTable.setItems(assocPartIndex);
        }

    }

    @FXML
    void cancelAddProducts(MouseEvent event)  throws IOException

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
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
    }

    @FXML
    void deleteParts(MouseEvent event) {
        Part deletePart = assocPartsTable.getSelectionModel().getSelectedItem();
        boolean deleted = false;
        if (deletePart != null) {
            boolean delete = AlertMessage.confirmationWindow(deletePart.getName());
            if (delete) {
                assocPartIndex.remove(deletePart);
                assocPartsTable.refresh();
            }
        } else {
            return;
        }
        if (deleted) {
            AlertMessage.infoWindow(0, deletePart.getName());
        } else {
            AlertMessage.infoWindow(1, "");
        }

    }

    @FXML
    void addProdSave(MouseEvent event) throws IOException {
        resetFieldsStyle();
        boolean end = false;
        TextField[] fieldCount = {inventory, price, min, max};
        double minCost = 0;
        for (int i = 0; i < assocPartIndex.size(); i++) {
            minCost += assocPartIndex.get(i).getPrice();
        }
        if (name.getText().trim().isEmpty() || name.getText().trim().toLowerCase().equals("part name")) {
            AlertMessage.errorProduct(3, name);
            return;
        }
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
        if (Integer.parseInt(min.getText().trim()) > Integer.parseInt(max.getText().trim())) {
            AlertMessage.errorProduct(9, min);
            return;
        }
        if (Integer.parseInt(inventory.getText().trim()) < Integer.parseInt(min.getText().trim())) {
            AlertMessage.errorProduct(7, inventory);
            return;
        }
        if (Integer.parseInt(inventory.getText().trim()) > Integer.parseInt(max.getText().trim())) {
            AlertMessage.errorProduct(8, inventory);
            return;
        }
        if (Double.parseDouble(price.getText().trim()) < minCost) {
            AlertMessage.errorProduct(5, price);
            return;
        }
        if (assocPartIndex.isEmpty()) {
            AlertMessage.errorProduct(6, null);
            return;
        }

        saveProduct();
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

    private void fieldError(TextField field) {
        if (field != null) {
            field.setStyle("-fx-border-color: darkred");
        }
    }

    private void saveProduct() {
        Product product = new Product();
        for (int i = 0; i < assocPartIndex.size(); i++) {
            product.addAssociatedPart(assocPartIndex.get(i));
        }

        inv.addProduct(product);

    }

    private void resetFieldsStyle() {
        name.setStyle("-fx-border-color: silver");
        inventory.setStyle("-fx-border-color: silver");
        price.setStyle("-fx-border-color: silver");
        min.setStyle("-fx-border-color: silver");
        max.setStyle("-fx-border-color: silver");

    }

    @FXML
    void searchParts(MouseEvent event) {
        if (addProductsSearchBox.getText() != null && addProductsSearchBox.getText().trim().length() != 0) {
            partsInvSearch.clear();
            for (Part i : inv.getAllParts()) {
                if (i.getName().contains(addProductsSearchBox.getText().trim())) {
                    partsInvSearch.add(i);
                }
            }
            partsTable.setItems(partsInvSearch);
        }

    }

    private ObservableList<Part> partsInv = FXCollections.observableArrayList();

    private ObservableList<Part> partsInvSearch = FXCollections.observableArrayList();

    private ObservableList<Part> assocPartIndex = FXCollections.observableArrayList();

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
