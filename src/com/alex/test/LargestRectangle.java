package com.alex.test;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class LargestRectangle {
    
    private static long increaseArea(long lastMax, List<Area> areas, int height) {
        
        ListIterator<Area> i = areas.listIterator();
        int boardth = 0;
        while (i.hasNext()) {
            Area a = i.next();
            if (a.h >= height)
                boardth = a.b > boardth ? a.b : boardth;
            else
                boardth = 0;
            if (!a.fixed) {
                if (a.h <= height)
                    a.b++;
                else
                    a.fixed = true;
            }
            long area = a.area();
            if (lastMax < area )
                lastMax = area;
            else {
                if (a.fixed)
                    i.remove();
            }
        }
        
        Area a = new Area(height, boardth+1);
        areas.add(a);
        if (a.area() > lastMax)
            return a.area();
        else
            return lastMax;
    }
    
    private static class Area {
        final int h;
        int b;
        boolean fixed;
        
        Area(int height, int boardth) {
            h = height;
            b = boardth;
            fixed = false;
        }
        
        long area() {
            return (long)h * b;
        }
        
        public String toString() {
            return "[" + h + ", " + b + "]";
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        
        List<Area> areas = new LinkedList<>();
        long maxArea = 0;
        for (int i = 0; i < num; i++) {
            int height = sc.nextInt();
            
            maxArea = increaseArea(maxArea, areas, height);
            //System.out.println(maxArea + areas.toString());
        }
        System.out.println(maxArea);
        sc.close();
    }
}
