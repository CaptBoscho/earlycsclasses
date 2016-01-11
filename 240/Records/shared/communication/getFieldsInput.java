package shared.communication;

import shared.model.*;
import java.util.List;


public class getFieldsInput {

	private String username = null;
	private String password = null;
	private int pID = -1;
	
	public getFieldsInput(){}
	public getFieldsInput(String u, String p, int id)
	{
		username = u;
		password = p;
		pID = id;
	}
	
	public void setUsername(String u)
	{
		username = u;
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
	
	public void setPID(int i)
	{
		pID = i;
	}
	
	public int getPID()
	{
		return pID;
	}
}
