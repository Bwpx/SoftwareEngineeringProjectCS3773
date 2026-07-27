package edu.softwareengineeringprojectcs3773.model;

public class Address {
	int addressId;
	int accountId;
	String line1;
	String line2;
	String city;
	String state;
	int zip;
	String type;
	boolean autofill;
	
	public Address(int addressId, int accountId, String line1, String line2, String city, String state, int zip, String type, boolean autofill) {
		this.addressId = addressId;
		this.accountId = accountId;
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.type = type;
		this.autofill = autofill;
		//this.autofill = (autofill > 0);
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
	
	public String getType() {
		return type;
	}
	
	public boolean getAutofill() {
		return autofill;
	}
	
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	
	public void setAutofill(boolean autofill) {
		this.autofill = autofill;
		
	}
	
	public String toString() {
		if(line2.isEmpty()) {
			return line1 + ", " + city + " " + state + " " + String.valueOf(zip);
		}
		return line1 + " " + line2 + ", " + city + " " + state + " " + String.valueOf(zip);
	}
}
