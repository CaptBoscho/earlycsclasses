package shared.communication;

import shared.model.*;

import java.util.List;


public class DownloadBatchOutput {

	private project pro;
	private int batchID = -1;
	private boolean worked = true;
	private String url = null;
	private String helphtml = null;
	private int numberofrecords;
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
	
	public int getProID()
	{
		return pro.getPersonalID();
	}
	
	public void NotWork()
	{
		worked = false;
	}
	
	public boolean getWorks()
	{
		return worked;
	}
	
	
	public int getNumofRecords()
	{
		return pro.getRecordsperimage();
	}
	
	public List<field> getFields()
	{
		return pro.getFields();
	}
	
	public String getUrl()
	{
		List<image> images = pro.getImages();
		int index = -1;
		int ii = 0;
		while(index == -1)
		{
			System.out.println("geturl ii = " + ii);
			if(images.get(ii).getAvailable())
			{
				index = ii;
			}
			ii++;
		}
		
		String nurl = url + images.get(index).getFile();
		return nurl;
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
		
		for(int i=0; i<pro.getFields().size(); i++)
		{
			String hh = url + pro.getFields().get(i).getHelphtml();
			String kd = url + pro.getFields().get(i).getKnowndata();
			pro.getFields().get(i).setHelphtml(hh);
			pro.getFields().get(i).setKnowndata(kd);
		}
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
			int index = -1;
			int ii = 0;
			while(index == -1)
			{
				System.out.println("geturl ii = " + ii);
				if(images.get(ii).getAvailable())
				{
					index = ii;
				}
				ii++;
			}
			
			sb.append(Integer.toString(batchID) + '\n');
			sb.append(Integer.toString(pro.getPersonalID()) + '\n');
			sb.append(url + images.get(index).getFile() + '\n');
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
				if(flds.get(i).getKnowndata().equals("Records/null"))
				{
				}
				else
				{
					sb.append(url + flds.get(i).getKnowndata() + '\n');
				}
			}
		}
		
		
	
		
		return sb.toString();
	}
	
	
		
	
}
