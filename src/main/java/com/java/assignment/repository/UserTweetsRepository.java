package com.java.assignment.repository;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.java.assignment.model.UserTweets;

public interface UserTweetsRepository extends MongoRepository<UserTweets, Integer> {

	@Query("{'userId': ?0, 'timeStamp': {$gte: ?1}}")
	List<UserTweets> findAllUserTweets(int userId, long epochSeconds);

	long deleteAllUserTweetsByUserId(int userId);

	@Field("{'tweet': 1, 'tweetId': 1}")
	List<UserTweets> findAllUserTweetsByUserId(int userId);
}