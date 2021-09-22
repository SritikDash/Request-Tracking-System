package com.project.RequestTrackingSystem.models;



import java.util.Date;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;




@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private int userId;
	@Column(name="username")
	private String userName;
	
	@Column(name="user_first_name")
	private String firstName;
	
	@Column(name="user_last_name")
	private String lastName;
	
	@Column(name="user_email")
	private String userEmail;
	@Column(name="user_password")
	private String userPassword;
	@Column(name="created_date")
	private Date createdDate = new Date();
	@Column(name="created_by")
	private String createdBy = "Admin";
	@Column(name="is_user_active")
	private Boolean userActive;
	
	
//	Joining User and Dept Many to Many using joining table user_dept_access
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
			name = "user_dept_access",
			joinColumns = @JoinColumn(name = "userid"),
			inverseJoinColumns = @JoinColumn(name = "deptid"))
	@JsonManagedReference
	List<Department> deptAccess;
	
	
	@OneToMany(mappedBy = "assignedTo", targetEntity = Requests.class)
	List<Requests> assignedList;
	
	
	
	@OneToMany(mappedBy = "createdBy", targetEntity = Requests.class)
	List<Requests> createdList;
	
	
	
	public List<Department> getDeptAccess() {
		return deptAccess;
	}
	public void setDeptAccess(List<Department> deptAccess) {
		this.deptAccess = deptAccess;
	}
	@Transient
	private Boolean isInvalid = false;
	
	@Transient
	private String msg = "";
	
	
	
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Boolean getIsInvalid() {
		return isInvalid;
	}
	public void setIsInvalid(Boolean isInvalid) {
		this.isInvalid = isInvalid;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public String getUserPassword() {
		return userPassword;
	}
	 
	public String getCreatedBy() {
		return createdBy;
	}
	public Boolean getUserActive() {
		return userActive;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setUserActive(Boolean userActive) {
		this.userActive = userActive;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<Requests> getAssignedList() {
		return assignedList;
	}
	public List<Requests> getCreatedList() {
		return createdList;
	}
	public void setAssignedList(List<Requests> assignedList) {
		this.assignedList = assignedList;
	}
	public void setCreatedList(List<Requests> createdList) {
		this.createdList = createdList;
	}
	
	
}
