package org.javautil.collections;

import java.util.ArrayList;

public class SimpleTreeNodeList extends ArrayList<SimpleTreeNode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("boxing")
	public SimpleTreeNodeList() {
		add(new SimpleTreeNode(1, null, "root"));
		add(new SimpleTreeNode(2, 1, "two"));
		add(new SimpleTreeNode(3, 2, "three"));
		add(new SimpleTreeNode(4, 2, "four"));
	}

}
