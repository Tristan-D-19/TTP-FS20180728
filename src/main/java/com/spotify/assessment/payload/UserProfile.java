package com.spotify.assessment.payload;

import java.util.ArrayList;
import java.util.Collection;

import com.spotify.assessment.domain.Stock;

/**
 * User profile/portfolio response to display users balance, and stocks
 * @author Tristan
 *
 */
public class UserProfile {
    private Long id;
   
    private String name;
    private String email;
    private  Collection<StockResponse> stocks; 
    private double balance;
    
    public UserProfile(Long id, String name,  String email, Collection<Stock> stocks2, double balance ) {
        this.id = id;
       this.email = email;
        this.name = name;
        this.balance = balance; 
        Collection<StockResponse> set = new ArrayList<StockResponse>();
        stocks2.stream().forEach(stock -> set.add(new StockResponse(stock)));
        this.stocks = set;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<StockResponse> getStocks() {
		return this.stocks;
	}

	public void setStocks(Collection<StockResponse> stocks) {
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