package com.project.RequestTrackingSystem.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.RequestTrackingSystem.models.Requests;
import com.project.RequestTrackingSystem.models.User;


@Repository
public interface RequestRepo extends JpaRepository<Requests, Integer> {
	@Query(value="SELECT * from rts.requests order by assigned_date desc", nativeQuery = true)
	public List<Requests> findAllByCreatedDateDesc();
	
	
	public List<Requests> findAllByAssignedToOrderByAssignedDateDesc(User user);
	
	
	public List<Requests> findAllByCreatedByOrderByAssignedDateDesc(User user);




}
