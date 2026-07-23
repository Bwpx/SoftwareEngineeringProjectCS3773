package edu.softwareengineeringproject3773.controller;


import edu.softwareengineeringprojectcs3773.SceneNavigator;
import edu.softwareengineeringprojectcs3773.model.CartItem;
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

    private String orderNumber;
    private String deliveryMethod;
    private double orderTotal;
    private String confirmationEmail;

    private LocalDate orderDate;
    private String orderStatus;
    private List<CartItem> orderItems = new ArrayList<>();
    private String deliveryAddress;
    private double subtotal;
    private double tax;
    private double deliveryFee;


    @FXML
    private void initialize() {
        viewOrderButton.setOnAction(event -> handleViewOrder());
        returnHomeButton.setOnAction(event -> SceneNavigator.showHome());

    }

    public void setConfirmationData(
            String orderNumber,
            String deliveryMethod,
            double total,
            String email
    ) {
        this.orderNumber = orderNumber;
        this.deliveryMethod = deliveryMethod;
        this.orderTotal = total;
        this.confirmationEmail = email;

        confirmationOrderNumberLabel.setText(safeText(orderNumber));
        confirmationDateLabel.setText(LocalDate.now().format(
                DateTimeFormatter.ofPattern("MMMM d, yyyy")));
        confirmationDeliveryLabel.setText(safeText(deliveryMethod));
        confirmationTotalLabel.setText(formatMoney(total));
        confirmationEmailLabel.setText(email == null || email.isBlank()
                ? "A confirmation email will be sent to the email address on your account."
                : "A confirmation email will be sent to " + email + ".");

    }


    public void setOrderDetailsData(String orderNumber, LocalDate orderDate,
                                    String orderStatus, List<CartItem> items,
                                    String deliveryAddress, String deliveryMethod,
                                    double subtotal, double tax,
                                    double deliveryFee, double total) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderItems = items == null ? new ArrayList<>() : new ArrayList<>(items);
        this.deliveryAddress = deliveryAddress;
        this.deliveryMethod = deliveryMethod;
        this.subtotal = subtotal;
        this.tax = tax;
        this.deliveryFee = deliveryFee;
        this.orderTotal = total;
    }

    private void handleViewOrder() {
        SceneNavigator.showScene(
                "order-detail-screen.fxml",
                (OrderDetailsController controller) -> controller.setOrderDetails(
                        orderNumber,
                        orderDate,
                        orderStatus,
                        orderItems,
                        deliveryAddress,
                        deliveryMethod,
                        subtotal,
                        tax,
                        deliveryFee,
                        orderTotal
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
