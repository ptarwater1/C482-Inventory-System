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
        addTestData(inv);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        View_Controller.MainScreen controller = new View_Controller.MainScreen(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    void addTestData(Inventory inv) {
        //In House Parts
        Part a1 = new InHouse(1, "Part A1", 1.89, 8, 1, 120, 105);
        Part a2 = new InHouse(2, "Part A2", 2.89, 7, 1, 120, 104);
        Part a3 = new InHouse(3, "Part A3", 3.89, 5, 1, 120, 105);
        Part a4 = new InHouse(4, "Part A4", 4.89, 8, 1, 120, 106);
        inv.addPart(a1);
        inv.addPart(a2);
        inv.addPart(a3);
        inv.addPart(a4);

        //Out Sourced Parts
        Part b1 = new OutSourced(5, "Part B1", 1.99, 10, 1, 120, "ABC");
        Part b2 = new OutSourced(5, "Part B2", 2.99, 10, 1, 120, "DEF");
        Part b3 = new OutSourced(5, "Part B3", 3.99, 10, 1, 120, "DEF");
        Part b4 = new OutSourced(5, "Part B4", 4.99, 10, 1, 120, "ABC");
        inv.addPart(b1);
        inv.addPart(b2);
        inv.addPart(b3);
        inv.addPart(b4);



    }
}
