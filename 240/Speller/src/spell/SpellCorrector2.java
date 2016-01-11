package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import spell.ITrie2.INode;

public class SpellCorrector2 implements ISpellCorrector2 {

	private static final Exception NoSimilarWordFoundException = null;
	private Trie2 t;
	
	SpellCorrector2(){
		t = new Trie2();
	}
	
	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {
		File file = new File(dictionaryFileName);
		Scanner sc = new Scanner(file);
		while(sc.hasNext())
		{
			String word = sc.next().toLowerCase();
			t.add(word);
			
		}
		
	}

	@Override
	public String suggestSimilarWord(String inputWord)
			throws NoSimilarWordFoundException {
		
		Node2 n = t.find(inputWord.toLowerCase());
		
		if(n==null)
		{
			Node2 s = t.SimWord(inputWord);
			if(s==null)
			{
				throw new ISpellCorrector2.NoSimilarWordFoundException();
			}
			else
			{
				String str = s.getNWord();
				return str;
			}
		}
		else
		{
			String w = n.getNWord();
			System.out.println("w " + w);
			return w;
		}
		
	}

}
