package com.soup.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.Assert;
import org.junit.Test;

import com.soup.utils.reflect.AnnoUtil;

public class AnnoUtilTestCase {
	
	@Test
	public void testHasAnnotation() {
		Assert.assertTrue(AnnoUtil.hasAnnotation(Bb.class, Anno.class));
		Assert.assertFalse(AnnoUtil.hasAnnotation(Cc.class, Anno.class));
	}
	
	/**
	 * AnnoUtil类中的方法
	 * hasCompoundAnno 复合注解的判断，类似于注解继承
	 * @CompoundAnno 注解类上存在注解 @Anno，那么被 @CompoundAnno 修饰的类同时也可以视作被 @Anno 修饰
	 */
	@Test
	public void testHasCompoundAnno() {
		Assert.assertTrue(AnnoUtil.hasCompoundAnno(Bb.class, Anno.class));
		Assert.assertTrue(AnnoUtil.hasCompoundAnno(Cc.class, Anno.class));
	}
	
	@Test
	public void test() {
		Assert.assertFalse(AnnoUtil.hasCompoundAnno(Dd.class, Anno.class));
	}
	
}

@Anno
class Bb {
	
}

@CompoundAnno
class Cc {
	
}

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@interface Anno {
	
}

@Anno
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@interface CompoundAnno {
	
}

@AnotherAnno
class Dd {
	
}

@AnotherAnno
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@interface OtherAnno {
	
}

@OtherAnno
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@interface AnotherAnno {
	
}
