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
			if (nguess < 1) {
				System.out.println("guess must be an integer greater than 0.");
				print_usage();
				return;
			}
			if (nwords < 2) {
				System.out.println("wordlength must be and integer greater than 2;");
				print_usage();
				return;
			}
		}
		catch (Exception e)
		{
			print_usage();
			return;
		}

		EvilHangmanGame game = new EvilHangmanGame();
		game.startGame(new File(args[0]),nwords);
		if (game.num_words() == 0)
		{
			System.out.println("There are no words of length "+nwords+" in the dictionary.");
			print_usage();
			return;
		}

		while (nguess>0)
		{
			if (nguess > 1) System.out.println("\nYou have "+nguess+" guesses remaining.");
			else System.out.println("\nYou have 1 guess remaining.");

			System.out.print("Used letters: ");
			game.print_guessed();
			game.print_word();
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

			int letters = game.lastGuessLetters();
			if (letters == 0)
			{
				System.out.println("Sorry, there are no "+g+"'s");
				nguess--;
			}
			else
			{
				System.out.println("Yes, there are "+letters+ " "+g+"'s");
			}

			if (game.isWon())
			{
				System.out.println("You win!");
				game.print_word();
				return;
			}
		}

		//if we got to this point, the game is lost
		System.out.println("You lose!\nThe word was "+game.randWord());
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
