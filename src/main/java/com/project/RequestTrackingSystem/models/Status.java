package com.project.RequestTrackingSystem.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="status_codes")

public class Status {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="status_id")
	private int statusId;
	
	@Column(name="status_code")
	private String statusCode;

	@Column(name="status_desc")
	private String statusDesc;
	
	@Column(name="created_date")
	private Date createdDate = new Date();
	
	@Column(name="created_by")
	private int createdBy;
	
	@Column(name="request_id")
	private int requestId;
	
	
	
//	@OneToOne(cascade = CascadeType.ALL, targetEntity = RequestsComments.class)
//	@JoinColumn(name = "request_comment_id", referencedColumnName = "request_comment_id")
//	private RequestsComments requestCommentId;
//	
//	
//	@ManyToOne(targetEntity = Requests.class)
//	@JoinColumn(name = "request_id")
//	private Requests request;
//	

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "request_id")
//	@JsonBackReference
//	private Requests request;

	/*
	* @OneToOne(mappedBy = "stausEntity",cascade = CascadeType.ALL) private
	* CommentsEntity commentsEntity;
	*/

	
	

	public int getStatusId() {
		return statusId;
	}
//
//	public Requests getRequest() {
//		return request;
//	}
//
//	public void setRequest(Requests request) {
//		this.request = request;
//	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}


}
