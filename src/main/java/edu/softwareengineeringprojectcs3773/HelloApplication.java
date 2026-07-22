package edu.softwareengineeringprojectcs3773;

import edu.softwareengineeringprojectcs3773.database.DatabaseInitializer;
import edu.softwareengineeringprojectcs3773.model.GroceryItem;
import edu.softwareengineeringprojectcs3773.service.ItemService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        DatabaseInitializer.initializeDatabase();

        ItemService itemService = new ItemService();

        System.out.println("\nAll grocery items:");
        for (GroceryItem item : itemService.getAllItems()) {
            System.out.println(item);
            System.out.println();
        }

        System.out.println("Search results for 'milk':");
        for (GroceryItem item : itemService.searchItemsByName("milk")) {
            System.out.println(item);
        }

        boolean stockUpdated = itemService.updateStock(1, 10);
        System.out.println("Stock updated: " + stockUpdated);

        GroceryItem updatedItem = itemService.getItemById(1);

        if (updatedItem != null) {
            System.out.println("Updated item:");
            System.out.println(updatedItem);
        }

        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Online Grocery System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}