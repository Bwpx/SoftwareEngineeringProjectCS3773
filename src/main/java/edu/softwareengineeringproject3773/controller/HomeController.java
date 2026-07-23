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



}
