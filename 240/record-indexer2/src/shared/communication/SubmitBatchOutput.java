package shared.communication;

public class SubmitBatchOutput {

	private boolean worked = true;
	
	public SubmitBatchOutput(){}
	public SubmitBatchOutput(boolean w)
	{
		worked = w;
	}
	
	public void NoWork()
	{
		worked = false;
	}
	
	public boolean getBool()
	{
		return worked;
	}
	
	public String toString()
	{
		if(worked == false)
		{
			return "FAILED";
		}
		else
		{
			return "TRUE";
		}
	}
}
