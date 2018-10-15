package com.spotify.assessment.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spotify.assessment.domain.Stock;
import com.spotify.assessment.domain.User;
import com.spotify.assessment.payload.ApiResponse;
import com.spotify.assessment.payload.PagedResponse;
import com.spotify.assessment.payload.StockPurchaseRequest;
import com.spotify.assessment.payload.StockResponse;
import com.spotify.assessment.repositories.StockRepository;
import com.spotify.assessment.security.CurrentUser;
import com.spotify.assessment.security.UserPrincipal;
import com.spotify.assessment.service.RestStockReader;
import com.spotify.assessment.service.StockService;
import com.spotify.assessment.service.UserService;
import com.spotify.assessment.validator.AppConstants;


/**
 * Stock controller for getting all stocks and buying a stock.
 * @author Tristan
 *
 */
@RestController
@RequestMapping("/api/stocks")
public class StockController {

	@Autowired
	private RestStockReader stockReader;
	
	@Autowired 
	private UserService userService;
	
	@Autowired 
	private StockService stockService;
	@Autowired
	private StockRepository stockRepository;
	
	@GetMapping(value ="/all")
	public PagedResponse<StockResponse> getAllStocks(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){

	return stockService.getStocks(page, size);
	}
	
	
	@GetMapping("/{symbol}")
	public  ResponseEntity<?> getStock(@PathVariable String symbol ){
		
	Stock stock = stockRepository.findBySymbol(symbol);
		

		return ResponseEntity.ok().body(stock);
	}
	
	//purchase a stock
	@PostMapping("/buy")
	public ResponseEntity<?> buyStock(@Valid @RequestBody StockPurchaseRequest stockPurchaseRequest, @CurrentUser UserPrincipal currentUser){
		
		

		User user = userService.findUserByEmail(currentUser.getEmail());
		
		boolean status = userService.buyStock(stockPurchaseRequest.getSymbol(), user, stockPurchaseRequest.getVolume());
		
		if(status)		
			 return new ResponseEntity<>(new ApiResponse(true, "purchase successfull"),
	                    HttpStatus.OK);
		
		else 
			 return new ResponseEntity<>(new ApiResponse(false, "purchase failure"),
	                    HttpStatus.BAD_REQUEST);
	}
}
