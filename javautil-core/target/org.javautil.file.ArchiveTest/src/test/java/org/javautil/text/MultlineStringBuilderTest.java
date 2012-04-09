package org.javautil.text;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MultlineStringBuilderTest {

	@Test
	public void test1() {
		final MultiLineStringBuilder msb = new MultiLineStringBuilder();
		msb.setIndentPad(" ");
		msb.appendLine("a");
		msb.indent();
		msb.appendLine("b");
		final String s = msb.toString();
		final String[] lines = s.split(msb.getNewline());
		assertEquals(2, lines.length);
		assertEquals("a", lines[0]);
		assertEquals(" b", lines[1]);
	}

	@Test
	public void test2() {
		final MultiLineStringBuilder msb = new MultiLineStringBuilder();
		msb.setIndentPad(" ");
		msb.appendLine("a");
		msb.indent();
		msb.setIndentPad("**");
		msb.appendLine("b");
		final String s = msb.toString();
		final String[] lines = s.split(msb.getNewline());
		assertEquals(2, lines.length);
		assertEquals("a", lines[0]);
		assertEquals("**b", lines[1]);
	}

	@Test
	public void test3() {
		final String pad = "**";
		final MultiLineStringBuilder msb = new MultiLineStringBuilder(64);
		msb.setIndentPad(" ");
		msb.appendLine("a");
		msb.indent();
		msb.setIndentPad(pad);
		assertEquals(pad, msb.getIndentPad());
		msb.appendLine("b");
		msb.outdent();
		msb.appendLine("c");
		final String s = msb.toString();
		final String[] lines = s.split(msb.getNewline());
		assertEquals(3, lines.length);
		assertEquals("a", lines[0]);
		assertEquals("**b", lines[1]);
		assertEquals("c", lines[2]);
	}
}
