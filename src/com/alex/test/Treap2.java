package com.alex.test;

import java.util.Random;
import java.util.Scanner;

public class Treap2 {

    Node root;
    
    public Treap2() {
        root = null;
    }
    
    public int size(Node root) {
        if (root == null)
            return 0;
        return root.size;
    }
    
    private void update(Node root) {
        if (root != null)
            root.size = 1 + size(root.left) + size(root.right);
    }
    
    public DNode split(Node root, int key) {
    	DNode pair = new DNode();
        if (root == null) {
            return pair;
        }
        if (size(root.left) >= key) {
            pair = split(root.left, key);
            root.left = pair.right;
            update(root);
            pair.right = root;
            System.out.println(root + "> " + key + " " + pair);
        } else {
            pair = split(root.right, key - size(root.left) - 1);
            root.right = pair.left;
            update(root);
            pair.left = root;
            System.out.println(root + "< " + key + " " + pair);
        }
        return pair;
    }
    
    public Node merge(Node left, Node right) {
        if (left == null) return right;
        if (right == null) return left;
        
        if (left.pri > right.pri) {
            left.right = merge(left.right, right);
            update(left);
            return left;
        } else {
            right.left = merge(left, right.left);
            update(right);
            return right;
        }
    }
    
    public Node getFirst() {
    	if (root == null)
    		return null;
    	return getFirst(root);
    }
    
    private Node getFirst(Node root) {
    	if (root.left != null)
    		return getFirst(root.left);
    	return root;
    }
    
    public Node getLast() {
    	if (root == null)
    		return null;
    	return getLast(root);
    }
    
    private Node getLast(Node root) {
    	if (root.right != null)
    		return getLast(root.right);
    	return root;
    }
    
    public void query1(int left, int right) {
    	DNode pair1 = split(root, right);
    	preorder(pair1.left);
    	System.out.println("query1");
    	preorder(pair1.right);
    	System.out.println("query1");
    	DNode pair2 = split(pair1.left, left-1);
    	preorder(pair2.left);
    	System.out.println("query1");
    	preorder(pair2.right);
    	System.out.println("query1");
    	
    	root = merge(pair2.left, pair1.right);
    	root = merge(pair2.right, root);
    }
    
    public void query2(int left, int right) {
    	DNode pair1 = split(root, right);
    	preorder(pair1.left);
    	System.out.println("query2");
    	preorder(pair1.right);
    	System.out.println("query2");
    	
    	DNode pair2 = split(pair1.left, left-1);
    	preorder(pair2.left);
    	System.out.println("query2");
    	preorder(pair2.right);
    	System.out.println("query2");
    	
    	root = merge(pair2.left, pair1.right);
    	root = merge(root, pair2.right);
    }
    
    public void add(int val, int pri) {
        Node n = new Node(val, pri);
        root = merge(root, n);
    }
    
    public void inorder() {
        inorder(root);
        System.out.println();
    }
    
    public void preorder() {
        preorder(root);
        System.out.println();
    }
    
    public void postorder() {
        postorder(root);
        System.out.println();
    }
    
    private void inorder(Node n) {
        if (n == null)
            return;
        inorder(n.left);
        System.out.print(n + " ");
        inorder(n.right);
    }
    
    private void preorder(Node n) {
        if (n == null)
            return;
        System.out.print(n + " ");
        preorder(n.left);
        preorder(n.right);
    }
    
    private void postorder(Node n) {
        if (n == null)
            return;
        postorder(n.left);
        postorder(n.right);
        System.out.print(n + " ");
    }

    private static class DNode {
        Node left;
        Node right;
        
        DNode() {
            left = null;
            right = null;
        }
        
        public String toString() {
            return "L:" + left + " R:" + right;
        }
    }
    
    private static class Node {
        int val;
        int pri;
        int size;
        
        Node left;
        Node right;
        
        Node(int val, int pri) {
            this.val = val;
            this.pri = pri;
            size = 1;
            left = null;
            right = null;
        }
        
        public String toString() {
            //return "[" + val + "," + pri + "," + size + "]";
        	//return String.valueOf(val);
        	return "[" + val + "," + size + "]";
        }
    }
    
    public void test() {
        DNode pair = split(root, 1);
        
        preorder(pair.left);
        System.out.println();
        preorder(pair.right);
    }
    
    public static void main(String[] args) {
        Treap2 t = new Treap2();
        Scanner sc = new Scanner(System.in);
        Random rd = new Random();
        int n = sc.nextInt();
        int m = sc.nextInt();
        for (int i = 0;  i < n; i++) {
            int v = sc.nextInt();
            //int p = sc.nextInt();
            //8
            //1 5 2 46 3 21 4 5 5 29 6 41 7 20 8 2
            //t.add(v, p);
            t.add(v, rd.nextInt(n * 10));
        }
        t.preorder();
        t.inorder();
        for (int i = 0; i < m; i++) {
        	int op = sc.nextInt();
            int low = sc.nextInt();
            int high = sc.nextInt();
            if (op == 1)
            	t.query1(low, high);
            else
            	t.query2(low, high);
        }
        System.out.println(t.getFirst());
        System.out.println(t.getLast());
        System.out.println(Math.abs(t.getFirst().val - t.getLast().val));
        t.preorder();
        t.inorder();
        //t.test();
        sc.close();
    }
}
