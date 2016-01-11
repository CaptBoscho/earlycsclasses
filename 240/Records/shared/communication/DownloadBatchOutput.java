package shared.communication;

import shared.model.*;

import java.util.List;


public class DownloadBatchOutput {

	private project pro;
	private int batchID = -1;
	private boolean worked = true;
	private String url = null;
	public DownloadBatchOutput()
	{
		
	}
	public DownloadBatchOutput(project n)
	{
		pro = n;
	}
	
	public void setProject(project p)
	{
		pro = p;
	}
	
	public project getProject()
	{
		return pro;
	}
	
	public void NotWork()
	{
		worked = false;
	}
	
	public boolean getWorks()
	{
		return worked;
	}
	
	public void FixURL(String hostname, int port)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("http://");
		sb.append(hostname);
		sb.append(":");
		sb.append(Integer.toString(port));
		sb.append("/");
		url = sb.toString();
	}
	
	public int getBatchID()
	{
		List<image> images = pro.getImages();
		if(images.size() == 0)
		{
			System.out.println("DownloadBatchOutput");
		}
		for(int k=0; k<images.size(); k++)
		{
			if(images.get(k).getAvailable() == true && batchID == -1)
			{
				batchID = images.get(k).getPersonalID();
			}
		}
		return batchID;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if(worked == false)
		{
			sb.append("FAILED");
			return sb.toString();
		}
		
		List<image> images = pro.getImages();
		if(images.size() == 0)
		{
			sb.append("FAILED");
			return sb.toString();
		}
		
		else
		{
			sb.append(Integer.toString(batchID) + '\n');
			sb.append(Integer.toString(pro.getPersonalID()) + '\n');
			sb.append(images.get(0).getFile() + '\n');
			sb.append(Integer.toString(pro.getFirstycoord()) + '\n');
			sb.append(Integer.toString(pro.getRecordheight()) + '\n');
			sb.append(Integer.toString(pro.getRecordsperimage()) + '\n');
			
			List<field> flds = pro.getFields();
			int size = flds.size();
			sb.append(Integer.toString(size) + '\n');
			for(int i=0; i<size; i++)
			{
				sb.append(Integer.toString(flds.get(i).getPersonalID()) + '\n');
				sb.append(Integer.toString(i) + '\n');
				sb.append(flds.get(i).getTitle() + '\n');
				sb.append(url + flds.get(i).getHelphtml() + '\n');
				sb.append(Integer.toString(flds.get(i).getXcoord()) + '\n');
				sb.append(Integer.toString(flds.get(i).getWidth()) + '\n');
				if(flds.get(i).getKnowndata() != null)
				{
					sb.append(url + flds.get(i).getKnowndata() + '\n');
				}
			}
		}
		
		
	
		
		return sb.toString();
	}
	
	
		
	
}
