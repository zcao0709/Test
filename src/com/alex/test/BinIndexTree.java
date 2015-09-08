package com.alex.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BinIndexTree {
	
	private long[] bit;
	
	public BinIndexTree(int n) {
		bit = new long[n + 1];
	}
	
	public void update(int index, long value) {
		index++;
		
		while (index < bit.length) {
			bit[index] += value;
			
			index += index & (-index);
		}
	}
	
	public void reset(int index) {
		update(index, -bit[index+1]);
	}
	
	public long getSum(int index) {
		long sum = 0;
		
		index++;
		while (index > 0) {
			sum += bit[index];
			index -= index & (-index);
		}
		return sum;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(bit);
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
		
			Collections.sort(cities);
			BinIndexTree bit = new BinIndexTree(ncity - 1);
			for (int j = 0; j < ncity-1; j++) {
				bit.update(j, (long)cities.get(j+1).dist - cities.get(j).dist);
			}
			long sum = 0;
			for (int j = 0; j < ncity-1; j++) {
				long subSum = 0;
				for (int k = j + 1; k < ncity; k++) {
					subSum += Math.abs(bit.getSum(k-1));
				}
				bit.reset(j);
				sum += subSum * cities.get(j).pop;
				sum %= 1000000007;
			}
			System.out.println(sum);
		}
		sc.close();
	}
}
