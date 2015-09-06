package com.alex.test;

import java.util.Scanner;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ArrayList;

public class DirectConnection {

	private static final long MODULE = 1000000007;
	
	public static void calc(Queue<City> cities) {
		long distsum = 0;
		while (cities.size() > 1) {
			City c = cities.poll();
			for (City other : cities) {
				sum += calc(c, other);
			}
		}
		System.out.println(sum % MODULE);
	}	
	
	private static long calc(City c, City other) {
		long dist = Math.abs(c.dist - other.dist);
		int pop = c.pop > other.pop ? c.pop : other.pop;
		return dist * pop;
	}
	
	private static class City implements Comparable<City> {
		int dist;
		int pop;
		
		City(int d) {
			dist = d;
			pop = 0;
		}
		
		void setPop(int p) {
			pop = p;
		}
		@Override
		public int compareTo(City c) {
			return pop - c.pop;
		}
		@Override
		public String toString() {
			return dist + "," + pop;
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int ncase = sc.nextInt();
		for (int i = 0; i < ncase; i++) {
			int ncity = sc.nextInt();
			List<City> cities = new ArrayList<>(ncity);
			for (int j = 0; j < ncity; j++)
				cities.add(new City(sc.nextInt()));
			for (int j = 0; j < ncity; j++)
				cities.get(j).setPop(sc.nextInt());
			//System.out.println(cities);
			Queue<City> cs = new PriorityQueue<>(cities);
			calc(cities);
		}
	}
}
