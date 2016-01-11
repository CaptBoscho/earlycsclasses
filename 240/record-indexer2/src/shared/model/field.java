package shared.model;

import server.DataImporter;

import org.w3c.dom.Element;


public class field {
	private String title = null;
	private int xcoord = -1;
	private int width = -1;
	private String helphtml = null;
	private String knowndata = null;
	private int parentProjectID = -1;
	private int personalID = -1;
	
	public field(String t, int x, int w, String hhtml, String kd, int ppID)
	{
		setTitle(t);
		setXcoord(x);
		setWidth(w);
		setHelphtml(hhtml);
		setKnowndata(kd);
		setParentProjectID(ppID);
	}
	
	public field(Element fieldElement, int pid)
	{
		/*title = new String();
		xcoord = 0;
		width = 0;
		helphtml = new String();
		knowndata = new String();*/
		
		title = DataImporter.getValue((Element)fieldElement.getElementsByTagName("title").item(0));
		
		xcoord = Integer.parseInt(DataImporter.getValue(
					(Element)fieldElement.getElementsByTagName("xcoord").item(0)));
		
		width = Integer.parseInt(DataImporter.getValue(
				(Element)fieldElement.getElementsByTagName("width").item(0)));
		
		helphtml = "Records/" + DataImporter.getValue((Element)fieldElement.getElementsByTagName("helphtml").item(0));
		
		knowndata = "Records/" + DataImporter.getValue((Element)fieldElement.getElementsByTagName("knowndata").item(0));
		parentProjectID = pid;//do i do this for parentProjectID??
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
	
	public void setTitle(String s)
	{
		title = s;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setXcoord(int i)
	{
		xcoord = i;
	}
	
	public int getXcoord()
	{
		return xcoord;
	}
	
	public void setWidth(int i)
	{
		width = i;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setHelphtml(String s)
	{
		helphtml = s;
	}
	
	public String getHelphtml()
	{
		return helphtml;
	}
	
	public void setKnowndata(String s)
	{
		knowndata = s;
	}
	
	public String getKnowndata()
	{
		return knowndata;
	}
	

}
