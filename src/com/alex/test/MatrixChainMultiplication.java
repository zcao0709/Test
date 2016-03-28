package com.alex.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class MatrixChainMultiplication {
	int[] p;
	Map<Pair, Integer> m;
	Map<Pair, Integer> s;
	//int[][] m;
	//int[][] s;
	
	public MatrixChainMultiplication(int[] a) {
		p = a;
		int size = (p.length-1)*(p.length)*2/3;
		m = new HashMap<>(size);
		s = new HashMap<>(size);
		//m = new int[p.length-1][p.length-1];
		//s = new int[p.length-1][p.length-1];
	}
	
	public String optimalParens() {
		matrixChainOrder();
		StringBuilder sb = new StringBuilder();
		return optimalParens(sb, 0, p.length-2);
	}
	
	private String optimalParens(StringBuilder sb, int i, int j) {
		if (i == j)
			sb.append("A").append(i);
		else {
			sb.append("(");
			optimalParens(sb, i, s.get(new Pair(i, j)));
			optimalParens(sb, s.get(new Pair(i, j))+1, j);
			sb.append(")");
		}
		return sb.toString();
	}
	
	private void matrixChainOrder() {
		int n = p.length - 1;
		for (int i = 0; i < n; i++) {
			m.put(new Pair(i, i), 0);
		}
		for (int c = 2; c <= n; c++) {
			 for (int i = 0; i <= n - c; i++) {
				 int j = i + c - 1;
				 m.put(new Pair(i, j), Integer.MAX_VALUE);
				 for (int k = i; k < j; k++) {
					 int q = m.get(new Pair(i, k)) + m.get(new Pair(k+1, j)) + p[i]*p[k+1]*p[j+1];
					 if (q < m.get(new Pair(i, j))) {
						 m.put(new Pair(i, j), q);
						 s.put(new Pair(i, j), k);
					 }
				 }
			 }
		}
		System.out.println(m);
		System.out.println(s);
	}
	
	private static class Pair {
		final int x;
		final int y;
		
		Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
		@Override
		public boolean equals(Object o) {
			Pair p = (Pair)o;
			return x == p.x && y == p.y;
		}
		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
		@Override 
		public String toString() {
			return x + ":" + y;
		}
	}

	//7
	//30 35 15 5 10 20 25
	public static void main(String[] args) {
		try (Scanner scan = new Scanner(System.in)) {
			int size = scan.nextInt();
			int[] a = new int[size];
			for (int i = 0; i < a.length; i++)
				a[i] = scan.nextInt();
			
			MatrixChainMultiplication mcm = new MatrixChainMultiplication(a);
			System.out.println(mcm.optimalParens());
		}
	}
}
