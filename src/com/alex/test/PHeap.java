package com.alex.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

// min-heap
public class PHeap<E> {

	private static final int DEFAULT_INIT_CAPACITY = 16;
	private static final int MAX_CAPACITY = Integer.MAX_VALUE - 8;
	private Object[] elem;
	private int size;
	private Comparator<? super E> comp;
	
	public PHeap(int initCapacity, Comparator<? super E> c) {
		elem = new Object[initCapacity];
		size = 0;
		comp = c;
	}
	
	public PHeap(int initCapacity) {
		this(initCapacity, null);
	}
	
	public PHeap() {
		this(DEFAULT_INIT_CAPACITY, null);
	}
	
	public int size() {
		return size;
	}
	
	public void offer(E e) {
		if (size >= elem.length)
			grow();
		if (size < elem.length) {
			size++;
			if (size == 1)
				elem[0] = e;
			else
				shiftUp(size-1, e);
			return;
		}
		throw new IllegalArgumentException();
	}
	
	@SuppressWarnings("unchecked")
	public E poll() {
		if (size > 0) {
			E ret = (E)elem[0];
			size--;
			shiftDown(0, (E)elem[size]);
			return ret;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public E peek() {
		if (size > 0) {
			return (E)elem[0];
		}
		return null;
	}
	
	public void heapify() {
		for (int i = (size >>> 1) - 1; i >= 0; i--) {
			shiftDown(i, (E)elem[i]);
		}
	}
	
	private void grow() {
		int newCapacity = elem.length >= MAX_CAPACITY/2 ? MAX_CAPACITY : elem.length*2;
		elem = Arrays.copyOf(elem, newCapacity);
	}
	
	@SuppressWarnings("unchecked")
	private void shiftUp(int i, E e) {
		while (i > 0) {
			int p = parent(i);
			if (compare((E)elem[p], e) <= 0)
				break;
			elem[i] = elem[p];
			i = p;
		}
		elem[i] = e;
	}
	
	@SuppressWarnings("unchecked")
	private void shiftDown(int i, E e) {
		while (i < (size >>> 1)) {
			int left = left(i);
			int right = right(i);
			int min = left;
			if (right < size && compare((E)elem[left], (E)elem[right]) > 0)
				min = right;
			if (compare(e, (E)elem[min]) <= 0)
				break;
			elem[i] = elem[min];
			i = min;
		}
		elem[i] = e;
	}
	
	private int compare(E a, E b) {
		if (comp != null)
			return comp.compare(a, b);
		else {
			@SuppressWarnings("unchecked")
			Comparable<? super E> c = (Comparable<? super E>)a;
			return c.compareTo(b);
		}
	}
	
	private int left(int i) {
		return (i << 1) + 1;
	}
	
	private int right(int i) {
		return (i << 1) + 2;
	}
	
	private int parent(int i) {
		return (i - 1) >>> 1;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(size).append(": ");
		for (int i = 0; i < size; i++)
			sb.append(elem[i]).append(" ");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			int size = sc.nextInt();
			PHeap<Integer> h = new PHeap<>();
			Random rand = new Random();
			for (int i = 0; i < size; i++) {
				h.offer(new Integer(rand.nextInt(size * 3)));
				System.out.println(h);
			}
			for (int i = 0; i < size; i++) {
				System.out.println(h.peek());
				h.poll();
				System.out.println(h);
			}
		}
	}
}
