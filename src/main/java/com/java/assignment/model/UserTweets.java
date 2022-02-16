package com.java.assignment.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_tweets")
public class UserTweets {

	@Id
	private int tweetId; // unique key
	private int userId; // user who posted the tweet
	private long timeStamp; // storing the epoch time
	private String tweet; // text with constraints from 2 to 140 characters

	public int getTweetId() {
		return tweetId;
	}

	public void setTweetId(int tweetId) {
		this.tweetId = tweetId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	@Override
	public String toString() {
		return "UserTweets [tweetId=" + tweetId + ", tweetPostedByUserId=" + userId + ", timeStamp="
				+ timeStamp + "]";
	}
}