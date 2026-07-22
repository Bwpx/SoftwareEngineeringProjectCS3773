package edu.softwareengineeringprojectcs3773.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
	private ArrayList<GroceryItem> items;
	private Account account;
	
	public Cart(Account account) {
		this.account = account;
		this.items = new ArrayList<>();
	}
	
	public void addItem(GroceryItem item) {
		if (item == null) {
			return;
		}
		items.add(item);

	}

	public boolean removeItem(GroceryItem item) {
		return items.remove(item);
	}

	public boolean removeItemById(int itemId) {
		for (int index = 0; index < items.size(); index++) {
			if (items.get(index).getItemId() == itemId) {
				items.remove(index);
				return true;
			}
		}

		return false;
	}

	public void clear() {
		items.clear();
	}

	public List<GroceryItem> getItems() {
		return Collections.unmodifiableList(items);
	}

	public int getItemCount() {
		return items.size();
	}

	public double getSubTotal() {
		double subtotal = 0.0;

		for (GroceryItem item : items) {
			subtotal += item.getPrice();
		}

		return subtotal;
	}

	public double getTax() {
		return getSubTotal() * 0.0825;
	}

	public double getTotal() {
		return getSubTotal() + getTax();
	}

	public Account getAccount() {
		return account;
	}
}
