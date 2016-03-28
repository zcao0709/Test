package com.alex.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Kundu {

	private static final long LIMIT = 1000000007;
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		Node[] nodes = new Node[num];
		for (int i = 0; i < num; i++) {
			nodes[i] = new Node(i + 1);
		}
		Set<Node> rootSet = new HashSet<>();
		String line = sc.nextLine();
		for (int i = 0; i < num - 1; i++) {
			line = sc.nextLine();
			String[] ops = line.split(" ");
			int op1 = Integer.parseInt(ops[0]) - 1;
			int op2 = Integer.parseInt(ops[1]) - 1;
			Node n1 = findRoot(nodes[op1]);
			Node n2 = findRoot(nodes[op2]);
			if (ops[2].equals("b")) {
				mergeNode(rootSet, n1, n2);
			} else {
				rootSet.add(n1);
				rootSet.add(n2);
			}
		}
		long total = 0;
		List<Node> roots = new ArrayList<>(rootSet);
		Collections.sort(roots, new Comparator<Node>() {
			public int compare(Node n1, Node n2) {
				return n1.size - n2.size;
				}
		});
		//System.out.println("trees " + roots.size());
		//System.out.println("tree " + roots);
		total += calc(roots, num);
		
		if (total >= LIMIT) {
			total %= LIMIT;
		}
		System.out.println(total);
		sc.close();
	}

	private static long calc(List<Node> roots, int num) {
		if (roots.size() < 3)
			return 0;
		if (roots.size() == 3) {
			long tmp = 1;
			for (Node n : roots)
				tmp *= n.size;
			return tmp;
		}
		long result = 0;
		while (roots.size() >= 3) {
			Node n = roots.remove(roots.size()-1);
			if (n.size == 1) {
				long left = roots.size() + 1;
				//System.out.println("left " + left);
				result += (left * (left-1) * (left-2) / 6);
				break;
			}
			num -= n.size;
			result += n.size * chooseTwo(roots, num);
		}
		return result;
	}

	private static long chooseTwo(List<Node> roots, int total) {
		long result = 0;

		for (int i = roots.size()-1; i > 0; i--) {
			Node n = roots.get(i);
			total -= n.size;
			result += (n.size * total);
		}
		return result;
	}

	private static class Node {
		int v;
		Node p;
		int rank = 0;
		int size;

		Node(int v) {
			this.v = v;
			p = this;
			size = 1;
		}

		public String toString() {
			return v + "[" + size + "]";
		}
	}

	private static void mergeNode(Set<Node> rootSet, Node c1, Node c2) {
		if (c1 == c2)
			return;
		if (c1.rank > c2.rank) {
			c2.p = c1;
			c1.size += c2.size;
			rootSet.add(c1);
			rootSet.remove(c2);
		} else {
			c1.p = c2;
			c2.size += c1.size;
			rootSet.add(c2);
			rootSet.remove(c1);
			if (c1.rank == c2.rank)
				c2.rank++;
		}
	}

	private static Node findRoot(Node n) {
		if (n.p != n) {
			return findRoot(n.p);
		} else {
			return n;
		}
	}
}
