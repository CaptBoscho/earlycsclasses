package shared.model;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import server.database.Database;
import server.database.DatabaseException;

public class record {
	private List<String> values = new ArrayList<String>();
	private int batchID;
	private int personalID;
	//private int xcoord;
	private int ycoord;
	
	public record()
	{
		//values = new ArrayList<String>();
		batchID = -1;
		//xcoord = -1;
		ycoord = -1;
		personalID = -1;
	}
	
	public record(int bid, int y)
	{
		batchID = bid;
		ycoord = y;
		personalID = y;
	}
	
	//ok, gotta create value model class and everything with it, in the record class not gonna have the
	//xcoord, just gonna have the y, increment it in the for loop from the thign that called record
	//then in values class, gonna have string, recordID, and xcoord.
	
	public record(Element recordElement, int yc, Database db, int bid) throws DatabaseException
	{
		batchID = bid;
		ycoord = yc;
		personalID = yc; //Is this right?
		Element valuesElement = (Element)recordElement.getElementsByTagName("values").item(0);
		NodeList valueElements = valuesElement.getElementsByTagName("value");
		//System.out.println("SIZE: " + valueElements.getLength());
		for(int i=0; i<valueElements.getLength(); i++)
		{
			String vl = (String)valueElements.item(i).getNodeValue();
			//System.out.println(vl);
			Node n = (Node)valueElements.item(i);
			//System.out.println(n.getFirstChild().getNodeValue());
			Value v = new Value(n.getFirstChild().getNodeValue(), i, personalID, batchID);
			//System.out.println((Element)valueElements.item(i).getNodeValue());
			db.getValueDAO().insert(v);
		}
	}
	
	public void setBatchID(int b)
	{
		batchID = b;
	}
	
	public int getBatchID()
	{
		return batchID;
	}
	
	public void setPersonalID(int id)
	{
		personalID = id;
	}
	
	public int getPersonalID()
	{
		return personalID;
	}
	
	/*public void setXcoord(int x)
	{
		xcoord = x;
	}
	
	public void Incrementxcoord()
	{
		xcoord++;
	}
	
	public int getXcoord()
	{
		return xcoord;
	}*/
	
	public void setYcoord(int y)
	{
		ycoord =y;
	}
	
	public void Incrementycoord()
	{
		ycoord++;
	}
	
	public int getYcoord()
	{
		return ycoord;
	}
	
	public void addValues(String s)
	{
		values.add(s);
		/*StringBuilder sb = new StringBuilder(values);
		if(sb.length()>0)
		{
			sb.append(',');
		}
		sb.append(s);
		values = sb.toString();*/
	}
	
	public List<String> getValues()
	{
		return values;
	}

}
