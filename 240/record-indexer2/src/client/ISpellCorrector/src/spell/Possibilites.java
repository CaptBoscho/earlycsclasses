package client.ISpellCorrector.src.spell;

import java.util.ArrayList;
import java.util.List;

public class Possibilites {
	private List<String> answer;
	private List<String> poss;
	
	public Possibilites()
	{
		answer = new ArrayList<String>();
		poss = new ArrayList<String>();
	}
	
	public void addAnswer(String s)
	{
		answer.add(s);
	}
	
	public void addPoss(String s)
	{
		poss.add(s);
	}
	
	public List<String> getAnswers()
	{
		return answer;
	}
	
	public List<String> getPossibilities()
	{
		return poss;
	}
	
	public int getAnswerCount()
	{
		return answer.size();
	}
	
	public int getPossCount()
	{
		return poss.size();
	}
	
	public String getPoss(int i)
	{
		return poss.get(i);
	}
	
	public String getAns(int i)
	{
		return answer.get(i);
	}
	
}
