package com.soup.utils.print;

import java.util.List;

import com.soup.utils.empty.EmptyUtil;

public class PrintUtil {
	
	public static String CONCAT = " : ";
	
	public static boolean PRINT_FLAG = true; 
	
	public static <T> void print(String prefix, T t, StringMaker<T> maker) {
		if(!PRINT_FLAG || EmptyUtil.isEmpty(t)) {
			return;
		}
		System.out.println(prefix + CONCAT + maker.from(t));
	}
	
	public static <T> void print(String prefix, String content) {
		if(!PRINT_FLAG) {
			return;
		}
		System.out.println(prefix + CONCAT + content);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> void printList(String prefix, List<T> list, StringMaker<T> maker) {
		printList(prefix, (T[]) list.toArray(), maker);
	}
	
	public static <T> void printList(String prefix, T[] list, StringMaker<T> maker) {
		if(!PRINT_FLAG || EmptyUtil.isEmpty(list)) {
			return;
		}
		System.out.print(prefix + CONCAT);
		System.out.print("[");
		int index = 0;
		for (T one : list) {
			System.out.print(maker.from(one)+ (++index == list.length ? "" : ", "));
		}
		System.out.print("]");
		System.out.println();
	}
}
