package org.javautil.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic implementation of ModifiableTreeNode as a simple bean.
 * 
 * @author bcm
 * 
 * @param <T>
 */
public class TreeNodeImpl implements ModifiableTreeNode<TreeNodeImpl> {

	private Integer nodeNbr;

	private Integer parentNodeNbr;

	private String description;

	private List<TreeNodeImpl> children = new ArrayList<TreeNodeImpl>();

	/**
	 * Javabeans constructor.
	 */
	public TreeNodeImpl() {
	}

	/**
	 * Constructor for creating an unlinked node (no children set).
	 * 
	 * @param nodeNbr
	 * @param parentNodeNbr
	 * @param description
	 */
	public TreeNodeImpl(final Integer nodeNbr, final Integer parentNodeNbr,
			final String description) {
		this.nodeNbr = nodeNbr;
		this.parentNodeNbr = parentNodeNbr;
		this.description = description;
	}

	@Override
	public Integer getNodeNbr() {
		return nodeNbr;
	}

	public void setNodeNbr(final Integer nodeNbr) {
		this.nodeNbr = nodeNbr;
	}

	@Override
	public Integer getParentNodeNbr() {
		return parentNodeNbr;
	}

	public void setParentNodeNbr(final Integer parentNodeNbr) {
		this.parentNodeNbr = parentNodeNbr;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public List<TreeNodeImpl> getChildren() {
		return children;
	}

	@Override
	public void setChildren(final List<TreeNodeImpl> children) {
		this.children = children;
	}

	@Override
	public String format() {
		return TreeNodeFormatter.format(this);
	}
}
