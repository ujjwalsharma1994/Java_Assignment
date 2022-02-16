package com.java.assignment.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.assignment.api.request.UserCreationRequest;
import com.java.assignment.api.response.RestResponse;
import com.java.assignment.service.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UsersService usersService;
	
	@PostMapping(path = "/create")
	public ResponseEntity<RestResponse> createAUser(@RequestBody UserCreationRequest userCreationRequest) {

		Optional<UserCreationRequest> optionalUserCreationRequest = Optional.of(userCreationRequest);
		return usersService.createAUser(optionalUserCreationRequest);
	}
}
