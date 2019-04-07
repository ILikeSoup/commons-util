package com.soup.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.soup.utils.empty.EmptyUtil;

/**
 * <p>类名称：ListFilter</p>
 * <p>类描述：集合筛选器</p>
 * <p>公司：深圳市雁联计算系统有限公司</p>
 * @author	GaoXiang
 * @date	2018年1月12日 上午10:06:23 
 */
public interface ListFilter<T> {
	
	boolean filter(T obj);
	
	/**
	 * 通过筛选器筛选出所有满足条件的元素
	 * @param all
	 * @param filter
	 * @return
	 */
	public static <T> List<T> findEach(Collection<T> all, ListFilter<T> filter) {
		List<T> result = new ArrayList<T>();
		if(EmptyUtil.isEmpty(all)) {
			return result;
		}
		for(T o : all) {
			if(filter.filter(o)) {
				result.add(o);
			}
		}
		return result;
	}
	
	/**
	 * 通过筛选器筛选出第一个满足条件的元素
	 * @param all
	 * @param filter
	 * @return
	 */
	public static <T> T findFirst(Collection<T> all, ListFilter<T> filter) {
		if(EmptyUtil.isEmpty(all)) {
			return null;
		}
		for(T o : all) {
			if(filter.filter(o)) {
				return o;
			}
		}
		return null;
	}
	
}
