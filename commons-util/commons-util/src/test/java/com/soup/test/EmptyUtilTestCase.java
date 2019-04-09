package com.soup.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.soup.utils.empty.EmptyUtil;

public class EmptyUtilTestCase {
	
	@Test
	public void testIsEmptyString() {
		String str1 = "";
		String str2 = "a";
		String str3 = " ";
		String str4 = null;
		Assert.assertTrue(EmptyUtil.isEmpty(str1));
		Assert.assertFalse(EmptyUtil.isEmpty(str2));
		Assert.assertFalse(EmptyUtil.isEmptyTrim(str3, false));
		Assert.assertTrue(EmptyUtil.isEmpty(str3));
		Assert.assertTrue(EmptyUtil.isEmpty(str4));
	}
	
	@Test
	public void testIsEmptyMap() {
		Map<String, String> map = new HashMap<>();
		Assert.assertTrue(EmptyUtil.isEmpty(map));
		map.put("test", "test");
		Assert.assertFalse(EmptyUtil.isEmpty(map));
		map = null;
		Assert.assertTrue(EmptyUtil.isEmpty(map));
	}
	
	@Test
	public void testIsEmptyArray() {
		String[] array = new String[] {};
		Assert.assertTrue(EmptyUtil.isEmpty(array));
		array = new String[] {"1", "2"};
		Assert.assertFalse(EmptyUtil.isEmpty(array));
		array = null;
		Assert.assertTrue(EmptyUtil.isEmpty(array));
	}
	
	@Test
	public void testIsEmptyList() {
		List<String> list = new ArrayList<>();
		Assert.assertTrue(EmptyUtil.isEmpty(list));
		list.add("3");
		Assert.assertFalse(EmptyUtil.isEmpty(list));
		list = null;
		Assert.assertTrue(EmptyUtil.isEmpty(list));
	}
	
	@Test
	public void testIsEmptyEmptiable() {
		Aa a = new Aa();
		Assert.assertTrue(EmptyUtil.isEmpty(() -> (a.id == null || a.name == null)));
		Assert.assertTrue(EmptyUtil.isEmpty(() -> (2 > 1)));
	}
	
	@Test
	public void testIsEmptyOneEach() {
		Assert.assertTrue(EmptyUtil.isEmptyOne("", "a", "b"));
		Assert.assertFalse(EmptyUtil.isEmptyEach("", null, "b"));
		List<String> list = new ArrayList<>();
		Assert.assertTrue(EmptyUtil.isEmptyOne(list));
		list.add("");
		list.add(" ");
		list.add(null);
		Assert.assertTrue(EmptyUtil.isEmptyEach(list));
		list = null;
		Assert.assertTrue(EmptyUtil.isEmptyOne(list) && EmptyUtil.isEmptyEach(list));
	}
	
	@Test
	public void testFindEmptyOne() {
		Assert.assertEquals(" ", EmptyUtil.findEmptyOne("a", " ", null));
	}
	
}

class Aa {
	String id;
	String name;
	
}
