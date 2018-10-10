package com.spotify.assessment.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * This class reperesents a stock pulled from IEXDATA's API
 * each stock is identified by their symbol. 
 * @author Tristan
 */


@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {

@Id
private String symbol;

private double lastSalePrice;

private int volume;


@ManyToMany(cascade=CascadeType.ALL)
private List <Account> account;

public Stock(String symbol,double lastSalePrice2, int volume2) {
	this.lastSalePrice = lastSalePrice2;
	this.volume = volume2;
	this.symbol = symbol;
	}

public Stock(Stock stock) {
	this.lastSalePrice = stock.getLastSalePrice();
	this.volume = stock.getVolume();
	this.symbol = stock.getSymbol();
}

}
