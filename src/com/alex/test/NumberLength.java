package com.alex.test;

import java.util.Arrays;
import java.util.Scanner;

public class NumberLength {
		
		private static final String[] UNITS = {"", "thousand", "million", "billion"};

		public static long totalLength(int num) {
			long len = 0;
			for (int i = num; i > 0; i--) {
				len += length(i);
			}
			return len;
		}
		
		public static long totalLength(String num) {
			analyze(num);
			return 0;
		}
		
		@SuppressWarnings("unused")
		private static int[] analyze(int num) {
			int unit = 0;
			
			for (int i = num; i > 0; i = i/1000) {
				unit++;
			}
			System.out.println("unit: " + unit);
			if (unit > UNITS.length)
				throw new IllegalArgumentException();
			
			int[] numParts = new int[unit+1];
			for (int i = unit; i >= 0; i--) {
				int mode = (int)Math.pow(1000, i);
				numParts[i] = num / mode;
				num = num % mode;
			}
			System.out.println(Arrays.toString(numParts));
			return numParts;
		}
		
		private static int[] analyze(String num) {
			int unit = num.length() / 3;
			System.out.println("unit: " + unit);
			
			int[] numParts = new int[unit+1];
			for (int i = num.length(), j = 0; j <= unit; i -= 3, j++) {
				numParts[j] = Integer.valueOf(num.substring(i-3 < 0 ? 0 : i-3, i));
			}
			System.out.println(Arrays.toString(numParts));
			return numParts;
		}
		
		private static int length(int num) {
			int len = 0;
			int unit = 0;
			
			for (int i = num/1000; i > 0; i = i/1000) {
				unit++;
			}
			System.out.println("unit: " + unit);
			for (int i = unit; i >= 0; i--) {
				//System.out.println(num);
				int mode = (int)Math.pow(1000, i);
				if (num < mode) {
					continue;
				}
				int numPart = num / mode;
				System.out.println(numPart + UNITS[i]);
				len = len + lengthLess1000(numPart) + UNITS[i].length();
				num = num % mode;
				if (num == 0)
					break;
			}
			return len;
		}
		
		private static int length1to9() {
			int len = 0;
			for (int i = 1; i <= 9; i++) {
				len += lengthSpecial(i);
			}
			return len;
		}
		
		private static int length11to19() {
			int len = 0;
			for (int i = 11; i <= 19; i++) {
				len += lengthSpecial(i);
			}
			return len;
		}
		
		private static int length20to90() {
			int len = 0;
			for (int i = 10; i <= 90; i++) {
				len += lengthSpecial(i);
			}
			return len;
		}
		
		private static int length1to99() {
			int len = length1to9() * 9 + length11to19() + length20to90() * 10 + lengthSpecial(10);
			return len;
		}
		
		private static int lengthLess1000(int num) {
			if (num == 0)
				return 0;
			int len = 0;
			int numPart = (int)(num / 100);
			if (numPart > 0) {
					System.out.println(numPart + "hundred");
					len = len + "hundred".length() + lengthSpecial(numPart);
			}
			len += lengthLess100(num % 100);
			return len;
		}
		
		private static int lengthLess100(int num) {
			if (num == 0)
				return 0;
			if (num <= 20) {
				return lengthSpecial(num);
			}
			return lengthSpecial((int)(num / 10) * 10) + lengthSpecial(num % 10);
		}
		
		private static int lengthSpecial(int num) {
			System.out.println("Special: " + num);
			switch (num) {
			case 0:
				return 0;
			case 1:
				return "one".length();
			case 2:
				return "two".length();
			case 3:
				return "three".length();
			case 4:
				return "four".length();
			case 5:
				return "five".length();
			case 6:
				return "six".length();
			case 7:
				return "seven".length();
			case 8:
				return "eight".length();
			case 9:
				return "nine".length();
			case 10:
				return "ten".length();
			case 11:
				return "eleven".length();
			case 12:
				return "twelve".length();
			case 13:
				return "thirteen".length();
			case 14:
				return "fourteen".length();
			case 15:
				return "fifteen".length();
			case 16:
				return "sixteen".length();
			case 17:
				return "seventeen".length();
			case 18:
				return "eighteen".length();
			case 19:
				return "nineteen".length();
			case 20:
				return "twenty".length();
			case 30:
				return "thirty".length();
			case 40:
				return "fourty".length();
			case 50:
				return "fifty".length();
			case 60:
				return "sixty".length();
			case 70:
				return "seventy".length();
			case 80:
				return "eighty".length();
			case 90:
				return "ninety".length();
			default:
				throw new IllegalArgumentException();
			}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println(NumberLength.totalLength(sc.nextLine()));
	}
}
