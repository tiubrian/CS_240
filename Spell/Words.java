package spell;

public class Words implements ITrie {

	public WordNode root;
	public static final int mod = 1000000007; // a prime

	public Words()
	{
		root = new WordNode();
	}

	public void add(String word) {
		WordNode curr_node = root;
		char c;

		for (int i = 0; i < word.length(); i++) {
			c = word.charAt(i);
			if (curr_node.getn(c) == null) {
				curr_node.setn(c, new WordNode());
			}
			curr_node = curr_node.getn(c);
		}
		curr_node.count++;
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
		return recWordCount(root);
	}

	public int getNodeCount() {
		return recNodeCount(root);
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
	public String toString() { return " "; }

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
		if (o instanceof Words) {
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
