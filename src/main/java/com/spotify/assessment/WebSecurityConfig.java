package com.spotify.assessment;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spotify.assessment.security.JwtAuthenticationEntryPoint;
import com.spotify.assessment.security.JwtAuthenticationFilter;
import com.spotify.assessment.service.AuthService;

/**
 * Security configuration to handle all security for the API
 * @author Tristan
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
    @Autowired
    private AuthService userDetailsService;
    
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	 }
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	 }
	
	//Authentication filter to store JWT in localstorage
	@Bean
	    public JwtAuthenticationFilter jwtAuthenticationFilter() {
	        return new JwtAuthenticationFilter();
	    }
	   
	
		//configure http settings
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {

	        http.
				     cors()
			         .and()
			        .csrf()
			        .disable()
			        .exceptionHandling()
			        .authenticationEntryPoint(unauthorizedHandler)
			        .and()
			        .sessionManagement()
			        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			         .and()
			        .authorizeRequests()
			        .antMatchers("/",
	                        "/favicon.ico",
	                        "/**/*.png",
	                        "/**/*.gif",
	                        "/**/*.svg",
	                        "/**/*.jpg",
	                        "/**/*.html",
	                        "/**/*.css",
	                        "/**/*.js")
	                        .permitAll()
	                .antMatchers("/api/auth/**").permitAll()	                             	                	               
	                .antMatchers("/api/*").permitAll()
	                .antMatchers("/api/stocks/*").hasAnyRole("USER")
	                .anyRequest().authenticated();
  
	        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	    }

	    //Register custom userdetailservice and encode password with bcrpyt
	    @Override
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	    	 auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	    }
}
