package com.alex.test;

import java.util.Scanner;

public class MaximumSubarray {

	public static void findMaxSub(int[] ar) {
		int maxNegtive = Integer.MIN_VALUE;
		int positiveSum = 0;
		int currSum = 0;
		int lastSum = -1;
		for (int i = 0; i < ar.length; i++) {
			if (ar[i] < 0) {
				if (ar[i] > maxNegtive)
					maxNegtive = ar[i];
				if (currSum + ar[i] < 0) {
					currSum = 0;
					continue;
				}
			}
			if (ar[i] > 0)
				positiveSum += ar[i];
			currSum += ar[i];
			if (currSum > lastSum)
				lastSum = currSum;
		}
		if (lastSum < 0)
			System.out.println(maxNegtive + " " + maxNegtive);
		else
			System.out.println(lastSum + " " + positiveSum);
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int ncase = sc.nextInt();
		
		for (int i = 0; i < ncase; i++) {
			int size = sc.nextInt();
			int[] ar = new int[size];
			for (int j = 0; j < size; j++)
				ar[j] = sc.nextInt();
			findMaxSub(ar);
		}
		sc.close();
	}
}
