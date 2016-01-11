package spell;

import spell.ITrie2.INode;

public class Node2 implements INode {

	private Node2[] seq;
	int count;
	int wordcount;
	String name;
	
	Node2(){
		seq = new Node2[26];
		count = 0;
		wordcount = 0;
		name = new String();
	}
	
	public void updateRoot(InsertResult numb)
	{
		int c = numb.getNCount();
		int wc = numb.getWCount();
		count = c + count;
		wordcount = wc + wordcount;
		System.out.println("NC: " + count + " WC: " + wordcount);
	}
	
	public int getWC()
	{
		return wordcount;
	}
	
	public String getNWord()
	{
		return name;
	}
	
	public Node2[] getSeq()
	{
		return seq;
	}
	
	public InsertResult Insert(String word, InsertResult ir, int level)
	{
		if(level >= word.length())
		{
			count++;
			if(count == 1)
			{
				ir.addWord();
				name = word;
				System.out.println(name);
			}
			return ir;
		}
		
		char c = word.charAt(level);
		int index = c - 'a';
		
		if(seq[index] == null)
		{
			Node2 n = new Node2();
			ir.addNode();
			seq[index] = n;
			level++;
			n.Insert(word, ir, level);
		}
		else
		{
			level++;
			seq[index].Insert(word, ir, level);
		}
		
		return ir;
	}
	
	public Node2 Find(String word, int level)
	{
		if(level == (word.length()-1))
		{
			
			char c = word.charAt(level);
			
			//System.out.println("final: " + c);
			int index = c - 'a';
			if(seq[index] != null)
			{
				if(seq[index].getValue() > 0)
				{
					//System.out.println("test " + seq[index].getNWord());
					return seq[index];
				}
				else
				{
					return null;
				}
			}
			else
			{
				return null;
			}
		}
		else if(level > word.length()-1)
		{
			return null;
		}
		
		char c = word.charAt(level);
		//System.out.println(c);
		int index = c - 'a';
		
		if(seq[index] == null)
		{
			return null;
		}
		else
		{
			level++;
			
			return seq[index].Find(word, level);
		}
		
	}
	
	public StringBuilder AllWords(StringBuilder sb)
	{
		for(int i=0; i<26; i++)
		{
			if(seq[i] != null)
			{
				if(seq[i].getValue()>0)
				{
					sb.append(seq[i].getNWord());
					sb.append('\n');
				}
				sb = seq[i].AllWords(sb);
			}
		}
		return sb;
	}
	
	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return count;
	}
	
	@Override
	public int hashCode()
	{
		int pos = 1;
		for(int i=0; i<26; i++)
		{
			if(seq[i] != null)
			{
				pos = i;
			}
		}
		return count*wordcount+pos;
	}

}
