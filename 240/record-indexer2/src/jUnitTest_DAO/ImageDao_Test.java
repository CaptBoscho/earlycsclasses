package jUnitTest_DAO;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import server.database.Database;
import server.database.DatabaseException;
import shared.model.Value;
import shared.model.image;
import shared.model.record;

public class ImageDao_Test {

	private static Database db;

	public static void main(String[] args) {
			try {
				Database.initialize();
				db = new Database();
				DropandCreate();
				InsertGood();
				DropandCreate();
				getSampleImageGood();
				DropandCreate();
				SearchGood();
				DropandCreate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	public static void InsertGood()
	{
		image img = new image(1, true, "file", -1);
		image img2 = new image(1, true, "file2", -1);
		try {
			int id1 = db.getImageDAO().insert(img);
			int id2 = db.getImageDAO().insert(img2);
			
			Assert.assertEquals(1, id1);
			Assert.assertEquals(2, id2);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void getSampleImageGood()
	{
		image img = new image(1, true, "file", -1);
		image img2 = new image(2, true, "file2", -1);
		try {
			int id1 = db.getImageDAO().insert(img);
			int id2 = db.getImageDAO().insert(img2);
			
			String f1 = db.getImageDAO().getSampleImage(1);
			String f2 = db.getImageDAO().getSampleImage(2);
			Assert.assertEquals("file", f1);
			Assert.assertEquals("file2", f2);
			
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void SearchGood()
	{
		image img = new image(1, true, "file", -1);
		image img2 = new image(2, true, "file2", -1);
		record rec = new record(1,1);
		record rec1 = new record(1,2);
		record rec2 = new record(2,1);
		record rec3 = new record(2,2);
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
			db.getRecordDAO().insert(rec3);
			db.getRecordDAO().insert(rec);
			db.getRecordDAO().insert(rec2);
			db.getRecordDAO().insert(rec1);
			int id1 = db.getImageDAO().insert(img);
			int id2 = db.getImageDAO().insert(img2);
			
			List<image> imgs1 = db.getImageDAO().Search(1);
			List<image> imgs2 = db.getImageDAO().Search(2);
			Assert.assertEquals(1, imgs1.size());
			Assert.assertEquals(1, imgs2.size());
			Assert.assertEquals("file", imgs1.get(0).getFile());
			Assert.assertEquals("file2", imgs2.get(0).getFile());			
		
			
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

