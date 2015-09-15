package com.alex.test;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class RandomizedSelect {

	public static int randomizedSelect(int[] a, int rank) {
		if (rank < 1 || rank > a.length)
			throw new IllegalArgumentException();
		return randomizedSelect(a, 0, a.length-1, rank);
	}
	
	private static int randomizedSelect(int[] a, int left, int right, int rank) {
		if (left == right)
			return a[left];
		int q = QuickSort.randomPartition(a, left, right);
		int pos = q - left + 1;
		if (rank == pos)
			return a[q];
		else if (rank < pos)
			return randomizedSelect(a, left, q-1, rank);
		else
			return randomizedSelect(a, q+1, right, rank-pos);
	}
	
	public static void main(String[] args) {
		int size = 10;
		try (Scanner scan = new Scanner(System.in)) {
			size = scan.nextInt();
		}
		int[] a = new int[size];
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			a[i] = rand.nextInt(size*3);
		}
		System.out.println(Arrays.toString(a));
		System.out.println(randomizedSelect(a, 10));
		QuickSort.sort(a);
		System.out.println(Arrays.toString(a));
	}
}
