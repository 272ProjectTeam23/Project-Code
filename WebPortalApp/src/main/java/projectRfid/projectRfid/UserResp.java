package projectRfid.projectRfid;

import java.io.Serializable;
import java.util.List;


public class UserResp implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5669410667132310412L;
	//private String startString;
	private List<UserInfo> userItems;
//	private List<BedInfo> bedItems;
	public List<UserInfo> getUserItems() {
		return userItems;
	}



	public void setUserItems(List<UserInfo> userItems) {
		this.userItems = userItems;
	}



	//public List<BedInfo> getBedItems() {
		//return bedItems;
	//}



	//public void setBedItems(List<BedInfo> bedItems) {
		//this.bedItems = bedItems;
//	}



	private int totalPages;
	private int currPage;
	private String status;

	
	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public UserResp(){
		
		
	}
	
	

	public List<UserInfo> getItems() {
		return userItems;
	}
	public void setItems(List<UserInfo> userInfo) {
		this.userItems = userInfo;
	}



	public int getTotalPages() {
		return totalPages;
	}



	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}



	public int getCurrPage() {
		return currPage;
	}



	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	
	
	
	
	
}
