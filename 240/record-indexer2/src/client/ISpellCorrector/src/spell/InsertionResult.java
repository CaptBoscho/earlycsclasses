package client.ISpellCorrector.src.spell;
public class InsertionResult {

	private int newnodes;
	private int newwords;
	
	public InsertionResult()
	{
		newnodes = 0;
		newwords = 0;
	}
	
	public void addNode()
	{
		newnodes++;
	}
	
	public void addWord()
	{
		newwords++;
	}
	
	public int getAddedNodes()
	{
		return newnodes;
	}
	
	public int getAddedWords()
	{
		return newwords;
	}
	
	public void Adjust()
	{
		newnodes--;
		newwords--;
	}
}