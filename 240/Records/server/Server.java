package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import server.database.Database;
import server.database.DatabaseException;
import server.facade.ServerFacade;
import server.handler.*;

public class Server {

	private HttpServer myServer;
	private HttpHandler downloadbatch = new DBHandler();
	private HttpHandler downloadfile = new DFHandler();
	private HttpHandler getfields = new GFldsHandler();
	private HttpHandler getprojects = new GProHandler();
	private HttpHandler submitbatch = new SBHandler();
	private HttpHandler sampleimage = new SIHandler();
	private HttpHandler search = new SrchHandler();
	private HttpHandler validateuser = new VUHandler();
	
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
		
		logger = Logger.getLogger("RI"); 
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
			myServer = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
											MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		myServer.setExecutor(null); // use the default executor
		
		myServer.createContext("/validateUser", validateuser);
		myServer.createContext("/search", search);
		myServer.createContext("/getsampleimage", sampleimage);
		myServer.createContext("/submitbatch", submitbatch);
		myServer.createContext("/getprojects", getprojects);
		myServer.createContext("/getfields", getfields);
		myServer.createContext("/", downloadfile);
		myServer.createContext("/downloadbatch", downloadbatch);
		
		logger.info("Starting HTTP Server");

		myServer.start();
	}
	
	public static void main(String[] args)
	{
		if(args.length != 0)
		{
			SERVER_PORT_NUMBER = Integer.valueOf(args[0]);
		}
		new Server().run();
		
	}
	
	
}
