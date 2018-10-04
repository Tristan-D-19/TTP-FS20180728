package com.spotify.assessment.service;



import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.spotify.assessment.domain.Role;
import com.spotify.assessment.domain.User;
import com.spotify.assessment.repositories.UserRepository;



@Component("authManager")
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
    public Authentication authenticate(Authentication auth) 
      throws AuthenticationException {
        String email = auth.getName();
        String password = auth.getCredentials()
            .toString();
 
        Optional<User> foundUser = userRepository.findByEmail(email);
        
        User user = foundUser.orElse(null);
        if(Objects.nonNull(user)) {
        	 Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        	 System.out.println("authenticating this shit!");

             if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
               //password is a match
               //Setting up Authorities
               for (Role role : user.getRoles()) {
                 logger.info("adding Role : " + role.getRole());
                 grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
               }

               return new UsernamePasswordAuthenticationToken(email, user.getPassword(), grantedAuthorities);

             } else {
               logger.info("Unsuccessful login attempt - wrong password.");
               return null;
             }
           } 
        else {
             logger.info("Unsuccessful login attempt - user not found.");
             return null;
           }
        	
        	
}
       

 
    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
