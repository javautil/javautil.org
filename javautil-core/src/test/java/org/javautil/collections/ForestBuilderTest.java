package org.javautil.collections;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ForestBuilderTest {

	private final List<TreeNodeImpl> testData = new ArrayList<TreeNodeImpl>();

	private TreeNodeImpl root1;

	private TreeNodeImpl root2;

	private TreeNodeImpl root3;

	@Before
	public void buildTestData() {
		root1 = new TreeNodeImpl(1, null, "Root Node 1");
		testData.add(root1);

		testData.add(new TreeNodeImpl(2, 1, "Child Node 2, 2nd Gen, Leaf"));
		testData.add(new TreeNodeImpl(3, 1, "Child Node 3, 2nd Gen, Branch"));
		testData.add(new TreeNodeImpl(4, 1, "Child Node 4, 2nd Gen, Branch"));
		testData.add(new TreeNodeImpl(5, 3, "Child Node 5, 3rd Gen, Leaf"));
		testData.add(new TreeNodeImpl(6, 3, "Child Node 6, 3rd Gen, Leaf"));
		testData.add(new TreeNodeImpl(7, 3, "Child Node 7, 3rd Gen, Leaf"));
		testData.add(new TreeNodeImpl(8, 4, "Child Node 8, 3rd Gen, Leaf"));
		testData.add(new TreeNodeImpl(9, 4, "Child Node 9, 3rd Gen, Leaf"));
		testData.add(new TreeNodeImpl(10, 4, "Child Node 10, 3rd Gen, Branch"));
		testData.add(new TreeNodeImpl(11, 10, "Child Node 11, 4th Gen, Leaf"));
		testData.add(new TreeNodeImpl(12, 10, "Child Node 12, 4th Gen, Leaf"));
		testData.add(new TreeNodeImpl(13, 10, "Child Node 13, 4th Gen, Leaf"));

		root2 = new TreeNodeImpl(14, null, "Root Node 2");
		testData.add(root2);

		root3 = new TreeNodeImpl(14, null, "Root Node 3");
		testData.add(root3);
	}

	@Test
	public void testBuildTree() {
		final ForestBuilder<TreeNodeImpl> forestBuilder = new ForestBuilder<TreeNodeImpl>();
		final List<TreeNodeImpl> actual = forestBuilder.buildForest(testData);
		Assert.assertEquals(root1, actual.get(0));
		Assert.assertEquals(root2, actual.get(1));
		Assert.assertEquals(root3, actual.get(2));
		Assert.assertEquals(3, root1.getChildren().size());
		Assert.assertEquals(0, root1.getChildren().get(0).getChildren().size());
		Assert.assertEquals(3, root1.getChildren().get(1).getChildren().size());
		Assert.assertEquals(3, root1.getChildren().get(2).getChildren().size());
		Assert.assertEquals(0, root1.getChildren().get(2).getChildren().get(0)
				.getChildren().size());
		Assert.assertEquals(0, root1.getChildren().get(2).getChildren().get(1)
				.getChildren().size());
		Assert.assertEquals(3, root1.getChildren().get(2).getChildren().get(2)
				.getChildren().size());
		Assert.assertEquals(0, root1.getChildren().get(2).getChildren().get(2)
				.getChildren().get(0).getChildren().size());
		Assert.assertEquals(0, root1.getChildren().get(2).getChildren().get(2)
				.getChildren().get(1).getChildren().size());
		Assert.assertEquals(0, root1.getChildren().get(2).getChildren().get(2)
				.getChildren().get(2).getChildren().size());
	}

}
