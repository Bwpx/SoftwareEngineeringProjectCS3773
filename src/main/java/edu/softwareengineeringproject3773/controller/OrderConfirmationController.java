package edu.softwareengineeringproject3773.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    private Scene homeScene;
    private Scene orderDetailScene;

    private String orderNumber;
    private String deliveryMethod;
    private double orderTotal;
    private String confirmationEmail;

    @FXML
    private void initialize() {
        configureButtons();
    }

    private void configureButtons() {
        viewOrderButton.setOnAction(event ->
                handleViewOrder()
        );

        returnHomeButton.setOnAction(event ->
                handleReturnHome()
        );
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

        displayConfirmationData();
    }

    private void displayConfirmationData() {
        confirmationOrderNumberLabel.setText(
                safeText(orderNumber)
        );

        confirmationDateLabel.setText(
                LocalDate.now().format(
                        DateTimeFormatter.ofPattern(
                                "MMMM d, yyyy"
                        )
                )
        );

        confirmationDeliveryLabel.setText(
                safeText(deliveryMethod)
        );

        confirmationTotalLabel.setText(
                formatMoney(orderTotal)
        );

        if (confirmationEmail == null
                || confirmationEmail.isBlank()) {

            confirmationEmailLabel.setText(
                    "A confirmation email will be sent "
                            + "to the email address on your account."
            );
        } else {
            confirmationEmailLabel.setText(
                    "A confirmation email will be sent to "
                            + confirmationEmail + "."
            );
        }

        /*
         * Database integration:
         *
         * Replace these temporary values with an Order object
         * loaded through OrderService.
         *
         * Example:
         *
         * Order order =
         *         orderService.getOrderById(orderId);
         *
         * confirmationOrderNumberLabel.setText(
         *         order.getOrderNumber()
         * );
         *
         * confirmationDateLabel.setText(
         *         order.getOrderDate().format(...)
         * );
         *
         * confirmationDeliveryLabel.setText(
         *         order.getDeliveryMethod()
         * );
         *
         * confirmationTotalLabel.setText(
         *         formatMoney(order.getTotal())
         * );
         *
         * OrderService should call OrderRepository. This
         * controller should not access the database directly.
         */
    }

    @FXML
    private void handleViewOrder() {
        if (orderDetailScene == null) {
            System.err.println(
                    "Order detail navigation has not been connected."
            );
            return;
        }

        /*
         * Database integration:
         *
         * Before opening the order detail screen, pass the
         * database-generated order ID or order number to
         * OrderDetailController.
         *
         * Example:
         *
         * orderDetailController.loadOrder(orderId);
         */

        getStage().setScene(orderDetailScene);
    }

    @FXML
    private void handleReturnHome() {
        if (homeScene == null) {
            System.err.println(
                    "Home navigation has not been connected."
            );
            return;
        }

        clearConfirmationData();

        getStage().setScene(homeScene);
    }

    public void clearConfirmationData() {
        orderNumber = null;
        deliveryMethod = null;
        orderTotal = 0.0;
        confirmationEmail = null;

        confirmationOrderNumberLabel.setText("");
        confirmationDateLabel.setText("");
        confirmationDeliveryLabel.setText("");
        confirmationTotalLabel.setText("");
        confirmationEmailLabel.setText(
                "A confirmation email will be sent "
                        + "to the email address on your account."
        );
    }

    private Stage getStage() {
        return (Stage) returnHomeButton
                .getScene()
                .getWindow();
    }

    private String safeText(String value) {
        return value == null ? "" : value;
    }

    private String formatMoney(double amount) {
        return String.format("$%.2f", amount);
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public String getConfirmationEmail() {
        return confirmationEmail;
    }

    public void setHomeScene(Scene homeScene) {
        this.homeScene = homeScene;
    }

    public void setOrderDetailScene(
            Scene orderDetailScene
    ) {
        this.orderDetailScene = orderDetailScene;
    }

}
