package spell;
public class Node {
	private int count;
	private Node[] seq;
	private int nodecount;
	//private char name;
	
	public Node()
	{
		
		count = 0;
		seq = new Node[26];
		nodecount = 0;
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
		char c = word.charAt(level);
		
		int index = c - 'a';
		
		if(seq[index] == null)
		{
			level++;
			if (level > word.length())
			{
				count++;
				counters.addWord();
				return counters;
			}
			else{
				Node n = new Node();
				seq[index] = n;
				counters.addNode();
				n.addWord(word, level, counters);
				return counters;
			}
			
		}
		else
		{
			level++;
			if (level > word.length()){
				count++;
				counters.addWord();
				return counters;
			}
			else {
				seq[index].addWord(word, level, counters);
				return counters;
			}
			
		}
	}
	
}
