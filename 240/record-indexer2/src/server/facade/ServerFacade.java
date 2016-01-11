package server.facade;

import java.util.*;

import server.database.*;
import shared.model.*;
import server.*;
import shared.communication.*;

public class ServerFacade {
	private static Database db;

	public static void initialize() throws ServerException {		
		try {
			Database.initialize();		
		}
		catch (DatabaseException e) {
			throw new ServerException(e.getMessage(), e);
		}		
	}
	
	public static String GetSampleImage(String username, String password, int proID) 
	{
		getSampleImageOutput bad = new getSampleImageOutput();
		bad.NoWork();
		try {
			initialize();
			db = new Database();
			db.startTransaction();
			User u = db.getUserDAO().validate(username, password);
			if(u==null)
			{
				db.endTransaction(false);
				return null;
				//Report Failed!
			}
			String imgurl = db.getImageDAO().getSampleImage(proID);
			//System.out.println("url in facade: " + imgurl);
			getSampleImageOutput result = new getSampleImageOutput(imgurl);
			result.setURL(imgurl);
			db.endTransaction(true);
			return imgurl;
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			db.endTransaction(false);
			return null;
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			db.endTransaction(false);
			return null;
		}
	}
	
	public static getProjectsOutput GetPro(String username, String password) throws ServerException, DatabaseException
	{
		//System.out.println("ServerFacade 51");
		initialize();
		db = new Database();
		db.startTransaction();
		User u = db.getUserDAO().validate(username, password);
		if (u==null)
		{
			getProjectsOutput bad = new getProjectsOutput();
			bad.NoWorks();
			db.endTransaction(false);
			return bad;
		}
		
		try {
			List<project> projects = db.getProjectDAO().getAll();
			if(projects == null)
			{
				getProjectsOutput bad = new getProjectsOutput();
				bad.NoWorks();
				db.endTransaction(false);
				return bad;
			}
			getProjectsOutput po = new getProjectsOutput(projects);
			db.endTransaction(true);
			return po;
		} catch (DatabaseException e) {
			db.endTransaction(false);
			getProjectsOutput bad = new getProjectsOutput();
			bad.NoWorks();
			return bad;
		}
	}
	
	public static getFieldsOutput GetFields(String username, String password, int ppID) throws ServerException, DatabaseException
	{
		initialize();
		db = new Database();
		db.startTransaction();
		User u = db.getUserDAO().validate(username, password);
		if (u==null)
		{
			db.endTransaction(false);
			getFieldsOutput bad = new getFieldsOutput();
			bad.NoWorks();
			return bad;
		}
		
		try {
			
			List<field> fields = db.getFieldDAO().getAll(ppID);
			
			if (fields == null)
			{
				db.endTransaction(false);
				getFieldsOutput bad = new getFieldsOutput();
				bad.NoWorks();
				return bad;
			}
			db.endTransaction(true);
			return new getFieldsOutput(fields);
		} catch (DatabaseException e) {
			db.endTransaction(false);
			getFieldsOutput bad = new getFieldsOutput();
			bad.NoWorks();
			return bad;
		}
	}
	
	public static DownloadBatchOutput DownloadBatch(String username, String password, int ID) throws ServerException, DatabaseException
	{
		initialize();
		db = new Database();
		db.startTransaction();
		User u = db.getUserDAO().validate(username, password);
		//System.out.println("username: " + username);
		//System.out.println("password: " + password);
		DownloadBatchOutput download = new DownloadBatchOutput();
		
		if (u == null)
		{
			db.endTransaction(false);
			download.NotWork();
			return download;
		}
		
		
		try {
			boolean already = db.getUserDAO().getBatchID(username);
			if(already == false)
			{
				db.endTransaction(false);
				download.NotWork();
				return download;
			}
			project p = db.getProjectDAO().getProject(ID);
			List<field> fields = db.getFieldDAO().getAll(ID);
			List<image> images = db.getImageDAO().Search(ID);
			boolean check = false;
			for(int i=0; i<images.size(); i++)
			{
				//System.out.println(images.get(i).getAvailable());
				if(images.get(i).getAvailable())
				{
					check = true;
				}
			}
			if(check == false)
			{
				db.endTransaction(false);
				download.NotWork();
				return download;
			}
			p.setFields(fields);
			p.setImages(images);
			DownloadBatchOutput dbo = new DownloadBatchOutput(p);
			int BID = dbo.getBatchID();
			db.getUserDAO().updateBatch(BID, username);
			db.getImageDAO().updateAvailable(BID);
			db.endTransaction(true);
			//Do I need to update something in the user that indicates the batch?
			return dbo;
		} catch (DatabaseException e) {
			db.endTransaction(false);
			download.NotWork();
			return download;
		}
		
	}
	
	public static SubmitBatchOutput SubmitBatch(String username, String password, int Batch, String fvalues) throws DatabaseException
	{
		try {
			initialize();
			db = new Database();
			db.startTransaction();
			User u = db.getUserDAO().validate(username, password);
			if (u==null)
			{
				db.endTransaction(false);
				return new SubmitBatchOutput(false);
			}
			if(u.getBatchID() != Batch)
			{
				db.endTransaction(false);
				return new SubmitBatchOutput(false);
			}
			boolean worked = db.getRecordDAO().SubmitBatch(Batch, fvalues);
			if(worked)
			{
				int ir = db.getUserDAO().getIndexedRecords(username);
				int projectID = db.getImageDAO().getProjectID(Batch);
				int rows = db.getProjectDAO().getNumberRecords(projectID);
				//int columns = db.getFieldDAO().getNumber(projectID);
				int total = ir + rows;
				db.getUserDAO().updateIndexedRecords(total, username);
				db.getUserDAO().updateBatch(-1, username);
			}
			db.endTransaction(true);
			
			return new SubmitBatchOutput(worked);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			
			db.endTransaction(false);
			return new SubmitBatchOutput(false);
		}
	}
	
	public static ValidateUserOutput ValidateUser(String username, String password) throws ServerException
	{
		initialize();
		
		db = new Database();
		try{
			db.startTransaction();
			User u = db.getUserDAO().validate(username, password);
			db.endTransaction(true);
			if (u==null)
			{
				ValidateUserOutput bad = new ValidateUserOutput();
				bad.NoWork();
				db.endTransaction(false);
				return bad;
			}
			ValidateUserOutput vuo = new ValidateUserOutput(u);
			return vuo;
		}
		catch (DatabaseException e) {
			db.endTransaction(false);
			ValidateUserOutput bad = new ValidateUserOutput();
			bad.NoWork();
			bad.Failed();
			return bad;
		}
	}
	
	public static SearchOutput Search(String username, String password, List<Integer> fields, List<String> values) throws DatabaseException, ServerException
	{
		System.out.println("Facade: \n Fields size: " + fields.size() + " value: " + values.get(0));
		
		
		initialize();
		db = new Database();
		db.startTransaction();
		User u = db.getUserDAO().validate(username, password);
		if (u==null)
		{
			SearchOutput bad = new SearchOutput();
			bad.NoWork();
			db.endTransaction(false);
			return bad;
		}
		
		List<SearchOutput2> allresults = new ArrayList<SearchOutput2>();
		
		
		for(int i=0; i<fields.size(); i++)
		{
			List<SearchOutput2> someresults = db.getFieldDAO().Search(fields.get(i), values);
			
			if(someresults != null)
			{
				System.out.println("allresults size: " + allresults.size() + " someresults size: " + someresults.size());
				allresults.addAll(someresults);
				System.out.println("final allresults size: " + allresults.size());
			}
		}
		db.endTransaction(true);
		//System.out.println(allresults.size());
		SearchOutput search = new SearchOutput(allresults);
		
		return search;
	}

}