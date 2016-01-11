package shared.communication;

import shared.model.User;

public class ValidateUserOutput {

	//private User dude;
	private int recordsindexed = -1;
	private String fname = null;
	private String lname = null;
	private int BID = -1;
	private boolean works = true;
	private boolean failed = true;
	/**
	 * creates a validateuseroutput object with the correct User
	 */
	
	public ValidateUserOutput(){}
	public ValidateUserOutput(User man)
	{
		recordsindexed = man.getIndexedrecords();
		fname = man.getFirstname();
		lname = man.getLastname();
		BID = man.getBatchID();
	}
	
	public int getBID()
	{
		return BID;
	}
	
	public void setRecordsIndexed(int r)
	{
		recordsindexed = r;
	}
	
	public int getRecordsIndexed()
	{
		return recordsindexed;
	}
	
	public void setFirstname(String f)
	{
		fname = f;
	}
	
	public String getFirstname()
	{
		return fname;
	}
	
	public void setLastname(String l)
	{
		lname = l;
	}
	
	public String getLastname()
	{
		return lname;
	}
	
	public void NoWork()
	{
		works = false;
	}
	
	public void Failed()
	{
		failed = false;
	}
	
	public boolean Works()
	{
		if(works == true && failed == true)
		{
			return works;
		}
		else
		{
			return false;
		}
	}
	
	
	public String toString()
	{
		if(works==false)
		{
			if(failed == false)
			{
				return "FAILED";
			}
			return "FALSE";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("TRUE" + '\n');
		sb.append(fname + '\n');
		sb.append(lname + '\n');
		sb.append(Integer.toString(recordsindexed) + '\n');
		
		return sb.toString();
	}
	
	
	
}
