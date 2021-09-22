package com.project.RequestTrackingSystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.RequestTrackingSystem.models.Status;


@Repository
public interface StatusRepo  extends JpaRepository<Status, Integer>{

}
