package com.alex.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WeightedMedian {
	
	private static int partition(List<Point> ps, int left, int right) {
		Point key = ps.get(right);
		int i = left - 1;
		for (int j = left; j < right; j++) {
			if (ps.get(j).compareTo(key) <= 0) {
				i++;
				Collections.swap(ps, i, j);
			}
		}
		Collections.swap(ps, right, i + 1);
		return i + 1;
	}
	
	public static void weightedMedian(List<Point> ps) {
		int w = 0;
		for (Point p : ps)
			w += p.w;
		double th = (double)w / 2;
		int p = weightedMedian(ps, th, 0, ps.size() - 1);
		System.out.println(th + " " + ps.get(p));
		
		Collections.sort(ps);
		System.out.println(ps.toString());
		weightedMedianBT(ps, th);
	}
	
	private static int weightedMedian(List<Point> ps, double th, int left, int right) {
		System.out.println("weightedMedian:" + left + "," + right);
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

	public static void weightedMedianBT(List<Point> ps, double th) {
		int minDist = Integer.MAX_VALUE;
		Point minPoint = null;
		for (Point p : ps) {
			int distSum = 0;
			for (Point other : ps) {
				distSum += p.weightedDistance(other);
			}
			System.out.println(p + ":" + distSum);
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
				ps.add(new Point(i, rand.nextInt(size*3)));
			}
			Collections.shuffle(ps);
			System.out.println(ps.toString());
			weightedMedian(ps);
			System.out.println(ps.toString());
		}
	}
}
