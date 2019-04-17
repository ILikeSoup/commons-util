package com.soup.faker;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soup.utils.reflect.FieldUtil;

public class ObjectFaker<T> implements Faker<T> {
	
	private Class<T> clazz;

	private Map<Field, Faker<?>> fakers = new HashMap<>();
	
	public ObjectFaker(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public T fake() {
		T obj = null;
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// ignore
		}
		List<Field> fields = FieldUtil.getDeclaredFields(clazz);
		for(int i = 0; i < fakers.size(); i++) {
			Field one = fields.get(i);
			FieldUtil.setFieldValue(obj, one, fakers.get(one).fake());
		}
		return obj;
	}
	
	public void addFaker(Field field, Faker<?> faker) {
		fakers.put(field, faker);
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public Map<Field, Faker<?>> getFakers() {
		return fakers;
	}

	public void setFakers(Map<Field, Faker<?>> fakers) {
		this.fakers = fakers;
	}
	
}
