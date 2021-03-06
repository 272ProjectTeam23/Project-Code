package projRfid.projRfid;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

	@Autowired
	UserInfo userInfo;

	@Autowired
	BedInfo bedInfo;

	@Autowired
	UserInfoService userService;

	@Autowired
	BedInfoService bedInfoService;

	List<UserInfo> userInfoList;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView indexPage() {

		UserInfo info = new UserInfo();
		ModelAndView model = new ModelAndView();
		model.addObject("info", info);
		model.setViewName("index");
		return model;
	}

	// Sample Page
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView loginUser() {

		UserInfo info = new UserInfo();
		ModelAndView model = new ModelAndView();
		model.addObject("info", info);
		model.setViewName("index");
		return model;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {

		UserInfo info = new UserInfo();
		ModelAndView model = new ModelAndView();
		model.addObject("info", info);
		model.setViewName("showAllUsers");

		return model;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update() {

		UserInfo info = new UserInfo();
		ModelAndView model = new ModelAndView();
		model.addObject("info", info);
		model.setViewName("updateUser");

		return model;
	}

	// search based on first name and last name
	@RequestMapping(value = "/userDetails/{fname}/{lname}", method = RequestMethod.GET)
	@ResponseBody
	public List<UserInfo> getUserDetails(@PathVariable String fname,
			@PathVariable String lname) throws Exception {

		// UserResp reponseStr = new UserResp();
		// reponseStr = userService.getUserDetails(fname, lname);

		userInfoList = new ArrayList<UserInfo>();
		userInfoList = userService.getUserDetails(fname, lname);

		return userInfoList;

	}

	@RequestMapping(value = "/viewAllUsers", method = RequestMethod.GET)
	@ResponseBody
	public List<UserInfo> viewAllUsers() throws Exception {
//		UserResp reponseStr = new UserResp();
//		reponseStr = userService.viewAllUsers();
//		reponseStr.setTotalPages(40);
//		reponseStr.setCurrPage(4);
		
		userInfoList = new ArrayList<UserInfo>();
		userInfoList = userService.viewAllUsers();
		
		return userInfoList;

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView registerUSer(
			@Valid @ModelAttribute("info") UserInfo userInfo) throws Exception {

		userService.insertUserInfo(userInfo);
		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:index.html");
		return model;

	}

	// del user
	@RequestMapping(value = "/deleteUser/{fname}/{lname}/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void deleteUser(@PathVariable String fname,
			@PathVariable String lname, @PathVariable int id) throws Exception {

		userInfo.setFirstName(fname);
		userInfo.setLastName(lname);
		userInfo.set_id(id);

		userService.deleteUser(userInfo);

	}

	@RequestMapping(value = "/updateUserInfo/{id}/{fname}/{lname}/{gender}/{ssn}/{city}/{state}/{zipCode}/{phoneno}/{email}/{tagNo}/{bedId}", method = RequestMethod.GET)
	@ResponseBody
	public void updateUserInfo(@PathVariable int id,
			@PathVariable String fname, @PathVariable String lname,
			@PathVariable String gender, @PathVariable String ssn,
			@PathVariable String city, @PathVariable String state,
			@PathVariable String zipCode, @PathVariable long phoneno,
			@PathVariable String email, @PathVariable int tagNo,
			@PathVariable int bedId) throws UnknownHostException {

		userInfo.set_id(id);
		userInfo.setFirstName(fname);
		userInfo.setLastName(lname);
		userInfo.setGender(gender);
		userInfo.setSsn(ssn);
		userInfo.setCity(city);
		userInfo.setState(state);
		userInfo.setZipCode(zipCode);
		userInfo.setPhone(phoneno);
		userInfo.setEmail(email);
		userInfo.setTagNo(tagNo);
		userInfo.setBedId(bedId);

		userService.updateUser(userInfo);

	}

	// **********************************
}
