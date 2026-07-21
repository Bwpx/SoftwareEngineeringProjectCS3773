package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.model.Item;
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
    private Label itemDescriptionLabel;

    @FXML
    private Label itemPriceLabel;

    @FXML
    private Label availabilityLabel;

    @FXML
    private Button viewDetailsButton;

    private Item item;

    /*
     * The parent screen supplies this callback.
     * This keeps the card independent from scene navigation.
     */

    private Consumer<Item> viewDetailsHandler;

    public void setItem(Item item) {
        this.item = item;

        if (item == null) {
            clearCard();
            return;
        }

        itemNameLabel.setText(item.getName());
        itemDescriptionLabel.setText(item.getDescription());
        itemPriceLabel.setText(String.format("$%.2f", item.getPrice()));

        updateAvailability();
        loadImage(item.getImagePath());
    }

    public Item getItem() {
        return item;
    }

    public void setOnViewDetails(Consumer<Item> viewDetailsHandler) {
        this.viewDetailsHandler = viewDetailsHandler;
    }

    @FXML
    private void handleViewDetails() {
        if (item != null && viewDetailsHandler != null) {
            viewDetailsHandler.accept(item);
        }
    }

    private void updateAvailability() {
        if (item.isAvailable()) {
            availabilityLabel.setText(
                    "In Stock (" + item.getQuantityAvailable() + ")"
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

            viewDetailsButton.setDisable(false);
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
            /*
             * Viewing details can remain enabled even when an item
             * cannot currently be purchased.
             */
            viewDetailsButton.setDisable(false);
        }
    }

    private void loadImage(String imagePath) {
        itemImageView.setImage(null);
        imagePlaceholderLabel.setVisible(true);
        imagePlaceholderLabel.setManaged(true);

        if (imagePath == null || imagePath.isBlank()) {
            return;
        }

        try {
            URL imageUrl = getClass().getResource(imagePath);

            if (imageUrl == null) {
                System.err.println(
                        "Item image was not found: " + imagePath
                );
                return;
            }

            Image image = new Image(
                    imageUrl.toExternalForm(),
                    true
            );

            itemImageView.setImage(image);
            imagePlaceholderLabel.setVisible(false);
            imagePlaceholderLabel.setManaged(false);

        } catch (Exception exception) {
            System.err.println(
                    "Unable to load item image: " + imagePath
            );
        }
    }

    private void clearCard() {
        itemNameLabel.setText("");
        itemDescriptionLabel.setText("");
        itemPriceLabel.setText("");
        availabilityLabel.setText("");

        itemImageView.setImage(null);
        imagePlaceholderLabel.setVisible(true);
        imagePlaceholderLabel.setManaged(true);
    }
}
