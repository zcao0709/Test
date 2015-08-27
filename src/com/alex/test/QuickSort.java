package com.alex.test;

import java.util.ArrayList;
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
	
	public static void main(String[] args) {
		int size;
		List<Integer> list = new ArrayList<>();
		try (Scanner scan = new Scanner(System.in)) {
			size = scan.nextInt();
			for (int i = 0; i < size; i++) {
				if (scan.hasNextInt())
					list.add(scan.nextInt());
				else
					break;
			}
		}
		if (list.size() == 0) {
			Random rand = new Random();
			for (int i = 0; i < size; i++) {
				list.add(rand.nextInt(size*3));
			}
		}
		System.out.println(list);
		sort(list);
		System.out.println(list);
		/*for (Integer i : list) {
			System.out.println(i);
		}*/
	}
}
