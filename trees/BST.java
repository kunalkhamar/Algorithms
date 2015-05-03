/**
 * Unbalanced BST 
 */

package trees;

public class BST<Key extends Comparable<Key>, Value> {

	private Node root;

	private class Node {
		private Key key;
		private Value val;
		private Node left, right;

		public Node(Key key, Value val) {
			this.key = key;
			this.val = val;
			this.left = null;
			this.right = null;
		}
	}

	public void put(Key key, Value val) {
		root = put(root, key, val);
	}
	
	public Node put(Node node, Key key, Value val) {
		if (node == null)
			return new Node(key, val);
		
		int cmp = key.compareTo(node.key);
		if (cmp < 0)
			node.left = put(node.left, key, val);
		else if (cmp > 0)
			node.right = put(node.right, key, val);
		else
			node.val = val;
		
		return node;
	}

	public Value get(Key key) {
		Node x = root;

		while (x != null) {
			int cmp = key.compareTo(x.key);
			if (cmp < 0)
				x = x.left;
			else if (cmp > 0)
				x = x.right;
			else if (cmp == 0)
				return x.val;
		}
		
		return null;
	}

	public void delete(Key key) {
		
	}
	
	public Key floor(Key key) {
		return floor(key, root);
	}

	public Key floor(Key key, Node x) {
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		
		if (cmp == 0)
			return x.key;
		if (cmp < 0)
			return floor(key, x.left);
		else { // if (cmp > 0)
			Key right = floor(key, x.right);
			if (right != null)
				return right;
			else
				return x.key;
		}
	}
	
	public void deleteMin() {
		root = deleteMin(root);
	}
	
	public Node deleteMin(Node x) {
		if (x == null)
			return null;
		if (x.left == null)
			return x.right;
		x.left = deleteMin(x.left);
		
		return x;
	}
	
}
