package server.communicator;

import shared.communication.*;
import shared.model.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class ClientCommunicator {
	
	/**
	 * The different methods of the Client Communicator class
	 */
	private String hostname = null;
	private int port = 40000;
	
	public ClientCommunicator()
	{
		
	}
	
	public void setHostname(String s)
	{
		hostname = s;
	}
	
	public String getHostname()
	{
		return hostname;
	}
	
	public void setPort(int p)
	{
		port = p;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public Object post(String urlPath, Object data) throws IOException
	{
		String urlBase = "http://" + hostname + ":" + Integer.toString(port);
		XStream xst = new XStream(new DomDriver());
		URL url = new URL(urlBase + urlPath);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.connect();
		
		xst.toXML(data, conn.getOutputStream());
		if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
		{
			
			Object result = xst.fromXML(conn.getInputStream());
			return result;
		}
		return null;
	}
	
	/**
	 * Reads in a username and password and calls upon the ValidateUserinput class to 
	 * validate it. If the user exists, then it will output the information of the 
	 * user. If the user doesn't exist (the output object received is null) then output false
	 * @param username
	 * @param password
	 * @throws IOException 
	 */
	public ValidateUserOutput ValidateUser(String username, String password)
	{
		ValidateUserInput bro = new ValidateUserInput(username, password);
		Object posted = null;
		try {
			posted = post("/validateUser", bro);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (ValidateUserOutput)posted;		
		
	}
	
	/**
	 * First call the ValidateUserinput class to get a user back.
	 * Then call the getProjectsOutput class to access the list of available projects
	 * Prints the info from getProjectsOutput function print
	 * @param username
	 * @param password
	 * @throws IOException 
	 */
	public getProjectsOutput getProjects(String username, String password) throws IOException
	{
		ValidateUserInput bro = new ValidateUserInput(username, password);
		Object posted = post("/getprojects", bro);
		//System.out.println("CC");
		if(posted == null)
		{
			System.out.println("It's null");
		}
		return (getProjectsOutput) posted;
	}
	
	/**
	 * First calls the ValidateUserinput class to get a user back
	 * Then passes in the project ID to getSampleImageOutput and prints out the string
	 * @param username
	 * @param password
	 * @param ID
	 * @throws IOException 
	 */
	public getSampleImageOutput getSampleImage(String username, String password, int projectID) throws IOException
	{
		getFieldsInput gfi = new getFieldsInput(username, password, projectID);
		getSampleImageOutput posted = (getSampleImageOutput)post("/getsampleimage", gfi); 
		if(posted.getWorks())
		{
			posted.FixURL(hostname, port);
		}
		return posted;
	}

	/**
	 * The Server should assign the user a batch from the requested project.
	 * The Server should not return batches that are already assigned to another user.
	 * If the user already has a batch assigned to them, this operation should fail.
	 * @param username
	 * @param password
	 * @param ID
	 * @throws IOException 
	 */
	public DownloadBatchOutput DownloadBatch(String username, String password, int projectID) throws IOException
	{
		getFieldsInput gfi = new getFieldsInput(username, password, projectID);
		DownloadBatchOutput posted = (DownloadBatchOutput)post("/downloadbatch", gfi);
		if(posted.getWorks())
		{
			posted.FixURL(hostname, port);
		}
		return posted;
	}
	
	/**
	 * Gets a user from the validateuserinput class.
	 * the String fieldvalues comes from the record class, getValues function.
	 * creates a SubmitBatchInput object and uses it's upload function.
	 * it will return true if successful, false if not.
	 * Output "True" or "Failed"
	 * @param username
	 * @param password
	 * @param batch
	 * @param fieldvalues
	 * @throws IOException 
	 */
	public SubmitBatchOutput SubmitBatch(String username, String password, int batch, String values) throws IOException
	{
		SubmitBatchInput sbi = new SubmitBatchInput(username, password, values, batch);
		Object posted = post("/submitbatch", sbi);
		return (SubmitBatchOutput) posted;
	}
	
	/**
	 * Validate user.
	 * Call the findfield(int projectID) function in getFieldsInput class to return the field
	 * 
	 * @param username
	 * @param password
	 * @param projectID
	 * @throws IOException 
	 */
	public getFieldsOutput getFields(String username, String password, int projectID) throws IOException
	{
		getFieldsInput gfi = new getFieldsInput(username, password, projectID);
		Object posted = post("/getfields", gfi);
		return (getFieldsOutput)posted;
	}
	
	public SearchOutput Search(String username, String password, List<Integer> lf, List<String> ls) throws IOException
	{
		SearchInput search = new SearchInput(username, password, lf, ls);
		Object posted = post("/search", search);
		return (SearchOutput)posted;
	}
}
