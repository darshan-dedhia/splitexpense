package edu.darshandedhia.info6250.response;

import java.util.ArrayList;
import java.util.List;

public class MultiResponse {
	
	private int statusCode;
	private String status;
	private List<String> messages = new ArrayList<>();
	
	public MultiResponse(int statusCode, String status, List<String> messages) {
		this.statusCode = statusCode;
		this.status = status;
		this.messages = messages;
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
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
}
