package com.spotify.assessment;


import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.spotify.assessment.security.JwtAuthenticationFilter;
import com.spotify.assessment.service.AuthService;
import com.spotify.assessment.service.JwtAuthenticationEntryPoint;






@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
//@EnableJpaRepositories(basePackageClasses = UserRepository.class)
//@ComponentScan(basePackageClasses = AuthService.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	

	private static final String ADMIN = "ADMIN";

	private static final String USER = "USER";

	@Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

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


	    
	    @Bean
	    public JwtAuthenticationFilter jwtAuthenticationFilter() {
	        return new JwtAuthenticationFilter();
	    }
	    
	
	    
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
	                .antMatchers(HttpMethod.GET, "/api/stocks/all").permitAll()
	                .anyRequest().authenticated();
  
	        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	    }

//	    @Autowired
//	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {    	
//	    	 auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//	      
//	        }

	    @Override
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	    	 auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	    }

	    


}
