package com.spotify.assessment.service;

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
import com.spotify.assessment.domain.User;
import com.spotify.assessment.payload.UserProfile;
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
	
	@Autowired
	private RoleRepository roleRepository;
	
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
	public User findById(Long userId) {	
		User user = userRepository.findById(userId).orElse(null);
		return user;
	}
	
	public UserProfile getUser(Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		Account account = accountRepository.findByUser(user);
		UserProfile userAccount = new UserProfile(user.getUserId(), user.getName(), user.getEmail(), account.getStocks(), account.getBalance());
		return userAccount;
	}
}
