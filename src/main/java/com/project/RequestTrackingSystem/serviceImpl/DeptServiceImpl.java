package com.project.RequestTrackingSystem.serviceImpl;

import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.RequestTrackingSystem.models.Department;

import com.project.RequestTrackingSystem.repos.DeptRepo;
import com.project.RequestTrackingSystem.repos.UserRepo;
import com.project.RequestTrackingSystem.services.DeptService;

@Service
public class DeptServiceImpl implements DeptService {

	@Autowired
	DeptRepo deptRepo;

	@Autowired
	UserRepo userRepo;

	public String save(Department dept) {
		System.out.println(dept.getDepartmentName() + dept.getDeptCode() + dept.getParentDepartmentCode()
				+ dept.getDeptActive() + dept.getUserId());
		List<Department> d;
		String msg;
		dept.setCreatedBy(userRepo.getById(dept.getUserId()).getFirstName());

		if (dept.getParentDepartmentCode().compareTo("none") == 0) {
			dept.setParentDepartmentCode(dept.getDeptCode());
		}

		if (this.deptRepo.findAll().isEmpty()) {

			if (dept.getDeptCode().length() == 4) {
				if (dept.getDeptCode().compareTo(dept.getParentDepartmentCode()) == 0) {
					deptRepo.save(dept);
					msg = "Department Saved Successfully";
				} else {
					msg = "Parent Department Doesn't Exist!! ";
				}
			} else {
				msg = "Department Code Length should be 4!!";
			}

//			if(dept.getDeptCode().compareTo(dept.getParentDepartmentCode()) == 0) {
//				deptRepo.save(dept);
//				msg = "Department Saved Successfully";
//			} else {
//				msg = "Parent Department Doesn't Exist!! ";
//			}
		}

		d = deptRepo.findAllByDeptCode(dept.getDeptCode());

		if (d.isEmpty()) {
			if (dept.getDeptCode().length() == 4) {
				if (dept.getDeptCode().compareTo(dept.getParentDepartmentCode()) == 0) {
					deptRepo.save(dept);
					msg = "Department Saved Successfully";
				} else {
					d = deptRepo.findAllByDeptCode(dept.getParentDepartmentCode());
					if (d.isEmpty()) {
						msg = "Parent Department Doesn't Exist!! ";
					} else {
						deptRepo.save(dept);
						msg = "Department Saved Successfully";
					}
				}
			} else {
				msg = "Department Code Length should be 4!!";
			}

		} else {
			msg = "Department Code already Exists!!";
		}

		return msg;
	}

	public TreeMap<Integer, String> getAllParentDeptId() {

		List<Department> getAllDept = deptRepo.findAllParentDept();

//		To store Departments in sorted order according to DeptID
		TreeMap<Integer, String> treeMapDeptCodes = new TreeMap<Integer, String>();

		for (Department codes : getAllDept) {

			treeMapDeptCodes.put(codes.getDeptId(), codes.getDeptCode());
		}

		System.out.println(treeMapDeptCodes);
		return treeMapDeptCodes;
	}

	public TreeMap<Integer, String> getAllDeptId() {

		List<Department> getAllDept = deptRepo.findAll();

//		To store Departments in sorted order according to DeptID
		TreeMap<Integer, String> treeMapDeptCodes = new TreeMap<Integer, String>();

		for (Department codes : getAllDept) {

			treeMapDeptCodes.put(codes.getDeptId(), codes.getDeptCode());
		}

		System.out.println(treeMapDeptCodes);
		return treeMapDeptCodes;
	}
	
	public Department getByDeptId(int id) {
		return this.deptRepo.getById(id);
	}
	
	public String edit(Department dept) {
		this.deptRepo.save(dept);
		return "Saved Successfully";
	}
	
	
	public List<Department> getAllDepts() {
		// TODO Auto-generated method stub
		return this.deptRepo.findAll();
	}
	
}
