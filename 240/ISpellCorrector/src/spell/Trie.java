package spell;

public class Trie implements ITrie{
	
private Node root;
	
	public Trie()
	{
		root = new Node();
	}

	@Override
	public void add(String word) {
		// TODO Auto-generated method stub
		InsertionResult counters = new InsertionResult();
		int level = 0;
		counters = root.addWord(word, level, counters);
		root.setRootInfo(counters);
	}

	@Override
	public Node find(String word) {
		// TODO Auto-generated method stub
		
		int level =0;
		Node p = root.FindfromRoot(word, level);
		
		if(p==null)
		{
			return null;
		}
		if(p.getValue() == 0)
		{
			return null;
		}
		
		return p;
	}

	@Override
	public int getWordCount() {
		// TODO Auto-generated method stub
		return root.getRootWordCount();
	}

	@Override
	public int getNodeCount() {
		// TODO Auto-generated method stub
		return root.getRootNodeCount();
	}
	
	public String SimWord(String s)
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
			//System.out.println("Try 2");
			//System.out.println("Answer size: " + q.getAnswerCount());
			//System.out.println("Poss size: " + q.getPossCount());
			if(q.getAnswerCount()==0)
			{
				return null;
			}
			else
			{
				//decide which answer
				
				String manda = q.getAns(0);
				Node w = root.FindfromRoot(manda, 0);
				int mandacount = w.getValue();
				manda = Decide(manda, 1, mandacount, q);
				//System.out.println("closest word: " + manda);
				return manda;
			}
		}
		else
		{
			//decide which answer
			String manda = p.getAns(0);
			Node w = root.FindfromRoot(manda, 0);
			int mandacount = w.getValue();
			manda = Decide(manda, 1, mandacount, p);
			return manda;
		}
		
		
		
	}
	
	public String Decide(String manda, int index, int mandacount, Possibilites q)
	{
		if(index>= q.getAnswerCount())
		{
			return manda;
		}
		String temp = q.getAns(index);
		//System.out.println("manda= "+manda);
		//System.out.println("temp= "+temp);
		Node w = root.FindfromRoot(temp, 0);
		int tc = w.getValue();
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
			//System.out.println(getWordCount()); 
			
			int level = 0;
			Node w = root.FindfromRoot(result, level);
			if(w==null)
			{
				p.addPoss(result);
			}
			else
			{
				if(w.getValue()>0)
				{
					p.addAnswer(result);
				}
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
			Node w = root.FindfromRoot(str, level);
			if(w==null)
			{
				p.addPoss(str);
			}
			else
			{
				if(w.getValue()>0)
				{
					p.addAnswer(str);
				}
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
				Node w = root.FindfromRoot(str, level);
				if(w==null)
				{
					p.addPoss(str);
				}
				else
				{
					if(w.getValue()>0)
					{
						p.addAnswer(str);
					}
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
				Node w = root.FindfromRoot(str, level);
				if(w==null)
				{
					p.addPoss(str);
				}
				else
				{
					if(w.getValue()>0)
					{
						p.addAnswer(str);
					}
				}
			}
		}
		
		return p;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trie other = (Trie) obj;
		Node oroot = other.root;
		return Traverse(oroot);
		
		/*if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (number != other.number)
			return false;*/
		//return true;
		
	}
	
	public boolean Traverse(Node oroot)
	{
		if(oroot == null && root != null)
		{
			return false;
		}
		if(root.getRootNodeCount() != oroot.getRootNodeCount())
		{
			return false;
		}
		if(root.getRootWordCount() != oroot.getRootWordCount())
		{
			return false;
		}
		
		for(int i=0; i<26; i++)
		{
			boolean p = Traversal(root.getSeq()[i], oroot.getSeq()[i]);
			if(p==false)
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean Traversal(Node a, Node b)
	{
		if(a == null && b!=null)
		{
			return false;
		}
		if(a != null && b==null)
		{
			return false;
		}
		if(a == null && b==null)
		{
			return true;
		}
		if(a.getValue() != b.getValue())
		{
			return false;
		}
		
		for(int i=0; i<26; i++)
		{
			boolean m = Traversal(a.getSeq()[i], b.getSeq()[i]);
			if(m==false)
			{
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((root == null) ? 0 : root.hashCode());
		result = prime * result + root.hashCode();
		return result;
	}
	
	@Override
	public String toString() //returns every word in my trie
	{
		StringBuilder sb = new StringBuilder();
		sb = root.WordReturn(sb, 0);
		
		String all = sb.toString();
		
		return all;
	}

}
