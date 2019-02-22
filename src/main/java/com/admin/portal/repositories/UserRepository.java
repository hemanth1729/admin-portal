package com.admin.portal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admin.portal.models.User;

public interface UserRepository extends JpaRepository<User, Long> { 
	
//	User findById(Long id);

}
