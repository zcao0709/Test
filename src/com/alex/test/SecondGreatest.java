package com.alex.test;

import java.util.Arrays;
import java.util.Scanner;

public class SecondGreatest {
	
	public static int secondGreatest(int[] a) {
		if (a.length < 2) 
			throw new IllegalArgumentException();
		int first = a[0], second = a[0];
		if (a[1] > a[0])
			first = a[1];
		else
			second = a[1];
		for (int i = 2; i < a.length; i += 2) {
			if (i == a.length-1) {
				if (a[i] > first)
					return first;
				else if (a[i] < second)
					return second;
				else
					return a[i];
			}
			int max = a[i], min = a[i];
			if (a[i+1] > a[i])
				max = a[i+1];
			else
				min = a[i+1];
			if (max > second) {
				if (min >= first) {
					first = max;
					second = min;
				} else {
					if (max > first) {
						second = first;
						first = max;
					} else {
						second = max;
					}
				}
			}
		}
		return second;
	}

	public static void main(String[] args) {
		int size = 10;
		try (Scanner scan = new Scanner(System.in)) {
			size = scan.nextInt();
			///*
			int[] a = new int[size];
			for (int i = 0; i < size; i++) {
				a[i] = scan.nextInt();
			}
			System.out.println(Arrays.toString(a));
			System.out.println(secondGreatest(a));
			QuickSort.sort(a);
			System.out.println(Arrays.toString(a));
			//*/
		}
		/*
		int[] a = new int[size];
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			a[i] = rand.nextInt(size*3);
		}
		System.out.println(Arrays.toString(a));
		System.out.println(secondGreatest(a));
		QuickSort.sort(a);
		System.out.println(Arrays.toString(a));
		*/
	}
}
