package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.ApplicationState;
import edu.softwareengineeringprojectcs3773.SceneNavigator;
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
        refreshHome();
    }

    private void configureButtons() {
        browseItemsButton.setOnAction(event -> SceneNavigator.showBrowseItems());
        viewCartButton.setOnAction(event -> openProtected(SceneNavigator::showCart));
        ordersButton.setOnAction(event -> openProtected(SceneNavigator::showOrderHistory));
        accountButton.setOnAction(event -> openProtected(SceneNavigator::showAccount));
        logoutButton.setOnAction(event -> handleLogout());

    }

    public void refreshHome() {
        Account account = ApplicationState.getCurrentAccount();
        boolean loggedIn = account != null;

        welcomeLabel.setText(loggedIn && account.getUsername() != null
                && !account.getUsername().isBlank()
                ? "Welcome, " + account.getUsername() + "!"
                : "Welcome!");

        viewCartButton.setDisable(!loggedIn);
        ordersButton.setDisable(!loggedIn);
        accountButton.setDisable(!loggedIn);
        loadFeaturedItems();
    }


    private void loadFeaturedItems() {
        featuredItemsPane.getChildren().clear();
        Label placeholder = new Label("Featured products will load here.");
        placeholder.getStyleClass().add("muted-text");
        featuredItemsPane.getChildren().add(placeholder);

    }

    private void openProtected(Runnable navigation) {
        if (!ApplicationState.isLoggedIn()) {
            SceneNavigator.showLogin();
            return;
        }
        navigation.run();
    }

    private void handleLogout() {
        ApplicationState.clearSession();
        SceneNavigator.showLogin();
    }


}
