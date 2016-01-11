package shared.communication;

import shared.model.*;

import java.util.ArrayList;
import java.util.List;


public class SearchInput {

	private String username = null;
	private String password = null;
	private List<Integer> fieldIDs = new ArrayList<Integer>();
	private List<String> values = new ArrayList<String>();
	
	public SearchInput(String u, String p, List<Integer> fs, List<String> v)
	{
		username = u;
		password = p;
		fieldIDs= fs;
		values = v;
	}
	
	public SearchInput(){}
	
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
	
	public void setFieldIDS(List<Integer> fs)
	{
		fieldIDs = fs;
	}
	
	public List<Integer> getFieldIDS()
	{
		return fieldIDs;
	}
	
	public void setValues(List<String> v)
	{
		values = v;
	}
	
	public List<String> getValues()
	{
		return values;
	}
	
	
}
