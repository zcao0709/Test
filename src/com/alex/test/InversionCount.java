package com.alex.test;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class InversionCount {

	public static int count(int[] a) {
		int[] aux = a.clone();
		return count(aux, a, 0, a.length-1);
	}
	
	private static int count(int[] src, int[] dest, int left, int right) {
		if (left >= right)
			return 0;
		int mid = (left + right) >>> 1;
		int ret = count(dest, src, left, mid) 
				+ count(dest, src, mid+1, right);
		
		if (src[mid] <= src[mid+1]) {
			System.arraycopy(src, left, dest, left, right-left+1);
			return ret;
		}
		for (int i = left, p = left, q = mid + 1; i <= right; i++) {
			if (q > right || p <= mid && src[p] <= src[q]) {
				dest[i] = src[p++];
			} else {
				if (p <= mid && src[p] > src[q]) {
					ret += (mid-p+1);
					//System.out.println(src[p] + "," + src[q] + ":" + ret);
				}
				dest[i] = src[q++];
			}
		}
		return ret;
	}
	
	public static void main(String[] args) {
		int size = 10;
		try (Scanner scan = new Scanner(System.in)) {
			size = scan.nextInt();
			int[] a = new int[size];
			for (int i = 0; i < size; i++) {
                a[i] = scan.nextInt();
            }
			//System.out.println(Arrays.toString(a));
    		System.out.println(count(a));
    		//System.out.println(Arrays.toString(a));
		}
		/*int[] a = new int[size];
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			a[i] = rand.nextInt(size*3);
		}
		System.out.println(Arrays.toString(a));
		System.out.println(count(a));
		System.out.println(Arrays.toString(a));*/
	}

}
