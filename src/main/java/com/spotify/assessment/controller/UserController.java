package com.spotify.assessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spotify.assessment.domain.User;
import com.spotify.assessment.exceptions.ResourceNotFoundException;
import com.spotify.assessment.payload.UserProfile;
import com.spotify.assessment.payload.UserSummary;
import com.spotify.assessment.repositories.UserRepository;
import com.spotify.assessment.security.CurrentUser;
import com.spotify.assessment.security.UserPrincipal;
import com.spotify.assessment.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	
	
	@Autowired 
	private UserService userService;
	
    @GetMapping("/token")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getUserId(), currentUser.getEmail(), currentUser.getName());
        return userSummary;
    }
	
	@GetMapping("/user")
	public UserProfile getUser(@CurrentUser UserPrincipal currentUser){

		UserProfile userProfile = userService.getUser(currentUser.getUserId());
		
	
		 return userProfile;
		 
//	@GetMapping("/user/{id}/account")
//	public void getUserAccount(@PathVariable(value="id") Long id ) {
//		
//		return 
//				
//	}
	}
}
