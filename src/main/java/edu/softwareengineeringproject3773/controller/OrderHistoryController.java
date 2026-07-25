package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.SceneNavigator;
import edu.softwareengineeringprojectcs3773.model.Order;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Comparator;

public class OrderHistoryController {
	@FXML private Button homeButton;
	@FXML private Button sortButton;
	@FXML private ComboBox<String> sortOrdersComboBox;
	@FXML private TableView<Order> ordersTableView;
	@FXML private TableColumn<Order, String> orderNumberColumn;
	@FXML private TableColumn<Order, String> orderDateColumn;
	@FXML private TableColumn<Order, String> orderTotalColumn;
	@FXML private TableColumn<Order, String> orderStatusColumn;
	@FXML private TableColumn<Order, String> deliveryColumn;
	@FXML private TableColumn<Order, String> viewDetailsColumn;

	@FXML
	private void initialize() {
		homeButton.setOnAction(event -> SceneNavigator.showHome());
		sortButton.setOnAction(event -> applySort());

		sortOrdersComboBox.getItems().setAll(
				"Newest first",
				"Oldest first",
				"Highest total",
				"Lowest total"
		);
		sortOrdersComboBox.getSelectionModel().selectFirst();

		orderNumberColumn.setCellValueFactory(data ->
				new SimpleStringProperty("#UTSA-" + data.getValue().getOrderId()));
		orderDateColumn.setCellValueFactory(data ->
				new SimpleStringProperty(String.valueOf(data.getValue().getDate())));
		orderTotalColumn.setCellValueFactory(data ->
				new SimpleStringProperty(String.format("$%.2f", data.getValue().getTotal())));
		orderStatusColumn.setCellValueFactory(data ->
				new SimpleStringProperty(data.getValue().getStatus()));
		deliveryColumn.setCellValueFactory(data ->
				new SimpleStringProperty(data.getValue().getDeliveryType()));
		viewDetailsColumn.setCellValueFactory(data ->
				new SimpleStringProperty("View Details"));

		Label placeholder = new Label(
				"No orders yet. Complete checkout to see your order history here.");
		placeholder.setWrapText(true);
		placeholder.getStyleClass().add("empty-state-text");
		ordersTableView.setPlaceholder(placeholder);

		// OrderService should populate this table once the team's repository query is ready.
		ordersTableView.setItems(FXCollections.observableArrayList());
	}

	private void applySort() {
		String selected = sortOrdersComboBox.getValue();
		if (selected == null || ordersTableView.getItems().isEmpty()) return;

		Comparator<Order> comparator;
		switch (selected) {
			case "Oldest first" -> comparator = Comparator.comparing(Order::getDate);
			case "Highest total" -> comparator = Comparator.comparingDouble(Order::getTotal).reversed();
			case "Lowest total" -> comparator = Comparator.comparingDouble(Order::getTotal);
			default -> comparator = Comparator.comparing(Order::getDate).reversed();
		}
		FXCollections.sort(ordersTableView.getItems(), comparator);
	}

}
