package com.spotify.assessment;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//Main application 
@SpringBootApplication
@EnableWebSecurity
public class TtpFs20180728Application {

	 private static final Logger log = LoggerFactory.getLogger(TtpFs20180728Application.class);
	
	 @PostConstruct
		void init() {
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		}
		
	public static void main(String[] args) {
		SpringApplication.run(TtpFs20180728Application.class, args);

	}
}
