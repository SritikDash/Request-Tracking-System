package com.project.RequestTrackingSystem.models;

public class UserDept {
	private int userId;
	
	private String userName;
	
	
	public UserDept(int userId, String userName) {
		
		this.userId = userId;
		this.userName = userName;
	}
	
	public int getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
