package projectRfid.projectRfid;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.ektorp.http.HttpClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@EnableAutoConfiguration
@Configuration
@ComponentScan()
public class UserDao {

	List<UserInfo> userInfoList;
	List<UserInfo> userlist;
	UserResp resp = new UserResp();

	public List<UserInfo> getUserDetails(String fname, String lname)
			throws Exception {

		try {
			String textUri = "mongodb://raina:raina@ds053370.mongolab.com:53370/socialservicedb";
			MongoClientURI uri = new MongoClientURI(textUri);
			MongoClient m = new MongoClient(uri);
			MongoOperations mongoOps = new MongoTemplate(m, "socialservicedb");
			userInfoList = mongoOps.find(new Query(Criteria.where("firstName")
					.is(fname)), UserInfo.class, "users");

			if (lname != null) {

				userlist = new ArrayList<UserInfo>();
				for (UserInfo info : userInfoList) {

					if (info.getLastName().equalsIgnoreCase(lname)) {
						userlist.add(info);
					}
				}

				userInfoList = new ArrayList<UserInfo>();
				userInfoList = userlist;
				// resp.setUserItems(userlist);
				// resp.setStatus("success");
				// } else {
				// // return userInfoList;
				// // resp.setUserItems(userInfoList);
				// // resp.setStatus("success");
				// }
			}
		} catch (Exception e) {
			// resp.setStatus("failure");
		}
		return userInfoList;
	}

	public void insertUSerInfo(UserInfo user2) throws Exception {

		boolean status = false;

		try {
			String textUri = "mongodb://raina:raina@ds053370.mongolab.com:53370/socialservicedb";
			MongoClientURI uri = new MongoClientURI(textUri);
			MongoClient m = new MongoClient(uri);
			DB db = m.getDB("socialservicedb");
			DBCollection table = db.getCollection("users");
			BasicDBObject document = new BasicDBObject();

			MongoOperations mongoOps = new MongoTemplate(m, "socialservicedb");

			int id = getNextSequence("users", mongoOps);

			document.put("_id", id);
			document.put("firstName", user2.getFirstName());
			document.put("lastName", user2.getLastName());
			// document.put("address", user2.getAddress());
			document.put("gender", user2.getGender());
			document.put("ssn", user2.getSsn());
			document.put("city", user2.getCity());
			document.put("state", user2.getState());
			document.put("zipCode", user2.getZipCode());
			document.put("email", user2.getEmail());
			document.put("phone", user2.getPhone());

			Integer tagNoInt = new Integer(user2.getTagNo());
			document.put("tagNo", tagNoInt);

			if (user2.getBedId().equalsIgnoreCase("0")) {
				document.put("bedId", 0);
			} else {
				Integer bedIdInt = new Integer(user2.getBedId());
				document.put("bedId", bedIdInt);
			}

			table.insert(document);

			status = true;
		} catch (Exception e) {

			status = false;
		}

		// return status;
	}

	public int getNextSequence(String collectionName, MongoOperations mongoOps) {

		Counter counter = mongoOps.findAndModify(new Query(Criteria
				.where("_id").is(collectionName)), new Update().inc("seq", 1),
				new FindAndModifyOptions().returnNew(true), Counter.class);

		return counter.getSeq();
	}

	public boolean loginUser(UserInfo loginInfo) {

		return false;

	}

	public List<UserInfo> viewAllUsers() throws Exception {

		try {
			String textUri = "mongodb://raina:raina@ds053370.mongolab.com:53370/socialservicedb";
			MongoClientURI uri = new MongoClientURI(textUri);
			MongoClient m = new MongoClient(uri);
			MongoOperations mongoOps = new MongoTemplate(m, "socialservicedb");
			userInfoList = mongoOps.findAll(UserInfo.class);
			// resp.setItems(userInfoList);
			// resp.setStatus("success");
		} catch (Exception e) {
			// resp.setStatus("failure");
		}
		return userInfoList;
	}

	public boolean deleteUser(UserInfo userInfo2) throws UnknownHostException {
		boolean status = false;
		try {
			String textUri = "mongodb://raina:raina@ds053370.mongolab.com:53370/socialservicedb";
			userlist = new ArrayList<UserInfo>();
			userInfoList = new ArrayList<UserInfo>();
			MongoClientURI uri = new MongoClientURI(textUri);
			MongoClient m = new MongoClient(uri);
			MongoOperations mongoOps = new MongoTemplate(m, "socialservicedb");
			userInfoList = mongoOps.find(new Query(Criteria.where("firstName")
					.is(userInfo2.getFirstName())), UserInfo.class, "users");

			if (!userInfoList.isEmpty() && userInfoList != null) {
				if (userInfo2.getLastName() != null) {
					for (UserInfo info : userInfoList) {
						if (info.getLastName().equalsIgnoreCase(
								userInfo2.getLastName())) {
							userlist.add(info);
						}
					}

					if (!userlist.isEmpty() && userlist != null) {
						for (UserInfo userInfo : userlist) {

							if (userInfo.get_id() == userInfo2.get_id()) {

								mongoOps.remove(
										new Query(Criteria.where("firstName")
												.is(userInfo.getFirstName())),
										UserInfo.class, "users");
								if (userInfo.getBedId().equalsIgnoreCase("0")) {
									// bedIdNo = 0;
								} else {
									Integer bedIdNo = new Integer(
											userInfo.getBedId());
									if (bedIdNo != 0) {

										// update status to available

										mongoOps.updateFirst(new Query(Criteria
												.where("_id").is(bedIdNo)),
												Update.update("status",
														"available"),
												BedInfo.class);

										status = true;
										break;

									}
								}
							}
						}

					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public boolean updateUser(UserInfo userInfo2) throws UnknownHostException {
		boolean status = false;

		try {
			String textUri = "mongodb://raina:raina@ds053370.mongolab.com:53370/socialservicedb";

			MongoClientURI uri = new MongoClientURI(textUri);
			MongoClient m = new MongoClient(uri);
			MongoOperations mongoOps = new MongoTemplate(m, "socialservicedb");

			mongoOps.updateFirst(
					new Query(Criteria.where("_id").is(userInfo2.get_id())),
					Update.update("firstName", userInfo2.getFirstName()),
					UserInfo.class);
			mongoOps.updateFirst(
					new Query(Criteria.where("_id").is(userInfo2.get_id())),
					Update.update("lastName", userInfo2.getLastName()),
					UserInfo.class);
			mongoOps.updateFirst(
					new Query(Criteria.where("_id").is(userInfo2.get_id())),
					Update.update("gender", userInfo2.getGender()),
					UserInfo.class);
			// mongoOps.updateFirst(
			// new Query(Criteria.where("_id").is(userInfo2.get_id())),
			// Update.update("address", userInfo2.getAddress()),
			// UserInfo.class);
			mongoOps.updateFirst(
					new Query(Criteria.where("_id").is(userInfo2.get_id())),
					Update.update("city", userInfo2.getCity()), UserInfo.class);
			mongoOps.updateFirst(
					new Query(Criteria.where("_id").is(userInfo2.get_id())),
					Update.update("state", userInfo2.getState()),
					UserInfo.class);
			mongoOps.updateFirst(
					new Query(Criteria.where("_id").is(userInfo2.get_id())),
					Update.update("zipCode", userInfo2.getZipCode()),
					UserInfo.class);
			mongoOps.updateFirst(
					new Query(Criteria.where("_id").is(userInfo2.get_id())),
					Update.update("email", userInfo2.getEmail()),
					UserInfo.class);
			mongoOps.updateFirst(
					new Query(Criteria.where("_id").is(userInfo2.get_id())),
					Update.update("phone", userInfo2.getPhone()),
					UserInfo.class);
			// mongoOps.updateFirst(
			// new Query(Criteria.where("_id").is(userInfo2.get_id())),
			// Update.update("tagNo", userInfo2.getTagNo()),
			// UserInfo.class);
			if (userInfo2.getBedId().equalsIgnoreCase("0")) {
				mongoOps.updateFirst(
						new Query(Criteria.where("_id").is(userInfo2.get_id())),
						Update.update("bedId", 0), UserInfo.class);
			} else {
				Integer bedIdInt = new Integer(userInfo2.getBedId());
				mongoOps.updateFirst(
						new Query(Criteria.where("_id").is(userInfo2.get_id())),
						Update.update("bedId", bedIdInt), UserInfo.class);
				mongoOps.updateFirst(
						new Query(Criteria.where("_id").is(bedIdInt)),
						Update.update("status", "occupied"), BedInfo.class);
			}

			status = true;
		} catch (Exception e) {

			e.printStackTrace();
		}

		return status;
	}
}
