package com.soup.faker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.soup.utils.reflect.FieldUtil;

public class ObjectFaker<T> implements Faker<T> {
	
	private Class<T> clazz;
	
	public ObjectFaker(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	private List<Faker<?>> fakers = new ArrayList<>();
	
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
			FieldUtil.setFieldValue(obj, fields.get(i), fakers.get(i).fake());
		}
		return obj;
	}
	
	public void addFaker(Faker<?> faker) {
		fakers.add(faker);
	}
	
}
