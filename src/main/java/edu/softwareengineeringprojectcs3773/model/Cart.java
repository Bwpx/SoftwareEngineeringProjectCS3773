package edu.softwareengineeringprojectcs3773.model;

import java.util.ArrayList;

public class Cart {
	private ArrayList<GroceryItem> items;
	private Account account;
	
	public Cart(Account account) {
		this.account = account;
		items = new ArrayList<>();
	}
	
	public void addItem(GroceryItem item) {
		items.add(item);
	}
}
