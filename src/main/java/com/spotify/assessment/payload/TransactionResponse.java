package com.spotify.assessment.payload;

import com.spotify.assessment.domain.Transaction;

/**
 * Transaction response to dislay only pertinent information needed for a user transaction
 * @author Tristan
 *
 */

public class TransactionResponse {

	
	private String symbol;
	
	private String transactionType;
	
	private double price;
	
	private int shares;
	
	
	public TransactionResponse(Transaction transaction) {
		this.symbol = transaction.getSymbol();
		this.transactionType = transaction.getTransactionType();
		this.price = transaction.getBoughtPrice();
		this.shares = transaction.getShares();
	}


	public String getSymbol() {
		return symbol;
	}


	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}


	public String getTransactionType() {
		return transactionType;
	}


	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getShares() {
		return shares;
	}


	public void setShares(int shares) {
		this.shares = shares;
	}
}
