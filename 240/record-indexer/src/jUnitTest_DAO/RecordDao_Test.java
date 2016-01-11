package jUnitTest_DAO;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import server.database.Database;
import server.database.DatabaseException;
import shared.model.Value;
import shared.model.record;

public class RecordDao_Test {

	private static Database db;

	public static void main(String[] args) {
		try {
			Database.initialize();
			db = new Database();
			DropandCreate();
			InsertGood();
			DropandCreate();
			SubmitBatchGood();
			DropandCreate();
			SearchGood();
			DropandCreate();
			SearchBad();
			DropandCreate();
			
		} catch (DatabaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void InsertGood()
	{
		record rec = new record(1,1);
		try {
			boolean worked = db.getRecordDAO().insertTest(rec);
			//System.out.println(worked);
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void InsertBad()
	{
		//what makes it bad?
	}
	
	public static void SubmitBatchGood()
	{
		int batchID = 2;
		String fvalues = "Fred,Jones,18;Johnny,Ways,24;Will,Die,102";
		boolean check = db.getRecordDAO().SubmitBatch(batchID, fvalues);
		//System.out.println(check);
		
	}
	
	public static void SearchGood()
	{
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
			List<String> values = new ArrayList<String>();
			values.add("dog");
			values.add("fish");
			values.add("hamster");
			List<String> values2 = new ArrayList<String>();
			values2.add("monkey");
			List<Integer> working1 = db.getRecordDAO().Search(2, values, 1);
			int row = working1.get(0);
			Assert.assertEquals(1, row);
			List<Integer> working2 = db.getRecordDAO().Search(1, values, 5);
			int row2 = working2.get(0);
			Assert.assertEquals(1, row2);
			List<Integer> working3 = db.getRecordDAO().Search(1, values, 4);
			int row3 = working3.get(0);
			Assert.assertEquals(2, row3);
		
			
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void SearchBad()
	{
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
		
			List<String> values2 = new ArrayList<String>();
			values2.add("monkey");
			
			List<Integer> working4 = db.getRecordDAO().Search(1, values2, 1);
			int size = working4.size();
			Assert.assertEquals(0, size);
			
		
			
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


