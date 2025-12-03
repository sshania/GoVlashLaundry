package model;

import java.util.Date;

public class Transaction {
	 private String transactionID;
	 private String serviceID;
	 private String customerID;
	 private String receptionistID;
	 private String laundryStaffID;
	 private Date transactionDate;
	 private String transactionStatus;
	 private double totalWeight;
	 private String transactionNotes;
	
	 public Transaction(String transactionID, String serviceID, String customerID, String receptionistID,
			String laundryStaffID, Date transactionDate, String transactionStatus, double totalWeight,
			String transactionNotes) {
		super();
		this.transactionID = transactionID;
		this.serviceID = serviceID;
		this.customerID = customerID;
		this.receptionistID = receptionistID;
		this.laundryStaffID = laundryStaffID;
		this.transactionDate = transactionDate;
		this.transactionStatus = transactionStatus;
		this.totalWeight = totalWeight;
		this.transactionNotes = transactionNotes;
	}
	 
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getReceptionistID() {
		return receptionistID;
	}
	public void setReceptionistID(String receptionistID) {
		this.receptionistID = receptionistID;
	}
	public String getLaundryStaffID() {
		return laundryStaffID;
	}
	public void setLaundryStaffID(String laundryStaffID) {
		this.laundryStaffID = laundryStaffID;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public String getTransactionNotes() {
		return transactionNotes;
	}
	public void setTransactionNotes(String transactionNotes) {
		this.transactionNotes = transactionNotes;
	}
	 
	 
}
