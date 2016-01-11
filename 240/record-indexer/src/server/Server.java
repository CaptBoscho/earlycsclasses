package server;

import java.io.*;
import java.net.*;
import java.util.logging.*;

import server.facade.ServerFacade;
import server.handler.*;

import com.sun.net.httpserver.*;

public class Server {

	private static int SERVER_PORT_NUMBER = 40000;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	private static Logger logger;
	
	static {
		try {
			initLog();
		}
		catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static void initLog() throws IOException {
		
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("server"); 
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}

	private HttpServer server;
	
	private Server() {
		return;
	}
	
	private void run() {
		
		logger.info("Initializing Model");
		
		try {
			ServerFacade.initialize();		
		}
		catch (ServerException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
			
		logger.info("Initializing HTTP Server");
		
		try {
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
											MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		server.setExecutor(null); // use the default executor
		
		server.createContext("/validateUser",   	 validateUserHandler);
		server.createContext("/getprojects",    	 getProjectsHandler);
		server.createContext("/getsampleimage", 	 getSampleImageHandler);
		server.createContext("/downloadbatch",  	 downloadBatchHandler);
		server.createContext("/submitbatch", 		 submitBatchHandler);
		server.createContext("/getfields", 			 getFieldsHandler);
		server.createContext("/search", 		     searchHandler);
		server.createContext("/", downloadFileHandler);
		
		logger.info("Starting HTTP Server");

		server.start();
	}

	private HttpHandler validateUserHandler   = new VUHandler();
	private HttpHandler getProjectsHandler    = new GProHandler();
	private HttpHandler getSampleImageHandler = new SIHandler();
	private HttpHandler downloadBatchHandler  = new DBHandler();
	private HttpHandler submitBatchHandler 	  = new SBHandler();
	private HttpHandler getFieldsHandler 	  = new GFldsHandler();
	private HttpHandler searchHandler  		  = new SrchHandler();
	private HttpHandler downloadFileHandler   = new DFHandler();
	
	public static void main(String[] args) 
	{	
		if (args.length != 0)
			SERVER_PORT_NUMBER = Integer.parseInt(args[0]);
		new Server().run();
	}

}

