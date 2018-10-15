package com.spotify.assessment.domain;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
/**
 * Transaction class represents a user purchase of a stock
 * @author Tristan
 *
 */
@Data
@Entity
public class Transaction {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
	
	private String transactionType;
	
	private int shares;
	
	private double boughtPrice;
	
	private String symbol;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Account account;
	
	public Transaction(String transactionType, int shares, double price, String symbol,  Account account) {
		this.transactionType = transactionType;
		this.shares = shares;
		this.boughtPrice = price;
		this.account = account;
		this.symbol = symbol;
	}
}
