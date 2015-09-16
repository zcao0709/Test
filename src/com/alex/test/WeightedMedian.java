package com.alex.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WeightedMedian {
	
	private static int medianOfMedianPartition(List<Point> ps, int left, int right) {
		int[] aux = new int[right-left+1];
		for (int i = left, j = 0; i <= right; i++, j++) {
			aux[j] = i;
		}
		return medianOfMedianPartition(ps, aux, 0, aux.length-1);
	}
	
	private static int medianOfMedianPartition(List<Point> ps, int[] index, int left, int right) {
		int len = index.length / 5;
		if (index.length % 5 != 0)
			len++;
		int[] aux = new int[len];
		int j = 0;
		for (int i = left; i <= right; i += 5) {
			int end = i + 4 > right ? right : i + 4;
			sort(ps, index, i, end);
			aux[j++] = index[(i+end)>>>1];
		}
		//System.out.println("medianOfMedianPartition");
		//System.out.println(Arrays.toString(index));
		//System.out.println(Arrays.toString(aux));
		//System.out.println(ps);
		if (aux.length == 1)
			return aux[0];
		return medianOfMedianPartition(ps, aux, 0, aux.length-1);
	}
	
	private static void sort(List<Point> ps, int[] a, int left, int right) {
		for (int i = left+1; i <= right; i++) {
			int key = a[i];
			int j = i - 1;
			while (j >= left && ps.get(a[j]).compareTo(ps.get(key)) > 0) {
				a[j+1] = a[j];
				j--;
			}
			a[j+1] = key;
		}
	}
	
	private static int partition(List<Point> ps, int left, int right) {
		int p = medianOfMedianPartition(ps, left, right);
		Collections.swap(ps, p, right);
		Point key = ps.get(right);
		//System.out.println("KEY: " + key);
		int i = left - 1;
		for (int j = left; j < right; j++) {
			if (ps.get(j).compareTo(key) <= 0) {
				i++;
				Collections.swap(ps, i, j);
			}
		}
		Collections.swap(ps, right, i + 1);
		//System.out.println("Partition" + (i+1) + ": " + ps.toString());
		return i + 1;
	}
	
	private static int weightedMedian(List<Point> ps, double th, int left, int right) {
		if (left >= right) {
			return left;
		}
		int p = partition(ps, left, right);
		int w = 0;
		for (int i = left; i < p; i++)
			w += ps.get(i).w;
		if (w < th && w+ps.get(p).w >= th) {
			return p;
		} else if (w+ps.get(p).w < th)
			return weightedMedian(ps, th-w-ps.get(p).w, p + 1, right);
		else
			return weightedMedian(ps, th, left, p - 1);
	}

	public static Point weightedMedian(List<Point> ps) {
		int w = 0;
		for (Point p : ps)
			w += p.w;
		System.out.println("WEIGHT: " + w);
		//double th = (double)w / 2;
		double th = w / 2;
		int p = weightedMedian(ps, th, 0, ps.size() - 1);
		System.out.println(th + " " + ps.get(p));
		
		Collections.sort(ps);
		weightedMedianBT(ps, th);
		return ps.get(p);
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
		int w = 0;
		for (int i = 0; i < minPoint.x; i++)
			w += ps.get(i).w;
		System.out.println(w + ", " + th + ", " + (w + minPoint.w));
	}
	
	private static class Point implements Comparable<Point> {
		int x;
		int w;
		
		Point(int x, int w) {
			this.x = x;
			this.w = w;
		}
		
		int weightedDistance(Point p) {
			return p.w * Math.abs(x - p.x);
		}
		
		@Override
		public String toString() {
			return x + "," + w;
		}
		
		@Override
		public int compareTo(Point p) {
			return x - p.x;
		}
	}
	
	public static void main(String[] args) {
		int size = 10;
		try (Scanner scan = new Scanner(System.in)) {
			size = scan.nextInt();
			List<Point> ps = new ArrayList<>(size);
			Random rand = new Random();
			for (int i = 0; i < size; i++) {
				if (rand.nextInt(100) % 2 == 0)
					ps.add(new Point(i, rand.nextInt(10)));
				else
					ps.add(new Point(i, 0));
			}
			Collections.shuffle(ps);
			System.out.println(ps.toString());
			weightedMedian(ps);
			//System.out.println(ps.toString());
		}
	}
}
