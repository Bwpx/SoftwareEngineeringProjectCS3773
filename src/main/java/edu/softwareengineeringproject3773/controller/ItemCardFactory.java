package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.model.Item;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.util.function.Consumer;

public class ItemCardFactory {

    private static final String ITEM_CARD_FXML =
            "/edu/softwareengineeringprojectcs3773/item-card.fxml";

    private ItemCardFactory() {

    }

    public static Node createItemCard(
            Item item, Consumer<Item> viewDetailsHandler) throws IOException {
        FXMLLoader loader = new FXMLLoader(ItemCardFactory.class.getResource(ITEM_CARD_FXML));

        Node itemCard = loader.load();

        ItemCardController controller = loader.getController();
        controller.setItem(item);
        controller.setOnViewDetails(viewDetailsHandler);

        return itemCard;
    }
}
