package com.alex.test;

import java.util.*;

public class MinAverageWaiting {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        List<Customer> cs = new ArrayList<Customer>(num);
        for (int i = 0; i < num; i++) {
            cs.add(new Customer(sc.nextLong(), sc.nextLong()));
        }
        Collections.sort(cs);
        Queue<Customer> cames = new PriorityQueue<Customer>(num, new Comparator<Customer>() {
        	 public int compare(Customer c1, Customer c2) {
        		if (c1.pizza > c2.pizza)
        			return 1;
        		else if (c1.pizza < c2.pizza)
        			return -1;
        		else
        			return 0;
        	}
        }
        );
        long currTime = cs.get(0).come;
        long waitTime = 0;
        int end = 0;
        do {
            if (end != num) {
            	int lastEnd = end;
            	end = lastEnd + checkAvailableCustomers(currTime, cs.subList(lastEnd, cs.size()));
            	if (end == lastEnd && end < num && cames.size() == 0) {
            		currTime = cs.get(end).come;
            		end = checkAvailableCustomers(currTime, cs);
            	}
            	for (int i = lastEnd; i < end; i++) {
            		cames.add(cs.get(i));
            	}
            }
            if (cames.size() == 0)
            	break;
            Customer c = cames.poll();
            currTime = c.readyTime(currTime);
            waitTime += c.waitTime(currTime);
        } while (true);
        System.out.println(waitTime / num);
        sc.close();
    }
    
    private static int checkAvailableCustomers(long currTime, List<Customer> cs) {
        int pos = Collections.binarySearch(cs, new Customer(currTime, 0));
        if (pos >= 0) {
            return pos + 1;
        } else {
            return -(pos + 1);
        }
    }
    
    private static class Customer implements Comparable<Customer> {
        long come;
        long pizza;
        boolean served;
        
        Customer(long come, long pizza) {
            this.come = come;
            this.pizza = pizza;
            served = false;
        }
        
        long readyTime(long currTime) {
                return pizza + currTime;
        }
        
        long waitTime(long currTime) {
            long t = currTime - come;
            if (t < 0)
                return 0;
            else
                return t;
        }
        
        public int compareTo(Customer c) {
            if (come > c.come)
                return 1;
            else if (come == c.come)
                return 0;
            else
                return -1;
        }
        
        public String toString() {
            return "[" + come + ", " + pizza + "]";
        }
    }
}
