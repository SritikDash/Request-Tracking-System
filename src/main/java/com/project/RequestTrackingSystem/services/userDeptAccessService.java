
package com.project.RequestTrackingSystem.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.RequestTrackingSystem.models.UserDeptAccess;

@Service
public interface userDeptAccessService {
	public void userDeptAccess(List<UserDeptAccess> ude);

}