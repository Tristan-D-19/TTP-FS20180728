package com.spotify.assessment.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * This class represents user roles for use with spring security. 
 *A role grants a user certain access, depending on the role.
 * @author Tristan
 *
 */


@Entity
@Data
@EqualsAndHashCode(exclude = {"users"}) 
@ToString(exclude = "users")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;
	
	private String role;
	
	private int roleAccess;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	private Set<User> users;
	
	
	public Role(String role, int roleAccess) {
		this.role = role;
		this.roleAccess = roleAccess;
	}


	public Role(String role2) {
		this.role = role2;
	}


	
}
