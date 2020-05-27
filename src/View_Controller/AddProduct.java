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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class AddProduct implements Initializable {

    private Inventory inv;

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
    private TextField search;
    @FXML
    private TableView<Part> partSearchTable;
    @FXML
    private TableView<Part> assocPartsTable;
    private ObservableList<Part> partsInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Part> assocPartList = FXCollections.observableArrayList();

    AddProduct(Inventory inv) {
        this.inv = inv;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assignProductId();
        createTable();
    }

    private void assignProductId() {
        boolean match;
        Random randomNum = new Random();
        Integer num = randomNum.nextInt(1000);


        if (inv.productListSize() == 0) {
            id.setText(num.toString());

        }
        if (inv.productListSize() == 1000) {
            AlertMessage.errorProduct(1, null);
        } else {
            match = assignNum(num);

            if (!match) {
                id.setText(num.toString());
            } else {
                assignProductId();
            }
        }

        id.setText(num.toString());
    }

    private boolean assignNum(Integer num) {
        Part match = inv.findPart(num);
        return match != null;
    }


    private void createTable() {
        partsInventory.setAll(inv.getAllParts());
        TableColumn<Part, Integer> partIdCol = composePartId();
        TableColumn<Part, String> nameCol = composeName();
        TableColumn<Part, Integer> partStockCol = composePartStock();
        TableColumn<Part, Double> costCol = composePrice();
        partSearchTable.getColumns().addAll(partIdCol, nameCol, partStockCol,costCol);

        partSearchTable.setItems(partsInventory);
        partSearchTable.refresh();
    }

    @FXML
    private void clearTextField(MouseEvent event) {
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
    }

    @FXML
    private void searchForPart(MouseEvent event) {
        if (search.getText() != null && search.getText().trim().length() != 0) {
            partsInventorySearch.clear();
            for (Part p : inv.getAllParts()) {
                if (p.getName().contains(search.getText().trim())) {
                    partsInventorySearch.add(p);
                }
            }
            partSearchTable.setItems(partsInventorySearch);
        }
    }

    @FXML
    private void addPart(MouseEvent event) {
        Part addPart = partSearchTable.getSelectionModel().getSelectedItem();
        boolean repeatedItem = false;

        if (addPart != null) {
            int id = addPart.getPartId();
            for (Part part : assocPartList) {
                if (part.getPartId() == id) {
                    AlertMessage.errorProduct(9, null);
                    repeatedItem = true;
                }
            }

            if (!repeatedItem) {
                assocPartList.add(addPart);

            }
            TableColumn<Part, Integer> partIdCol = composePartId();
            TableColumn<Part, String> nameCol = composeName();
            TableColumn<Part, Integer> partStockCol = composePartStock();
            TableColumn<Part, Double> costCol = composePrice();
            assocPartsTable.getColumns().addAll(partIdCol, nameCol, partStockCol,costCol);

            assocPartsTable.setItems(assocPartList);
        }
    }

    @FXML
    private void deletePart(MouseEvent event
    ) {
        Part deletedPart = assocPartsTable.getSelectionModel().getSelectedItem();

        if (deletedPart != null) {
            boolean remove = AlertMessage.confirmationWindow(deletedPart.getName());
            if (remove) {
                assocPartList.remove(deletedPart);
                assocPartsTable.refresh();
            }
        } else {
            return;
        }
        AlertMessage.infoWindow(1, "");

    }


    @FXML
    private void cancelAddProduct(MouseEvent event
    ) {
        boolean cancel = AlertMessage.cancel();
        if (cancel) {
            mainScreen(event);
        }
    }

    @FXML
    private void saveAddProduct(MouseEvent event
    ) {
        resetFieldsStyle();
        boolean end = false;
        TextField[] fieldCount = {amount, price, min, max};
        double minCost = 0;
        for (int i = 0; i < assocPartList.size(); i++) {
            minCost += assocPartList.get(i).getPrice();
        }
        if (name.getText().trim().isEmpty() || name.getText().trim().toLowerCase().equals("part name")) {
            AlertMessage.errorProduct(2, name);
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
            AlertMessage.errorProduct(6, min);
            return;
        }
        if (Integer.parseInt(amount.getText().trim()) < Integer.parseInt(min.getText().trim())) {
            AlertMessage.errorProduct(5, amount);
            return;
        }
        if (Integer.parseInt(amount.getText().trim()) > Integer.parseInt(max.getText().trim())) {
            AlertMessage.errorProduct(4, amount);
            return;
        }
        if (Double.parseDouble(price.getText().trim()) < minCost) {
            AlertMessage.errorProduct(7, price);
            return;
        }
        if (assocPartList.isEmpty()) {
            AlertMessage.errorProduct(8, null);
            return;
        }

        saveProduct();
        mainScreen(event);

    }

    private void fieldError(TextField field) {
        if (field != null) {
            field.setStyle("-fx-border-color: darkred");
        }
    }

    private void saveProduct() {
        Product product = new Product(Integer.parseInt(id.getText().trim()), name.getText().trim(), Double.parseDouble(price.getText().trim()),
                Integer.parseInt(amount.getText().trim()), Integer.parseInt(min.getText().trim()), Integer.parseInt(max.getText().trim()));
        for (int i = 0; i < assocPartList.size(); i++) {
            product.assocPartAdd(assocPartList.get(i));
        }

        inv.addProduct(product);

    }

    private void resetFieldsStyle() {
        name.setStyle("-fx-border-color: silver");
        amount.setStyle("-fx-border-color: silver");
        price.setStyle("-fx-border-color: silver");
        max.setStyle("-fx-border-color: silver");
        min.setStyle("-fx-border-color: silver");
    }

    @FXML
    void clearField(MouseEvent event) {
        search.setText("");
        if (!partsInventory.isEmpty()) {
            partSearchTable.setItems(partsInventory);
        }

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
            if (field.getText().trim().isEmpty()) {
                AlertMessage.errorProduct(0, field);
                return true;
            }
            if (field == price && Double.parseDouble(field.getText().trim()) < 0) {
                AlertMessage.errorProduct(3, field);
                error = true;
            }
        } catch (NumberFormatException e) {
            error = true;
            AlertMessage.errorProduct(1, field);
            System.out.println(e);

        }
        return error;
    }

    private boolean checkType(TextField field) {

        if (field == price & !field.getText().trim().matches("\\d+(\\.\\d+)?")) {
            AlertMessage.errorProduct(1, field);
            return true;
        }
        if (field != price & !field.getText().trim().matches("[0-9]*")) {
            AlertMessage.errorProduct(1, field);
            return true;
        }
        return false;

    }

    private <F> TableColumn<F, Integer> composePartId() {
        TableColumn<F, Integer> partIdCol = new TableColumn("Part ID");
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        partIdCol.setPrefWidth(100);
        partIdCol.setCellFactory((TableColumn<F, Integer> column) -> {
            return new TableCell<F, Integer>() {
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

    private <G> TableColumn<G, Integer> composeProductId() {
        TableColumn<G, Integer> productIdCol = new TableColumn("Product ID");
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productIdCol.setPrefWidth(100);
        productIdCol.setCellFactory((TableColumn<G, Integer> column) -> {
            return new TableCell<G, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    if (!empty) {
                        setText(String.format("%d", item));
                    }
                }
            };
        });
        return productIdCol;
    }

    private <H> TableColumn<H, String> composeName() {
        TableColumn<H, String> nameCol = new TableColumn("Part Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(100);
        nameCol.setCellFactory((TableColumn<H, String> column) -> {
            return new TableCell<H, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    if (!empty) {
                        setText(String.format("%s", item));
                    }
                }
            };
        });
        return nameCol;
    }

    private <I> TableColumn<I, Integer> composeStock() {
        TableColumn<I, Integer> stockCol = new TableColumn("Inventory Level");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockCol.setPrefWidth(100);
        stockCol.setCellFactory((TableColumn<I, Integer> column) -> {
            return new TableCell<I, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    if (!empty) {
                        setText(String.format("%d", item));
                    }
                }
            };
        });
        return stockCol;
    }

    private <J> TableColumn<J, Double> composePrice() {
        TableColumn<J, Double> costCol = new TableColumn("Price");
        costCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        costCol.setPrefWidth(100);
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

    private <K> TableColumn<K, String> composePrName() {
        TableColumn<K, String> namePrCol = new TableColumn("Product Name");
        namePrCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        namePrCol.setPrefWidth(100);
        namePrCol.setCellFactory((TableColumn<K, String> column) -> {
            return new TableCell<K, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    if (!empty) {
                        setText(String.format("%s", item));
                    }
                }
            };
        });
        return namePrCol;
    }

    private <L> TableColumn<L, Integer> composePartStock() {
        TableColumn<L, Integer> partStockCol = new TableColumn("Inventory Level");
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        partStockCol.setPrefWidth(100);
        partStockCol.setCellFactory((TableColumn<L, Integer> column) -> {
            return new TableCell<L, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    if (!empty) {
                        setText(String.format("%d", item));
                    }
                }
            };
        });
        return partStockCol;
    }


}
