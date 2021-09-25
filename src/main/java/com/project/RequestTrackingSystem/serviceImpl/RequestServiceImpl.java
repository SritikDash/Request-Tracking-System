package com.project.RequestTrackingSystem.serviceImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.project.RequestTrackingSystem.models.AuditLog;
import com.project.RequestTrackingSystem.models.MailResponse;
import com.project.RequestTrackingSystem.models.Requests;
import com.project.RequestTrackingSystem.models.RequestsComments;
import com.project.RequestTrackingSystem.models.SequenceCounter;
import com.project.RequestTrackingSystem.models.Status;
import com.project.RequestTrackingSystem.repos.AuditRepo;
import com.project.RequestTrackingSystem.repos.DeptRepo;
import com.project.RequestTrackingSystem.repos.RequestCommentsRepo;
import com.project.RequestTrackingSystem.repos.RequestRepo;
import com.project.RequestTrackingSystem.repos.SeqCounterRepo;
import com.project.RequestTrackingSystem.repos.StatusRepo;
import com.project.RequestTrackingSystem.repos.UserRepo;
import com.project.RequestTrackingSystem.services.RequestService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;



@Service
public class RequestServiceImpl implements RequestService {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	AuditRepo auditRepo;
	
	@Autowired
	RequestRepo reqRepo;

	@Autowired
	DeptRepo deptRepo;

	@Autowired
	SeqCounterRepo seqRepo;
	
	@Autowired
	RequestCommentsRepo reqComRepo;
	
	@Autowired
	StatusRepo statRepo;

	
	
	
//	flag = 0 SAVE
//	flag = 1 EDIT
	public int saveRequest(Requests req, int flag) {
		int status;

		System.out.println(req);
		System.out.print(req.getRequestTitle() + req.getRequestDept() + req.getRequestNumber());
		
		
		
		

		// deptRepo.findById(req.getRequestDept()).get().getDeptCode();

//		req.setRequestNumber(this.generateReqNumber(
//				deptRepo.findById(req.getRequestDept().getDeptId()).get().getDeptCode(), Requests.getSeqNum()));

		if( flag == 0) {
			req.setCreatedBy(this.userRepo.getById(req.getAssignedUser()));
			int seqCounter = seqRepo.getSeqNumber();
			req.setRequestNumber(this.buildReqNumber(deptRepo.findById(req.getRequestDept().getDeptId()).get().getDeptCode(), seqCounter));
		}
		
		if(flag == 1) {
			req.setCreatedBy(this.reqRepo.getById(req.getRequestId()).getCreatedBy());
			req.setRequestNumber(this.buildReqNumber(deptRepo.findById(req.getRequestDept().getDeptId()).get().getDeptCode(), Integer.parseInt(req.getRequestNumber().substring(4, req.getRequestNumber().length()))));
		}
		
		try {

			
			reqRepo.save(req);
			
			if(flag == 0) {
				seqRepo.save(new SequenceCounter());
			}
			Map<String, Object> modelAssignedTo = new HashMap<>();
			modelAssignedTo.put("Number", req.getRequestNumber());
			modelAssignedTo.put("Title", req.getRequestTitle());
			modelAssignedTo.put("Description", req.getRequestDescription());
			modelAssignedTo.put("location", "Cozentus, Bhubaneswar");
    		
			this.sendEmail(modelAssignedTo, req.getAssignedTo().getUserEmail());
			
			Map<String, Object> modelCreatedBy = new HashMap<>();
			modelCreatedBy.put("Number", req.getRequestNumber());
			modelCreatedBy.put("Title", req.getRequestTitle());
			modelCreatedBy.put("Description", "Your Request Has been assigned!!");
			modelCreatedBy.put("location", "Cozentus, Bhubaneswar");
    		
    		this.sendEmail(modelCreatedBy, req.getCreatedBy().getUserEmail());

			status = 1;
		} catch (Exception e) {
			System.out.println(e.toString());
			status = 0;
		}

		return status;
	}

//	public String generateReqNumber(String requestDept, String seqNum) {
//		String requestNumber = "";
//
//		System.out.println("Requests Constructor");
//		if (seqNum.compareTo("100000") == 0) {
//			seqNum = "00001";
//		}
//
//		requestNumber = requestDept + seqNum;
//		System.out.println("REQ:" + requestNumber);
//
//		int index;
//		for (index = 0; index < seqNum.length(); index++) {
//			if (seqNum.charAt(index) != '0') {
//				System.out.println(index);
//				break;
//			}
//		}
//
//		String str = seqNum;
//		String substr = str.substring(index, seqNum.length());
//		String substr1 = str.substring(0, index);
//		System.out.println("range :" + substr1);
//		System.out.println("number = " + substr);
//		int val = Integer.parseInt(substr);
//		val++;
//		substr = Integer.toString(val);
//
//		seqNum = substr1 + substr;
//
//		System.out.println("SEQ:" + seqNum);
//		Requests.setSeqNum(seqNum);
//		return requestNumber;
//
//	}

	public String buildReqNumber(String deptCode,int seq) {
		DecimalFormat df = new DecimalFormat("00000");

		String reqNumber = df.format(seq);
		System.out.println("REQ NUMBER:"+reqNumber);
		return deptCode+reqNumber;
	}

	public List<Requests> getAllRequests() {
		List<Requests> allRequests = this.reqRepo.findAllByCreatedDateDesc();
		for(Requests argReq : allRequests) {
//			System.out.println(this.reqRepo.getAge(argReq.getRequestId()));
			argReq.setRequestAge(this.reqRepo.getAge(argReq.getRequestId()));
		}
		return allRequests;
	}
	
	
	public List<Requests> getReqs() {
		List<Requests> reqList =  this.reqRepo.findAll();
		for(Requests argReq : reqList) {
			System.out.println(this.reqRepo.getAge(argReq.getRequestId()));
			argReq.setRequestAge(this.reqRepo.getAge(argReq.getRequestId()));
		}
		return reqList;
	}

	public Requests getRequestByID(int id) {
		return this.reqRepo.getById(id);
	}
	
	public long getTotalRows() {
		return this.reqRepo.count();
	}
	
	
	
//	public List<Requests> findRequestsWithSorting(String field) {
//        return  reqRepo.findAll(Sort.by(Sort.Direction.ASC,field));
//    }


    public Page<Requests> findRequestsWithPagination(int offset, int pageSize) {
        Page<Requests> request = reqRepo.findAll(PageRequest.of(offset, pageSize).withSort(Direction.DESC, "createdDate"));
        return  request;
    }
    
    
    
    
    
    

//    public Page<Requests> findRequestsWithPaginationAndSorting(int offset,int pageSize,String field) {
//        Page<Requests> request = reqRepo.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
//        return  request;
//    }
    
    
    
    
    
  //==================================================================================================
    
    
    @Autowired
	private JavaMailSender sender;
	
	@Autowired
	private Configuration config;
	


	//@Override
	public MailResponse sendEmail(Map<String, Object> model, String email) {
		// TODO Auto-generated method stub
		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			
			

			Template t = config.getTemplate("email-template.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

			helper.setTo(email);
			System.out.println(email);
			helper.setText(html, true);
			helper.setSubject("New Request");
			//helper.setFrom(request.getFrom());
			sender.send(message);

			response.setMessage("mail sent");
			response.setStatus(Boolean.TRUE);

		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : "+e.getMessage());
			response.setStatus(Boolean.FALSE);
		}
		System.out.println(response.getMessage());

		return response;
	
		
	}
    
	public List<Requests> getMyRequests(int userId) {
		List<Requests> myRequests =  this.reqRepo.findAllByCreatedByOrderByAssignedDateDesc(userRepo.getById(userId));
		for(Requests argReq : myRequests) {
//			System.out.println(this.reqRepo.getAge(argReq.getRequestId()));
			argReq.setRequestAge(this.reqRepo.getAge(argReq.getRequestId()));
		}
		return myRequests;
	}
	
	public List<Requests> getMyTasks(int userId) {
		List<Requests> myTasks = this.reqRepo.findAllByAssignedToOrderByAssignedDateDesc(userRepo.getById(userId));
		for(Requests argReq : myTasks) {
//			System.out.println(this.reqRepo.getAge(argReq.getRequestId()));
			argReq.setRequestAge(this.reqRepo.getAge(argReq.getRequestId()));
		}
		return myTasks;
	}
	
	
	public Page<Requests> findPaginated(Pageable pageable) {
    	List<Requests> req = this.getAllRequests();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Requests> list;

        if (req.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, req.size());
            list = req.subList(startItem, toIndex);
        }

        Page<Requests> reqPage
          = new PageImpl<Requests>(list, PageRequest.of(currentPage, pageSize), req.size());

        return reqPage;
    }
	
	
	
	
//	
//	Stats = 0 ===============>>>>>>>>>>>>  GetMyRequests
//	Stats = AnyOther ===============>>>>>>>>>>>>  GetMyTasks
//	
	public Page<Requests> findPaginatedByUserId(Pageable pageable, int userId, int stats) {
    	
		List<Requests> req;
		
		
		if(stats == 0) {
			req = this.getMyRequests(userId);
		} else {
			req = this.getMyTasks(userId);
		}
		
		
		
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Requests> list;

        if (req.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, req.size());
            list = req.subList(startItem, toIndex);
        }

        Page<Requests> reqPage
          = new PageImpl<Requests>(list, PageRequest.of(currentPage, pageSize), req.size());

        return reqPage;
    }
    
    
    
    
//=================================================================================-===========
	
	
    
    
    
	public int saveRequestsComments(Requests request, int userId) {
    	
    	int status;
    	//Requests argReq = this.reqRepo.getById(request.getRequestId());
    	RequestsComments argReqCom = new RequestsComments();
    	
    	argReqCom.setCreatedBy(userId);
    	argReqCom.setCreatedDate(new Date());
    	argReqCom.setRequestComment(request.getInitialComments());
    	argReqCom.setRequestId(request.getRequestId());
    	
    	
    	String reqSequenceNo = request.getRequestNumber();
    	
    	argReqCom.setRequestSequenceNo(Integer.parseInt(reqSequenceNo.substring(4)));
    	this.reqComRepo.save(argReqCom);
    	
    	status = 1;
    	
    	return status;
    }
    
    public int saveStatus(Requests request, int userId) {
    	
    	int status;
    	
    	Status argStat = new Status();
    	
    	argStat.setCreatedBy(userId);
    	argStat.setCreatedDate(new Date());
    	argStat.setStatusCode(request.getInitialStatus().substring(0,1));
    	argStat.setRequestId(request.getRequestId());
    	argStat.setStatusDesc(request.getInitialStatus());
    	//argStat.setRequestCommentId(requestComment);
    	this.statRepo.save(argStat);
    	
    	if(request.getInitialStatus().compareTo("Completed") == 0) {
    		Map<String, Object> model = new HashMap<>();
    		model.put("Number", request.getRequestNumber());
    		model.put("Title", request.getRequestTitle());
    		model.put("Description", request.getInitialStatus());
    		model.put("location", "Cozentus, Bhubaneswar");
    		
//			this.sendEmail(model, this.userRepo.getById(request.getCreatedBy().getUserId()).getUserEmail());
			this.sendEmail(model, request.getCreatedBy().getUserEmail());
    	}
    	
    	return 1;
   
    }

	@Override
	public List<AuditLog> requestHistory(int reqId) {
		// TODO Auto-generated method stub
		
		List<AuditLog> requestHistoryLogs = this.auditRepo.findAllByChangeTypeIdOrderByCreatedDateDesc(reqId);
		
		return requestHistoryLogs;
	}

	


    
}
