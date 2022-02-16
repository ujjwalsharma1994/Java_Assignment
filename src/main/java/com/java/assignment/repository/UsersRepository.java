package com.java.assignment.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.java.assignment.model.Users;


public interface UsersRepository extends MongoRepository<Users, Integer> {

	@Query(fields = "{'username' : 1}")
	Users findFirstUsersByUsername(String username);

	@Query(fields = "{'username': 1}")
	Optional<Users> findByUserId(int userId);

}