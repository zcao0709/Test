package com.alex.test;

import java.util.Arrays;
import java.util.Scanner;

public class SegmentTree {

	private int[] st;
	private int size;
	
	public SegmentTree(int[] ar) {
		size = ar.length;
		
		if (size < 2) {
			st = new int[1];
		} else {
			int height = (int)Math.ceil(Math.log(size) / Math.log(2));
			int size = 2 * (int)Math.pow(2, height) - 1;
			st = new int[size];
		}
		construct(ar, 0, ar.length-1, 0);
	}
	
	private int construct(int[] ar, int ss, int se, int si) {
		if (ss == se) {
			st[si] = ar[ss];
		} else {
			int mid = getMid(ss, se);
			st[si] = construct(ar, ss, mid, si*2+1)
					+ construct(ar, mid+1, se, si*2+2);
		}
		return st[si];
	}
	
	private int getMid(int s, int e) {
		return (s + e) / 2;
	}
	
	public long getSum(int qs, int qe) {
		return getSum(0, size-1, qs, qe, 0);
	}
	
	private long getSum(int ss, int se, int qs, int qe, int index) {
		if (qs <= ss && qe >= se) {
			return (long)st[index];
		}
		if (qs > se || qe < ss) {
			return 0;
		}
		int mid = getMid(ss, se);
		return getSum(ss, mid, qs, qe, 2*index+1)
				+ getSum(mid+1, se, qs, qe, 2*index+2);
	}
	
	@Override
	public String toString() {
		return Arrays.toString(st);
	}
	
	public static void main(String[] args) {
		/*int[] a = {1, 3, 5, 7, 9, 11};
		SegmentTree st = new SegmentTree(a);
		System.out.println(st.toString());*/
		Scanner sc = new Scanner(System.in);
		int ncase = sc.nextInt();
		for (int i = 0; i < ncase; i++) {
			int ncity = sc.nextInt();
			int[] dist = new int[ncity-1];
			int start = sc.nextInt();
			for (int j = 1; j < ncity; j++) {
				int next = sc.nextInt();
				dist[j-1] = next - start;
				start = next;
			}
			int[] pop = new int[ncity];
			for (int j = 0; j < ncity; j++) {
				pop[j] = sc.nextInt();
			}
			if (ncity == 1) {
				System.out.println(0);
				continue;
			}
			SegmentTree st = new SegmentTree(dist);
			long sum = 0;
			for (int j = 0; j < ncity-1; j++) {
				for (int k = j; k < ncity-1; k++) {
					sum += (Math.abs(st.getSum(j, k)) * (pop[j] > pop[k+1] ? pop[j] : pop[k+1]));
					sum %= 1000000007;
				}
			}
			System.out.println(sum);
		}
		sc.close();
	}
}
