import java.io.*;

public class ImageEditor
{
	private Pixel[][] PixelMap;
	public static void main(String[] args)
	{
		int argc = args.length;
		if (argc < 3 || argc > 4)
		{
			die_with_usage();
		}
		else
		{
			// parse input file
			read_input_ppm(args[0]);

			// then determine and perform the required image transformation
			switch (args[2])
			{
				case "grayscale":
					break;
				case "invert":
					break;
				case "emboss":
					break;
				case "motionblur":
					if (argc != 4)
					{
						verbose_die("Missing length argument for motionblur.");
					}
					break;
				default:
					verbose_die("Invalid third argument " + args[2]+ " given, see usage.");
			}
		}

		System.out.println("Number of arguments: " + args.length);
	}

	private static void die_with_usage()
	{
		System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
		System.exit(1);
	}

	private static void verbose_die(String msg)
	{
		System.out.println(msg);
		die_with_usage();
	}

	private static void read_input_ppm(String filename)
	{
		int[] temp = new int[3]; //a temporary array to hold the last three pixel values
		int width = 0;
		int height = 0;

		try {
			File fil = new File(filename);
			FileReader freader = new FileReader(filename);
			BufferedReader f = new BufferedReader(freader);
			String s = f.readLine();
			String[] t_arr; // temporary array for strings

			// get height and width
			while (s != null)
			{
				t_arr = s.split("\\s+");
				s = f.readLine();
				if(t_arr.length >= 2)
				{
					try
					{
						width = Integer.parseInt(t_arr[0].trim());
						height = Integer.parseInt(t_arr[1].trim());
						System.out.println(width + " " + height);
					}
					catch (Exception e)
					{
						continue;
					}

					if (height==0 || width==0)	continue;
					break; // if we reached this point, we're ready to move on
				}
			}

			int row, col,i;
			for (row = 0; row < height; row++)
			{
				for (col = 0; col < width; col++)
				{
					for (i = 0; i < 3; i++)
					{
						temp[i] = Integer.parseInt(s.trim());
						s = f.readLine();
					}
					//now add new pixel
					System.out.println(temp[0] + " " + temp[1] + " " + temp[2] + " ");
				}
			}

		}
		catch (IOException e)
		{
			verbose_die("Something went wrong when reading file: " + filename +
				".\nError Message: " + e.getMessage());
		}
		finally
		{
			System.out.println("Survived");
		}
	}



}