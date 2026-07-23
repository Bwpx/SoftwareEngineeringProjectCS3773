package edu.softwareengineeringprojectcs3773;

import edu.softwareengineeringprojectcs3773.database.DatabaseInitializer;
import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            DatabaseInitializer.initializeDatabase();

            SceneNavigator.initialize(primaryStage);
            SceneNavigator.showHome();

        } catch (Exception exception) {
            exception.printStackTrace();

            throw new IllegalStateException(
                    "Unable to start the application.",
                    exception
            );
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}