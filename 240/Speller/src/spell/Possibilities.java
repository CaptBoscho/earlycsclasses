package spell;

import java.util.ArrayList;
import java.util.List;

public class Possibilities {

	private List<String> answers;
	private List<String> poss;
	
	Possibilities(){
		answers = new ArrayList<String>();
		poss = new ArrayList<String>();
	}
	
	public void addAnswer(String s)
	{
		answers.add(s);
	}
	
	public void addPoss(String s)
	{
		poss.add(s);
	}
	
	public String getAnswer(int index)
	{
		return answers.get(index);
	}
	
	public String getPoss(int index)
	{
		return poss.get(index);
	}
	
	public int getAnswerCount()
	{
		return answers.size();
	}
	
	public int getPossCount()
	{
		return poss.size();
	}
}
