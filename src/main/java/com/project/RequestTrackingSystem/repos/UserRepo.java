package com.project.RequestTrackingSystem.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.RequestTrackingSystem.models.User;


@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	public User findByUserEmail(String email);
	public User findByUserName(String userName);
	
	
	@Query(value="Select * from rts.user u\r\n"
			+ "Inner join rts.user_dept_access uda ON u.user_id = uda.userid\r\n"
			+ "Inner join rts.dept d ON uda.deptid = d.dept_id where u.user_id = :id", nativeQuery = true)
	public User findAllDataByJoin(int id);
	
//	==================================================================================================================
	
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="Update rts.user set user_password = :password where user_email = :email", nativeQuery = true)
	public int updatePassword(@Param("email") String email, @Param("password") String password);
	
//	@Modifying
//	@Query("Update User u set u.userPassword = :password where u.email = :email")
//	public void updatePassword(String email, String password);
	
	
	
	
	@Query(value="Select * from rts.dept d\r\n"
			+ "Inner join rts.user_dept_access uda ON d.dept_id = uda.deptid\r\n"
			+ "Inner join rts.user u On u.user_id = uda.userid\r\n"
			+ "where d.dept_id = :deptId", nativeQuery = true)
	public List<User> getAllUsersByDeptId(int deptId);
	
	
	@Query(value = "Select uda.is_admin from user u\n"
			+ "Inner Join user_dept_access uda ON u.user_id = uda.userid\n"
			+ "where \n"
			+ "uda.userid = :userId\n"
			, nativeQuery = true)
	public List<Boolean> getAdmin(@Param("userId") int userId);
	
	
	
	@Query(value = "Select * from rts.user where username = :userName and user_id != :userId", nativeQuery = true)
	public User checkDuplicateUserName(String userName, int userId);
	
	@Query(value = "Select * from rts.user where user_email = :userEmail and user_id != :userId", nativeQuery = true)
	public User checkDuplicateUserEmail(String userEmail, int userId);
	
	@Query(value = "SELECT * FROM rts.user\n"
			+ "WHERE username LIKE %:searchPattern%\n"
			+ "OR user_first_name LIKE %:searchPattern%\n"
			+ "OR user_last_name LIKE %:searchPattern%", nativeQuery = true)
	public List<User> searchUser(@Param("searchPattern") String searchPattern);
	
	
}
