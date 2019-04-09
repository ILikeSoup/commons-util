package com.soup.test;

import java.util.Date;

import org.junit.Test;

import com.soup.faker.FakerUtil;
import com.soup.test.model.FakeTarget;

public class FakerUtilTestCase {
	
	@Test
	public void originalTest() {
		FakerUtil.put(Date.class, () -> new Date());
		FakerUtil.put(int.class, () -> 3);
		FakeTarget fake = FakerUtil.fake(FakeTarget.class);
		System.out.println(fake);
	}
	
	@Test
	public void advancedTest() {
		FakeTarget fake = FakerUtil.fake(FakeTarget.class);
		System.out.println(fake);
		FakeTarget fake2 = FakerUtil.fake(FakeTarget.class);
		System.out.println(fake2);
	}
	
}

