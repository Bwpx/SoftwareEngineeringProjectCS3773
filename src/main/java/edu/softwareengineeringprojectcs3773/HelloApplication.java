package edu.softwareengineeringprojectcs3773;

import edu.softwareengineeringprojectcs3773.database.DatabaseInitializer;
import edu.softwareengineeringproject3773.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        DatabaseInitializer.initializeDatabase();

        FXMLLoader fxmlLoader = new FXMLLoader(getURL("login-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        LoginController loginController = fxmlLoader.getController();

        FXMLLoader registerLoader = new FXMLLoader(getURL("register-screen.fxml"));
        Scene registerScene = new Scene(registerLoader.load());

        FXMLLoader homeLoader = new FXMLLoader(getURL("home-screen.fxml"));
        Scene homeScene = new Scene(homeLoader.load());

        loginController.setStage(stage);
        loginController.setRegisterScene(registerScene);
        loginController.setHomeScene(homeScene);

        stage.setTitle("RoadRunner MarketPlace!");
        stage.setScene(scene);
        stage.show();
    }

    public static URL getURL(String fileName) {
        return HelloApplication.class.getResource(fileName);
    }

    public static void main(String[] args) {
        launch(args);
    }
}