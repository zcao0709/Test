package com.alex.test;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class CountingSort {
	
	private CountingSort() {
		
	}
	
	public static void sort(int[] a, int max) {
		int[] aux = new int[a.length];
		int[] c = new int[max+1];
		for (int n : a) {
			c[n]++;
		}
		System.out.println(Arrays.toString(c));
		for (int i = 1; i < c.length; i++) {
			c[i] += c[i-1];
		}
		System.out.println(Arrays.toString(c));
		for (int i = a.length-1; i >= 0; i--) {
			aux[c[a[i]]-1] = a[i];
			c[a[i]]--;
		}
		System.arraycopy(aux, 0, a, 0, aux.length);
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
		sort(a, size*3-1);
		System.out.println(Arrays.toString(a));
	}
}
