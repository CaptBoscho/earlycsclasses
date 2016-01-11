package shared.communication;

import java.util.ArrayList;
import java.util.List;

import shared.model.*;

public class getProjectsOutput {
	
	private List<project> projects = new ArrayList<project>();
	private boolean works = true;
	/**
	 * Constructor
	 */
	public getProjectsOutput(){}
	public getProjectsOutput(List<project> p)
	{
		projects = p;
	}
	
	public void setProjects(List<project> p)
	{
		projects = p;
	}
	
	public void addProject(project a)
	{
		projects.add(a);
	}
	
	public List<project> getProjects()
	{
		return projects;
	}
	
	public project getOneProject(int i)
	{
		return projects.get(i);
	}
	
	public void NoWorks()
	{
		works = false;
	}
	
	public String toString()
	{
		if(works == false)
		{
			return "FAILED";
		}
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<projects.size(); i++)
		{
			sb.append(Integer.toString(projects.get(i).getPersonalID()) + '\n');
			sb.append(projects.get(i).getTitle() + '\n');
		}
		
		return sb.toString();
	}

}
