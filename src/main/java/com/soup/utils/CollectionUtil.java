package com.soup.utils;

import java.util.Collection;

import com.soup.utils.empty.EmptyUtil;

public class CollectionUtil {
	
	public static <T> boolean contains(Collection<T> collection, T t) {
		if(collection == null) {
			return false;
		}
		return collection.contains(t);
	}
	
	public static <T> boolean contains(T[] array, T t) {
		if(EmptyUtil.isEmpty(array) || t == null) {
			return false;
		}
		for(T one : array) {
			if(t.equals(one)) {
				return true;
			}
		}
		return false;
	}
	
	
	
}
