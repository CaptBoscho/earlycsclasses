package shared.model;

import server.DataImporter;
import server.database.Database;
import server.database.DatabaseException;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class image {
	private int parentProjectID = -1;
	private String file = null;
	private boolean available = true;
	private List<Integer> records = new ArrayList<Integer>();
	
	private int personalID = -1;
	
	public image(){}
	public image(Element imageElement, Database db, int pid, int bid) throws DatabaseException
	{
		/*parentProjectID = 0;
		file = new String();
		available = true;
		records = new ArrayList<Integer>();*/
		personalID = bid;
		parentProjectID = pid;
		//file = DataImporter.getValue((Element)imageElement.getElementsByTagName("file").item(0));
		Element recordsElement = (Element)imageElement.getElementsByTagName("records").item(0);
		NodeList recordElements = imageElement.getElementsByTagName("record");
		for (int i=0; i<recordElements.getLength(); i++)
		{
			record rec = new record((Element)recordElements.item(i), i, db, personalID);
			db.getRecordDAO().insert(rec);
		}
		//Do I do this for the rest of them?
	}
	
	public image(int pid, boolean av, String f, int personal)
	{
		parentProjectID = pid;
		available = av;
		file = f;
		personalID = personal;
	}
	
	public void setParentProjectID(int i)
	{
		parentProjectID = i;
	}
	
	public int getParentProjectID()
	{
		return parentProjectID;
	}
	
	public void setPersonalID(int i)
	{
		personalID = i;
	}
	
	public int getPersonalID()
	{
		return personalID;
	}
	
	public void setFile(String s)
	{
		file = s;
	}
	
	public String getFile()
	{
		return file;
	}
	
	public void setNotAvailable()
	{
		available = false;
	}
	
	public void setAvailable()
	{
		available = true;
	}
	
	public boolean getAvailable()
	{
		return available;
	}
	
	public void addRecord(int r)
	{
		records.add(r);
	}
	
	public List<Integer> getRecords()
	{
		return records;
	}
}
