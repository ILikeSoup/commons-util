package com.soup.faker;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.soup.utils.reflect.FieldUtil;

public class FakerUtil {
	
	private static FakerRegistry fakerRegistry = DefaultFakerRegistry.getInstance();
	
	static {
		fakerRegistry.register(int.class, () -> new Random().nextInt(5));
		fakerRegistry.register(double.class, () -> new Random().nextInt(100)/2.0);
		fakerRegistry.register(long.class, () -> (long) new Random().nextInt(5) << 3);
		fakerRegistry.register(Date.class, () -> new Date());
	}
	
	public static <T> void register(Class<T> clazz, Faker<T> faker) {
		fakerRegistry.register(clazz, faker);
	}
	
	public static <T> T fake(Class<T> clazz) {
		if(fakerRegistry.containsKey(clazz)) {
			return (T) fakerRegistry.get(clazz).fake();
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
			fakeOneField(objectFaker, obj, field);
		}
		fakerRegistry.register(clazz, objectFaker);
		return obj;
	}

	private static <T> void fakeOneField(ObjectFaker<T> objectFaker, T obj, Field field) {
		FieldUtil.setFieldValue(obj, field, fake(field, objectFaker));
	}
	
	private static Object fake(Field field, ObjectFaker<?> objectFaker){
		Class<?> clazz = field.getType();
		if(fakerRegistry.containsKey(clazz)) {
			objectFaker.addFaker(field, fakerRegistry.get(clazz));
			return fakerRegistry.get(clazz).fake();
		} else {
			Object subObj = doFake(clazz);
			objectFaker.addFaker(field, fakerRegistry.get(clazz));
			return subObj;
		}
	}
	
	private static <T> ObjectFaker<T> createObjectFaker(Class<T> clazz) {
		return new ObjectFaker<T>(clazz);
	}

	public static FakerRegistry getFakerRegistry() {
		return fakerRegistry;
	}

	public static void setFakerRegistry(FakerRegistry fakerRegistry) {
		FakerUtil.fakerRegistry = fakerRegistry;
	}
	
}
