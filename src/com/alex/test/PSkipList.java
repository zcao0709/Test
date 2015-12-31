package com.alex.test;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PSkipList {
	
	private static final Object BASE_HEADER = new Object();
	private Header head;
	private int size;
	
	public PSkipList() {
		head = new Header(null, null, null, 1);
		size = 0;
	}
	
	private Node findPredecessor(Node node) {
		for (Index i = head, r = i.right, d = null; ;) {
			if (r != null) {
				Node n = r.node;
				if (n.key < node.key) {
					i = r;
					r = i.right;
					continue;
				}
			}
			d = i.down;
			if (d == null)
				return i.node;
			i = d;
			r = i.right;
		}
	}
	
	public void add(int key) {
		Node n = new Node(key, null);
		Node pre = findPredecessor(n);
		n.next = pre.next;
		pre.next = n;
		
		int rnd = new Random().nextInt();
        if ((rnd & 0x80000001) == 0) {
            int level = 1;
            while (((rnd >>>= 1) & 1) != 0)
                ++level;
            Index i = null;
            if (level < head.level) {
            	for (int j = 1; j <= level; j++)
            		i = new Index(n, null, i);
            } else {
            	level = head.level + 1;
            	for (int j = 1; j <= level; j++)
            		i = new Index(n, null, i);
            	for (int j = head.level + 1; j <= level; j++) {
            		head = new Header(head.node, null, head, j);
            	}
            }
            
        }
	}
	
	public boolean remove(int key) {
		return false;
	}
	
	public boolean contains(int key) {
		return false;
	}
	
	public int size() {
		return size;
	}
	
	public String toString() {
		return "";
	}
	
	
	static final class Node {
		int key;
		Node next;
		
		public Node(int key, Node next) {
			this.key = key;
			this.next = next;
		}
	}
	
	static class Index {
		final Node node;
		Index right;
		Index down;
		
		public Index(Node node, Index right, Index down) {
			this.node = node;
			this.right = right;
			this.down = down;
		}
	}
	
	static class Header extends Index {
		final int level;

		public Header(Node node, Index right, Index down, int level) {
			super(node, right, down);
			this.level = level;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
