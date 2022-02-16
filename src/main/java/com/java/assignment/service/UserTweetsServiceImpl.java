package com.java.assignment.service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.java.assignment.util.EpochToDateTimeConversion.*;
import com.java.assignment.api.request.UserTweetsCreationRequest;
import com.java.assignment.api.response.RestResponse;
import com.java.assignment.config.ValidationConfig;
import com.java.assignment.dao.CountersDAO;
import com.java.assignment.model.Counters;
import com.java.assignment.model.UserTweets;
import com.java.assignment.model.Users;
import com.java.assignment.repository.UserTweetsRepository;
import com.java.assignment.repository.UsersRepository;

@Service
public class UserTweetsServiceImpl implements UserTweetsService {

	@Autowired
	private UserTweetsRepository userTweetsRepository;
	@Autowired
	private CountersDAO sequence;
	@Autowired
	private ValidationConfig validationConfig;
	@Autowired
	private UsersRepository usersRepository;

	@Override
	public ResponseEntity<RestResponse> createAUserTweet(Optional<UserTweetsCreationRequest> userTweetsCreationRequest) {

		LinkedHashMap<Object, Object> responseObj = new LinkedHashMap<>();

		if (userTweetsCreationRequest.isPresent()) { //if request payload is null or not

			// it will check in which fields the violation of rule has been practised.
			Set<ConstraintViolation<UserTweetsCreationRequest>> violations = validationConfig.getValidator().validate(userTweetsCreationRequest.get());

			if (violations.size() > 0) { // if size of error(s) is greater than 0 then show the errors in the response.
				
				List<String> errors = violations.stream().map(e->e.getMessage()).collect(Collectors.toList());
				responseObj.put("Output", errors);
				
				return ResponseEntity.status(409).body(new RestResponse(409, "Error occured.", responseObj, false));
			}else {

				Optional<Users> user = usersRepository.findByUserId(userTweetsCreationRequest.get().getUserId());

				if (user.isPresent()) {

					int tweetId = 0;
		
					UserTweets userTweets = new UserTweets(); // create object of UserTweets
					
					UserTweetsCreationRequest userTweetsCreationOptionalData = userTweetsCreationRequest.get();
		
					userTweets.setUserId(userTweetsCreationOptionalData.getUserId());
					userTweets.setTweet(userTweetsCreationOptionalData.getTweet());
					userTweets.setTimeStamp(System.currentTimeMillis());
			
					try {
	
						tweetId = sequence.getNextSequenceValue("userId"); // generating netxt counter of primary key
					}catch (Exception e) { // if counter do not exist in db, create a new one
						Counters counter = new Counters();
						counter.setName("userId");
						counter.setSequence(2);
						
						sequence.saveCounterObject(counter);
						tweetId = 1;
					}
	
					userTweets.setTweetId(tweetId);
	
					userTweetsRepository.save(userTweets);

					String dateTime = convertToDateTime(userTweets.getTimeStamp()); // convert epoch to LocalDateTime
					responseObj.put("tweet_id", tweetId);
					responseObj.put("created_timestamp", dateTime);
	
					return ResponseEntity.ok().body(new RestResponse(200, "Tweet created successfully.", responseObj, true));
				}else
					return ResponseEntity.status(404).body(new RestResponse(404, "given user_id does not exist in our system.",  "No data found.", false)); 
			}
		}else {

			responseObj.put("Output", "No value provided.");

			return ResponseEntity.status(409).body(new RestResponse(409, "Please provide data.",  responseObj, false));
		}
	}

	@Override
	public ResponseEntity<RestResponse> fetchAllTweetsFromASpecificDate(String usernameField, String dateField) {

		LinkedHashMap<Object, Object> responseObj = new LinkedHashMap<>(); // to show the response

		if (usernameField != null && dateField != null) {

			Users user = usersRepository.findFirstUsersByUsername(usernameField);

			if (user == null) {

				responseObj.put("Error", "No data found.");
				responseObj.put("Reason", usernameField+" does not exist in our system.");

				return ResponseEntity.status(404).body(new RestResponse(404, "Error occured", responseObj, false));
			}else {

				long epochSeconds = 0;
				try {
					epochSeconds = Long.parseLong(dateField);
				}catch(Exception e) {
					epochSeconds = createTimestampFromLocalDateTime(LocalDateTime.parse(dateField)); // convert LocalDateTime to epochTime
				}
	
				List<UserTweets> userTweets = userTweetsRepository.findAllUserTweets(user.getUserId(), epochSeconds);

				if (userTweets.size() > 0) {

					responseObj.put("Number of tweet(s) found", userTweets.size());
					responseObj.put("Tweet(s) list", userTweets);
				}
				else
					responseObj.put("No Tweets found", 0+" tweets posted by user.");

				return ResponseEntity.ok().body(new RestResponse(200, "Tweet(s) fetched successfully.", responseObj, true));
	
			}
		}else {

			responseObj.put("Error", "Please provide valid data.");
			responseObj.put("Reason", "Either username or date value not provided");
			return ResponseEntity.status(409).body(new RestResponse(409, "Error occured",  responseObj, false));
		}
	}

	@Override
	public ResponseEntity<RestResponse> deleteAllTweetsOfUser(String usernameField) {

		LinkedHashMap<Object, Object> responseObj = new LinkedHashMap<>();

		if (usernameField != null) {

			Users user = usersRepository.findFirstUsersByUsername(usernameField);

			if (user == null) {

				return ResponseEntity.status(404).body(new RestResponse(404, usernameField+" does not exist in our system.",  "No data found.", false));
			}else {

				List<UserTweets> userTweets = userTweetsRepository.findAllUserTweetsByUserId(user.getUserId());
	
				long noOfDeletedTweets = userTweetsRepository.deleteAllUserTweetsByUserId(user.getUserId());
	
				if (noOfDeletedTweets > 0) {

					responseObj.put("Number of tweet(s) deleted", noOfDeletedTweets);
					responseObj.put("Tweet(s) list", userTweets);
				}
				else
					responseObj.put("No Tweets found", noOfDeletedTweets+" tweets of "+usernameField+" found.");
	
				return ResponseEntity.ok().body(new RestResponse(200, "Tweet(s) deleted successfully.",  responseObj, true));
			}
		}else {

			responseObj.put("Output", "username not provided.");

			return ResponseEntity.status(409).body(new RestResponse(409, "Error occured.",  responseObj, false));
		}
	}
}
