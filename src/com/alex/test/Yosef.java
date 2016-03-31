package com.alex.test;

public class Yosef {

	private static int yosef(int m, int k, int i) {
		if (i == 1) {
			return (m + k - 1) % m;
		} else {
			return (yosef(m - 1, k, i - 1) + k) % m;
		}
	}
	
	public static void main(String[] args) {
		for (int i = 1; i <= 10; i++) {
			System.out.println("The " + i + "th out:" + yosef(10, 3, i));
		}
	}
}
