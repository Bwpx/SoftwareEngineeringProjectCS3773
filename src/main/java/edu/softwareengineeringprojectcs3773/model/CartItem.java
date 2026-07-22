package edu.softwareengineeringprojectcs3773.model;

public class CartItem {

    private final GroceryItem item;

    private int quantity;

    public CartItem(GroceryItem item) {
        this(item, 1);
    }

    public CartItem(GroceryItem item, int quantity) {
        this.item = item;
        setQuantity(quantity);
    }

    public GroceryItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 1) {
            this.quantity = 1;
        } else {
            this.quantity = quantity;
        }
    }

    public void incrementQunatity() {
        quantity++;
    }

    public void decrementQuantity() {
        if (quantity > 1) {
            quantity--;
        }
    }

    public double getSubtotal() {
        return item.getPrice() * quantity;
    }
}
