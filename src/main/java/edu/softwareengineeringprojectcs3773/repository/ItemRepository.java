package edu.softwareengineeringprojectcs3773.repository;

import edu.softwareengineeringprojectcs3773.model.GroceryItem;
import java.util.ArrayList;

public class ItemRepository {
    private ArrayList<GroceryItem> items;

    public ItemRepository() {
        items = new ArrayList<>();
        loadSampleItems();
    }

    public void save(GroceryItem item) {
        items.add(item);
    }

    public GroceryItem findById(int itemId) {
        for (GroceryItem item : items) {
            if (item.getItemId() == itemId) {
                return item;
            }
        }

        return null;
    }

    public ArrayList<GroceryItem> findAll() {
        return items;
    }

    public ArrayList<GroceryItem> searchByName(String searchTerm) {
        ArrayList<GroceryItem> results = new ArrayList<>();

        for (GroceryItem item : items) {
            if (item.nameContains(searchTerm)) {
                results.add(item);
            }
        }

        return results;
    }

    public ArrayList<GroceryItem> searchByCategory(String category) {
        ArrayList<GroceryItem> results = new ArrayList<>();

        for (GroceryItem item : items) {
            if (item.categoryMatches(category)) {
                results.add(item);
            }
        }

        return results;
    }

    public ArrayList<GroceryItem> findInStockItems() {
        ArrayList<GroceryItem> results = new ArrayList<>();

        for (GroceryItem item : items) {
            if (item.isInStock()) {
                results.add(item);
            }
        }

        return results;
    }

    private void loadSampleItems() {
        items.add(new GroceryItem(1, "Milk", "Dairy", 3.49, 20));
        items.add(new GroceryItem(2, "Eggs", "Dairy", 4.29, 15));
        items.add(new GroceryItem(3, "Bread", "Bakery", 2.99, 30));
        items.add(new GroceryItem(4, "Apples", "Produce", 1.49, 50));
        items.add(new GroceryItem(5, "Chicken Breast", "Meat", 8.99, 12));
        items.add(new GroceryItem(6, "Rice", "Pantry", 5.99, 25));
    }
}
