package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.ApplicationState;
import edu.softwareengineeringprojectcs3773.SceneNavigator;
import edu.softwareengineeringprojectcs3773.model.Cart;
import edu.softwareengineeringprojectcs3773.model.CartItem;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class CartController {

    @FXML
    private TableView<CartItem> cartTable;

    @FXML
    private TableColumn<CartItem, String> cartItemColumn;

    @FXML
    private TableColumn<CartItem, String> cartPriceColumn;

    @FXML
    private TableColumn<CartItem, CartItem> cartQuantityColumn;

    @FXML
    private TableColumn<CartItem, String> cartSubtotalColumn;

    @FXML
    private TableColumn<CartItem, CartItem> cartActionColumn;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label discountLabel;

    @FXML
    private Label taxLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private TextField discountCodeField;

    @FXML
    private Label cartMessageLabel;

    @FXML
    private Button continueShoppingButton;

    @FXML
    private Button updateCartButton;

    @FXML
    private Button clearCartButton;

    @FXML
    private Button applyDiscountButton;

    @FXML
    private Button checkoutButton;


    @FXML
    private void initialize() {
        configureColumns();
        configureButtons();
        refreshCart();
    }

    private void configureColumns() {
        cartItemColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(
                        cellData.getValue()
                                .getItem()
                                .getItemName()
                )
        );

        cartPriceColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(
                        formatMoney(
                                cellData.getValue()
                                        .getItem()
                                        .getPrice()
                        )
                )
        );

        cartSubtotalColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(
                        formatMoney(
                                cellData.getValue()
                                        .getSubtotal()
                        )
                )
        );

        cartQuantityColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(
                        cellData.getValue()
                )
        );

        cartQuantityColumn.setCellFactory(column ->
                createQuantityCell()
        );

        cartActionColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(
                        cellData.getValue()
                )
        );

        cartActionColumn.setCellFactory(column ->
                createRemoveButtonCell()
        );
    }

    private TableCell<CartItem, CartItem>
    createQuantityCell() {

        return new TableCell<>() {

            private final Spinner<Integer> quantitySpinner =
                    new Spinner<>();

            {
                quantitySpinner.setEditable(true);
                quantitySpinner.setPrefWidth(90.0);
            }

            @Override
            protected void updateItem(
                    CartItem cartItem,
                    boolean empty
            ) {
                super.updateItem(cartItem, empty);

                if (empty || cartItem == null) {
                    setGraphic(null);
                    return;
                }

                int maximumQuantity = Math.max(
                        1,
                        cartItem.getItem()
                                .getQuantityInStock()
                );

                SpinnerValueFactory.IntegerSpinnerValueFactory
                        valueFactory =
                        new SpinnerValueFactory
                                .IntegerSpinnerValueFactory(
                                1,
                                maximumQuantity,
                                cartItem.getQuantity()
                        );

                quantitySpinner.setValueFactory(
                        valueFactory
                );

                valueFactory.valueProperty().addListener(
                        (observable, oldValue, newValue) -> {
                            if (newValue == null) {
                                return;
                            }

                            updateItemQuantity(
                                    cartItem,
                                    newValue
                            );
                        }
                );

                setGraphic(quantitySpinner);
            }
        };
    }

    private TableCell<CartItem, CartItem>
    createRemoveButtonCell() {

        return new TableCell<>() {

            private final Button removeButton =
                    new Button("Remove");

            {
                removeButton.getStyleClass().add(
                        "danger-button"
                );
            }

            @Override
            protected void updateItem(
                    CartItem cartItem,
                    boolean empty
            ) {
                super.updateItem(cartItem, empty);

                if (empty || cartItem == null) {
                    setGraphic(null);
                    return;
                }

                removeButton.setOnAction(event ->
                        removeCartItem(cartItem)
                );

                setGraphic(removeButton);
            }
        };
    }

    private void configureButtons() {
        continueShoppingButton.setOnAction(event ->
                handleContinueShopping()
        );

        updateCartButton.setOnAction(event ->
                handleUpdateCart()
        );

        clearCartButton.setOnAction(event ->
                handleClearCart()
        );

        applyDiscountButton.setOnAction(event ->
                handleApplyDiscount()
        );

        checkoutButton.setOnAction(event ->
                handleCheckout()
        );
    }

    private void updateItemQuantity(
            CartItem cartItem,
            int quantity
    ) {
        Cart cart = getCart();

        if (cart == null) {
            showMessage(
                    "No active cart is available.",
                    true
            );
            return;
        }

        boolean updated = cart.updateQuantity(
                cartItem.getItem().getItemId(),
                quantity
        );

        if (!updated) {
            showMessage(
                    "The requested quantity is unavailable.",
                    true
            );
        } else {
            clearMessage();
        }

        /*
         * Database integration:
         *
         * Replace or supplement this in-memory update with:
         *
         * cartService.updateQuantity(
         *         currentAccountId,
         *         itemId,
         *         quantity
         * );
         *
         * CartService should delegate persistence to
         * CartRepository.
         */

        refreshCart();
    }

    private void removeCartItem(CartItem cartItem) {
        Cart cart = getCart();

        if (cart == null) {
            return;
        }

        cart.removeItem(
                cartItem.getItem().getItemId()
        );

        /*
         * Database integration:
         *
         * Call CartService.removeItem(accountId, itemId).
         * Database access should remain inside CartRepository.
         */

        showMessage(
                cartItem.getItem().getItemName()
                        + " was removed from the cart.",
                false
        );

        refreshCart();
    }

    @FXML
    private void handleUpdateCart() {
        /*
         * Quantity changes are currently applied immediately by
         * the quantity spinners.
         *
         * Database integration:
         *
         * This button may later call CartService.saveCart(...)
         * if the database implementation batches cart changes.
         */

        refreshCart();

        showMessage(
                "Cart updated.",
                false
        );
    }

    @FXML
    private void handleClearCart() {
        Cart cart = getCart();

        if (cart == null || cart.isEmpty()) {
            showMessage(
                    "The cart is already empty.",
                    false
            );
            return;
        }

        cart.clear();

        /*
         * Database integration:
         *
         * Call CartService.clearCart(accountId).
         */

        discountCodeField.clear();

        showMessage(
                "Cart cleared.",
                false
        );

        refreshCart();
    }

    @FXML
    private void handleApplyDiscount() {
        Cart cart = getCart();

        if (cart == null) {
            showMessage(
                    "No active cart is available.",
                    true
            );
            return;
        }

        String enteredCode =
                discountCodeField.getText();

        if (cart.applyDiscountCode(enteredCode)) {
            discountCodeField.setText(
                    cart.getDiscountCode()
            );

            showMessage(
                    "Discount code applied.",
                    false
            );
        } else {
            showMessage(
                    "That discount code is invalid.",
                    true
            );
        }

        refreshSummary();
    }

    @FXML
    private void handleContinueShopping() {
        SceneNavigator.showBrowseItems();
    }

    @FXML
    private void handleCheckout() {
        Cart cart = getCart();

        if (cart == null || cart.isEmpty()) {
            showMessage(
                    "Add at least one item before checking out.",
                    true
            );
            return;
        }

        /*
         * Database integration:
         *
         * CheckoutController will eventually obtain the active
         * database-backed cart through CartService.
         *
         * Do not create the Order record until the customer
         * confirms checkout.
         */

        SceneNavigator.showCheckout();

    }

    public void refreshCart() {
        Cart cart = getCart();

        if (cart == null) {
            cartTable.setItems(
                    FXCollections.observableArrayList()
            );

            setEmptySummary();

            checkoutButton.setDisable(true);
            updateCartButton.setDisable(true);
            clearCartButton.setDisable(true);

            showMessage(
                    "Log in to view your shopping cart.",
                    true
            );

            return;
        }

        cartTable.setItems(
                FXCollections.observableArrayList(
                        cart.getItems()
                )
        );

        cartTable.refresh();
        refreshSummary();

        boolean cartIsEmpty = cart.isEmpty();

        checkoutButton.setDisable(cartIsEmpty);
        updateCartButton.setDisable(cartIsEmpty);
        clearCartButton.setDisable(cartIsEmpty);
    }

    private void refreshSummary() {
        Cart cart = getCart();

        if (cart == null) {
            setEmptySummary();
            return;
        }

        subtotalLabel.setText(
                formatMoney(cart.getSubtotal())
        );

        discountLabel.setText(
                "-" + formatMoney(
                        cart.getDiscountAmount()
                )
        );

        taxLabel.setText(
                formatMoney(cart.getTax())
        );

        totalLabel.setText(
                formatMoney(cart.getTotal())
        );
    }

    private void setEmptySummary() {
        subtotalLabel.setText("$0.00");
        discountLabel.setText("-$0.00");
        taxLabel.setText("$0.00");
        totalLabel.setText("$0.00");
    }

    private Cart getCart() {
        return ApplicationState.getCurrentCart();
    }

    private String formatMoney(double amount) {
        return String.format("$%.2f", amount);
    }

    private void showMessage(
            String message,
            boolean error
    ) {
        cartMessageLabel.setText(message);

        cartMessageLabel.getStyleClass().remove(
                "error-label"
        );

        cartMessageLabel.getStyleClass().remove(
                "status-label"
        );

        cartMessageLabel.getStyleClass().add(
                error
                        ? "error-label"
                        : "status-label"
        );
    }

    private void clearMessage() {
        cartMessageLabel.setText("");
    }

}