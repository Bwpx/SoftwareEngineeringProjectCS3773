package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.SceneNavigator;
import edu.softwareengineeringprojectcs3773.model.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class OrderHistoryController {
	@FXML Button homeButton;
	@FXML TableView<Order> orderTable;
	@FXML TableColumn<Order, String> orderNumberColumn;
	@FXML TableColumn<Order, String> orderDateColumn;
	@FXML TableColumn<Order, String> orderTotalColumn;
	@FXML TableColumn<Order, String> orderStatusColumn;
	@FXML TableColumn<Order, String> deliveryColumn;
	@FXML TableColumn<Order, String> viewDetailsColumn;
	
	
	public void initialize() {
		homeButton.setOnAction(event -> SceneNavigator.showHome());
	}
}
