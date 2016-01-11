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
		System.out.println("GProHandler1");
		XStream myxs = new XStream(new DomDriver());
		System.out.println("GProHandler2");
		ValidateUserInput vu = (ValidateUserInput)myxs.fromXML(exchange.getRequestBody());
		System.out.println("GProHandler3");
		getProjectsOutput results;
		try {
			System.out.println("GProHandler4");
			results = ServerFacade.GetPro(vu.getUsername(), vu.getPassword());
			System.out.println("GProHandler5");
			//got to facade
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