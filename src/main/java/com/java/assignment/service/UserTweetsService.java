package com.java.assignment.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.java.assignment.api.request.UserTweetsCreationRequest;
import com.java.assignment.api.response.RestResponse;

public interface UserTweetsService {

	ResponseEntity<RestResponse> createAUserTweet(Optional<UserTweetsCreationRequest> userTweetsCreationRequest);

	ResponseEntity<RestResponse> fetchAllTweetsFromASpecificDate(String usernameField, String dateField);

	ResponseEntity<RestResponse> deleteAllTweetsOfUser(String usernameField);
}
