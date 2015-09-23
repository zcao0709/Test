package com.alex.test;

public interface MapEntry<V> {
	int getKey();
	V getValue();
	V setValue(V val);
}
