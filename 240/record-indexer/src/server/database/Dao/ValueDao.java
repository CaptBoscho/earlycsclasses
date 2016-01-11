package server.database.Dao;

import server.database.*;
import shared.model.Value;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//import shared.model.*;


public class ValueDao {

	
	private static Database db;
	
	public ValueDao(Database db) {
		this.db = db;
	}
	
	public boolean Search(int rid, int bid, List<String> values, int x) throws DatabaseException
	{
		//List<Integer> result = new ArrayList<Integer>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> strings = new ArrayList<String>();
		try {
			String query = "select value, xcoord, recordID, BatchID from value";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				String v = rs.getString(1);
				int xcoord = rs.getInt(2);
				int recID = rs.getInt(3);
				int b = rs.getInt(4);
				
				if(b == bid && recID == rid && x == xcoord)
				{
					for(int i=0; i<values.size(); i++)
					{
						v = v.toLowerCase();
						String val = values.get(i).toLowerCase();
						if(v.equals(val))
						{
							return true;
						}
					}
				}
			}
			
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
						
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}
		return false;
	}
	
	public void insert(Value val) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into value (value, xcoord, recordID, BatchID)"
					+ " values (?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, val.getValue());
			stmt.setInt(2, val.getXcoord());
			stmt.setInt(3, val.getRecordID());
			stmt.setInt(4, val.getBatchID());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				val.setPersonalID(id); //Do i need this?
			}
			else {
				throw new DatabaseException("Could not insert value");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert value", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	@SuppressWarnings("finally")
	public boolean insertTest(Value val) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;	
		boolean worked = false;
		try {
			String query = "insert into value (value, xcoord, recordID, BatchID)"
					+ " values (?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, val.getValue());
			stmt.setInt(2, val.getXcoord());
			stmt.setInt(3, val.getRecordID());
			stmt.setInt(4, val.getBatchID());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				val.setPersonalID(id); //Do i need this?
				worked = true;
			}
			else {
				//throw new DatabaseException("Could not insert value");
				worked = false;
			}
		}
		catch (SQLException e) {
			//throw new DatabaseException("Could not insert value", e);
			worked = false;
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
			return worked;
		}
	}

}