package server.handler;

//import shared.communication.ValidateUserOutput;

import shared.communication.getFieldsInput;
import shared.communication.getSampleImageOutput;


import java.io.IOException;
import java.net.HttpURLConnection;

import server.ServerException;
import server.database.DatabaseException;
import server.facade.ServerFacade;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SIHandler implements HttpHandler {

	public SIHandler(){}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		XStream myxs = new XStream(new DomDriver());
		System.out.println("SIHandler 28");
		getFieldsInput flds = (getFieldsInput)myxs.fromXML(exchange.getRequestBody());
		getSampleImageOutput results = new getSampleImageOutput();
		System.out.println("SIHandler 31");
		String u = ServerFacade.GetSampleImage(flds.getUsername(), flds.getPassword(), flds.getPID());
		System.out.println(u);
		if(u == null)
		{
			results.NoWork();
			u = "notempty";
		}
		results.setURL(u);
		//got to facade
		
		
		if(u == null)
		{
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
			myxs.toXML(results, exchange.getResponseBody());
		}
		else
		{
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			myxs.toXML(results, exchange.getResponseBody());
		}
		exchange.getResponseBody().close();
		
		
	}

}