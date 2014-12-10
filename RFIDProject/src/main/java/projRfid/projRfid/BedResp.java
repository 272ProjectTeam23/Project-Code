package projRfid.projRfid;

import java.io.Serializable;
import java.util.List;

public class BedResp implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5669410667132310412L;
	//private String startString;
	//private List<UserInfo> userItems;
	private List<BedInfo> bedItems;
	public List<BedInfo> getUserItems() {
		return bedItems;
	}



	public void setBedItems(List<BedInfo> bedItems) {
		this.bedItems = bedItems;
	}



	public List<BedInfo> getBedItems() {
		return bedItems;
	}
	
	public List<BedInfo> getItems() {
		return bedItems;
	}
	public void setItems(List<BedInfo> bedInfo) {
		this.bedItems = bedInfo;
	}

	private int totalPages;
	private int currPage;
	private String status;

	
	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public BedResp(){
		
		
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

