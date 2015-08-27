package com.alex.test;

import java.util.Scanner;

public class BinSearchTree {

	private Node root;

	public BinSearchTree(int[] data) {
		root = new Node(data[0]);
		makeTree(root, data, 0);
	}

	public void printTree() {
		inorder(root);
		preorder(root);
	}

	private void inorder(Node node) {
		if (node == null)
			return;
		inorder(node.left);
		System.out.println(node.val + ", " + node.ht);
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

	Node insert(int val) {
		if (root == null) {
			root = new Node();
			root.val = val;
			root.ht = 0;
			root.left = root.right = null;
			return root;
		}
		Node n = insert(null, root, val);
		if (root.val > val) {
			int h = height(root.left);
			if (root.right == null)
				root.ht = h + 1;
			else
				root.ht = 1 + (h > root.right.ht ? h : root.right.ht);
		} else {
			int h = height(root.right);
			if (root.left == null)
				root.ht = 1 + h;
			else
				root.ht = 1 + (h > root.left.ht ? h : root.left.ht);
		}
		System.out.println("n: " + n.val + " " + banlanceFactor(n));
		if (n == null)
			return root;

		Node parent = findParent(root, n);
		while (isNormal(n)) {
			n = parent;
			if (n == null)
				return root;
			parent = findParent(root, n);
		}
		if (parent != null)
			System.out.println("parent: " + parent.val);
		root = makeBalance(root, parent, n);

		int leftH = height(root.left);
		int rightH = height(root.right);
		root.ht = 1 + (leftH > rightH ? leftH : rightH);
		return root;
	}

	Node findParent(Node root, Node n) {
		if (root == n)
			return null;
		if (root.left == n || root.right == n)
			return root;
		if (root.val > n.val)
			return findParent(root.left, n);
		else
			return findParent(root.right, n);
	}

	Node makeBalance(Node root, Node parent, Node n) {
		if (banlanceFactor(n) == 2) {
			switch (banlanceFactor(n.left)) {
			case -1:
				leftR(n, n.left);
				// fall throught
			case 1:
				if (root == n) {
					root = n.left;
				}
				rightR(parent, n);
				break;
			default:
				throw new IllegalArgumentException();
			}
		} else if (banlanceFactor(n) == -2) {
			switch (banlanceFactor(n.right)) {
			case 1:
				rightR(n, n.right);
				// fall throught
			case -1:
				if (root == n) {
					root = n.right;
				}
				leftR(parent, n);
				break;
			default:
				throw new IllegalArgumentException();
			}
		}
		return root;
	}

	void leftR(Node parent, Node child) {
		Node n = child.right;
		if (parent != null) {
			if (child == parent.left)
				parent.left = n;
			else
				parent.right = n;
		}
		child.right = n.left;
		n.left = child;
	}

	void rightR(Node parent, Node child) {
		Node n = child.left;
		if (parent != null) {
			if (child == parent.left)
				parent.left = n;
			else
				parent.right = n;
		}
		child.left = n.right;
		n.right = child;
	}

	int banlanceFactor(Node node) {
		if (node.left != null && node.right != null)
			return node.left.ht - node.right.ht;
		else if (node.left == null && node.right == null)
			return 0;
		else if (node.left != null)
			return node.left.ht + 1;
		else
			return -1 - node.right.ht;
	}

	boolean isNormal(Node node) {
		int bf = banlanceFactor(node);
		if (bf >= -1 && bf <= 1)
			return true;
		return false;
	}

	void checkBalance(Node root, boolean isLeft) {
		if (isLeft)
			checkBalance(root, root.left, isLeft);
		else
			checkBalance(root, root.right, isLeft);
		// else
		// return;
	}

	void checkBalance(Node parent, Node child, boolean isLeft) {
		if (child == null)
			return;
		if (isNormal(child)) {
			checkBalance(child, child.left, true);
			checkBalance(child, child.right, false);
		} else {
			return;
		}
	}

	Node insert(Node parent, Node child, int val) {
		if (child.val > val) {
			if (child.left == null) {
				child.left = create(child, val);
				return parent;
			} else {
				return insert(child, child.left, val);
			}
		} else {
			if (child.right == null) {
				child.right = create(child, val);
				return parent;
			} else {
				return insert(child, child.right, val);
			}
		}
	}

	Node create(Node parent, int val) {
		Node child = new Node();
		child.val = val;
		child.ht = 0;
		child.left = child.right = null;
		if (parent != null) {
			if (parent.val > val)
				parent.left = child;
			else
				parent.right = child;
		}
		return child;
	}

	int height(Node node) {
		if (node == null)
			return -1;
		int leftH = height(node.left);
		int rightH = height(node.right);
		node.ht = 1 + (leftH > rightH ? leftH : rightH);
		return node.ht;
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
		private int ht;

		public Node() {

		}

		public Node(int data, Node left, Node right, Node parent) {
			this.val = data;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}

		public Node(int data) {
			this(data, null, null, null);
		}
	}

	public static void main(String[] args) {

		try (Scanner sc = new Scanner(System.in)) {
			int size = sc.nextInt();
			int[] data = new int[size];
			for (int i = 0; i < size; i++)
				data[i] = sc.nextInt();
			BinSearchTree bst = new BinSearchTree(data);
			bst.insert(sc.nextInt());
			bst.printTree();
			//System.out.println(bst.isValid());
			//System.out.println(bst.sum());
		}
	}
}
