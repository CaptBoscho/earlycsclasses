package spell;

import spell.ITrie.INode;
//import InsertionResult;
//import Node;

public class Node implements INode {
	private int count;
	private Node[] seq;
	private int nodecount;
	//private int veces;
	//private char name;
	
	public Node()
	{
		
		count = 0;
		setSeq(new Node[26]);
		nodecount = 1;
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
		return getSeq()[i];
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
		
		
		if(getSeq()[index] == null)
		{
			level++;
			
			
				Node n = new Node();
				getSeq()[index] = n;
				counters.addNode();
				n.addWord(word, level, counters);
				return counters;
			
			
		}
		else
		{
			level++;
		
				getSeq()[index].addWord(word, level, counters);
				return counters;
			
			
		}
	}
	
	public Node FindfromRoot(String s, int level)
	{
		
		if(level >= s.length())
		{
			return null;
		}
		
		char c = s.charAt(level);
		int index = c - 'a';
		
		
		if(getSeq()[index] == null)
		{
			return null;
		}
		
		
		
		level++;
		Node p = getSeq()[index];
		p = getSeq()[index].Find2(s, level, p);
		return p;
	}
	
	public Node Find2(String s, int level, Node p)
	{
		
		if(level >= s.length())
		{
			return p;
		}
		
		char c = s.charAt(level);
		int index = c - 'a';
		
		
		if(getSeq()[index] == null)
		{
			return null;
		}
		
		
		
		level++;
		p = getSeq()[index];
		p = getSeq()[index].Find2(s, level, p);
		return p;
	}
	
	public StringBuilder WordReturn(StringBuilder sb, int place)
	{
		for(int i=0; i<26; i++)
		{
			if(getSeq()[i] != null)
			{
				int r = 'a' + i;
				char c = (char)r;
				sb.append(c);
				if(getSeq()[i].getValue() > 0)
				{
					sb.append('\n');
					//place = sb.length();
				}
				place = sb.length()-1;
				//System.out.println("from root: " + place);
				sb = getSeq()[i].WR(sb, place);
			}
		}
		
		return sb;
	}
	
	public StringBuilder WR(StringBuilder sb, int place)
	{
		if(count > 0)
		{
			//System.out.println("place " + place);
			String temp = sb.substring(place);
			sb.append('\n');
			place = sb.length()-1;
			//sb.append(temp);
			for(int i=0; i<26; i++)
			{
				if(getSeq()[i] != null)
				{
					int r = 'a' + i;
					char c = (char)r;
					sb.append(temp);
					sb.append(c);
					sb = getSeq()[i].WR(sb, place);
				}
			}
		}
		else
		{
		
			for(int i=0; i<26; i++)
			{
				if(getSeq()[i] != null)
				{
					int r = 'a' + i;
					char c = (char)r;
					
					sb.append(c);
					sb = getSeq()[i].WR(sb, place);
				}
			}
		}
		
		return sb;
	}
	
	public boolean Traversal()
	{
		
		return true;
	}
	
	
	

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return count;
	}

	public Node[] getSeq() {
		return seq;
	}

	public void setSeq(Node[] seq) {
		this.seq = seq;
	}
	
	@Override
	public int hashCode() {
		int t = 0;
		boolean hw = true;
		for(int x=0; x<26; x++)
		{
			if(seq[x]!=null)
			{
				t=x;
			}
		}
		
		int party = 17*count + t;
		
		return party;
	}
	
	
}
