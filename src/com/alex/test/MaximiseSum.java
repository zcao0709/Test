package com.alex.test;

import java.util.Scanner;

//You are given an array of size N and another integer M. Your target is to find the maximum value of sum of subarray modulo M.
//
//Subarray is a continuous subset of array elements.
//
//Note that we need to find the maximum value of (Sum of Subarray)%M , where there are N¡Á(N+1)/2 possible subarrays.
//
//For a given array A[] of size NN, subarray is a contiguous segment from ii to jj where 0 ¡Ü i ¡Ü j ¡Ü N
//
//Input Format
//First line contains T, number of test cases to follow. Each test case consists of exactly 2 lines. First line of each test case
//contain 2 space separated integers NN and MM, size of the array and modulo value M.
//Second line contains N space separated integers representing the elements of the array.
//
//Output Format
//For every test case output the maximum value asked above in a newline.
//
//Constraints
//2 ¡Ü N ¡Ü 1052 ¡Ü N ¡Ü 105
//1 ¡Ü M ¡Ü 10141 ¡Ü M ¡Ü 1014
//1 ¡Ü elements of the array ¡Ü 10181 ¡Ü elements of the array¡Ü1018
//2 ¡Ü Sum of N over all test cases ¡Ü 500000

public class MaximiseSum {

	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
		int ncase = sc.nextInt();
		for (int i = 0; i < ncase; i++) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            int[] sum = new int[n];
            int max = 0;
            for (int j = 0; j < n; j++) {
                int num = (sc.nextInt() % m);
                if (max < num)
                    max = num;
                if (j == 0) {
                    sum[j] = num;
                } else {
                    sum[j] = (num + sum[j-1]) % m;
                }
                if (j != 0 && max < sum[j])
                    max = sum[j];
            }
            if (max != m - 1) {
                RedBlackTree rbt = new RedBlackTree();
                rbt.insert(sum[0]);
                for (int j = 1; j < n; j++) {
                    int num = rbt.higher(sum[j]);
                    rbt.insert(sum[j]);
                    if (num != -1) {
                        int newSum = sum[j] + m - num;
                        if (max < newSum)
                            max = newSum;
                    }
                }
            }
            System.out.println(max);
        }
        sc.close();
	}

}
