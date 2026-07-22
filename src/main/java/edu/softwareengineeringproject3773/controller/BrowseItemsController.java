package edu.softwareengineeringproject3773.controller;

import edu.softwareengineeringprojectcs3773.model.GroceryItem;
import edu.softwareengineeringprojectcs3773.service.ItemService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class BrowseItemsController {

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private ComboBox<String> sortComboBox;

    @FXML
    private CheckBox availableOnlyCheckBox;

    @FXML
    private TilePane itemsTilePane;

    private final ItemService itemService = new ItemService();

    private ArrayList<GroceryItem> allItems;

    @FXML
    private void initialize() {
        allItems = itemService.getAllItems();

        categoryComboBox.getSelectionModel().select(
                "All Categories"
        );

        displayItems(allItems);
    }

    private void displayItems(List<GroceryItem> items) {
        itemsTilePane.getChildren().clear();

        for (GroceryItem item : items) {
            try {
                Node itemCard = ItemCardFactory.create(
                        item,
                        this::openItemDetails,
                        this::addItemToCart
                );

                itemsTilePane.getChildren().add(itemCard);

            } catch (IOException exception) {
                System.err.println(
                        "Unable to create card for item: "
                                + item.getItemName()
                );

                exception.printStackTrace();
            }
        }
    }

    @FXML
    private void handleSearch() {
        applyFilters();
    }

    @FXML
    private void handleFiltersChanged() {
        applyFilters();
    }

    @FXML
    private void handleClearFilters() {
        searchField.clear();

        categoryComboBox.getSelectionModel().select(
                "All Categories"
        );

        sortComboBox.getSelectionModel().clearSelection();
        availableOnlyCheckBox.setSelected(false);

        displayItems(allItems);
    }

    private void applyFilters() {
        String searchText = searchField
                .getText()
                .trim()
                .toLowerCase(Locale.ROOT);

        String selectedCategory = categoryComboBox.getValue();

        List<GroceryItem> filteredItems =
                new ArrayList<>(allItems);

        filteredItems.removeIf(item ->
                !searchText.isEmpty()
                        && !item.nameContains(searchText)
        );

        if (selectedCategory != null
                && !selectedCategory.equals("All Categories")) {

            filteredItems.removeIf(item ->
                    !item.categoryMatches(selectedCategory)
            );
        }

        if (availableOnlyCheckBox.isSelected()) {
            filteredItems.removeIf(item -> !item.isInStock());
        }

        sortItems(filteredItems);

        displayItems(filteredItems);
    }

    private void sortItems(List<GroceryItem> items) {
        String sortSelection = sortComboBox.getValue();

        if ("Price: Low to High".equals(sortSelection)) {
            items.sort(
                    Comparator.comparingDouble(
                            GroceryItem::getPrice
                    )
            );

        } else if ("Price: High to Low".equals(sortSelection)) {
            items.sort(
                    Comparator.comparingDouble(
                            GroceryItem::getPrice
                    ).reversed()
            );

        } else if ("Availability".equals(sortSelection)) {
            items.sort(
                    Comparator.comparing(
                            GroceryItem::isInStock
                    ).reversed()
            );
        }
    }

    private void openItemDetails(GroceryItem item) {
        System.out.println(
                "Open details for item ID: "
                        + item.getItemId()
        );

        /*
         * Later:
         * ItemDetailsController detailsController =
         *         MainController.showItemDetails();
         *
         * detailsController.setItem(item);
         */
    }

    private void addItemToCart(GroceryItem item) {
        System.out.println(
                "Add to cart: " + item.getItemName()
        );

        /*
         * This will later call the existing Cart model or a
         * CartService once we inspect those classes.
         */
    }

    @FXML
    private void handleHome() {
        System.out.println("Open home");
    }

    @FXML
    private void handleCart() {
        System.out.println("Open cart");
    }

    @FXML
    private void handleOrders() {
        System.out.println("Open order history");
    }

}