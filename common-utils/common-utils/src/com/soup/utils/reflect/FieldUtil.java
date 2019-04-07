package com.soup.utils.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
		return ListFilter.findFirst(all, field -> field.getName().equals(fieldName));
	}

	private static Field getField(List<Field> all, String fieldName, int index) {
		return ListFilter.find(all, field -> field.getName().equals(fieldName), index);
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
			Field one = null;
			int index = 0;
			// fieldNames中有若有重名，则寻找父类属性
			// XXX 算法需调整，每次循环都会从头开始查找
			do {
				one = getField(fields, fieldName, index++);
			} while(one != null && result.contains(one));
			if(one != null) {
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
		return ListFilter.findEach(getDeclaredFields(clazz), field -> null != field.getAnnotation(anno));
	}

	public static Object getFieldValue(Object obj, Field field) {
		if(obj == null) {
			return null;
		}
		try {
			if(!field.isAccessible()) {
				field.setAccessible(true);
			}
			return field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getFieldValue(Object obj, String fieldName) {
		return getFieldValue(obj, getField(obj.getClass(), fieldName));
	}

	public static Object getFieldValueByGetter(Object obj, Field field) {
		try {
			Method getter = field.getDeclaringClass().getMethod(getter(field.getName(), field.getType() == Boolean.class || field.getType() == boolean.class));
			if(!getter.isAccessible()) {
				getter.setAccessible(true);
			}
			return getter.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getFieldValueByGetter(Object obj, String fieldName) {
		return getFieldValueByGetter(obj, getField(obj.getClass(), fieldName));
	}
	
	public static void setFieldValue(Object obj, Field field, Object value) {
		try {
			if(!field.isAccessible()) {
				field.setAccessible(true);
			}
			field.set(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setFieldValue(Object obj, String fieldName, Object value) {
		setFieldValue(obj, getField(obj.getClass(), fieldName), value);
	}
	
	public static void setFieldValueBySetter(Object obj, Field field, Object value) {
		try {
			Method setter = field.getDeclaringClass().getMethod(setter(field.getName()), field.getType());
			if(!setter.isAccessible()) {
				setter.setAccessible(true);
			}
			setter.invoke(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setFieldValueBySetter(Object obj, String fieldName, Object value) {
		setFieldValueBySetter(obj, getField(obj.getClass(), fieldName), value);
	}

	public static String getter(String fieldName) {
		return getter(fieldName, false);
	}

	public static String getter(String fieldName, boolean isBool) {
		return (isBool ? "is" : "get") + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
	}

	public static String setter(String fieldName) {
		return "set"+ Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
	}

}
