package com.java.assignment.api.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserCreationRequest {

	@Size(min = 8, max = 11, message = "Username should be between 8 and 10 characters.")
	@NotNull
	private String username;
	private String firstName;
	private String lastName;
	private String profilePhoto;
	private long createdOn;
	private String contactEmail;
	private String contactPhone;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public long getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Override
	public String toString() {
		return "UserCeationRequest [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", profilePhoto=" + profilePhoto + ", createdOn=" + createdOn + ", contactEmail=" + contactEmail
				+ ", contactPhone=" + contactPhone + "]";
	}
}