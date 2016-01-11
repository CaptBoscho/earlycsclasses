package server.handler;

//import shared.communication.ValidateUserOutput;
import shared.communication.SubmitBatchInput;
import shared.communication.SubmitBatchOutput;
import shared.communication.getFieldsOutput;
import shared.communication.getFieldsInput;

import java.io.IOException;
import java.net.HttpURLConnection;

import server.ServerException;
import server.database.DatabaseException;
import server.facade.ServerFacade;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SBHandler implements HttpHandler {

	public SBHandler(){}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		XStream myxs = new XStream(new DomDriver());
		SubmitBatchInput gFI = (SubmitBatchInput)myxs.fromXML(exchange.getRequestBody());
		SubmitBatchOutput results;
		try {
			results = ServerFacade.SubmitBatch(gFI.getUsername(), gFI.getPassword(), gFI.getBatchID(), gFI.getRecords());
			//got to facade
			if(results == null)
			{
				results = new SubmitBatchOutput();
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
		}
		
		
	}

}