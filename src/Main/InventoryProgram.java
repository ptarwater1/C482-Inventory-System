package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class InventoryProgram extends Application {

    public static void main(String[] args) {launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        Inventory inv = new Inventory();
        addSampleData(inv);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        View_Controller.MainScreen controller = new View_Controller.MainScreen(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    void addSampleData(Inventory inv) {
        //In House Parts
        Part a1 = new InHouse(1, "Part A1", 1.89, 8, 1, 120, 105);
        Part a2 = new InHouse(2, "Part A2", 2.89, 7, 1, 120, 104);
        Part a3 = new InHouse(3, "Part A3", 3.89, 5, 1, 120, 105);
        Part a4 = new InHouse(4, "Part A4", 4.89, 8, 1, 120, 106);
        Part a5 = new InHouse(5, "Part A5", 5.89, 6, 1, 120, 103);
        inv.addPart(a1);
        inv.addPart(a2);
        inv.addPart(a3);
        inv.addPart(a4);
        inv.addPart(a5);

        //Out Sourced Parts
        Part b1 = new OutSourced(6, "Part B1", 1.99, 9, 1, 40, "ABC");
        Part b2 = new OutSourced(7, "Part B2", 2.99, 6, 1, 50, "DEF");
        Part b3 = new OutSourced(8, "Part B3", 3.99, 8, 1, 60, "DEF");
        Part b4 = new OutSourced(9, "Part B4", 4.99, 10, 1, 70, "ABC");
        Part b5 = new OutSourced(10, "Part B5", 5.99, 11, 1, 80, "GHI");
        inv.addPart(b1);
        inv.addPart(b2);
        inv.addPart(b3);
        inv.addPart(b4);
        inv.addPart(b5);

        //Products
        Product p1 = new Product(01, "Product 1", 10.00, 4, 1, 30);
        Product p2 = new Product(02, "Product 2", 15.00, 3, 1, 30);
        Product p3 = new Product(03, "Product 3", 20.00, 2, 1, 30);
        Product p4 = new Product(04, "Product 4", 25.00, 1, 1, 30);
        inv.addProduct(p1);
        inv.addProduct(p2);
        inv.addProduct(p3);
        inv.addProduct(p4);

    }
}
