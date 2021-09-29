package com.project.RequestTrackingSystem.repos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.RequestTrackingSystem.models.UserDeptAccess;

@Repository
public interface UserDeptAccessRepo extends JpaRepository<UserDeptAccess, Integer>{
	
	@Modifying
	@Transactional
	@Query(value = "Delete from user_dept_access where userid = :userId", nativeQuery = true)
	public int deleteFromUserDeptAccess(int userId);
}
