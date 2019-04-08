package com.soup.utils.empty;
/**
 * 实现该接口的类，标识为可以为空的
 * @author soup
 * @date 2018年6月26日 下午5:21:34 
 */
@FunctionalInterface
public interface Emptiable {
	/**
	 * 当返回值为true时，表示该对象是空的
	 * @return
	 */
	boolean isEmpty();
	
}
