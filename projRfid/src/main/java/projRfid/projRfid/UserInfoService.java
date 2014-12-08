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
public class UserInfoService {

	@Autowired
	UserInfo userInfo;

	@Autowired
	UserDao userDao;

	boolean status = false;
	List<UserInfo> userInfoList = new ArrayList<UserInfo>();
	UserResp userResp = new UserResp();

	public UserResp getUserDetails(String fname, String lname)
			throws Exception {

		userResp = userDao.getUserDetails(fname, lname);

		return userResp;
	}

	

	
	public void insertUserInfo(UserInfo user) throws Exception {

		//status = 
				userDao.insertUSerInfo(user);
		//return status;
	}

	public boolean loginUser(UserInfo loginInfo) {

		status = userDao.loginUser(loginInfo);
		return status;
	}

	public UserResp viewAllUsers() throws Exception {

		userResp = userDao.viewAllUsers();
		return userResp;
	}

	public boolean deleteUser(UserInfo userInfo2) throws UnknownHostException {
		status = userDao.deleteUser(userInfo2);
		return status;
	}

	public boolean updateUser(UserInfo userInfo2) throws UnknownHostException {
		
		status = userDao.updateUser(userInfo2);
		return status;
	}

}
