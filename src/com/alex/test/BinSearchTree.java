package com.alex.test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BinSearchTree {

	private Node root;

	public BinSearchTree(int[] data) {
		root = new Node(data[0]);
		makeTree(root, data, 0);
	}
	
	public BinSearchTree() {
		root = null;
	}

	public void printTree() {
		if (root == null) {
			System.out.println("Null tree");
			return;
		}
		//preorder(root);
		printTree(root, height(root));
	}

	private void printTree(Node n, int h) {
		if (n == null)
			return;
		Queue<Node> ns = new LinkedList<>();
		final Node DUMMY = new Node(-1);
		ns.offer(n);
		
		int nPerLayer = 1;
		int maxPerLayer = (int)Math.pow(2, h-1);
		int unit = String.valueOf(last()).length() + 2;
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
					printVal(node.val, unit);
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
	
	private void printVal(int val, int unit) {
		int len = String.valueOf(val).length();
		int pre, post;
		pre = post = (unit - len) / 2;
		if ((len & 1) != 0) {
			pre++;
		}
		for (int i = 0; i < pre; i++)
			System.out.print(" ");
		System.out.print(val);
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

	public void insert(int val) {
		insert(new Node(val));
	}
	
	private void insert(Node n) {
		Node p = null;
		Node x = root;
		while (x != null) {
			p = x;
			if (x.val > n.val)
				x = x.left;
			else
				x = x.right;
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
	}
	
	public boolean delete(int val) {
		Node n = find(val);
		if (n == null)
			return false;
		delete(n);
		return true;
	}
	
	private void delete(Node n) {
		Node x;
		if (n.left == null || n.right == null) {
			x = n;
		} else {
			x = higher(n);
		}
		Node child = (x.left == null) ? x.right : x.left;
		if (child != null) {
			child.parent = x.parent;
		}
		if (x.parent == null) {
			root = child;
		} else {
			if (x == x.parent.left)
				x.parent.left = child;
			else
				x.parent.right = child;
		}
		
		if (x != n) {
			n.val = x.val;
		}
		x.parent = null;
		x.left = null;
		x.right = null;
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

	private void makeTree(Node parent, int[] data, int index) {
		int left = index * 2 + 1;
		if (left < data.length && data[left] != 0) {
			Node node = new Node(data[left]);
			parent.left = node;
			node.parent = parent;
			makeTree(node, data, left);
		}
		int right = index * 2 + 2;
		if (right < data.length && data[right] != 0) {
			Node node = new Node(data[right]);
			parent.right = node;
			node.parent = parent;
			makeTree(node, data, right);
		}
	}

	private static class Node {
		private int val;
		private Node left;
		private Node right;
		private Node parent;
		int layer;
		private static final int INVALID = -1;
		
		public Node(int val, Node left, Node right, Node parent) {
			this.val = val;
			this.left = left;
			this.right = right;
			this.parent = parent;
			this.layer = 0;
		}

		public Node(int val) {
			this(val, null, null, null);
		}
	}

	public static void main(String[] args) {

		try (Scanner sc = new Scanner(System.in)) {
			int size = sc.nextInt();
			BinSearchTree bst = new BinSearchTree();
			for (int i = 0; i < size; i++) {
				bst.insert(sc.nextInt());
				bst.printTree();
				System.out.println();
			}
			/*bst.delete(5);
			bst.printTree();*/
			bst.delete(4);
			bst.printTree();
			
			System.out.println(bst.first());
			System.out.println(bst.last());
			System.out.println(bst.lower(6));
			System.out.println(bst.lower(9));
			System.out.println(bst.higher(9));
			System.out.println(bst.higher(4));
			bst.printTree();


		}
	}
}
