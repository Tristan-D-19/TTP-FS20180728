package com.spotify.assessment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.spotify.assessment.domain.Role;
import com.spotify.assessment.domain.Stock;
import com.spotify.assessment.domain.User;
import com.spotify.assessment.repositories.RoleRepository;
import com.spotify.assessment.repositories.StockRepository;
import com.spotify.assessment.repositories.UserRepository;
import com.spotify.assessment.service.RestStockReader;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository; 
	
	private final StockRepository stockRepository; 
	
	@Autowired 
	RestStockReader stockReader;
	
	@Autowired
	public DatabaseLoader(UserRepository userRepository, RoleRepository roleRepository, StockRepository stockRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.stockRepository = stockRepository;
	}

	@Override
	public void run(String... strings) throws Exception {
		
		
		Role user = new Role("ROLE_USER", 0);
		Role admin = new Role("ROLE_ADMIN", 1);
		user = this.roleRepository.save(user);
		admin = this.roleRepository.save(admin);
		
		Set<User> users = new HashSet<User>();
		
		User frodo = new User("Frodo Baggins", "LordOftheRing", "frodo@gmail.com");
		this.userRepository.save(frodo);
		
		
		frodo = userRepository.findById(frodo.getUserId()).orElse(null);
		users.add(frodo);
		user.setUsers(users);
		Set<Role> roles = new HashSet<Role>();
		roles.add(user);
		frodo.setRoles(roles);
//		List<Stock> stocks = stockReader.executeRequest();
		List<Stock> stocks = new ArrayList<Stock>();
	
				stocks.add(new Stock("AXP", 108.42, 38444));
				stocks.add(new Stock("VFMV", 0.0, 0));
				stocks.add(new Stock("NCNA", 129.93, 346));
				stocks.add(new Stock("ZBH", 75.58, 164));
				stocks.add(new Stock("VWOB", 200.46, 0));
				stocks.add(new Stock("HYDW", 0.0, 0));
				stocks.add(new Stock("QVM", 0.0, 0));
				stocks.add(new Stock("AXP", 86.2, 1358182 ));
				stocks.add(new Stock("HYG", 108.42, 34979));
				stocks.add(new Stock("SOR", 108.42, 3536));		
				stocks.add(new Stock("NXST", 108.42, 13796));
						
		stocks.stream().forEach(stock -> {
			stockRepository.saveAndFlush(stock);
			System.out.println(stock);
		}
		);		
		
	}
}
