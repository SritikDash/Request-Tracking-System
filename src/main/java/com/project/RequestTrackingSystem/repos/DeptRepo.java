package com.project.RequestTrackingSystem.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.RequestTrackingSystem.models.Department;


@Repository
public interface DeptRepo extends JpaRepository<Department, Integer> {
	public List<Department> findAllByParentDepartmentCode(String parentDepartmentCode);
	public List<Department> findAllByDeptCode(String departmentCode);
	
	@Query(value="SELECT * FROM rts.dept where dept_code  = parent_department_code", nativeQuery = true)
	public List<Department> findAllParentDept();
	
	public List<Department> findAllByOrderByCreatedDateDesc();
	
	
	@Query(value="SELECT * FROM rts.dept\n"
			+ "where dept_code LIKE %:searchPattern%\n"
			+ "OR department_name LIKE %:searchPattern%", nativeQuery = true)
	public List<Department> searchByDepartment(@Param("searchPattern") String searchPattern);
	
	
	
	
	@Query(value = "Select d.* from rts.user u\r\n"
			+ "Inner join rts.user_dept_access uda ON u.user_id = uda.userid\r\n"
			+ "Inner join rts.dept d ON uda.deptid = d.dept_id where u.user_id = :userId\r\n"
			+ "and d.dept_code  = d.parent_department_code and uda.is_admin = true;", nativeQuery = true)
	public List<Department> findAllParentDeptCodesByUser(int userId);
	
}
