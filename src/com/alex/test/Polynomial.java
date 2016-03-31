package com.alex.test;

import java.util.Scanner;

public class Polynomial {
	private Term head;
	
	public Polynomial(String s) {
		head = new Term(0);
		head.next = head;

		s = s.trim();
		if (s.length() == 0)
			return;
		
		int[] params = new int[4];
		params[0] = 1;
		int index = 0;
		boolean neg = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isWhitespace(c) || c == '^') {
				continue;
			} else if (c == '+' || c == '-') {
				if (i != 0) {
					add(new Term(neg, params));
					params[0] = 1;
					params[1] = 0;
					params[2] = 0;
					params[3] = 0;
					index = 0;
					neg = false;
				}
				if (c == '-')
					neg = true;
			} else if (c == 'x' || c == 'X') {
				index = 1;
				params[index]++;
			} else if (c == 'y' || c == 'Y') {
				index = 2;
				params[index]++;
			} else if (c == 'z' || c == 'Z') {
				index = 3;
				params[index]++;
			} else if (Character.isDigit(c)) {
				int start = i;
				while (++i < s.length() && Character.isDigit(s.charAt(i)))
					;
				params[index] = Integer.parseInt(s.substring(start, i));
				i--;
			}
		}
		add(new Term(neg, params));
	}
	
	public void multiply(Polynomial poly) {
		// M1
		Term m = poly.head.next;
		Polynomial q = new Polynomial("");
		while (m != poly.head) {
			// M2
			multiply(q, this, m);
			m = m.next;
		}
		this.head = q.head;
	}
	
	private void multiply(Polynomial result, Polynomial left, Term right) {
		Term p = left.head.next;
		while (p != left.head) {
			result.add(multiply(p, right));
			p = p.next;
		}
	}
	
	private Term multiply(Term left, Term right) {
		int[] exp = new int[4];
		exp[0] = left.coef * right.coef;
		if (exp[0] == 0)
			return null;
		for (int i = 0; i < left.exp.length; i++) {
			exp[i+1] = left.exp[i] + right.exp[i];
		}
		return new Term(false, exp);
	}
	
	public void add(Polynomial poly) {
		add(this, poly);
	}
	
	private void add(Polynomial left, Polynomial right) {
		// A1
		Term p = right.head.next;
		Term q = left.head.next;
		Term q1 = left.head;
		
		// A2
		while (true) {
			while (p.compareTo(q) < 0) {
				q1 = q;
				q = q.next;
			}
			if (p.compareTo(q) == 0) {
				// A3
				if (p.coef == 0)
					return;
				q.coef += p.coef;
				if (q.coef == 0) {
					// A4
					p = p.next;
					q = q.next;
					q1.next = q;
				} else {
					p = p.next;
					q1 = q;
					q = q.next;
				}
			} else {
				// A5
				Term q2 = new Term(p);
				p = p.next;
				q2.next = q;
				q1.next = q2;
				q1 = q2;
			}
		}
	}
	
	private void add(Term term) {
		if (term == null)
			return;
		Term p = head;
		while (p.next != head) {
			if (p.next.compareTo(term) > 0) {
				p = p.next;
			} else if (p.next.compareTo(term) == 0) {
				p.next.coef += term.coef;
				if (p.next.coef == 0) {
					p.next = p.next.next;
				}
				return;
			} else {
				term.next = p.next;
				p.next = term;
				return;
			}
		}
		term.next = p.next;
		p.next = term;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Term p = head.next;
		while (p != head) {
			if (p.coef > 0)
				sb.append("+");
			sb.append(p);
			p = p.next;
		}
		return sb.toString();
	}
	
	private static class Term implements Comparable<Term> {
		int coef;
		int[] exp;
		Term next;
		static final char[] names = {'x', 'y', 'z'};
		
		Term(Boolean neg, int[] params) {
			if (params.length-1 > names.length) {
				throw new IllegalArgumentException();
			}
			if (neg)
				params[0] = -params[0];
			this.coef = params[0];
			this.exp = new int[params.length-1];
			for (int i = 1; i < params.length; i++)
				this.exp[i-1] = params[i];
			next = null;
		}
		
		public Term(int coef) {
			this.coef = coef;
			exp = null;
			next = null;
		}
		
		public Term(Term t) {
			coef = t.coef;
			exp = t.exp.clone();
			next = null;
		}
		
		@Override
		public int compareTo(Term term) {
			if (coef == 0) {
				if (term.coef == 0)
					return 0;
				else
					return -1;
			} else {
				if (term.coef == 0)
					return 1;
			}
			for (int i = 0; i < exp.length; i++) {
				if (exp[i] == term.exp[i])
					continue;
				return exp[i] - term.exp[i];
			}
			return 0;
		}
		
		@Override
		public String toString() {
			if (coef == 0)
				return "head";
			StringBuilder sb = new StringBuilder();
			if (coef > 1 || coef < -1)
				sb.append(coef);
			else if (coef == -1)
				sb.append('-');
			for (int i = 0; i < exp.length; i++) {
				if (exp[i] > 0) {
					sb.append(names[i]);
					if (exp[i] > 1)
						sb.append("^").append(exp[i]);
				}
			}
			return sb.toString();
		}
	}
/*
x4+2x3y+3x2y2+4xy3+5y4
x2-2xy+y2
 */
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			String s = sc.nextLine();
			Polynomial p = new Polynomial(s);
			s = sc.nextLine();
			Polynomial q = new Polynomial(s);
			
			System.out.println(p);
			System.out.println(q);
			
			q.multiply(p);
			System.out.println(q);
			
		}
	}

}