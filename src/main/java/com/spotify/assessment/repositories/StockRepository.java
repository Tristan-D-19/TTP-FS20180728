package com.spotify.assessment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spotify.assessment.domain.Stock;
import java.lang.String;
import java.util.List;

import javax.transaction.Transactional;

import com.spotify.assessment.domain.Account;

@Repository
@Transactional
public interface StockRepository extends JpaRepository<Stock, Long> {

	public List<Stock> findBySymbol(String symbol);
	
	public List<Stock> findByVolume(int volume);
	
	public List<Stock> findByLastSalePrice(double lastsaleprice);
	
	List<Stock> findByAccount(List <Account> account);
}
