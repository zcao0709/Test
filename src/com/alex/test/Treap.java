package com.alex.test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

// not working well
public class Treap {

	private Node root;
	
	public Treap() {
		root = null;
	}
	
	public void printTree() {
		if (root == null) {
			System.out.println("Null tree");
			return;
		}
		//preorder(root);
		printTree(root, height(root));
		System.out.println();
	}

	private void printTree(Node n, int h) {
		if (n == null)
			return;
		Queue<Node> ns = new LinkedList<>();
		final Node DUMMY = new Node(-1, -1);
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
	
	private boolean isLeft(Node node) {
		return node == node.parent.left;
	}

	private boolean isRight(Node node) {
		return node == node.parent.right;
	}

	private boolean isRoot(Node node) {
		return node == root;
	}

	public void insert(int val, int pri) {
		insert(new Node(val, pri));
	}
	
	private void insert(Node n) {
		Node p = null;
		Node x = root;
		while (x != null) {
			p = x;
			if (x.val > n.val) {
				x.size++;
				x = x.left;
			} else {
				x.size++;
				x = x.right;
			}
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
		fixInsert(n.parent);
	}
	
	public int deleteFirst() {
		int ret = root.val;
		delete(root);
		return ret;
	}
	
	public boolean delete(int val) {
		Node n = search(val);
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
		Node parent = x.parent;
		Node child = (x.left == null) ? x.right : x.left;
		if (child != null) {
			child.parent = x.parent;
		}
		if (parent == null) {
			root = child;
		} else {
			if (x == parent.left)
				parent.left = child;
			else
				parent.right = child;
		}
		while (parent != null) {
			parent.size--;
			parent = parent.parent;
		}
		
		if (x != n) {
			n.val = x.val;
			n.pri = x.pri;
			fixDelete(n);
		}
		x.parent = null;
		x.left = null;
		x.right = null;
	}
	
	private Node search(int val) {
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
	
	private void fixInsert(Node n) {
		if (n == null)
			return;
		if (n.left == null && n.right == null)
			return;
		if (n.left == null) {
			fixUp(n, n.right);
		} else if (n.right == null) {
			fixUp(n, n.left);
		} else {
			if (n.left.compareTo(n.right) < 0) {
				fixUp(n, n.left);
			} else {
				fixUp(n, n.right);
			}
		}
	}
	
	private void fixUp(Node parent, Node child) {
		if (parent == null || child == null)
			return;
		if (parent.compareTo(child) <= 0)
			return;
		
		if (parent.left == child)
			rightR(parent);
		else
			leftR(parent);
		
		fixUp(child.parent, child);
	}
	
	private void fixDelete(Node n) {
		if (n == null)
			return;
		if (n.parent != null && n.compareTo(n.parent) < 0) {
			fixUp(n.parent, n);
			return;
		}
		if (n.left == null) {
			fixDown(n, n.right);
		} else if (n.right == null) {
			fixDown(n, n.right);
		} else {
			if (n.left.compareTo(n.right) > 0)
				fixDown(n, n.left);
			else
				fixDown(n, n.right);
		}
		fixDelete(n);
	}
	
	private void fixDown(Node parent, Node child) {
		if (parent == null || child == null)
			return;
		if (parent.compareTo(child) <= 0)
			return;
		
		if (parent.left == child)
			rightR(parent);
		else
			leftR(parent);
	}
	
	public int size() {
		return root == null ? 0 : root.size;
	}
	
	private void leftR(Node n) {
		if (n.right == null)
			return;
		
		Node right = n.right;
		if (n.parent != null) {
			if (n.parent.left == n)
				n.parent.left = right;
			else
				n.parent.right = right;
		}
		right.parent = n.parent;
		
		n.right = right.left;
		if (right.left != null)
			right.left.parent = n;
		
		right.left = n;
		n.parent = right;
		
		if (n == root)
			root = right;
		
		n.size = getNodeSize(n.left) + getNodeSize(n.right) + 1;
		right.size = getNodeSize(right.left) + getNodeSize(right.right) + 1;
	}
	
	private int getNodeSize(Node n) {
		if (n == null)
			return 0;
		return n.size;
	}
	
	private void rightR(Node n) {
		if (n.left == null)
			return;
		
		Node left = n.left;
		if (n.parent != null) {
			if (n.parent.left == n)
				n.parent.left = left;
			else
				n.parent.right = left;
		}
		left.parent = n.parent;
		
		n.left = left.right;
		if (left.right != null)
			left.right.parent = n;
		
		left.right = n;
		n.parent = left;
		
		if (n == root)
			root = left;
		
		n.size = getNodeSize(n.left) + getNodeSize(n.right) + 1;
		left.size = getNodeSize(left.left) + getNodeSize(left.right) + 1;
	}
	
	private long sum(Node node) {
		if (node == null)
			return (long)0;
		return (long)node.val + sum(node.left) + sum(node.right);
	}
	
	public long getLeftSum() {
		return sum(root.left);
	}
	
	public long getRightSum() {
		return sum(root.right);
	}
	
	public int getLeftSize() {
		return root.left == null ? 0 : root.left.size;
	}
	
	public int getRightSize() {
		return root.right == null ? 0 : root.right.size;
	}
	
	public int getRootVal() {
		return root.val;
	}
	
	public int getRootPri() {
		return root.pri;
	}
	
	public void inorder() {
		inorder(root);
	}
	
	public void preorder() {
		preorder(root);
	}
	
	public void postorder() {
		postorder(root);
	}
	
	private void inorder(Node n) {
		if (n == null)
			return;
		inorder(n.left);
		System.out.println(n);
		inorder(n.right);
	}
	
	private void preorder(Node n) {
		if (n == null)
			return;
		System.out.println(n);
		preorder(n.left);
		preorder(n.right);
	}
	
	private void postorder(Node n) {
		if (n == null)
			return;
		postorder(n.left);
		postorder(n.right);
		System.out.println(n);
	}
	
	private static class Node implements Comparable<Node> {
		private int val;
		private int pri;
		private Node left;
		private Node right;
		private Node parent;
		private int layer;
		private int size;
		private static final int INVALID = -1;
	
		Node(int val, int pri) {
			this.val = val;
			this.pri = pri;
			this.size = 1;
		
			parent = left = right = null;
		}
		
		@Override
		public int compareTo(Node n) {
			if (n == null)
				return 1;
			return n.pri - this.pri;
		}
		
		@Override
		public String toString() {
			return "[" + val + "," + pri + "," + size + "]";
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int ncase = sc.nextInt();
		for (int i = 0; i < ncase; i++) {
			int ncity = sc.nextInt();
			Treap t = new Treap();
			int[] dist = new int[ncity];
			for (int j = 0; j < ncity; j++)
				dist[j] = sc.nextInt();
			for (int j = 0; j < ncity; j++) {
				t.insert(dist[j], sc.nextInt());
				//t.printTree();
			}
			long sum = 0;
			while (t.size() > 0) {
				long leftDist = t.getLeftSum();
				long rightDist = t.getRightSum();
				int leftSize = t.getLeftSize();
				int rightSize = t.getRightSize();
				sum += (long)t.getRootPri() 
						* (rightDist - leftDist + (long)t.getRootVal() * (leftSize - rightSize)) 
						% 1000000007;
				t.deleteFirst();
			}
			
			//t.printTree();
			System.out.println(sum);
		}
		sc.close();
	}
}
