package org.javautil.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.javautil.lang.SystemProperties;

public class TreeHelper<T extends TreeNode<?>> {
	private final Logger logger = Logger.getLogger(getClass());

	private final HashMap<Integer, T> nodeMapById = new HashMap<Integer, T>();

	public TreeHelper(final Collection<T> nodes) {
		for (final T node : nodes) {
			final T existing = nodeMapById.put(node.getNodeNbr(), node);
			if (existing != null) {
				throw new IllegalArgumentException("duplicate node ID "
						+ existing.getNodeNbr());
			}
		}

		for (final T node : nodeMapById.values()) {
			if (node.getParentNodeNbr() != null) {
				if (nodeMapById.get(node.getParentNodeNbr()) == null) {
					throw new IllegalArgumentException("parent node number "
							+ node.getParentNodeNbr() + " not found for node "
							+ node.getNodeNbr());
				}
			}
		}
	}

	public T getNode(final Integer nodeNbr) {
		return nodeMapById.get(nodeNbr);
	}

	/**
	 * Returns leaf nodes from the tree node collection. (Leaf nodes are nodes
	 * that have no children).
	 * 
	 * @return
	 */
	public List<T> getLeafNodes() {
		final List<T> todo = new ArrayList<T>();
		todo.addAll(nodeMapById.values());
		final List<T> leafNodes = new ArrayList<T>();
		while (todo.size() > 0) {
			final T node = todo.remove(0);
			boolean isLeaf = true;
			for (final T anotherNode : nodeMapById.values()) {
				if (anotherNode.getParentNodeNbr() != null
						&& node.getNodeNbr() != null
						&& anotherNode != node
						&& anotherNode.getParentNodeNbr().equals(
								node.getNodeNbr())) {
					isLeaf = false;
				}
			}
			if (isLeaf) {
				leafNodes.add(node);
			}
		}
		return leafNodes;
	}

	public List<T> getParents(final Number nodeNbr) {
		final Stack<T> lineage = new Stack<T>();
		T node = nodeMapById.get(nodeNbr);
		if (node == null) {
			throw new IllegalArgumentException("no such node " + nodeNbr);
		}

		while (node != null) {
			logger.debug("adding " + node);
			lineage.push(node);
			node = nodeMapById.get(node.getParentNodeNbr());
		}

		final ArrayList<T> retval = new ArrayList<T>();
		for (int i = lineage.size(); i > 0; i--) {
			retval.add(lineage.pop());
		}

		return retval;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (final Entry<Integer, T> entry : nodeMapById.entrySet()) {
			sb.append("id: ");
			sb.append(entry.getKey());
			sb.append(" ");
			sb.append(entry.getValue());
			sb.append(SystemProperties.newline);
		}
		return sb.toString();
	}
}
