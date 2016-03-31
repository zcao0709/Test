package com.alex.test;

import java.util.Scanner;

public class TopologSort {
	
	private static class LinkedTable {
		int suc;
		LinkedTable next;
		
		LinkedTable(int s) {
			suc = s;
			next = null;
		}
	}
	
	private static class SeqTable {
		int[] count;
		LinkedTable[] top;
		static final LinkedTable ONE = new LinkedTable(0);
		
		SeqTable(int n) {
			count = new int[n+1];
			top = new LinkedTable[n+1];
		}
		
		void addRelation(int left, int right) {
			LinkedTable lt = new LinkedTable(right);
			lt.next = top[left];
			top[left] = lt;
			count[right]++;
		}
		
		// count[] is used as a linker, which links to next node 
		// whose all predecessors are printed
		void sort() {
			// T4
			int n = count.length - 1;
			count[0] = 0;
			int rear = 0;
			for (int k = 1; k < count.length; k++) {
				if (count[k] == 0) {
					count[rear] = k;
					rear = k;
				}
			}
			int front = count[0];
			
			while (true) {
				// T5
				if (front != 0) {
					System.out.print(front + ", ");
					n--;
					LinkedTable p = top[front];
					top[front] = null;
					// T6
					while (p != null) {
						count[p.suc]--;
						if (count[p.suc] == 0) {
							count[rear] = p.suc;
							rear = p.suc;
						}
						p = p.next;
					}
					// T7
					front = count[front];
				} else {
					// T8
					if (n > 0) {
						System.out.println("LOOP DETECTED IN INPUT:");
						System.out.println(this);
						for (int k = 1; k < count.length; k++) {
							count[k] = 0;
						}
						// T9
						for (int k = 1; k < count.length; k++) {
							LinkedTable p = top[k];
							top[k] = null;
							// T10
							while (p != null) {
								count[p.suc] = k;
								p = p.next;
							}
						}
						System.out.println(this);
						// T11
						int k;
						for (k = 1; k < count.length; k++) {
							if (count[k] != 0)
								break;
						}
						// T12, mark the loop
						do {
							top[k] = ONE;
							k = count[k];
						} while (top[k] == null);
						System.out.println(this);
						// T13, print the loop
						do {
							System.out.print(k + ", ");
							top[k] = null;
							k = count[k];
						} while (top[k] == ONE);
						// T14
						System.out.println(k);
					}
					//System.out.printf("\nn = %d, rear = %d\n", n, rear);
					break;
				}
			}
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < count.length; i++) {
				sb.append(i).append(":").append(count[i]);
				LinkedTable p = top[i];
				while (p != null) {
					sb.append("-").append(p.suc);
					p = p.next;
				}
				sb.append("\n");
			}
			return sb.toString();
		}
	}

/*
9 10
9 2
3 7
7 5
5 8
8 6
4 6
1 3
7 4
9 5
2 8
*/
	public static void main(String[] args) {
		SeqTable st;
		
		try (Scanner sc = new Scanner(System.in)) {
			int n = sc.nextInt();
			st = new SeqTable(n);
		
			int m = sc.nextInt();
			for (int i = 0; i < m; i++) {
				int left = sc.nextInt();
				int right = sc.nextInt();
				st.addRelation(left, right);
			}
		}
		System.out.println(st);
		st.sort();
		System.out.println(st);
	}
}
