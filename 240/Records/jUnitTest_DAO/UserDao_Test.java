package jUnitTest_DAO;

import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.Assert;

import org.junit.Before;

import server.database.Database;
import server.database.DatabaseException;
import server.database.Dao.UserDao;
import shared.model.User;

public class UserDao_Test {
	private static Database db;

	public static void main(String[] args) {
		try {
			Database.initialize();
			db = new Database();
			
			DropandCreate();
			InsertGood();
			DropandCreate();
			InsertBad();
			DropandCreate();
			ValidateUserGood();
			DropandCreate();
			ValidateUserBad();
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Before
	public static void ValidateUserGood()
	{
		User man = new User("amanda", "cupcake", "Amanda", "Michelle", "imcool@gmail.com", 0, 0);
		try {
			db.getUserDAO().insert(man);
			String username = "amanda";
			String password = "cupcake";
			User u = null;
			u = db.getUserDAO().validate(username, password);
			if(u != null)
			{
				System.out.println("Yay");
			}
			else
			{
				System.out.println("boo");
			}
			//assert statements
			//Assert.assertNotNull(u);
		} catch (DatabaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	@Before
	public static void ValidateUserBad()
	{
		String username = "pikachu";
		String password = "trainer";
		User u = null;
		try {
			u = db.getUserDAO().validate(username, password);
			if(u==null)
			{
				System.out.println("bad worked");
			}
			Assert.assertNull(u);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Before
	public static void InsertGood()
	{
		User u = new User("Pikachu", "shock", "Ash", "Ketchum", "pika@poke.com", 0, 0);
		try {
			boolean worked = db.getUserDAO().insertTest(u);
			System.out.println(worked);
			Assert.assertEquals(true, worked);
			
			
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Before
	public static void InsertBad()
	{
		User u = new User("Pikachu", "shock", "Ash", "Ketchum", "pika@poke.com", 0, 0);
		User u2 = new User("Pikachu", "shock", "Ash", "Ketchum", "pika@poke.com", 0, 0);
		try {
			db.getUserDAO().insert(u);
			boolean worked = db.getUserDAO().insertTest(u2);
			System.out.println(worked);
			Assert.assertFalse(worked);
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

}
