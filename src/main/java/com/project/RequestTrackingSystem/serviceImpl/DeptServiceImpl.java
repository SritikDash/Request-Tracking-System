package com.project.RequestTrackingSystem.serviceImpl;

import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.RequestTrackingSystem.models.Department;
import com.project.RequestTrackingSystem.models.User;
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

	public TreeMap<String, String> getAllParentDeptId() {

		List<Department> getAllDept = deptRepo.findAllParentDept();

//		To store Departments in sorted order according to DeptID
		TreeMap<String, String> treeMapDeptCodes = new TreeMap<String, String>();

		for (Department codes : getAllDept) {

			treeMapDeptCodes.put(codes.getDeptCode(), codes.getDepartmentName());
		}

		System.out.println(treeMapDeptCodes);
		return treeMapDeptCodes;
	}

	public TreeMap<Integer, String> getAllDeptId() {

		List<Department> getAllDept = deptRepo.findAll();

//		To store Departments in sorted order according to DeptID
		TreeMap<Integer, String> treeMapDeptCodes = new TreeMap<Integer, String>();

		for (Department codes : getAllDept) {

			treeMapDeptCodes.put(codes.getDeptId(), codes.getDepartmentName());
		}

		System.out.println(treeMapDeptCodes);
		return treeMapDeptCodes;
	}
	
	public Department getByDeptId(int id) {
		return this.deptRepo.getById(id);
	}
	
	
	
	////////////////////////////Fix the Edit Department/////////////////////////////////////////////////
	public String edit(Department dept) {
		
		if (dept.getParentDepartmentCode().compareTo("none") == 0) {
			dept.setParentDepartmentCode(dept.getDeptCode());
		}
		
		dept.setCreatedBy(userRepo.getById(dept.getUserId()).getFirstName());
		this.deptRepo.save(dept);
		return "Saved Successfully";
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	public List<Department> getAllDepts() {
		// TODO Auto-generated method stub
		return this.deptRepo.findAll();
	}
	
	public List<Department> getSortedDeptByCreatedDate() {
		return this.deptRepo.findAllByOrderByCreatedDateDesc();
	}
	
	public Page<Department> findPaginated(Pageable pageable) {
    	List<Department> dept = this.deptRepo.findAll();
    	
    	
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        
        
        int startItem = currentPage * pageSize;
        List<Department> list;

        if (dept.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, dept.size());
            list = dept.subList(startItem, toIndex);
        }

        Page<Department> deptPage
          = new PageImpl<Department>(list, PageRequest.of(currentPage, pageSize), dept.size());

        return deptPage;
    }
	
	
	
	
	public Page<Department> searchByDeptField(Pageable pageable, String searchPattern) {
		List<Department> dept = this.deptRepo.searchByDepartment(searchPattern);
    	
    	
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        
        
        int startItem = currentPage * pageSize;
        List<Department> list;

        if (dept.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, dept.size());
            list = dept.subList(startItem, toIndex);
        }

        Page<Department> deptPage
          = new PageImpl<Department>(list, PageRequest.of(currentPage, pageSize), dept.size());

        return deptPage;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	public TreeMap<String, String> getAllParentDeptIdByUserId(int userId) {

		List<Department> getAllDept = deptRepo.findAllParentDeptCodesByUser(userId);

//		To store Departments in sorted order according to DeptID
		TreeMap<String, String> treeMapDeptCodes = new TreeMap<String, String>();

		for (Department codes : getAllDept) {
			treeMapDeptCodes.put(codes.getDeptCode(), codes.getDepartmentName());
		}

		System.out.println(treeMapDeptCodes);
		return treeMapDeptCodes;
	}
	
	
	
	
}
