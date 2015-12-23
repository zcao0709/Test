package com.alex.test;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

	public Test() {
		System.out.println("Test");
	}
	
	public Test(Test t) {
		this.a = t.a;
		this.b = t.b;
	}
	
	static final int MAXIMUM_CAPACITY = 1 << 30;
	
	private int a;
	protected int b;
	
	public static void main(String[] args) {
		/*List<Integer> list = new ArrayList<>(10);
		for (int i = 0; i < 3; i++)
			list.add(i);
		List<Integer> listToPrint = new ArrayList<>();
		printFull(listToPrint, list);
		int i = 21;
		System.out.println(indexFor(i, 1<<3));
		System.out.println(i % (1<<3));
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
		System.out.println(countRunAndMakeAscending(a, 0, a.length));
		System.out.println(Arrays.toString(a));
		Test t = new Test();
		System.out.println(t.a);
		System.out.println(t.b);*/
		//MakeTest t = () -> System.out.println("do test");
		//t.dotest();
		List<String> test = Arrays.asList("hello", "world", "haha");
		System.out.println(test.stream()
								.filter(str -> str.matches("h.*"))
								.collect(Collectors.toList()));
		System.out.println(Stream.of("hello", "world", "haha").filter(str -> str.matches("h.*")).collect(Collectors.toList()));
	}
	
	private interface MakeTest {
		void dotest();
	}
	
    public static int roundUpToPowerOf2(int number) {
        // assert number >= 0 : "number must be non-negative";
        int rounded = number >= MAXIMUM_CAPACITY
                ? MAXIMUM_CAPACITY
                : (rounded = Integer.highestOneBit(number)) != 0
                    ? (Integer.bitCount(number) > 1) ? rounded << 1 : rounded
                    : 1;

        return rounded;
    }
    
    public static int countRunAndMakeAscending(int[] a, int lo, int hi) {
        assert lo < hi;
        int runHi = lo + 1;
        if (runHi == hi)
            return 1;

        // Find end of run, and reverse range if descending
        if (a[runHi++] < a[lo]) { // Descending
            while (runHi < hi && a[runHi] < a[runHi - 1])
                runHi++;
            reverseRange(a, lo, runHi);
        } else {                              // Ascending
            while (runHi < hi && a[runHi] >= a[runHi - 1])
                runHi++;
        }
        return runHi - lo;
    }
    
    private static void reverseRange(int[] a, int lo, int hi) {
        hi--;
        while (lo < hi) {
            int t = a[lo];
            a[lo++] = a[hi];
            a[hi--] = t;
        }
    }
    
    public static int indexFor(int h, int length) {
        // assert Integer.bitCount(length) == 1 : "length must be a non-zero power of 2";
        return h & (length-1);
    }
	
	public static void printFull(List<Integer> listToPrint, List<Integer> numbers) {
		if (numbers.size() == 1) {
			listToPrint.add(numbers.get(0));
			System.out.println(listToPrint);
			listToPrint.remove(listToPrint.size()-1);
			return;
		}
		for (int i = 0; i < numbers.size(); i++) {
			listToPrint.add(numbers.get(i));
			List<Integer> newList = new ArrayList<>();
			newList.addAll(numbers);
			newList.remove(i);
			printFull(listToPrint, newList);
			listToPrint.remove(listToPrint.size()-1);
		}
	}
	
	public static void findZero() {
		int[] numbers = new int[10];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = i + 1;
		}
		numbers[3] = 0;

		Arrays.sort(numbers);
		System.out.println(Arrays.toString(numbers));
		if (numbers[0] == 1 || numbers[1] == 0) {
			throw new IllegalArgumentException();
		}
		if (numbers[1] == 2) {
			System.out.println(1);
			return;
		}
		if (numbers[numbers.length-1] == numbers.length-1) {
			System.out.println(numbers.length);
			return;
		}
		int left = 0;
		int right = numbers.length;
		while (true) {
			//System.out.println("Left " + left + ", right " + right);
			if (left >= right) {
				throw new IllegalArgumentException();
			}
			int mid = (right - left) / 2 + left;
			//System.out.println("Mid " + mid);
			if (numbers[mid] == mid) {
				if (numbers[mid+1] != mid+1) {
					System.out.println(mid+1);
					return;
				}
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
	}
}
