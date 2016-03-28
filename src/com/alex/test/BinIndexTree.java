package com.alex.test;

import java.util.Arrays;
import java.util.Scanner;

public class BinIndexTree {
	
	private long[] bit;
	
	public BinIndexTree(int n) {
		bit = new long[n + 1];
	}
	
	public void update(int index, long value) {
		index++;
		
		while (index < bit.length) {
			bit[index] += value;
			
			index += index & (-index);
		}
	}
	
	public void reset(int index) {
		update(index, -bit[index+1]);
	}
	
	public long getSum(int index) {
		long sum = 0;
		
		index++;
		while (index > 0) {
			sum += bit[index];
			index -= index & (-index);
		}
		return sum;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(bit);
	}
	
	private static long maxSum(BinIndexTree bit, long max, long m, int n) {
        for (int j = 0; j < n; j++) {
            for (int k = j+1; k < n; k++) {
                long sum = bit.getSum(k);
                sum %= m;
                if (sum > max)
                    max = sum;
                if (max == m-1)
                    return max;
            }
            bit.reset(j);
        }
        return max;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
		int ncase = sc.nextInt();
		for (int i = 0; i < ncase; i++) {
            int n = sc.nextInt();
            long m = sc.nextLong();
            BinIndexTree bit = new BinIndexTree(n);
            long max = 0;
            for (int j = 0; j < n; j++) {
                long num = (sc.nextLong() % m);
                if (num == 0) {
                    n--;
                    continue;
                }
                if (num > max)
                    max = num;
                bit.update(j, num);
            }
            if (max != m-1) {
                max = maxSum(bit, max, m, n);
            }
            System.out.println(max);
        }
        sc.close();
    }
}
