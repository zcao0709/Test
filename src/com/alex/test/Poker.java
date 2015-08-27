package com.alex.test;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;

public class Poker {

	private List<List<Card>> allCards;
	//private TreeSet<Card> cards;
	private List<Dragon> allDragons;
	private List<Pair> allPairs;
	private List<Card> allSingles;
	
	Poker(List<Card> cards) {
		allCards = new LinkedList<>();
		allCards.add(new LinkedList<Card>());
		
		for (Card c : cards) {
			boolean added = false;
			for (List<Card> cs : allCards) {
				if (!cs.contains(c)) {
					cs.add(c);
					added = true;
					break;
				}
			}
			if (added)
				continue;
			List<Card> cs = new LinkedList<>();
			cs.add(c);
			allCards.add(cs);
		}
		for (List<Card> cs : allCards) {
			Collections.sort(cs);
		}
		System.out.println(this.allCards);
		allDragons = new LinkedList<>();
		allPairs = new LinkedList<>();
		allSingles = new LinkedList<>();
	}
	
	private interface Dealable {
		public String deal();
		
		public void undeal();
		
		public boolean isDealt();
	}
	
	private static class Card implements Comparable<Card>, Dealable {
		private int v;
		private String s;
		private boolean deal = false;
		
		Card(int v) {
			if (v < 1 || v > 14)
				throw new IllegalArgumentException();
			this.v = v;
			switch (v) {
			case 1:
			case 14:
				s = "A";
				break;
			case 11:
				s = "J";
				break;
			case 12:
				s = "Q";
				break;
			case 13:
				s = "K";
				break;
			default:
				s = String.valueOf(v);
				break;
			}
		}
		
		public int compareTo(Card c) {
			return this.v - c.v;
		}
		
		@Override
		public String toString() {
			return s;
		}
		
		@Override
		public boolean equals(Object c) {
			return (this.v == ((Card)c).v); // || (this.v == 1 && c.v == 14) || (this.v == 14 && c.v == 1);
		}
		
		public boolean isNext(Card c) {
			return c.v == this.v+1;
		}
		
		public boolean isPrev(Card c) {
			return c.v == this.v-1;
		}
		
		public String deal() {
			this.deal = true;
			return toString();
		}
		
		public void undeal() {
			this.deal = false;
		}
		
		public boolean isDealt() {
			return this.deal;
		}
		
		public void oneToA() {
			if (v == 1)
				v = 14;
		}
		
		public void aToOne() {
			if (v == 14)
				v = 1;
		}
	}
	
	private static class Dragon implements Dealable {
		
		private TreeSet<Card> cards;
		private boolean deal = false;
		private int time = 1;
		
		public Dragon() {
			this.cards = new TreeSet<>();
		}
		
		public Dragon(List<Card> c) {
			this();
			this.cards.addAll(c);
		}
		
		public boolean add(Card c) {
			if (cards.size() == 0) {
				cards.add(c);
				return true;
			}
			if (cards.last().isNext(c)) {
				cards.add(c);
				return true;
			}
			return false;
		}
		
		public void clear() {
			cards.clear();
			time = 1;
		}
		
		public int size() {
			return cards.size();
		}
		
		@Override
		public boolean equals(Object d) {
			System.out.println("dragon equals");
			return cards.equals(((Dragon)d).cards);
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(cards.toString());
			for (int i = 2; i <= time; i++) {
				sb.append(", ");
				sb.append(cards.toString());
			}
			return sb.toString();
		}
		
		public String deal() {
			this.deal = true;
			return toString();
		}
		
		public void undeal() {
			this.deal = false;
		}
		
		public boolean isDealt() {
			return this.deal;
		}
	}
	
	private static class Pair implements Dealable {
		private Card c;
		private boolean deal = false;
		private int time = 1;
		
		public Pair(Card c) {
			this.c = c;
		}
		
		@Override
		public boolean equals(Object p) {
			return this.c.equals(((Pair)p).c);
		}
		
		@Override
		public String toString() {
			String s = "[" + c.toString() + ", " + c.toString() + "]";
			StringBuilder sb = new StringBuilder(s);
			for (int i = 2; i <= time; i++) {
				sb.append(", ");
				sb.append(s);
			}
			return sb.toString();
		}
		
		public String deal() {
			this.deal = true;
			return toString();
		}
		
		public void undeal() {
			this.deal = false;
		}
		
		public boolean isDealt() {
			return this.deal;
		}
	}
	
	public void shuffle() {
		for (List<Card> cards : allCards) {
			while (cards.size() >= 3) {
				if (findDragon(cards)) {
					
				} else {
					Card head = cards.remove(0);
					if (head.v == 1) {
						head.oneToA();
						cards.add(head);
					} else {
						allSingles.add(head);
					}
				}
			}
			allSingles.addAll(cards);
			cards.clear();
		}
		findPair();
	}
	
	private boolean findDragon(List<Card> cards) {
		if (cards.size() < 3 || cards.get(2).v != cards.get(0).v+2)
			return false;
		
		Dragon d = new Dragon(cards.subList(0, 3));
		cards.remove(0);
		cards.remove(0);
		cards.remove(0);
		while (cards.size() > 0) {
			Card c = cards.remove(0);
			if (d.add(c)) {
				
			} else {
				cards.add(0, c);
				break;
			}
		}
		/*if (allDragons.remove(d)) {
			d.time++;
		}*/
		allDragons.add(d);
		return true;
	}
	
	private void findPair() {
		Collections.sort(allSingles);
		//System.out.println("Single: " + allSingles);
		
		for (int i = 0; i < allSingles.size()-1; i++) {
			Card c = allSingles.get(i);
			if (c.equals(allSingles.get(i+1))) {
				Pair p = new Pair(c);
				/*if (allPairs.remove(p)) {
					p.time++;
				}*/
				allPairs.add(p);
				i++;
			}
		}
		for (Pair p : allPairs) {
			for (int i = 1; i <= p.time*2; i++) {
				allSingles.remove(p.c);
			}
		}
	}
	
	public void deal() {
		System.out.println(allDragons.size() + " dragon[s]: " + allDragons);
		System.out.println(allPairs.size() + " pair[s]: " + allPairs);
		System.out.println(allSingles.size() + " single[s]: " + allSingles);
		
		TreeSet<String> dragonOuts = new TreeSet<>();
		StringBuilder line = new StringBuilder();
		deal(dragonOuts, line, allDragons);
		
		TreeSet<String> pairOuts = new TreeSet<>();
		line.delete(0, line.length());
		deal(pairOuts, line, allPairs);
		
		TreeSet<String> singleOuts = new TreeSet<>();
		line.delete(0, line.length());
		deal(singleOuts, line, allSingles);
		
		/*System.out.println(dragonOuts);
		System.out.println(pairOuts);
		System.out.println(singleOuts);*/
		line.delete(0, line.length());
		for (String d : dragonOuts) {
			line.append(d);
			for (String p : pairOuts) {
				int len1 = line.length();
				line.append(p);
				for (String c : singleOuts) {
					System.out.println(line.toString() + c);
				}
				line.delete(len1, line.length());
			}
		}
	}
	
	private <T> void deal(TreeSet<String> outs, StringBuilder line, List<T> list) {
		if (list.size() == 1) {
			int oldLen = line.length();
			if (line.length() > 0)
				line.append(", ");
			line.append(list.get(0).toString());
			outs.add(line.toString());
			line.delete(oldLen, line.length());
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			int oldLen = line.length();
			if (line.length() > 0)
				line.append(", ");
			line.append(list.get(i).toString());
			T t = list.remove(i);
			deal(outs, line, list);
			list.add(i, t);
			line.delete(oldLen, line.length());
		}
	}
	
	public static void main(String[] args) {

		List<Card> cards = new ArrayList<>(13);;
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < 13; i++) {
			cards.add(new Card(sc.nextInt()));
		} //1 3 3 4 5 6 7 8 9 10 11 12 13
		sc.close();
		Poker poker = new Poker(cards);
		poker.shuffle();
		poker.deal();
	}
}
