package com.alex.test;

import java.io.File;
import java.util.Scanner;

public class ListDir {
	private static String INDENT = "    ";
	
	private static void printDir(File dir, StringBuilder indent) {
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				System.out.println(indent.toString() + files[i].getName());
			} else if (files[i].isDirectory()) {
				indent.append(INDENT);
				printDir(files[i], indent);
				indent.delete(0, INDENT.length());
			}
		}
	}
	
	public static void main(String[] args) {
		String root;
		try (Scanner sc = new Scanner(System.in)) {
			 root = sc.nextLine();
		}
		StringBuilder indent = new StringBuilder();
		printDir(new File(root), indent);
	}
}
