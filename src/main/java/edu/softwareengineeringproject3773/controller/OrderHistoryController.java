package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.ApplicationState;
import edu.softwareengineeringprojectcs3773.SceneNavigator;
import edu.softwareengineeringprojectcs3773.model.Account;
import edu.softwareengineeringprojectcs3773.model.Order;
import edu.softwareengineeringprojectcs3773.repository.OrderRepository;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class OrderHistoryController {
	@FXML Button homeButton;
	@FXML TableView<Order> ordersTableView;
	@FXML TableColumn<Order, String> orderNumberColumn;
	@FXML TableColumn<Order, String> orderDateColumn;
	@FXML TableColumn<Order, String> orderTotalColumn;
	@FXML TableColumn<Order, String> orderStatusColumn;
	@FXML TableColumn<Order, String> deliveryColumn;
	@FXML TableColumn<Order, String> viewDetailsColumn;
	
	Account account;
	OrderRepository orders;
	
	
	public void initialize() {
		account = ApplicationState.getCurrentAccount();
		orders = new OrderRepository();
		homeButton.setOnAction(event -> SceneNavigator.showHome());
		configureColumns();
		refreshOrders();
	}
	
	private void configureColumns() {
		orderNumberColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getOrderId()));
		orderDateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDate().toString()));
		orderTotalColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Double.toString(cellData.getValue().getTotal())));
		orderStatusColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStatus()));
		deliveryColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDeliveryType()));
		
	}
	
	private void refreshOrders() {
		account = ApplicationState.getCurrentAccount();
		
		ordersTableView.setItems(FXCollections.observableArrayList(orders.findByAccountId(account.getAccountId())));
		ordersTableView.refresh();
	}
}
