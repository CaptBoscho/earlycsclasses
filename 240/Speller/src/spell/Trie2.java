package spell;

public class Trie2 implements ITrie2 {

	private Node2 root;
	
	Trie2(){
		root = new Node2();
	}
	
	@Override
	public void add(String word) {
		InsertResult ir = new InsertResult();
		ir = root.Insert(word, ir, 0);
		root.updateRoot(ir);
		
	}

	@Override
	public Node2 find(String word) {
		Node2 n = root.Find(word, 0);
		
		return n;
	}
	
	public Node2 SimWord(String word)
	{
		Possibilities p = new Possibilities();
		p = Operations(word, p);
		
		
		if(p.getAnswerCount()>0)
		{
			String ans = p.getAnswer(0);
			Node2 n = root.Find(ans, 0);
			int cc = n.getValue();
			return Decide(p, 1, n);
		}
		else
		{
			Possibilities q = new Possibilities();
			for(int i=0; i<p.getPossCount(); i++)
			{
				String str = p.getPoss(i);
				q = Operations(str, q);
			}
			
			if(q.getAnswerCount() > 0)
			{
				String ans = q.getAnswer(0);
				Node2 n = root.Find(ans, 0);
				int cc = n.getValue();
				return Decide(q, 1, n);
			}
			else
			{
				return null;
			}
		}
		
	}
	
	public Node2 Decide(Possibilities p, int index, Node2 first)
	{
		if(index >= p.getAnswerCount())
		{
			return first;
		}
		Node2 newbie = root.Find(p.getAnswer(index), 0);
		index++;
		if(newbie==null)
		{
			return Decide(p,index, first);
		}
		
		if(newbie.getValue() == first.getValue())
		{
			//alphabetize
			int comp = first.getNWord().compareTo(newbie.getNWord());
			if(comp > 0)
			{
				return Decide(p, index, newbie);
			}
			else
			{
				return Decide(p, index, first);
			}
			
		}
		else if(newbie.getValue() > first.getValue())
		{
		
			return Decide(p, index, newbie);
		}
		else
		{
			return Decide(p, index, first);
		}
		
	}
	
	public Possibilities Operations(String word, Possibilities p)
	{
		p = Deletion(word, p);
		p = Transpose(word, p);
		p = Alteration(word, p);
		p = Insert(word, p);
		
		
		return p;
	}
	
	public Possibilities Deletion(String word, Possibilities p)
	{
		for(int i=0; i<word.length(); i++)
		{
			StringBuilder sb = new StringBuilder(word);
			sb.deleteCharAt(i);
			String result = sb.toString();
			
			Node2 n = root.Find(result, 0);
			
			if(n == null)
			{
				p.addPoss(result);
			}
			else
			{
				if(n.getValue()>0)
				{
					p.addAnswer(result);
				}
				else
				{
					p.addPoss(result);
				}
			}
		}
		
		return p;
	}
	
	public Possibilities Transpose(String word, Possibilities p)
	{
		for(int i=0; i<word.length()-1; i++)
		{
			char a = word.charAt(i);
			char b = word.charAt(i+1);
			StringBuilder sb = new StringBuilder(word);
			sb.setCharAt(i,b);
			sb.setCharAt(i+1,a);
			String result = sb.toString();
			
			Node2 n = root.Find(result, 0);
			
			if(n == null)
			{
				p.addPoss(result);
			}
			else
			{
				if(n.getValue()>0)
				{
					p.addAnswer(result);
				}
				else
				{
					p.addPoss(result);
				}
			}
			
		}
		return p;
	}
	
	public Possibilities Alteration(String word, Possibilities p)
	{
		for(int i=0; i<word.length(); i++)
		{
			for(char c='a'; c<='z'; c++)
			{
				StringBuilder sb = new StringBuilder(word);
				sb.setCharAt(i, c);
				String result = sb.toString();
				Node2 n = root.Find(result, 0);
				
				if(n == null)
				{
					p.addPoss(result);
				}
				else
				{
					if(n.getValue()>0)
					{
						p.addAnswer(result);
					}
					else
					{
						p.addPoss(result);
					}
				}
			}
		}
		
		return p;
	}
	
	public Possibilities Insert(String word, Possibilities p)
	{
		for(int i=0; i<word.length()+1; i++)
		{
			for(char c='a'; c<='z'; c++)
			{
				StringBuilder sb = new StringBuilder(word);
				sb.insert(i, c);
				String result = sb.toString();
				Node2 n = root.Find(result, 0);
				
				if(n == null)
				{
					p.addPoss(result);
				}
				else
				{
					if(n.getValue()>0)
					{
						p.addAnswer(result);
					}
					else
					{
						
						p.addPoss(result);
					}
				}
			}
			
		}
		
		return p;
	}

	@Override
	public int getWordCount() {
		// TODO Auto-generated method stub
		return root.getWC();
	}

	@Override
	public int getNodeCount() {
		// TODO Auto-generated method stub
		return root.getValue();
	}
	
	@Override
	public String toString()
	{
		StringBuilder all = new StringBuilder();
		all = root.AllWords(all);
		String str = all.toString();
		return str;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 17;
		int result = prime * root.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this==o){return true;}
		if(o==null){return false;}
		if(getClass() != this.getClass()){return false;}
		Trie2 other = (Trie2) o;
		Node2 oop = other.root;
		
		return Traverse(oop, root);
		
	}
	
	public boolean Traverse(Node2 oop, Node2 r)
	{
		if(oop==null && r!=null)
		{
			return false;
		}
		if(oop!=null && r==null)
		{
			return false;
		}
		if(oop == null && r == null)
		{
			return true;
		}
		if(oop.getValue() != r.getValue())
		{
			return false;
		}
		if(oop.getWC() != r.getWC())
		{
			return false;
		}
		
		
		for(int i=0; i<26; i++)
		{
			boolean p = Traverse(oop.getSeq()[i], r.getSeq()[i]);
			if(p==false)
			{
				return false;
			}
		}
		return true;
	}

}
