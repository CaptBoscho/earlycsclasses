package shared.communication;

import java.util.ArrayList;
import java.util.List;


public class SearchOutput2 {

	private int batchID = -1;
	private String imageURL = null;
	private List<Integer> recordnumbers = new ArrayList<Integer>();
	private int fieldID = -1;
	
	public SearchOutput2(){}
	public SearchOutput2(int batch, String url, List<Integer> rn, int field)
	{
		batchID = batch;
		imageURL = url;
		recordnumbers = rn;
		fieldID = field;
	}
	
	public List<Integer> getRecordNumbers()
	{
		return recordnumbers;
	}
	
	public int getBatchID()
	{
		return batchID;
	}
	
	public String getImageURL()
	{
		return imageURL;
	}
	
	public int getFieldID()
	{
		return fieldID;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		//System.out.println("SearchOutput2 outside");
		for(int i=0; i<recordnumbers.size(); i++)
		{
			//System.out.println("SearchOutput2");
			sb.append(Integer.toString(batchID) + '\n');
			sb.append(imageURL + '\n');
			sb.append(Integer.toString(recordnumbers.get(i)) + '\n');
			sb.append(Integer.toString(fieldID) + '\n');
		}
		
		return sb.toString();
	}
}