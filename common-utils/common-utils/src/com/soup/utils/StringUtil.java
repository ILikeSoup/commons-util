package com.soup.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class StringUtil {
	
	public static String toString(Object obj) {
		if(obj == null) {
			return "";
		}
		if(obj.getClass().isArray()) {
			return Arrays.toString((Object[]) obj);
		} else if(obj.getClass() == Date.class){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			return sdf.format((Date) obj);
		}
		return obj.toString();
	}
	
	public static boolean equals(String first, String second) {
		if(first == null) {
			if(second == null) {
				return true;
			}
			return false;
		} 
		return first.equals(second);
	}
	
	/**
	 * 取出字符串str中从左至右第num个字符c的位置
	 * @param str
	 * @param c
	 * @param num
	 * @return
	 */
	public static int indexOf(String str, char c, int num) {
		char[] array = str.toCharArray();
		int n = 0;
		for(int i = 0; i < array.length; i++) {
			if(array[i] == c && ++n == num) {
				return i;
			}
		}
 		return -1;
	}
	
	public static String valueOf(String str) {
		return str == null ? "" : str;
	}
	
}
