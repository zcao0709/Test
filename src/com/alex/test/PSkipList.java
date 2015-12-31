package com.alex.test;

public class PSkipList {
	
	private static final Object BASE_HEADER = new Object();
	private final Header head;
	private int size;
	
	public PSkipList() {
		head = new Header(null, null, null, 1);
		size = 0;
	}
	
	public void add(int key) {
		Node n = new Node(key, null);
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
