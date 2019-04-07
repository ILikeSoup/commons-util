package com.soup.utils.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.soup.utils.ListFilter;
import com.soup.utils.empty.EmptyUtil;

/**
 * 用于获取域的工具类
 * @author soup
 * @date 2018年7月2日 下午5:21:25 
 */
public class FieldUtil {
	/**
	 * 获取clazz及其父类的所有域，[不包括Object]
	 * @param clazz
	 * @return
	 */
	public static List<Field> getDeclaredFields(Class<?> clazz) {
		List<Field> result = new ArrayList<Field>();
		result.addAll(Arrays.asList(clazz.getDeclaredFields()));
		while((clazz = clazz.getSuperclass()) != Object.class) {
			Field[] superFields = clazz.getDeclaredFields();
			if(null != superFields && superFields.length != 0) {
				result.addAll(Arrays.asList(superFields));
			}
		}
		return result;
	}

	
	/**
	 * 获取指定类的指定域
	 * @param fieldName
	 * @param clazz
	 * @return
	 */
	public static Field getField(Class<?> clazz, String fieldName) {
		return getField(getDeclaredFields(clazz), fieldName);
	}
	
	private static Field getField(List<Field> all, String fieldName) {
		return ListFilter.findFirst(all, new ListFilter<Field>() {
			@Override
			public boolean filter(Field field) {
				return field.getName().equals(fieldName);
			}
		});
	}
	
	/**
	 * 获取指定类的指定名字列表的域
	 * @param clazz
	 * @param fieldNames
	 * @return
	 */
	public static List<Field> getFields(Class<?> clazz, String... fieldNames) {
		if(EmptyUtil.isEmpty(fieldNames)) {
			return getDeclaredFields(clazz);
		}
		List<Field> fields = getDeclaredFields(clazz);
		List<Field> result = new ArrayList<Field>();
		for(String fieldName : fieldNames) {
			Field one = getField(fields, fieldName);
			if(one != null && !result.contains(one)) {	// 不允许重复
				result.add(one);
			}
		}
		return result;
	}
	
	/**
	 * 获取clazz及其父类中的所有带有anno注解的属性
	 * @param clazz
	 * @param anno
	 * @return
	 */
	public static List<Field> getFieldsWithAnno(Class<?> clazz, final Class<? extends Annotation> anno) {
		return ListFilter.findEach(getDeclaredFields(clazz), new ListFilter<Field>() {
			@Override
			public boolean filter(Field obj) {
				return null != obj.getAnnotation(anno);
			}
		});
	}

	/**
	 * 通过属性名，获取Field，包含父类的Field
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static Field getDeclaredField(Class<?> clazz, String fieldName) {
		List<Field> fields = getDeclaredFields(clazz);
		if(EmptyUtil.isEmpty(fields)) {
			return null;
		}
		for(Field field : fields) {
			if(field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}
	
	public static Object getFieldValue(Object obj, Field field) {
		try {
			Method getter = obj.getClass().getMethod(getter(field.getName()));
			return getter.invoke(obj);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getter(String fieldName) {
		return "get"+ fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	public static String setter(String fieldName) {
		return "set"+ fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
	
}
