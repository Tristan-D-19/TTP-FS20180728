package com.spotify.assessment.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spotify.assessment.domain.Stock;

/**
 * Get stocks from IEXTrading API
 * @author Tristan
 *
 */
@Service
public class RestStockReader {

	private RestTemplate restTemplate;
	

	private final String IEXURL = "https://api.iextrading.com/1.0/tops";

	@PostConstruct
	public List<Stock> executeRequest() {
		restTemplate = new RestTemplate();
		
		ParameterizedTypeReference<List<Stock>> listOfStock = new ParameterizedTypeReference<List<Stock>>() {};
	
	
		ResponseEntity<List<Stock>> stockBody = this.restTemplate.exchange(IEXURL, HttpMethod.GET, null, listOfStock);
		List<Stock> stocks = stockBody.getBody();	
		List<Stock> stocks2 = new ArrayList<Stock>();
		
		if(stocks.isEmpty())
		return stocks2;
		else 
			return stocks;
	}
	
}
