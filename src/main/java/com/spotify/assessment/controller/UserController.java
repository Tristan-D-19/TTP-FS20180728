package com.spotify.assessment.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spotify.assessment.domain.Transaction;
import com.spotify.assessment.domain.User;
import com.spotify.assessment.exceptions.ResourceNotFoundException;
import com.spotify.assessment.payload.TransactionResponse;
import com.spotify.assessment.payload.UserProfile;
import com.spotify.assessment.payload.UserSummary;
import com.spotify.assessment.repositories.UserRepository;
import com.spotify.assessment.security.CurrentUser;
import com.spotify.assessment.security.UserPrincipal;
import com.spotify.assessment.service.UserService;

/**
 * User controller for getting a user token for authentication to API, user profile and user transactions
 * @author Tristan
 *
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	
	@Autowired 
	private UserService userService;
	
	//user token for authentication
    @GetMapping("/token")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getUserId(), currentUser.getEmail(), currentUser.getName());
        return userSummary;
    }
	
    //user profile
	@GetMapping("/user")
	public UserProfile getUser(@CurrentUser UserPrincipal currentUser){

		UserProfile userProfile = userService.getUser(currentUser.getUserId());
		
	
		 return userProfile;
	}	 
	
	//user transactions
	@GetMapping("/user/transactions")
	public Collection<TransactionResponse> getUserTransactions(@CurrentUser UserPrincipal currentUser) {
		User user = userService.findById(currentUser.getUserId());
		List<Transaction> transactions = userService.getTransactions(user);
		List <TransactionResponse> transactionResponses = new ArrayList<TransactionResponse>();
		transactions.stream().forEach(transaction -> transactionResponses.add(new TransactionResponse(transaction)));
		
		return transactionResponses;
		}
	
}
