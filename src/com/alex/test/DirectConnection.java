package com.alex.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

//Enter-View(EV)is a linear,street-like country.By linear,we mean all the cities of the country are placed 
//on a single straight line-the x-axis.Thus every city'sposition can be defined by a single coordinate,xi,
//the distance from the left borderline of the country.You can treat all cities as single points.
//
//Unfortunately,the dictator of telecommunication of EV(Mr.S.Treat Jr.)is not an intelligent man.He doesn't
//know anything about the modern telecom technologies,except for peer-to-peer connections.Even worse,his thoughts 
//on peer-to-peer connections are extremely faulty:he believes that,if Pi people are living in city i,there must 
//be at least Pi cables from city i to every other city of EV-this way he can guarantee no congestion will ever occur!
//
//Mr.Treat is not good in math and computing.He hires an engineer to find out how much cable they need to implement
//this telecommunication system,given the coordination of the cities and their respective population.The connections
//between the cities can be shared.Look at the example for the detailed explanation.
//
//Input Format A number T is given in the first line and then comes T blocks,each representing a scenario.
//
//Each scenario consists of three lines.The first line indicates the number of cities(N).The second line indicates
//the co-ordinates of the N cities.The third line contains the population of each of the cities.The cities needn't
//be in increasing order in the input.
//
//Output Format For each scenario of the input,write the length of cable needed in a single line modulo 1,000,000,007.

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
