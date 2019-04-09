package com.soup.utils.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.soup.utils.StringUtil;
import com.soup.utils.empty.EmptyUtil;

/**
 * 构建树的工具类，另包含自定义排序的方法
 * @author soup
 * @date 2018年6月26日 下午5:50:17 
 * @see OrderableTreeNode
 * @see Orderable
 * @see TreeNode
 */
public class TreeUtil {
	
	/**
	 * 递归将OrderableTreeNode集合转换为数结构
	 * @param nodeList
	 * @param rootParentId 指定根节点的parentId
	 * @return
	 * @see OrderableTreeNode
	 * @see Orderable
	 * @see TreeNode
	 */
	public static <T extends OrderableTreeNode<T, E>, E> List<T> orderTree(Collection<T> nodeList, String rootParentId) {
		List<T> list = new ArrayList<T>();
		for(T node : nodeList) {
			if(StringUtil.equals(rootParentId, node.getParentNodeId())) {
				list.add(findChildren(node, nodeList));
			}
		}
		orderNodeTree(list);
		return list;
	}
	
	/**
	 * 递归将OrderableTreeNode集合转换为数结构
	 * @param nodeList
	 * @return
	 * @see OrderableTreeNode
	 * @see Orderable
	 * @see TreeNode
	 */
	public static <T extends OrderableTreeNode<T, E>, E> List<T> orderTree(Collection<T> nodeList) {
		return orderTree(nodeList, null);
	}
	
	/**
	 * 将集合变为树结构
	 * @param nodeList
	 * @return
	 * @see TreeNode
	 */
	public static <T extends TreeNode<T>> List<T> tree(Collection<T> nodeList) {
		List<T> list = new ArrayList<T>();
		for(T node : nodeList) {
			if(node.getParentNodeId() == null) {
				list.add(findChildren(node, nodeList));
			}
		}
		return list;
	}
	
	/**
	 * 递归获取一个TreeNode下的所有子(孙)元素的树
	 * @param parent
	 * @param nodeList
	 * @return
	 * @see TreeNode
	 */
	public static <T extends TreeNode<T>> T findChildren(T parent, Collection<T> nodeList) {
		for(T node : nodeList) {
			if(parent.getCurrentNodeId().equals(node.getParentNodeId())) {
				if(null == parent.getChildren()) {
					parent.setChildren(new ArrayList<T>());
				}
				parent.getChildren().add(findChildren(node, nodeList));
			}
		}
		return parent;
	}
	
	/**
	 * 将OrderableTreeNode树结构排序
	 * @param nodeList
	 * @see OrderableTreeNode
	 * @see Orderable
	 * @see TreeNode
	 */
	private static <T extends OrderableTreeNode<T, E>, E> void orderNodeTree(List<T> nodeList) {
		orderList(nodeList);
		for(T node : nodeList) {
			if(! EmptyUtil.isEmpty(node.getChildren())) {
				orderNodeTree(node.getChildren());
			}
		}
	}
	
	/**
	 * 对Orderable集合进行排序
	 * @param list
	 * @see Orderable
	 */
	public static <E> void orderList(List<? extends Orderable<E>> list) {
		if(EmptyUtil.isEmpty(list)) {
			return;
		}
		Collections.sort(list, new Comparator<Orderable<E>>() {
			@SuppressWarnings("unchecked")
			@Override
			public int compare(Orderable<E> o1, Orderable<E> o2) {
				return o1.getOrderNum().compareTo((E) o2.getOrderNum());
			}
		});
	}
	
	/**
	 * 由于前端的树结构常常是有当父节点被选中，所有的子节点都会被选中的特性
	 * 对于资源等只要 拥有访问一个子节点的权限，便拥有了父节点的权限 的 这类树的反显
	 * 常常会因为上述原因而导致反显出错（即因父节点被选中，导致所有子节点被选中的错误）
	 * 
	 * 本方法的作用是，递归遍历parent下的所有子节点，使所有子节点满足如下条件：
	 * 	每个节点下只有有一个子节点没被选中[isChecked() == false]，该节点就是未选中状态
	 * @param parent是树的根节点
	 */
	public static <T extends CheckableTreeNode<T>> void recheck(T parent) {
		if(EmptyUtil.isEmpty(parent.getChildren())) {
			return;
		}
		for(T child : parent.getChildren()) {
			recheck(child);
			if(!child.isChecked()) {
				parent.setChecked(false);
			}
		}
	}
	
}
