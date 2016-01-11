package shared.model;

import server.DataImporter;

import org.w3c.dom.Element;

public class User {
	private String username = null;
	private String password = null;
	private String firstname = null;
	private String lastname = null;
	private String email = null;
	private int indexedrecords = 0;
	private int batchID = -1;
	private int personalID = -1;
	
	
	public User(Element userElement)
	{
		/*username = new String();
		password = new String();
		firstname = new String();
		lastname = new String();
		email = new String();
		indexedrecords = 0;
		batchID = -1;*/
		
		username = DataImporter.getValue((Element)userElement.getElementsByTagName("username").item(0));
		password = DataImporter.getValue((Element)userElement.getElementsByTagName("password").item(0));
		firstname = DataImporter.getValue((Element)userElement.getElementsByTagName("firstname").item(0));
		lastname = DataImporter.getValue((Element)userElement.getElementsByTagName("lastname").item(0));
		email = DataImporter.getValue((Element)userElement.getElementsByTagName("email").item(0));
		indexedrecords = Integer.parseInt(DataImporter.getValue(
					(Element)userElement.getElementsByTagName("indexedrecords").item(0)));
		//Do I do this for the batchID??
	}
	
	public User(String uname, String pword, String fname, String lname,
			String email, int ir, int bid)
	{
		setUsername(uname);
		setPassword(pword);
		setFirstname(fname);
		setLastname(lname);
		setEmail(email);
		setIndexedrecords(ir);
		setBatchID(bid);
	}
	
	public void setPersonalID(int i)
	{
		personalID = i;
	}
	
	public int getPersonalID()
	{
		return personalID;
	}
	
	public void setUsername(String s)
	{
		username = s;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setPassword(String s)
	{
		password = s;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setFirstname(String s)
	{
		firstname = s;
	}
	
	public String getFirstname()
	{
		return firstname;
	}
	
	public void setLastname(String s)
	{
		lastname = s;
	}
	
	public String getLastname()
	{
		return lastname;
	}
	
	public void setEmail(String s)
	{
		email = s;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setIndexedrecords(int i)
	{
		indexedrecords = i;
	}
	
	public void IncrementIndexedRecords()
	{
		indexedrecords++;
	}
	
	public int getIndexedrecords()
	{
		return indexedrecords;
	}
	
	public void setBatchID(int i)
	{
		batchID = i;
	}
	
	public int getBatchID()
	{
		return batchID;
	}
}
