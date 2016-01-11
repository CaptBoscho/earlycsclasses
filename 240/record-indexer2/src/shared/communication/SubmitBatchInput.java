package shared.communication;

import shared.model.*;

import java.util.List;


public class SubmitBatchInput {

	private String username = null;
	private String password = null;
	private String records = null;
	private int batchID = -1;
	
	public SubmitBatchInput(String u, String p, String r, int batch)
	{
		username = u;
		password = p;
		records = r;
		batchID = batch;
	}
	public SubmitBatchInput(){}
	
	public void setUsername(String s)
	{
		username = s;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setPassword(String p)
	{
		password = p;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setRecords(String r)
	{
		records = r;
	}
	
	public String getRecords()
	{
		return records;
	}
	
	public void setBatchID(int bid)
	{
		batchID = bid;
	}
	
	public int getBatchID()
	{
		return batchID;
	}
	
	
	/**
	 * tries to upload the batch using the the User params, the batchID and the 
	 * fieldvalues. if successful, return true. else, return false.
	 * @return
	 */
	public boolean Upload()
	{
		//somehow submits this batch to I don't know where
		/*image img = new image(); //get img from the batch
		int projectID = img.getparentProjectID();
		project p = new project(); //get p from the projectID
		
		List<Integer> fields = p.getFields();
		List<Integer> records = img.getRecords();
		for(int k=0; k<records.size(); k++)
		{
	 		for(int i=0; i<fields.size(); i++)
			{
				//So I'm thinking that records will contain all the info I need...
	 			//Or do I have to look in the records table for the record that matches the x/y coord?
			}
		}*/
		
		return false;
	}
	
}
