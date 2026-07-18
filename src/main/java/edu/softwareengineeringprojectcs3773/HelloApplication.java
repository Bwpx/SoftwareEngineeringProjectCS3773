package edu.softwareengineeringprojectcs3773;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import edu.softwareengineeringproject3773.controller.LoginController;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getURL("login-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        
        LoginController loginController = fxmlLoader.getController();
        
        FXMLLoader registerLoader = new FXMLLoader(getURL("register-screen.fxml"));
        Scene registerScene = new Scene(registerLoader.load());
        
        loginController.setStage(stage);
        loginController.setRegisterScene(registerScene);
        
        
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