package com.project.RequestTrackingSystem.models;

import lombok.Data;

@Data
public class MailRequest {
	
	private String name;
	private String to;
	private String from = "16.amitraj2000@gmail.com";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	private String subject;

}
