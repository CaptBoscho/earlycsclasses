package server.communicator;

import java.io.*;
import java.net.*;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
//import client.*;

public class ClientCommunicator 
{
	private static String SERVER_HOST = "localhost";
	private static int SERVER_PORT    = 40000;
	private static String URL_PREFIX  = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	private static final String HTTP_POST = "POST";
	
	private XStream xmlStream;

	public ClientCommunicator() {
		xmlStream = new XStream(new DomDriver());
	}

	public ClientCommunicator(String host, String port)
	{
		SERVER_HOST = host;
		SERVER_PORT = Integer.parseInt(port);
		URL_PREFIX  = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}

	public Object doPost(String urlPath, Object postData) throws ClientException 
	{
		Object result = null;
		try 
		{
			xmlStream = new XStream(new DomDriver());
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.connect();
			xmlStream.toXML(postData, connection.getOutputStream());
			connection.getOutputStream().close();
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new ClientException(String.format("doPost failed: %s (http code %d)",
						urlPath, connection.getResponseCode()));
			}
			else {
				result = xmlStream.fromXML(connection.getInputStream());
			}
		}
		catch (IOException e) {
			throw new ClientException(String.format("doPost failed: %s", e.getMessage()), e);
		}
		return result;
	}
	
	/**
	 * Reads in a username and password and calls upon the ValidateUserinput class to 
	 * validate it. If the user exists, then it will output the information of the 
	 * user. If the user doesn't exist (the output object received is null) then output false
	 * @param username
	 * @param password
	 * @throws ClientException 
	 * @throws IOException 
	 */
	public ValidateUserOutput ValidateUser(String username, String password) throws ClientException
	{
		ValidateUserInput bro = new ValidateUserInput(username, password);
		Object posted = null;
	
		posted = doPost("/validateUser", bro);
	
		return (ValidateUserOutput)posted;		
		
	}
	
	/**
	 * First call the ValidateUserinput class to get a user back.
	 * Then call the getProjectsOutput class to access the list of available projects
	 * Prints the info from getProjectsOutput function print
	 * @param username
	 * @param password
	 * @throws IOException 
	 * @throws ClientException 
	 */
	public getProjectsOutput getProjects(String username, String password) throws ClientException
	{
		ValidateUserInput bro = new ValidateUserInput(username, password);
		Object posted = doPost("/getprojects", bro);
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
	 * @throws ClientException 
	 * @throws IOException 
	 */
	public getSampleImageOutput getSampleImage(String username, String password, int projectID) throws ClientException
	{
		getFieldsInput gfi = new getFieldsInput(username, password, projectID);
		getSampleImageOutput posted = (getSampleImageOutput)doPost("/getsampleimage", gfi); 
		if(posted.getWorks())
		{
			posted.FixURL(SERVER_HOST, SERVER_PORT);
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
	 * @throws ClientException 
	 * @throws IOException 
	 */
	public DownloadBatchOutput DownloadBatch(String username, String password, int projectID) throws ClientException 
	{
		getFieldsInput gfi = new getFieldsInput(username, password, projectID);
		DownloadBatchOutput posted = (DownloadBatchOutput)doPost("/downloadbatch", gfi);
		if(posted.getWorks())
		{
			posted.FixURL(SERVER_HOST, SERVER_PORT);
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
	 * @throws ClientException 
	 * @throws IOException 
	 */
	public SubmitBatchOutput SubmitBatch(String username, String password, int batch, String values) throws ClientException 
	{
		SubmitBatchInput sbi = new SubmitBatchInput(username, password, values, batch);
		Object posted = doPost("/submitbatch", sbi);
		return (SubmitBatchOutput) posted;
	}
	
	/**
	 * Validate user.
	 * Call the findfield(int projectID) function in getFieldsInput class to return the field
	 * 
	 * @param username
	 * @param password
	 * @param projectID
	 * @throws ClientException 
	 * @throws IOException 
	 */
	public getFieldsOutput getFields(String username, String password, int projectID) throws ClientException 
	{
		getFieldsInput gfi = new getFieldsInput(username, password, projectID);
		Object posted = doPost("/getfields", gfi);
		return (getFieldsOutput)posted;
	}
	
	public SearchOutput Search(String username, String password, List<Integer> lf, List<String> ls) throws ClientException
	{
		SearchInput search = new SearchInput(username, password, lf, ls);
		SearchOutput posted = (SearchOutput)doPost("/search", search);
		if(posted.getWorks())
		{
			posted.FixURL(SERVER_HOST, SERVER_PORT);
		}
		System.out.println("CC: posted size: " + posted.getSize());
		return posted;
	}
}
