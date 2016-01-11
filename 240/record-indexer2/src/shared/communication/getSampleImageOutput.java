package shared.communication;

import shared.model.*;

public class getSampleImageOutput {
	
	private String url;
	boolean works = true;
	
	public getSampleImageOutput()
	{
		
	}
	public getSampleImageOutput(String u)
	{
		url = u;
	}
	
	public void setURL(String s)
	{
		url = s;
	}
	
	public void NoWork()
	{
		works = false;
	}
	
	public boolean getWorks()
	{
		return works;
	}
	
	/**
	 * From the specified project, calls the getImages function and returns the 
	 * URL string
	 * @param ID
	 * @return
	 */	
	public String getURL()
	{
		return url;
	}
	
	public void FixURL(String hostname, int port)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("http://");
		sb.append(hostname);
		sb.append(":");
		sb.append(Integer.toString(port));
		sb.append("/");
		sb.append(url);
		System.out.println("url in output " + url);
		url = sb.toString();
	}
	
	public String toString()
	{
		if(works == false)
		{
			return "FAILED";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(url + '\n');
		return sb.toString();
	}

}
