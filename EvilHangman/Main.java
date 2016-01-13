import java.io.Console;

public class Main {

	public static void main(String[] args)
	{
		Console c = System.console();
		while (true)
		{
			String nguess = c.readLine("Enter Next Guess: ");
			System.out.println("You entered " + nguess + " , a string of length " + nguess.length());
		}
	}

}
