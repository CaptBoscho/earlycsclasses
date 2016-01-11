package spell;
public class Trie {

	private Node root;
	
	public Trie()
	{
		root = new Node();
	}
	
	public void Upload(String s)
	{
		InsertionResult counters = new InsertionResult();
		int level = 0;
		counters = root.addWord(s, level, counters);
	}
	
	public int WordCount()
	{
		return root.getRootWordCount();
	}
	
	public int NodeCount()
	{
		return root.getRootNodeCount();
	}
	
	

}
