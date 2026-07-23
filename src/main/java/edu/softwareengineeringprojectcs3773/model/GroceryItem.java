package edu.softwareengineeringprojectcs3773.model;

public class GroceryItem {
    private int itemId;
    private String itemName;
    private String category;
    private double price;
    private int quantityInStock;

    public GroceryItem(int itemId, String itemName, String category, double price, int quantityInStock) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public boolean isInStock() {
        return quantityInStock > 0;
    }

    public boolean nameContains(String searchTerm) {
        return itemName.toLowerCase().contains(searchTerm.toLowerCase());
    }

    public boolean categoryMatches(String category) {
        return this.category.equalsIgnoreCase(category);
    }

    @Override
    public String toString() {
        return "Item ID: " + itemId +
                "\nName: " + itemName +
                "\nCategory: " + category +
                "\nPrice: $" + price +
                "\nIn Stock: " + quantityInStock;
    }
}