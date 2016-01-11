package server;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import server.database.Database;
import server.database.DatabaseException;
import shared.model.User;
import shared.model.project;
//import server.database.Dao.*;

public class DataImporter {
	private static Database db;
	//private static UserDao userDAO;
	//private static ProjectDao projectDAO;
	
	public DataImporter(Database d)
	{
		db = d;
	}

	public static void main(String[] args) throws DatabaseException, SQLException {
		// TODO Auto-generated method stub
		
		Database.initialize();
		DataImporter di = new DataImporter(new Database());
		
		
		try {
			File xmlFile = new File(args[0]);
			if(!xmlFile.canRead())
			{
				throw new IOException();
			}
			File dest = new File("Records");
			FileUtils.copyDirectory(xmlFile.getParentFile(), dest);
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.startTransaction();
		di.DropandCreate();
		Document doc = di.Open();
		
		NodeList temp = doc.getElementsByTagName("users");
		NodeList users = doc.getElementsByTagName("user");
		for (int i=0; i< users.getLength(); i++)
		{
			Element uselement = (Element)users.item(i);
			User u = new User(uselement);
			db.getUserDAO().insert(u);//insert user to Dao
		}
		
		NodeList temp2 = doc.getElementsByTagName("projects");
		NodeList proList = doc.getElementsByTagName("project");
		for (int i=0; i< proList.getLength(); i++)
		{
			Element proelement = (Element)proList.item(i);
			project p = new project(proelement, db, i+1);
			db.getProjectDAO().insert(p);//insert project to Dao
		}
		
		db.endTransaction(true);
		
		/*File xmlFile = new File("Records.xml");
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = dBuilder.parse(xmlFile);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		doc.getDocumentElement().normalize();
		Element root = doc.getDocumentElement();
		//IndexerData indexer = new IndexerData(root);*/
		
	}
	
	public Document Open()
	{

		DocumentBuilder builder = null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File file = new File("Records.xml");
		Document doc = null;
		try {
			doc = builder.parse(file);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public void DropandCreate() throws SQLException
	{
		Statement s = db.getConnection().createStatement();
		
		
		try {
			s.execute("DROP TABLE IF EXISTS field");
			s.execute("DROP TABLE IF EXISTS image");
			s.execute("DROP TABLE IF EXISTS user");
			s.execute("DROP TABLE IF EXISTS project");
			s.execute("DROP TABLE IF EXISTS record");
			s.execute("DROP TABLE IF EXISTS value");
			
			s.execute("CREATE TABLE IF NOT EXISTS value ("
					+ "value TEXT, "
					+ "xcoord INTEGER, "
					+ "recordID INTEGER, "
					+ "BatchID INTEGER)");
			
			s.execute("CREATE TABLE IF NOT EXISTS record ("
					+ "ycoord INTEGER, "
					+ "BatchID INTEGER NOT NULL  DEFAULT -1)");
			
			s.execute("CREATE TABLE IF NOT EXISTS image ("
					+ "ParentProjectID INTEGER,"
					+ "available BOOL,"
					+ "file TEXT,"
					+ "personalID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE)");
			
			s.execute("CREATE TABLE IF NOT EXISTS user ("
					+ "username TEXT PRIMARY KEY  NOT NULL  UNIQUE,"
					+ "password TEXT NOT NULL,"
					+ "firstname TEXT NOT NULL,"
					+ "lastname TEXT NOT NULL,"
					+ "email TEXT,"
					+ "indexedrecords INTEGER,"
					+ "batchID INTEGER)");
			
			s.execute("CREATE TABLE IF NOT EXISTS project ("
					+ "title TEXT,"
					+ "recordsperimage INTEGER,"
					+ "firstycoord INTEGER,"
					+ "recordheight INTEGER,"
					+ "personalID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE)");
			
			s.execute("CREATE TABLE IF NOT EXISTS field ("
					+ "title TEXT NOT NULL,"
					+ "xcoord INTEGER,"
					+ "width INTEGER,"
					+ "helphtml TEXT,"
					+ "knowndata TEXT,"
					+ "parentProjectID INTEGER,"
					+ "personalID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE)");
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public static ArrayList<Element> getChildElements (Node node)
	{
		ArrayList<Element> result = new ArrayList<Element>();
		
		NodeList children = node.getChildNodes();
		for(int i=0; i<children.getLength(); i++)
		{
			Node child = children.item(i);
			if(child.getNodeType() == Node.ELEMENT_NODE)
			{
				result.add((Element)child);
			}
		}
		
		return result;
	}*/
	
	public static String getValue(Element element)
	{
		String result = "";
		if(element == null)
		{
			return null;
		}
		Node child = element.getFirstChild();
	
		result = child.getNodeValue();
		return result;
	}

}
