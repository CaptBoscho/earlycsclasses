package shared.model;

import org.w3c.dom.Element;

import server.DataImporter;

public class Value {

	private String realvalue = null;
	private int xcoord = -1;
	private int recordID = -1;
	private int personalID = -1;
	private int batchID = -1;
	
	public Value(Element valueElement, int x, int id, int bid)
	{
		batchID = bid;
		xcoord = x;
		recordID = id;
		realvalue = DataImporter.getValue((Element)valueElement.getElementsByTagName("value").item(0));
	}
	
	public Value(String s, int x, int rID, int bid)
	{
		batchID = bid;
		realvalue = s;
		xcoord = x;
		recordID = rID;
	}
	
	public void setBatchID(int bid)
	{
		batchID = bid;
	}
	
	public int getBatchID()
	{
		return batchID;
	}
	
	public void setValue(String v)
	{
		realvalue = v;
	}
	
	public String getValue()
	{
		return realvalue;
	}
	
	public void setXcoord(int x)
	{
		xcoord = x;
	}
	
	public int getXcoord()
	{
		return xcoord;
	}
	
	public void setRecordID(int r)
	{
		recordID = r;
	}
	
	public int getRecordID()
	{
		return recordID;
	}
	
	public void setPersonalID(int id)
	{
		personalID = id;
	}
	
	public int getPersonalID()
	{
		return personalID;
	}
}
