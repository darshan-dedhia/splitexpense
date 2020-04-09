package edu.darshandedhia.info6250.response;

import java.util.ArrayList;
import java.util.List;

import edu.darshandedhia.info6250.pojo.ApiError;

public class ErrorResponse {
		
		private int statusCode;
		private String status;
		private List<ApiError> messages = new ArrayList<>();
		
		public ErrorResponse(int statusCode, String status, List<ApiError> messages) {
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
		public List<ApiError> getMessages() {
			return messages;
		}
		public void setMessages(List<ApiError> messages) {
			this.messages = messages;
		}
		
	
}
