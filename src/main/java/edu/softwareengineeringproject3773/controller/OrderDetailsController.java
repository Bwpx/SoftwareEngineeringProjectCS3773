package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.SceneNavigator;
import edu.softwareengineeringprojectcs3773.model.Address;
import edu.softwareengineeringprojectcs3773.model.CartItem;
import edu.softwareengineeringprojectcs3773.model.GroceryItem;
import edu.softwareengineeringprojectcs3773.model.Order;
import edu.softwareengineeringprojectcs3773.repository.AddressRepository;
import edu.softwareengineeringprojectcs3773.repository.OrderRepository;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsController {

    @FXML
    private Button backToHistoryButton;

    @FXML
    private Label orderNumberLabel;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label orderStatusLabel;

    @FXML
    private TableView<CartItem> orderItemsTable;

    @FXML
    private TableColumn<CartItem, String> orderItemNameColumn;

    @FXML
    private TableColumn<CartItem, String> orderItemPriceColumn;

    @FXML
    private TableColumn<CartItem, Number> orderItemQuantityColumn;

    @FXML
    private TableColumn<CartItem, String> orderItemSubtotalColumn;

    @FXML
    private Label orderAddressLabel;

    @FXML
    private Label orderDeliveryMethodLabel;

    @FXML
    private Label orderDetailSubtotalLabel;

    @FXML
    private Label orderDetailTaxLabel;

    @FXML
    private Label orderDetailTotalLabel;

    /*
     * Temporary in-memory order information.
     *
     * Database integration:
     *
     * Replace these separate fields with an Order model loaded
     * through OrderService.
     */
    private OrderRepository orders;
    private AddressRepository addresses;
    private Order order;
    private Address address;
    private double subtotal;
    private double tax;
    private double deliveryFee;

    private final List<CartItem> orderItems =
            new ArrayList<>();

    @FXML
    private void initialize() {
        configureTable();
        configureActions();
        clearOrderDetails();
    }

    private void configureTable() {
        orderItemNameColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(
                        cellData.getValue()
                                .getItem()
                                .getItemName()
                )
        );

        orderItemPriceColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(
                        formatMoney(
                                cellData.getValue()
                                        .getItem()
                                        .getPrice()
                        )
                )
        );

        orderItemQuantityColumn.setCellValueFactory(cellData ->
                new ReadOnlyIntegerWrapper(
                        cellData.getValue()
                                .getQuantity()
                )
        );

        orderItemSubtotalColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(
                        formatMoney(
                                cellData.getValue()
                                        .getSubtotal()
                        )
                )
        );
    }

    private void configureActions() {
        backToHistoryButton.setOnAction(event ->
                handleBackToHistory()
        );
    }

    /**
     * Temporarily displays the order that was just placed.
     *
     * Later this should be replaced by loadOrder(orderId),
     * which obtains a complete Order through OrderService.
     */
    public void setOrderDetails(
    		Order order,
    		Address address,
            List<CartItem> items,
            double subtotal,
            double tax,
            double deliveryFee
    ) {
    	this.order = order;
    	this.address = address;

        this.subtotal = subtotal;
        this.tax = tax;
        this.deliveryFee = deliveryFee;

        orderItems.clear();

        if (items != null) {
            /*
             * Make a snapshot rather than keeping references to
             * CartItem objects that will be cleared from the cart.
             */
            for (CartItem cartItem : items) {
                orderItems.add(copyCartItem(cartItem));
            }
        }

        displayOrderDetails();
    }

    private void displayOrderDetails() {
        orderNumberLabel.setText(
                "Order " + order.getOrderId()
        );

        if (order.getDate() == null) {
            orderDateLabel.setText("");
        } else {
            orderDateLabel.setText(
                    "Order Date: "
                            + order.getDate()
                    );
        }

        orderStatusLabel.setText(
                "Status: " + order.getStatus()
        );

        orderItemsTable.setItems(
                FXCollections.observableArrayList(
                        orderItems
                )
        );

        orderItemsTable.refresh();

        orderAddressLabel.setText(
                address.toString()
        );

        orderDeliveryMethodLabel.setText(
                order.getDeliveryType()
        );

        orderDetailSubtotalLabel.setText(
                formatMoney(subtotal)
        );

        orderDetailTaxLabel.setText(
                formatMoney(tax)
        );

        orderDetailTotalLabel.setText(
                formatMoney(order.getTotal())
        );
    }

    private CartItem copyCartItem(CartItem source) {
        GroceryItem originalItem = source.getItem();

        /*
         * We can reuse GroceryItem because CartItem quantity is
         * the part that changes within the cart.
         *
         * Later, OrderItem should store the price paid at the
         * time of purchase. This prevents later inventory price
         * changes from altering old order totals.
         */
        return new CartItem(
                originalItem,
                source.getQuantity()
        );
    }

    /**
     * Future database-backed loading method.
     */
    public void loadOrder(int orderId) {
        /*
         * Database integration:
         *
         * Order order =
         *         orderService.getOrderById(orderId);
         *
         * Verify that the order belongs to the currently
         * authenticated account before displaying it.
         *
         * Then populate:
         *
         * - order number
         * - date
         * - status
         * - OrderItem records
         * - delivery address
         * - delivery method
         * - subtotal
         * - tax
         * - delivery fee
         * - total
         *
         * OrderService should call OrderRepository. SQL must not
         * be placed in this controller.
         */
    	order = orders.findById(orderId);
    }

    @FXML
    private void handleBackToHistory() {
        SceneNavigator.showOrderHistory();
    }

    public void clearOrderDetails() {
        order = null;
        address = null;

        subtotal = 0.0;
        tax = 0.0;
        deliveryFee = 0.0;

        orderItems.clear();

        if (orderNumberLabel != null) {
            orderNumberLabel.setText("");
            orderDateLabel.setText("");
            orderStatusLabel.setText("");

            orderItemsTable.setItems(
                    FXCollections.observableArrayList()
            );

            orderAddressLabel.setText("");
            orderDeliveryMethodLabel.setText("");

            orderDetailSubtotalLabel.setText("$0.00");
            orderDetailTaxLabel.setText("$0.00");
            orderDetailTotalLabel.setText("$0.00");
        }
    }


    private String safeText(int orderNumber2) {
        //return orderNumber2 == null ? "" : orderNumber2;
    	return null;
    }

    private String formatMoney(double amount) {
        return String.format("$%.2f", amount);
    }


    public int getOrderNumber() {
        return order.getOrderId();
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public double getTotal() {
        return order.getTotal();
    }

}
