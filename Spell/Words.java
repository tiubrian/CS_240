package spell;

public class Words implements ITrie {

	public WordNode root;

	public Words()
	{
		root = new WordNode();
	}

	public void add(String word) {
		Wordnode curr_node = root;
		char c;

		for (int i = 0; i < word.length(); i++) {
			c = word.charAt(i);
			if (curr_node[c] == null) {
				curr_node[c] = new WordNode();
			}
			curr_node = curr_node[c];
		}
		curr_node.count++;
	}

	public ITrie.INode find(String word) {
		Wordnode curr_node = root;
		public static final int mod = 1000000007; // a prime
		char c;

		for (int i = 0; i < word.length(); i++) {
			c = word.charAt(i);
			if (curr_node[c] == null) {
				return null; //word not in Trie
			}
			curr_node = curr_node[c];
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

	public static recNodeCount(WordNode start)
	{
		if (start == null) return 0;
		int tot = 1;

		for (i='a';i<='z';i++) {
			tot += recNodeCount(start.nodes[i]);
		}
		return tot;
	}

	public static recWordCount(WordNode start)
	{
		if (start == null) return 0;
		int tot = start.count;

		for (i='a';i<='z';i++) {
			tot += recWordCount(start.nodes[i]);
		}
		return tot;
	}

	@Override
	public String toString() {  }

	@Override
	public int hashCode() {
		return recHashCode(root);
	}

	public int recHashCode(WordNode start)
	{
		if (start == null) return 1;
		int tot = 0;
		for (int i='a';i<='z';i++) {
			tot += i*recHashCode(start.nodes[i]);
			tot = tot % mod;
		}
		return tot;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Itrie.Inode) {
			return recEquals(o.root,root);
		}
		else return false;
	}

	public boolean recEquals(WordNode start_o, WordNode start)
	{
		if (start_o == null || start == null) {
			return start_o==null && start == null;
		}
		if (start_o.count != start.count) return false;

		for (i='a';i<='z';i++) {
			if (! recEquals(start_o.nodes[i],start.nodes[i]) return false;
		}

		return true;
	}

}

public class WordNode implements ITrie.INode {

	WordNode[] nodes;
	public int count;

	public WordNode()
	{
		nodes = new WordNode['z'+1];
		int i;
		for (i = 0; i < 26; i++)
		{
			nodes['a'+i] = null;
		}
		count = 0;
	}

	public int getValue() {
		return count;
	}
}
