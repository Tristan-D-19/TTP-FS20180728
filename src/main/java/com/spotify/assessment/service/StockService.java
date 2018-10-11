package com.spotify.assessment.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spotify.assessment.domain.Stock;
import com.spotify.assessment.exceptions.BadRequestException;
import com.spotify.assessment.payload.PagedResponse;
import com.spotify.assessment.payload.StockResponse;
import com.spotify.assessment.repositories.StockRepository;
import com.spotify.assessment.validator.AppConstants;

/**
 * Service to retrieve stocks from DB.
 * @author Tristan
 *
 */
@Service
public class StockService {

	@Autowired
	private StockRepository stockRepository;
	
	  private void validatePageNumberAndSize(int page, int size) {
	        if(page < 0) {
	            throw new BadRequestException("Page number cannot be less than zero.");
	        }

	        if(size > AppConstants.MAX_PAGE_SIZE) {
	            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
	        }
	    }
	
	 public PagedResponse<StockResponse> getStocks( int page, int size) {
	        validatePageNumberAndSize(page, size);

	       
	        // Retrieve Stocks
	        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "symbol");
	        Page<Stock> stocks = stockRepository.findAll(pageable);

	        if(stocks.getNumberOfElements() == 0) {
	            return new PagedResponse<>(Collections.emptyList(), stocks.getNumber(),
	                    stocks.getSize(), stocks.getTotalElements(), stocks.getTotalPages(), stocks.isLast());
	        }


	        List<StockResponse> stockResponses = new ArrayList<StockResponse>();
	        		stocks.stream().forEach(stock -> {
	        	   stockResponses.add(new StockResponse(stock));
	                  
	        });

	        return new PagedResponse<>(stockResponses, stocks.getNumber(),
	                stocks.getSize(), stocks.getTotalElements(), stocks.getTotalPages(), stocks.isLast());
	    }
}
