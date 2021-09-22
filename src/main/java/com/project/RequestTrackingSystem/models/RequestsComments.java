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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "requests_comments")

public class RequestsComments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "request_comment_id")
	private int requestCommentId;

	@Column(name = "request_sequence_no")
	private int requestSequenceNo;
	
	@Column(name = "request_comment")
	private String requestComment;
	
	@Column(name = "created_date")
	private Date createdDate = new Date();
	
	@Column(name = "created_by")
	private int createdBy;
	
	@Column(name = "request_id")
	private int requestId;
	
	

//	@OneToOne(mappedBy = "requestCommentId", targetEntity = Status.class)
//	private Status status;
//
//	@ManyToOne(targetEntity = Requests.class)
//	@JoinColumn(name = "request_id")
//	private Requests request;

	
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "request_id")
//	@JsonBackReference
//	private Requests request;
	
	
	
	
	
	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getRequestCommentId() {
		return requestCommentId;
	}

	public void setRequestCommentId(int requestCommentId) {
		this.requestCommentId = requestCommentId;
	}


//	public Requests getRequest() {
//		return request;
//	}
//
//	public void setRequest(Requests request) {
//		this.request = request;
//	}

	public int getRequestSequenceNo() {
		return requestSequenceNo;
	}

	public void setRequestSequenceNo(int requestSequenceNo) {
		this.requestSequenceNo = requestSequenceNo;
	}

	public String getRequestComment() {
		return requestComment;
	}

	public void setRequestComment(String requestComment) {
		this.requestComment = requestComment;
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