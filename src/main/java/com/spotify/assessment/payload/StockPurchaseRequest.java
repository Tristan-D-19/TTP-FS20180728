package com.spotify.assessment.payload;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Stock purchase request for incoming stock purchase request 
 * @author Tristan
 *
 */
public class StockPurchaseRequest {

		@NotBlank
	    @Size(max = 40)
	    private String symbol;

	    public String getSymbol() {
	        return this.symbol;
	    }

	    public void setSymbol(String symbol) {
	        this.symbol = symbol;
	    }
	    
	    @DecimalMin(value = "1")
	    private int volume; 
	    
	    public int getVolume() {
	    	return this.volume;
	    }
	    
	    public void setVolume(int volume) {
	    	this.volume = volume;
	    }
	
}
