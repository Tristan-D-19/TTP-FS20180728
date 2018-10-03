//package com.spotify.assessment.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import com.spotify.assessment.domain.User;
//import com.spotify.assessment.repositories.UserRepository;
//
//@Component
//public class CustomUserDetailService implements UserDetailsService {
//
//	@Autowired
//	private UserRepository userRepository;
//	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		User user = userRepository.findByEmail(username).orElse(null);
//				
//		return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
//	}
//
//}
