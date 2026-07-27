package edu.softwareengineeringproject3773.controller;


import edu.softwareengineeringprojectcs3773.SceneNavigator;
import edu.softwareengineeringprojectcs3773.model.Address;
import edu.softwareengineeringprojectcs3773.model.CartItem;
import edu.softwareengineeringprojectcs3773.model.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderConfirmationController {

    @FXML
    private Label confirmationOrderNumberLabel;

    @FXML
    private Label confirmationDateLabel;

    @FXML
    private Label confirmationDeliveryLabel;

    @FXML
    private Label confirmationTotalLabel;

    @FXML
    private Label confirmationEmailLabel;

    @FXML
    private Button viewOrderButton;

    @FXML
    private Button returnHomeButton;

    private Order order;
    private Address address;
    private String confirmationEmail;
    private List<CartItem> orderItems = new ArrayList<>();;
    private double subtotal;
    private double tax;
    private double deliveryFee;


    @FXML
    private void initialize() {
        viewOrderButton.setOnAction(event -> handleViewOrder());
        returnHomeButton.setOnAction(event -> SceneNavigator.showHome());

    }

    public void setConfirmationData(
            Order order,
            Address address,
            String email
    ) {
        this.order = order;
        this.confirmationEmail = email;

        confirmationOrderNumberLabel.setText(String.valueOf(order.getOrderId()));
        confirmationDateLabel.setText(LocalDate.now().format(
                DateTimeFormatter.ofPattern("MMMM d, yyyy")));
        confirmationDeliveryLabel.setText(order.getDeliveryType());
        confirmationTotalLabel.setText(formatMoney(order.getTotal()));
        confirmationEmailLabel.setText(email == null || email.isBlank()
                ? "A confirmation email will be sent to the email address on your account."
                : "A confirmation email will be sent to " + email + ".");

    }


    public void setOrderDetailsData(Order order, Address address, List<CartItem> items,
                                    double subtotal, double tax,
                                    double deliveryFee) {
    	this.order = order;
    	this.address = address;
        this.orderItems = items == null ? new ArrayList<>() : new ArrayList<>(items);
        this.subtotal = subtotal;
        this.tax = tax;
        this.deliveryFee = deliveryFee;
    }

    private void handleViewOrder() {
        SceneNavigator.showScene(
                "order-detail-screen.fxml",
                (OrderDetailsController controller) -> controller.setOrderDetails(
                        order,
                        address,
                        orderItems,
                        subtotal,
                        tax,
                        deliveryFee
                )
        );
    }


    private String safeText(String value) {

        return value == null ? "" : value;
    }

    private String formatMoney(double amount) {

        return String.format("$%.2f", amount);
    }

}
