package edu.softwareengineeringprojectcs3773.model;

public class CartItem {

    private GroceryItem item;

    private int quantity;

    public CartItem(GroceryItem item) {
        this.item = item;
        quantity = 1;
    }

    public GroceryItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQunatity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }

    public double getSubtotal() {
        return quantity * item.getPrice();
    }
}
