package shared.communication;

import shared.model.*;

import java.util.ArrayList;
import java.util.List;


public class getFieldsOutput {

	List<field> fields = new ArrayList<field>();
	private boolean works = true;
	
	public getFieldsOutput(){}
	public getFieldsOutput(List<field> in)
	{
		fields = in;
	}
	
	public void setFields(List<field> in)
	{
		fields = in;
	}
	
	public void addField(field f)
	{
		fields.add(f);
	}
	
	public List<field> getFields()
	{
		return fields;
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
		for(int i=0; i<fields.size(); i++)
		{
			sb.append(Integer.toString(fields.get(i).getParentProjectID()) + '\n');
			sb.append(Integer.toString(fields.get(i).getPersonalID()) + '\n');
			sb.append(fields.get(i).getTitle() + '\n');
		}
		
		return sb.toString();
	}
}
