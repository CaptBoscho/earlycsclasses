package server.database.Dao;

import server.database.*;
import shared.model.project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//import shared.model.*;


public class ProjectDao {

	
	private Database db;
	
	public ProjectDao(Database db) {
		this.db = db;
	}
	
	/*
	 * Gets a list of all the projects in the database.
	 */
	public List<project> getAll() throws DatabaseException {
		
		
		ArrayList<project> result = new ArrayList<project>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select title, recordsperimage, firstycoord, recordheight, "
					+ "personalID from project";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				String t = rs.getString(1);
				int rpi = rs.getInt(2);
				int y = rs.getInt(3);
				int rh = rs.getInt(4);
				int pid = rs.getInt(5);
				
				result.add(new project(t, rpi, y, rh, pid));
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
		return result;	
	}
	
	/*
	 * Gets the information for a specific project
	 */
	public project getProject(int ID) throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		project p = null;
		try {
			String query = "select title, recordsperimage, firstycoord, recordheight, "
					+ "personalID from project";
			stmt = db.getConnection().prepareStatement(query);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
					String t = rs.getString(1);
					int rpi = rs.getInt(2);
					int y = rs.getInt(3);
					int rh = rs.getInt(4);
					int pid = rs.getInt(5);
					
					if(pid == ID)
					{
						p = new project(t, rpi, y, rh, pid);
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
		return p;	
	}
	
	/*
	 * Inserts a user into the database
	 */
	public void insert(project pro) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into project (title, recordsperimage, firstycoord, recordheight) values (?, ?, ?, ?)";
			//System.out.println("Test1");
			
			stmt = db.getConnection().prepareStatement(query);
			//System.out.println("Test2");
			stmt.setString(1, pro.getTitle());
			stmt.setInt(2, pro.getRecordsperimage());
			stmt.setInt(3, pro.getFirstycoord());
			stmt.setInt(4, pro.getRecordheight());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				pro.setPersonalID(id);
			}
			else {
				throw new DatabaseException("Could not insert project");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert project", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	/*
	 * Gets the recordsperimage number in order to update the amount of indexed records
	 * for a user after submitting a batch.
	 */
	public int getNumberRecords(int ID) throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		project p = null;
		try {
			String query = "select title, recordsperimage, firstycoord, recordheight, "
					+ "personalID from project";
			stmt = db.getConnection().prepareStatement(query);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
					String t = rs.getString(1);
					int rpi = rs.getInt(2);
					int y = rs.getInt(3);
					int rh = rs.getInt(4);
					int pid = rs.getInt(5);
					
					if(pid == ID)
					{
						return rpi;
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
		return -1;	
	}
	
	public void update(project pro) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "update project set title = ?, recordsperimage = ?, firstycoord = ?, "
					+ "recordheight = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, pro.getTitle());
			stmt.setInt(2, pro.getRecordsperimage());
			stmt.setInt(3, pro.getFirstycoord());
			stmt.setInt(4, pro.getRecordheight());
			stmt.setInt(5, pro.getPersonalID());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update user");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update user", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	/*
	 * Checks the new project ID to see if it was correctly inserted.
	 * Only ran by ProjectDAO_Test
	 */
	public int insertTest(project pro) {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into project (title, recordsperimage, firstycoord, recordheight) values (?, ?, ?, ?)";
			//System.out.println("Test1");
			
			stmt = db.getConnection().prepareStatement(query);
			//System.out.println("Test2");
			stmt.setString(1, pro.getTitle());
			stmt.setInt(2, pro.getRecordsperimage());
			stmt.setInt(3, pro.getFirstycoord());
			stmt.setInt(4, pro.getRecordheight());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				pro.setPersonalID(id);
				return id;
			}
			else {
				return -1;
			}
		}
		catch (SQLException e) {
			return -1;
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	
	}

}