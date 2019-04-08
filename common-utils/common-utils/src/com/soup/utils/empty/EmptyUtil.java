package com.soup.utils.empty;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 空判断工具类
 * 
 * 此处的空表示：null/空串/没有元素的集合或数组/自定义空
 * @author soup
 * @date 2018年5月18日 上午11:21:55 
 * @see Emptiable
 */
public class EmptyUtil {

	/**
	 * 判断一个对象是否为空，并且根据它的类型来判断它是否是空的
	 * @param o
	 * @return
	 */
	public static boolean isEmpty(Object o) {
		if(o == null) {
			return true;
		} else {
			if(o instanceof String) {
				return isEmpty((String) o);
			} else if(o instanceof Collection<?>) {
				return isEmpty((Collection<?>) o);
			} else if(o instanceof Map<?, ?>) {
				return isEmpty((Map<?, ?>) o);
			} else if(o instanceof Properties) {
				return isEmpty((Properties) o);
			} else if(o instanceof Emptiable) {
				return isEmpty((Emptiable) o);
			} else if(o.getClass().isArray()) {
				return isEmpty((Object[]) o);
			}
			
		}
		return false;
	}

	/**
	 * 多个对象一起判断，所有对象空，返回true
	 * @param objs 若为Null 返回 true
	 * @return
	 */
	public static boolean isEmptyEach(Object... objs) {
		if(isEmpty(objs)) {
			return true;
		}
		for(Object o : objs) {
			if(!isEmpty(o)) {
				return false;
			}
		}
		return true;
	}

	public static <T> boolean isEmptyEach(List<T> objs) {
		if(isEmpty(objs)) {
			return true;
		}
		return isEmptyEach(objs.toArray());
	}

	/**
	 * 多个对象一起判断，其中至少有一个对象空，返回true
	 * @param objs 若为空返回 true
	 * @return
	 */
	public static boolean isEmptyOne(Object... objs) {
		if(isEmpty(objs)) {
			return true;
		}
		for(Object o : objs) {
			if(isEmpty(o)) {
				return true;
			}
		}
		return false;
	}

	public static <T> boolean isEmptyOne(List<T> objs) {
		if(isEmpty(objs)) {
			return true;
		}
		return isEmptyOne(objs.toArray());
	}

	/**
	 * 多个对象一起判断，发现一个对象为空，立刻返回该对象
	 * @param objs
	 * @return
	 */
	@SafeVarargs
	public static <T> T findEmptyOne(T... objs) {
		if(isEmpty(objs)) {
			return null;
		}
		for(T o : objs) {
			if(isEmpty(o)) {
				return o;
			}
		}
		return null;
	}

	/**
	 * 字符串判空，默认trim
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return isEmptyTrim(str, true);
	}

	/**
	 * 字符串判空，有trim选项
	 * @param str
	 * @param trim
	 * @return
	 */
	public static boolean isEmptyTrim(String str, boolean trim) {
		if(str == null) {
			return true;
		}
		if(trim) {
			return "".equals(str.trim());
		} else {
			return "".equals(str);
		}
	}

	/**
	 * map判空
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	/**
	 * Properties判空
	 * @param prop
	 * @return
	 */
	public static boolean isEmpty(Properties prop) {
		return prop == null || prop.isEmpty();
	}

	/**
	 * 数组判空
	 * @param array
	 * @return
	 */
	public static <T> boolean isEmpty(T[] array) {
		return array == null || array.length == 0;
	}

	/**
	 * 集合判空
	 * @param coll
	 * @return
	 */
	public static boolean isEmpty(Collection<?> coll) {
		return coll == null || coll.isEmpty();
	}

	/**
	 * 实现可空接口对象判空
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Emptiable obj) {
		return obj.isEmpty();
	}
	
}
