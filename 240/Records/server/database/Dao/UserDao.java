package server.database.Dao;

import server.database.*;
import shared.model.User;

import java.sql.*;

//import shared.model.*;


public class UserDao {

	
	private Database db;
	
	public UserDao(Database db) {
		this.db = db;
	}
	
	/*
	public List<User> getAll() throws DatabaseException {
		
		
		ArrayList<User> result = new ArrayList<User>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select id, name, phone, address, email, url from contact";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String phone = rs.getString(3);
				String address = rs.getString(4);
				String email = rs.getString(5);
				String url = rs.getString(6);

				result.add(new User(id, name, phone, address, email, url));
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Contacts", "getAll", serverEx);
			
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(stmt);
		}

		logger.exiting("server.database.Contacts", "getAll");
		
		return result;	
	}*/
	
	public User validate(String usname, String pw) throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			String query = "select username, password, firstname, lastname, email, "
					+ "indexedrecords, batchID from user";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			User u = null;
			while (rs.next())
			{
				String uname = rs.getString(1);
				String pword = rs.getString(2);
				String fname = rs.getString(3);
				String lname = rs.getString(4);
				String mail = rs.getString(5);
				int ir = rs.getInt(6);
				int BID = rs.getInt(7);
				
				System.out.println(uname + " vs. " + usname);
				if(uname.equals(usname) && pword.equals(pw))
				{
					System.out.println("userDAO 84");
					u = new User(uname, pword, fname, lname, mail, ir, BID);
					return u;
				}
				
			}
			return u;
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Could not return user", e);
		}
	}
	
	public void insert(User user) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;		
		try {
			String query = "insert into user (username, password, firstname, lastname, email,"
					+ " indexedrecords, batchID) values (?, ?, ?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstname());
			stmt.setString(4, user.getLastname());
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getIndexedrecords());
			stmt.setInt(7, user.getBatchID());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				user.setPersonalID(id);
			}
			else {
				throw new DatabaseException("Could not insert user");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert user", e);
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
		}
	}
	
	@SuppressWarnings("finally")
	public boolean insertTest(User user) throws DatabaseException {
		PreparedStatement stmt = null;
		ResultSet keyRS = null;	
		boolean worked = false;
		try {
			String query = "insert into user (username, password, firstname, lastname, email,"
					+ " indexedrecords, batchID) values (?, ?, ?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstname());
			stmt.setString(4, user.getLastname());
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getIndexedrecords());
			stmt.setInt(7, user.getBatchID());
			if (stmt.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				user.setPersonalID(id);
				worked = true;
			}
			else {
				worked = false;
			}
		}
		catch (SQLException e) {
			worked = false;
		}
		finally {
			Database.safeClose(stmt);
			Database.safeClose(keyRS);
			return worked;
		}
	}
	
	public void updateBatch(int bid, String usname) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "update user set batchID = ?"
					+ " where username = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, bid);
			stmt.setString(2, usname);
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
	
	public void updateIndexedRecords(int ir, String usname) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "update user set indexedrecords = ?"
					+ " where username = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, ir);
			stmt.setString(2, usname);
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
	
	public boolean getBatchID(String usname)
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			String query = "select username, password, firstname, lastname, email, "
					+ "indexedrecords, batchID from user";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			User u = null;
			while (rs.next())
			{
				String uname = rs.getString(1);
				String pword = rs.getString(2);
				String fname = rs.getString(3);
				String lname = rs.getString(4);
				String mail = rs.getString(5);
				int ir = rs.getInt(6);
				int BID = rs.getInt(7);
				
				if(uname.equals(usname))
				{
					if(BID != -1)
					{
						return false;
					}
					else
					{
						return true;
					}
				}
				
			}
			return false;
		}
		catch(SQLException e)
		{
			return false;
			//throw new DatabaseException("Could not return user", e);
		}
	}
	
	public int getIndexedRecords(String usname) throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			String query = "select username, password, firstname, lastname, email, "
					+ "indexedrecords, batchID from user";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			User u = null;
			while (rs.next())
			{
				String uname = rs.getString(1);
				String pword = rs.getString(2);
				String fname = rs.getString(3);
				String lname = rs.getString(4);
				String mail = rs.getString(5);
				int ir = rs.getInt(6);
				int BID = rs.getInt(7);
				
				if(uname.equals(usname))
				{
					return ir;
				}
				
			}
			return -1;
		}
		catch(SQLException e)
		{
			throw new DatabaseException("Could not return user", e);
		}
	}
	
	/*
	public void update(User user) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "update user set username = ?, password = ?, firstname = ?, "
					+ "lastname = ?, email = ?, indexedrecords = ?, batchID = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstname());
			stmt.setString(4, user.getLastname());
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getIndexedrecords());
			stmt.setInt(7, user.getBatchID());
			stmt.setInt(8, user.getPersonalID());
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
	
	public void delete(User user) throws DatabaseException {
		PreparedStatement stmt = null;
		try {
			String query = "delete from user where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, user.getPersonalID());
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
	}*/

}