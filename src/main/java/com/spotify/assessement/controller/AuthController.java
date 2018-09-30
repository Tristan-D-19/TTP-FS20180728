package com.spotify.assessement.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/auth")
public class AuthController {

	
	
	
	
//	@PostMapping("/login")
//    public Resp loginUser(@ModelAttribute("user") User user, final HttpServletRequest request) {    	
//	        
//	 	UsernamePasswordAuthenticationToken authReq =
//	            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
//	        Authentication auth = authManager.authenticate(authReq);
//	       
//	        SecurityContext sc = SecurityContextHolder.getContext();
//	        sc.setAuthentication(auth);
//	        HttpSession session = request.getSession(true);
//	        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
//	        user = userRepo.findByEmail(user.getEmail()).get();
//	        session.setAttribute("user", user);
//	 ModelAndView mav = new ModelAndView();
//    	
//
//    			mav.setViewName("index");
//
//    	return mav;
    }
