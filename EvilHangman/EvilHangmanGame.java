package hangman;

import java.io.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.HashMap;
import hangman.IEvilHangmanGame.GuessAlreadyMadeException;

public class EvilHangmanGame implements IEvilHangmanGame {

	private Set<String> words;
	private int wlen;
	private Set<Character> guessed;

	/**
	 * Starts a new game of evil hangman using words from <code>dictionary</code>
	 * with length <code>wordLength</code>.
	 *	<p>
	 *	This method should set up everything required to play the game,
	 *	but should not actually play the game. (ie. There should not be
	 *	a loop to prompt for input from the user.)
	 *
	 * @param dictionary Dictionary of words to use for the game
	 * @param wordLength Number of characters in the word to guess
	 */
	public void startGame(File dictionary, int wordLength)
	{
		wlen = wordLength;
		guessed = new HashSet<Character>();
		words = new HashSet<String>();
		try {
			FileReader freader = new FileReader(dictionary);
			BufferedReader fbuf = new BufferedReader(freader);
			Scanner s = new Scanner(fbuf);
			while (s.hasNext())
			{
				words.add(s.next());
			}

			// Iterator<String> it = words.iterator();
			// while (it.hasNext())
			// {
			// 	System.out.println(it.next());
			// }
		}
		catch (IOException e)
		{
			System.out.println("Error occured when reading input file "+dictionary+ " "+e.toString());
		}
	}


	/**
	 * Make a guess in the current game.
	 *
	 * @param guess The character being guessed
	 *
	 * @return The set of strings that satisfy all the guesses made so far
	 * in the game, including the guess made in this call. The game could claim
	 * that any of these words had been the secret word for the whole game.
	 *
	 * @throws GuessAlreadyMadeException If the character <code>guess</code>
	 * has already been guessed in this game.
	 */
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException
	{
		if (guessed.contains(new Character(guess))) throw new GuessAlreadyMadeException();
		guessed.add(guess);
		HashMap<Integer, Set<String>> partitions = new HashMap<Integer, Set<String>>();

		//choose set to


		return words;
	}

	public void print_guessed()
	{
		Iterator<Character> it = guessed.iterator();
		while (it.hasNext())
		{
			System.out.print(it.next() + " ");
		}
		System.out.print("\n");
	}
}
