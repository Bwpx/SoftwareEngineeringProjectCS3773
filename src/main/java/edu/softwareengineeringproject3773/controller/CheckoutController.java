package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.ApplicationState;
import edu.softwareengineeringprojectcs3773.SceneNavigator;
import edu.softwareengineeringprojectcs3773.model.Account;
import edu.softwareengineeringprojectcs3773.model.Cart;
import edu.softwareengineeringprojectcs3773.model.CartItem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckoutController {

    private static final String STANDARD_DELIVERY =
            "Standard Delivery";

    private static final String EXPRESS_DELIVERY =
            "Express Delivery";

    private static final String STORE_PICKUP =
            "Store Pickup";

    private static final String CREDIT_CARD =
            "Credit Card";

    private static final String DEBIT_CARD =
            "Debit Card";

    private static final double STANDARD_DELIVERY_FEE = 4.99;
    private static final double EXPRESS_DELIVERY_FEE = 9.99;
    private static final double STORE_PICKUP_FEE = 0.00;

    private CheckoutController checkoutController;

    @FXML
    private Button backToCartButton;

    @FXML
    private TextField checkoutNameField;

    @FXML
    private TextField checkoutPhoneField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField stateField;

    @FXML
    private TextField zipField;

    @FXML
    private CheckBox saveAddressCheckBox;

    @FXML
    private ComboBox<String> deliveryMethodComboBox;

    @FXML
    private ComboBox<String> paymentMethodComboBox;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField expirationField;

    @FXML
    private PasswordField cvvField;

    @FXML
    private Label checkoutSubtotalLabel;

    @FXML
    private Label deliveryFeeLabel;

    @FXML
    private Label checkoutTaxLabel;

    @FXML
    private Label checkoutTotalLabel;

    @FXML
    private Button placeOrderButton;

    @FXML
    private Label checkoutMessageLabel;


    @FXML
    private void initialize() {
        configureDeliveryMethods();
        configurePaymentMethods();
        configureActions();
        configureInputFormatting();

        loadCurrentAccount();
        refreshCheckout();
    }

    private void configureDeliveryMethods() {
        deliveryMethodComboBox.getItems().setAll(
                STANDARD_DELIVERY,
                EXPRESS_DELIVERY,
                STORE_PICKUP
        );

        deliveryMethodComboBox.getSelectionModel().select(
                STANDARD_DELIVERY
        );

        deliveryMethodComboBox.valueProperty().addListener(
                (observable, oldValue, newValue) ->
                        refreshSummary()
        );
    }

    private void configurePaymentMethods() {
        paymentMethodComboBox.getItems().setAll(
                CREDIT_CARD,
                DEBIT_CARD
        );

        paymentMethodComboBox.getSelectionModel().select(
                CREDIT_CARD
        );
    }

    private void configureActions() {
        backToCartButton.setOnAction(event ->
                handleBackToCart()
        );

        placeOrderButton.setOnAction(event ->
                handlePlaceOrder()
        );
    }

    private void configureInputFormatting() {
        stateField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    String formatted = newValue
                            .replaceAll("[^a-zA-Z]", "")
                            .toUpperCase();

                    if (formatted.length() > 2) {
                        formatted = formatted.substring(0, 2);
                    }

                    if (!formatted.equals(newValue)) {
                        stateField.setText(formatted);
                    }
                }
        );

        zipField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    String formatted =
                            newValue.replaceAll("[^0-9]", "");

                    if (formatted.length() > 5) {
                        formatted = formatted.substring(0, 5);
                    }

                    if (!formatted.equals(newValue)) {
                        zipField.setText(formatted);
                    }
                }
        );

        cvvField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    String formatted =
                            newValue.replaceAll("[^0-9]", "");

                    if (formatted.length() > 4) {
                        formatted = formatted.substring(0, 4);
                    }

                    if (!formatted.equals(newValue)) {
                        cvvField.setText(formatted);
                    }
                }
        );
    }

    private void loadCurrentAccount() {
        Account account =
                ApplicationState.getCurrentAccount();

        if (account == null) {
            showMessage(
                    "You must be logged in to check out.",
                    true
            );

            placeOrderButton.setDisable(true);
            return;
        }

        checkoutPhoneField.setText(
                safeText(account.getPhoneNumber())
        );

        /*
         * Database integration:
         *
         * Use AddressService to load the account's default
         * delivery address.
         *
         * Example:
         *
         * Address defaultAddress =
         *         addressService.getDefaultAddress(
         *                 account.getAccountId()
         *         );
         *
         * Then populate the street, city, state, and ZIP fields.
         *
         * AddressService should call AddressRepository. This
         * controller should not execute SQL.
         *
         * The current Account model contains a username, email,
         * password, and phone number, but no full-name field.
         * Leave checkoutNameField blank until that data is
         * available from the account or customer database.
         */
    }

    /**
     * Refreshes the screen whenever it is opened.
     */
    public void refreshCheckout() {
        Cart cart = getCart();

        if (cart == null || cart.isEmpty()) {
            setEmptySummary();

            placeOrderButton.setDisable(true);

            showMessage(
                    "Your cart is empty.",
                    true
            );

            return;
        }

        placeOrderButton.setDisable(
                ApplicationState.getCurrentAccount() == null
        );

        clearMessage();
        refreshSummary();
    }

    private void refreshSummary() {
        Cart cart = getCart();

        if (cart == null) {
            setEmptySummary();
            return;
        }

        double subtotal = cart.getSubtotal();
        double tax = cart.getTax();
        double deliveryFee = getDeliveryFee();
        double total = cart.getTotal() + deliveryFee;

        checkoutSubtotalLabel.setText(
                formatMoney(subtotal)
        );

        deliveryFeeLabel.setText(
                formatMoney(deliveryFee)
        );

        checkoutTaxLabel.setText(
                formatMoney(tax)
        );

        checkoutTotalLabel.setText(
                formatMoney(total)
        );
    }

    @FXML
    private void handleBackToCart() {
        SceneNavigator.showCart();
    }

    @FXML
    private void handlePlaceOrder() {
        clearMessage();

        Cart cart = getCart();
        Account account =
                ApplicationState.getCurrentAccount();

        if (account == null) {
            showMessage(
                    "You must be logged in to place an order.",
                    true
            );
            return;
        }

        if (cart == null || cart.isEmpty()) {
            showMessage(
                    "Your cart is empty.",
                    true
            );
            return;
        }

        String validationMessage =
                validateCheckoutInformation();

        if (validationMessage != null) {
            showMessage(validationMessage, true);
            return;
        }

        placeOrderButton.setDisable(true);

        try {
            double subtotal = cart.getSubtotal();
            double tax = cart.getTax();
            double deliveryFee = getDeliveryFee();

            double orderTotal =
                    cart.getTotal() + deliveryFee;

            String deliveryMethod =
                    deliveryMethodComboBox.getValue();

            String deliveryAddress =
                    buildDeliveryAddress();

            List<CartItem> orderedItems =
                    new ArrayList<>();

            for (CartItem cartItem : cart.getItems()) {
                orderedItems.add(
                        new CartItem(
                                cartItem.getItem(),
                                cartItem.getQuantity()
                        )
                );
            }

            /*
             * Payment-gateway integration:
             *
             * Do not save the raw card number or CVV in the
             * application database.
             *
             * Send payment information to PaymentService. The
             * PaymentService should communicate with the payment
             * gateway and return a payment token or transaction
             * identifier.
             *
             * Example:
             *
             * PaymentResult result =
             *         paymentService.authorizePayment(
             *                 orderTotal,
             *                 cardNumber,
             *                 expiration,
             *                 cvv
             *         );
             *
             * Only continue when result.isSuccessful() is true.
             */

            /*
             * Database integration:
             *
             * 1. Save the address when saveAddressCheckBox is
             *    selected by calling AddressService.
             *
             * 2. Call OrderService.placeOrder(...) to create:
             *      - the Order record
             *      - its OrderItem records
             *      - delivery information
             *      - the payment transaction reference
             *
             * 3. Update inventory through ItemService or an
             *    InventoryService.
             *
             * 4. Clear the persisted cart through CartService.
             *
             * These operations should ideally occur in one
             * database transaction.
             */

            String temporaryOrderNumber =
                    createTemporaryOrderNumber();

            final String finalOrderNumber = temporaryOrderNumber;
            final String finalDeliveryMethod = deliveryMethod;
            final double finalOrderTotal = orderTotal;
            final String finalEmail = account.getEmail();
            final String finalDeliveryAddress = deliveryAddress;
            final double finalSubtotal = subtotal;
            final double finalTax = tax;
            final double finalDeliveryFee = deliveryFee;


            /*
             * Clear sensitive payment fields immediately after
             * the temporary order has been accepted.
             */
            clearPaymentFields();

            /*
             * Keep the cart available until the confirmation
             * data has been prepared. Once database integration
             * exists, OrderService should clear the cart only
             * after the order transaction succeeds.
             */


            cart.clear();

            SceneNavigator.showScene(
                    "order-confirmation-screen.fxml",
                    (OrderConfirmationController controller) -> {
                        controller.setConfirmationData(
                                finalOrderNumber,
                                finalDeliveryMethod,
                                finalOrderTotal,
                                finalEmail
                        );
                        controller.setOrderDetailsData(
                                finalOrderNumber,
                                LocalDate.now(),
                                "Processing",
                                orderedItems,
                                finalDeliveryAddress,
                                finalDeliveryMethod,
                                finalSubtotal,
                                finalTax,
                                finalDeliveryFee,
                                finalOrderTotal
                        );
                    }
            );

        } catch (Exception exception) {
            showMessage(
                    "The order could not be placed. Please try again.",
                    true
            );

            placeOrderButton.setDisable(false);
            exception.printStackTrace();
        }
    }

    private String validateCheckoutInformation() {
        if (isBlank(checkoutNameField)) {
            return "Enter the name for the delivery.";
        }

        if (isBlank(checkoutPhoneField)) {
            return "Enter a phone number.";
        }

        if (isBlank(streetField)) {
            return "Enter a street address.";
        }

        if (isBlank(cityField)) {
            return "Enter a city.";
        }

        if (stateField.getText().trim().length() != 2) {
            return "Enter a two-letter state abbreviation.";
        }

        if (!zipField.getText().trim().matches("\\d{5}")) {
            return "Enter a valid five-digit ZIP code.";
        }

        if (deliveryMethodComboBox.getValue() == null) {
            return "Select a delivery method.";
        }

        if (paymentMethodComboBox.getValue() == null) {
            return "Select a payment method.";
        }

        String cardNumber = normalizedCardNumber();

        if (!cardNumber.matches("\\d{13,19}")) {
            return "Enter a valid card number.";
        }

        if (!isValidExpiration(
                expirationField.getText().trim()
        )) {
            return "Enter the expiration date as MM/YY.";
        }

        if (!cvvField.getText().trim().matches("\\d{3,4}")) {
            return "Enter a valid CVV.";
        }

        return null;
    }

    private boolean isValidExpiration(String expiration) {
        if (!expiration.matches(
                "(0[1-9]|1[0-2])/\\d{2}"
        )) {
            return false;
        }

        /*
         * Payment-gateway integration:
         *
         * The payment provider will perform the authoritative
         * expiration and card validation.
         *
         * A later local check may also reject cards whose
         * expiration month has already passed.
         */

        return true;
    }

    private double getDeliveryFee() {
        String deliveryMethod =
                deliveryMethodComboBox.getValue();

        if (EXPRESS_DELIVERY.equals(deliveryMethod)) {
            return EXPRESS_DELIVERY_FEE;
        }

        if (STORE_PICKUP.equals(deliveryMethod)) {
            return STORE_PICKUP_FEE;
        }

        return STANDARD_DELIVERY_FEE;
    }

    private String createTemporaryOrderNumber() {
        /*
         * Database integration:
         *
         * Replace this temporary number with the order ID or
         * confirmation number generated by OrderService after
         * the Order record is successfully inserted.
         */
        return "#UTSA-" + System.currentTimeMillis();
    }

    private String normalizedCardNumber() {
        return cardNumberField
                .getText()
                .replaceAll("[\\s-]", "");
    }

    private boolean isBlank(TextField field) {
        return field.getText() == null
                || field.getText().trim().isEmpty();
    }

    private void clearPaymentFields() {
        cardNumberField.clear();
        expirationField.clear();
        cvvField.clear();
    }

    private void setEmptySummary() {
        checkoutSubtotalLabel.setText("$0.00");
        deliveryFeeLabel.setText("$0.00");
        checkoutTaxLabel.setText("$0.00");
        checkoutTotalLabel.setText("$0.00");
    }

    private Cart getCart() {
        return ApplicationState.getCurrentCart();
    }

    private String safeText(String value) {
        return value == null ? "" : value;
    }

    private String formatMoney(double amount) {
        return String.format("$%.2f", amount);
    }

    private void showMessage(
            String message,
            boolean error
    ) {
        checkoutMessageLabel.setText(message);

        checkoutMessageLabel.getStyleClass().remove(
                "error-label"
        );

        checkoutMessageLabel.getStyleClass().remove(
                "status-label"
        );

        checkoutMessageLabel.getStyleClass().add(
                error
                        ? "error-label"
                        : "status-label"
        );
    }

    private void clearMessage() {
        checkoutMessageLabel.setText("");
    }

    private String buildDeliveryAddress() {
        return checkoutNameField.getText().trim()
                + "\n"
                + streetField.getText().trim()
                + "\n"
                + cityField.getText().trim()
                + ", "
                + stateField.getText().trim()
                + " "
                + zipField.getText().trim();
    }

}
