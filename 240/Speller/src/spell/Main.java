package spell;

import java.io.IOException;

import spell.ISpellCorrector2.NoSimilarWordFoundException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws NoSimilarWordFoundException, IOException {
		
		String dictionaryFileName = args[0];
		String inputWord = args[1];
		
		/**
		 * Create an instance of your corrector here
		 */
		SpellCorrector2 corrector = new SpellCorrector2();
		
		corrector.useDictionary(dictionaryFileName);
		String suggestion = corrector.suggestSimilarWord(inputWord);
		
		System.out.println("Suggestion is: " + suggestion);
		
		Trie2 a = new Trie2();
		Trie2 b = new Trie2();
		
		a.add("cat");
		a.add("cat");
		b.add("cat");
		//b.add("dog");
		
		boolean rand = a.equals(b);
		
		if (rand == false)
		{
			System.out.println("not equal");
			
		}
		else
		{
			System.out.println("equal");
		}
	}

}