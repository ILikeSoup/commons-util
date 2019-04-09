package com.soup.test;


import org.junit.Test;

import com.soup.utils.StringUtil;
import com.soup.utils.reflect.FieldUtil;

import static com.soup.utils.print.PrintUtil.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class FieldUtilTestCase {
	
	/**
	 * JDK class类的方法
	 * getDeclaredFields() 获取该类中声明的所有属性，包含所有修饰符
	 */
	@Test
	public void testJDKGetDeclaredFields() {
		printList("JDKGetDeclaredFields", C.class.getDeclaredFields(), one -> one.getName());
	}
	
	/**
	 * JDK class类的方法
	 * getFields() 获取该类及其父类声明的所有被public修饰的属性，顺序为先子后父
	 */
	@Test
	public void testJDKGetFields() {
		printList("JDKGetFields", C.class.getFields(), one -> one.getName());
	}
	
	/**
	 * FieldUtil类中方法
	 * getDeclaredFields() 获取该类及其父类声明的所有属性，顺序为先子后父
	 */
	@Test
	public void testUtilGetDeclaredFields() {
		printList("UtilGetDeclaredFields", FieldUtil.getDeclaredFields(C.class), one -> one.getName());
	}
	
	/**
	 * FieldUtil类中方法
	 * getField() 基于getDeclaredFields() 通过属性名获取指定属性，优先级先子后父
	 */
	@Test
	public void testUtilGetField() {
		print("UtilGetDeclaredFields", FieldUtil.getField(C.class, "same"), one -> one.toString());
	}
	
	/**
	 * FieldUtil类中方法
	 * getFields() 基于getDeclaredFields() 通过属性名获取指定属性，优先级先子后父
	 */
	@Test
	public void testUtilGetFields() {
		printList("UtilGetDeclaredFields", FieldUtil.getFields(C.class, "same", "same"), one -> one.toString());
	}
	
	/**
	 * FieldUtil类中方法
	 * getFieldsWithAnno() 基于getDeclaredFields() 获取被注解修饰的属性，顺序先子后父
	 */
	@Test
	public void testUtilGetFieldsWithAnno() {
		printList("UtilGetFieldsWithAnno", FieldUtil.getFieldsWithAnno(C.class, E.class), one -> one.toString());
	}

	/**
	 * FieldUtil类中方法
	 * getFieldValue() 基于getDeclaredFields() 获取属性，直接通过反射获取属性值
	 * getFieldValueByGetter() 基于getDeclaredFields() 获取属性，通过反射调用getter方法获取属性值
	 */
	@Test
	public void testUtilGetFieldValue() {
		C c = new C();
		c.c_def = 2;
		print("UtilGetFieldValue", FieldUtil.getFieldValue(c, "c_def"), one -> one.toString());
		print("UtilGetFieldValueByGetter", FieldUtil.getFieldValueByGetter(c, "c_def"), one -> one.toString());
		
		c.setA_pri(4);
		print("UtilGetFieldValue", FieldUtil.getFieldValue(c, "a_pri"), one -> one.toString());
		print("UtilGetFieldValueByGetter", FieldUtil.getFieldValueByGetter(c, "a_pri"), one -> one.toString());
	}
	
	/**
	 * FieldUtil类中方法
	 * setFieldValue() 基于getDeclaredFields() 获取属性，直接通过反射设置属性值
	 * setFieldValueBySetter() 基于getDeclaredFields() 获取属性，直接通过反射设置属性值
	 */
	@Test
	public void testUtilSetFieldValue() {
		C c = new C();
		FieldUtil.setFieldValue(c, "c_def", 3);
		print("UtilSetFieldValue", StringUtil.toString(c.c_def));
		FieldUtil.setFieldValueBySetter(c, "c_def", 4);
		print("UtilSetFieldValueBySetter", StringUtil.toString(c.c_def));
		
		FieldUtil.setFieldValue(c, "a_pri", 5);
		print("UtilSetFieldValue", StringUtil.toString(c.getA_pri()));
		FieldUtil.setFieldValueBySetter(c, "a_pri", 6);
		print("UtilSetFieldValueBySetter", StringUtil.toString(c.getA_pri()));
	}
	
}
@SuppressWarnings("unused")
class A {
	public int a_pub;
	protected int a_pro;
	int a_def;
	private int a_pri;
	@E
	public static int a_sPub;
	private static int a_sPri;
	public String same;
	public int getA_pri() {
		return a_pri;
	}
	public void setA_pri(int a_pri) {
		this.a_pri = a_pri;
	}
}
@SuppressWarnings("unused")
class B extends A {
	@E
	public int b_pub;
	protected int b_pro;
	int b_def;
	private D b_pri;
	public String same;
}
@SuppressWarnings("unused")
class C extends B {
	@E
	public int c_pub;
	protected int c_pro;
	int c_def;
	private int c_pri;
	static int c_sDef;
	public static int c_sPub;
	public String same;
	
	public int getC_def() {
		return c_def;
	}
	public void setC_def(int c_def) {
		this.c_def = c_def;
	}
	
}

@SuppressWarnings("unused")
class D {
	private int d1;
	private int d2;
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface E {
	
}