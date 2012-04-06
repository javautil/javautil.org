package org.javautil.collections;

/**
 * Represents a DTO that is a node in a tree. This node has a single parent, any
 * number of children, and a human-readable description.
 * 
 * The use of "T extends TreeNode<T>" on this class implies that the child nodes
 * from getChildren() and setChildren() are assignable from the same generic
 * type (T) as the parent.
 * 
 * TODO jjs But there are no getChildren and setChildren methods.
 * 
 * @author bcm
 * 
 * @param <T>
 */
public interface TreeNode<T extends TreeNode<T>> {

	/**
	 * The parent node number of this node.
	 * 
	 * @return parentNodeNbr
	 */
	public Integer getParentNodeNbr();

	/**
	 * The unique node number of this node in the tree.
	 * 
	 * @return nodeNbr
	 */
	public Integer getNodeNbr();

	/**
	 * A human-readable description for this node.
	 * 
	 * @return description
	 */
	public String getDescription();

	/**
	 * A short toString
	 */
	public String format();
}
