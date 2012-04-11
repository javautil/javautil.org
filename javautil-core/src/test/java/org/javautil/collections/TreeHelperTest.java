package org.javautil.collections;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TreeHelperTest {
	private static Logger logger = Logger.getLogger(TreeHelperTest.class);

	@SuppressWarnings("boxing")
	@Test
	public void testGetParents() {
		@SuppressWarnings({ "boxing", "serial" })
		class SimpleTreeNodeList extends ArrayList<SimpleTreeNode> {
			{
				add(new SimpleTreeNode(1, null, "root"));
				add(new SimpleTreeNode(2, 1, "two"));
				add(new SimpleTreeNode(3, 2, "three"));
				add(new SimpleTreeNode(4, 2, "four"));
			}
		}
		final TreeHelper<SimpleTreeNode> th = new TreeHelper<SimpleTreeNode>(
				new SimpleTreeNodeList());
		final List<SimpleTreeNode> lineage = th.getParents(4);
		assertEquals(3, lineage.size());
		assertEquals(new Integer(1), lineage.get(0).getNodeNbr());
		assertEquals(new Integer(2), lineage.get(1).getNodeNbr());
		assertEquals(new Integer(4), lineage.get(2).getNodeNbr());
	}

	@Test
	public void testGetLeafNodes() {
		@SuppressWarnings({ "boxing", "serial" })
		class SimpleTreeNodeList extends ArrayList<SimpleTreeNode> {
			{
				add(new SimpleTreeNode(1, null, "root"));
				add(new SimpleTreeNode(2, 1, "two"));
				add(new SimpleTreeNode(3, 2, "three"));
				add(new SimpleTreeNode(4, 2, "four"));
				add(new SimpleTreeNode(5, 1, "five"));
			}
		}
		final TreeHelper<SimpleTreeNode> th = new TreeHelper<SimpleTreeNode>(
				new SimpleTreeNodeList());
		final List<SimpleTreeNode> leafNodes = th.getLeafNodes();
		assertEquals(3, leafNodes.size());
		assertEquals(new Integer(3), leafNodes.get(0).getNodeNbr());
		assertEquals(new Integer(4), leafNodes.get(1).getNodeNbr());
		assertEquals(new Integer(5), leafNodes.get(2).getNodeNbr());
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testConstructorWithDisjointTreeNodes() {
		@SuppressWarnings({ "boxing", "serial" })
		class SimpleTreeNodeList extends ArrayList<SimpleTreeNode> {
			{
				add(new SimpleTreeNode(1, null, "root"));
				add(new SimpleTreeNode(2, 5, "two"));
			}
		}
		new TreeHelper<SimpleTreeNode>(new SimpleTreeNodeList());

	}
}
