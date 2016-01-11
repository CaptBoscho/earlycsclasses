package server.database.Dao;

import server.database.*;
import shared.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//import shared.model.*;


public class RecordDao {

	
	private static Database db;
	
	public RecordDao(Database db) {
		this.db = db;
	}
	/*
	 * 
	 */
	public static List<Integer> Search(int bid, List<String> values, int x) throws DatabaseException
	{
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select ycoord, BatchID from record";
			
			stmt = db.getConnection().prepareStatement(query);
			List<String> strings = new ArrayList<String>();
			List<Integer> result = new ArrayList<Integer>();
			rs = stmt.executeQuery();
			while (rs.next()) {
				
				int y = rs.getInt(1);
				int b = rs.getInt(2);
				//System.out.println(b + " vs. " + bid);
				if(b == bid)
				{
					boolean isthere = db.getValueDAO().Search(y, b, values, x);
					//System.out.println("RecordDAO: " + isthere);
					if(isthere)
					{
						System.out.println("y-coord " + y);
						result.add(y);
					}
				}
			}
			return result;
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
						
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
	}
	
	public void insert(record rec) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into record (ycoord, BatchID) values (?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			//stmt.setString(1, rec.getValues());
			//stmt.setInt(2, rec.getXcoord());
			stmt.setInt(1, rec.getYcoord());
			stmt.setInt(2, rec.getBatchID());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				rec.setPersonalID(id);
			}
			else {
				throw new DatabaseException("Could not insert record");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert record", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	@SuppressWarnings("finally")
	public boolean insertTest(record rec) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		boolean worked = false;
		try {
			String query = "insert into record (ycoord, BatchID) values (?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			//stmt.setString(1, rec.getValues());
			//stmt.setInt(2, rec.getXcoord());
			stmt.setInt(1, rec.getYcoord());
			stmt.setInt(2, rec.getBatchID());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				rec.setPersonalID(id);
				worked = true;
			}
			else {
				//throw new DatabaseException("Could not insert record");
				worked = false;
			}
		}
		catch (SQLException e) {
			//throw new DatabaseException("Could not insert record", e);
			worked = false;
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
			return worked;
		}
	}
	
	public void update(record rec) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "update record set ycoord = ?"
					+ " where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			//stmt.setString(1, rec.getValues());
			//stmt.setInt(2, rec.getXcoord());
			stmt.setInt(1, rec.getYcoord());
			stmt.setInt(2, rec.getPersonalID());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update record");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update record", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	public void delete(record rec) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from record where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, rec.getPersonalID());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete record");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete record", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}

	public boolean SubmitBatch(int Batch, String fvalues)
	{
		try {
			List<String> recs = new ArrayList<String>();
			Scanner s1 = new Scanner(fvalues);
			s1.useDelimiter(";");
			while(s1.hasNext())
			{
				recs.add(s1.next());
			}
			for(int i=0; i<recs.size(); i++)
			{
				//System.out.println(i);
				Scanner s2 = new Scanner(recs.get(i));
				s2.useDelimiter(",");
				int xcoord = 0;
				while(s2.hasNext())
				{
					Value v = new Value(s2.next(), xcoord, i, Batch);
					System.out.println(v.getValue());
					db.getValueDAO().insert(v);
					xcoord++;
				}
				record r = new record(Batch, i);
				insert(r);
			}
			return true;
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
			
		
	}

}