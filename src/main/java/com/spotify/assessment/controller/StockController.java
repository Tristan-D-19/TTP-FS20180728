package com.spotify.assessment.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spotify.assessment.domain.Stock;
import com.spotify.assessment.domain.User;
import com.spotify.assessment.repositories.StockRepository;
import com.spotify.assessment.service.RestStockReader;
import com.spotify.assessment.service.UserService;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

	@Autowired
	private RestStockReader stockReader;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private StockRepository stockRepository;
	
	@GetMapping("/all")
	public Collection<Stock> getAllStocks(){
//		List<Stock> stocks = stockReader.executeRequest();
		List<Stock> stocks = stockRepository.findAll();
	return stocks;
	}
	
	
	@GetMapping("/{symbol}")
	public  ResponseEntity<?> getStock(@PathVariable String symbol ){
		
	Stock stock = stockRepository.findBySymbol(symbol);
		

		return ResponseEntity.ok().body(stock);
	}
	
	@PostMapping("/buy")
	public ResponseEntity<?> buyStock(@RequestBody Map<String, String> payload){
		
		String symbol = payload.get("symbol");
		String v = payload.get("volume");
		int volume = Integer.parseInt(v);
	String emailToken = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findUserByEmail(emailToken);
		
		boolean status = userService.buyStock(symbol, user, volume);
		
		if(status)		
		return new ResponseEntity<>(HttpStatus.OK);
		
		else 
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
}
