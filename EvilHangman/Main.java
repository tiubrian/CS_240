package hangman;
import java.io.*;

import hangman.IEvilHangmanGame.GuessAlreadyMadeException;

public class Main {

	public static void main(String[] args)
	{
		Console c = System.console();
		int nguess;
		int nwords;
		try
		{
			nguess = Integer.parseInt(args[2].trim());
			nwords = Integer.parseInt(args[1].trim());
		}
		catch (Exception e)
		{
			print_usage();
			return;
		}

		EvilHangmanGame game = new EvilHangmanGame();
		game.startGame(new File(args[0]),nwords);

		while (nguess>0)
		{
			if (nguess > 1) System.out.println("\nYou have "+nguess+" guesses remaining.");
			else System.out.println("\nYou have 1 guess remaining.");

			System.out.print("Guessed: ");
			game.print_guessed();
			String g = null;

			while (!validate(g))
			{
				if (g != null) System.out.println("Invalid Input");
				g = c.readLine("Enter Next Guess: ");
			}

			while (true)
			{
				try {
					game.makeGuess(g.charAt(0));
					break;
				}
				catch (GuessAlreadyMadeException e)
				{
					System.out.println("Letter " + g + " already guessed.");
					g = c.readLine("Enter Next Guess: ");
				}
			}

			nguess--;
		}
	}

	public static boolean validate(String s)
	{
		if (s==null) return false;
		if (s.length() != 1) return false;
		return Character.isLetter(s.charAt(0));
	}

	public static void print_usage()
	{
		System.out.println("Usage: dictionary wordLength guesses");
	}

}
