package com.project.RequestTrackingSystem.serviceImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.project.RequestTrackingSystem.models.ChangePassword;

import com.project.RequestTrackingSystem.models.MailResponse;
import com.project.RequestTrackingSystem.models.Requests;
import com.project.RequestTrackingSystem.models.User;
import com.project.RequestTrackingSystem.models.UserDept;
import com.project.RequestTrackingSystem.repos.UserRepo;
import com.project.RequestTrackingSystem.services.UserService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepo userRepo;
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validateEmail(String emailStr) {
	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
	        return matcher.find();
	}
	
	
	
	//BcryptEncoding
	public String bcryptEncoding(String  originalPassword) {
        String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
        System.out.println(generatedSecuredPasswordHash);
		return generatedSecuredPasswordHash;
	}
	
	//BcryptDecoding
	public boolean bcryptDecoding(String originalPassword, String hashedPassword) {
		boolean matched = BCrypt.checkpw(originalPassword, hashedPassword);
		return matched;
	}
	
	
	
	public User validate(User user) {
		User argUser;	
		String msg;
		boolean isEmail = validateEmail(user.getUserEmail());
		
		
		
		if(isEmail) {
			argUser = userRepo.findByUserEmail(user.getUserEmail());
			System.out.println(argUser);
			System.out.println(userRepo.findByUserEmail(user.getUserEmail()));
			
			
			if(argUser == null) {
				msg = "Email doesn't Exist";
			} else if(this.bcryptDecoding(user.getUserPassword(), argUser.getUserPassword())) {
				System.out.println(this.bcryptDecoding(user.getUserPassword(), argUser.getUserPassword()));
				if(argUser.getUserActive())
					msg = "Login Successful";
				else
					msg = "User is currently not active";
			} else {
				msg = "Invalid Email or Password";
			}
			
		}
		
		else {
			user.setUserName(user.getUserEmail());
			user.setUserEmail(null);
			argUser = userRepo.findByUserName(user.getUserName());
			System.out.println(argUser);
			
			System.out.println(userRepo.findByUserName(user.getUserName()));
			if(argUser == null) {
				msg = "UserName doesn't Exist";
			} else if(this.bcryptDecoding(user.getUserPassword(), argUser.getUserPassword())) {
				if(argUser.getUserActive())
					msg = "Login Successful";
				else
					msg = "User is currently not active";
			} else {
				argUser.setUserEmail(user.getUserName());
				msg = "Invalid Email or Password";
				User dummyUser = this.userRepo.findAllDataByJoin(argUser.getUserId());
				System.out.println("DEPTNAME: " + dummyUser.getDeptAccess().get(0).getDepartmentName());
			}
		}
		if(argUser == null) {
			argUser = new User();
		}
		argUser.setMsg(msg);
		return argUser;
		
		
		
	}
	
	
	public User getById(int id) {
		return this.userRepo.getById(id);
	}
	
	
	public String edit(User user) {
		
		String msg;
		
		User checkEmail = this.userRepo.checkDuplicateUserEmail(user.getUserEmail(), user.getUserId());
		User checkUserName = this.userRepo.checkDuplicateUserName(user.getUserName(), user.getUserId());
		if(checkEmail == null && checkUserName == null) {
			this.userRepo.save(user);
			msg = "Saved";
		} else {
			if(checkEmail != null && checkUserName != null) {
				msg = "Email and UserName already Exists!!";
			}
			else if(checkEmail != null) {
				msg = "Email already Exists!!";
			} 
			else if(checkUserName != null) {
				msg = "UserName already exists!!";
			}
			else {
				msg = "";
			}
			
		}
		
		return msg;
	}
	
	
	
	
	public boolean isUserAdmin(int userId) {
		List<Boolean> adminFieldList =  this.userRepo.getAdmin(userId);
		boolean isAdmin = false;
		for(boolean iteratorOverAdminFieldList : adminFieldList) {
			if(iteratorOverAdminFieldList == true) {
				isAdmin = true;
				break;
			}
		}
		return isAdmin;
	}
	
	
	
//	public void save(User user) {
//		userRepo.save(user);
//	}
	
	
	public boolean passwordValidator(String password) {
		if(password.length() >= 8) {
			
			boolean upper = false;
			boolean lower = false;
			boolean numeric = false;
			boolean special = false;
			
			for(int i = 0; i < password.length(); i++) {
				int asciiVal = (int)password.charAt(i);
				if( asciiVal >= 65 && asciiVal <= 90 ) {
					upper = true;
				}
				else if( asciiVal >= 97 && asciiVal <= 122 ) {
					lower = true;
				}
				else if( asciiVal >= 48 && asciiVal <= 57 ) {
					numeric = true;
				}
				else {
					special = true;
				}	
			}
			
			
			if(special && numeric && lower && upper) {
				return true;
			} else {
				return false;
			}
		
		} else {
			return false;
		}
	}
	
	
	
	public ChangePassword verifyPassword(ChangePassword pass) {
		ChangePassword argPass = new ChangePassword();
		String msg;
		argPass.setUserId(pass.getUserId());
		User user = userRepo.findById(pass.getUserId()).get();
		
		if(this.bcryptDecoding(pass.getOldPassword(), user.getUserPassword())) {
			if(pass.getNewPassword().compareTo(pass.getConfirmPassword()) == 0) {
				if(pass.getNewPassword().compareTo(pass.getOldPassword()) != 0) {
					if(this.passwordValidator(pass.getNewPassword())) {
						user.setUserPassword(this.bcryptEncoding(pass.getNewPassword()));
						this.userRepo.save(user);
						msg = "Password Changed";
					} else {
						msg = "Weak Password!! <Password must contain upper case, lower case, numeric and special characters and should be"
								+ "atleast 8 characters long> !!!";
					}
				} else {
					msg = "New Password cannot be same as Old Password!!";
				}
			} else {
				msg = "New Password and Confirm Password didnot match!!";
			}
		} else {
			msg = "Old Password did not match with current password!!";
		}
		argPass.setMsg(msg);
		return argPass;
	}
	
	public ChangePassword changeAnyPassword(ChangePassword pass) {
		ChangePassword argPass = new ChangePassword();
		String msg;
		boolean isEmail = validateEmail(pass.getEmailName());
		User user;
		
		if(isEmail) {
			user = userRepo.findByUserEmail(pass.getEmailName());		
		} else {
			user = userRepo.findByUserName(pass.getEmailName());	
		}
		
		if(user != null) {
			if(pass.getNewPassword().compareTo(pass.getConfirmPassword()) == 0) {
				if(!this.bcryptDecoding(pass.getNewPassword(), user.getUserPassword())) {
					String passwordMsg = this.isPasswordValid(pass.getNewPassword());
					if(passwordMsg.compareTo("Strong") == 0) {
						user.setUserPassword(this.bcryptEncoding(pass.getNewPassword()));
						userRepo.save(user);
						msg = "Password Changed Successfully";
					} else {
						msg = passwordMsg;
					}
				} else {
					msg = "New Password cannot be same as old password";
				}
			} else {
				msg = "New Password and Confirm Password did not match!!";
			}
		} else {
			if(isEmail) {
				msg = "Email does not exist";
			}else {
				msg="Username does not exist";
			}
			
		}
		
		argPass.setMsg(msg);
		return argPass;
	}
	
	public String isPasswordValid(String samplepassword)
    {
        int integercheck=0;
        int capitalcheck=0;
        int smallcheck=0;
        boolean valid=true;
        String msg = "Strong";

        if (!((samplepassword.length() >= 8) && (samplepassword.length() <= 20)) && valid) {
            msg = "Password Length must be of 8 characer long";
            valid=false;

        }

        if (samplepassword.contains(" ") && valid) {
            msg = "Password Must Not Contain a Space";
            valid=false;
        }

        
        if (true) {
            for (int i = 0; i <= 9; i++) {

                String str1 = Integer.toString(i);

                if (samplepassword.contains(str1)) {
                    integercheck = 1;
                }
            }
            if ((integercheck == 0) && valid) {
                msg = "Password Must Contain a Numeric Value";
                valid=false;            }
        }

        if (!(samplepassword.contains("@") || samplepassword.contains("#")
            || samplepassword.contains("!") || samplepassword.contains("$") 
            || samplepassword.contains("*") || samplepassword.contains("&")
            || samplepassword.contains(".")) && valid) {
            msg = "Password Must contain a special Character";
            valid=false;        }

        if (true) {
            for (int i = 65; i <= 90; i++) {

                char c = (char)i;

                String str1 = Character.toString(c);
                if (samplepassword.contains(str1)) {
                    capitalcheck = 1;
                }
            }
            if ((capitalcheck == 0) && valid) {
                msg = "Password Must Contain Capital Letter";
                valid=false;
            }
        }

        if (true) {
            for (int i = 97; i <= 122; i++) {

                char c = (char)i;
                String str1 = Character.toString(c);

                if (samplepassword.contains(str1)) {
                    smallcheck = 1;
                }
            }
            if ((smallcheck == 0) && valid){
                msg = "Password Must Contain a Small Character";
                valid=false;
            }
        }
        
        
        if (valid==true){
//        	If password satisfies all the conditions... <Good Password>
        	msg = "Strong";
            return msg;
        }else{
        	
//        	If password did not satisfy any of the condition
            return msg;
        }
    }
	
	
//====================================================================================================================
	
	
	
	public String forgotPassword(User user) {

		User argUser = this.userRepo.findByUserEmail(user.getUserEmail());
		String msg;

		if (argUser == null) {
			msg = "Email doesn't exist";

		} else {
			msg = "A new password has been sent to your email";
			String password = argUser.getFirstName() + getAlphaNumericString();
			System.out.println(password);
			//this.userRepo.updatePassword(argUser.getUserEmail(), password);
			int stat = this.userRepo.updatePassword(argUser.getUserEmail(), this.bcryptEncoding(password));
			System.out.println(stat);
			try {

				Map<String, Object> model = new HashMap<>();
				model.put("Name", argUser.getFirstName());
				model.put("Password", password);
				model.put("location", "Cozentus, Bhubaneswar");

				this.sendEmail(model, argUser);

			} catch (Exception e) {
				System.out.println(e.toString());

			}
		}

		return msg;

	}

	static String getAlphaNumericString() {

		int n = 6;
		// chose a Character random from this String
		String AlphaNumericString = "@#$%^&*" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
				+ "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private Configuration config;

	public MailResponse sendEmail(Map<String, Object> model, User user) {
		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			Template t = config.getTemplate("reset-template.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
			
			helper.setTo(user.getUserEmail());
			
			helper.setText(html, true);
			helper.setSubject("Reset Password");

			sender.send(message);

			response.setMessage("mail send to : " + user.getFirstName());
			response.setStatus(Boolean.TRUE);


		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : " + e.getMessage());
			response.setStatus(Boolean.FALSE);
		}

		return response;

	}
	
	
	
	public List<UserDept> getAllUsersByDept(int deptId) {
		List<User> argUser = this.userRepo.getAllUsersByDeptId(deptId);

		List<UserDept> ud = new ArrayList<UserDept>();
		for(User userRow : argUser) {
			ud.add(new UserDept(userRow.getUserId(), userRow.getFirstName()));
		}
		return ud;
	}

	
	public Page<User> findPaginated(Pageable pageable) {
    	List<User> user = this.userRepo.findAll();
    	
    	
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        
        
        int startItem = currentPage * pageSize;
        List<User> list;

        if (user.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, user.size());
            list = user.subList(startItem, toIndex);
        }

        Page<User> userPage
          = new PageImpl<User>(list, PageRequest.of(currentPage, pageSize), user.size());

        return userPage;
    }
	
	
	
	//===================================================================================
	
	public String save(User user) {

		String msg;
		User argUser = this.userRepo.findByUserName(user.getUserName());

		if(argUser == null) {
			argUser = this.userRepo.findByUserEmail(user.getUserEmail());
			if(argUser == null) {
				String passwordStrength = this.isPasswordValid(user.getUserPassword());
				if(passwordStrength.compareTo("Strong") == 0) {
					user.setUserPassword(this.bcryptEncoding(user.getUserPassword()));
					userRepo.save(user);
					msg = "Saved";
				} else {
					msg = passwordStrength;
				}
			} else {
				msg = "Email already exists!!";
			}
		} else {
			msg = "UserName already exist!!";
		}

		return msg;


	}
	
	public Page<User> searchByUserField(Pageable pageable, String searchPattern) {
		List<User> user = this.userRepo.searchUser(searchPattern);
    	
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        
        
        int startItem = currentPage * pageSize;
        List<User> list;

        if (user.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, user.size());
            list = user.subList(startItem, toIndex);
        }

        Page<User> userPage
          = new PageImpl<User>(list, PageRequest.of(currentPage, pageSize), user.size());

        return userPage;
	}
	
	
	public List<User> getUsers() {
		List<User> userList =  this.userRepo.findAll();
		
		return userList;
	}
	
}
