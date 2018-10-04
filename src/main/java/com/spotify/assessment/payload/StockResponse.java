package com.spotify.assessment.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

public class StockResponse {

   
	    private String symbol;  
	 
	    private Instant creationDateTime;
	    private Instant expirationDateTime;
	    private Boolean isExpired;

	    @JsonInclude(JsonInclude.Include.NON_NULL)
	    private int volume;
	   
	    private double lastSalesPrice;

		public String getSymbol() {
			return symbol;
		}

		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}

		public Instant getCreationDateTime() {
			return creationDateTime;
		}

		public void setCreationDateTime(Instant creationDateTime) {
			this.creationDateTime = creationDateTime;
		}

		public Instant getExpirationDateTime() {
			return expirationDateTime;
		}

		public void setExpirationDateTime(Instant expirationDateTime) {
			this.expirationDateTime = expirationDateTime;
		}

		public Boolean getIsExpired() {
			return isExpired;
		}

		public void setIsExpired(Boolean isExpired) {
			this.isExpired = isExpired;
		}

		public int getVolume() {
			return volume;
		}

		public void setVolume(int volume) {
			this.volume = volume;
		}

		public double getLastSalesPrice() {
			return lastSalesPrice;
		}

		public void setLastSalesPrice(double lastSalesPrice) {
			this.lastSalesPrice = lastSalesPrice;
		}

	

}
