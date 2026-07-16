package edu.softwareengineeringprojectcs3773.service;

import edu.softwareengineeringprojectcs3773.model.GroceryItem;
import edu.softwareengineeringprojectcs3773.repository.ItemRepository;
import java.util.ArrayList;

public class ItemService {
    private ItemRepository itemRepository;
    private int nextItemId;

    public ItemService() {
        itemRepository = new ItemRepository();
        nextItemId = 7;
    }

    public void addItem(String itemName, String category, double price, int quantityInStock) {
        GroceryItem newItem = new GroceryItem(nextItemId, itemName, category, price, quantityInStock);
        itemRepository.save(newItem);
        nextItemId++;
    }

    public ArrayList<GroceryItem> getAllItems() {
        return itemRepository.findAll();
    }

    public GroceryItem getItemById(int itemId) {
        return itemRepository.findById(itemId);
    }

    public ArrayList<GroceryItem> searchItemsByName(String searchTerm) {
        return itemRepository.searchByName(searchTerm);
    }

    public ArrayList<GroceryItem> searchItemsByCategory(String category) {
        return itemRepository.searchByCategory(category);
    }

    public ArrayList<GroceryItem> getInStockItems() {
        return itemRepository.findInStockItems();
    }

    public boolean updateStock(int itemId, int newQuantity) {
        GroceryItem item = itemRepository.findById(itemId);

        if (item == null) {
            return false;
        }

        item.setQuantityInStock(newQuantity);
        return true;
    }
}
