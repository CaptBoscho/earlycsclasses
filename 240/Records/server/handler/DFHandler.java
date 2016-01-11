package server.handler;

import java.io.IOException;
import java.nio.file.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class DFHandler implements HttpHandler{

	

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		String s = exchange.getRequestURI().toString();
		s = s.substring(1);
		Path p = Paths.get(s);
		byte[] b = Files.readAllBytes(p);
		exchange.sendResponseHeaders(200, b.length);
		exchange.getResponseBody().write(b);;
		exchange.getResponseBody().close();
	}


}
