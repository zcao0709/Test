package com.alex.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TwoDWeightedMedian {

	private static int medianOfMedianPartition(List<Point> ps, int left, int right, Comparator<Point> comp) {
		int[] aux = new int[right-left+1];
		for (int i = left, j = 0; i <= right; i++, j++) {
			aux[j] = i;
		}
		return medianOfMedianPartition(ps, aux, 0, aux.length-1, comp);
	}
	
	private static int medianOfMedianPartition(List<Point> ps, int[] index, int left, int right, Comparator<Point> comp) {
		int len = index.length / 5;
		if (index.length % 5 != 0)
			len++;
		int[] aux = new int[len];
		int j = 0;
		for (int i = left; i <= right; i += 5) {
			int end = i + 4 > right ? right : i + 4;
			sort(ps, index, i, end, comp);
			aux[j++] = index[(i+end)>>>1];
		}
		if (aux.length == 1)
			return aux[0];
		return medianOfMedianPartition(ps, aux, 0, aux.length-1, comp);
	}
	
	private static void sort(List<Point> ps, int[] a, int left, int right, Comparator<Point> comp) {
		for (int i = left+1; i <= right; i++) {
			int key = a[i];
			int j = i - 1;
			while (j >= left && comp.compare(ps.get(a[j]), ps.get(key)) > 0) {
				a[j+1] = a[j];
				j--;
			}
			a[j+1] = key;
		}
	}
	
	private static int partition(List<Point> ps, int left, int right, Comparator<Point> comp) {
		int p = medianOfMedianPartition(ps, left, right, comp);
		Collections.swap(ps, p, right);
		Point key = ps.get(right);
		int i = left - 1;
		for (int j = left; j < right; j++) {
			if (comp.compare(ps.get(j), key) <= 0) {
				i++;
				Collections.swap(ps, i, j);
			}
		}
		Collections.swap(ps, right, i + 1);
		return i + 1;
	}
	
	private static int weightedMedian(List<Point> ps, double th, int left, int right, Comparator<Point> comp) {
		if (left >= right) {
			return left;
		}
		int p = partition(ps, left, right, comp);
		int w = 0;
		for (int i = left; i < p; i++)
			w += ps.get(i).w;
		if (w < th && w+ps.get(p).w >= th) {
			return p;
		} else if (w+ps.get(p).w < th)
			return weightedMedian(ps, th-w-ps.get(p).w, p + 1, right, comp);
		else
			return weightedMedian(ps, th, left, p - 1, comp);
	}

	public static Point weightedMedian(List<Point> ps) {
		int w = 0;
		for (Point p : ps)
			w += p.w;
		System.out.println("WEIGHT: " + w);
		double th = (double)w / 2;
		Comparator<Point> xcomp = new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				return p1.x - p2.x;
			}
		};
		int p = weightedMedian(ps, th, 0, ps.size() - 1, xcomp);
		int x = ps.get(p).x;
		Comparator<Point> ycomp = new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				return p1.y - p2.y;
			}
		};
		p = weightedMedian(ps, th, 0, ps.size() - 1, ycomp);
		int y = ps.get(p).y;
		System.out.println(th + " " + x + "," + y);
		
		weightedMedianBT(ps, th);
		return new Point(x, y, 0);
	}
	
	private static void weightedMedianBT(List<Point> ps, double th) {
		long minDist = Long.MAX_VALUE;
		Point minPoint = null;
		for (Point p : ps) {
			long distSum = 0;
			for (Point other : ps) {
				distSum += p.weightedDistance(other);
			}
			//System.out.println(p + ":" + distSum);
			if (distSum < minDist) {
				minDist = distSum;
				minPoint = p;
			}
		}
		System.out.println(minPoint);
	}
	
	private static class Point implements Comparable<Point> {
		int x;
		int y;
		int w;
		
		Point(int x, int y, int w) {
			this.x = x;
			this.y = y;
			this.w = w;
		}
		
		int weightedDistance(Point p) {
			return p.w * (Math.abs(x - p.x) + Math.abs(y - p.y));
		}
		
		@Override
		public String toString() {
			return x + "," + y + ":" + w;
		}
		
		@Override
		public int compareTo(Point p) {
			return x - p.x;
		}
	}
	
	public static void main(String[] args) {
		try (Scanner scan = new Scanner(System.in)) {
			int size = scan.nextInt();
			List<Point> ps = new ArrayList<>(size * size);
			Random rand = new Random();
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (rand.nextInt(100) % 2 == 0)
						ps.add(new Point(i, j, rand.nextInt(10)));
					else
						ps.add(new Point(i, j, 0));
				}
			}
			Collections.shuffle(ps);
			System.out.println(ps.toString());
			weightedMedian(ps);
		}
	}
}
