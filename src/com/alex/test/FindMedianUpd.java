package com.alex.test;

import java.util.Scanner;
import java.util.TreeSet;

public class FindMedianUpd {

	private TreeSet<Elem> tree;
	private Elem mid;
	private int balance;
	private int smaller;
	private int bigger;

	public FindMedianUpd() {
		tree = new TreeSet<>();
		mid = null;
		balance = 0;
		smaller = 0;
		bigger = 0;
	}
	
	public void add(long val) {
		add(new Elem(val));
	}
	
	public void remove(long val) {
		remove(new Elem(val));
	}
	
	public void median() {
		System.out.println(mid + ": " + tree);
		System.out.println(smaller + " SB " + bigger);
		
		if (size() == 0)
			System.out.println("Wrong!");
		else if ((size() & 1) != 0)
			System.out.println(mid.val);
		else {
			long first = mid.val;
			long sum = first + midCeiling().val;
			if ((sum & 1) != 0)
				System.out.printf("%.1f\n", ((double)sum) / 2);
			else
				System.out.println(sum / 2);
		}
	}
	
	public int size() {
		if (tree.size() == 0)
			return 0;
		return smaller + bigger + mid.num;
	}
	
	private Elem midCeiling() {
		if (mid.num == 1)
			return tree.higher(mid);
		if (smaller == bigger)
			return mid;
		if (Math.abs(smaller - bigger) == mid.num)
			return tree.higher(mid);
		return mid;
	}

	private void add(Elem e) {
		if (tree.size() == 0) {
			mid = e;
			tree.add(e);
			return;
		} else {
			Elem elem = tree.ceiling(e);
			if (elem != null && elem.equals(e))
				e = elem;
			
			if (e.val > mid.val) {
				balance++;
				bigger++;
				tree.add(e);
			} else if (e.val < mid.val) {
				balance--;
				smaller++;
				tree.add(e);
			} else {
				balance++;
				mid.num++;
			}
		}
		makeBalance();	
	}

	private void makeBalance() {
		if (bigger == smaller) {
			if (smaller != 0 && mid.num == 0)
				moveLeft();
			return;
		}
		if (bigger > smaller+mid.num) {
			moveRight();
			return;
		}
		if (smaller >= bigger+mid.num) {
			moveLeft();
			return;
		}
		/*if (balance > 1) {
			balance -= 2;
			moveRight();
		} else if (balance < 0) {
			balance += 2;
			moveLeft();
		}*/
	}

	private void moveRight() {
		//if (mid.num == 1)
		int i = mid.num;
		mid = tree.higher(mid);
		if (mid == null)
			return;
		smaller += i;
		bigger -= mid.num;
	}

	private void moveLeft() {
		//if (mid.num == 1)
		int i = mid.num;
		mid = tree.lower(mid);
		if (mid == null)
			return;
		smaller -= mid.num;
		bigger += i;
	}

	private void remove(Elem e) {
		if (tree.size() == 0)
			return;
		Elem elem = tree.ceiling(e);
		if (elem != null && elem.equals(e))
			e = elem;
		else
			return;

		if (e.val > mid.val) {
			balance--;
			bigger--;
			tree.remove(e);
		} else if (e.val < mid.val) {
			balance++;
			smaller--;
			tree.remove(e);
		} else {
			balance--;
			if (mid.num == 1)
				tree.remove(mid);
			mid.num--;
		}
		makeBalance();
	}

	private static class Elem implements Comparable<Elem> {
		final long val;
		int num;

		Elem(long val) {
			this.val = val;
			num = 1;
		}

		@Override
		public int compareTo(Elem e) {
			if (val > e.val)
				return 1;
			else if (val < e.val)
				return -1;
			else
				return 0;
		}

		@Override
		public boolean equals(Object o) {
			Elem e = (Elem)o;
			return 0 == compareTo(e);
		}
		
		@Override
		public String toString() {
			//return String.valueOf(val);
			return val + "," + num;
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		String line = sc.nextLine();
		FindMedianUpd tree = new FindMedianUpd();
		
		for (int i = 0; i < n; i++) {
			line = sc.nextLine();
			String[] fs = line.split(" ");
			if (fs[0].equals("r")) {
				tree.remove(Integer.parseInt(fs[1]));
				tree.median();
			} else {
				tree.add(Integer.parseInt(fs[1]));
				tree.median();
			}
		}
		sc.close();
	}
}
