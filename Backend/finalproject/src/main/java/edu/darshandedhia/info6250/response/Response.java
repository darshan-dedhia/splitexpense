package edu.darshandedhia.info6250.response;

public class Response {
	private int statusCode;
	private String status;    //Represents Success or Failure. Values = {"SUCCESS", "ERROR", "FAILURE"}
	private String message;   // Represents relevant message based on success or failures
	
	public Response(int statusCode, String status, String message) {
		this.statusCode = statusCode;
		this.status = status;
		this.message = message;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
