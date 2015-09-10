package com.alex.test;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	public static void sort(int[] a) {
		//sort(a, 0, a.length-1);
		int[] aux = a.clone();
		sort(aux, a, 0, a.length-1);
	}
	
	private static void sort(int[] a, int left, int right) {
		if (left < right) {
			int mid = (left + right) / 2;
			sort(a, left, mid);
			sort(a, mid+1, right);
			merge(a, left, mid, right);
		}
	}
	
	private static void merge(int[] a, int left, int mid, int right) {
		int[] lhalf = new int[mid - left + 2];
		int[] rhalf = new int[right - mid + 1];
		for (int i = left; i <= mid; i++)
			lhalf[i-left] = a[i];
		for (int i = mid+1; i <= right; i++)
			rhalf[i-mid-1] = a[i];
		lhalf[lhalf.length-1] = Integer.MAX_VALUE;
		rhalf[rhalf.length-1] = Integer.MAX_VALUE;
		int i = 0;
		int j = 0;
		int k = left;
		for (; k <= right; k++) {
			if (lhalf[i] <= rhalf[j]) {
				a[k] = lhalf[i++];
				if (i >= lhalf.length)
					break;
			} else {
				a[k] = rhalf[j++];
				if (j >= rhalf.length)
					break;
			}
		}
	}
	
	private static void sort(int[] src, int[] dest, int left, int right) {
		if (left >= right)
			return;
		int mid = (left + right) >>> 1;
		sort(dest, src, left, mid);
		sort(dest, src, mid+1, right);
		
		if (src[mid] <= src[mid+1]) {
			System.arraycopy(src, left, dest, left, right-left+1);
			return;
		}
		for (int i = left, p = left, q = mid + 1; i <= right; i++) {
			if (q > right || (p <= mid && src[p] <= src[q]))
				dest[i] = src[p++];
			else
				dest[i] = src[q++];
		}
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
		sort(a);
		System.out.println(Arrays.toString(a));
		/*List<Integer> list = new ArrayList<>(size);
		
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			list.add(rand.nextInt(size*3));
		}
		System.out.println(list);
		sort(list);
		System.out.println(list);*/
	}
}
