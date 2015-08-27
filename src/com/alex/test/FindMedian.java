package com.alex.test;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class FindMedian {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Dheap dh = new Dheap((n&1) == 0 ? n/2 : n/2+1);

        for (int i = 0; i < n; i++) {
            dh.add(sc.nextInt());

            System.out.printf("%.1f\n", dh.median());
        }
        System.out.println(dh);
        sc.close();
	}
	
	private static class Dheap {
		PriorityQueue<Elem> minH; // bigger numbers
		PriorityQueue<Elem> maxH; // smaller numbers
		
		Dheap(int num) {
			minH = new PriorityQueue<>(num, new Comparator<Elem>() {
				@Override
				public int compare(Elem e1, Elem e2) {
					return e1.val - e2.val;
				}
			});
			
			maxH = new PriorityQueue<>(num, new Comparator<Elem>() {
				@Override
				public int compare(Elem e1, Elem e2) {
					return e2.val - e1.val;
				}
			});
		}
		
		void add(int val) {
			Elem e = new Elem(val);
			
			if (minH.size() == maxH.size()) {
				if (minH.size() == 0)
					minH.add(e);
				else if (val <= maxH.peek().val)
					maxH.add(e);
				else
					minH.add(e);
			} else if (minH.size() > maxH.size()) {
				if (val <= minH.peek().val)
					maxH.add(e);
				else {
					Elem minE = minH.poll();
					minH.add(e);
					maxH.add(minE);
				}
			} else {
				if (val >= maxH.peek().val)
					minH.add(e);
				else {
					Elem maxE = maxH.poll();
					maxH.add(e);
					minH.add(maxE);
				}
			}
		}
		
		float median() {
			if (minH.size() == maxH.size())
				return (float)(minH.peek().val + maxH.peek().val) / 2;
			else if (minH.size() > maxH.size())
				return (float)minH.peek().val;
			else
				return (float)maxH.peek().val;
		}
		
		public String toString() {
			return maxH.toString() + " " + minH.toString();
		}
	}
	
	private static class Elem {
		int val;
		
		Elem(int val) {
			this.val = val;
		}
		
		public String toString() {
			return String.valueOf(val);
		}
	}
}
