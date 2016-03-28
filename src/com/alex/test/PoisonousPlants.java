package com.alex.test;

import java.util.LinkedList;
import java.util.Scanner;

public class PoisonousPlants {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		Garden g = new Garden();

		for (int i = 0; i < num; i++) {
			g.add(new Plant(sc.nextInt(), i));
		}
		if (g.size() == num) {
			System.out.println(0);
			sc.close();
			return;
		}
		int day = 1;
		System.out.println(g);

		while (!g.allSurviveOneDay()) {
			day++;
		}
		System.out.println(day);
		sc.close();
	}

	private static class Plant {
		int v;
		int pos;
		Plant next;
		Plant prev;

		Plant(int v, int pos) {
			this.v = v;
			this.pos = pos;
			next = null;
			prev = null;
		}

		boolean dieToday() {
			if (prev == null)
				return false;
			if (v > prev.v)
				return true;
			return false;
		}
		
		public boolean equals(Object p) {
			return this.pos == ((Plant)p).pos;
		}
		
		public String toString() {
			return v + " "; // + pos;
		}
	}

	private static class Garden {
		Plant head;
		Plant tail;
		int leftv;
		int size;
		LinkedList<Plant> rightMayDieTomorrow;

		Garden() {
			head = null;
			tail = null;
			size = 0;
			leftv = Integer.MAX_VALUE;
			rightMayDieTomorrow = new LinkedList<>();
		}

		void add(Plant p) {
			if (head == null) {
				head = p;
				tail = p;
				size = 1;
				leftv = p.v;
				return;
			}
			if (p.v > leftv) {
				addToMayDie(tail);
			} else {
				tail.next = p;
				p.prev = tail;
				tail = p;
				size++;
			}
			leftv = p.v;
		}

		void addToMayDie(Plant p) {
			if (rightMayDieTomorrow.size() == 0
					|| !rightMayDieTomorrow.getLast().equals(p))
				rightMayDieTomorrow.add(p);
		}
		
		void remove(Plant p) {
			if (size == 1) {
				head = null;
				tail = null;
				size = 0;
				return;
			}
			if (p != head) {
				p.prev.next = p.next;
			} else {
				head = p.next;
			}
			if (p != tail) {
				p.next.prev = p.prev;
			} else {
				tail = p.prev;
			}
			size--;
			p.next = null;
			p.prev = null;
			
			if (rightMayDieTomorrow.size() > 0 
					&& rightMayDieTomorrow.getFirst().equals(p))
				rightMayDieTomorrow.removeFirst();
		}

		int size() {
			return size;
		}

		boolean allSurviveOneDay() {
			if (size() < 2)
				return true;
			LinkedList<Plant> tmp = new LinkedList<>(rightMayDieTomorrow);
			rightMayDieTomorrow.clear();
			boolean someDie = false;
			while (tmp.size() > 0) {
				Plant left = tmp.removeLast();
				Plant curr = left.next;
				if (curr == null)
					continue;
				if (curr.dieToday()) {
					someDie = true;
					int nextv = -1;
					if (curr.next != null)
						nextv = curr.next.v;
					remove(curr);
					if (nextv > left.v)
						rightMayDieTomorrow.addFirst(left); // left.next will die tomorrow
				}
			}
			return !someDie;
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			Plant p = head;
			while (p != null) {
				sb.append(p).append(",");
				p = p.next;
			}
			return sb.toString();
		}
	}
}