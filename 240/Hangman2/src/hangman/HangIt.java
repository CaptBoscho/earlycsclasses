package hangman;

import java.io.File;

public class HangIt {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length != 3)
		{
			System.out.println("Error, entered arguments wrong.");
			return;
		}
		
		String filen = args[0];
		File file = new File(filen);
		String wol = args[1];
		String g = args[2];
		
		int wl = Integer.parseInt(wol);
		int guess = Integer.parseInt(g);
		
		EvilHangmanGame2 ehm = new EvilHangmanGame2();
		
		ehm.startGame(file, wl);
		ehm.setGuess(guess);
		ehm.Execute();
		
		
	}

}
