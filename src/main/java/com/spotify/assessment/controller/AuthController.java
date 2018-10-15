package com.spotify.assessment.controller;

import java.net.URI;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spotify.assessment.domain.User;
import com.spotify.assessment.payload.ApiResponse;
import com.spotify.assessment.payload.JwtAuthenticationResponse;
import com.spotify.assessment.payload.LoginRequest;
import com.spotify.assessment.payload.RegisterRequest;
import com.spotify.assessment.repositories.UserRepository;
import com.spotify.assessment.security.JwtTokenProvider;
import com.spotify.assessment.service.UserService;
import com.spotify.assessment.validator.UserValidator;


/**
 * Authorization controller for handling user login and registration
 * @author Tristan
 *
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
    private AuthenticationManager authManager;
	
	@Autowired
	private UserRepository userRepo;
	
	
	@Autowired
    private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/login")
    public ResponseEntity<?> loginUser( @Valid @RequestBody LoginRequest loginRequest) {    	
	    
		 Authentication auth = authManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginRequest.getEmail(),
	                        loginRequest.getPassword()
	                ));
	     SecurityContext sc = SecurityContextHolder.getContext();
	        sc.setAuthentication(auth);

	        String jwt = tokenProvider.generateToken(auth);
	        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));	
    }
	
	
	@PostMapping({"/registration", "/register"})
    public ResponseEntity<?> registration(@Valid @RequestBody RegisterRequest registerRequest, BindingResult bindingResult) {      
     
        //check for existing user
        User userExists = userRepo.findByEmail(registerRequest.getEmail()).orElse(null);
        if (userExists != null) {
           
            
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
       
         
        User newUser = new User(registerRequest.getName(), registerRequest.getPassword(), registerRequest.getEmail());
        
            User result = userService.createNewUser(newUser);
            
            //create user url
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/users/{id}")
                    .buildAndExpand(result.getUserId()).toUri();

            return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));

    }
	
}
