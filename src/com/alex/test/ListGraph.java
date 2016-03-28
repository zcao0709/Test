package com.alex.test;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ListGraph {

	private Vertex[] vs;
	private int start;
	
	private ListGraph(Vertex[] vs) {
		this.vs = new Vertex[vs.length];
		for (int i = 0; i < vs.length; i++) {
			this.vs[i] = new Vertex(vs[i]);
		}
	}
	
	public ListGraph(int nNode) {
		vs = new Vertex[nNode];
		for (int i = 0; i < nNode; i++) {
			vs[i] = new Vertex(i + 1);
		}
	}
	
	public void addEdge(int s, int d, int w) {
		addEdge(s, d, w, false);
	}
	
	public void addEdge(int s, int d, int w, boolean undirected) {
		
		Vertex start = vs[s];
		Edge dest = new Edge(vs[d], w);
		dest.next = start.edges;
		start.edges = dest;
		
		if (undirected) {
			start = vs[d];
			dest = new Edge(vs[s], w);
			dest.next = start.edges;
			start.edges = dest;
		}
	}
	
	public void setStart(int i) {
		start = i;
		vs[start].dist = 0;
	}
	
	public int getStart() {
		return start;
	}
	
	public ListGraph transpose() {
		ListGraph g = new ListGraph(vs);
		for (Vertex v : g.vs) {
			v.edges = null;
		}
		for (Vertex v : vs) {
			Edge e = v.edges;
			while (e != null) {
				g.addEdge(e.dest.idx, v.idx, e.weight, false);
				e = e.next;
			}
		}
		g.setStart(start);
		//System.out.println(vs[0] + "," + g.vs[0]);
		return g;
	}
	
	private void init() {
		for (Vertex v : vs) {
			v.color = Color.WHILTE;
			v.pred = null;
			v.dist = v == vs[start] ? 0 : -1;
			//v.dt = 0;
			//v.ft = 0;
		}
	}
	
	public void bfs() {
		init();
		Queue<Vertex> q = new LinkedList<>();
		vs[start].color = Color.GREY;
		q.offer(vs[start]);
		
		while (q.size() > 0) {
			Vertex start = q.poll();
			Edge e = start.edges;
			while (e != null) {
				//System.out.println(e);
				Vertex v = e.dest;
				if (v.color == Color.WHILTE) {
					v.color = Color.GREY;
					v.dist = start.dist + 1;
					v.pred = start;
					q.offer(v);
				}
				e = e.next;
			}
			start.color = Color.BLACK;
		}
	}
	
	public void dfs(int start) {
		init();
		
		int time = dfsVisit(vs[start], 0);
		for (Vertex v : vs) {
			if (v.color == Color.WHILTE)
				time = dfsVisit(v, time);
		}
	}
	
	public void dfs() {
		int time = 0;
		PHeap<Vertex> heap = new PHeap<>(vs.length, new Comparator<Vertex>() {
			@Override
			public int compare(Vertex v1, Vertex v2) {
				return v2.ft - v1.ft;
			}
		});
		for (Vertex v : vs)
			heap.offer(v);
		while (heap.size() > 0) {
			Vertex v = heap.poll();
			if (v.color == Color.WHILTE)
				time = dfsVisit(v, time);
		}
	}
	
	private int dfsVisit(Vertex v, int time) {
		v.color = Color.GREY;
		time++;
		v.dt = time;
		Edge e = v.edges;
		while (e != null) {
			Vertex u = e.dest;
			if (u.color == Color.WHILTE) {
				u.pred = v;
				time = dfsVisit(u, time);
			}
			e = e.next;
		}
		v.color = Color.BLACK;
		v.ft = ++time;
		return time;
	}
	
	public void dijkstra() {
		PHeap<Vertex> heap = new PHeap<>(vs.length, new Comparator<Vertex>() {
			@Override
			public int compare(Vertex v1, Vertex v2) {
				if (v1.dist == -1 && v2.dist == -1)
					return 0;
				else if (v1.dist == -1)
					return 1;
				else if (v2.dist == -1)
					return -1;
				else
					return v1.dist - v2.dist;
			}
		});
		for (Vertex v : vs)
			heap.offer(v);
		while (heap.size() > 0) {
			//System.out.println(heap.toString());
			Vertex start = heap.poll();
			if (start.dist == -1)
				break;
			//System.out.println(start);
			//System.out.println(heap);
			Edge e = start.edges;
			while (e != null) {
				//System.out.println(e);
				Vertex v = e.dest;
				if (v.dist == -1 || v.dist > start.dist + e.weight) {
					v.dist = start.dist + e.weight;
					v.pred = start;
					//heap.offer(v);
				}
				e = e.next;
			}
			heap.heapify();
		}
	}
	
	public void printPath() {
		for (Vertex v : vs) {
			printPath(v);
			System.out.println();
		}
		System.out.println();
	}
	
	private void printPath(Vertex v) {
		if (v == vs[start])
			System.out.print(v + " ");
		else {
			if (v.pred == null)
				System.out.print("no path from " + vs[start] + " to " + v);
			else {
				printPath(v.pred);
				System.out.print(v + " ");
			}
		}
	}
	
	public void printTree() {
		for (Vertex v : vs) {
			if (v.ft - v.dt == 1) {
				printTree(v);
				System.out.println();
			}
		}
		System.out.println();
	}
	
	private void printTree(Vertex v) {
		if (v.pred != null) {
			printTree(v.pred);
		}
		System.out.print(v + " ");
	}
	
	public void printDist() {
		for (int i = 0; i < vs.length; i++) {
			if (i == start)
				continue;
			System.out.print(vs[i].dist + " ");
		}
		System.out.println();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Vertex v : vs) {
			sb.append(v);
			if (v == vs[start])
				sb.append("*");
			Edge e = v.edges;
			while (e != null) {
				sb.append(e).append(" ");
				e = e.next;
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private static enum Color {
		WHILTE("w"), BLACK("b"), GREY("g");
		
		private String c;
		
		private Color(String c) {
			this.c = c;
		}
		@Override
		public String toString() {
			return c;
		}
	}
	
	private static class Vertex {
		int val;
		int idx;
		Color color;
		int dist;
		int dt; //discovery time in dfs
		int ft; //finish time in dfs
		Vertex pred;
		Edge edges;
		
		Vertex(int v, int i) {
			val = v;
			idx = i;
			color = Color.WHILTE;
			dist = -1;
			pred = null;
			edges = null;
		}
		
		Vertex(int v) {
			this(v, v - 1);
		}
		
		Vertex(Vertex v) {
			this(v.val, v.idx);
			dt = v.dt;
			ft = v.ft;
		}
		
		@Override
		public String toString() {
			//return val + color.toString() + dist;
			return val + "-" + dt + "-" + ft;
			//return String.valueOf(val);
		}
	}
	
	private static class Edge {
		Vertex dest;
		int weight;
		Edge next;
		
		Edge(Vertex d) {
			this(d, 1);
		}
		
		Edge(Vertex d, int w) {
			dest = d;
			weight = w;
			next = null;
		}
		
		@Override
		public String toString() {
			return "->" + dest;
		}
	}
	/*
1
8 14
1 2
2 3
2 5
2 6
3 4
3 7
4 3
4 8
5 1
5 6
6 7
7 6
7 8
8 8
3
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();

		for (int i = 0; i < n; i++) {
			int v = sc.nextInt();
			int e = sc.nextInt();
			ListGraph g = new ListGraph(v);
			for (int j = 0; j < e; j++)
				g.addEdge(sc.nextInt()-1, sc.nextInt()-1, 1);
			g.setStart(sc.nextInt()-1);
			System.out.println(g);
			g.dfs(g.getStart());
			g.printTree();
			ListGraph tg = g.transpose();
			tg.dfs();
			tg.printTree();
			System.out.println(tg);
		}
		sc.close();
	}
}
