package com.alex.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AssemblyLineFastestWay {
	private int length;
	private AssemblyLine line1;
	private AssemblyLine line2;
	private int[] time1;
	private int[] time2;
	private int[] path1;
	private int[] path2;
	private int fastTime;
	private int fastLeave;
	
	public AssemblyLineFastestWay(int len, int[] lines) {
		length = len;
		if (lines.length < 4)
			throw new IllegalArgumentException();
		line1 = new AssemblyLine(lines[0], lines[1], length);
		line2 = new AssemblyLine(lines[2], lines[3], length);
		path1 = new int[length];
		path2 = new int[length];
		time1 = new int[length];
		time2 = new int[length];
	}
	
	public void addLine(int[] statns) {
		int i = 0;
		for (; i < length*2; i += 2) {
			line1.addStation(statns[i], statns[i+1]);
		}
		for (; i < length*4; i += 2) {
			line2.addStation(statns[i], statns[i+1]);
		}
		fastestWay();
	}
	
	public int findFastestTime() {
		return fastTime;
	}
	
	public int[] findFastestWay() {
		int[] ret = new int[length];
		ret[length-1] = fastLeave;
		for (int i = length-1; i > 0; i--) {
			if (ret[i] == 1)
				ret[i-1] = path1[i];
			else
				ret[i-1] = path2[i];
		}
		return ret;
	}
	
	private void fastestWay() {
		time1[0] = line1.starttime();
		time2[0] = line2.starttime();
		
		for (int i = 1; i < length; i++) {
			if (time1[i-1] <= time2[i-1] + line2.get(i-1).swch) {
				time1[i] = time1[i-1] + line1.get(i).pass;
				path1[i] = 1;
			} else {
				time1[i] = time2[i-1] + line2.get(i-1).swch + line1.get(i).pass;
				path1[i] = 2;
			}
			if (time2[i-1] <= time1[i-1] + line1.get(i-1).swch) {
				time2[i] = time2[i-1] + line2.get(i).pass;
				path2[i] = 2;
			} else {
				time2[i] = time1[i-1] + line1.get(i-1).swch + line2.get(i).pass;
				path2[i] = 1;
			}
		}
		if (time1[length-1] + line1.leave < time2[length-1] + line2.leave) {
			fastTime = time1[length-1] + line1.leave;
			fastLeave = 1;
		} else {
			fastTime = time2[length-1] + line2.leave;
			fastLeave = 2;
		}
	}
	
	private static class AssemblyLine {
		int enter;
		int leave;
		List<Station> statns;

		AssemblyLine(int e, int l, int n) {
			enter = e;
			leave = l;
			statns = new ArrayList<>(n);
		}
		
		Station get(int i) {
			return statns.get(i);
		}

		void addStation(int p, int s) {
			Station sta = new Station(p, s);
			statns.add(sta);
		}
		
		int starttime() {
			return enter + statns.get(0).pass;
		}
	}

	private static class Station {
		int pass;
		int swch;

		Station(int p, int s) {
			pass = p;
			swch = s;
		}
	}

	//6
	//2 3 4 2
	//7 2 9 3 3 1 4 3 8 4 4 0
	//8 2 5 1 6 2 4 2 5 1 7 0
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] lines = new int[4];
		for (int i = 0; i < lines.length; i++)
			lines[i] = sc.nextInt();
		AssemblyLineFastestWay fastest = new AssemblyLineFastestWay(n, lines);
		int[] stations = new int[n * 4];
		for (int i = 0; i < stations.length; i++)
			stations[i] = sc.nextInt();
		sc.close();
		
		fastest.addLine(stations);
		System.out.println(fastest.findFastestTime());
		System.out.println(Arrays.toString(fastest.findFastestWay()));
	}

}
