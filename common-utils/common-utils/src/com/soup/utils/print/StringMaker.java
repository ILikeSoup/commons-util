package com.soup.utils.print;

@FunctionalInterface
public interface StringMaker<T> {
	
	String from(T t);
	
}
