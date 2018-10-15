package com.spotify.assessment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spotify.assessment.domain.Account;
import com.spotify.assessment.domain.Stock;
import com.spotify.assessment.domain.User;
import java.util.List;

/**
 * Respository for user accounts
 * @author Tristan
 *
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByUser(User user);
	
	List<Account> findByStocks(List<Stock> stocks);
}
