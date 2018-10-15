package com.spotify.assessment.service;



import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spotify.assessment.domain.User;
import com.spotify.assessment.repositories.UserRepository;
import com.spotify.assessment.security.UserPrincipal;

/**
 * Custom userdetailservice used for user authentication with the security context holder
 * @author Tristan
 *
 */
@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	 @Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username) 
		.orElseThrow(() ->
        new UsernameNotFoundException("User not found with username or email : " + username));
				
		return UserPrincipal.create(user);
	}
		
	 // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user);
    }
}
	

