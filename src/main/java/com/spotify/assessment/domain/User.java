package com.spotify.assessment.domain;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.stream.Collectors;


import lombok.Data;
import lombok.EqualsAndHashCode;

import lombok.ToString;


/**
 *This class represents a user for use in trading of stocks
 *A user instance will be passed a name, password and email. 
 *This class also implements userDetails for use with spring security to keep track of users in the session. 
 *@author Tristan
 */

@Data
@Entity
@EqualsAndHashCode(exclude = {"account", "roles"})

@ToString(exclude = {"account", "roles"})
public class User implements UserDetails {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2301502094697683680L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Account account;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="enabled")
	private boolean enabled = true;
	
	@ManyToMany(cascade = {CascadeType.ALL})
    private Set<Role> roles;
	
	
	 
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List <GrantedAuthority> authorityList = this.roles.stream().map(role -> 
		(new SimpleGrantedAuthority(role.getRole())))
		.collect(Collectors.toList());
		
		return authorityList;
	}

	public User(String name, String password, String email) {
		this.name = name;
		this.password = password;
		this.email = email;
	}
	
	public static User create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRole())
        ).collect(Collectors.toList());
        
        return new User(user.getEmail(), user.getPassword(), authorities);
	}
	
	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
	public String getUsername() {
		
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public User(String email, String password2, Collection<? extends GrantedAuthority> authorities ) {
		this.email = email;
		this.password = password2;
		authorities.stream().forEach(s-> {
			if (s.getAuthority().equals("ROLE_USER"))
			this.roles.add(new Role(s.getAuthority(), 0));
			if(s.getAuthority().equals("ROLE_ADMIN"))
				this.roles.add(new Role(s.getAuthority(),1));	
		});
	}

}
