package com.java.assignment.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.java.assignment.api.request.UserCreationRequest;
import com.java.assignment.api.response.RestResponse;

public interface UsersService {

	public ResponseEntity<RestResponse> createAUser(Optional<UserCreationRequest> userCreationRequest);

}
