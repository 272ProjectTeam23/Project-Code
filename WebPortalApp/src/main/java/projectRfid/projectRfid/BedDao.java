package projectRfid.projectRfid;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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
public class BedDao {

	List<BedInfo> bedInfoList = new ArrayList<BedInfo>();
	List<BedInfo> bedlist = new ArrayList<BedInfo>();
	BedResp bresp = new BedResp();

	boolean status = false;

	public void addBed() throws Exception {

		try {
			String textUri = "mongodb://raina:raina@ds053370.mongolab.com:53370/socialservicedb";
			MongoClientURI uri = new MongoClientURI(textUri);
			MongoClient m = new MongoClient(uri);
			DB db = m.getDB("socialservicedb");
			DBCollection table = db.getCollection("beds");
			BasicDBObject document = new BasicDBObject();
			// get all bed Ids and then insert

			MongoOperations mongoOps = new MongoTemplate(m, "socialservicedb");

			int id = getNextSequence("beds", mongoOps);

			document.put("_id", id);
			document.put("status", "available");

			table.insert(document);
			status = true;
		} catch (Exception e) {

			status = false;
		}

		//return false;
	}

	public int getNextSequence(String collectionName, MongoOperations mongoOps) {

		Counter counter = mongoOps.findAndModify(new Query(Criteria
				.where("_id").is(collectionName)), new Update().inc("seq", 1),
				new FindAndModifyOptions().returnNew(true), Counter.class);

		return counter.getSeq();
	}

	public List<BedInfo> viewAllBeds() throws Exception {

		try {
			String textUri = "mongodb://raina:raina@ds053370.mongolab.com:53370/socialservicedb";
			MongoClientURI uri = new MongoClientURI(textUri);
			MongoClient m = new MongoClient(uri);
			MongoOperations mongoOps = new MongoTemplate(m, "socialservicedb");
			bedInfoList = mongoOps.findAll(BedInfo.class);
//			bresp.setItems(bedInfoList);
//			bresp.setStatus("success");
		} catch (Exception e) {
			//bresp.setStatus("failure");
		}
		return bedInfoList;
	}

	public boolean deleteBed(int bedId) throws UnknownHostException {
		boolean status = false;
		try {
			String textUri = "mongodb://raina:raina@ds053370.mongolab.com:53370/socialservicedb";

			MongoClientURI uri = new MongoClientURI(textUri);
			MongoClient m = new MongoClient(uri);
			MongoOperations mongoOps = new MongoTemplate(m, "socialservicedb");

			mongoOps.remove(new Query(Criteria.where("_id").is(bedId)),
					BedInfo.class, "beds");
			status = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

//	public boolean updateBed(BedInfo bedInfo2) throws UnknownHostException {
//		boolean status = false;
//
//		try {
//			String textUri = "mongodb://raina:raina@ds053370.mongolab.com:53370/socialservicedb";
//
//			MongoClientURI uri = new MongoClientURI(textUri);
//			MongoClient m = new MongoClient(uri);
//			MongoOperations mongoOps = new MongoTemplate(m, "socialservicedb");
//
//			mongoOps.updateFirst(
//					new Query(Criteria.where("_id").is(bedInfo2.get_id())),
//					Update.update("status", bedInfo2.getStatus()),
//					BedInfo.class);
//
//			// mongoOps.updateFirst(
//			// new Query(Criteria.where("_id").is(bedInfo2.get_id())),
//			// Update.update("bedId", bedInfo2.get_id()),
//			// UserInfo.class);
//
//			status = true;
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//
//		return status;
//	}

	public boolean assignBed(int userId, int bedId) throws UnknownHostException {

		try {
			boolean status = false;
			String textUri = "mongodb://raina:raina@ds053370.mongolab.com:53370/socialservicedb";

			MongoClientURI uri = new MongoClientURI(textUri);
			MongoClient m = new MongoClient(uri);
			MongoOperations mongoOps = new MongoTemplate(m, "socialservicedb");

			mongoOps.updateFirst(new Query(Criteria.where("_id").is(bedId)),
					Update.update("status", "occupied"), BedInfo.class);

			mongoOps.updateFirst(new Query(Criteria.where("_id").is(userId)),
					Update.update("bedId", bedId), UserInfo.class);

			status = true;
		} catch (Exception e) {

			e.printStackTrace();
		}

		return status;
	}

}
