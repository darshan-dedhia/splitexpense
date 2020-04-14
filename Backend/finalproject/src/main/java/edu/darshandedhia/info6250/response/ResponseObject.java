package edu.darshandedhia.info6250.response;

public class ResponseObject extends Response{
	private Object object;
	
	public ResponseObject(int statusCode, String status, String message, Object object) {
		super(statusCode, status, message);
		this.object = object;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
}
