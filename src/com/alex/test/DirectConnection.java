package com.alex.test;

import java.util.Scanner;
import java.util.TreeSet;
import java.util.List;
import java.util.PriorityQueue;
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
		int nLeftSmaller;
		long distSumLeftSmaller;
		int nRightSmaller;
		long distSumRightSmaller;
		
		City(int d) {
			dist = d;
			pop = 0;
		}
		
		void setPop(int p) {
			pop = p;
		}
		/*@Override
		public boolean equals(Object o) {
			return dist == ((City)o).dist;
		}*/
		@Override
		public int compareTo(City c) {
			return pop - c.pop;
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
			long distsum = 0;
			for (int j = 0; j < ncity; j++) {
				int dist = sc.nextInt();
				distsum += dist;
				distOrder.add(new City(dist));
			}
			for (int j = 0; j < ncity; j++)
				distOrder.get(j).setPop(sc.nextInt());
			
			List<City> popOrder = new ArrayList<>(distOrder);
			Collections.sort(distOrder, new Comparator<City>() {
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
			Collections.sort(popOrder);
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
				long rightDistSum = bit.getSum(ncity - 1) - leftDistSum;
				int leftCities = rrbt.rank(c.dist) - 1;
				int rightCities = rrbt.size() - leftCities - 1;
				sum += c.pop * (rightDistSum - leftDistSum + (leftCities - rightCities) * (long)c.dist);
				sum %= MODULE;
				System.out.println(sum);
			}
			System.out.println(sum);
			/*dists.addAll(cities);
			Collections.sort(cities);
			
			BinIndexTree bt = new BinIndexTree(ncity);
			for (int j = 0; j < ncity; j++) {
				bt.update(j, cities.get(i).dist);
			}
			List<City> pops = new ArrayList<>(ncity);
			for (City c : dists) {
				int index = Collections.binarySearch(cities, c);
				while (cities.get(++index).compareTo(c) == 0)
					;
				index--; // number of cities with population <= c
				int ncitySamePop = 0;
				int pos = 0;
				if (pops.size() > 0) {
					pos = Collections.binarySearch(pops, c);
					if (pos < 0) {
						pos = -(pos + 1);
					} else {
						while (pops.get(++pos).compareTo(c) == 0)
							;
						while (pops.get(--pos).compareTo(c) == 0)
							ncitySamePop++;
						pos++;
					}
					pops.add(pos, c);
				}
				c.nLeftSmaller = pos;
				c.distSumLeftSmaller = bt.getSum(pos - 1);
				c.nRightSmaller = index - ncitySamePop - pos;
				c.distSumRightSmaller = distsum - c.distSumLeftSmaller;
			}
			long sum = 0;
			int j = 0;
			
			while (dists.size() > 1) {
				City c = dists.pollFirst();
				
				sum += c.pop * (distsum - (long)c.dist * (dists.size()+1));
				distsum -= c.dist;
				sum %= MODULE;

				//j++;
			}
			System.out.println(sum);*/
			//Queue<City> cs = new PriorityQueue<>(cities);
			//calc(cs);
		}
		sc.close();
	}
}
