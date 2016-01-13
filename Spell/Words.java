package spell;

public class Words implements ITrie {

	public WordNode root;
	public static final int mod = 1000000007; // a prime
	private int wordCount;
	private int nodeCount;

	public Words()
	{
		root = new WordNode();
		wordCount = 0;
		nodeCount = 1;
	}

	public void add(String word) {
		WordNode curr_node = root;
		char c;
		word = word.toLowerCase();

		for (int i = 0; i < word.length(); i++) {
			c = word.charAt(i);
			if (curr_node.getn(c) == null) {
				curr_node.setn(c, new WordNode());
				nodeCount++;
			}
			curr_node = curr_node.getn(c);
		}
		curr_node.count++;
		wordCount++;
	}

	public ITrie.INode find(String word) {
		WordNode curr_node = root;
		char c;

		for (int i = 0; i < word.length(); i++) {
			c = word.charAt(i);
			if (curr_node.getn(c) == null) {
				return null; //word not in Trie
			}
			curr_node = curr_node.getn(c);
		}

		if (curr_node.count == 0) return null;
		else return curr_node;
	}

	public int getWordCount() {
		return wordCount;
	}

	public int getNodeCount() {
		return nodeCount;
	}

	public static int recNodeCount(WordNode start)
	{
		if (start == null) return 0;
		int tot = 1;

		for (char i='a';i<='z';i++) {
			tot += recNodeCount(start.getn(i));
		}
		return tot;
	}

	public static int recWordCount(WordNode start)
	{
		if (start == null) return 0;
		int tot = start.count;

		for (char i='a';i<='z';i++) {
			tot += recWordCount(start.getn(i));
		}
		return tot;
	}

	@Override
	public String toString()
	{
		StringBuilder curr_w = new StringBuilder(10);
		StringBuilder res = new StringBuilder(getWordCount()*6);
		recToString(curr_w,res,root);
		return res.toString();
	}

	private void recToString(StringBuilder curr_w, StringBuilder curr_res, WordNode curr_node)
	{
		if (curr_node == null) return;
		if (curr_node.getValue() > 0) {
			curr_res.append(curr_w);
			curr_res.append('\n');
		}

		for (char i = 'a'; i<='z'; i++)
		{
			curr_w.append(i);
			recToString(curr_w, curr_res, curr_node.getn(i));
			curr_w.deleteCharAt(curr_w.length()-1);
		}
	}

	@Override
	public int hashCode() {
		return recHashCode(root);
	}

	public int recHashCode(WordNode start)
	{
		if (start == null) return 1;
		int tot = 0;
		for (int i='a';i<='z';i++) {
			tot += i*recHashCode(start.getn((char)i));
			tot = tot % mod;
		}
		return tot;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Words) {
			Words w = (Words)o;
			if (w.getWordCount() != getWordCount() || w.getNodeCount() != getNodeCount()) return false;
			return recEquals(((Words)o).root,root);
		}
		else return false;
	}

	public boolean recEquals(WordNode start_o, WordNode start)
	{
		if (start_o == null || start == null) {
			return start_o==null && start == null;
		}
		if (start_o.count != start.count) return false;

		for (char i='a';i<='z';i++) {
			if (! recEquals(start_o.getn(i),start.getn(i))) return false;
		}

		return true;
	}



}
