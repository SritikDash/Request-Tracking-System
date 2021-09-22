package com.project.RequestTrackingSystem.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.RequestTrackingSystem.models.RequestsComments;

@Repository
public interface RequestCommentsRepo extends JpaRepository<RequestsComments, Integer> {

}
