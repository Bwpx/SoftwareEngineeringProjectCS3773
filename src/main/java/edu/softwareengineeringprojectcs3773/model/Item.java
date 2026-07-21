package edu.softwareengineeringprojectcs3773.model;

public class Item {
    private int itemId;
    private String name;
    private String description;
    private double price;
    private int quantityAvailable;
    private String imagePath;
    private String category;

    public Item() {
    }

    public Item(
            int itemId,
            String name,
            String description,
            double price,
            int quantityAvailable,
            String imagePath,
            String category
    ) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.imagePath = imagePath;
        this.category = category;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return quantityAvailable > 0;
    }
}
