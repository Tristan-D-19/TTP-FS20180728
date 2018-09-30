package com.spotify.assessment;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.spotify.assessment.domain.Role;
import com.spotify.assessment.domain.User;
import com.spotify.assessment.repositories.RoleRepository;
import com.spotify.assessment.repositories.UserRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository; 
	
	@Autowired
	public DatabaseLoader(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public void run(String... strings) throws Exception {
		Role user = new Role("ROLE_USER", 0);
		Role admin = new Role("ROLE_ADMIN", 1);
		this.roleRepository.save(user);
		this.roleRepository.save(admin);
		User frodo = new User("Frodo Baggins", "LordOftheRing", "frodo@gmail.com");
		Set<Role> roles = new HashSet<Role>();
		roles.add(user);
		frodo.setRoles(roles);
		this.userRepository.save(frodo);
	}
}
