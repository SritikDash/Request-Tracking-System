package com.project.RequestTrackingSystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.RequestTrackingSystem.models.UserDeptAccess;

@Repository
public interface UserDeptAccessRepo extends JpaRepository<UserDeptAccess, Integer>{

}
