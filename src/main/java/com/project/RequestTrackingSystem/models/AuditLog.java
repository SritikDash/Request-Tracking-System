package com.project.RequestTrackingSystem.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "audit_log")
public class AuditLog {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "audit_id")
	private int auditId;
	
	@Column(name = "change_type")
	private String changeType;
	
	@Column(name = "change_type_id")
	private int changeTypeId;
	
	@Column(name = "audit_desc")
	private String auditDesc;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "createdBy")
	private int createdBy;
	
	@Column(name = "comments")
	private String comments;
	
	@Column(name = "status")
	private String status;

	public int getAuditId() {
		return auditId;
	}

	public void setAuditId(int auditId) {
		this.auditId = auditId;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public int getChangeTypeId() {
		return changeTypeId;
	}

	public void setChangeTypeId(int changeTypeId) {
		this.changeTypeId = changeTypeId;
	}

	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
