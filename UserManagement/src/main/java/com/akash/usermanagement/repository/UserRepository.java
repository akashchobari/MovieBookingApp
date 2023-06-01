package com.akash.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akash.usermanagement.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUserName(String username);

}
