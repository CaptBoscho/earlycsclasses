package spell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector{
	
	private Trie t;
	
	public SpellCorrector()
	{
		t = new Trie();
	}

	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {
		// TODO Auto-generated method stub
		
		Scanner sc;
		try {
			sc = new Scanner(new File(dictionaryFileName));
			
			
			
			while (sc.hasNext())
			{
				
				String word = sc.next().toLowerCase();
				t.add(word);
				
			}
			String str = t.toString();
			System.out.println(str);
			//t.Find(args[1]);
			
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String suggestSimilarWord(String inputWord)
			throws NoSimilarWordFoundException {
		// TODO Auto-generated method stub
		Node p = t.find(inputWord.toLowerCase());
		
		
		if(p==null)
		{
			
			String s = t.SimWord(inputWord);
			//System.out.println("check1: " + s);
			
			if(s==null)
			{
				throw new ISpellCorrector.NoSimilarWordFoundException();
			}
			else
			{
				return s;
			}
		}
		else
		{
			
			return inputWord;
			
		}
		
	}

	

}
