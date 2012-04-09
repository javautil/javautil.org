package org.javautil.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/*
 * Copyright (c) 2002-2004, Martian Software, Inc.
 * This file is made available under the LGPL as described in the accompanying
 * LICENSE.TXT file.
 */

//package com.martiansoftware.jsap;

/**
 * This junit TestCase runs a barrage of tests against the CommandLineTokenizer.
 * This TestCase is unusual for this project in that it contains a main()
 * method; this method is used to generate test methods to paste into this
 * class.
 * 
 * @see CommandLineTokenizer
 * @author <a href="http://www.martiansoftware.com/contact.html">Marty Lamb</a>
 */
public class CommandLineTokenizerTest {

	/**
	 * Tests for correct parsing of [this is a test].
	 */
	@Test
	public void test1() {
		final String cmdLine = "this is a test";
		final String[] tokens = new CommandLineTokenizer().tokenize(cmdLine);

		assertEquals(4, tokens.length);

		assertEquals("this", tokens[0]);
		assertEquals("is", tokens[1]);
		assertEquals("a", tokens[2]);
		assertEquals("test", tokens[3]);
	}

	/**
	 * Tests for corect parsing of [this is a "test"]
	 */
	@Test
	public void test2() {
		final String[] tokens = new CommandLineTokenizer()
				.tokenize("this is a \"test\"");

		assertEquals(4, tokens.length);

		assertEquals("this", tokens[0]);
		assertEquals("is", tokens[1]);
		assertEquals("a", tokens[2]);
		assertEquals("test", tokens[3]);
	}

	/**
	 * Tests for correct parsing of ["this is a test"]
	 */
	@Test
	public void test3() {
		final String cmdLine = "\"this is a test\"";
		final String[] tokens = new CommandLineTokenizer().tokenize(cmdLine);

		assertEquals(1, tokens.length);

		assertEquals("this is a test", tokens[0]);
	}

	/**
	 * Tests for correct parsing of [this is a "test]
	 */
	@Test
	public void test4() {
		final String cmdLine = "this is a \"test";
		final String[] tokens = new CommandLineTokenizer().tokenize(cmdLine);
		assertEquals(4, tokens.length);

		assertEquals("this", tokens[0]);
		assertEquals("is", tokens[1]);
		assertEquals("a", tokens[2]);
		assertEquals("test", tokens[3]);
	}

	/**
	 * Tests for correct parsing of [thi\s is a \"test]
	 */
	@Test
	public void test5() {
		final String cmdLine = "thi\\s is a \\\"test";
		final String[] tokens = new CommandLineTokenizer().tokenize(cmdLine);
		assertEquals(4, tokens.length);

		assertEquals("thi\\s", tokens[0]);
		assertEquals("is", tokens[1]);
		assertEquals("a", tokens[2]);
		assertEquals("\"test", tokens[3]);
	}

	/**
	 * Tests for correct parsing of [thi\s is a \"test\\]
	 */
	@Test
	public void test6() {
		final String cmdLine = "thi\\s is a \\\"test\\";
		final String[] tokens = new CommandLineTokenizer().tokenize(cmdLine);
		assertEquals(4, tokens.length);

		assertEquals("thi\\s", tokens[0]);
		assertEquals("is", tokens[1]);
		assertEquals("a", tokens[2]);
		assertEquals("\"test\\", tokens[3]);
	}

	/**
	 * Tests for correct parsing of [thi\s is a \"test\\\"]
	 */
	@Test
	public void test7() {
		final String cmdLine = "thi\\s is a \\\"test\\\\\"";
		final String[] tokens = new CommandLineTokenizer().tokenize(cmdLine);
		assertEquals(4, tokens.length);

		assertEquals("thi\\s", tokens[0]);
		assertEquals("is", tokens[1]);
		assertEquals("a", tokens[2]);
		assertEquals("\"test\\", tokens[3]);
	}

	/**
	 * Tests for correct parsing of [thi\s is a \"test\\\"]
	 */
	@Test
	public void test71() {
		final String cmdLine = "thi\\s is  a \\\"test\\\\\"";
		final String[] tokens = new CommandLineTokenizer().tokenize(cmdLine);
		assertEquals(4, tokens.length);

		assertEquals("thi\\s", tokens[0]);
		assertEquals("is", tokens[1]);
		assertEquals("a", tokens[2]);
		assertEquals("\"test\\", tokens[3]);
	}

	/**
	 * Tests for correct parsing of a null command line.
	 */
	@Test
	public void test8() {
		final String[] tokens = new CommandLineTokenizer().tokenize(null);
		assertEquals(0, tokens.length);
	}

	/**
	 * Tests for correct parsing of a whitespace-only command line.
	 */
	@Test
	public void test9() {
		final String cmdLine = "   \t\t  \t ";
		final String[] tokens = new CommandLineTokenizer().tokenize(cmdLine);
		assertEquals(0, tokens.length);
	}

	/**
	 * Tests for correct parsing of ["this is a test]
	 */
	@Test
	public void test10() {
		final String cmdLine = "\"this is a test";
		final String[] tokens = new CommandLineTokenizer().tokenize(cmdLine);
		assertEquals(1, tokens.length);

		assertEquals("this is a test", tokens[0]);
	}

	/**
	 * A helper method to write additional test cases based upon the specified
	 * argument array.
	 * 
	 * @param args
	 *            the argument array that CommandLineTokenizer.tokenize() should
	 *            produce.
	 */
	public static void main(final String[] args) {

		System.out.println("public void testXXX(){");
		System.out.println("\tString cmdLine = \"\";");
		System.out
				.println("\tString[] tokens = CommandLineTokenizer.tokenize(cmdLine);");
		System.out.println("\n");
		System.out.println("\tassertEquals(" + args.length
				+ ", tokens.length);\n");
		for (int i = 0; i < args.length; ++i) {
			System.out.println("\tassertEquals(\"" + args[i] + "\", tokens["
					+ i + "]);");
		}
		System.out.println("}");
	}

}
