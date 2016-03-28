package com.alex.testExtend;

import com.alex.test.Test;

public class TestExtend extends Test {

	public int i;
	protected int c;
	
	public static void main(String[] args) {

		TestExtend te = new TestExtend();
		Test t = new Test();
		System.out.println(te.c);
		//System.out.println(t.a);
		//System.out.println(t.b);
		te.makeTest(t);
		//TestExtend.Nested.makeNestedTest();
		Nested n = te.new Nested();
		n.makeNestedTest();
	}
	
	public void makeTest(Test t) {
		//System.out.println(t.b);
		Nested n = new Nested();
		n.makeNestedTest();
	}
	
	private class Nested {
		Nested() {
			//System.out.println(i);
		}
		
		void makeNestedTest() {
			System.out.println("Nested");
		}
	}

}
