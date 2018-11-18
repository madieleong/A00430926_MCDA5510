package com.mcda5510.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
	private String id;
	private String nameOnCard;
	private String cardNumber;
	private String cardType;
	private String expDate;
	private double unitPrice;
	private int quantity;
	private double totalPrice;
	private String createdOn;
	private String createdBy;

	//ID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	//NameonCard
	public String getNameOnCard() {
		return nameOnCard;
	}
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	//CardNumber
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	//CardType
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	//ExpDate
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	//UnitPrice
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	//Quantity
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	//TotalPrice
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public void setNewTotalPrice(double unitPrice, int qty) {
		this.totalPrice = unitPrice * qty;
	}

	//CreatedOn
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public void setNewCreatedOn( ) {
		String newCreatedOn = "";
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		newCreatedOn = (dateTime).format(formatter);
		this.createdOn = newCreatedOn;
	}

	//CreatedBy
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setNewCreatedBy() {
		String newCreatedBy = System.getProperty("user.name");
		this.createdBy = newCreatedBy;
	}

	public String toString() {

		String results = new String();
		results = "[ID: " + id + ", NameOnCard: " + nameOnCard + ", CardNumber: " + cardNumber
				+ ", CardType: " + cardType + ", ExpDate: " + expDate 
				+ ", UnitPrice: " + unitPrice + ", Quantity: " + quantity
				+ ", TotalPrice: " + totalPrice + ", CreatedOn: " + createdOn
				+ ", CreatedBy: " + createdBy + "]";
		return results;
	}
}
