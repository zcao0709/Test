package com.alex.test;

import java.util.Scanner;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ArrayList;

public class DirectConnection {

	private static final long MODULE = 1000000007;
	
	public static void calc(Queue<City> cities) {
		long sum = 0;
		while (cities.size() > 1) {
			City c = cities.poll();
			//System.out.println(c);
			long distsum = 0;
			int numCityOnRight = 0;
			for (City other : cities) {
				if (other.dist > c.dist) {
					numCityOnRight++;
					distsum += other.dist;
				} else {
					numCityOnRight--;
					distsum -= other.dist;
				}
			}
			distsum += (long)c.dist * -numCityOnRight;
			sum += (c.pop * distsum) % MODULE;
		}
		System.out.println(sum % MODULE);
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
			return c.pop - pop;
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
			calc(cs);
		}
		sc.close();
	}
}
