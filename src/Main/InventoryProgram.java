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
        View_Controller.MainScreenController controller = new View_Controller.MainScreenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    void addTestData(Inventory inv) {
        //InHouse Parts
        Part a1 = new InHouse(1, "Part A1", 1.89, 8, 6, 120, 105);
        Part a2 = new InHouse(2, "Part A2", 2.89, 7, 4, 120, 104);
        Part a3 = new InHouse(3, "Part A3", 3.89, 5, 5, 120, 105);
        inv.addPart(a1);
        inv.addPart(a2);
        inv.addPart(a3);


    }
}
