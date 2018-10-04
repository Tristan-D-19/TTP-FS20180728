package com.spotify.assessment.payload;

import java.time.Instant;
import java.util.List;

import com.spotify.assessment.domain.Account;
import com.spotify.assessment.domain.Stock;

public class UserProfile {
    private Long id;
   
    private String name;
    private String email;
    private  List<Stock> stocks; 
    private double balance;
    
    public UserProfile(Long id, String name,  String email, List<Stock> stocks, double balance ) {
        this.id = id;
       this.email = email;
        this.name = name;
        this.balance = balance; 
        this.stocks = stocks;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Stock> getStocks() {
		return this.stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

  
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}