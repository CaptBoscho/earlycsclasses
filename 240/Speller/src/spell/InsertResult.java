package spell;

public class InsertResult {
	
	private int newwords;
	private int newnodes;
	
	InsertResult(){
		newwords = 0;
		newnodes = 0;
	}
	
	public void addWord()
	{
		newwords++;
	}
	
	public void addNode()
	{
		newnodes++;
	}
	
	public int getNCount()
	{
		return newnodes;
	}
	
	public int getWCount()
	{
		return newwords;
	}

}
