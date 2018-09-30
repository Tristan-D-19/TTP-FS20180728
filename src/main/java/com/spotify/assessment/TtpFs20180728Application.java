package com.spotify.assessment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import com.spotify.assessment.domain.Stock;
import com.spotify.assessment.service.RestStockReader;

@SpringBootApplication
public class TtpFs20180728Application {

	 private static final Logger log = LoggerFactory.getLogger(TtpFs20180728Application.class);
	
		
		
	public static void main(String[] args) {
		SpringApplication.run(TtpFs20180728Application.class, args);
//		 String IEXURL = "https://api.iextrading.com/1.0/stock/aapl/quote";
//		RestTemplate restTemplate = new RestTemplate();
//		Stock stock = restTemplate.getForObject(IEXURL, Stock.class);
//		System.out.println("getForObject: "+ stock.getLatestPrice());
		RestStockReader reader = new RestStockReader();
		reader.executeRequest();
	}
}
