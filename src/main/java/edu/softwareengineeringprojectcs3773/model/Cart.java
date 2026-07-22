package edu.softwareengineeringprojectcs3773.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {

	public static final double TAX_RATE = 0.0825;

	private ArrayList<CartItem> items;
	private Account account;

	private String discountCode;
	private double discountPercent;
	
	public Cart(Account account) {
		this.account = account;
		this.items = new ArrayList<>();

		this.discountCode = "";
		this.discountPercent = 0.0;
	}
	
	public void addItem(GroceryItem groceryItem) {
		if (groceryItem == null || !groceryItem.isInStock()) {
			return;
		}

		CartItem existingItem = findCartItem(groceryItem.getItemId());

		if (existingItem != null) {
			if (existingItem.getQuantity() < groceryItem.getQuantityInStock()) {
				existingItem.incrementQunatity();
			}

			return;

		}
		items.add(new CartItem(groceryItem));

	}

	public CartItem findCartItem(int itemId) {
		for (CartItem cartItem : items) {
			if(cartItem.getItem().getItemId() == itemId) {
				return cartItem;
			}
		}
		return null;
	}

	public boolean updateQuantity(int itemId, int quantity) {
		CartItem cartItem = findCartItem(itemId);

		if (cartItem == null) {
			return false;
		}

		if (quantity <= 0) {
			return removeItem(itemId);
		}

		int availableStock =
				cartItem.getItem().getQuantityInStock();

		if (quantity > availableStock) {
			return false;
		}

		cartItem.setQuantity(quantity);
		return true;
	}

	public boolean removeItem(int itemId) {
		CartItem cartItem = findCartItem(itemId);

		if (cartItem == null) {
			return false;
		}

		return items.remove(cartItem);
	}

	public void clear() {
		items.clear();
		clearDiscount();
	}

	public List<CartItem> getItems() {
		return Collections.unmodifiableList(items);
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public int getItemCount() {
		int itemCount = 0;

		for (CartItem cartItem : items) {
			itemCount += cartItem.getQuantity();
		}

		return itemCount;
	}

	public double getSubtotal() {
		double subtotal = 0.0;

		for (CartItem cartItem : items) {
			subtotal += cartItem.getSubtotal();
		}

		return subtotal;
	}

	public boolean applyDiscountCode(String code) {
		if (code == null || code.isBlank()) {
			clearDiscount();
			return false;
		}

		String normalizedCode =
				code.trim().toUpperCase();

		/*
		 * Database integration:
		 *
		 * Replace this temporary discount-code check with a call
		 * to DiscountService.
		 *
		 * DiscountService should obtain the code, percentage,
		 * expiration date, and eligibility rules through a
		 * DiscountRepository.
		 *
		 * The Cart and controller should not directly query the
		 * database.
		 */
		if ("SAVE10".equals(normalizedCode)) {
			discountCode = normalizedCode;
			discountPercent = 0.10;
			return true;
		}

		clearDiscount();
		return false;
	}

	public void clearDiscount() {
		discountCode = "";
		discountPercent = 0.0;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public double getDiscountPercent() {
		return discountPercent;
	}

	public double getDiscountAmount() {
		return getSubtotal() * discountPercent;
	}

	public double getTaxableAmount() {
		return Math.max(
				0.0,
				getSubtotal() - getDiscountAmount()
		);
	}

	public double getTax() {
		return getTaxableAmount() * TAX_RATE;
	}

	public double getTotal() {
		return getTaxableAmount() + getTax();
	}

	public Account getAccount() {
		return account;
	}
}
