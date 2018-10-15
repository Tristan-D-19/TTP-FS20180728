package com.spotify.assessment.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spotify.assessment.domain.Account;
import com.spotify.assessment.domain.Role;
import com.spotify.assessment.domain.Stock;
import com.spotify.assessment.domain.Transaction;
import com.spotify.assessment.domain.User;
import com.spotify.assessment.payload.UserProfile;
import com.spotify.assessment.repositories.*;
/**
 * User service is used to handle all user actions, creating users, update users,
 * get user transactions, buy stock, and get the users profile/portfolio.
 * @author Tristan
 *
 */
@Service
@Transactional
public class UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired 
	private TransactionRepository transactionRepository;
	
	 @Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder;
	 
	public User createNewUser(User user) {
		Role userRole = roleRepository.findByRole("ROLE_USER");
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Set<Role> roles = new HashSet<Role>();
		roles.add(userRole);
		user.setRoles(roles);
		user = userRepository.save(user);
		Set<User> users = userRole.getUsers();
		users.add(user);
		userRole.setUsers(users);
		roleRepository.save(userRole);
		Account account = new Account(user);
		account = accountRepository.save(account);
		user.setAccount(account);
		user = userRepository.save(user);
		System.out.println(user);
		return user;
	}
	public User createAdminUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Role adminRole = roleRepository.findByRole("ROLE_ADMIN");
		
		Set<Role> roles = new HashSet<Role>();
		roles.add(adminRole);	
		user.setRoles(roles);
		
		Set<User> adminUsers = adminRole.getUsers();
		adminUsers.add(user);
		adminRole.setUsers(adminUsers);
		roleRepository.save(adminRole);
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
		
		
		//check shares	
		int shares = stock.getVolume();
		if ((shares-volume) < 0)
			return false;
		shares = shares - volume;
		
		
		Stock userStock = new Stock(stock);
		userStock.setVolume(volume);
		//check balance
		if((balance- amount) < 0 || balance <= 0)
			return false;
		
		Transaction transaction = new Transaction("BUY", volume, stock.getLastSalePrice(), stock.getSymbol(), account);
		transaction = transactionRepository.save(transaction);
		balance = balance - amount;
		account.setBalance(balance);
		List<Transaction> userTransactions = account.getTransactions();
		userTransactions.add(transaction);
		//change to hashmap to ensure no duplicates
		HashMap<String, Stock> userStocks = new HashMap<String, Stock>();
		account.getStocks().stream().forEach(stockItem-> userStocks.put(stockItem.getSymbol(), stockItem));
		stock.setVolume(volume);
		userStocks.put(stock.getSymbol(),stock);
		
		//turn back to list 
		 List<Stock> userStockList = new ArrayList<Stock> ();
		 userStockList.addAll(userStocks.values());
		account.setStocks(userStockList);
		account = accountRepository.save(account);
		userStock = stock;
		stock.setVolume(shares);
		List<Account> accounts = stock.getAccount();
		accounts.add(account);
		stock.setAccount(accounts);
		stock = stockRepository.save(stock);
		if(stock.getVolume() == shares && account.getBalance() == balance && account.getStocks().contains(stock))
		return true;
		else 
		return false;
	}
	
	public User findUserByEmail(String email) {
		User user = userRepository.findByEmail(email).orElse(null);
		
		return user;
		
	}
	public User findById(Long userId) {	
		User user = userRepository.findById(userId).orElse(null);
		return user;
	}
	
	public UserProfile getUser(Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		Account account = accountRepository.findByUser(user);
		Collection<Stock> stocks = account.getStocks();
		Collection<Transaction> transactions = account.getTransactions();
		HashMap<String, Transaction> tMap = new HashMap<String, Transaction>();
		transactions.stream().forEach(t-> {
			tMap.put(t.getSymbol(), t ); 
			});
		stocks.stream().forEach(stock -> 
		{
			stock.setVolume(tMap.get(stock.getSymbol()).getShares());
		});
		UserProfile userAccount = new UserProfile(user.getUserId(), user.getName(), user.getEmail(), stocks, account.getBalance());
		return userAccount;
	}
	
	public List<Transaction> getTransactions(User user){
		Account account = accountRepository.findByUser(user);
		return account.getTransactions();
	}
}
