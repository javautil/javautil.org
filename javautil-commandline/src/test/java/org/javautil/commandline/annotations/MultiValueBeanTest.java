package org.javautil.commandline.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jcmdline.CmdLineException;

import org.javautil.commandline.BaseTest;
import org.javautil.commandline.CommandLineHandler;
import org.junit.Test;

public class MultiValueBeanTest extends BaseTest {

	private final MultiValueBean argumentBean = new MultiValueBean();
	private final CommandLineHandler clh = new CommandLineHandler(argumentBean);

	@Test
	public void test1() throws CmdLineException {
		final Set<String> wordSet = new HashSet<String>();
		wordSet.add("one");
		wordSet.add("two");
		final String argString = "-words one -words two ";
		clh.evaluateArgumentsString(argString);
		final Collection<String> words = argumentBean.getWords();
		for (final String word : words) {
			assertTrue(wordSet.remove(word));
		}
		assertEquals(0, wordSet.size());
	}
}
