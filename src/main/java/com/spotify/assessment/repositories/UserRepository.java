package com.spotify.assessment.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spotify.assessment.domain.User;
import java.lang.String;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByEmail(String email);
	
	List<User> findByName(String name);
}
