package shared.communication;

import shared.model.User;

public class ValidateUserInput {

	private static final Exception FALSE = null;
	private String username = null;
	private String password = null;
	
	public ValidateUserInput(){}
	public ValidateUserInput(String u, String p)
	{
		username = u;
		password = p;
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
	
}
