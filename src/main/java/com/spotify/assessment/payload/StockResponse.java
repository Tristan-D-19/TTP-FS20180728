package com.spotify.assessment.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spotify.assessment.domain.Stock;

import java.time.Instant;
import java.util.List;

public class StockResponse {

   
	    private String symbol;   
	    
	   
	    private int volume;
	   
	    private double lastSalesPrice;

		public String getSymbol() {
			return this.symbol;
		}

		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}


		public int getVolume() {
			return this.volume;
		}

		public void setVolume(int volume) {
			this.volume = volume;
		}

		public double getLastSalesPrice() {
			return this.lastSalesPrice;
		}

		public void setLastSalesPrice(double lastSalesPrice) {
			this.lastSalesPrice = lastSalesPrice;
		}

	
		public StockResponse(Stock stock) {
			this.symbol = stock.getSymbol();
			this.lastSalesPrice = stock.getLastSalePrice();
			this.volume = stock.getVolume();
		
		}
}
