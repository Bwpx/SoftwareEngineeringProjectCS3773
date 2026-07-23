package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.ApplicationState;
import edu.softwareengineeringprojectcs3773.model.Account;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class HomeController {
    @FXML
    private Label welcomeLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button browseItemsButton;

    @FXML
    private Button viewCartButton;

    @FXML
    private Button ordersButton;

    @FXML
    private Button accountButton;

    @FXML
    private TilePane featuredItemsPane;

    private Scene loginScene;
    private Scene browseScene;
    private Scene cartScene;
    private Scene orderHistoryScene;
    private Scene accountScene;

    private CartController cartController;

    @FXML
    private void initialize() {
        configureButtons();

        /*
         * The controller is initialized while HelloApplication
         * preloads the screen. At that point, the user may not
         * be logged in yet, so refreshHome() must also be called
         * immediately before this scene is displayed.
         */
        refreshHome();
    }

    private void configureButtons() {
        browseItemsButton.setOnAction(event ->
                handleBrowseItems()
        );

        viewCartButton.setOnAction(event ->
                handleViewCart()
        );

        ordersButton.setOnAction(event ->
                handleViewOrders()
        );

        accountButton.setOnAction(event ->
                handleAccount()
        );

        logoutButton.setOnAction(event ->
                handleLogout()
        );
    }

    public void refreshHome() {
        Account account =
                ApplicationState.getCurrentAccount();

        if (account == null) {
            welcomeLabel.setText("Welcome!");

            /*
             * During normal use, the home screen should only be
             * displayed after login. Disabling account-specific
             * navigation prevents null-account errors.
             */
            viewCartButton.setDisable(true);
            ordersButton.setDisable(true);
            accountButton.setDisable(true);

            return;
        }

        String username = account.getUsername();

        if (username == null || username.isBlank()) {
            welcomeLabel.setText("Welcome!");
        } else {
            welcomeLabel.setText(
                    "Welcome, " + username + "!"
            );
        }

        viewCartButton.setDisable(false);
        ordersButton.setDisable(false);
        accountButton.setDisable(false);

        loadFeaturedItems();
    }

    /**
     * Temporary featured-item placeholder.
     *
     * Later this should call ItemService for featured or popular
     * products and create cards through ItemCardFactory.
     */
    private void loadFeaturedItems() {
        featuredItemsPane.getChildren().clear();

        Label placeholder = new Label(
                "Featured products will load here."
        );

        placeholder.getStyleClass().add(
                "muted-text"
        );

        featuredItemsPane.getChildren().add(
                placeholder
        );
    }

    private void handleBrowseItems() {
        if (browseScene == null) {
            showNavigationError(
                    "Browse Items"
            );
            return;
        }

        getStage().setScene(browseScene);
    }

    private void handleViewCart() {
        if (!ApplicationState.isLoggedIn()) {
            returnToLogin();
            return;
        }

        if (cartScene == null) {
            showNavigationError(
                    "Shopping Cart"
            );
            return;
        }

        if (cartController != null) {
            cartController.refreshCart();
        }

        getStage().setScene(cartScene);
    }

    private void handleViewOrders() {
        if (!ApplicationState.isLoggedIn()) {
            returnToLogin();
            return;
        }

        if (orderHistoryScene == null) {
            showNavigationError(
                    "Order History"
            );
            return;
        }

        /*
         * Later:
         *
         * orderHistoryController.refreshOrders();
         *
         * The controller will retrieve orders belonging to:
         *
         * ApplicationState.getCurrentAccount().getAccountId()
         */

        getStage().setScene(orderHistoryScene);
    }

    private void handleAccount() {
        if (!ApplicationState.isLoggedIn()) {
            returnToLogin();
            return;
        }

        if (accountScene == null) {
            showNavigationError(
                    "Account"
            );
            return;
        }

        /*
         * Later:
         *
         * accountController.refreshAccount();
         */

        getStage().setScene(accountScene);
    }

    private void handleLogout() {
        ApplicationState.clearSession();

        if (loginScene == null) {
            showNavigationError(
                    "Login"
            );
            return;
        }

        getStage().setScene(loginScene);
    }

    private void returnToLogin() {
        ApplicationState.clearSession();

        if (loginScene != null) {
            getStage().setScene(loginScene);
        }
    }

    private void showNavigationError(
            String screenName
    ) {
        System.err.println(
                screenName
                        + " navigation has not been connected."
        );
    }

    private Stage getStage() {
        return (Stage) welcomeLabel
                .getScene()
                .getWindow();
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    public void setBrowseScene(Scene browseScene) {
        this.browseScene = browseScene;
    }

    public void setCartScene(Scene cartScene) {
        this.cartScene = cartScene;
    }

    public void setOrderHistoryScene(
            Scene orderHistoryScene
    ) {
        this.orderHistoryScene =
                orderHistoryScene;
    }

    public void setAccountScene(
            Scene accountScene
    ) {
        this.accountScene = accountScene;
    }

    public void setCartController(
            CartController cartController
    ) {
        this.cartController = cartController;
    }


}
