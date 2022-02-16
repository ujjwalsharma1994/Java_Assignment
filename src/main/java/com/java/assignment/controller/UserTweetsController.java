package com.java.assignment.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.assignment.api.request.UserTweetsCreationRequest;
import com.java.assignment.api.response.RestResponse;
import com.java.assignment.service.UserTweetsService;

@RestController
@RequestMapping("/tweets")
public class UserTweetsController {

	@Autowired
	private UserTweetsService userTweetsService;
	
	@PostMapping(path = "/create")
	public ResponseEntity<RestResponse> createAUserTweet(@RequestBody UserTweetsCreationRequest userTweetsCreationRequest) {

		Optional<UserTweetsCreationRequest> userCreationRequestOptional = Optional.of(userTweetsCreationRequest);
		return userTweetsService.createAUserTweet(userCreationRequestOptional);
	}

	@GetMapping(path = "/fetch")
	public ResponseEntity<RestResponse> fetchAllTweetsFromASpecificDate(@RequestParam(name="username") String username,
			@RequestParam(name = "date") String date) {

		return userTweetsService.fetchAllTweetsFromASpecificDate(username, date);
	}

	@DeleteMapping(path = "/delete")
	public ResponseEntity<RestResponse> deleteAllTweetsOfUser(@RequestParam(name = "username", required = true) String username) {

		return userTweetsService.deleteAllTweetsOfUser(username);
	}
}