package hangman;

import hangman.IEvilHangmanGame.GuessAlreadyMadeException;

import java.io.File;
import java.util.Scanner;

public class Hanger {

	public static void main(String[] args) throws GuessAlreadyMadeException {
		// TODO Auto-generated method stub

		if(args.length != 3)
		{
			System.out.println("Need 3 parameters");
		}
		else
		{
		
			String filen = args[0];
			File dict = new File(filen);
			String swl = args[1];
			int wl = Integer.parseInt(swl);
			String guess = args[2];
			int g = Integer.parseInt(guess);
			
			EvilHangmanGame ehm = new EvilHangmanGame();
			ehm.Initialize(g, wl);
			ehm.startGame(dict, wl);
		
			ehm.Execute();
			
			
		}
	}
	
	

}


