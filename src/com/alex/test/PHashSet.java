package com.alex.test;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class PHashSet<E> {
	
	private static int DEFAULT_INIT_CAPACITY = 16;
	private static float DEFAULT_LOAD_FACTOR = 0.75f;
	
	private int capacity;
	private final float loadFactor;
	private Entry<E>[] table;
	private int size;
	
	@SuppressWarnings("unchecked")
	public PHashSet(int capacity, float loadFactor) {
		this.capacity = capacity;
		this.loadFactor = loadFactor;
		
		table = new Entry[capacity];
		size = 0;
	}
	
	public PHashSet() {
		this(DEFAULT_INIT_CAPACITY, DEFAULT_LOAD_FACTOR);
	}
	
	public boolean add(E e) {
		int hash = Objects.hashCode(e);
		int i = hash & (table.length-1);
		
		for (Entry<E> entry = table[i]; entry != null; entry = entry.next) {
			if (hash == entry.hash && e.equals(entry.getKey()))
				return false;
		}
		addEntry(e, hash, i);
		return true;
	}
	
	private void addEntry(E e, int h, int i) {
		if (size >= capacity * loadFactor)
			rehash();
		i = h & (table.length-1);
		Entry<E> s = table[i];
		table[i] = new Entry<E>(e, h, s);
		size++;
	}
	
	private void rehash() {
		System.out.println("Rehash");
		@SuppressWarnings("unchecked")
		Entry<E>[] newTable = new Entry[table.length*2];
		
		for (Entry<E> entry : table) {
			while (entry != null) {
				Entry<E> next = entry.next;
				int i = entry.hash & (newTable.length-1);
				entry.next = newTable[i];
				newTable[i] = entry;
				entry = next;
			}
		}
		table = newTable;
		capacity = table.length;
	}
	
	public boolean contains(E e) {
		int hash = Objects.hashCode(e);
		int i = hash & (table.length-1);
		
		for (Entry<E> entry = table[i]; entry != null; entry = entry.next) {
			if (hash == entry.hash && e.equals(entry.getKey()))
				return true;
		}
		return false;
	}
	
	public boolean remove(E e) {
		int hash = Objects.hashCode(e);
		int i = hash & (table.length-1);
		
		Entry<E> prev = null;
		for (Entry<E> entry = table[i]; entry != null; entry = entry.next) {
			if (hash == entry.hash && e.equals(entry.getKey())) {
				if (prev == null)
					table[i] = entry.next;
				else
					prev.next = entry.next;
				entry.next = null;
				size--;
				return true;
			}
			prev = entry;
		}
		return false;
	}
	
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(String.valueOf(size));
		sb.append(":\n");
		int i = 0;
		for (Entry<E> e : table) {
			sb.append(i).append(": ");
			while (e != null) {
				sb.append(e.toString()).append(", ");
				e = e.next;
			}
			sb.append("null\n");
			i++;
		}
		return sb.toString();
	}

	private static class Entry<E> {
		final E key;
		final int hash;
		Entry<E> next;
		
		Entry(E k, int h, Entry<E> n) {
			key = k;
			hash = h;
			next = n;
		}
		
		E getKey() {
			return key;
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Entry))
				return false;
			@SuppressWarnings("unchecked")
			Entry<E> e = (Entry<E>)o;
			Object k1 = getKey();
			Object k2 = e.getKey();
			if (k1 == k2 || k1 != null && k1.equals(k2))
				return true;
			return false;
		}
		
		@Override
		public String toString() {
			//return getKey().toString();
			return getKey() + "-" + hash;
		}
	}
	
	public static void main(String[] args) {
		int size = 10;
		try (Scanner scan = new Scanner(System.in)) {
			size = scan.nextInt();
		}
		PHashSet<Integer> hs = new PHashSet<>();
		int[] a = new int[size];
		Random rand = new Random();
		
		for (int i = 0; i < size; i++) {
			int k = rand.nextInt(size*3);
			a[i] = k;
			System.out.println("Adding " + k);
			hs.add(new Integer(k));
			System.out.println(hs);
			if (i % 5 == 1) {
				System.out.println("Removing " + a[i-1]);
				hs.remove(a[i-1]);
				System.out.println(hs);
			}
		}
	}
}
