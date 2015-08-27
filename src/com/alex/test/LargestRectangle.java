package com.alex.test;

import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map;

public class LargestRectangle {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        TreeMap<Integer, Integer> builds = new TreeMap<>();
        for (int i = 0; i < num; i++) {
            Integer height = Integer.valueOf(sc.nextInt());
            Integer broadth = builds.get(height);
            if (broadth == null)
                builds.put(height, Integer.valueOf(1));
            else
                builds.put(height, Integer.valueOf(broadth + 1));
        }
        sc.close();
    
        Map.Entry<Integer, Integer> highestBuilding = builds.lastEntry();
        int highest = highestBuilding.getKey();
        int broadth = highestBuilding.getValue();
        long area = highest * broadth;
        //System.out.println(builds);
    
        while(true) {
            Map.Entry<Integer, Integer> b = builds.lowerEntry(highest);
            if (b == null)
               break;
            highest = b.getKey();
            broadth += b.getValue();
            long na = highest * broadth;
            //System.out.println(na);
            if (na > area)
                area = na;
        }
        System.out.println(area);
        sc.close();
	}

}
