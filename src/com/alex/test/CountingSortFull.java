package com.alex.test;

import java.util.Scanner;

public class CountingSortFull {
    private static int size = 0;

    private static void countSort(Node[] a, int[] c) {
        for (int i = 1; i < size; i++)
            c[i] += c[i-1];
        
        Node[] b = new Node[a.length];
        for (int i = a.length-1; i >= a.length/2; i--) {
            int key = a[i].v;
            b[c[key]-1] = a[i];
            c[key]--;
        }
        StringBuilder sb = new StringBuilder();
        for (Node node : b) {
            if (node == null)
                sb.append("- ");
            else
                sb.append(node);
        }
        System.out.println(sb);
    }
    
    private static class Node {
        int v;
        String s;
        
        Node(int v, String s) {
            this.v = v;
            this.s = s;
        }
        
        @Override
        public String toString() {
            return s + " ";
        }
    }
    
    /*
20
0 ab
6 cd
0 ef
6 gh
4 ij
0 ab
6 cd
0 ef
6 gh
0 ij
4 that
3 be
0 to
1 be
5 question
1 or
2 not
4 is
2 to
4 the
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Node[] a = new Node[n];
        int[] c = new int[100];
        for (int i = 0; i < n; i++) {
            a[i] = new Node(sc.nextInt(), sc.next());
            c[a[i].v]++;
            if (a[i].v > size)
                size = a[i].v;
        }
        sc.close();
        size++;
        countSort(a, c);
    }
}
