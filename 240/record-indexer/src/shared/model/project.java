package shared.model;

import server.DataImporter;
import server.database.Database;
import server.database.DatabaseException;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;

public class project {
	
	private String title = null;
	private int firstycoord = -1;
	private int recordsperimage = -1;
	private int recordheight = -1;
	
	private int personalID = -1;
	List<field> fielding = new ArrayList<field>();
	List<image> images = new ArrayList<image>();
	
	public project(String t, int rpi, int y, int rh, int pid)
	{
		setTitle(t);
		setRecordsperimage(rpi);
		setFirstycoord(y);
		setRecordheight(rh);
		setPersonalID(pid);
	}
	
	public project(Element projectElement, Database db, int pid) throws DatabaseException
	{
		
		title = DataImporter.getValue((Element)projectElement.getElementsByTagName("title").item(0));
		recordsperimage = Integer.parseInt(DataImporter.getValue(
						(Element)projectElement.getElementsByTagName("recordsperimage").item(0)));
		firstycoord = Integer.parseInt(DataImporter.getValue(
						(Element)projectElement.getElementsByTagName("firstycoord").item(0)));
		recordheight = Integer.parseInt(DataImporter.getValue(
				(Element)projectElement.getElementsByTagName("recordheight").item(0)));
		
		personalID = pid;
		
		Element fieldsElement = (Element)projectElement.getElementsByTagName("fields").item(0);
		NodeList fieldElements = fieldsElement.getElementsByTagName("field");
		for(int i=0; i<fieldElements.getLength(); i++)
		{
			field f = new field((Element)fieldElements.item(i), personalID);
			fielding.add(f);
			db.getFieldDAO().insert(f);//add field to database
		}
		
		Element imagesElement = (Element)projectElement.getElementsByTagName("images").item(0);
		NodeList imageElements = imagesElement.getElementsByTagName("image");
		//System.out.println("amount of images" + imageElements.getLength());
		for(int i=0; i<imageElements.getLength(); i++)
		{
			Element imageElement = (Element) imageElements.item(i);
			String file = "Records/" + DataImporter.getValue((Element)imageElement.getElementsByTagName("file").item(0));
			//System.out.println(file);
			image image1 = new image();
			image1.setParentProjectID(personalID);
			image1.setAvailable();
			image1.setFile(file);
			int batchID = db.getImageDAO().insert(image1);
			//System.out.println("project " + batchID);
			image img = new image(imageElement, db, personalID, batchID);
			images.add(img);
			//image img = new image((Element)imageElements.item(i), db, personalID);
			//images.add(img);
			//db.getImageDAO().insert(img);//add image to database
		}
		
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
	
	public void setRecordsperimage(int i)
	{
		recordsperimage = i;
	}
	
	public int getRecordsperimage()
	{
		return recordsperimage;
	}
	
	public void setFirstycoord(int i)
	{
		firstycoord = i;
	}
	
	public int getFirstycoord()
	{
		return firstycoord;
	}
	
	public void setRecordheight(int i)
	{
		recordheight = i;
	}
	
	public int getRecordheight()
	{
		return recordheight;
	}
	
	public void setFields(List<field> f)
	{
		fielding = f;
	}
	
	public List<field> getFields()
	{
		return fielding;
	}
	
	public void setImages(List<image> img)
	{
		images = img;
	}
	
	public List<image> getImages()
	{
		return images;
	}
	
	/*public void setFieldID(int f)
	{
		fieldID = f;
	}
	
	public int getFieldID()
	{
		return fieldID;
	}
	
	public void setImageID(int i)
	{
		imageID = i;
	}
	
	public int getImageID()
	{
		return imageID;
	}
	
	public void addField(int f)
	{
		fields.add(f);
	}
	
	public List<Integer> getFields()
	{
		return fields;
	}*/
}
