package com.spotify.assessment.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spotify.assessment.domain.Stock;
import com.spotify.assessment.domain.StockList;
import com.spotify.assessment.repositories.StockRepository;

@Service
public class RestStockReader {

	private RestTemplate restTemplate;
	

	private final String IEXURL = "https://api.iextrading.com/1.0/tops";
//	private final String IEXURL2 = "https://api.iextrading.com/1.0/stock/aapl/batch?types=quote,news,chart&range=1m&last=10";

	@PostConstruct
	public List<Stock> executeRequest() {
		int i = 0;
		restTemplate = new RestTemplate();
		
		ParameterizedTypeReference<List<Stock>> listOfStock = new ParameterizedTypeReference<List<Stock>>() {};

//		ResponseEntity<List<Stock>> stockBody = this.restTemplate.exchange(IEXURL, HttpMethod.GET, null, listOfStock);
//		List<Stock> stocks = stockBody.getBody();	
		List<Stock> stocks = new ArrayList<Stock>();
		return stocks;
	}
}
