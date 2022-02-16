package com.java.assignment.api.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserTweetsCreationRequest {

	@NotNull(message = "userId cannot be null")
	private Integer userId;
	@Size(min = 2, max = 140, message = "Tweet should be between 2 and 140 characters.")
	private String tweet;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
}