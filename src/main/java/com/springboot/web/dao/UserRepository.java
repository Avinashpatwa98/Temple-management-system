package com.springboot.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.web.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.email = :email")
	public User getUserByUserName(@RequestParam("email") String email);// method and
														// param use for take dynamic value input


 }
