package com.java.assignment.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.java.assignment.api.request.UserCreationRequest;
import com.java.assignment.api.response.RestResponse;
import com.java.assignment.config.ValidationConfig;
import com.java.assignment.dao.CountersDAO;
import com.java.assignment.model.Counters;
import com.java.assignment.model.Users;
import com.java.assignment.repository.UsersRepository;
import com.java.assignment.security.JwtTokenUtil;
import com.java.assignment.util.EpochToDateTimeConversion;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private ValidationConfig validationConfig;
	@Autowired
	private CountersDAO sequence;
	@SuppressWarnings("unused")
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public ResponseEntity<RestResponse> createAUser(Optional<UserCreationRequest> userCreationRequest) {

		if (userCreationRequest.isPresent()) { // check if the data coming is null or not.

			LinkedHashMap<Object, Object> responseObj = new LinkedHashMap<>();

			// it will check in which fields the violation of rule has been practised.
			Set<ConstraintViolation<UserCreationRequest>> violations = validationConfig.getValidator().validate(userCreationRequest.get());

			if (violations.size() > 0) { // if size of error(s) is greater than 0 then show the errors in the response.
				
				List<String> errors = violations.stream().map(e->e.getMessage()).collect(Collectors.toList());
				responseObj.put("Error(s)", errors);
				
				return ResponseEntity.status(409).body(new RestResponse(409, "Error occured.", responseObj, false));

			}else {

				// check if the given username exist in db

				Users ifUsernameExist = usersRepository.findFirstUsersByUsername(userCreationRequest.get().getUsername());
				
				if (ifUsernameExist != null) {

					responseObj.put("Output", "username "+userCreationRequest.get().getUsername()+" already exist. Please try something else.");

					return ResponseEntity.status(409).body(new RestResponse(409, "Error occured.", responseObj, false));
				}

				int userId = 0;

				Users user = new Users(); // create object of user
				
				UserCreationRequest usersCreationOptionalData = userCreationRequest.get();

				user.setUsername(usersCreationOptionalData.getUsername());
				user.setFirstName(usersCreationOptionalData.getFirstName());
				user.setLastName(usersCreationOptionalData.getLastName());
				user.setProfilePhoto(usersCreationOptionalData.getProfilePhoto());
				user.setContactPhone(usersCreationOptionalData.getContactPhone());
				user.setContactEmail(usersCreationOptionalData.getContactEmail());
				user.setCreatedOn(System.currentTimeMillis()); // current time in epoch datetime

				try {
					userId = sequence.getNextSequenceValue("userId"); // generating next counter of primary key
				}catch (Exception e) { // if counter do not exist in db, create a new one which will calculate the current counter of the key
					Counters counter = new Counters();
					counter.setName("userId");
					counter.setSequence(2); // set the next count data

					sequence.saveCounterObject(counter);
					userId = 1;
				}

				final String token = jwtTokenUtil.generateToken(user); // jwt token generated
				String dateTime = EpochToDateTimeConversion.convertToDateTime(user.getCreatedOn()); //generate date

				responseObj.put("JWT_Token", token);
				responseObj.put("user_id", userId);
				responseObj.put("created_timestamp", dateTime);				

				user.setJwtToken(token);
				user.setUserId(userId);
				usersRepository.save(user);

				return ResponseEntity.ok().body(new RestResponse(200, "User created successfully", responseObj, true));
			}
		}else // handle null data situation
			return ResponseEntity.status(409).body(new RestResponse(409, "Please provide valid details.", null, false));
	}
}