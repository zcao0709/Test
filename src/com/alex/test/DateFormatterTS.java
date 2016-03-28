package com.alex.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatterTS {

	public final static ThreadLocal<DateFormat> formatter =
			ThreadLocal.withInitial(() -> new SimpleDateFormat("dd-MMM-yyyy", Locale.US));
	
	public static void main(String[] args) {
		Runnable hello = () -> System.out.println("hello");
		Thread t = new Thread(hello);
		t.start();
		System.out.println(DateFormatterTS.formatter.get().format(new Date()));
	}
}