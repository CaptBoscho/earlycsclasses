package server.handler;

//import shared.communication.ValidateUserOutput;
import shared.communication.SearchInput;
import shared.communication.SearchOutput;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import server.ServerException;
import server.database.DatabaseException;
import server.facade.ServerFacade;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SrchHandler implements HttpHandler {

	public SrchHandler(){}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		XStream myxs = new XStream(new DomDriver());
		SearchInput si = (SearchInput)myxs.fromXML(exchange.getRequestBody());
		SearchOutput results;
		try {
			results = ServerFacade.Search(si.getUsername(), si.getPassword(), si.getFieldIDS(), si.getValues());
			//got to facade
			System.out.println("SrchHandler: \n inside results size: " + results.getSize());
			
			if(results == null)
			{
				results = new SearchOutput();
				results.NoWork();
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
			
			
		} catch (DatabaseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}