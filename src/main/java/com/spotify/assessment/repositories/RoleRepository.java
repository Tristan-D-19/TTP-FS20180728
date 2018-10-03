package com.spotify.assessment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spotify.assessment.domain.Role;
import java.lang.String;
import java.util.List;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByRole(String role);
	
	Role findByRoleAccess(int roleaccess);
}
