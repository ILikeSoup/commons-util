package com.soup.faker;

@FunctionalInterface
public interface Faker<T> {
	
	T fake();
	
}
