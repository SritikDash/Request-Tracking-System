package com.project.RequestTrackingSystem.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.RequestTrackingSystem.models.AuditLog;

@Repository
public interface AuditRepo extends JpaRepository<AuditLog, Integer> {
	public List<AuditLog> findAllByChangeTypeIdOrderByCreatedDateDesc(int changeTypeId);
}
