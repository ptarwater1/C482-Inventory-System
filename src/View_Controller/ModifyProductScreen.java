package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.event.Event;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductScreen  implements Initializable {

    Inventory inv;
    Product product;

    private ObservableList<Part> partsInv = FXCollections.observableArrayList();
    private ObservableList<Part> partsInvSearch = FXCollections.observableArrayList();
    private ObservableList<Part> assocPartList = FXCollections.observableArrayList();

    public ModifyProductScreen(Inventory inv, Product product) {
        this.inv = inv;
        this.product = product;

    }

    private TextField find;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillSearch();
        listData();
    }

    private void fillSearch() {
        partsInv.setAll(inv.getAllParts());
        TableColumn<Part, Integer> partIdCol = composePartId();
        TableColumn<Part, String> nameCol = composeName();
        TableColumn<Part, Integer> stockCol = composeStock();
        TableColumn<Part, Double> costCol = composePrice();
        partsTableSearch.getColumns().addAll(partIdCol, nameCol, stockCol, costCol);
        partsTableSearch.setItems(partsInv);
        partsTableSearch.refresh();
    }

    private void listData() {
        for (int i = 0; i < 500; i++) {
            Part part = product.getAllAssociatedParts(i);
            if (part != null) {
                assocPartList.add(part);
            }
        }
        TableColumn<Part, Integer> partIdCol = composePartId();
        TableColumn<Part, String> nameCol = composeName();
        TableColumn<Part, Integer> stockCol = composeStock();
        TableColumn<Part, Double> costCol = composePrice();
        assocPartsTable.getColumns().addAll(partIdCol, nameCol, stockCol, costCol);
        assocPartsTable.setItems(assocPartList);

        this.name.setText(product.getName());
        this.id.setText((Integer.toString(product.getProductId())));
        this.inventory.setText((Integer.toString(product.getStock())));
        this.price.setText(Double.toString( product.getPrice()));
        this.min.setText((Integer.toString(product.getMin())));
        this.max.setText((Integer.toString(product.getMax())));

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
    private TableView<Part> partsTableSearch;

    @FXML
    private Button modProdDeleteButton;

    @FXML
    private TableView<Part> assocPartsTable;

    @FXML
    private Button modProdSaveButton;

    @FXML
    private Button modProdCancelButton;

    @FXML
    private void modProdSearch(MouseEvent event) {
        if (find.getText() != null && find.getText().trim().length() != 0) {
            partsInvSearch.clear();
            for (Part i : inv.getAllParts()) {
                if (i.getName().contains(find.getText().trim())) {
                    partsInvSearch.add(i);
                }
            }
            partsTableSearch.setItems(partsInvSearch);
        }
    }

    @FXML
    void addParts(MouseEvent event) {
        Part addPart = partsTableSearch.getSelectionModel().getSelectedItem();
        boolean repeatedItem = false;

        if (addPart == null) {
            return;
        } else {
            int id = addPart.getPartId();
            for (int i = 0; i < assocPartList.size(); i++) {
                if (assocPartList.get(i).getPartId() == id) {
                    AlertMessage.errorProduct(1, null);
                    repeatedItem = true;
                }
            }

            if (!repeatedItem) {
                assocPartList.add(addPart);
            }
            assocPartsTable.setItems(assocPartList);
        }
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
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.clear();
        if (field == find) {
            partsTableSearch.setItems(partsInv);
        }
    }

    @FXML
    void deleteParts(MouseEvent event) {
        Part delPart = assocPartsTable.getSelectionModel().getSelectedItem();
        boolean deleted = false;
        if (delPart != null) {
            boolean remove = confirmationWindow(delPart.getName());
            if (remove) {
                deleted = product.deleteAssociatedPart(delPart.getPartId());
                assocPartList.remove(delPart);
                assocPartsTable.refresh();
            }
        } else {
            return;
        }
        if (deleted) {
            AlertMessage.infoWindow(0, delPart.getName());
        } else {
            AlertMessage.infoWindow(1, "");
        }

    }

    private boolean confirmationWindow(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove part");
        alert.setHeaderText("Remove: " + name);
        alert.setContentText("OK to confirm");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    private void mainScreen(Event event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainScreen.fxml"));
        MainScreen controller = new MainScreen(inv);

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void modProdSave(MouseEvent event) throws IOException {
        Product product = new Product(Integer.parseInt(id.getText().trim()), name.getText().trim(), Double.parseDouble(price.getText().trim()),
                Integer.parseInt(inventory.getText().trim()), Integer.parseInt(min.getText().trim()), Integer.parseInt(max.getText().trim()));
        for (int i = 0; i < assocPartList.size(); i++) {
            product.addAssociatedPart(assocPartList.get(i));
        }
        inv.updateProduct(product);

        }


    @FXML
    void searchParts(MouseEvent event) {

    }



    private <F> TableColumn<F, Integer> composePartId() {
        TableColumn<F, Integer> partIdCol = new TableColumn("Part ID");
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        partIdCol.setCellFactory((TableColumn<F, Integer> column) -> {
            return new TableCell<F, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    if (!empty) {
                        setText(String.format("%s", item));
                    }
                }
            };
        });
        return partIdCol;
    }

    private <G> TableColumn<G, Integer> composeProductId() {
        TableColumn<G, Integer> productIdCol = new TableColumn("Product ID");
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productIdCol.setCellFactory((TableColumn<G, Integer> column) -> {
            return new TableCell<G, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    if (!empty) {
                        setText(String.format("%s", item));
                    }
                }
            };
        });
        return productIdCol;
    }

    private <H> TableColumn<H, String> composeName() {
        TableColumn<H, String> nameCol = new TableColumn("Part Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
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