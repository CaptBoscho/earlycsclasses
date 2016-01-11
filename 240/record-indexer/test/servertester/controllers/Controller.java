package servertester.controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import server.communicator.ClientCommunicator;
import server.communicator.ClientException;
import servertester.views.*;
import shared.communication.*;

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		try {
			getView().setHost(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			getView().setHost("localhost");
		}
		getView().setPort("40000");
		operationSelected();
		System.out.println("controller:" + getView().getPort());
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			
			try {
				validateUser();
			} catch (ClientException e1) {
				// TODO Auto-generated catch block
				getView().setResponse("FAILED");
				e1.printStackTrace();
			}
			
			break;
		case GET_PROJECTS:
			try {
				getProjects();
			} catch (IOException e) { 
				// TODO Auto-generated catch block
				getView().setResponse("FAILED");
				e.printStackTrace();
			} catch (ClientException e) {
				// TODO Auto-generated catch block
				getView().setResponse("FAILED");
				e.printStackTrace();
			}
			break;
		case GET_SAMPLE_IMAGE:
			try {
				getSampleImage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				getView().setResponse("FAILED");
				e.printStackTrace();
			} catch (ClientException e) {
				// TODO Auto-generated catch block
				getView().setResponse("FAILED");
				e.printStackTrace();
			}
			break;
		case DOWNLOAD_BATCH:
			try {
				downloadBatch();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				getView().setResponse("FAILED");
				e.printStackTrace();
			} catch (ClientException e) {
				// TODO Auto-generated catch block
				getView().setResponse("FAILED");
				e.printStackTrace();
			}
			break;
		case GET_FIELDS:
			try {
				getFields();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				getView().setResponse("FAILED");
				e.printStackTrace();
			} catch (ClientException e) {
				// TODO Auto-generated catch block
				getView().setResponse("FAILED");
				e.printStackTrace();
			}
			break;
		case SUBMIT_BATCH:
			try {
				submitBatch();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				getView().setResponse("FAILED");
				e.printStackTrace();
			} catch (ClientException e) {
				// TODO Auto-generated catch block
				getView().setResponse("FAILED");
				e.printStackTrace();
			}
			break;
		case SEARCH:
			try {
				search();
			
			} catch (ClientException e) {
				// TODO Auto-generated catch block
				getView().setResponse("FAILED");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				getView().setResponse("FAILED");
				e.printStackTrace();
			}
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() throws ClientException {
		ClientCommunicator cc = new ClientCommunicator(getView().getHost(), getView().getPort());
		
		ValidateUserOutput result = new ValidateUserOutput();
		
		String[] params = getView().getParameterValues();
		if(params.length != 2)
		{
			System.out.println("not good parameters for validateUser in controller");
		}
		else
		{
			String username = params[0];
			String password = params[1];
			result = cc.ValidateUser(username, password);
			if(result == null)
			{
				getView().setResponse("FAILED");
			}
			
			getView().setResponse(result.toString());
		}
		//System.out.println("client comm: " + cc.getPort());
	}
	
	private void getProjects() throws IOException, ClientException
	{
		ClientCommunicator cc = new ClientCommunicator(getView().getHost(), getView().getPort());
		
		getProjectsOutput result = new getProjectsOutput();
		
		String[] params = getView().getParameterValues();
		if(params.length != 2)
		{
			System.out.println("no number");
		}
		
		else
		{
			String username = params[0];
			String password = params[1];
			result = cc.getProjects(username, password);
			
			getView().setResponse(result.toString());
			
		}
	}
	
	private void getSampleImage() throws IOException, ClientException {
		ClientCommunicator cc = new ClientCommunicator(getView().getHost(), getView().getPort());
		
		getSampleImageOutput result = new getSampleImageOutput();
		
		
		String[] params = getView().getParameterValues();
		if(params.length != 3)
		{
			getView().setResponse("FAILED");
		}
		else
		{
			String username = params[0];
			String password = params[1];
			String temp = params[2];
			if(temp.equals(""))
			{
				getView().setResponse("FAILED");
			}
			else
			{
				int ID = Integer.parseInt(temp);
				result = cc.getSampleImage(username, password, ID);
				getView().setResponse(result.toString());
			}
		}
	}
	
	private void downloadBatch() throws IOException, ClientException {
		ClientCommunicator cc = new ClientCommunicator(getView().getHost(), getView().getPort());
		
		DownloadBatchOutput result = new DownloadBatchOutput();
		
		String[] params = getView().getParameterValues();
		if(params.length != 3)
		{
			System.out.println("not good parameters for downloadBatch in controller");
		}
		else
		{
			String username = params[0];
			String password = params[1];
			String temp = params[2];
			if(temp.equals(""))
			{
				getView().setResponse("FAILED");
			}
			else
			{
				int ID = Integer.parseInt(temp);
				result = cc.DownloadBatch(username, password, ID);
				getView().setResponse(result.toString());
			}
		}
	}
	
	private void getFields() throws IOException, ClientException {
		ClientCommunicator cc = new ClientCommunicator(getView().getHost(), getView().getPort());
		
		getFieldsOutput result = new getFieldsOutput();
		
		String[] params = getView().getParameterValues();
		
		
		if (params.length != 3)
		{
			System.out.println("error controller 256");
		}
		String username = params[0];
		String password = params[1];
		String temp = params[2];
		System.out.println("temp is: " + temp);
		int ID = -1;
		if(temp.equals(""))
		{
			System.out.println("empty");
			temp = "0";
		}
		
		ID = Integer.parseInt(temp);
		
		
		
		result = cc.getFields(username, password, ID);
		getView().setResponse(result.toString());
		
		
	}
	
	private void submitBatch() throws IOException, ClientException {
		ClientCommunicator cc = new ClientCommunicator(getView().getHost(), getView().getPort());
		
		SubmitBatchOutput result;
		
		String[] params = getView().getParameterValues();
		if(params.length != 4)
		{
			System.out.println("not good parameters for submitBatch in controller");
		}
		else
		{
			String username = params[0];
			String password = params[1];
			String temp = params[2];
			if(temp.equals(""))
			{
				getView().setResponse("FAILED");
			}
			else
			{
				int batch = Integer.parseInt(temp);
				String values = params[3];
				result = cc.SubmitBatch(username, password, batch, values);
				getView().setResponse(result.toString());
			}
		}
	}
	
	private void search() throws IOException, ClientException {
		ClientCommunicator cc = new ClientCommunicator(getView().getHost(), getView().getPort());
		
		SearchOutput result = new SearchOutput();
		
		String[] params = getView().getParameterValues();
		if(params.length != 4)
		{
			System.out.println("not good parameters for search in controller");
		}
		else
		{
			String username = params[0];
			String password = params[1];
			String fieldstemp = params[2];
			//int batch = Integer.parseInt(temp);
			String valuestemp = params[3];
			String[] fieldsarray = fieldstemp.split(",", -1);
			String[] valuesarray = valuestemp.split(",", -1);
			List<Integer> fieldIDs = new ArrayList<Integer>();
			List<String> values = new ArrayList<String>();
			for(int i=0; i< fieldsarray.length; i++)
			{
				int ID = Integer.parseInt(fieldsarray[i]);
				fieldIDs.add(ID);
			}
			for(int k=0; k<valuesarray.length; k++)
			{
				values.add(valuesarray[k]);
			}
			result = cc.Search(username, password, fieldIDs, values);
			getView().setResponse(result.toString());
		}
	}
	
	public void ThrowFailed()
	{
		getView().setResponse("FAILED");
	}
	
	

}

