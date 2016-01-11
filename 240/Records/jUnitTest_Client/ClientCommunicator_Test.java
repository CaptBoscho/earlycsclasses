package jUnitTest_Client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import shared.communication.ValidateUserInput;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ClientCommunicator_Test {

	private String hostname = null;
	private int port = 0;
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	public void ValidateUserGood()
	{
		String usname = "sheila";
		String pword = "parker";
		ValidateUserInput bro = new ValidateUserInput(usname, pword);
		//Object posted = post("/validateUser", bro);
	}

}
