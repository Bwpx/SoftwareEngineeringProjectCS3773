package edu.softwareengineeringprojectcs3773.model;

import java.sql.Date;

public class Order {
	private int orderId;
	private int accountId;
	private Date date;
	private double total;
	private String status;
	private String deliveryType;
	private String actions;
	
	public Order(int orderId, int accountId, Date date, double total, String status, String deliveryType, String actions) {
		this.orderId = orderId;
		this.accountId = accountId;
		this.date = date;
		this.total = total;
		this.status = status;
		this.deliveryType = deliveryType;
		this.actions = actions;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public int getAccountId() {
		return accountId;
	}
	
	public Date getDate() {
		return date;
	}
	
	public double getTotal() {
		return total;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getDeliveryType() {
		return deliveryType;
	}
	
	public String getActions() {
		return actions;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
		
	}
}
