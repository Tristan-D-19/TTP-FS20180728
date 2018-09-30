package com.spotify.assessment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;


/**
 * 
 * @author Tristan
 *This class represents user roles for use with spring security. 
 *A role grants a user certain access, depending on the role.
 */

@Data
@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;
	
	private String role;
	
	private int roleAccess;
	
	public Role(String role, int roleAccess) {
		this.role = role;
		this.roleAccess = roleAccess;
	}
}
