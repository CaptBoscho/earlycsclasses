package spell;
//import InsertionResult;
//import Node;

public class Node {
	private int count;
	private Node[] seq;
	private int nodecount;
	//private int veces;
	//private char name;
	
	public Node()
	{
		
		count = 0;
		seq = new Node[26];
		nodecount = 0;
		//veces = 0;
	}
	
	public void setRootInfo(InsertionResult counter)
	{
		count = counter.getAddedWords() + count;
		nodecount = counter.getAddedNodes() + nodecount;
	}
	
	public int getRootWordCount()
	{
		return count;
	}
	
	public int getRootNodeCount()
	{
		return nodecount;
	}
	
	public void upCount()
	{
		count++;
	}
	
	public Node getSeqNode(int i)
	{
		return seq[i];
	}
	
	public InsertionResult addWord(String word, int level, InsertionResult counters)
	{
		if (level >= word.length())
		{
			count++;
			if (count == 1)
			{
				counters.addWord();
			}
			return counters;
		}
		char c = word.charAt(level);
		
		int index = c - 'a';
		
		
		if(seq[index] == null)
		{
			level++;
			
			
				Node n = new Node();
				seq[index] = n;
				counters.addNode();
				n.addWord(word, level, counters);
				return counters;
			
			
		}
		else
		{
			level++;
		
				seq[index].addWord(word, level, counters);
				return counters;
			
			
		}
	}
	
	public int Find(String s, int level)
	{
		
		if(level >= s.length())
		{
			return count;
		}
		
		char c = s.charAt(level);
		int index = c - 'a';
		
		
		if(seq[index] == null)
		{
			return 0;
		}
		
		
		
		level++;
		int veces = seq[index].Find(s, level);
		return veces;
	}
	
}
