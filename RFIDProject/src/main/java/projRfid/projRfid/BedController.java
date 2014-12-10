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
public class BedController {

	@Autowired
	BedInfo bedInfo;

	@Autowired
	BedInfoService bedInfoService;

	List<BedInfo> bedInfoList;
//
//	@RequestMapping(value = "/addBed", method = RequestMethod.GET)
//	@ResponseBody
//	public ModelAndView addBedInfo() {
//
//		ModelAndView model = new ModelAndView();
//
//		model.setViewName("AddBed");
//		return model;
//	}

	@RequestMapping(value = "/deleteBeds", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView showBeds() {

		ModelAndView model = new ModelAndView();
		model.setViewName("showAllBeds");
		return model;
	}

	
	@RequestMapping(value = "/viewBeds", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView viewBeds() {

		ModelAndView model = new ModelAndView();

		model.setViewName("showAllBeds");
		return model;
	}

	// Add a bed

	@RequestMapping(value = "/addBed", method = RequestMethod.POST)
	@ResponseBody
	public void addBed() {

		boolean status = false;
		//status = 
				bedInfoService.addBed();

		//return status;
	}

	// del a bed

	@RequestMapping(value = "/deleteBed/{bedId}", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteBed(@PathVariable int bedId) {

		boolean status = false;
		status = bedInfoService.deleteBed(bedId);

		return status;
	}

	// View all Beds
	@RequestMapping(value = "/viewAllBeds", method = RequestMethod.GET)
	@ResponseBody
	public List<BedInfo> bedInfo() {

		bedInfoList = new ArrayList<BedInfo>();
		
		bedInfoList = bedInfoService.viewAllBeds();
		
		return bedInfoList;
	}

	// Assign a bed

	@RequestMapping(value = "/assignBed/{userId}/{bedId}", method = RequestMethod.GET)
	@ResponseBody
	public boolean assignBed(@PathVariable int userId, @PathVariable int bedId) {

		boolean status = false;
		status = bedInfoService.assignBed(userId, bedId);

		return status;
	}

}
