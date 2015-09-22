package com.alex.test;

import java.util.Scanner;

public class RedBlackTree {

	private Node root;
	private int size;
	
	public RedBlackTree() {
		root = null;
		size = 0;
	}

	public void printTree() {
		if (root == null) {
			System.out.println("Null tree");
			return;
		}
		//System.out.println("BH: " + blackHeight(root));
		printTree(root);
		System.out.println();
	}

	private void printTree(Node n) {
		if (n == null)
			return;
		int h = height(n, 0);
		int unit = last(n).toString().length() + 2;
		int broad = (int)Math.pow(2, h-1) * unit;
		
		StringBuilder sb = new StringBuilder();
		Node[] ns = new Node[1];
		ns[0] = n;
		for (int j = 1; j <= h; j++) {
			Node[] next = new Node[ns.length * 2];
			indent(ns.length, broad, unit, sb);
			for (int k = 0; k < ns.length; k++) {
				space(ns.length, broad, unit, sb);
				if (ns[k] != null) {
					stringlize(ns[k].toString(), unit, sb);
					next[2*k] = ns[k].left;
					next[2*k+1] = ns[k].right;
				} else
					stringlize("", unit, sb);
				space(ns.length, broad, unit, sb);
			}
			indent(ns.length, broad, unit, sb);
			sb.append("\n");
			ns = next;
		}
		System.out.println(sb);
	}
	
	private void indent(int n, int broad, int unit, StringBuilder sb) {
		for (int i = 0; i < (broad%n)/2; i++)
			sb.append(" ");
		int space = broad / n - unit;
		if ((space & 1) != 0)
			for (int i = 0; i < n / 2; i++)
				sb.append(" ");
	}
	
	private void space(int n, int broad, int unit, StringBuilder sb) {
		int space = broad / n - unit;
		for (int i = 0; i < space / 2; i++)
			sb.append(" ");
	}
	
	private void stringlize(String s, int unit, StringBuilder sb) {
		int len = s.length();
		if (len == 0) {
			for (int i = 0; i < unit; i++)
				sb.append(" ");
			return;
		}
		int pre, post;
		pre = post = (unit - len) / 2;
		if (((unit - len) & 1) != 0) {
			pre++;
		}
		for (int i = 0; i < pre; i++)
			sb.append(" ");
		sb.append(s);
		for (int i = 0; i < post; i++)
			sb.append(" ");
	}
	
	public int height() {
		return height(root, 0);
	}
	
	private int height(Node n, int h) {
		if (n == null)
			return h;
		int left = height(n.left, h+1);
		int right = height(n.right, h+1);
		return left > right ? left : right;
	}
	
	private int blackHeight(Node n) {
		if (n == null)
			return 0;
		int self = 1;
		if (isRed(n))
			self = 0;
		int left = blackHeight(n.left);
		int right = blackHeight(n.right);
		if (left != right)
			throw new IllegalArgumentException();
		return self + left;
	}
	
	/*public void chgColor(int val) {
		Node n = find(val);
		if (n.color == Color.BLACK)
			n.color  = Color.RED;
		else
			n.color  = Color.BLACK;
	}*/
	
	public void traverse() {
		traverse(root);
	}
	
	private void traverse(Node node) {
		Node prev = null;
		StringBuilder sb = new StringBuilder();
		
		while (node != null) {
			if (prev == node.parent) {
				sb.append(node).append(" ");
				prev = node;
				node = node.left != null ? node.left
						: (node.right != null ? node.right
						: node.parent);
			} else if (prev == node.left && node.right != null) {
				prev = node;
				node = node.right;
			} else {
				prev = node;
				node = node.parent;
			}
		}
		System.out.println(sb);
	}
	
	private void inorder(Node node) {
		if (node == null)
			return;
		inorder(node.left);
		System.out.println(node.val);
		inorder(node.right);
	}
	
	private void preorder(Node node) {
		if (node == null)
			return;
		System.out.println(node.val);
		preorder(node.left);
		preorder(node.right);
	}

	public boolean isValid() {
		return isValid(root);
	}
	
	private boolean isValid(Node node) {
		if (node == null)
			return true;
		if (node.left != null) {
			if (node.left.val > node.val)
				return false;
			if (isRight(node) && node.left.val < node.parent.val)
				return false;
		}
		if (node.right != null) {
			if (node.right.val < node.val)
				return false;
			if (isLeft(node) && node.right.val > node.parent.val)
				return false;
		}
		if (isValid(node.left) && isValid(node.right))
			return true;
		return false;
	}

	public int sum() {
		return sum(root);
	}

	private int sum(Node node) {
		if (node == null)
			return 0;
		return node.val + sum(node.left) + sum(node.right);
	}

	private boolean isLeft(Node node) {
		return node != root && node == node.parent.left;
	}

	private boolean isRight(Node node) {
		return node != root && node == node.parent.right;
	}

	private boolean isRoot(Node node) {
		return node == root;
	}

	private boolean isBlack(Node node) {
		return (node == null || node.color == Color.BLACK);
	}
	
	private boolean isRed(Node node) {
		return !isBlack(node);
	}
	
	public int size() {
		return size;
	}
	
	public boolean insert(int val) {
		return insert(new Node(val));
	}
	
	private boolean insert(Node n) {
		Node p = null;
		Node toInsert = root;
		while (toInsert != null) {
			p = toInsert;
			if (toInsert.val > n.val)
				toInsert = toInsert.left;
			else if (toInsert.val < n.val)
				toInsert = toInsert.right;
			else
				return false;
		}
		n.parent = p;
		if (p == null) {
			root = n;
		} else {
			if (p.val > n.val)
				p.left = n;
			else
				p.right = n;
		}
		n.color = Color.RED;
		fixInsert(n);
		size++;
		return true;
	}
	
	private void fixInsert(Node n) {
		while (isRed(n.parent)) {
			Node grand = n.parent.parent;
			if (n.parent == grand.left) {
				Node uncle = grand.right;
				if (isRed(uncle)) {
					n.parent.color = Color.BLACK;
					uncle.color = Color.BLACK;
					grand.color = Color.RED;
					n = grand;
				} else {
					if (n == n.parent.right) {
						leftRotate(n.parent);
						n = n.left;
					}
					n.parent.color = Color.BLACK;
					grand.color = Color.RED;
					rightRotate(grand);
				}
			} else {
				Node uncle = grand.left;
				if (isRed(uncle)) {
					n.parent.color = Color.BLACK;
					uncle.color = Color.BLACK;
					grand.color = Color.RED;
					n = grand;
				} else {
					if (n == n.parent.left) {
						rightRotate(n.parent);
						n = n.right;
					}
					n.parent.color = Color.BLACK;
					grand.color = Color.RED;
					leftRotate(grand);
				}
			}
		}
		root.color = Color.BLACK;
	}
	
	public boolean delete(int val) {
		Node n = find(val);
		if (n == null)
			return false;
		delete(n);
		return true;
	}
	
	private void delete(Node n) {
		Node toDelete;
		if (n.left == null || n.right == null) {
			toDelete = n;
		} else {
			toDelete = higher(n);
		}
		Node parent = toDelete.parent;
		Node child = (toDelete.left == null) ? toDelete.right : toDelete.left;
		if (child != null) {
			child.parent = parent;
		}
		if (parent == null) {
			root = child;
		} else {
			if (toDelete == parent.left)
				parent.left = child;
			else
				parent.right = child;
		}
		if (toDelete != n) {
			n.val = toDelete.val;
		}
		toDelete.parent = null;
		toDelete.left = null;
		toDelete.right = null;
		if (isBlack(toDelete))
			fixDelete(parent, child);
		size--;
	}
	
	private void fixDelete(Node p, Node n) {
		while (n != root && isBlack(n)) {
			if (n == p.left) {
				Node sibling = p.right;
				if (isRed(sibling)) {
					sibling.color = Color.BLACK;
					p.color = Color.RED;
					leftRotate(p);
					sibling = p.right;
				}
				if (isBlack(sibling.left) && isBlack(sibling.right)) {
					sibling.color = Color.RED;
					n = p;
					if (p != null)
						p = p.parent;
				} else {
					if (isBlack(sibling.right)) {
						sibling.left.color = Color.BLACK;
						sibling.color = Color.RED;
						rightRotate(sibling);
						sibling = p.right;
					}
					sibling.color = p.color;
					p.color = Color.BLACK;
					sibling.right.color = Color.BLACK;
					leftRotate(p);
					n = root;
				}
			} else {
				Node sibling = p.left;
				if (isRed(sibling)) {
					sibling.color = Color.BLACK;
					p.color = Color.RED;
					rightRotate(p);
					sibling = p.left;
				}
				if (isBlack(p.left) && isBlack(p.right)) {
					sibling.color = Color.RED;
					n = p;
					if (p != null)
						p = p.parent;
				} else {
					if (isBlack(sibling.left)) {
						sibling.right.color = Color.BLACK;
						sibling.color = Color.RED;
						leftRotate(sibling);
						sibling = p.left;
					}
					sibling.color = p.color;
					p.color = Color.BLACK;
					sibling.left.color = Color.BLACK;
					rightRotate(p);
					n = root;
				}
			}
		}
		n.color = Color.BLACK;
	}
	
	private void leftRotate(Node radix) {
		Node right = radix.right;
		radix.right = right.left;
		if (right.left != null)
			right.left.parent = radix;
		
		right.parent = radix.parent;
		if (right.parent == null)
			root = right;
		else {
			if (right.parent.val > right.val)
				right.parent.left = right;
			else
				right.parent.right = right;
		}
		right.left = radix;
		radix.parent = right;
	}
	
	private void rightRotate(Node radix) {
		Node left = radix.left;
		radix.left = left.right;
		if (left.right != null)
			left.right.parent = radix;
		
		left.parent = radix.parent;
		if (left.parent == null)
			root = left;
		else {
			if (left.parent.val > left.val)
				left.parent.left = left;
			else
				left.parent.right = left;
		}
		
		left.right = radix;
		radix.parent = left;
	}
	
	public boolean search(int val) {
		Node x = root;
		while (x != null) {
			if (x.val == val)
				return true;
			if (x.val > val)
				x = x.left;
			else
				x = x.right;
		}
		return false;
	}
	
	private Node find(int val) {
		Node x = root;
		while (x != null) {
			if (x.val == val)
				return x;
			if (x.val > val)
				x = x.left;
			else
				x = x.right;
		}
		return x;
	}
	
	public int first() {
		return first(root).val;
	}
	
	private Node first(Node x) {
		Node p = x;
		while (x != null) {
			p = x;
			x = x.left;
		}
		return p;
	}
	
	public int last() {
		return last(root).val;
	}
	
	private Node last(Node x) {
		Node p = x;
		while (x != null) {
			p = x;
			x = x.right;
		}
		return p;
	}
	
	private int keyOrNull(Node n) {
		if (n == null)
			return Node.INVALID;
		return n.val;
	}
	
	public int higher(int val) {
		return keyOrNull(higherNode(val));
		/*Node p = null;
		Node x = root;
		while (x != null) {
			if (x.val == val)
				break;
			p = x;
			if (x.val > val)
				x = x.left;
			else
				x = x.right;
		}
		if (x == null && p == null)
			return Node.INVALID;
		else if (x == null) {
			if (p.val > val)
				return p.val;
			else
				x = p;
		}
		Node n = higher(x);
		if (n == null)
			return Node.INVALID;
		else
			return n.val;*/
	}
	
	private Node higherNode(int val) {
		Node p = root;
		while (p != null) {
			if (val < p.val) {
				if (p.left != null)
					p = p.left;
				else
					return p;
			} else {
				if (p.right != null)
					p = p.right;
				else {
					Node parent = p.parent;
					Node child = p;
					while (parent != null && child == parent.right) {
						child = parent;
						parent = parent.parent;
					}
					return parent;
				}
			}
		}
		return null;
	}
	
	private Node higher(Node n) {
		if (n.right != null)
			return first(n.right);
		Node p = n.parent;
		while (p != null && p.right == n) {
			n = p;
			p = n.parent;
		}
		return p;
	}
	
	public int lower(int val) {
		return keyOrNull(lowerNode(val));
		/*Node p = null;
		Node x = root;
		while (x != null) {
			if (x.val == val)
				break;
			p = x;
			if (x.val > val)
				x = x.left;
			else
				x = x.right;
		}
		if (x == null && p == null)
			return Node.INVALID;
		else if (x == null) {
			if (p.val < val)
				return p.val;
			else
				x = p;
		}
		Node n = lower(x);
		if (n == null)
			return Node.INVALID;
		else
			return n.val;*/
	}
	
	private Node lowerNode(int val) {
		Node p = root;
		while (p != null) {
			if (val > p.val) {
				if (p.right != null)
					p = p.right;
				else
					return p;
			} else {
				if (p.left != null)
					p = p.left;
				else {
					Node parent = p.parent;
					Node child = p;
					while (parent != null && child == parent.left) {
						child = parent;
						parent = parent.parent;
					}
					return parent;
				}
			}
		}
		return null;
	}
	
	private Node lower(Node n) {
		if (n.left != null)
			return last(n.left);
		Node p = n.parent;
		while (p != null && p.left == n) {
			n = p;
			p = n.parent;
		}
		return p;
	}

	private static enum Color {
		RED("r"), BLACK("b");
		
		private String color;
		
		private Color(String c) {
			color = c;
		}
		
		@Override
		public String toString() {
			return color;
		}
	}
	
	private static class Node {
		private int val;
		private Node left;
		private Node right;
		private Node parent;
		private Color color;
		private int bh;
		private static final int INVALID = -1;
		
		public Node(int val, Node left, Node right, Node parent) {
			this.val = val;
			this.left = left;
			this.right = right;
			this.parent = parent;
			this.color = Color.BLACK;
			this.bh = 0;
			//this.layer = 0;
		}

		public Node(int val) {
			this(val, null, null, null);
		}
		
		@Override
		public String toString() {
			return val + color.toString();
		}
	}

	//9
	//11 2 14 1 15 7 5 8 4
	public static void main(String[] args) {

		try (Scanner sc = new Scanner(System.in)) {
			int size = sc.nextInt();
			RedBlackTree rbt = new RedBlackTree();
			for (int i = 0; i < size; i++) {
				rbt.insert(sc.nextInt());
				rbt.printTree();
			}
			
			rbt.delete(2);
			rbt.printTree();
			rbt.delete(4);
			rbt.printTree();
			/*
			rbt.traverse();
			
			System.out.println(rbt.first());
			System.out.println(rbt.last());
			System.out.println(rbt.lower(6));
			System.out.println(rbt.lower(9));
			System.out.println(rbt.higher(9));
			System.out.println(rbt.higher(4));
			System.out.println(rbt.size());
			rbt.printTree();
			*/
		}
	}
}