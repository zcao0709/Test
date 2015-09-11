package com.alex.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class QuickSort {
	
	private QuickSort() {
		
	}
	
	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		sort(list, 0, list.size()-1);
	}

	private static <T extends Comparable<? super T>> void sort(List<T> list, int min, int max) {
		if (min >= max)
			return;
		
		int mid = partition(list, min, max);
		System.out.println(min + " " + mid + " " + max + " " + list.toString());
		sort(list, min, mid);
		sort(list, mid+1, max);
	}
	
	private static <T extends Comparable<? super T>> int partition(List<T> list, int min, int max) {
		int key = getKey(min, max);
		T tag = list.get(key);
		
		int i = min;
		int j = max;
		while (true) {
			while (list.get(i).compareTo(tag) < 0)
				i++;
			while (list.get(j).compareTo(tag) > 0)
				j--;
			if (i >= j)
				return j;
			Collections.swap(list, i++, j--);
		}
	}
	
	private static int getKey(int min, int max) {
		return min;
	}
	
	public static void sort(int[] a) {
		sort(a, 0, a.length-1);
	}
	
	private static void sort(int[] a, int left, int right) {
		if (left < right) {
			int p = triplePartition(a, left, right);
			sort(a, left, p-1);
			sort(a, p+1, right);
		}
	}
	
	private static int partition(int[] a, int left, int right) {
		int key = a[right];
		int i = left - 1;
		for (int j = left; j < right; j++) {
			if (a[j] < key) {
				i++;
				exchange(a, i, j);
			}
		}
		exchange(a, i+1, right);
		return i + 1;
	}
	
	// package wise
	static int randomPartition(int[] a, int left, int right) {
		Random rand = new Random();
		int p = rand.nextInt(right - left + 1) + left;
		exchange(a, p, right);
		return partition(a, left, right);
	}
	
	private static int triplePartition(int[] a, int left, int right) {
		int[] p = new int[3];
		Random rand = new Random();
		p[0] = rand.nextInt(right - left + 1);
		p[1] = rand.nextInt(right - left + 1);
		p[2] = rand.nextInt(right - left + 1);
		InsertSort.sort(p);
		exchange(a, p[1]+left, right);
		return partition(a, left, right);
	}
	
	/* 
	 * hoarePartition is different with other partitions in
	 * hoarePartition doesn't put the pivot (a[left]) in the final place.
	 * So we should sort(a, left, j) and sort(a, j+1, right) then.
	 */
	private static int hoarePartition(int[] a, int left, int right) {
		int key = a[left];
		int i = left - 1;
		int j = right + 1;
		while (true) {
			do {
				--j;
			} while (a[j] > key);
			do {
				++i;
			} while (a[i] < key);
			if (i < j)
				exchange(a, i, j);
			else {
				return j;
			}
		}
	}
	
	private static void exchange(int[] a, int i, int j) {
		if (i == j)
			return;
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	public static void main(String[] args) {
		int size = 10;
		try (Scanner scan = new Scanner(System.in)) {
			size = scan.nextInt();
			/*int[] a = new int[size];
			for (int i = 0; i < size; i++) {
				a[i] = scan.nextInt();
			}
			System.out.println(Arrays.toString(a));
			sort(a);
			System.out.println(Arrays.toString(a));*/
		}
		int[] a = new int[size];
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			a[i] = rand.nextInt(size*3);
		}
		System.out.println(Arrays.toString(a));
		sort(a);
		System.out.println(Arrays.toString(a));
	}
}
