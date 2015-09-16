package com.alex.test;

import java.util.*;

public class InsertSort {
	
	private InsertSort() {
		
	}

	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		for (int i = 1; i < list.size(); i++) {
			T key = list.get(i);
			int j = i - 1;
			while (j >= 0 && list.get(j).compareTo(key) > 0) {
				Collections.swap(list, j+1, j);
				j--;
			}
			list.set(j + 1, key);
		}
	}
	
	public static <T extends Comparable<? super T>> void sort(List<T> list, int left, int right) {
		for (int i = left + 1; i <= right; i++) {
			T key = list.get(i);
			int j = i - 1;
			while (j >= left && list.get(j).compareTo(key) > 0) {
				list.set(j+1, list.get(j));
				j--;
			}
			list.set(j + 1, key);
		}
	}
	
	public static void sort(int[] a) {
		for (int i = 1; i < a.length; i++) {
			int key = a[i];
			int j = i - 1;
			while (j >= 0 && a[j] > key) {
				a[j+1] = a[j];
				j--;
			}
			a[j+1] = key;
		}
	}
	
	public static void main(String[] args) {
		int size = 10;
		try (Scanner scan = new Scanner(System.in)) {
			size = scan.nextInt();
		} catch (Exception e) {
			System.out.println("Invalid input, use the default: 10");
		}
		int[] a = new int[size];
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			a[i] = rand.nextInt(size*3);
		}
		System.out.println(Arrays.toString(a));
		sort(a);
		System.out.println(Arrays.toString(a));
		/*List<Integer> list = new ArrayList<>(size);

		Random rand = new Random();
		for (int i = 0; i < size+1; i++) {
			list.add(rand.nextInt(size*3));
		}
		System.out.println(list);
		sort(list);
		System.out.println(list);*/
	}
}