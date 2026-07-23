package edu.softwareengineeringprojectcs3773;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;


public class SceneNavigator {

    private static final String RESOURCE_DIRECTORY =
            "/edu/softwareengineeringprojectcs3773/";

    private static final double WINDOW_WIDTH = 1180.0;
    private static final double WINDOW_HEIGHT = 800.0;

    private static Stage primaryStage;

    private SceneNavigator() {
    }

    public static void initialize(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException(
                    "The primary stage cannot be null."
            );
        }

        primaryStage = stage;
        primaryStage.setTitle("RoadRunner Marketplace");
        primaryStage.setMinWidth(900.0);
        primaryStage.setMinHeight(650.0);
    }

    public static void showLogin() {
        showScene("login-screen.fxml");
    }

    public static void showRegister() {
        showScene("register-screen.fxml");
    }

    public static void showHome() {
        showScene("home-screen.fxml");
    }

    public static void showBrowseItems() {
        showScene("browse-items-screen.fxml");
    }

    public static void showCart() {
        showScene("cart-screen.fxml");
    }

    public static void showCheckout() {
        showScene("checkout-screen.fxml");
    }

    public static void showAccount() {
        showScene("account-screen.fxml");
    }

    public static void showOrderHistory() {
        showScene("order-history-screen.fxml");
    }

    public static void showOrderConfirmation() {
        showScene("order-confirmation-screen.fxml");
    }

    public static void showItemDetails() {
        showScene("item-detail-screen.fxml");
    }

    public static void showOrderDetails() {
        showScene("order-detail-screen.fxml");
    }

    public static void showScene(String fxmlFile) {
        showScene(fxmlFile, controller -> {
        });
    }

    public static <T> void showScene(
            String fxmlFile,
            Consumer<T> controllerConfigurer
    ) {
        ensureInitialized();

        try {
            URL resource = SceneNavigator.class.getResource(
                    RESOURCE_DIRECTORY + fxmlFile
            );

            if (resource == null) {
                throw new IllegalArgumentException(
                        "FXML resource not found: "
                                + RESOURCE_DIRECTORY
                                + fxmlFile
                );
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            T controller = loader.getController();

            if (controller != null
                    && controllerConfigurer != null) {
                controllerConfigurer.accept(controller);
            }

            Scene scene = new Scene(
                    root,
                    WINDOW_WIDTH,
                    WINDOW_HEIGHT
            );

            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();

            if (!primaryStage.isShowing()) {
                primaryStage.show();
            }

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Could not load screen: " + fxmlFile,
                    exception
            );
        }
    }

    public static Stage getPrimaryStage() {
        ensureInitialized();
        return primaryStage;
    }

    private static void ensureInitialized() {
        if (primaryStage == null) {
            throw new IllegalStateException(
                    "SceneNavigator has not been initialized."
            );
        }
    }
}
