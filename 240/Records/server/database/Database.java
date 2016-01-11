package server.database;

import java.io.*;
import java.sql.*;
import java.util.logging.*;

import server.database.Dao.*;


public class Database {
	
	private static final String DATABASE_DIRECTORY = "database";
	/*private static final String DATABASE_FILE = "contactmanager.sqlite";
	private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY +
												File.separator + DATABASE_FILE;*/
	
	private static final String DATABASE_FILE = "db" + File.separator + "P1tables.sqlite.sqlite"; //change to file location
	private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_FILE;

	/*private static Logger logger;
	
	static {
		logger = Logger.getLogger("contactmanager");
	}*/

	public static void initialize() throws DatabaseException {
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch(ClassNotFoundException e) {
			
			DatabaseException serverEx = new DatabaseException("Could not load database driver", e);
			
			//logger.throwing("server.database.Database", "initialize", serverEx);

			throw serverEx; 
		}
	}

	//private ContactsDAO contactsDAO; //user, project, image
	private UserDao userDAO;
	private ProjectDao projectDAO;
	private FieldDao fieldDAO;
	private ImageDao imageDAO;
	private RecordDao recordDAO;
	private ValueDao valueDAO;
	
	private Connection connection;

	
	
	public Database() {
		//contactsDAO = new ContactsDAO(this); //make instances of each DAO
		userDAO = new UserDao(this);
		projectDAO = new ProjectDao(this);
		fieldDAO = new FieldDao(this);
		imageDAO = new ImageDao(this);
		recordDAO = new RecordDao(this);
		valueDAO = new ValueDao(this);
		String dbName = "db" + File.separator + "P1tables.sqlite.sqlite";
		String connectionURL = "jdbc:sqlite:" + dbName;
		connection = null;
		try {
			connection = DriverManager.getConnection(connectionURL);
			
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public UserDao getUserDAO()
	{
		return userDAO;
	}
	
	public ProjectDao getProjectDAO()
	{
		return projectDAO;
	}
	
	public FieldDao getFieldDAO()
	{
		return fieldDAO;
	}
	
	public ImageDao getImageDAO()
	{
		return imageDAO;
	}
	
	public RecordDao getRecordDAO()
	{
		return recordDAO;
	}
	
	public ValueDao getValueDAO()
	{
		return valueDAO;
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void startTransaction() throws DatabaseException {
		try {
			assert (connection == null);			
			connection = DriverManager.getConnection(DATABASE_URL);
			connection.setAutoCommit(false);
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not connect to database. Make sure " + 
				DATABASE_FILE + " is available in ./" + DATABASE_DIRECTORY, e);
			
		}
	}
	
	public void endTransaction(boolean commit) {
		if (connection != null) {		
			try {
				if (commit) {
					connection.commit();
				}
				else {
					connection.rollback();
				}
			}
			catch (SQLException e) {
				System.out.println("Could not end transaction");
				e.printStackTrace();
			}
			finally {
				safeClose(connection);
				connection = null;
			}
		}
	}
	
	public static void safeClose(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(PreparedStatement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
}