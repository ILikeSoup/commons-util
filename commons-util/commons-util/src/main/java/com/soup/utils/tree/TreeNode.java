package com.soup.utils.tree;

import java.util.List;

public interface TreeNode<T> {
	
	List<T> getChildren();
	
	void setChildren(List<T> list);
	
	String getParentNodeId();
	
	String getCurrentNodeId();
	
}
