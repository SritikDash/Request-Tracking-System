package com.project.RequestTrackingSystem.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.RequestTrackingSystem.models.Requests;
import com.project.RequestTrackingSystem.models.User;


@Repository
public interface RequestRepo extends JpaRepository<Requests, Integer> {
	@Query(value="SELECT * from rts.requests order by assigned_date desc", nativeQuery = true)
	public List<Requests> findAllByCreatedDateDesc();
	
	
	public List<Requests> findAllByAssignedToOrderByAssignedDateDesc(User user);
	
	
	public List<Requests> findAllByCreatedByOrderByAssignedDateDesc(User user);

	@Query(value="SELECT datediff(assigned_date, created_date) FROM rts.requests where request_id = :requestId", nativeQuery = true)
	public int getAge(@Param("requestId") int requestId);
	
	@Query(value="SELECT * FROM rts.requests WHERE request_number like %:searchPattern%  OR request_title like %:searchPattern% ", nativeQuery = true)
	public List<Requests> searchByRequestNumber(@Param("searchPattern") String searchPattern);
	
	@Query(value="Select * from rts.requests\r\n"
			+ "where request_dept IN( \r\n"
			+ "SELECT d.dept_id from user_dept_access ud\r\n"
			+ "inner join dept d on d.dept_id = ud.deptid\r\n"
			+ "where ud.is_admin = true and ud.userid = :userId)", nativeQuery = true)
	public List<Requests> getAllRequestsForDeptAdmin(int userId);
}
