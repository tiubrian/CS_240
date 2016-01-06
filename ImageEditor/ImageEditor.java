import java.io.*;

public class ImageEditor
{
	private Pixel[][] PixelMap;
	private int height;
	private int width;
	public ImageEditor()
	{
		height = 0;
		width = 0;
	}

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
			ImageEditor editor = new ImageEditor();
			editor.read_input_ppm(args[0]);

			// then determine and perform the required image transformation
			switch (args[2])
			{
				case "grayscale":
					editor.grayscale();
					break;
				case "invert":
					editor.invert();
					break;
				case "emboss":
					editor.emboss();
					break;
				case "motionblur":
					if (argc != 4)
					{
						verbose_die("Missing length argument for motionblur.");
					}

					editor.motionblur(Integer.parseInt(args[3].trim()));

					break;
				default:
					verbose_die("Invalid third argument " + args[2]+ " given, see usage.");
			}

			editor.save(args[1]);
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

	private void read_input_ppm(String filename)
	{
		int[] temp = new int[3]; //a temporary array to hold the last three pixel values

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

			f.readLine(); //skip max color value
			System.out.println("H: "+height + " W: " + width );
			PixelMap = new Pixel[height][width];
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
// 					System.out.println(temp[0]+ " " + temp[1] + " " + temp[2]);
					PixelMap[row][col] = new Pixel(temp[0], temp[1], temp[2]);
				}
			}

			f.close();
		}
		catch (Exception e)
		{
			verbose_die("Something went wrong when reading file: " + filename +
				".\nError Message: " + e.toString());
		}
	}

	public void save(String outfile)
	{
		String nl = System.getProperty("line.separator");

		//Each int value is represented with a maximum of three digits, plus a separating whitespace
		//There are 3 values in a pixel, width*height pixels, and hence 12*width*height characters are needed for the pixels.
		//3 bytes are needed for the initial P3, and I'm betting 17 are enough to handle the width and height\n
		//This is where width*height*12+20 comes from
		StringBuilder res = new StringBuilder(width*height*12+20);
		res.append("P3"+nl);
		res.append(width);
		res.append(" ");
		res.append(height+nl+255+nl); // take care of maximum color value

		int i,j;
		for (i = 0; i < height; i++)
		{
			for (j = 0; j < width; j++)
			{
				res.append(PixelMap[i][j].r.val+nl);
				res.append(PixelMap[i][j].g.val+nl);
				res.append(PixelMap[i][j].b.val+nl);
			}
		}

		try {
			FileWriter fw = new FileWriter(outfile);
			BufferedWriter f = new BufferedWriter(fw);
			f.append(res);
			f.close();
		}
		catch (Exception e)
		{
			verbose_die("Error writing to output file: " + e.getMessage());
		}
	}

	public void invert()
	{
		int i,j;
		for (i = 0; i < height; i++)
		{
			for (j = 0; j < width; j++)
			{
				PixelMap[i][j].invert();
			}
		}
	}

	public void grayscale()
	{
		int i,j;
		for (i = 0; i < height; i++)
		{
			for (j = 0; j < width; j++)
			{
				PixelMap[i][j].grayscale();
			}
		}
	}

	public void emboss()
	{
		int i,j;
		int V,rd,gd,bd;

		for (i = height-1; i >=0; i--)
		{
			for (j = width-1; j >=0; j--)
			{
				if (i-1<0 || j-1 < 0)
				{
					V = 128;
				}
				else
				{
					rd = PixelMap[i][j].r.val - PixelMap[i-1][j-1].r.val;
					gd = PixelMap[i][j].g.val - PixelMap[i-1][j-1].g.val;
					bd = PixelMap[i][j].b.val - PixelMap[i-1][j-1].b.val;
					if (Math.abs(rd) > Math.abs(gd)) {
						if (Math.abs(bd) > Math.abs(rd)) V = bd + 128;
						else V = rd + 128;
					}
					else {
						if (Math.abs(bd) > Math.abs(gd)) V = bd + 128;
						else V = gd + 128;
					}
					if (V<0) V = 0;
					if (V>255) V = 255;
					PixelMap[i][j].reset(V,V,V);
				}
			}
		}
	}

	public void motionblur(int blur)
	{
		int i,j,k;
		int off;

		for (i = 0;i<height; i++)
		{
			for (j = 0; j < height; j++)
			{
				int[] t_arr = {0,0,0};
				//ensure we don't go off the image
				off = Math.min(blur,height-j);
				for (k = j; k < j+off; k++)
				{
					t_arr[0] += PixelMap[i][k].r.val;
					t_arr[1] += PixelMap[i][k].g.val;
					t_arr[2] += PixelMap[i][k].b.val;
				}

				//average out values
				for (k = 0; k < 3; k++)
				{
					t_arr[k] /= off;
				}

				PixelMap[i][j].reset(t_arr[0],t_arr[1],t_arr[2]);
			}
		}
	}


}