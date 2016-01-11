package spell;
import java.io.*;
import java.util.Scanner;

public class SpellCorrector {
	//private static File file;
	//private static Trie t;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File file = new File(args[0]);
		Scanner sc;
		try {
			sc = new Scanner(file);
			
			Trie t = new Trie();
			
			while (sc.hasNext())
			{
				
				String word = sc.next().toLowerCase();
				t.Upload(word);
				
			}
			
			
			t.Find(args[1]);
			
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}

