public class Color
{
	public int val;
	public Color(int value)
	{
		val = value;
	}

	public void invert()
	{
		val = 255 - val;
	}
}