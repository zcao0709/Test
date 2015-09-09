package com.alex.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// not completely finished
public class MultiAssemblyLineFastestWay {
	private int length;
	private List<AssemblyLine> lines;
	private List<Time> times;
	private List<Path> paths;
	private int fastTime;
	private int fastLeaveLine;
	
	public MultiAssemblyLineFastestWay(int num, int len, int[] ls) {
		length = len;
		lines = new ArrayList<>(num);
		times = new ArrayList<>(num);
		paths = new ArrayList<>(num);
		int j = 0;
		for (int i = 0; i < num; i++) {
			for (; j < 2 * (i+1); j += 2) {
				lines.add(new AssemblyLine(ls[j], ls[j+1], length));
			}
			times.add(new Time(length));
			paths.add(new Path(length));
		}
		fastTime = Integer.MAX_VALUE;
		fastLeaveLine = -1;
	}
	
	public void addLine(int[] stns) {
		int j = 0;
		for (int i = 0; i < lines.size(); i++) {
			for (; j < (2*length) * (i+1); j += 2) {
				lines.get(i).addStation(stns[j], stns[j+1]);
			}
		}
		for (AssemblyLine line : lines)
			System.out.println(line);
		fastestWay();
	}
	
	public int findFastestTime() {
		return fastTime;
	}
	
	public int[] findFastestWay() {
		int[] ret = new int[length];
		ret[length-1] = fastLeaveLine;
		for (int i = length-1; i > 0; i--) {
			ret[i-1] = paths.get(ret[i]).paths[i];
			/*if (ret[i] == 1)
				ret[i-1] = path1[i];
			else
				ret[i-1] = path2[i];*/
		}
		return ret;
	}
	
	private void fastestWayToStation(int n) {
		for (int j = 0; j < lines.size(); j++) {
			fastestWayToStationOnLine(n, j);
		}
	}
	
	private void fastestWayToStationOnLine(int ns, int nl) {
		int minTime = Integer.MAX_VALUE;
		int minLine = -1;
		for (int j = 0; j < lines.size(); j++) {
			int time;
			if (nl == j) {
				time = times.get(j).times[ns-1];
			} else {
				time = times.get(j).times[ns-1] + lines.get(j).getStn(ns-1).swch;
			}
			time += lines.get(nl).getStn(ns).pass;
			if (minTime > time) {
				minTime = time;
				minLine = j;
			}
		}
		times.get(nl).times[ns] = minTime;
		paths.get(nl).paths[ns] = minLine;
	}
	
	private void fastestWay() {
		for (int i = 0; i < lines.size(); i++) {
			times.get(i).times[0] = lines.get(i).startTime();
		}
		for (int i = 1; i < length; i++) {
			fastestWayToStation(i);
		}
		for (int i = 0; i < lines.size(); i++) {
			if (fastTime > times.get(i).times[length-1] + lines.get(i).leave) {
				fastTime = times.get(i).times[length-1] + lines.get(i).leave;
				fastLeaveLine = i;
			}
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
		
		Station getStn(int i) {
			return statns.get(i);
		}

		void addStation(int p, int s) {
			Station sta = new Station(p, s);
			statns.add(sta);
		}
		
		int startTime() {
			return enter + statns.get(0).pass;
		}
		@Override
		public String toString() {
			return enter + "-" + statns.toString() + "-" + leave;
		}
	}

	private static class Time {
		int[] times;
		
		Time(int n) {
			times = new int[n];
		}
		@Override
		public String toString() {
			return Arrays.toString(times);
		}
	}
	
	private static class Path {
		int[] paths;
		
		Path(int n) {
			paths = new int[n];
		}
		@Override
		public String toString() {
			return Arrays.toString(paths);
		}
	}
	
	private static class Station {
		int pass;
		int swch;

		Station(int p, int s) {
			pass = p;
			swch = s;
		}
		@Override
		public String toString() {
			return pass + ":" + swch;
		}
	}

	//2
	//6
	//2 3 4 2
	//7 2 9 3 3 1 4 3 8 4 4 0
	//8 2 5 1 6 2 4 2 5 1 7 0
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int nLine = sc.nextInt();
		int nStn = sc.nextInt();
		int[] lines = new int[nLine*2];
		for (int i = 0; i < lines.length; i++)
			lines[i] = sc.nextInt();
		MultiAssemblyLineFastestWay fastest = new MultiAssemblyLineFastestWay(nLine, nStn, lines);
		int[] stations = new int[nLine * nStn * 2];
		for (int i = 0; i < stations.length; i++)
			stations[i] = sc.nextInt();
		sc.close();
		
		fastest.addLine(stations);
		System.out.println(fastest.findFastestTime());
		System.out.println(Arrays.toString(fastest.findFastestWay()));
	}
}