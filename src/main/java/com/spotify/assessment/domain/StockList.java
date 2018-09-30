package com.spotify.assessment.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class StockList {

	private List<Stock> stocks = new ArrayList<Stock>();
	
}
