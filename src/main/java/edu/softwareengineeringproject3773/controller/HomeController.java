package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.ApplicationState;
import edu.softwareengineeringprojectcs3773.SceneNavigator;
import edu.softwareengineeringprojectcs3773.model.Account;
import edu.softwareengineeringprojectcs3773.model.GroceryItem;
import edu.softwareengineeringprojectcs3773.service.ItemService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.List;

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
        refreshHome();

    }

    public void refreshHome() {
        Account account = ApplicationState.getCurrentAccount();
        boolean loggedIn = account != null;

        welcomeLabel.setText(loggedIn && account.getUsername() != null
                && !account.getUsername().isBlank()
                ? "Welcome, " + account.getUsername() + "!"
                : "Welcome to Roadrunner Marketplace!");

        viewCartButton.setDisable(!loggedIn);
        ordersButton.setDisable(!loggedIn);
        accountButton.setDisable(!loggedIn);
        logoutButton.setText(loggedIn ? "Log Out" : "Log In");

        loadFeaturedItems();
    }


    private void loadFeaturedItems() {
        featuredItemsPane.getChildren().clear();
        try {
            List<GroceryItem> items = new ItemService().getInStockItems();
            int count = Math.min(3, items.size());
            for (int i = 0; i < count; i++) {
                GroceryItem item = items.get(i);
                Node card = ItemCardFactory.create(
                        item,
                        selected -> SceneNavigator.showScene(
                                "item-detail-screen.fxml",
                                (ItemDetailsController controller) -> controller.setItem(selected)),
                        selected -> {
                            if (!ApplicationState.isLoggedIn()) {
                                SceneNavigator.showLogin();
                                return;
                            }
                            ApplicationState.getCurrentCart().addItem(selected);
                        });
                featuredItemsPane.getChildren().add(card);
            }
            if (count == 0) showFeaturedPlaceholder("No featured items are available right now.");
        } catch (IOException | RuntimeException exception) {
            showFeaturedPlaceholder("Featured products are temporarily unavailable.");
        }
    }


    private void showFeaturedPlaceholder(String text) {
        Label placeholder = new Label(text);
        placeholder.getStyleClass().add("empty-state-text");
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
        if (ApplicationState.isLoggedIn()) {
            ApplicationState.clearSession();
        }
        SceneNavigator.showLogin();
    }

}
