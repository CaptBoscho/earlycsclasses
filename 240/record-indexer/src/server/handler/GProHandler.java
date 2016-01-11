package server.handler;

//import shared.communication.ValidateUserOutput;
import shared.communication.ValidateUserInput;
import shared.communication.getProjectsOutput;

import java.io.IOException;
import java.net.HttpURLConnection;

import server.ServerException;
import server.database.DatabaseException;
import server.facade.ServerFacade;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GProHandler implements HttpHandler {

	public GProHandler(){}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		XStream myxs = new XStream(new DomDriver());
		
		ValidateUserInput vu = (ValidateUserInput)myxs.fromXML(exchange.getRequestBody());
		
		getProjectsOutput results;
		try {
			results = ServerFacade.GetPro(vu.getUsername(), vu.getPassword());
			
			//got to facade
			if(results == null)
			{
				results = new getProjectsOutput();
				results.NoWorks();
			}
			
			if(results == null)
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
			}
			else
			{
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				myxs.toXML(results, exchange.getResponseBody());
			}
			exchange.getResponseBody().close();
			
			
		} catch (ServerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
		}
		
		
	}

}