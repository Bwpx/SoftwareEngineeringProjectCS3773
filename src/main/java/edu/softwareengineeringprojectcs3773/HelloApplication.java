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

        FXMLLoader accountLoader = new FXMLLoader(getURL("account-screen.fxml"));
        Scene accountScene = new Scene(accountLoader.load());

        FXMLLoader addressLoader = new FXMLLoader(getURL("address-dialog.fxml"));
        Scene addressScene = new Scene(addressLoader.load());

        FXMLLoader browseLoader = new FXMLLoader(getURL("browse-items-screen.fxml"));
        //Scene browseScene = new Scene(browseLoader.load());

        FXMLLoader cartLoader = new FXMLLoader(getURL("cart-screen.fxml"));
        Scene cartScene = new Scene(browseLoader.load());

        FXMLLoader checkoutLoader = new FXMLLoader(getURL("checkout-screen.fxml"));
        Scene checkoutScene = new Scene(checkoutLoader.load());

        FXMLLoader itemLoader = new FXMLLoader(getURL("item-detail-screen.fxml"));
        Scene itemScene = new Scene(itemLoader.load());

        FXMLLoader confirmationLoader = new FXMLLoader(getURL("order-confirmation-screen.fxml"));
        Scene confirmationScene = new Scene(confirmationLoader.load());

        FXMLLoader detailLoader = new FXMLLoader(getURL("order-detail-screen.fxml"));
        Scene detailScene = new Scene(detailLoader.load());

        FXMLLoader historyLoader = new FXMLLoader(getURL("order-history-screen.fxml"));
        Scene historyScene = new Scene(historyLoader.load());

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