package com.alex.test;

import java.util.Scanner;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
			if (dist > c.dist)
				return 1;
			else if (dist < c.dist)
				return -1;
			else
				return 0;
		}
		
		@Override
		public String toString() {
			return dist + ":" + pop;
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int ncase = sc.nextInt();
		for (int i = 0; i < ncase; i++) {
			int ncity = sc.nextInt();
			List<City> distOrder = new ArrayList<>(ncity);
			for (int j = 0; j < ncity; j++) {
				int dist = sc.nextInt();
				distOrder.add(new City(dist));
			}
			for (int j = 0; j < ncity; j++)
				distOrder.get(j).setPop(sc.nextInt());
			
			List<City> popOrder = new ArrayList<>(distOrder);
			Collections.sort(distOrder);
			Collections.sort(popOrder, new Comparator<City>() {
				@Override
				public int compare(City c1, City c2) {
					return c1.pop - c2.pop;
				}
			});
			
			BinIndexTree bit = new BinIndexTree(ncity);
			RankedRedBlackTree rrbt = new RankedRedBlackTree();
			long sum = 0;
			for (City c : popOrder) {
				int distRank = Collections.binarySearch(distOrder, c, new Comparator<City>() {
					@Override
					public int compare(City c1, City c2) {
						if (c1.dist > c2.dist)
							return 1;
						else if (c1.dist < c2.dist)
							return -1;
						else
							return 0;
					}
				});
				bit.update(distRank, c.dist);
				rrbt.insert(c.dist);
				if (rrbt.size() ==  1)
					continue;
				long leftDistSum = bit.getSum(distRank - 1);
				long rightDistSum = bit.getSum(ncity - 1) - leftDistSum - c.dist;
				int leftCities = rrbt.rank(c.dist) - 1;
				int rightCities = rrbt.size() - leftCities - 1;
				/*System.out.println(leftDistSum);
				System.out.println(rightDistSum);
				System.out.println(leftCities);
				System.out.println(rightCities);*/
				sum += (rightDistSum - leftDistSum 
						+ (leftCities - rightCities) 
						* (long)c.dist) * c.pop;
				sum %= MODULE;
			}
			System.out.println(sum);
		}
		sc.close();
	}
}
