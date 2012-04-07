package org.javautil.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds a tree from a list of nodes. The generic type T is used to allow for
 * compile time checking of the type of the tree node contained inside the tree.
 * The ModifiableTreeNode type is required to call setChildren when building the
 * tree.
 * 
 * @author bcm
 * 
 * @param <T>
 *            the type of TreeNode
 */
public class TreeBuilder<T extends ModifiableTreeNode<T>> {

	/**
	 * Calls setChildren on the treeNodes, returning the root of the tree. The
	 * list of treeNodes must have the nodeNbr and parentNodeNbr properties
	 * correctly set. Any value set on the children property will be lost.
	 * 
	 * @throws IllegalStateException
	 *             if no root node is found, if more than one root is found, or
	 *             if some nodes in the tree were not linked.
	 * 
	 * @param treeNodes
	 * @return root of tree
	 */
	public ModifiableTreeNode<T> buildTree(final List<T> treeNodes) {
		final Map<Integer, List<T>> nodesByParentNodeNbr = new HashMap<Integer, List<T>>();
		for (final T treeNode : treeNodes) {
			List<T> list = nodesByParentNodeNbr
					.get(treeNode.getParentNodeNbr());
			if (list == null) {
				list = new ArrayList<T>();
				nodesByParentNodeNbr.put(treeNode.getParentNodeNbr(), list);
			}
			list.add(treeNode);
		}
		final List<T> roots = nodesByParentNodeNbr.remove(null);
		if (roots == null) {
			throw new IllegalStateException("no root node for treeNodes list");
		}
		if (roots.size() > 1) {
			throw new IllegalStateException("more than one root node was "
					+ "found in the treeNodes list");
		}
		ModifiableTreeNode<T> current = roots.get(0);
		final List<ModifiableTreeNode<T>> todo = new ArrayList<ModifiableTreeNode<T>>();
		while (current != null) {
			final List<T> list = nodesByParentNodeNbr.remove(current
					.getNodeNbr());
			current.setChildren(list == null ? new ArrayList<T>() : list);
			if (list != null) {
				todo.addAll(list);
			}
			current = null;
			if (todo.size() > 0) {
				current = todo.remove(0);
			}
		}
		if (nodesByParentNodeNbr.size() > 0) {
			throw new IllegalStateException("some nodes in the treeNodes "
					+ "list were not linked or have an invalid parent");
		}
		return roots.get(0);
	}

}
