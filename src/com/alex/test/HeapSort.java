package com.alex.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HeapSort {

	private HeapSort() {
		
	}
	
	public static void sort(int[] a) {
		buildMaxHeap(a);
		System.out.println(Arrays.toString(a));
		for (int i = a.length-1, size = a.length; i > 0; i--) {
			exch(a, i, 0);
			size--;
			heapify(a, 0, size);
			System.out.println(Arrays.toString(a) + size);
		}
	}
	
	private static void buildMaxHeap(int[] a) {
		for (int i = a.length/2 - 1; i >= 0; i--) {
			heapify(a, i, a.length);
		}
	}
	
	private static void heapify(int[] a, int i, int size) {
		int left = 2 * i + 1;
		int right = 2 * i + 2;
		int max = i;
		if (left < size && a[left] > a[i]) {
			max = left;
		}
		if (right < size && a[right] > a[max]) {
			max = right;
		}
		if (max != i) {
			exch(a, max, i);
			heapify(a, max, size);
		}
	}
	
	private static void exch(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
	
	public static void main(String[] args) {
		int size = 10;
		try (Scanner scan = new Scanner(System.in)) {
			size = scan.nextInt();
		}
		
		int [] a = new int[size];
		
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			a[i] = rand.nextInt(size*3);
		}
		System.out.println(Arrays.toString(a));
		sort(a);
		System.out.println(Arrays.toString(a));

	}

}
