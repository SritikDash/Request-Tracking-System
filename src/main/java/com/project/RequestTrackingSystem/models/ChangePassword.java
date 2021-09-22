package com.project.RequestTrackingSystem.models;


public class ChangePassword {
	
	
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	private int userId;
	private String msg = "";
	private boolean isVisited = false;
	
	private String emailName;
	
	public String getEmailName() {
		return emailName;
	}
	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}
	public boolean getVisited() {
		return isVisited;
	}
	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	public int getUserId() {
		return userId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
}
