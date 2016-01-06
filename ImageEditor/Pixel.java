public class Pixel
{
	public Color r;
	public Color g;
	public Color b;
	public Pixel(int red, int green, int blue)
	{
		r = new Color(red);
		g = new Color(green);
		b = new Color(blue);
	}

	public void invert()
	{
		r.invert();
		g.invert();
		b.invert();
	}

	public void grayscale()
	{
		int avg = (r.val + b.val + g.val) / 3;
		r.val = avg;
		b.val = avg;
		g.val = avg;
	}

	public void reset(int new_r, int new_g, int new_b)
	{
		r.val = new_r;
		g.val = new_g;
		b.val = new_b;
	}

	public boolean equals(Pixel other)
	{
		return (r == other.r) && (g == other.g) && (b == other.b);
	}
}