package com.spotify.assessment.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Stock list representation used for getting stocks from an external API
 * @author Tristan
 *
 */
@Data
public class StockList {

	private List<Stock> stocks = new ArrayList<Stock>();
	
}
