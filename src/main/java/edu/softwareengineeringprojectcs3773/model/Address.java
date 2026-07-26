package edu.softwareengineeringprojectcs3773.model;

public class Address {
	int addressId;
	int accountId;
	String line1;
	String line2;
	String city;
	String state;
	int zip;
	
	public Address(int addressId, int accountId, String line1, String line2, String city, String state, int zip) {
		this.addressId = addressId;
		this.accountId = accountId;
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public int getAddressId() {
		return addressId;
	}
	
	public int getAccountId() {
		return accountId;
	}
	
	public String getLine1() {
		return line1;
	}
	
	public String getLine2() {
		return line2;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getState() {
		return state;
	}
	
	public int getZip() {
		return zip;
	}
	
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
}
