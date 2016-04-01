package acp;

import java.util.Scanner;

// 2.2.4 (1)
public class CircularList<E> {
	private Item<E> ptr; // pointing to the last item in list.
	
	public CircularList() {
		ptr = null;
	}
	
	public void addLeft(E data) {
		Item<E> item = new Item<>(data);
		if (ptr == null) {
			ptr = item;
			item.next = item;
		} else {
			item.next = ptr.next;
			ptr.next = item;
		}
	}
	
	public void addRight(E data) {
		Item<E> item = new Item<>(data);
		if (ptr == null) {
			ptr = item;
			item.next = ptr;
		} else {
			item.next = ptr.next;
			ptr.next = item;
			ptr = item;
		}
	}
	
	private Item<E> find(int index) {
		int i = 0;
		Item<E> p = ptr.next;
		// this is circular, so we don't check 
		// if index is out of range
		while (i != index) {
			i++;
			p = p.next;
		}
		return p;
	}
	
	public void add(int index, E data) {
		Item<E> p = find(index);
		E tmp = p.data;
		p.data = data;
		data = tmp;
		Item<E> q = new Item<>(data);
		q.next = p.next;
		p.next = q;
	}
	
	public E delete(int index) {
		Item<E> p = find(index);
		// remove item p by copy whole item p.next to p and remove item p.next
		// so not necessary to trace Item before p.
		E data = p.data;
		Item<E> q = p.next;
		p.data = q.data;
		p.next = q.next;
		
		q.data = null;
		q.next = null;
		return data;
	}
	
	public E deleteLeft() {
		if (ptr == null)
			return null;
		Item<E> p = ptr.next;
		ptr.next = p.next;
		return p.data;
	}
	
	public void reverse() {
		if (ptr == null)
			return;
		Item<E> prev = ptr;
		Item<E> curr = prev.next;
		while (curr != ptr) {
			Item<E> next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		curr.next = prev;
	}
	
	@Override
	public String toString() {
		if (ptr == null)
			return "";
		StringBuilder sb = new StringBuilder();
		sb.append("S");
		Item<E> i = ptr.next;
		while (i != ptr) {
			sb.append("->").append(i.data);
			i = i.next;
		}
		sb.append("->").append(ptr.data);
		return sb.toString();
	}
	
	private static class Item<E> {
		E data;
		Item<E> next;
		
		Item(E data) {
			this.data = data;
			this.next = null;
		}
		
		@Override
		public String toString() {
			return data.toString();
		}
	}
/*
6
1 2 3 4 5 6
 */
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			int n = sc.nextInt();
			CircularList<Integer> cl = new CircularList<>();
			int i = 0;
			for (; i < n/2; i++) {
				cl.addLeft(sc.nextInt());
			}
			System.out.println(cl);
			for (; i < n; i++) {
				//cl.addRight(sc.nextInt());
				cl.add(i, sc.nextInt());
				System.out.println(cl);
			}
			System.out.println(cl);
			System.out.println(cl.delete(1));
			//System.out.println(cl.deleteLeft());
			System.out.println(cl);
			//cl.reverse();
			//System.out.println(cl);
		}
	}
}
