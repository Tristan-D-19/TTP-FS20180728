package com.spotify.assessment.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


import lombok.Data;


/**
 * This domain model represents a user's account to purchase stocks and manage user transactions
 * @author Tristan
 * 
 *
 */
@Data
@Entity
public class Account {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accId;
	
	private double balance = 5000.00;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private User user;
	
	@ManyToMany(cascade=CascadeType.ALL)
	private List <Stock> stocks;
	
	@OneToMany(cascade= {CascadeType.ALL})
	private List<Transaction> transactions;
	
	public Account(User user) {
		this.user = user;
	}
}
