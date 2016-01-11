package jUnitTest_DAO;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import server.database.Database;
import server.database.DatabaseException;
import shared.model.*;

public class ValueDao_Test {

	private static Database db;

	public static void main(String[] args) {
			try {
				Database.initialize();
				db = new Database();
				
				DropandCreate();
				InsertGood();
				DropandCreate();
				SearchGood();
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
		public static void SearchGood()
		{
			Value v = new Value("dog", 1, 1, 2);
			Value val = new Value("cat", 2, 1, 2);
			Value value = new Value("mouse", 3, 1, 2);
			Value vl = new Value("fish", 4, 2, 1);
			Value v5 = new Value("hamster", 5, 1, 1);
			try {
				db.getValueDAO().insert(v);
				db.getValueDAO().insert(val);
				db.getValueDAO().insert(value);
				db.getValueDAO().insert(vl);
				db.getValueDAO().insert(v5);
				List<String> values = new ArrayList<String>();
				values.add("dog");
				values.add("fish");
				values.add("hamster");
				List<String> values2 = new ArrayList<String>();
				values2.add("monkey");
				boolean working1 = db.getValueDAO().Search(1, 2, values, 1);
				System.out.println(working1); 
				//should be true
				boolean working2 = db.getValueDAO().Search(2, 1, values, 4);
				System.out.println(working2);
				//assert true
				boolean working3 = db.getValueDAO().Search(1, 1, values, 5);
				System.out.println(working3);
				//assert true
				//boolean working4 = db.getValueDAO().Search(2, 1, values2);
				//System.out.println(working4);
					//assert false
				
			
				
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		public static void InsertGood()
		{
			Value v = new Value("dog", 1, 2, 3);
			try {
				boolean worked = db.getValueDAO().insertTest(v);
				Assert.assertEquals(true, worked);
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public static void InsertBad()
		{
			//what's a bad value to insert?
		}
	
		public static void DropandCreate() throws SQLException
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
						+ "personalID INTEGER)");
				
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

	}


