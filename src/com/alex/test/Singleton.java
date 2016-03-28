package com.alex.test;

public class Singleton {
	
	private static Singleton INSTANCE = new Singleton();
	
	private Singleton() {
		
	}
	
	public static Singleton getInstance() {
		return INSTANCE;
	}

	public static void main(String[] args) {
		System.out.println(SingletonEnum.INSTANCE);
	}
}
