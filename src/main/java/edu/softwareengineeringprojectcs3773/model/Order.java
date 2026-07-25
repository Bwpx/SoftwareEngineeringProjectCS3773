package edu.softwareengineeringprojectcs3773.model;

import java.sql.Date;

public class Order {
	private String orderId;
	private int accountId;
	private Date date;
	private double total;
	private String status;
	private String deliveryType;
	private String actions;
	
	public Order(String orderId, int accountId, Date date, double total, String status, String deliveryType, String actions) {
		this.orderId = orderId;
		this.accountId = accountId;
		this.date = date;
		this.total = total;
		this.status = status;
		this.deliveryType = deliveryType;
		this.actions = actions;
	}
	
	public String getOrderId() {
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

	public void setOrderId(String orderId) {
		this.orderId = orderId;
		
	}
}
