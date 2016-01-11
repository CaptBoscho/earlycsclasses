package jUnitTest_DAO;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import junit.framework.Assert;
import server.database.Database;
import server.database.DatabaseException;
import shared.model.project;

public class ProjectDao_Test {

	private static Database db;

	public static void main(String[] args) {
			try {
				Database.initialize();
				db = new Database();
				db.startTransaction();
				DropandCreate();
				InsertGood();
				DropandCreate();
				GetAllProjectsGood();
				DropandCreate();
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	/*
	 * Checks if the projects are correctly added by receiving the projectID number after insertion.
	 * It should be changed to ID's 1 and 2.
	 */
	public static void InsertGood()
	{
		project p1 = new project("Pokemon Census", 8, 0, 150, 10);
		project p2 = new project("Trainer Census", 4, 0, 250, 12);
		int id1 = db.getProjectDAO().insertTest(p1);
		int id2 = db.getProjectDAO().insertTest(p2);
		Assert.assertEquals(1, id1);
		Assert.assertEquals(2, id2);
		
	}
	
	public static void GetAllProjectsGood()
	{
		project p1 = new project("Pokemon Census", 8, 0, 150, 10);
		project p2 = new project("Trainer Census", 4, 0, 250, 12);
		try {
			db.getProjectDAO().insert(p1);
			db.getProjectDAO().insert(p2);
			List<project> list = db.getProjectDAO().getAll();
			
			Assert.assertEquals(2, list.size());
			Assert.assertEquals("Pokemon Census", list.get(0).getTitle());
			Assert.assertEquals("Trainer Census", list.get(1).getTitle());
			
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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


