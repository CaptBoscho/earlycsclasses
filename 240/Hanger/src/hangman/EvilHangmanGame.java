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

public class EvilHangmanGame implements IEvilHangmanGame {

	private Set<String> dd;
	private Set<Character> used;
	private int wl;
	private int guesses;
	private char[] word;
	
	public EvilHangmanGame(){
		dd = new TreeSet<String>();
		used = new TreeSet<Character>();
	}
	
	public void Initialize(int g, int swl){
		//dd = new TreeSet<String>();
		//used = new TreeSet<Character>();
		wl = swl;
		guesses = g;
		//word = new char[wl];
		/*for(int i=0; i<wl; i++)
		{
			word[i] = '-';
		}*/
	}
	
	@Override
	public void startGame(File dictionary, int wordLength) {
		
		//wl = wordLength;
		
		Scanner sc = null;
		try {
			sc = new Scanner(dictionary);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(sc.hasNext())
		{
			String word = sc.nextLine();
			if(word.length() == wordLength)
			{
				dd.add(word);		
			}
		}
		word = new char[wordLength];
		for(int i=0; i<wl; i++)
		{
			word[i] = '-';
		}
		
	}

	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
		
		guess = Character.toLowerCase(guess);
		//char guess = PrintInfo();
		//System.out.println(guess);
		Set<String> dog = new TreeSet<String>();
		if(Character.isLetter(guess))
		{
			for(int p=0; p<used.size(); p++)
			{
				Iterator<Character> it = used.iterator();
				while(it.hasNext())
				{
					if(it.next() == guess)
					{
						//throw new IEvilHangmanGame.GuessAlreadyMadeException();
						System.out.println("You already used that letter");
						//System.out.println("Enter guess: ");
						Execute();
						
						//Scanner in4 = new Scanner(new InputStreamReader(System.in));
						//ReadIn(in4);
					}
				}
			}
			
			used.add(guess);
				//
			Map.Entry<String, Set<String>> m = Evil(guess);
			String k = m.getKey();
			dd = m.getValue();
			dog = m.getValue();
			boolean there = false;
			int tom = 0;
			for(int t=0; t<k.length(); t++)
			{
				if(k.charAt(t)==guess)
				{
					there = true;
					word[t] = guess;
					tom++;
				}
			}
			
			if(there)
			{
				System.out.println("Yes, there is " + tom + " " + guess);
			}
			else
			{
				System.out.println("Sorry, there are no " + guess + "'s");
			}
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
				System.out.print("The correct word was: ");
				for(int i=0; i<word.length; i++)
				{
					System.out.print(word[i]);
				}
				//return null;
			}	
			
		}
		else
		{
			System.out.println("Invalid input");
			Execute();
			/*System.out.println("Enter guess: ");
			Scanner in2 = new Scanner(new InputStreamReader(System.in));
			ReadIn(in2);*/
		}

		
		
		
		return dog;
	}
	
	public void Execute()
	{
		Set<String> options = new TreeSet<String>();
		while(guesses > 0)
		{
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
				/*System.out.println("You Win!");
				System.out.print("The correct word was: ");
				for(int i=0; i<word.length; i++)
				{
					System.out.print(word[i]);
				}*/
				return;
			}
			else
			{
		
				try {
					options = PrintInfo();
					//System.out.println("size: " + options.size());
				} catch (GuessAlreadyMadeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				guesses--;
			}
		}
		
		//find and get the options, and return "i'm sorry, the correct word was:
		if(options.size() > 1)
		{
			System.out.print("I'm sorry, the correct word was: ");
			Iterator<String> it = options.iterator();
			if(it.hasNext())
			{
				String x = it.next();
				System.out.print(x);
			}
			
		}
		
	}
	
	public Set<String> PrintInfo() throws GuessAlreadyMadeException
	{
		System.out.println("You have " + guesses + " guesses left");
		System.out.print("Used letters: ");
		
		Iterator<Character> it = used.iterator();
		while(it.hasNext())
		{
			System.out.print(it.next());
			System.out.print(" ");
		}
		System.out.print('\n');
		
		System.out.print("Word: ");
		for(int i=0; i<word.length; i++)
		{
			System.out.print(word[i]);
		}
		System.out.print('\n');
		
		System.out.print("Enter guess: ");
		

		Scanner in = new Scanner(new InputStreamReader(System.in));
		
		Set<String> options = new TreeSet<String>();
		options = ReadIn(in);
		//System.out.println("test " + options.size());
		return options;
	}
	
	public Set<String> ReadIn(Scanner in) throws GuessAlreadyMadeException
	{
		Set<String> options = new TreeSet<String>();
		if(in.hasNext())
		{
			String l = in.next();
			//System.out.println(l);
			
			if(l.length()>1)
			{
				System.out.println("Error, you an only enter one letter.");
				/*System.out.println("Enter guess: ");
				Scanner in3 = new Scanner(new InputStreamReader(System.in));
				ReadIn(in3);*/
				Execute();
			}
			
			//Set<String> options = new TreeSet<String>();
			options = makeGuess(l.charAt(0));
			//System.out.println("test2 " + options.size());
			
		}
		//System.out.println("test3 " + options.size());
		
		return options;
	}
	
	public Map.Entry<String, Set<String>> Evil(char c)
	{
		Map<String, Set<String>> m = new HashMap<String, Set<String>>();
		Iterator<String> it = dd.iterator();
		while(it.hasNext())
		{
			String l = it.next();
			List<Integer> index = new ArrayList<Integer>();
			for(int i=0; i<l.length(); i++)
			{
				if(l.charAt(i) == c)
				{
					index.add(i);
				}
			}
			StringBuilder sb = new StringBuilder();
			int q =0;
			for(int k=0; k<l.length(); k++)
			{
				if(q<index.size())
				{
					if(k == index.get(q))
					{
						sb.append(c);
						q++;
					}
					else
					{
						sb.append('-');
					}
				}
			}
			//add to map
			String manda = sb.toString();
			
			if(!m.containsKey(manda))
			{
				Set<String> s = new TreeSet<String>();
				m.put(manda, s);
				//System.out.println("map size: " + m.size());
			}
			
			m.get(manda).add(l);
			
		}
		
		
		
		return Judge(m, c);
		
	}
	
	public Map.Entry<String, Set<String>> Judge(Map<String, Set<String>> m, char c)
	{
		Set<Map.Entry<String, Set<String>>> da = m.entrySet();
		
		Set<String> keys = new TreeSet<String>();
		keys = m.keySet();
		
		//System.out.println("Kyes: " + keys.size());
		
		int s = 0;
		//Set<Map.Entry<String, Set<String>>> fin = new TreeSet<Map.Entry<String, Set<String>>>();
		//Iterator<Map.Entry<String, Set<String>>> it = da.iterator();
		
		Set<String> best = new TreeSet<String>();
		Iterator<String> it = keys.iterator();
		
		while(it.hasNext())
		{
			//Map.Entry<String, Set<String>> newbie = it.next();
			String access = it.next();
			//newbie = it.next();
			//int a = newbie.getValue().size();
			int a = m.get(access).size();
			//System.out.println("a: " + a);
			if(a>s)
			{
				//fin.clear();
				//fin.add(newbie);
				best.clear();
				best.add(access);
				s = a;
			}
			else if(a==s)
			{
				//fin.add(newbie);
				best.add(access);
			}
		}
		
		//if(fin.size() > 1)
		//System.out.println(best.size());
		if(best.size() > 1)
		{
			//Set<Map.Entry<String, Set<String>>> end = new TreeSet<Map.Entry<String, Set<String>>>();
			Set<String> ender = new TreeSet<String>();
			
			int countcheck = 0;
			//Iterator<Map.Entry<String, Set<String>>> chord = fin.iterator();
			Iterator<String> chord = best.iterator();
			
			while(chord.hasNext())
			{
				//Map.Entry<String, Set<String>> newbie;
				String x = new String();
				//x = it.next();
				x = chord.next();
				//String x = newbie.getKey();
				
				int count = 0;
				
				for(int b=0; b<x.length(); b++)
				{
					if(x.charAt(b) == c)
					{
						count++;
					}
				}
				
				if(ender.size()==0)
				{
					countcheck = count;
					//end.clear();
					//end.add(newbie);
					ender.add(x);
				}
				else if(count < countcheck && count > 0)
				{
					countcheck = count;
					//end.clear();
					//end.add(newbie);
					ender.clear();
					ender.add(x);
					
				}
				else if(count == countcheck)
				{
					//end.add(newbie);
					ender.add(x);
				}
			}
			
			//if(end.size() > 1)
			if(ender.size() > 1)
			{
				//Set<Map.Entry<String, Set<String>>> love = new TreeSet<Map.Entry<String, Set<String>>>();
				//Iterator<Map.Entry<String, Set<String>>> mich = end.iterator();
				Set<String> lover = new TreeSet<String>();
				Iterator<String> michelle = ender.iterator();
				
				int pos = 0;
				while(michelle.hasNext())
				{
					int pos2 = 0;
					//Map.Entry<String, Set<String>> newbie;
					//newbie = mich.next();
					//String x = newbie.getKey();
					String x = michelle.next();
					for(int y = x.length()-1; y>0; y--)
					{
						if(x.charAt(y)==c)
						{
							pos2 = y;
						}
						if(pos2 > pos)
						{
							pos = pos2;
							//love.clear();
							//love.add(newbie);
							lover.clear();
							lover.add(x);
						}
					}
				}
				if(lover.size()!=1)
				{
					System.out.println("Error");
					return null;
				}
				else
				{
					Iterator<Map.Entry<String, Set<String>>> hope = da.iterator();
					Iterator<String> hoper = lover.iterator();
					String check = hoper.next();
					while(hope.hasNext())
					{
						Map.Entry<String, Set<String>> checker = hope.next();
						if(checker.getKey().equals(check))
						{
							return checker;
						}
					}
					
					//return hope.next();
				}
				
			}
			else
			{
				//Iterator<Map.Entry<String, Set<String>>> mich = end.iterator();
				//return mich.next();	
				Iterator<Map.Entry<String, Set<String>>> hope = da.iterator();
				Iterator<String> hoper = ender.iterator();
				String check = hoper.next();
				while(hope.hasNext())
				{
					Map.Entry<String, Set<String>> checker = hope.next();
					if(checker.getKey().equals(check))
					{
						return checker;
					}
				}
			}
		}
		else
		{
			//Iterator<Map.Entry<String, Set<String>>> chord = fin.iterator();
			//return chord.next();	
			Iterator<Map.Entry<String, Set<String>>> hope = da.iterator();
			Iterator<String> hoper = best.iterator();
			String check = hoper.next();
			while(hope.hasNext())
			{
				Map.Entry<String, Set<String>> checker = hope.next();
				if(checker.getKey().equals(check))
				{
					return checker;
				}
			}
		}
		return null;
	}

}
