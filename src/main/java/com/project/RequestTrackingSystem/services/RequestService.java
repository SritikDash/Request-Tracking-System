package com.project.RequestTrackingSystem.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.RequestTrackingSystem.models.AuditLog;
import com.project.RequestTrackingSystem.models.Requests;
import com.project.RequestTrackingSystem.models.RequestsComments;


public interface RequestService {
	public int saveRequest(Requests req, int flag);
	public String buildReqNumber(String deptCode,int seq);
	public List<Requests> getAllRequests();
	public Requests getRequestByID(int id);
//	public List<Requests> findRequestsWithSorting(String field);
	public Page<Requests> findRequestsWithPagination(int offset,int pageSize);
	public long getTotalRows();
	
	
	public List<Requests> getReqs();
	public Page<Requests> findPaginated(Pageable pageable);
//	public Page<Requests> findRequestsWithPaginationAndSorting(int offset,int pageSize,String field);
	
	
//	public List<Requests> getMyRequests(int userId);
	public Page<Requests> findPaginatedByUserId(Pageable pageable, int userId, int stats); 
	
	public int saveRequestsComments(Requests req, int userId);
	public int saveStatus(Requests req, int userId);
	
	
	
	public List<AuditLog> requestHistory(int reqId);
}
