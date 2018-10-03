package com.spotify.assessment.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotify.assessment.domain.User;
import com.spotify.assessment.repositories.UserRepository;
import com.spotify.assessment.validator.UserValidator;



@Controller
@RequestMapping("/auth")
public class AuthController {

	@Resource(name="AUTHENTICATION_MANAGER")
    private AuthenticationManager authManager;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired 
	private UserValidator userValidator;
	
	
	
	
	@PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, @JsonProperty("email") String email, @JsonProperty("password") String password, final HttpServletRequest request) {    	
	        
//		String email = payload.get("email");
//		String password = payload.get("password");
		
	 	UsernamePasswordAuthenticationToken authReq =
	            new UsernamePasswordAuthenticationToken(email, password);
	        Authentication auth = authManager.authenticate(authReq);
	       
	        try {
				request.login(email, password);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        SecurityContext sc = SecurityContextHolder.getContext();
	        sc.setAuthentication(auth);
	        HttpSession session = request.getSession(true);
	        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
	        User user = userRepo.findByEmail(email).orElse(null);
	        session.setAttribute("user", user);
	 ModelAndView mav = new ModelAndView();
    	

    			mav.setViewName("index");
    			if(auth.isAuthenticated())
    			return new ResponseEntity<>(HttpStatus.ACCEPTED);
    			else 
    				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
	
	
	@PostMapping({"/registration", "/register"})
    public ResponseEntity<?> registration(@RequestBody Map<String, String> payload, BindingResult bindingResult, HttpSession session) {
        String email = payload.get("email");
        String password = payload.get("password");
        String name = payload.get("name");
     
        User userExists = userRepo.findByEmail(email).orElse(null);
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");         
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        if (bindingResult.hasErrors()) { //error return to registration
        	SecurityContext sc = SecurityContextHolder.getContext();
        	Authentication auth = sc.getAuthentication();
        	if(!auth.isAuthenticated())
        		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        	else
        		return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        
        User newUser = new User(name, password, email);
        
            userRepo.save(newUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }
	
}
