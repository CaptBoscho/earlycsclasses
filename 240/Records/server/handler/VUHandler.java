package server.handler;

import shared.communication.ValidateUserOutput;
import shared.communication.ValidateUserInput;

import java.io.IOException;
import java.net.HttpURLConnection;

import server.ServerException;
import server.facade.ServerFacade;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class VUHandler implements HttpHandler {

	public VUHandler(){}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		XStream myxs = new XStream(new DomDriver());
		ValidateUserInput vu = (ValidateUserInput)myxs.fromXML(exchange.getRequestBody());
		ValidateUserOutput results;
		try {
			results = ServerFacade.ValidateUser(vu.getUsername(), vu.getPassword());
			
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
			e.printStackTrace();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
		}
		
		
	}

}
