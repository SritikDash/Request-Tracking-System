package com.project.RequestTrackingSystem.services;

import java.util.List;
import java.util.TreeMap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.RequestTrackingSystem.models.Department;
import com.project.RequestTrackingSystem.models.Requests;



public interface DeptService {
	public String save(Department dept);
	public TreeMap<Integer, String> getAllDeptId();
	public TreeMap<String, String> getAllParentDeptId();
	
	public Department getByDeptId(int id);

	public String edit(Department dept);
	public List<Department> getAllDepts();
	
	public List<Department> getDept();
	
	public Page<Department> findPaginated(Pageable pageable);
	
	
	public Page<Department> searchByDeptField(Pageable pageable, String searchPattern);
	
	public TreeMap<String, String> getAllParentDeptIdByUserId(int userId);

}
