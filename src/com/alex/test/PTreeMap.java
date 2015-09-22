package com.alex.test;

import java.util.Random;
import java.util.Scanner;

public class PTreeMap<V> {
	
	private Entry<V> root;
	private int size;
	
	public PTreeMap() {
		root = null;
		size = 0;
	}

	public void printTree() {
		if (root == null) {
			System.out.println("Null tree");
			return;
		}
		System.out.println("SIZE: " + size);
		printTree(root);
		System.out.println();
	}

	private void printTree(Entry<V> e) {
		if (e == null)
			return;
		int h = height(e, 0);
		int unit = 6;
		//int unit = last(n).toString().length() + 2;
		int broad = (int)Math.pow(2, h-1) * unit;
		
		StringBuilder sb = new StringBuilder();
		@SuppressWarnings("unchecked")
		Entry<V>[] es = new Entry[1];
		es[0] = e;
		for (int j = 1; j <= h; j++) {
			@SuppressWarnings("unchecked")
			Entry<V>[] next = new Entry[es.length * 2];
			indent(es.length, broad, unit, sb);
			for (int k = 0; k < es.length; k++) {
				space(es.length, broad, unit, sb);
				if (es[k] != null) {
					stringlize(es[k].toString(), unit, sb);
					next[2*k] = es[k].left;
					next[2*k+1] = es[k].right;
				} else
					stringlize("", unit, sb);
				space(es.length, broad, unit, sb);
			}
			indent(es.length, broad, unit, sb);
			sb.append("\n");
			es = next;
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
	
	private int height(Entry<V> e, int h) {
		if (e == null)
			return h;
		int left = height(e.left, h+1);
		int right = height(e.right, h+1);
		return left > right ? left : right;
	}
	
	public V get(int key) {
		Entry<V> e = root;
		while (e != null) {
			if (key < e.key)
				e = e.left;
			else if (key > e.key)
				e = e.right;
			else
				return e.val;
		}
		return null;
	}
	
	public V put(int key, V val) {
		Entry<V> p = null;
		Entry<V> e = root;
		while (e != null) {
			if (key < e.key) {
				p = e;
				e = e.left;
			} else if (key > e.key) {
				p = e;
				e = e.right;
			} else {
				V old = e.val;
				e.val = val;
				return old;
			}
		}
		Entry<V> n = new Entry<V>(key, val);
		n.parent = p;
		if (p == null) {
			root = n;
		} else {
			if (n.key < p.key)
				p.left = n;
			else
				p.right = n;
		}
		n.color = Color.RED;
		fixPut(n);
		size++;
		return null;
	}
	
	public V remove(int key) {
		Entry<V> e = getEntry(key);
		if (e == null)
			return null;
		V old = e.val;
		remove(e);
		return old;
	}
	
	private Entry<V> getEntry(int key) {
		Entry<V> e = root;
		while (e != null) {
			if (key < e.key)
				e = e.left;
			else if (key > e.key)
				e = e.right;
			else
				return e;
		}
		return null;
	}
	
	private void remove(Entry<V> e) {
		
	}
	
	private void fixPut(Entry<V> e) {
		while (isRed(e.parent)) {
			Entry<V> g = e.parent.parent;
			if (e.parent == g.left) {
				if (isRed(g.right)) {
					e.parent.color = Color.BLACK;
					g.right.color = Color.BLACK;
					g.color = Color.RED;
					e = g;
				} else {
					if (e == e.parent.right) {
						leftRotate(e.parent);
						e = e.left;
					}
					e.parent.color = Color.BLACK;
					g.color = Color.RED;
					rightRotate(g);
				}
			} else {
				if (isRed(g.left)) {
					e.parent.color = Color.BLACK;
					g.left.color = Color.BLACK;
					g.color = Color.RED;
					e = g;
				} else {
					if (e == e.parent.left) {
						rightRotate(e.parent);
						e = e.right;
					}
					e.parent.color = Color.BLACK;
					g.color = Color.RED;
					leftRotate(g);
				}
			}
		}
		root.color = Color.BLACK;
	}
	
	private void leftRotate(Entry<V> e) {
		Entry<V> p = e.parent;
		Entry<V> right = e.right;
		e.right = right.left;
		if (right.left != null)
			right.left.parent = e;
		
		right.left = e;
		e.parent = right;
		
		right.parent = p;
		if (p == null)
			root = right;
		else {
			if (right.key < p.key)
				p.left = right;
			else
				p.right = right;
		}
	}
	
	private void rightRotate(Entry<V> e) {
		Entry<V> p = e.parent;
		Entry<V> left = e.left;
		e.left = left.right;
		if (left.right != null)
			left.right.parent = e;
		 
		left.right = e;
		e.parent = left;
		 
		left.parent = p;
		if (p == null)
			root = left;
		else {
			if (left.key < p.key) 
				p.left = left;
			else
				p.right = left;
		}
	}
	
	private boolean isRed(Entry<V> e) {
		return (e != null && e.color == Color.RED);
	}
	
	private boolean isBlack(Entry<V> e) {
		return !isRed(e);
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
	
	private static class Entry<V> implements Comparable<Entry<V>> {
		final int key;
		V val;
		Entry<V> left;
		Entry<V> right;
		Entry<V> parent;
		Color color;
		
		Entry(int k, V v) {
			key = k;
			val = v;
			left = right = parent = null;
		}
		
		@Override
		public int compareTo(Entry<V> e) {
			return key - e.key;
		}
		
		@Override
		public String toString() {
			return key + "," + val + color;
		}
	}
	
	//9
	//11 2 14 1 15 7 5 8 4
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			int size = sc.nextInt();
			PTreeMap<Integer> tree = new PTreeMap<>();
			Random rand = new Random();
			for (int i = 0; i < size; i++) {
				int n = sc.nextInt();
				tree.put(n, new Integer(rand.nextInt(n*2)));
				tree.printTree();
			}
			System.out.println(tree.get(14));
		}
	}

}
