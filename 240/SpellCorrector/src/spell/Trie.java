package spell;

//import java.util.ArrayList;
//import java.util.List;

//import InsertionResult;
//import Node;

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
		root.setRootInfo(counters);
	}
	
	public int WordCount()
	{
		return root.getRootWordCount();
	}
	
	public int NodeCount()
	{
		return root.getRootNodeCount();
	}
	
	public void Find(String s)
	{
		int level =0;
		int veces = root.Find(s, level);
		
		if(veces == 0)
		{
			Possibilites p = new Possibilites();
			p = Operations(s, p);
			if(p.getAnswerCount() == 0)
			{
				Possibilites q = new Possibilites();
				for(int k=0; k<p.getPossCount(); k++)
				{
					
					String temp = p.getPoss(k);
					q = Operations(temp, q);
				}
				System.out.println("Try 2");
				System.out.println("Answer size: " + q.getAnswerCount());
				System.out.println("Poss size: " + q.getPossCount());
				if(q.getAnswerCount()==0)
				{
					System.out.println("NoSimilarWordFound");
				}
				else
				{
					//decide which answer
					
					String manda = q.getAns(0);
					int mandacount = root.Find(manda, 0);
					manda = Decide(manda, 1, mandacount, q);
					System.out.println("closest word: " + manda);
					
				}
			}
			else
			{
				//decide which answer
				String manda = p.getAns(0);
				int mandacount = root.Find(manda, 0);
				manda = Decide(manda, 1, mandacount, p);
				System.out.println("closest word: " + manda);
			}
		}
		else
		{
			System.out.println(s + " found " + veces);
		}
	}
	
	public String Decide(String manda, int index, int mandacount, Possibilites q)
	{
		if(index>= q.getAnswerCount())
		{
			return manda;
		}
		String temp = q.getAns(index);
		int tc = root.Find(temp, 0);
		if(tc == mandacount)
		{
			//alphabetize
			int comp = manda.compareTo(temp);
			if(comp<=0){
				index++;
				manda = Decide(manda, index, mandacount, q);
			}
			else
			{
				manda = Decide(temp, index, tc, q);
			}
			
		}
		else if(tc> mandacount)
		{
			index++;
			manda = Decide(temp, index, tc, q);
		}
		else
		{
			index++;
			manda = Decide(manda, index, mandacount, q);
		}
		
		
		
		return manda;
	}

	public Possibilites Operations(String s, Possibilites p)
	{
		p = Deletion(s, p);
		p = Transposition(s, p);
		p = Alternation(s, p);
		p = Insertion(s, p);
		
		
		return p;
	}
	
	public Possibilites Deletion(String s, Possibilites p)
	{
		for(int i=0; i<s.length(); i++)
		{
			StringBuilder build = new StringBuilder(s);
			build.deleteCharAt(i);
			String result = build.toString();
			//System.out.println(result);
			
			int level = 0;
			int veces = root.Find(result, level);
			if(veces>0)
			{
				p.addAnswer(result);
			}
			else
			{
				p.addPoss(result);
			}
		}
		
		return p;
	}
	
	public Possibilites Transposition(String s, Possibilites p)
	{
				
		for(int i=0; i<s.length()-1; i++)
		{
			char[] chars = s.toCharArray();
			char a = chars[i];
			char b = chars[i+1];
			chars[i] = b;
			chars[i+1] = a;
			String str = new String(chars);
			//System.out.println(str);
			
			int level = 0;
			int veces = root.Find(str, level);
			if(veces>0)
			{
				p.addAnswer(str);
			}
			else
			{
				p.addPoss(str);
			}
		}
		
		
		return p;
	}
	
	public Possibilites Alternation(String s, Possibilites p)
	{
		for(int i=0; i<s.length(); i++)
		{
			for(char c = 'a'; c<'z'; c++)
			{
				char[] chars = s.toCharArray();
				chars[i] = c;
				String str = new String(chars);
				//System.out.println(str);
				
				int level = 0;
				int veces = root.Find(str, level);
				if(veces>0)
				{
					p.addAnswer(str);
				}
				else
				{
					p.addPoss(str);
				}
			}
		}
		return p;
	}
	
	public Possibilites Insertion(String s, Possibilites p)
	{
		for(int i=0; i<=s.length(); i++)
		{
			for(char c = 'a'; c<'z'+1; c++)
			{
				StringBuilder build = new StringBuilder(s);
				build.insert(i, c);
				String str = build.toString();
				//System.out.println(str);
				
				int level = 0;
				int veces = root.Find(str, level);
				if(veces>0)
				{
					p.addAnswer(str);
				}	
				else
				{
					//System.out.println(str);
					p.addPoss(str);
					
				}
			}
		}
		
		return p;
	}
	

}
