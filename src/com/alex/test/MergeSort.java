package com.alex.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MergeSort {
	
	private MergeSort() {
		
	}
	
	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		sort(list, 0, list.size()-1);
	}

	@SuppressWarnings("unused")
	private static <T extends Comparable<? super T>> void sort_r(List<T> list, int min, int max) {
		if (min >= max)
			return;
		
		int mid = (max - min) / 2 + min;
		sort(list, min, mid);
		sort(list, mid+1, max);
		merge(list, min, max, mid);
	}
	
	private static <T extends Comparable<? super T>> void sort(List<T> list, int min, int max) {
		for (int i = 2; ; i *= 2) {
			for (int j = min; j < max; j += i) {
				int newMax = j + i - 1;
				newMax = newMax > max ? max : newMax;
				int mid = j + i/2 - 1;
				if (mid >= newMax)
					continue;
				merge(list, j, newMax, mid);
			}
			if (i >= max-min+1)
				break;
		}
	}
	
	private static <T extends Comparable<? super T>> void merge(List<T> list, int min, int max, int mid) {
		if (min >= max)
			return;
		List<T> newList = new ArrayList<>();
		
		int i = min;
		int j = mid + 1;
		while (i <= mid && j <= max) {
			if (list.get(i).compareTo(list.get(j)) <= 0) {
				newList.add(list.get(i));
				i++;
			} else {
				newList.add(list.get(j));
				j++;
			}
		}
		for (; i <= mid; i++)
			newList.add(list.get(i));
		for (; j <= max; j++)
			newList.add(list.get(j));
		
		assert newList.size() == (max - min + 1);
		
		for (i = min; i <=max; i++)
			list.set(i, newList.get(i-min));
		return;
	}
	
	public static void main(String[] args) {
		int size = 10;
		try (Scanner scan = new Scanner(System.in)) {
			size = scan.nextInt();
		}
		
		List<Integer> list = new ArrayList<>(size);
		
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			list.add(rand.nextInt(size*3));
		}
		System.out.println(list);
		sort(list);
		System.out.println(list);
		/*for (Integer i : list) {
			System.out.println(i);
		}*/
	}
}
