package com.alex.test;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class ListGraph {

	private static int WEIGHT = 6;
	private Vertex[] vs;
	private int start;
	
	public ListGraph(int nNode) {
		vs = new Vertex[nNode];
		for (int i = 0; i < nNode; i++) {
			vs[i] = new Vertex(i + 1);
		}
	}
	
	public void addEdge(int s, int d, int w) {
		Vertex start = vs[s - 1];
		Edge dest = new Edge(vs[d - 1], w);
		dest.next = start.edges;
		start.edges = dest;
		
		start = vs[d - 1];
		dest = new Edge(vs[s - 1], w);
		dest.next = start.edges;
		start.edges = dest;
	}
	
	public void setStart(int i) {
		start = i - 1;
		vs[start].dist = 0;
	}
	
	// only for fixed weight
	public void bfs() {
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
					v.pred = start;
					q.offer(v);
				//} else {
					//if (v.dist > start.dist + e.weight)
						//v.dist = start.dist + e.weight;
				}
				e = e.next;
			}
			start.color = Color.BLACK;
		}
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
		for (int i = 0; i < vs.length; i++) {
			if (i == start)
				continue;
			System.out.print(vs[i].dist + " ");
		}
		System.out.println();
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
		Color color;
		int dist;
		Vertex pred;
		Edge edges;
		
		Vertex(int v) {
			val = v;
			color = Color.WHILTE;
			dist = -1;
			pred = null;
			edges = null;
		}
		
		@Override
		public String toString() {
			return val + color.toString() + dist;
		}
	}
	
	private static class Edge {
		Vertex dest;
		int weight;
		Edge next;
		
		Edge(Vertex d) {
			this(d, WEIGHT);
		}
		
		Edge(Vertex d, int w) {
			dest = d;
			weight = w;
			next = null;
		}
		@Override
		public String toString() {
			return "->" + dest + weight;
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();

		for (int i = 0; i < n; i++) {
			int v = sc.nextInt();
			int e = sc.nextInt();
			ListGraph g = new ListGraph(v);
			for (int j = 0; j < e; j++)
				g.addEdge(sc.nextInt(), sc.nextInt(), sc.nextInt());
			g.setStart(sc.nextInt());
			g.dijkstra();
			g.printPath();
		}
		sc.close();
	}
}
