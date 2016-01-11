package server.database.Dao;

import server.database.*;
import shared.model.field;
import shared.model.image;
import shared.model.project;
import shared.communication.SearchOutput;
import shared.communication.SearchOutput2;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

//import shared.model.*;


public class FieldDao {

	
	private Database db;
	
	public FieldDao(Database db) {
		this.db = db;
	}
	
	
	public List<field> getAll(int projectID) throws DatabaseException {
		
		ArrayList<field> result = new ArrayList<field>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select title, xcoord, width, helphtml, "
					+ "knowndata, parentProjectID, personalID from field";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				String t = rs.getString(1);
				int x = rs.getInt(2);
				int width = rs.getInt(3);
				String hhtml = rs.getString(4);
				String kd = rs.getString(5);
				int parentID = rs.getInt(6);
				int personal = rs.getInt(7);
				
				int ppID = 0;
				if(projectID == 0)
				{
					ppID = parentID;
				}
				else
				{
					ppID = projectID;
					System.out.println(parentID);
				}
				if(parentID == ppID)
				{
					field f = new field(t, x, width, hhtml, kd, parentID);
					f.setPersonalID(personal);
					result.add(f);
				}
			}
			if(result.size() == 0)
			{
				return null;
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
	
	public int LowestFieldID(int projectID)
	{
		Set<Integer> ids = new TreeSet<Integer>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {

			String query = "select title, xcoord, width, helphtml, "
					+ "knowndata, parentProjectID, personalID from field";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				String t = rs.getString(1);
				int x = rs.getInt(2);
				int width = rs.getInt(3);
				String hhtml = rs.getString(4);
				String kd = rs.getString(5);
				int parentID = rs.getInt(6);
				int pID = rs.getInt(7);
				//System.out.println(pID);
				if(parentID == projectID)
				{
					ids.add(pID);					
				}
			}
			if(ids.size() == 0)
			{
				return -1;
			}
			Iterator<Integer> it = ids.iterator();
			int first = it.next();
			return first;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
			
	}
	
public int getNumber(int projectID) throws DatabaseException {
		
		ArrayList<field> result = new ArrayList<field>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
	
		try {
			String query = "select title, xcoord, width, helphtml, "
					+ "knowndata, parentProjectID, personalID from field";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			int amount = 0;
			while (rs.next()) {
				String t = rs.getString(1);
				int x = rs.getInt(2);
				int width = rs.getInt(3);
				String hhtml = rs.getString(4);
				String kd = rs.getString(5);
				int parentID = rs.getInt(6);
				int personal = rs.getInt(7);
				
				
				if(parentID == projectID)
				{
					amount++;
				}
			}
			return amount;
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
	
	public List<SearchOutput2> Search(int fID, List<String> values) throws DatabaseException
	{
		ArrayList<field> result = new ArrayList<field>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select title, xcoord, width, helphtml, "
					+ "knowndata, parentProjectID, personalID from field";
			stmt = db.getConnection().prepareStatement(query);
			List<SearchOutput2> listofsearches = new ArrayList<SearchOutput2>();
			rs = stmt.executeQuery();
			while (rs.next()) {
				String t = rs.getString(1);
				int x = rs.getInt(2);
				int width = rs.getInt(3);
				String hhtml = rs.getString(4);
				String kd = rs.getString(5);
				int parentID = rs.getInt(6);
				int pID = rs.getInt(7);
				//System.out.println(pID);
				if(pID == fID)
				{
					int lowest = db.getFieldDAO().LowestFieldID(parentID);
					if (lowest == -1)
					{
						System.out.println("error FieldDAO 147");
					}
					List<image> imgs = db.getImageDAO().Search(parentID);
					System.out.println("images: " + imgs.size());
					for(int i=0; i<imgs.size(); i++)
					{
						x = fID - lowest;
						int BatchID = imgs.get(i).getPersonalID();
						String uri = imgs.get(i).getFile();
						List<Integer> rec_nums = db.getRecordDAO().Search(BatchID, values, x);
						//System.out.println("Size " + rec_nums.size());
						SearchOutput2 so = new SearchOutput2(BatchID, uri, rec_nums, fID);
						listofsearches.add(so);
					}
					
				}
			}
			return listofsearches;
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
	
	public void insert(field fld) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into field (title, xcoord, width, helphtml, knowndata,"
					+ " parentProjectID) values (?, ?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, fld.getTitle());
			stmt.setInt(2, fld.getXcoord());
			stmt.setInt(3, fld.getWidth());
			stmt.setString(4, fld.getHelphtml());
			stmt.setString(5, fld.getKnowndata());
			stmt.setInt(6, fld.getParentProjectID());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				fld.setPersonalID(id);
			}
			else {
				throw new DatabaseException("Could not insert field");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert field", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	/*
	 * Allows the JUnit Tests to discover if the insert function works.
	 */
	public int insertTest(field fld) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into field (title, xcoord, width, helphtml, knowndata,"
					+ " parentProjectID) values (?, ?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, fld.getTitle());
			stmt.setInt(2, fld.getXcoord());
			stmt.setInt(3, fld.getWidth());
			stmt.setString(4, fld.getHelphtml());
			stmt.setString(5, fld.getKnowndata());
			stmt.setInt(6, fld.getParentProjectID());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				fld.setPersonalID(id);
				return id;
			}
			else {
				//throw new DatabaseException("Could not insert field");
				return 0;
			}
		}
		catch (SQLException e) {
			//throw new DatabaseException("Could not insert field", e);
			return 0;
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	public void update(field fld) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "update field set title = ?, xcoord = ?, width = ?, "
					+ "helphtml = ?, knowndata = ?, parentProjectID = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, fld.getTitle());
			stmt.setInt(2, fld.getXcoord());
			stmt.setInt(3, fld.getWidth());
			stmt.setString(4, fld.getHelphtml());
			stmt.setString(5, fld.getKnowndata());
			stmt.setInt(6, fld.getParentProjectID());
			stmt.setInt(7, fld.getPersonalID());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update field");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update field", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	public void delete(field fld) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from field where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, fld.getPersonalID());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete user");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete user", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}

}