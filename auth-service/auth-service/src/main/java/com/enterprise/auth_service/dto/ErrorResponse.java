package com.enterprise.auth_service.dto;

import java.time.LocalDateTime;

public class ErrorResponse {
	
	private LocalDateTime localDateTime;
	private String error;
	private int status;
	
	public ErrorResponse(){
		
	}
	public ErrorResponse(LocalDateTime localDateTime, String error, int status) {
		this.localDateTime=localDateTime;
		this.status=status;
		this.error=error;
	}
	
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}
	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	

}
