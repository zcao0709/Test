package com.alex.test;

import java.util.*;

public class InsertSort {
	
	private InsertSort() {
		
	}

	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		for (int i = 1; i < list.size(); i++) {
			for (int j = i-1; j >= 0; j--) {
				if (list.get(j+1).compareTo(list.get(j)) < 0) {
					Collections.swap(list, j+1, j);
				} else {
					break;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int size = 10;
		try (Scanner scan = new Scanner(System.in)) {
			size = scan.nextInt();
		} catch (Exception e) {
			System.out.println("Invalid input, use the default: 10");
		}
		
		List<Integer> list = new ArrayList<>(size);

		Random rand = new Random();
		for (int i = 0; i < size+1; i++) {
			list.add(rand.nextInt(size*3));
		}
		System.out.println(list);
		sort(list);
		System.out.println(list);
	}
}