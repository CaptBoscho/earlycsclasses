/*package server;

import shared.model.*;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;


public class IndexerData {

	private List<User> users = new ArrayList<User>();
	private List<project> projects = new ArrayList<project>();
	
	public IndexerData(Element root)
	{
		ArrayList<Element> rootElements = DataImporter.getChildElements(root);
		
		ArrayList<Element> userElements = DataImporter.getChildElements(rootElements.get(0));
		
		for(Element userElement : userElements)
		{
			users.add(new User(userElement));
		}
		
		ArrayList<Element> projectElements = DataImporter.getChildElements(rootElements.get(1));
		for(Element projectElement : projectElements)
		{
			projects.add(new project(projectElement));
		}
	}
}*/
