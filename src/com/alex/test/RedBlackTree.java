package com.alex.test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RedBlackTree {

	private Node root;
	
	public RedBlackTree() {
		root = null;
	}

	public void printTree() {
		if (root == null) {
			System.out.println("Null tree");
			return;
		}
		//preorder(root);
		System.out.println("BH: " + blackHeight(root));
		printTree(root, height(root));
		System.out.println();
	}

	private void printTree(Node n, int h) {
		if (n == null)
			return;
		Queue<Node> ns = new LinkedList<>();
		final Node DUMMY = new Node(-1);
		ns.offer(n);
		
		int nPerLayer = 1;
		int maxPerLayer = (int)Math.pow(2, h-1);
		int unit = last(root).toString().length() + 2;
		if ((unit & 1) != 0)
			unit++;
		
		for (int j = h; j > 0; j--) {
			for (int i = 0; i < nPerLayer; i++) {
				printIndent(nPerLayer, maxPerLayer, unit);
				Node node = ns.poll();
				if (node == DUMMY) {
					printDummy(unit);
					ns.offer(DUMMY);
					ns.offer(DUMMY);
				} else {
					printNode(node, unit);
					if (node.left == null)
						ns.offer(DUMMY);
					else
						ns.offer(node.left);
					if (node.right == null)
						ns.offer(DUMMY);
					else
						ns.offer(node.right);
				}
				printIndent(nPerLayer, maxPerLayer, unit);
			}
			System.out.println();
			nPerLayer *= 2;
		}
	}
	
	private void printIndent(int n, int max, int unit) {
		for (int i = 0; i < (max*unit/n-unit)/2; i++)
			System.out.print(" ");
	}
	
	private void printNode(Node n, int unit) {
		int len = n.toString().length();
		int pre, post;
		pre = post = (unit - len) / 2;
		if ((len & 1) != 0) {
			pre++;
		}
		for (int i = 0; i < pre; i++)
			System.out.print(" ");
		System.out.print(n);
		for (int i = 0; i < post; i++)
			System.out.print(" ");
	}
	
	private void printDummy(int unit) {
		for (int i = 0; i < unit; i++)
			System.out.print(" ");
	}
	
	private int height(Node n) {
		if (n == null)
			return 0;
		if (n.parent == null)
			n.layer = 1;
		else
			n.layer = n.parent.layer + 1;
		int left = height(n.left);
		int right = height(n.right);
		return 1 + (left > right ? left : right);
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
			if (!isRoot(node) && isRight(node) && node.left.val < node.parent.val)
				return false;
		}
		if (node.right != null) {
			if (node.right.val < node.val)
				return false;
			if (!isRoot(node) && isLeft(node) && node.right.val > node.parent.val)
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
		return node == node.parent.left;
	}

	private boolean isRight(Node node) {
		return node == node.parent.right;
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
	
	public void insert(int val) {
		insert(new Node(val));
	}
	
	private void insert(Node n) {
		Node p = null;
		Node toInsert = root;
		while (toInsert != null) {
			p = toInsert;
			if (toInsert.val > n.val)
				toInsert = toInsert.left;
			else
				toInsert = toInsert.right;
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
	
	public int higher(int val) {
		Node p = null;
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
			return n.val;
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
		Node p = null;
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
			return n.val;
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
		private int layer;
		private static final int INVALID = -1;
		
		public Node(int val, Node left, Node right, Node parent) {
			this.val = val;
			this.left = left;
			this.right = right;
			this.parent = parent;
			this.color = Color.BLACK;
			this.bh = 0;
			this.layer = 0;
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
			/*rbt.delete(4);
			rbt.printTree();*/
			
			System.out.println(rbt.first());
			System.out.println(rbt.last());
			System.out.println(rbt.lower(6));
			System.out.println(rbt.lower(9));
			System.out.println(rbt.higher(9));
			System.out.println(rbt.higher(4));
			rbt.printTree();
		}
	}
}