package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class EvilHangmanGame2 implements IEvilHangmanGame2 {

	private Set<String> poss;
	private Set<Character> used;
	private int wl;
	private char[] word;
	private int guesses;
	
	public EvilHangmanGame2()
	{
		poss = new TreeSet<String>();
		used = new TreeSet<Character>();
	}
	
	public void setGuess(int g)
	{
		guesses = g;
	}
	
	@Override
	public void startGame(File dictionary, int wordLength) {
		// TODO Auto-generated method stub
		
		wl = wordLength;
		word = new char[wordLength];
		for(int i=0; i<wordLength; i++)
		{
			word[i] = '-';
		}
		
		Scanner sc = null;
		
		try {
			sc = new Scanner(dictionary);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(sc.hasNext())
		{
			String temp = sc.nextLine();
			if(temp.length() == wordLength)
			{
				//System.out.println(temp);
				poss.add(temp);
			}
		}
	}
	
	
	
	public void Execute()
	{
		Set<String> options = new TreeSet<String>();
		while(guesses>0)
		{
			options = PrintInfo();
			guesses--;
			boolean done = true;
			for(int i=0; i<wl; i++)
			{
				if(word[i] == '-')
				{
					done = false;
				}
			}
			
			if(done)
			{
				System.out.println("You Win!");
				Iterator<String> tired = options.iterator();
				if(tired.hasNext())
				{
					System.out.println("The word was: " + tired.next());
				}
				return;
			}
			
			if(guesses == 0)
			{
				System.out.println("You Lose!");
				Iterator<String> tired = options.iterator();
				if(tired.hasNext())
				{
					System.out.println("The word was: " + tired.next());
				}
				return;
			}
			
			
			//poss = options;
			
		}
	}
	
	public Set<String> PrintInfo()
	{
		System.out.println("You have " + guesses + " guesses left");
		System.out.print("Used letters: ");
		
		Iterator<Character> it = used.iterator();
		while(it.hasNext())
		{
			System.out.print(it.next() + " ");
		}
		System.out.print('\n');
		
		System.out.print("Word: ");
		for(int i=0; i<wl; i++)
		{
			System.out.print(word[i]);
		}
		System.out.print('\n');
		
		System.out.print("Enter guess: ");
		
		Scanner in = new Scanner(new InputStreamReader(System.in));
		return ReadIn(in);
		
	}
	
	public Set<String> ReadIn(Scanner in)
	{
		Set<String> options = new TreeSet<String>();
		if(in.hasNext())
		{
			String l = in.next();
			
			if(l.length()!=1)
			{
				System.out.println("Can only enter one character! Try again.");
				Execute();
			}
			else if(!Character.isLetter(l.charAt(0)))
			{
				System.out.println("Please try again with a letter.");
				Execute();
			}
			
			char c = Character.toLowerCase(l.charAt(0));
			
			for(int i=0; i<used.size(); i++)
			{
				Iterator<Character> cb = used.iterator();
				while(cb.hasNext())
				{
					if(cb.next().equals(c))
					{
						System.out.println("You have already used this string. Try again.");
						Execute();
					}
				}
			}
			
			used.add(c);
			
			try {
				options = makeGuess(c);
			} catch (GuessAlreadyMadeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return options;
	}

	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
		// TODO Auto-generated method stub
		
		Map.Entry<String, Set<String>> cruel = Evil(guess);
		
		String str = cruel.getKey();
		Set<String> options = cruel.getValue();
		poss = cruel.getValue();
		
		
		int there = 0;
		
		for(int i=0; i<str.length(); i++)
		{
			if(str.charAt(i) == guess)
			{
				there++;
				word[i] = guess;
			}
		}
		
		if(there>0)
		{
			System.out.println("Yes, there is " + there + " " + guess);
		}
		else
		{
			System.out.println("Sorry, there are no " + guess + "'s");
		}
		System.out.print('\n');
			
		
		
		
		return options;
	}
	
	public Map.Entry<String, Set<String>> Evil(char guess)
	{
		Map<String, Set<String>> m = new HashMap<String, Set<String>>();
		Iterator<String> look = poss.iterator();
		while(look.hasNext())
		{
			String temp = look.next();
			List<Integer> index = new ArrayList<Integer>();
			for(int i=0; i<temp.length(); i++)
			{
				if(temp.charAt(i)==guess)
				{
					index.add(i);
				}
			}
			
			StringBuilder w = new StringBuilder();
			int q=0;
			for(int i=0; i<temp.length(); i++)
			{
				if(q<index.size())
				{
					if(i==index.get(q))
					{
						w.append(guess);
						q++;
					}
					else
					{
						w.append('-');
					}
				}
			}
			
			
			String manda = w.toString();
			
			if(!m.containsKey(manda))
			{
				Set<String> t = new TreeSet<String>();
				m.put(manda, t);
			}
			
			m.get(manda).add(temp);
			
		}
		
		return Judge(m, guess);
	}
	
	public Map.Entry<String, Set<String>> Judge(Map<String, Set<String>> m, char c)
	{
		Set<Map.Entry<String, Set<String>>> da = m.entrySet();
		
		Set<String> keys = m.keySet();
		Set<String> best = new TreeSet<String>(); //Stores the keys of the best sets
		int f = 0;
		Iterator<String> it = keys.iterator();
		while(it.hasNext())
		{
			String temp = it.next();
			int s = m.get(temp).size();
			//System.out.println(s);
			if(s>f)
			{
				f = s;
				best.clear();
				best.add(temp);
			}
			else if(s==f)
			{
				best.add(temp);
			}
		}
		//System.out.println("best: " + best.size());
		if(best.size() > 1)
		{
			Iterator<String> cor = best.iterator();
			Set<String> bin = new TreeSet<String>();
			int countcheck = 0;
			while(cor.hasNext())
			{
				int count = 0;
				String b = cor.next();
				for(int i=0; i<b.length(); i++)
				{
					if(b.charAt(i)==c)
					{
						count++;
					}
				}
				
				if(bin.size() == 0)
				{
					countcheck = count;
					bin.add(b);
				}
				else if(count < countcheck)
				{
					bin.clear();
					bin.add(b);
					countcheck = count;
				}
				else if(count == countcheck)
				{
					bin.add(b);
				}
			}
			
			if(bin.size() > 1)
			{
				Iterator<String> man = bin.iterator();
				Set<String> dad = new TreeSet<String>();
				int where = 0;
				while(man.hasNext())
				{
					int here = 0;
					String str = man.next();
					//System.out.println(str);
					for(int i=0; i<str.length(); i++)
					{
						if(str.charAt(i) == c)
						{
							here = i;
						}
					}
					if(here > where)
					{
						dad.clear();
						dad.add(str);
						here = where;
					}
					
				}
				
				if(dad.size() != 1)
				{
					System.out.println("Error");
					return null;
				}
				
				Iterator<Map.Entry<String, Set<String>>> dog = da.iterator();
				Iterator<String> mich = dad.iterator();
				String k = mich.next();
				while(dog.hasNext())
				{
					Map.Entry<String, Set<String>> amp = dog.next();
					if(amp.getKey().equals(k))
					{
						return amp;
					}
				}
			}
			else
			{				
				Iterator<Map.Entry<String, Set<String>>> dog = da.iterator();
				Iterator<String> man = bin.iterator();
				String kat = man.next();
				while(dog.hasNext())
				{
					Map.Entry<String, Set<String>> amp = dog.next();
					if(amp.getKey().equals(kat))
					{
						return amp;
					}
				}
			}

		}
		else
		{
			//System.out.println("test");
			Iterator<Map.Entry<String, Set<String>>> dog = da.iterator();
			Iterator<String> cor = best.iterator();
			String k = cor.next();
			while(dog.hasNext())
			{
				Map.Entry<String, Set<String>> amp = dog.next();
				if(amp.getKey().equals(k))
				{
					return amp;
				}
			}
		}
		return null;
		
	}

}
