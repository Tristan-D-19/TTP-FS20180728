package com.spotify.assessment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spotify.assessment.domain.Transaction;
import com.spotify.assessment.domain.Account;
import java.util.List;

/**
 * Transaction repository for storing user transactions
 * @author Tristan
 *
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByAccount(Account account);
	
}
