package com.web_app.personal_finance.exception;

public class ErrorResponse {
	
	private String message;
	private int statusCode;
	private long timestamp;
	private String path;
	
	public ErrorResponse(String message, int statusCode,String Path) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.timestamp = System.currentTimeMillis();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
