package com.alex.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class WeightedTaskQueue {
	
    private HashedPriorityQueue queue = new HashedPriorityQueue();
	
	public synchronized void offer(Runnable task) throws IllegalArgumentException {
		queue.offer(new Task(task));
	}
	
	public synchronized void offer(Runnable task, int time, TimeUnit tu) throws IllegalArgumentException {
		queue.offer(new Task(task, time, tu));
	}
	
	public synchronized Runnable poll() {
		Task t = queue.poll();
		if (t != null)
			return t.task;
		return null;
	}
	
	public synchronized boolean cancel(Runnable task) {
		return queue.remove(task);
	}
	
	public synchronized int size() {
		return queue.size();
	}
	
	public String toString() {
		return queue.toString();
	}
	
	private static class Task implements Comparable<Task> {
		Runnable task;
		long timeout;
		
		static final int DEFAULT_TIME = 0;
		static final TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;
		
		Task(Runnable task) throws IllegalArgumentException {
			this(task, DEFAULT_TIME, DEFAULT_TIMEUNIT);
		}
		
		Task(Runnable task, int time, TimeUnit tu) throws IllegalArgumentException {
			this.task = task;
			if (time < 0)
				time = 0;
			this.timeout = now() + tu.toNanos(time);
			if (this.timeout < 0)
				throw new IllegalArgumentException("Timer overflow");
		}
		
		long now() {
	        return System.nanoTime();
	    }
		
		@Override
		public int compareTo(Task t) {
			if (this.timeout > t.timeout)
				return 1;
			else if (this.timeout < t.timeout)
				return -1;
			else 
				return 0;
		}
		
		@Override
		public String toString() {
			return task + ":" + timeout;
		}
		
		boolean available() {
			if (timeout <= now())
				return true;
			return false;
		}
	}
	
	private static class HashedPriorityQueue {
		Map<Runnable, Integer> taskIndex = new HashMap<>();
		static final int DEFAULT_INIT_CAPACITY = 16;
		static final int MAX_CAPACITY = Integer.MAX_VALUE - 8;
		Task[] tasks;
		int size;
		
		public HashedPriorityQueue() {
			tasks = new Task[DEFAULT_INIT_CAPACITY];
			size = 0;
		}
		
		public int size() {
			return size;
		}
		
		public void offer(Task t) {
			if (size >= tasks.length)
				grow();
			if (size < tasks.length) {
				size++;
				if (size == 1) {
					tasks[0] = t;
					taskIndex.put(t.task, 0);
				} else {
					shiftUp(size-1, t);
				}
				return;
			}
			throw new RuntimeException("Internal error");
		}
		
		public Task poll() {
			if (size > 0) {
				Task ret = tasks[0];
				if (ret.available()) {
					size--;
					taskIndex.remove(ret.task);
					if (size > 0)
						shiftDown(0, tasks[size]);
					return ret;
				}
			}
			return null;
		}
		
		public boolean remove(Runnable r) {
			Integer index = taskIndex.remove(r);
			if (index == null)
				return false;
			size--;
			if (size > 0)
				shiftDown(index, tasks[size]);
			return true;
		}
		
		private void grow() {
			int newCapacity = tasks.length >= MAX_CAPACITY/2 ? MAX_CAPACITY : tasks.length*2;
			tasks = Arrays.copyOf(tasks, newCapacity);
		}
		
		private void shiftUp(int i, Task t) {
			while (i > 0) {
				int p = parent(i);
				if (tasks[p].compareTo(t) <= 0)
					break;
				tasks[i] = tasks[p];
				taskIndex.put(tasks[p].task, i);
				i = p;
			}
			tasks[i] = t;
			taskIndex.put(t.task, i);
		}
		
		private void shiftDown(int i, Task t) {
			while (i < (size >>> 1)) {
				int left = left(i);
				int right = right(i);
				int min = left;
				if (right < size && tasks[left].compareTo(tasks[right]) > 0)
					min = right;
				if (t.compareTo(tasks[min]) <= 0)
					break;
				tasks[i] = tasks[min];
				taskIndex.put(tasks[min].task, i);
				i = min;
			}
			tasks[i] = t;
			taskIndex.put(t.task, i);
		}
		
		private int left(int i) {
			return (i << 1) + 1;
		}
		
		private int right(int i) {
			return (i << 1) + 2;
		}
		
		private int parent(int i) {
			return (i - 1) >>> 1;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(size).append(": ");
			for (Map.Entry<Runnable, Integer> e : taskIndex.entrySet()) {
				sb.append(e.getKey()).append(":").append(e.getValue());
			}
			sb.append("-");
			for (int i = 0; i < size; i++)
				sb.append(tasks[i]).append(" ");
			return sb.toString();
		}
	}
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			int size = sc.nextInt();
			ArrayList<Runnable> runs = new ArrayList<>(size);
			WeightedTaskQueue q = new WeightedTaskQueue();
			Random rand = new Random();
			for (int i = 0; i < size; i++) {
				Runnable r = new Runnable() {
					
					@Override
					public void run() {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				};
				q.offer(r, rand.nextInt(size * 3), TimeUnit.SECONDS);
				runs.add(r);
				System.out.println(q);
			}
			for (int i = 0; i < size; i++) {
				System.out.println("removing: " + runs.get(i));
				q.cancel(runs.get(i));
				System.out.println(q);
			}
		}
	}
}
