public class ImageEditor
{
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
						die_with_usage();
					}
					break;
				default:
					die_with_usage();
			}
		}

		System.out.println("Number of arguments: " + args.length);
	}

	private static void die_with_usage()
	{
		System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
		System.exit(1);
	}
}