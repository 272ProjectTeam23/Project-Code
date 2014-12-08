package projRfid.projRfid;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@Configuration
@ComponentScan()
public class BedInfoService {

	@Autowired
	BedInfo bedInfo;

	@Autowired
	BedDao bedDao;

	boolean status = false;
	List<BedInfo> bedInfoList = new ArrayList<BedInfo>();
	BedResp bedResp = new BedResp();

	public boolean assignBed(int userId, int bedId) {
		try {
			status = bedDao.assignBed(userId, bedId);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteBed(int bedId) {
		// TODO Auto-generated method stub
		try {
			status = bedDao.deleteBed(bedId);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public boolean addBed() {

		try {
			status = bedDao.addBed();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public BedResp viewAllBeds() {
		// TODO Auto-generated method stub
		try {
			bedResp = bedDao.viewAllBeds();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bedResp;
		// return null;
	}

}
