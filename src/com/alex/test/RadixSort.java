package com.alex.test;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class RadixSort {

	private RadixSort() {
		
	}
	
	public static void sort(int[] a) {
		int len = String.valueOf(a[0]).length();
		for (int i = 1; i <= len; i++) {
			sort(a, i);
		}
	}
	
	private static void sort(int[] a, int index) {
		int max = 9;
		int[] aux = new int[a.length];
		int[] c = new int[max+1];
		for (int n : a) {
			int radix = getRadix(n, index);
			c[radix]++;
		}
		//System.out.println(Arrays.toString(c));
		for (int i = 1; i < c.length; i++) {
			c[i] += c[i-1];
		}
		System.out.println(Arrays.toString(c));
		for (int i = a.length-1; i >= 0; i--) {
			int radix = getRadix(a[i], index);
			aux[c[radix]-1] = a[i];
			c[radix]--;
		}
		System.arraycopy(aux, 0, a, 0, aux.length);
	}
	
	private static int getRadix(int number, int index) {
		return (number % (int)Math.pow(10, index)) / (int)Math.pow(10, index-1); 
	}
	
	public static void main(String[] args) {
		int size = 10;
		try (Scanner scan = new Scanner(System.in)) {
			size = scan.nextInt();
		}
		int[] a = new int[size];
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			a[i] = 100 + rand.nextInt(size*3);
		}
		System.out.println(Arrays.toString(a));
		sort(a);
		System.out.println(Arrays.toString(a));
	}

}
