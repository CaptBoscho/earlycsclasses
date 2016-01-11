package server.database.Dao;

import server.database.*;
import shared.model.image;
import shared.model.project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//import shared.model.*;


public class ImageDao {

	
	private Database db;
	
	public ImageDao(Database db) {
		this.db = db;
	}

	public List<image> Search(int idfromfield) throws DatabaseException//working here
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<image> imgs = new ArrayList<image>();
		try {
			String query = "select ParentProjectID, available, file, personalID from image";
			stmt = db.getConnection().prepareStatement(query);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
					int pid = rs.getInt(1);
					boolean av = rs.getBoolean(2);
					String f = rs.getString(3);
					int personal = rs.getInt(4);
					
					if(pid == idfromfield)
					{
						image mg = new image(pid, av, f, personal);
						imgs.add(mg);
					}
				}
			return imgs;
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
	
	//@SuppressWarnings("finally")
	public int insert(image img) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;	
		int id = -1;
		try {
			
			String query = "insert into image (ParentProjectID, available, file) values (?, ?, ?)";
			
			stmt = db.getConnection().prepareStatement(query);
		
			stmt.setInt(1, img.getParentProjectID());
			stmt.setBoolean(2, img.getAvailable());
			stmt.setString(3, img.getFile());
			
			if (stmt.executeUpdate() == 1) {
				
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				id = keyRS.getInt(1);
				img.setPersonalID(id);
				//System.out.println(id);
				return id;
			}
			else {
				throw new DatabaseException("Could not insert image");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert image", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
			
		}
		
	}
	
	public String getSampleImage(int proID) throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		project p = null;
		String uri = null;
		try {
			String query = "select ParentProjectID, available, file, "
					+ "personalID from image";
			stmt = db.getConnection().prepareStatement(query);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
					int ppi = rs.getInt(1);
					boolean avail = rs.getBoolean(2);
					String f = rs.getString(3);
					int pid = rs.getInt(4);
					
					if(ppi == proID)
					{
						return f;
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
		return uri;
	}
	
	public int getProjectID(int batchID) throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		project p = null;
		String uri = null;
		try {
			String query = "select ParentProjectID, available, file, "
					+ "personalID from image";
			stmt = db.getConnection().prepareStatement(query);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
					int ppi = rs.getInt(1);
					boolean avail = rs.getBoolean(2);
					String f = rs.getString(3);
					int pid = rs.getInt(4);
					
					if(pid == batchID)
					{
						return ppi;
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
	
	public void updateAvailable(int bid) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "update image set available = ?"
					+ " where personalID = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setBoolean(1, false);
			stmt.setInt(2, bid);
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update image");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update image", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}
	
	public void delete(image pro) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from image where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, pro.getPersonalID());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete image");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete image", e);
		}
		finally {
			Database.safeClose(stmt);
		}
	}

}