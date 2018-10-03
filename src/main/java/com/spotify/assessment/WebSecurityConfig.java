package com.spotify.assessment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import com.spotify.assessment.service.AuthService;




@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
@EnableWebSecurity
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
    

	    @Bean
	    public BCryptPasswordEncoder bCryptPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	    @Override
	    public AuthenticationManager authenticationManager() throws Exception {
	        return super.authenticationManagerBean();
	    }
	    
	    @Bean
	    public DaoAuthenticationProvider authenticationProvider(){
	        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
	        auth.setUserDetailsService(userDetailsService);
	        auth.setPasswordEncoder(bCryptPasswordEncoder());
	        return auth;
	    }
	    
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {

	        http.
	                authorizeRequests()
	                .antMatchers("/").permitAll()	                             	                	               
	                .antMatchers("/auth/registration","/auth/register").permitAll()
	                .antMatchers("/api/**").permitAll()
	                .antMatchers("/stocks").permitAll()
	                .antMatchers("/auth/login").permitAll()	          
	                .anyRequest().permitAll()
	                	.and().csrf().disable()
	                .formLogin()
//	                .loginPage("/auth/login")
	                .failureUrl("/login?error=true")
//	                .loginProcessingUrl("/auth/login")
	                .defaultSuccessUrl("/")
//	                .usernameParameter("email")
//	                .passwordParameter("password")
	                	.and().logout()
	                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	              
	                .and().httpBasic()
	                ;
  
	    }

	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    	
	    	
	    	auth.authenticationProvider(authenticationProvider());
	    	

	        }

	 
	    
	    @Override
	    public void configure(WebSecurity web) throws Exception {
	        web
	                .ignoring()
	                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/webjars/**");
	    } 
	    


}
