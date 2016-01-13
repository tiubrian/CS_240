package hangman;
import java.io.*;

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

		while (nguess>=0)
		{
			String g = null;
			while (!validate(g))
			{
				g = c.readLine("Enter Next Guess: ");
				if (g != null) System.out.println("Invalid Input");
			}
			System.out.println("You entered " + g + " , a string of length " + g.length());
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
