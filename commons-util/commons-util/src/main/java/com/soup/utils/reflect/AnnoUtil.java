package com.soup.utils.reflect;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

import com.soup.utils.empty.EmptyUtil;

/**
 * 注解工具类
 * @author	soup
 * @date	2018年3月27日上午11:21:29
 */
public class AnnoUtil {

	/**
	 * 判断 类/方法/域/构造器/参数 上是否有注解anno
	 * @param obj 类/方法/域/构造器/参数
	 * @param anno 注解Class
	 * @return
	 */
	public static boolean hasAnnotation(AnnotatedElement annotatedElement, Class<? extends Annotation> anno) {
		if(null == annotatedElement) {
			return false;
		}
		return annotatedElement.getAnnotation(anno) != null;
	}

	/**
	 * 复合注解：A注解修饰B注解，使得B注解拥有和A注解的功能。
	 * 有了这个方法注解就可以实现"继承"了
	 * 判断 类/方法/域/构造器/参数 上是否有注解anno， 或者被anno修饰的注解(会一直向上递归查找)
	 * @param obj
	 * @param anno
	 * @return
	 */
	public static boolean hasCompoundAnno(AnnotatedElement annotatedElement, Class<? extends Annotation> anno) {
		return hasCompoundAnno(annotatedElement.getAnnotations(), anno, new ArrayList<>());
	}
	
	/**
	 * @param annotatedElement
	 * @param anno
	 * @param except 排除已经解析的注解，防止相互引用导致的堆栈溢出
	 * @return
	 */
	private static boolean hasCompoundAnno(AnnotatedElement annotatedElement, Class<? extends Annotation> anno, List<Annotation> except) {
		if(null == annotatedElement) {
			return false;
		}
		return hasCompoundAnno(annotatedElement.getAnnotations(), anno, except);
	}
	
	/**
	 * 判断annos注解数组中是否有被anno修饰的注解
	 * @param annos
	 * @param anno
	 * @param except 
	 * @return
	 */
	private static boolean hasCompoundAnno(Annotation[] annos, Class<? extends Annotation> anno, List<Annotation> except) {
		if(EmptyUtil.isEmpty(annos)) {
			return false;
		}
		for(Annotation one : annos) {
			// 跳过元注解和已解析的注解
			if(isMetaAnno(one.annotationType()) || except.contains(one)) {
				continue;
			}
			except.add(one);
			if(one.annotationType() == anno || hasCompoundAnno(one.annotationType(), anno, except)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 元注解判断
	 * @param anno
	 * @return
	 */
	private static boolean isMetaAnno(Class<? extends Annotation> anno) {
		return anno == Retention.class || anno == Target.class 
				|| anno == Documented.class || anno == Inherited.class;
	}


}
