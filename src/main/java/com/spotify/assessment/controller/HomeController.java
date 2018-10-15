package com.spotify.assessment.controller;

import org.springframework.web.bind.annotation.RequestMapping;

//Index page
public class HomeController {
	
	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
}
