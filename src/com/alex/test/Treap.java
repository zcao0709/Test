package com.alex.test;

import java.util.Scanner;

public class Treap {

	private Node root;
	private int size;
	
	public Treap() {
		root = null;
		size = 0;
	}
	
	public void add(int data, int tval, int hval) {
		Node n = new Node(data, tval, hval);
		if (root == null) {
			root = n;
			size++;
			return;
		}
		if (add(root, n))
			size++;
	}
	
	private boolean add(Node parent, Node n) {
		if (parent.tval > n.tval) {
			if (parent.left == null) {
				parent.left = n;
				n.parent = parent;
				heapify(parent, n);
				return true;
			} else {
				return add(parent.left, n);
			}
		} else if (parent.tval < n.tval) {
			if (parent.right == null) {
				parent.right = n;
				n.parent = parent;
				heapify(parent, n);
				return true;
			} else {
				return add(parent.right, n);
			}
		} else {
			return false;
		}
	}
	
	public void heapify(Node n) {
		if (n == null)
			return;
		if (n.left == null && n.right == null)
			return;
		if (n.left == null) {
			heapify(n, n.right);
			return;
		}
		if (n.right == null) {
			heapify(n, n.left);
			return;
		}
		if (n.left.hval < n.right.hval) {
			heapify(n, n.left);
			return;
		}
		if (n.left.hval > n.right.hval) {
			heapify(n, n.right);
			return;
		}
	}
	
	private void heapify(Node parent, Node child) {
		if (parent == null || child == null)
			return;
		if (parent.hval <= child.hval)
			return;
		
		Node n;
		if (parent.left == child)
			n = rightR(parent);
		else
			n = leftR(parent);
		
		heapify(n.parent, n);
	}
	
	public Node remove(int tval) {
		if (root.tval == tval)
			return removeFirst();
		return remove(root, tval);
	}
	
	private Node remove(Node parent, int tval) {
		if (parent == null)
			return null;
		if (parent.tval > tval)
			return remove(parent.left, tval);
		else if (parent.tval < tval)
			return remove(parent.right, tval);
		else {
			size--;
			if (parent.left == null && parent.right == null) {
				if (parent.parent.left == parent)
					parent.parent.left = null;
				else
					parent.parent.right = null;
				
			} else if (parent.left != null && parent.right != null) {
				if (parent.left.hval < parent.right.hval) {
					if (parent.parent.left == parent)
						parent.parent.left = parent.left;
					else
						parent.parent.right = parent.left;
					
					parent.left.parent = parent.parent;
					if (parent.left.right == null) {
						parent.left.right = parent.right;
						parent.right.parent = parent.left;
					} else {
						add(parent.left.right, parent.right);
					}
				} else {
					if (parent.parent.left == parent)
						parent.parent.left = parent.right;
					else
						parent.parent.right = parent.right;
					
					parent.right.parent = parent.parent;
					if (parent.right.left == null) {
						parent.right.left = parent.left;
						parent.left.parent = parent.right;
					} else {
						add(parent.right.left, parent.left);
					}
				}
				
			} else if (parent.left != null) {
				if (parent.parent.left == parent) {
					parent.parent.left = parent.left;
				} else {
					parent.parent.right = parent.left;
				}
				parent.left.parent = parent.parent;
			} else {
				if (parent.parent.left == parent) {
					parent.parent.left = parent.right;
				} else {
					parent.parent.right = parent.right;
				}
				parent.right.parent = parent.parent;
			}
			parent.parent = null;
			parent.left = null;
			parent.right = null;
			return parent;
		}
	}
	
	public Node removeFirst() {
		return remove(root, root.tval);
	}
	
	public int size() {
		return size;
	}
	
	private Node leftR(Node n) {
		if (n.right == null)
			return null;
		
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
		return right;
	}
	
	private Node rightR(Node n) {
		if (n.left == null)
			return null;
		
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
		return left;
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
	
	private static class Node {
		int data;
		int tval;
		int hval;
		
		Node parent;
		Node left;
		Node right;
	
		Node(int data, int tval, int hval) {
			this.data = data;
			this.tval = tval;
			this.hval = hval;
		
			parent = left = right = null;
		}
		@Override
		public String toString() {
			return "[" + data + "," + tval + "," + hval + "]";
		}
	}
	
	public static void main(String[] args) {
		Treap t = new Treap();
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		for (int i = 0;  i < n; i++) {
			int v = sc.nextInt();
			t.add(v, i+1, i+1);
		}
		System.out.println(t.size());
		t.inorder();
		System.out.println();
		t.preorder();
		System.out.println();
		t.postorder();
		
		/*int lowest = 0;
		int highest = n + 1;
		for (int i = 0; i < m; i++) {
			int op = sc.nextInt();
            int low = sc.nextInt();
            int high = sc.nextInt();
            int num = high - low + 1;
		}*/
	}
}
