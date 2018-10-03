package com.spotify.assessment.service;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spotify.assessment.domain.Account;
import com.spotify.assessment.domain.Stock;
import com.spotify.assessment.domain.User;
import com.spotify.assessment.repositories.*;

@Service
@Transactional
public class UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	
	
	public User createNewUser(User user) {
		user = userRepository.save(user);
		//Create Stock bearing account for user
		Account account = new Account(user);
		account = accountRepository.save(account);
		return user;
	}
	
	public boolean validate(String email, String password) {
		User user = userRepository.findByEmail(email).orElse(null);
		//user does not exist 
		if(user == null)
			return false;
		//check password
		if(password.equals(user.getPassword()))
			return true;
		return false;	
	}
	
	public boolean buyStock(String symbol, User user, int volume) {
		
		Stock stock = stockRepository.findBySymbol(symbol);
		Account account = accountRepository.findByUser(user);
		double balance = account.getBalance();
		double amount = stock.getLastSalePrice();
		accountRepository.save(account);
		
		int shares = stock.getVolume();
		shares = shares - volume;
		stock.setVolume(shares);
		balance = balance - amount;
		account.setBalance(balance);
		
		stockRepository.save(stock);
		return false;
	}
	
	public User findUserByEmail(String email) {
		User user = userRepository.findByEmail(email).orElse(null);
		
		return user;
		
	}
}
