package org.javautil.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SqlStatementHelperTest {
	private SqlStatementHelper stripper;
	private final Logger logger = Logger.getLogger(getClass());

	void testSqlText(String original, String expectedNoComment,
			String expectedConverted) {
		stripper = new SqlStatementHelper(original);
		stripper.process();
		logger.info("original:          " + original);
		logger.info("expectedNoComment: " + expectedNoComment);
		logger.info("actualNoComment:   " + stripper.getNoComments());
		logger.info("expectedConverted  " + expectedConverted);
		logger.info("actualConverted:   "
				+ stripper.getConvertedBindStatement());
		assertEquals("noComment", expectedNoComment, stripper.getNoComments());
		assertEquals("converted", expectedConverted,
				stripper.getConvertedBindStatement());
	}

	@Test
	public void testLineComment() {
		String text = "select 'x' from dual -- toast";
		String expectedNoComment = "select 'x' from dual ";

		testSqlText(text, expectedNoComment, text);
		// assertEquals(expected,stripper.getNoComments());
	}

	@Test
	public void testComment() {
		String text = "select 'x' from /* toast */ dual";
		String expected = "select 'x' from  dual";
		testSqlText(text, expected, text);

		assertEquals(expected, stripper.getNoComments());
	}

	@Test
	public void testCommentedBind() {
		String text = "select 'x' from /* :toast */ dual";
		String expectedNoComment = "select 'x' from  dual";
		testSqlText(text, expectedNoComment, text);
		ArrayList<SqlBindParameter> binds = stripper.getBindParameters();
		assertEquals(0, binds.size());
	}

	@Test
	public void testCommentedBindWithAdditionalBind() {
		String text = "select 'x' from /* :toast */ dual where :one = 1 -- and :two = 2";
		String expectedNoComment = "select 'x' from  dual where :one = 1 ";
		String expectedConvertedBind = "select 'x' from /* :toast */ dual where ? = 1 -- and :two = 2";
		testSqlText(text, expectedNoComment, expectedConvertedBind);
		ArrayList<SqlBindParameter> binds = stripper.getBindParameters();
		assertEquals(1, binds.size());
	}

	@Test
	public void testBind() {
		String text = "select 'x' from  dual where type = :toast";
		String expectedConverted = "select 'x' from  dual where type = ?";
		testSqlText(text, text, expectedConverted);
		ArrayList<SqlBindParameter> binds = stripper.getBindParameters();
		assertEquals(1, binds.size());
		for (SqlBindParameter bind : binds) {
			logger.info("found bind '" + bind.getBindName() + "'");
		}
		assertTrue(binds.get(0).getBindName().equals("toast"));
	}

	@Test
	public void testLineCommentedBind() {
		String text = "select 'x' from  dual -- where type = :toast";
		String noComment = "select 'x' from  dual ";
		String converted = text;
		testSqlText(text, noComment, converted);
		ArrayList<SqlBindParameter> binds = stripper.getBindParameters();
		assertEquals(0, binds.size());
	}
}
