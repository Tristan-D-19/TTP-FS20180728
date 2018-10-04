package com.spotify.assessment.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotify.assessment.domain.User;
import com.spotify.assessment.payload.ApiResponse;
import com.spotify.assessment.payload.JwtAuthenticationResponse;
import com.spotify.assessment.repositories.UserRepository;
import com.spotify.assessment.security.JwtTokenProvider;
import com.spotify.assessment.service.UserService;
import com.spotify.assessment.validator.LoginRequest;
import com.spotify.assessment.validator.RegisterRequest;
import com.spotify.assessment.validator.UserValidator;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
    private AuthenticationManager authManager;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired 
	private UserValidator userValidator;
	
	@Autowired
    private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/login")
    public ResponseEntity<?> loginUser( @Valid @RequestBody LoginRequest loginRequest) {    	
	     
		User user = userService.findUserByEmail(loginRequest.getEmail());
		
//		System.out.println(user);
		 Authentication auth = authManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginRequest.getEmail(),
	                        loginRequest.getPassword()
	                ));
	                
	 	
	      
	        SecurityContext sc = SecurityContextHolder.getContext();
	        sc.setAuthentication(auth);
	        System.out.print(sc.getAuthentication().getName());
//	        HttpSession session = request.getSession(true);
//	        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);

	        
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
        SecurityContext sc = SecurityContextHolder.getContext();
       
     
        System.out.println(registerRequest);
         
        User newUser = new User(registerRequest.getName(), registerRequest.getPassword(), registerRequest.getEmail());
        
            userService.createNewUser(newUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }
	
}
