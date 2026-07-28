package edu.softwareengineeringprojectcs3773;

import edu.softwareengineeringprojectcs3773.database.DatabaseInitializer;
import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        DatabaseInitializer.initializeDatabase();
        SceneNavigator.initialize(stage);
        SceneNavigator.showLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
