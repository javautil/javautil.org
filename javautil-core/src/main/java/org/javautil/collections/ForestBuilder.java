package org.javautil.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds a forest from a list of nodes. The generic type T is used to allow for
 * compile time checking of the type of the tree node contained inside the tree.
 * The ModifiableTreeNode type is required to call setChildren when building the
 * tree.
 * 
 * @author bcm
 * 
 * @param <T>
 *            the type of TreeNode
 */
public class ForestBuilder<T extends ModifiableTreeNode<T>> {

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
	 * @return top level nodes in the forest
	 */
	public List<T> buildForest(final List<T> treeNodes) {
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
		if (roots == null || roots.size() == 0) {
			throw new IllegalStateException(
					"no top level nodes for treeNodes list");
		}
		final List<T> todo = new ArrayList<T>(roots);
		ModifiableTreeNode<T> current = todo.remove(0);
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
			final List<Integer> nodes = new ArrayList<Integer>();
			final Collection<List<T>> nodez = nodesByParentNodeNbr.values();
			for (final List<T> n : nodez) {
				for (final T node : n) {
					nodes.add(node.getNodeNbr());
				}
			}
			throw new IllegalStateException("one or more treeNodes "
					+ "have an invalid parent node: "
					+ Arrays.toString(nodes.toArray()));
		}
		return roots;
	}

}
