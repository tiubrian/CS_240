package hangman;

import java.io.*;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.HashMap;
import java.util.BitSet;
import hangman.IEvilHangmanGame.GuessAlreadyMadeException;

public class EvilHangmanGame implements IEvilHangmanGame {

	private Set<String> words;
	private int wlen;
	private Set<Character> guessed;
	private char[] constraint_str; //a string of known letters in the word
	private long constraint_map; // a bitmap of taken positions
	private int last_guess_letters;
	private int letters_remaining;

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
		guessed = new TreeSet<Character>();
		words = new HashSet<String>();
		try {
			FileReader freader = new FileReader(dictionary);
			BufferedReader fbuf = new BufferedReader(freader);
			Scanner s = new Scanner(fbuf);
			while (s.hasNext())
			{
				String next = s.next();
				if (next.length() == wlen)	words.add(s.next());
			}

			constraint_str = new char[wlen];
			constraint_map = 0;
			last_guess_letters = 0;
			letters_remaining = wlen;
			for (int i = 0; i < wlen; i++)
			{
				constraint_str[i] = '_';
			}
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
		Character cg = new Character(guess);
		if (guessed.contains(cg)) throw new GuessAlreadyMadeException();
		guessed.add(guess);
		HashMap<Long, Set<String>> partition = new HashMap<Long, Set<String>>();

	//	print_set(words);
		//generate partitions
		Iterator<String> it  = words.iterator();
		while (it.hasNext())
		{
			add_to_partition(it.next(),partition,guess);
		}

		long best_ind = -1;
		Iterator<Long> itp = partition.keySet().iterator();
		while (itp.hasNext())
		{
			long ind = itp.next();
			if (is_compatible(ind) && is_better_part(ind,best_ind,partition)) best_ind = ind;
		}

		words = partition.get(best_ind);
//	 System.out.println("Best Index: "+best_ind);
	//	print_set(words);
		update_guesses(best_ind, guess);
		return words;
	}

	//updates current displayed correct guesses
	private void update_guesses(long ind, char guess)
	{
		constraint_map &= ind;
		last_guess_letters = partition_letters(ind);
		letters_remaining -= last_guess_letters;
		// System.out.println(last_guess_letters	+" "+letters_remaining+" "+ind);
		for (int i = 0; i < wlen; i++)
		{
			if (ind%2 == 1) constraint_str[i] = guess;
			ind /= 2;
		}
	}

	public void print_word()
	{
		System.out.print("Word: ");
		for (int i = 0; i < wlen; i++)
		{
			System.out.print(constraint_str[i]);
		}
		System.out.print("\n");
	}


	public boolean is_compatible(long ind)
	{
		if ((ind&constraint_map) == 0) return true;
		else return false;
	}

	//Is p1 better than p2?
	public boolean is_better_part(long p1_num, long p2_num, HashMap<Long, Set<String>> partition)
	{
		if (p2_num < 0) return true; //parition not initialized
		long p1_size = partition.get(p1_num).size();
		long p2_size = partition.get(p2_num).size();
		int p1_let = partition_letters(p1_num);
		int p2_let = partition_letters(p2_num);

		if (p1_size > p2_size) return true;
		else if (p1_size < p2_size) return false;
		// if (p1_num == 0) return true;
		// if (p2_num == 0) return false;
		if (p1_let < p2_let) return true;
		if (p2_let < p1_let) return false;
		return (p1_num > p2_num);
	}

	public static int partition_letters(long partition_num)
	{
		int tot = 0;
		while (partition_num !=0)
		{
			tot += partition_num%2;
			partition_num /= 2;
		}
		return tot;
	}

	//adds word to the proper partition
	public static void add_to_partition(String word, HashMap<Long, Set<String>> partition, char guess)
	{
		//get the index of the partition
		long i = partition_num(word, guess);
		if (!partition.containsKey(i)) partition.put(i, new HashSet<String>());
		(partition.get(i)).add(word);
	}

  //First: map all chars in word to 1 if they are guess, 0 otherwise
	//Interpret the result as a binary number, reading right to left
	//This is to make comparisons between two word classes easier later on
	public static long partition_num(String word, char guess)
	{
		long num = 0;
		for (int i = word.length()-1; i >=0; i--)
		{
			num *= 2;
			if (word.charAt(i) == guess)
			{
				num += 1;
			}
		}
		return num;
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

	//debugging helper
	public static void print_set(Set<String> s)
	{
		Iterator<String> it = s.iterator();
		System.out.print("\nset([");
		while(it.hasNext())
		{
			System.out.print(it.next()+" ");
		}
		System.out.println("])");
	}

	public int lastGuessLetters()
	{
		return last_guess_letters;
	}

	public boolean isWon()
	{
		return (letters_remaining == 0);
	}

	public String randWord()
	{
		Iterator<String> it = words.iterator();
		if (!it.hasNext()) return "";
		else return it.next();
	}
}
