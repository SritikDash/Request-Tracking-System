package com.project.RequestTrackingSystem.models;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="requests")

public class Requests {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="request_id")
	private int requestId;

	@Column(name="request_number")
	private String requestNumber;
	
	@Column(name="request_title")
	private String requestTitle;
	
	@Column(name="request_description")
	private String requestDescription;
	
	
	
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name="assigned_to")
	private User assignedTo;
	
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name="created_by")
	private User createdBy;
	
	
	
	@Column(name="assigned_date")
	private Date assignedDate = new Date();
	
	
	@Column(name="initial_comments")
	private String initialComments;
	
	@Column(name="initial_status")
	private String initialStatus = "New Request";
	
	

	@ManyToOne(targetEntity = Department.class)
	@JoinColumn(name="request_dept")
	private Department requestDept;

	
	
//	@OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
//	@JsonManagedReference
//	private List<Status> status;
//
//	@OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
//	@JsonManagedReference
//	private List<RequestsComments> requestComment;
	
//	@OneToMany(mappedBy = "request", targetEntity = RequestsComments.class)
//	List<RequestsComments> CommentList;
//	
//	@OneToOne(mappedBy = "request", targetEntity = Status.class)
//	List<Requests> StatusList;

	
	
	@Transient
	private int assignedUser;

	

	@Transient
	private int requestAge;
	
	
	

	@Transient
	private static String seqNum = "00001";
//	public Requests() {
//		
//	}


	
	public User getAssignedTo() {
		return assignedTo;
	}

	public User getCreatedBy() {
		return createdBy;
	}

//	public List<Status> getStatus() {
//		return status;
//	}
//
//	public void setStatus(List<Status> status) {
//		this.status = status;
//	}
//
//	public List<RequestsComments> getRequestComment() {
//		return requestComment;
//	}
//
//	public void setRequestComment(List<RequestsComments> requestComment) {
//		this.requestComment = requestComment;
//	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}


	public static String getSeqNum() {
		return seqNum;
	}
	
	public static void setSeqNum(String seqNum) {
		Requests.seqNum = seqNum;
	}
	
	
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	

	

	public String getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	public String getRequestTitle() {
		return requestTitle;
	}
	public void setRequestTitle(String requestTitle) {
		this.requestTitle = requestTitle;
	}
	public String getRequestDescription() {
		return requestDescription;
	}
	public void setRequestDescription(String requestDescription) {
		this.requestDescription = requestDescription;
	}
	
	
	
	
	
	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	
	public String getInitialComments() {
		return initialComments;
	}
	public void setInitialComments(String initialComments) {
		this.initialComments = initialComments;
	}
	
	

	public Department getRequestDept() {
		return requestDept;
	}

	public void setRequestDept(Department requestDept) {
		this.requestDept = requestDept;
	}

	public int getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(int assignedUser) {
		this.assignedUser = assignedUser;
	}

	public String getInitialStatus() {
		return initialStatus;
	}

	public void setInitialStatus(String initialStatus) {
		this.initialStatus = initialStatus;
	}

	
	public int getRequestAge() {
		return requestAge;
	}

	public void setRequestAge(int requestAge) {
		this.requestAge = requestAge;
	}
	



}