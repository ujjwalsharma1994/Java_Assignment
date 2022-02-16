package com.java.assignment.api.response;

public class RestResponse {

	public int statusCode;
	public String message;
	public Object output;
	public boolean success;

	public RestResponse(int statusCode, String message, Object output, boolean success) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.output = output;
		this.success = success;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getOutput() {
		return output;
	}

	public void setOutput(Object output) {
		this.output = output;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}