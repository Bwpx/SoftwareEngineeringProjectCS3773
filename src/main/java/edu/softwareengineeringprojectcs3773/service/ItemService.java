package edu.softwareengineeringprojectcs3773.service;

import edu.softwareengineeringprojectcs3773.model.GroceryItem;
import edu.softwareengineeringprojectcs3773.repository.ItemRepository;

import java.util.ArrayList;

public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService() {
        itemRepository = new ItemRepository();
        itemRepository.loadSampleItems();
    }

    public boolean addItem(
            String itemName,
            String category,
            double price,
            int quantityInStock
    ) {
        if (itemName == null || itemName.isBlank()) {
            return false;
        }

        if (category == null || category.isBlank()) {
            return false;
        }

        if (price < 0) {
            return false;
        }

        if (quantityInStock < 0) {
            return false;
        }

        GroceryItem newItem = new GroceryItem(
                0,
                itemName.trim(),
                category.trim(),
                price,
                quantityInStock
        );

        return itemRepository.save(newItem) != null;
    }

    public ArrayList<GroceryItem> getAllItems() {
        return itemRepository.findAll();
    }

    public GroceryItem getItemById(int itemId) {
        if (itemId <= 0) {
            return null;
        }

        return itemRepository.findById(itemId);
    }

    public ArrayList<GroceryItem> searchItemsByName(String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank()) {
            return new ArrayList<>();
        }

        return itemRepository.searchByName(searchTerm.trim());
    }

    public ArrayList<GroceryItem> searchItemsByCategory(String category) {
        if (category == null || category.isBlank()) {
            return new ArrayList<>();
        }

        return itemRepository.searchByCategory(category.trim());
    }

    public ArrayList<GroceryItem> getInStockItems() {
        return itemRepository.findInStockItems();
    }

    public boolean updateStock(int itemId, int newQuantity) {
        if (itemId <= 0 || newQuantity < 0) {
            return false;
        }

        return itemRepository.updateStock(itemId, newQuantity);
    }
}
