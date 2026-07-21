package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.model.GroceryItem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.function.Consumer;

public class ItemCardController {
    @FXML
    private ImageView itemImageView;

    @FXML
    private Label imagePlaceholderLabel;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label itemPriceLabel;

    @FXML
    private Label availabilityLabel;

    @FXML
    private Button viewDetailsButton;

    @FXML
    private Button addToCartButton;

    private GroceryItem item;

    /*
     * The parent screen supplies this callback.
     * This keeps the card independent from scene navigation.
     */

    private Consumer<GroceryItem> viewDetailsHandler;
    private Consumer<GroceryItem> addToCartHandler;
    public void setItem(GroceryItem item) {
        this.item = item;

        if (item == null) {
            clearCard();
            return;
        }

        itemNameLabel.setText(item.getItemName());
        categoryLabel.setText(item.getCategory());
        itemPriceLabel.setText(String.format("$%.2f", item.getPrice()));

        updateAvailability();
        loadItemImage();
    }

    public GroceryItem getItem() {
        return item;
    }

    public void setOnViewDetails(
            Consumer<GroceryItem> viewDetailsHandler
    ) {
        this.viewDetailsHandler = viewDetailsHandler;
    }

    public void setOnAddToCart(
            Consumer<GroceryItem> addToCartHandler
    ) {
        this.addToCartHandler = addToCartHandler;
    }

    @FXML
    private void handleViewDetails() {
        if (item != null && viewDetailsHandler != null) {
            viewDetailsHandler.accept(item);
        }
    }

    @FXML
    private void handleAddToCart() {
        if (item != null
                && item.isInStock()
                && addToCartHandler != null) {

            addToCartHandler.accept(item);
        }
    }

    private void updateAvailability() {
        if (item.isInStock()) {
            availabilityLabel.setText(
                    item.getQuantityInStock() + " in stock"
            );

            availabilityLabel.getStyleClass().remove(
                    "unavailable-label"
            );

            if (!availabilityLabel.getStyleClass().contains(
                    "availability-label"
            )) {
                availabilityLabel.getStyleClass().add(
                        "availability-label"
                );
            }

            addToCartButton.setDisable(false);
        } else {
            availabilityLabel.setText("Out of Stock");

            availabilityLabel.getStyleClass().remove(
                    "availability-label"
            );

            if (!availabilityLabel.getStyleClass().contains(
                    "unavailable-label"
            )) {
                availabilityLabel.getStyleClass().add(
                        "unavailable-label"
                );
            }

            addToCartButton.setDisable(true);
        }
    }

    private void loadItemImage() {
        itemImageView.setImage(null);
        showImagePlaceholder(true);

        /*
         *
         * This convention lets us add images without changing
         * repository or database code.
         *
         * Example:
         * /images/items/milk.png
         */
        String imageName = item.getItemName()
                .toLowerCase()
                .replace(" ", "-");

        String imagePath =
                "/edu/softwareengineeringprojectcs3773/images/items/"
                        + imageName
                        + ".png";

        URL imageUrl = getClass().getResource(imagePath);

        if (imageUrl == null) {
            return;
        }

        try {
            Image image = new Image(
                    imageUrl.toExternalForm(),
                    true
            );

            itemImageView.setImage(image);
            showImagePlaceholder(false);

        } catch (Exception exception) {
            System.err.println(
                    "Could not load item image: " + imagePath
            );
        }
    }

    private void showImagePlaceholder(boolean show) {
        imagePlaceholderLabel.setVisible(show);
        imagePlaceholderLabel.setManaged(show);
    }

    private void clearCard() {
        itemNameLabel.setText("");
        categoryLabel.setText("");
        itemPriceLabel.setText("");
        availabilityLabel.setText("");

        itemImageView.setImage(null);
        showImagePlaceholder(true);

        addToCartButton.setDisable(true);
    }
}

