package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.model.GroceryItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public class ItemCardFactory {

    private static final String ITEM_CARD_FXML =
            "/edu/softwareengineeringprojectcs3773/item-card.fxml";

    private ItemCardFactory() {

    }

    public static Node create(
            GroceryItem item, Consumer<GroceryItem> viewDetailsHandler, Consumer<GroceryItem> addToCartHandler)
            throws IOException {
             URL resource = ItemCardFactory.class.getResource(
                ITEM_CARD_FXML);

        if (resource == null) {
            throw new IOException(
                    "Could not locate " + ITEM_CARD_FXML
            );
        }

        FXMLLoader loader = new FXMLLoader(resource);

        Node card = loader.load();

        ItemCardController controller = loader.getController();

        controller.setItem(item);
        controller.setOnViewDetails(viewDetailsHandler);
        controller.setOnAddToCart(addToCartHandler);

        return card;
    }
}
