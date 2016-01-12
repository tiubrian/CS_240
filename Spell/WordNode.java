package spell;

public class WordNode implements ITrie.INode {

	WordNode[] nodes;
	public int count;

	public WordNode()
	{
		nodes = new WordNode[26];
		int i;
		for (i = 0; i < 26; i++)
		{
			nodes[i] = null;
		}
		count = 0;
	}

	public WordNode getn(char c)
	{
		if (c >= 'A' && c <= 'Z') c = (char)((int)c - 'A' + 'a');
		return nodes[c-'a'];
	}

	public void setn(char c, WordNode w)
	{
		nodes[c-'a'] = w;
	}

	public int getValue() {
		return count;
	}
}
