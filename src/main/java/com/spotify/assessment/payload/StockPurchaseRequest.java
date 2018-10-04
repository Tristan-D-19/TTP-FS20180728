package com.spotify.assessment.payload;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
	    
	    @NotBlank
	    private int volume; 
	    
	    public int getVolume() {
	    	return this.volume;
	    }
	    
	    public void setVolume(int volume) {
	    	this.volume = volume;
	    }
	
}
