package com.alex.test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Trie {

	private Node root;
	
	public Trie() {
		root = new Node();
	}
	
	public String insert(String s) {
		//System.out.println("insert" + s);
		Node n = root;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int index = getIndex(c);
			if (n.childs[index] == null) {
				n.childs[index] = new Node(c);
				if (i == s.length()-1)
					n.childs[index].stop = true;
			} else {
				if (n.childs[index].stop) {
					return s;
				}
			}
			n = n.childs[c - 'a'];
		}
		return null;
	}
	
	private int getIndex(char c) {
		if (c >= 'a' && c <= 'j')
			return c - 'a';
		throw new IllegalArgumentException();
	}
	
	public void printTrie() {
		Queue<Node> q = new LinkedList<>();
		q.offer(root);
		while (q.size() > 0) {
			Node n = q.poll();
			System.out.println(n);
			for (int i = 0; i < n.childs.length; i++) {
				if (n.childs[i] != null)
					q.offer(n.childs[i]);
			}
		}
	}
	
	private static class Node {
		char c;
		Node parent;
		Node[] childs;
		boolean stop;
		
		Node() {
			this(' ');
		}
		
		Node (char c) {
			this.c = c;
			parent = null;
			childs = new Node['j'-'a'+1];
			stop = false;
		}
		
		@Override
		public String toString() {
			return c + Arrays.toString(childs);
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		Trie t = new Trie();
		for (int i = 0; i < n ; i++) {
			String s = t.insert(sc.next());
			if (s != null) {
				System.out.println("BAD SET");
				System.out.println(s);
				return;
			}
		}
		//t.printTrie();
		System.out.println("GOOD SET");
		sc.close();
	}
}
