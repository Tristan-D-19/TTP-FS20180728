package com.spotify.assessment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spotify.assessment.domain.Stock;
import java.lang.String;
import java.util.List;

import javax.transaction.Transactional;

/**
 * Repository to store stocks from the IEXtrading API
 * @author Tristan
 *
 */
@Repository
@Transactional
public interface StockRepository extends JpaRepository<Stock, Long> {

	Stock findBySymbol(String symbol);
	
	public List<Stock> findByVolume(int volume);
	
	public List<Stock> findByLastSalePrice(double lastsaleprice);
	
	List<Stock> findByAccount(List<?> account);
}
