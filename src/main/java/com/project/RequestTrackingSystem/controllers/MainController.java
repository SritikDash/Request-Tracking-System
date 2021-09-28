package com.project.RequestTrackingSystem.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.RequestTrackingSystem.models.AuditLog;
import com.project.RequestTrackingSystem.models.ChangePassword;
import com.project.RequestTrackingSystem.models.Department;
import com.project.RequestTrackingSystem.models.Requests;
import com.project.RequestTrackingSystem.models.RequestsComments;
import com.project.RequestTrackingSystem.models.User;
import com.project.RequestTrackingSystem.models.UserDept;
import com.project.RequestTrackingSystem.models.UserDeptAccess;
import com.project.RequestTrackingSystem.services.DeptService;
import com.project.RequestTrackingSystem.services.RequestService;
import com.project.RequestTrackingSystem.services.UserService;
import com.project.RequestTrackingSystem.services.userDeptAccessService;

import dto.APIResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class MainController {

	@Autowired
	private UserService userSvc;

	@Autowired
	private DeptService deptSvc;

	@Autowired
	private RequestService reqSvc;
	
	@Autowired
	private userDeptAccessService userDeptSvc;

//	INDEX LOGIN
	@GetMapping("/")
	public String serveLogin(Model model, HttpServletRequest request) {
		User user = new User();
		model.addAttribute("user", user);
		
//		try {
//			this.userSvc.bcryptEncoding();
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		return "index";
	}

	@GetMapping("/logout")
	public String logOut(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
//		session.setAttribute("userId", null);
//		session.setAttribute("userName", null);
		try {
			session.invalidate();
		} catch(Exception e) {
			System.out.println(e.toString());
			return "redirect:/";
		}
		
		
		System.out.println(request.getSession(false));
		return "redirect:/";
	}

	@PostMapping("/Login")
	public String login(@ModelAttribute("user") User user, Model model, HttpServletRequest request) {
		User argUser = userSvc.validate(user);
		if (argUser.getMsg().compareTo("Login Successful") == 0) {

			HttpSession session = request.getSession();
			session.setAttribute("userId", argUser.getUserId());
			session.setAttribute("userName", argUser.getFirstName());

			System.out.println(session.getAttribute("userId"));
			System.out.println(request.getSession(false));
			
//			Check whether user is admin or not
			
			
//			session.setAttribute("isAdmin", isAdmin);
			return "redirect:/dashboard";    
			
		} else {
			argUser.setIsInvalid(true);
			// argUser.setMsg(argUser.getMsg());
			model.addAttribute("user", argUser);
			System.out.println(argUser.getMsg() + argUser.getIsInvalid());
			return "index";
		}
	}

	@GetMapping("/dashboard")
	public String getDashboard(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
//		System.out.println("ID" + session.getAttribute("userId"));
		if (session == null) {
			return "redirect:/";
		}
		boolean isAdmin = this.userSvc.isUserAdmin((int) session.getAttribute("userId"));
		if(isAdmin)
			return "dashboard";
		else
			return "redirect:/userdashboard";
//		return "dashboard";
	}
	
	@GetMapping("/userdashboard")
	public String getUserDashboard(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
//		System.out.println("ID" + session.getAttribute("userId"));
		if (session == null) {
			return "redirect:/";
		}
		return "user_dashboard";
	}

	@GetMapping("/ChangePassword")
	public String changePassword(Model model, HttpServletRequest request) {
//		ModelAndView mav = new ModelAndView("change_password");

		HttpSession session = request.getSession(false);
		
		if (session == null) {
			return "redirect:/";
		}
		System.out.println("In Change Password: " + session.getAttribute("userId"));

//		if(session.getAttribute("userId") == null) {
//			System.out.println("Session Failed");
//		}

		ChangePassword password = new ChangePassword();
		password.setUserId((int) session.getAttribute("userId"));

		model.addAttribute("password", password);

		return "change_password";

	}
	
	

	@PostMapping("/UpdatePassword")
	public String updatePassword(@ModelAttribute("password") ChangePassword password, Model model) {

		ChangePassword argPassword = userSvc.verifyPassword(password);
		System.out.println(argPassword.getMsg());
		model.addAttribute("message", argPassword.getMsg());
		argPassword.setVisited(true);
		model.addAttribute("password", argPassword);
		return "change_password";
	}

	@GetMapping("/ChangeAnyPassword")
	public String getChangeAnyPassword(Model model) {
		ChangePassword password = new ChangePassword();
		model.addAttribute("password", password);
		return "ChangeAnyPassword";
	}

	@PostMapping("/UpdateAnyPassword")
	public String updateAnyPassword(@ModelAttribute("password") ChangePassword password, Model model) {

		ChangePassword argPassword = userSvc.changeAnyPassword(password);
		System.out.println(argPassword.getMsg());
		model.addAttribute("message", argPassword.getMsg());
		model.addAttribute("password", argPassword);
		return "ChangeAnyPassword";
	}

	@GetMapping("/Dept")
	public String serveDepartment(Model model, HttpServletRequest request) {

		Department dept = new Department();

		HttpSession session = request.getSession(false);

		if (session == null) {
			return "redirect:/";
		}

		System.out.println("In Dept: " + session.getAttribute("userId"));

		dept.setUserId((int) session.getAttribute("userId"));

//		Get All Dept Codes to display in parent deptCode
		TreeMap<String, String> deptIdsAndCodes = deptSvc.getAllParentDeptId();

		model.addAttribute("deptIds", deptIdsAndCodes);
		model.addAttribute("dept", dept);
		return "Department";
	}

	@GetMapping("/EditDept/{id}")
	public ModelAndView showEditDeptPage(@PathVariable(name = "id") int id, HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		ModelAndView mav = new ModelAndView("EditDepartment");
//		Product product = service.get(id);
		TreeMap<String, String> deptIdsAndCodes = deptSvc.getAllParentDeptId();

		Department dept = this.deptSvc.getByDeptId(id);
		dept.setUserId((int) session.getAttribute("userId"));
		mav.addObject("deptIds", deptIdsAndCodes);
		mav.addObject("dept", dept);

		return mav;
	}

	@PostMapping("/editDept")
	public String editDept(@ModelAttribute("dept") Department dept, Model model, HttpServletRequest request) {
		System.out.println("Edit Dept" + dept.getDeptId());
		
		HttpSession session = request.getSession(false);
		dept.setUserId((int) session.getAttribute("userId"));
		
		
		String msg = deptSvc.edit(dept);
		
		System.out.println(msg);

		TreeMap<String, String> deptIdsAndCodes = deptSvc.getAllParentDeptId();

		model.addAttribute("deptIds", deptIdsAndCodes);
		model.addAttribute("dept", dept);
		model.addAttribute("message", msg);
		return "redirect:/ViewDepts";
	}

	@PostMapping("/saveDept")
	public String saveDept(@ModelAttribute("dept") Department dept, Model model) {
		System.out.println("Save Dept" + dept.getDeptId());
		String msg = deptSvc.save(dept);
		System.out.println(msg);

		TreeMap<String, String> deptIdsAndCodes = deptSvc.getAllParentDeptId();

		model.addAttribute("deptIds", deptIdsAndCodes);
		model.addAttribute("dept", dept);
		model.addAttribute("message", msg);
		return "Department";
	}

	@GetMapping("/CreateRequest")
	public String getCreateRequest(Model model, HttpServletRequest req) {

		HttpSession session = req.getSession(false);

		if (session == null) {
			return "redirect:/";
		}

		Requests request = new Requests();
		
		TreeMap<Integer, String> deptIdsAndCodes = deptSvc.getAllDeptId();

		model.addAttribute("deptIds", deptIdsAndCodes);
		model.addAttribute("request", request);
		return "CreateRequest";
	}

	@PostMapping("/saveRequest")
	public String saveRqst(@ModelAttribute("request") Requests request, Model model, HttpServletRequest req) {
		System.out.println("Save Request");
		HttpSession session = req.getSession(false);
		
		request.setAssignedUser((int) session.getAttribute("userId"));
		
		
		int status = this.reqSvc.saveRequest(request, 0);
		this.reqSvc.saveRequestsComments(request, (int) session.getAttribute("userId"));
		this.reqSvc.saveStatus(request, (int) session.getAttribute("userId"));
		
		if (status == 1) {
			return "redirect:/Homepage";
		} else {
			String msg = "Some Error Occured!! Please try again";
			model.addAttribute("message", msg);
			model.addAttribute("request", request);
			return "CreateRequest";
		}

//	        System.out.println(msg);
//	        model.addAttribute("message", msg);

	}
	
	@PostMapping("/editRequest")
	public String editRqst(@ModelAttribute("request") Requests request, Model model, HttpServletRequest req) {
		System.out.println("Save Request");
		HttpSession session = req.getSession(false);
		
//		request.setAssignedUser((int) session.getAttribute("userId"));
		
		
		int status = this.reqSvc.saveRequest(request, 1);
		this.reqSvc.saveRequestsComments(request, (int) session.getAttribute("userId"));
		this.reqSvc.saveStatus(request, (int) session.getAttribute("userId"));
		
		if (status == 1) {
			return "redirect:/Homepage";
		} else {
			String msg = "Some Error Occured!! Please try again";
			model.addAttribute("message", msg);
			model.addAttribute("request", request);
			return "redirect:/EditRequest/"+request.getRequestId();
		}

//	        System.out.println(msg);
//	        model.addAttribute("message", msg);

	}

//	@GetMapping("/Homepage")
//	public String getHomepage(Model model) {
//
//		List<Requests> allRequests = reqSvc.getAllRequests();
//		model.addAttribute("allRequests", allRequests);
//		long totalRowCount = reqSvc.getTotalRows();
//		model.addAttribute("totalRows", totalRowCount);
//		return "Homepage";
//	}

	@GetMapping("/EditRequest/{id}")
	public ModelAndView showEditRequestPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("EditRequest");
//		Product product = service.get(id);
		TreeMap<Integer, String> deptIdsAndCodes = deptSvc.getAllDeptId();

		Requests request = this.reqSvc.getRequestByID(id);
		
		int assignedUserId = request.getAssignedTo().getUserId();
		String assignedUserFirstName = request.getAssignedTo().getFirstName();
		
		
		List<AuditLog> requestHistoryLogs = this.reqSvc.requestHistory(id);
		
		
		mav.addObject("assignedUserId", assignedUserId);
		mav.addObject("assignedUserFirstName", assignedUserFirstName);
		mav.addObject("deptIds", deptIdsAndCodes);
		mav.addObject("request", request);
		mav.addObject("requestHistoryLogs", requestHistoryLogs);

		return mav;
	}

//    @GetMapping("/{field}")
//    private String getRequestsWithSort(@PathVariable String field, Model model) {
//        List<Requests> allRequests = reqSvc.findRequestsWithSorting(field);
//        model.addAttribute("allRequests", allRequests);
//        return "Homepage";
//    }

//	=============================Original Code======================================
//	=============================Try with dropdown==================================
//    @GetMapping("/Homepage/{offset}/{pageSize}")
//    private String getRequestsWithPagination(@PathVariable int offset, @PathVariable int pageSize, Model model) {
//        Page<Requests> allRequests = reqSvc.findRequestsWithPagination(offset, pageSize);
//        model.addAttribute("allRequests", allRequests);
//        return "Homepage";
//    }

//	@GetMapping("/Homepage/{offset}")
//	private String getRequestsWithPagination(@PathVariable int offset, Model model) {
//		Page<Requests> allRequests = reqSvc.findRequestsWithPagination(offset - 1, 5);
//		model.addAttribute("allRequests", allRequests);
//		return "Homepage";
//	}

//    @GetMapping("/Homepage/{offset}/{pageSize}/{field}")
//    private String getRequestsWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String field, Model model) {
//        Page<Requests> allRequests = reqSvc.findRequestsWithPaginationAndSorting(offset, pageSize, field);
//        model.addAttribute("allRequests", allRequests);
//        return "Homepage";
//    }

	@GetMapping(value = "/Homepage")
	public String listRequests(HttpServletRequest request, Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		HttpSession session = request.getSession(false);

		if (session == null) {
			return "redirect:/";
		}

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(8);

		Page<Requests> reqPage = this.reqSvc.findPaginated(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("reqPage", reqPage);

		int totalPages = reqPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		
		String str = "Homepage";
		model.addAttribute("Page", str);

		return "Homepage";
	}

	@GetMapping("/dtoGetReq")
	private String getAllRequests(Model model) {
		List<Requests> requests = this.reqSvc.getReqs();
//	        return new APIResponse<>(requests.size(), requests);
		APIResponse<Object> api = new APIResponse<Object>(requests.size(), requests);
		System.out.println(api.getRecordCount());

		model.addAttribute("req", api.getResponse());

		return "DTO";
	}

	// ========================================================================================

	@GetMapping("/reset")
	public String serveResetPassword(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "resetPassword";
	}

	@PostMapping("/reset")
	public String resetPassword(@ModelAttribute("user") User user, Model model) {

		String msg = this.userSvc.forgotPassword(user);
		model.addAttribute("user", user);
		model.addAttribute("message",msg);
		return "resetPassword";
	}
	
	
	@GetMapping("/ManageUser")
	public String manageUser(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);

		if (session == null) {
			return "redirect:/";
		}
		
		User user = new User();
		model.addAttribute("user", user);
		return "ManageUser";
	}
	
//	@PostMapping("/saveUser")
//	public String saveUser(Model model, @ModelAttribute("user") User user) {
//		System.out.println("User Saved");
//		this.userSvc.save(user);
//		return "redirect:/ViewUsers";
//	}
//	
	
	
	@PostMapping("/saveUser")
	public String saveUser(Model model, @ModelAttribute("user") User user) {
		System.out.println("User Saved");
		String msg = this.userSvc.save(user);
		if(msg.compareTo("Saved") == 0) {
			return "redirect:/ViewUsers";
		} else {
			model.addAttribute("user", user);
			model.addAttribute("msg", msg);
			return "ManageUser";
		}

	}
	
	@PostMapping("/saveEditUser")
	public String saveEditUser(Model model, @ModelAttribute("user") User user, RedirectAttributes redirAttrs) {
		System.out.println("User Saved");
		String msg = this.userSvc.edit(user);
		if(msg.compareTo("Saved") == 0) {
			return "redirect:/ViewUsers";
		} else {
			model.addAttribute("user", user);
			model.addAttribute("msg", msg);
			redirAttrs.addFlashAttribute("error", msg);
			return "redirect:/EditUser/"+user.getUserId();
		}

	}
	
	
	@GetMapping("/EditUser/{id}")
	public ModelAndView editUser(Model model, @PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("EditUser");
		
		User user = this.userSvc.getById(id);
		mav.addObject("user", user);
		
		return mav;
		
	}
	
	@GetMapping("/ViewUsers")
	public String listUsers(HttpServletRequest request, Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		HttpSession session = request.getSession(false);

		if (session == null) {
			return "redirect:/";
		}

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(8);

		Page<User> userPage = this.userSvc.findPaginated(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("userPage", userPage);

		int totalPages = userPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		return "ViewUsers";
	}
	
	
	@GetMapping("/ViewDepts")
	public String listDepts(HttpServletRequest request, Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		HttpSession session = request.getSession(false);

		if (session == null) {
			return "redirect:/";
		}

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(8);

		Page<Department> deptPage = this.deptSvc.findPaginated(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("deptPage", deptPage);

		int totalPages = deptPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		return "ViewDepts";
	}
	
	
	
	@GetMapping("/getAllUsersByDept")
	public ResponseEntity<List<UserDept>> getAllUsersByDept(@RequestParam(name = "deptId") int deptId) {
		return new ResponseEntity<List<UserDept>>(this.userSvc.getAllUsersByDept(deptId) ,HttpStatus.OK);
	}
	
	
	
	@GetMapping("/MyRequests")
	public String getMyRequests(Model model, HttpServletRequest request, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		
		HttpSession session = request.getSession(false);

		if (session == null) {
			return "redirect:/";
		}
		
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(8);

		int userId = (int) session.getAttribute("userId");
		
		Page<Requests> reqPage = this.reqSvc.findPaginatedByUserId(PageRequest.of(currentPage - 1, pageSize), userId, 0);

		model.addAttribute("reqPage", reqPage);

		int totalPages = reqPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		
		
//		List<Requests> reqPage = this.reqSvc.findPaginatedByUserId(userId);
		
		
		model.addAttribute("Page", "MyRequests");
		
		
		
		return "Homepage";
		
	}
	
	@GetMapping("/MyTasks")
	public String getMyTasks(Model model, HttpServletRequest request, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		
		HttpSession session = request.getSession(false);

		if (session == null) {
			return "redirect:/";
		}
		
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(8);

		int userId = (int) session.getAttribute("userId");
		
		Page<Requests> reqPage = this.reqSvc.findPaginatedByUserId(PageRequest.of(currentPage - 1, pageSize), userId, 1);

		model.addAttribute("reqPage", reqPage);

		int totalPages = reqPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		
		
//		List<Requests> reqPage = this.reqSvc.findPaginatedByUserId(userId);
		
		model.addAttribute("Page", "MyTasks");
		
		
		
		
		return "Homepage";
		
	}
	
	
	
	//============================================================================================
	
	
	@GetMapping("/requestComments/{id}")
	public ModelAndView requestComments(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("requestComments");
//		ModelAndView mavv = new ModelAndView("status");
//		Product product = service.get(id);
		//TreeMap<Integer, String> deptIdsAndCodes = deptSvc.getAllDeptId();

		Requests request = this.reqSvc.getRequestByID(id);
		
		//mav.addObject("deptIds", deptIdsAndCodes);
		mav.addObject("request", request);
		mav.addObject("requestComment", new RequestsComments());

		return mav;
	}
	
	@PostMapping("/saveComments")
	public String requestComments(@ModelAttribute("request") Requests request, Model model, HttpServletRequest req) {

		
//		model.addAttribute("requests", request);
		//model.addAttribute("message",msg);

		
		System.out.println("Save Comments");
		HttpSession session = req.getSession(false);
		
		//request.setAssignedUser((int) session.getAttribute("userId"));
		
		int status = this.reqSvc.saveRequestsComments(request, (int) session.getAttribute("userId"));
		this.reqSvc.saveStatus(request, (int) session.getAttribute("userId"));
		
		//int status = this.reqSvc.requestComments(request);
		System.out.println(status);
		
		if (status == 1) {
			return "redirect:/Homepage";                 
		} else {
			String msg = "Some Error Occured!! Please try again";
			model.addAttribute("message", msg);
			model.addAttribute("request", request);
			return "redirect:/requestComments/"+request.getRequestId();
		}
	}
	
	
	
	
	@GetMapping("/deptAccess/{id}")
	public String showDeptAccessForm(Model model, @PathVariable(name = "id") int id) {
	List<Department> listDepts = this.deptSvc.getAllDepts();
	model.addAttribute("listDepts",listDepts);
	model.addAttribute("id", id);
	// model.addAttribute("userDeptAccess",new UserDeptAccess());
	// model.addAttribute("userDeptObj", new UserDeptAccess());
	// model.addAttribute("userDeptObj", new ArrayList<UserDeptAccess>());


	return "UserDeptAccess";
	}
	
	/* ======== Save list of Department user role ======== */
	@PostMapping("/saveuserrole")
	public String SaveUserDeptRole(@RequestBody List<UserDeptAccess> userDeptAccess) {

		System.out.println("Inside User_dept_access");
		System.out.println(userDeptAccess);


		List<UserDeptAccess> listOfValidUsers = new ArrayList<UserDeptAccess>();




		for(UserDeptAccess uda : userDeptAccess) {
			// System.out.println(uda.getRole());
			// System.out.println(uda.getDeptId());
			// System.out.println(uda.getUserId());

			if(uda.getRole().compareTo("nouser") == 0) {
				continue;
			}

			if(uda.getRole().compareTo("user") == 0) {
				uda.setUser(true);
				uda.setAdmin(false);
				listOfValidUsers.add(uda);
			}

			if(uda.getRole().compareTo("admin") == 0) {
				uda.setUser(false);
				uda.setAdmin(true);
				listOfValidUsers.add(uda);
			}
		}

		if (listOfValidUsers != null) {
			this.userDeptSvc.userDeptAccess(listOfValidUsers);
		}

		System.out.println("Data Saved");
		return "redirect:/ViewUsers";
	}
	
	
	
	// SEARCHING Request
	@GetMapping("/search")
	public String searchByRequestNumber(Model model, @RequestParam("search") String searchValue, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size ) {
		
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(8);

		
		
//		Page<Requests> reqPage = this.reqSvc.findPaginatedByUserId(PageRequest.of(currentPage - 1, pageSize), userId, 1);
		Page<Requests> reqPage = this.reqSvc.searchByRequestNumberOrTitle(PageRequest.of(currentPage - 1, pageSize), searchValue);
		
		
		model.addAttribute("reqPage", reqPage);

		int totalPages = reqPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		
		
//		List<Requests> reqPage = this.reqSvc.findPaginatedByUserId(userId);
		
		model.addAttribute("Page", "search");
		
		
		
		
		return "Homepage";
		
		
	}
	
	
	// SEARCHING User
		@GetMapping("/searchUser")
		public String searchByUserName(Model model, @RequestParam("search") String searchValue, @RequestParam("page") Optional<Integer> page,
				@RequestParam("size") Optional<Integer> size ) {
			
			int currentPage = page.orElse(1);
			int pageSize = size.orElse(8);

			Page<User> userPage = this.userSvc.searchByUserField(PageRequest.of(currentPage - 1, pageSize), searchValue);

			model.addAttribute("userPage", userPage);

			int totalPages = userPage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}

			return "ViewUsers";
			
			
		}
		
	// SEARCHING Department
	@GetMapping("/searchDept")
	public String searchDepartment(Model model, @RequestParam("search") String searchValue, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(8);

		Page<Department> deptPage = this.deptSvc.searchByDeptField(PageRequest.of(currentPage - 1, pageSize), searchValue);

		model.addAttribute("deptPage", deptPage);

		int totalPages = deptPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		return "ViewDepts";
	}
	
	
	
	
	
	
}
	

