package org.javautil.collections;

import java.util.List;

/**
 * Interface to be implemented by TreeNode classes that should be built by the
 * TreeNodeBuilder class.
 * 
 * @author bcm
 * 
 * @param <T>
 */
public interface ModifiableTreeNode<T extends ModifiableTreeNode<T>> extends
		TreeNode<T> {

	/**
	 * Assigns the children that have a parent node number equals to this node's
	 * node number.
	 * 
	 * @param children
	 */
	public void setChildren(List<T> children);

	/**
	 * Returns the nodes that have a parent node equal to the id of this node.
	 * 
	 * @param children
	 */
	public List<T> getChildren();

}
