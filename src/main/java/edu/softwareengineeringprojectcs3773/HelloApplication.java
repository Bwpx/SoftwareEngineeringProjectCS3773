package edu.softwareengineeringprojectcs3773;

import edu.softwareengineeringproject3773.controller.CartController;
import edu.softwareengineeringproject3773.controller.CheckoutController;
import edu.softwareengineeringproject3773.controller.LoginController;
import edu.softwareengineeringproject3773.controller.OrderConfirmationController;
import edu.softwareengineeringproject3773.controller.OrderDetailsController;
import edu.softwareengineeringprojectcs3773.database.DatabaseInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {

    private static final String APPLICATION_TITLE =
            "RoadRunner Marketplace";

    private static final double WINDOW_WIDTH = 1200.0;
    private static final double WINDOW_HEIGHT = 790.0;

    @Override
    public void start(Stage primaryStage) throws IOException {

        try {
            DatabaseInitializer.initializeDatabase();

            LoadedScreen<LoginController> loginScreen =
                    loadScreen(
                            "login-screen.fxml",
                            LoginController.class
                    );

            LoadedScreen<Object> registerScreen =
                    loadScreen(
                            "register-screen.fxml",
                            Object.class
                    );

            LoadedScreen<Object> homeScreen =
                    loadScreen(
                            "home-screen.fxml",
                            Object.class
                    );

            LoadedScreen<Object> accountScreen =
                    loadScreen(
                            "account-screen.fxml",
                            Object.class
                    );

            LoadedScreen<Object> addressScreen =
                    loadScreen(
                            "address-dialog.fxml",
                            Object.class
                    );

            LoadedScreen<Object> browseScreen =
                    loadScreen(
                            "browse-items-screen.fxml",
                            Object.class
                    );

            LoadedScreen<CartController> cartScreen =
                    loadScreen(
                            "cart-screen.fxml",
                            CartController.class
                    );

            LoadedScreen<CheckoutController> checkoutScreen =
                    loadScreen(
                            "checkout-screen.fxml",
                            CheckoutController.class
                    );

            LoadedScreen<Object> itemDetailsScreen =
                    loadScreen(
                            "item-detail-screen.fxml",
                            Object.class
                    );

            LoadedScreen<OrderConfirmationController>
                    confirmationScreen =
                    loadScreen(
                            "order-confirmation-screen.fxml",
                            OrderConfirmationController.class
                    );

            LoadedScreen<OrderDetailsController>
                    orderDetailsScreen =
                    loadScreen(
                            "order-detail-screen.fxml",
                            OrderDetailsController.class
                    );

            LoadedScreen<Object> orderHistoryScreen =
                    loadScreen(
                            "order-history-screen.fxml",
                            Object.class
                    );

            configureLoginController(
                    loginScreen.getController(),
                    primaryStage,
                    registerScreen.getScene(),
                    homeScreen.getScene()
            );

            configureCartController(
                    cartScreen.getController(),
                    browseScreen.getScene(),
                    checkoutScreen.getScene(),
                    checkoutScreen.getController()
            );

            configureCheckoutController(
                    checkoutScreen.getController(),
                    cartScreen.getScene(),
                    confirmationScreen.getScene(),
                    confirmationScreen.getController(),
                    orderDetailsScreen.getController()
            );

            configureConfirmationController(
                    confirmationScreen.getController(),
                    homeScreen.getScene(),
                    orderDetailsScreen.getScene(),
                    orderDetailsScreen.getController()
            );

            configureOrderDetailsController(
                    orderDetailsScreen.getController(),
                    orderHistoryScreen.getScene()
            );

            configurePrimaryStage(
                    primaryStage,
                    loginScreen.getScene()
            );

        } catch (Exception exception) {
            exception.printStackTrace();

            throw new IllegalStateException(
                    "The RoadRunner Marketplace application "
                            + "could not be started.",
                    exception
            );
        }
    }

    private void configureLoginController(
            LoginController loginController,
            Stage primaryStage,
            Scene registerScene,
            Scene homeScene
    ) {
        loginController.setStage(primaryStage);
        loginController.setRegisterScene(registerScene);
        loginController.setHomeScene(homeScene);
    }

    private void configureCartController(
            CartController cartController,
            Scene browseScene,
            Scene checkoutScene,
            CheckoutController checkoutController
    ) {
        cartController.setBrowseScene(browseScene);
        cartController.setCheckoutScene(checkoutScene);
        cartController.setCheckoutController(
                checkoutController
        );
    }

    private void configureCheckoutController(
            CheckoutController checkoutController,
            Scene cartScene,
            Scene confirmationScene,
            OrderConfirmationController
                    confirmationController,
            OrderDetailsController orderDetailsController
    ) {
        checkoutController.setCartScene(cartScene);

        checkoutController.setConfirmationScene(
                confirmationScene
        );

        checkoutController.setConfirmationController(
                confirmationController
        );

        checkoutController.setOrderDetailsController(
                orderDetailsController
        );
    }

    private void configureConfirmationController(
            OrderConfirmationController
                    confirmationController,
            Scene homeScene,
            Scene orderDetailsScene,
            OrderDetailsController orderDetailsController
    ) {
        confirmationController.setHomeScene(homeScene);

        confirmationController.setOrderDetailScene(
                orderDetailsScene
        );

        confirmationController.setOrderDetailsController(
                orderDetailsController
        );
    }

    private void configureOrderDetailsController(
            OrderDetailsController orderDetailsController,
            Scene orderHistoryScene
    ) {
        orderDetailsController.setOrderHistoryScene(
                orderHistoryScene
        );
    }

    private void configurePrimaryStage(
            Stage primaryStage,
            Scene initialScene
    ) {
        primaryStage.setTitle(APPLICATION_TITLE);
        primaryStage.setScene(initialScene);

        primaryStage.setMinWidth(900.0);
        primaryStage.setMinHeight(650.0);

        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);

        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private <T> LoadedScreen<T> loadScreen(
            String fileName,
            Class<T> controllerClass
    ) throws IOException {

        URL resource = getURL(fileName);

        FXMLLoader loader = new FXMLLoader(resource);
        Parent root = loader.load();

        Object loadedController = loader.getController();

        if (loadedController == null) {
            throw new IllegalStateException(
                    "No controller was created for "
                            + fileName
                            + ". Check the fx:controller value."
            );
        }

        T controller;

        if (controllerClass == Object.class) {
            @SuppressWarnings("unchecked")
            T genericController = (T) loadedController;

            controller = genericController;
        } else {
            if (!controllerClass.isInstance(
                    loadedController
            )) {
                throw new IllegalStateException(
                        "Controller mismatch for "
                                + fileName
                                + ". Expected "
                                + controllerClass.getName()
                                + " but FXMLLoader created "
                                + loadedController
                                .getClass()
                                .getName()
                );
            }

            controller = controllerClass.cast(
                    loadedController
            );
        }

        Scene scene = new Scene(
                root,
                WINDOW_WIDTH,
                WINDOW_HEIGHT
        );

        return new LoadedScreen<>(
                scene,
                controller
        );
    }

    public static URL getURL(String fileName) {
        URL resource =
                HelloApplication.class.getResource(
                        fileName
                );

        if (resource == null) {
            throw new IllegalArgumentException(
                    "Resource not found: "
                            + fileName
                            + "\nExpected resource package: "
                            + "/edu/"
                            + "softwareengineeringprojectcs3773/"
            );
        }

        return resource;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Stores a loaded Scene together with its controller.
     */
    private static final class LoadedScreen<T> {

        private final Scene scene;
        private final T controller;

        private LoadedScreen(
                Scene scene,
                T controller
        ) {
            this.scene = scene;
            this.controller = controller;
        }

        private Scene getScene() {
            return scene;
        }

        private T getController() {
            return controller;
        }
    }
}





