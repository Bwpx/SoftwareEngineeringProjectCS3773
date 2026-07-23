package edu.softwareengineeringproject3773.controller;
import edu.softwareengineeringprojectcs3773.ApplicationState;
import edu.softwareengineeringprojectcs3773.SceneNavigator;
import edu.softwareengineeringprojectcs3773.model.Cart;
import edu.softwareengineeringprojectcs3773.model.GroceryItem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;

public class ItemDetailsController {
    @FXML private Button backToBrowseButton;
    @FXML private Button viewCartButton;
    @FXML private Button addToCartButton;
    @FXML private Button buyNowButton;
    @FXML private ImageView itemImageView;
    @FXML private Label itemNameLabel;
    @FXML private Label itemPriceLabel;
    @FXML private Label availabilityLabel;
    @FXML private Label itemDescriptionLabel;
    @FXML private Label itemMessageLabel;
    @FXML private Spinner<Integer> quantitySpinner;

    private GroceryItem item;

    @FXML
    private void initialize() {
        backToBrowseButton.setOnAction(event -> SceneNavigator.showBrowseItems());
        viewCartButton.setOnAction(event -> SceneNavigator.showCart());
        addToCartButton.setOnAction(event -> addSelectedQuantity(false));
        buyNowButton.setOnAction(event -> addSelectedQuantity(true));
    }

    public void setItem(GroceryItem item) {
        this.item = item;
        if (item == null) {
            return;
        }

        itemNameLabel.setText(item.getItemName());
        itemPriceLabel.setText(String.format("$%.2f", item.getPrice()));
        availabilityLabel.setText(item.isInStock() ? "In Stock" : "Out of Stock");
        itemDescriptionLabel.setText("Category: " + item.getCategory());

        int maximum = Math.max(1, item.getQuantityInStock());
        quantitySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maximum, 1)
        );

        addToCartButton.setDisable(!item.isInStock());
        buyNowButton.setDisable(!item.isInStock());
    }

    private void addSelectedQuantity(boolean checkoutImmediately) {
        Cart cart = ApplicationState.getCurrentCart();
        if (cart == null) {
            itemMessageLabel.setText("Log in before adding items to your cart.");
            return;
        }
        if (item == null) {
            itemMessageLabel.setText("No item is selected.");
            return;
        }

        int quantity = quantitySpinner.getValue();
        for (int i = 0; i < quantity; i++) {
            cart.addItem(item);
        }

        if (checkoutImmediately) {
            SceneNavigator.showCheckout();
        } else {
            itemMessageLabel.setText(item.getItemName() + " added to cart.");
        }
    }
}
