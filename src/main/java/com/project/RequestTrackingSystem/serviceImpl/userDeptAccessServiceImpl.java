
package com.project.RequestTrackingSystem.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.RequestTrackingSystem.models.UserDeptAccess;
import com.project.RequestTrackingSystem.repos.UserDeptAccessRepo;
import com.project.RequestTrackingSystem.services.userDeptAccessService;

@Service
public class userDeptAccessServiceImpl implements userDeptAccessService {
	@Autowired
	UserDeptAccessRepo userDeptRepo;

	public void userDeptAccess(List<UserDeptAccess> ude) {
		
		int userId = ude.get(0).getUserId();
		
		this.userDeptRepo.deleteFromUserDeptAccess(userId);
		
		for (UserDeptAccess u1 : ude) {
			this.userDeptRepo.save(u1);
		}
	}

}