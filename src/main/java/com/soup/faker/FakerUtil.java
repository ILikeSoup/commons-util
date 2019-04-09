package com.soup.faker;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.soup.utils.reflect.FieldUtil;

public class FakerUtil {
	
	private static final Map<Class<?>, Faker<?>> FAKER_MAP = new HashMap<Class<?>, Faker<?>>();
	
	static {
		FAKER_MAP.put(int.class, () -> new Random().nextInt(5));
		FAKER_MAP.put(double.class, () -> new Random().nextInt(100)/2.0);
		FAKER_MAP.put(long.class, () -> new Random().nextInt(5) << 3);
		FAKER_MAP.put(Date.class, () -> new Date());
	}
	
	public static <T> void put(Class<T> clazz, Faker<T> faker) {
		FAKER_MAP.put(clazz, faker);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T fake(Class<T> clazz) {
		if(FAKER_MAP.containsKey(clazz)) {
			return (T) FAKER_MAP.get(clazz).fake();
		}
		return doFake(clazz);
	}
	
	private static <T> T doFake(Class<T> clazz) {
		ObjectFaker<T> objectFaker = createObjectFaker(clazz);
		T obj = null;
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		List<Field> fields = FieldUtil.getDeclaredFields(clazz);
		for(Field field : fields) {
			FieldUtil.setFieldValue(obj, field, fake(field.getType(), objectFaker));
		}
		FAKER_MAP.put(clazz, objectFaker);
		System.out.println(FAKER_MAP);
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T fake(Class<T> clazz, ObjectFaker<?> objectFaker){
		if(FAKER_MAP.containsKey(clazz)) {
			objectFaker.addFaker(FAKER_MAP.get(clazz));
			return (T) FAKER_MAP.get(clazz).fake();
		} else {
			T subObj = doFake(clazz);
			objectFaker.addFaker(FAKER_MAP.get(clazz));
			return subObj;
		}
	}
	
	private static <T> ObjectFaker<T> createObjectFaker(Class<T> clazz) {
		return new ObjectFaker<T>(clazz);
	}
	
}
