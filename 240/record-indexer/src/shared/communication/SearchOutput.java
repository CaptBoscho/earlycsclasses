package shared.communication;

import java.util.ArrayList;
import java.util.List;


public class SearchOutput {

	private List<SearchOutput2> so2 = new ArrayList<SearchOutput2>();
	private boolean works = true;
	
	public SearchOutput(){}
	public SearchOutput(List<SearchOutput2> so)
	{
		so2 = so;
	}
	
	public void addSearchOutput(SearchOutput2 s)
	{
		so2.add(s);
	}
	
	public List<SearchOutput2> getOutputs()
	{
		return so2;
	}
	
	public void NoWork()
	{
		works = false;
	}
	
	public boolean getWorks()
	{
		return works;
	}
	
	public void FixURL(String host, int port)
	{
		if(works == true)
		{
			for(int i=0; i<so2.size(); i++)
			{
				so2.get(i).FixURL(host, port);
			}
		}
	}
	
	public String toString()
	{
		if(works == false)
		{
			return "FAILED";
		}
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<so2.size(); i++)
		{
			System.out.println("SearchOutput: \n" + so2.get(i).toString());
			sb.append(so2.get(i).toString());
		}
		
		return sb.toString();
	}
	
	public List<String> getURLs() {
		
		List<String> urls = new ArrayList<String>();
		for(int i=0; i<so2.size(); i++)
		{
			
			String u = so2.get(i).getFullURL();
			if (u!=null)
			{
				urls.add(u);
			}
		}
		return urls;
	}
	
	public int getSize()
	{
		return so2.size();
	}
}
