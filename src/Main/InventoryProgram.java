package Main;

import Model.*;
import View_Controller.MainScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryProgram extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Inventory inv = new Inventory();
        addTestData(inv);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        MainScreen controller = new MainScreen(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    void addTestData(Inventory inv) {
        //In House Parts
        Part a1 = new InHouse(1, "Part A1", 1.89, 8, 100, 1, 105);
        Part a2 = new InHouse(2, "Part A2", 2.89, 7, 100, 1, 104);
        Part a3 = new InHouse(3, "Part A3", 3.89, 5, 100, 1, 105);
        Part a4 = new InHouse(4, "Part A4", 4.89, 8, 100, 1, 106);
        Part a5 = new InHouse(5, "Part A5", 5.89, 6, 100, 1, 103);
        inv.addPart(a1);
        inv.addPart(a2);
        inv.addPart(a3);
        inv.addPart(a4);
        inv.addPart(a5);

        //Out Sourced Parts
        Part b1 = new OutSourced(6, "Part B1", 1.99, 9, 100, 1, "ABC");
        Part b2 = new OutSourced(7, "Part B2", 2.99, 6, 100, 1, "DEF");
        Part b3 = new OutSourced(8, "Part B3", 3.99, 8, 100, 1, "DEF");
        Part b4 = new OutSourced(9, "Part B4", 4.99, 10, 100, 1, "ABC");
        Part b5 = new OutSourced(10, "Part B5", 5.99, 11, 100, 1, "GHI");
        inv.addPart(b1);
        inv.addPart(b2);
        inv.addPart(b3);
        inv.addPart(b4);
        inv.addPart(b5);

        //Products
        Product p1 = new Product(1, "Product P1", 10.19, 4, 100, 1);
        Product p2 = new Product(2, "Product P2", 15.29, 3, 100, 1);
        Product p3 = new Product(3, "Product P3", 20.39, 2, 100, 1);
        Product p4 = new Product(4, "Product P4", 25.49, 1, 100, 1);
        inv.addProduct(p1);
        inv.addProduct(p2);
        inv.addProduct(p3);
        inv.addProduct(p4);


        //Part and Product Association
        p1.assocPartAdd(a1);
        p1.assocPartAdd(b5);
        p2.assocPartAdd(a2);
        p2.assocPartAdd(b4);
        p3.assocPartAdd(a3);
        p3.assocPartAdd(b3);
        p3.assocPartAdd(b2);
        p4.assocPartAdd(a1);
        p4.assocPartAdd(b4);
        p4.assocPartAdd(b1);
    }

}
