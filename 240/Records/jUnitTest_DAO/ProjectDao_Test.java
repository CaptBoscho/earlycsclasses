package jUnitTest_DAO;

import java.sql.SQLException;
import java.sql.Statement;

import server.database.Database;

public class ProjectDao_Test {

	private static Database db;

	public static void main(String[] args) {
		db = new Database();
	
			try {
				DropandCreate();
			} catch (SQLException e) {
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


