package org.javautil.commandline.annotations;

import jcmdline.CmdLineException;

import org.javautil.commandline.BaseTest;
import org.javautil.commandline.CommandLineHandler;
import org.junit.Test;

public class InvalidRequiredUnlessTest extends BaseTest {
	/**
	 * There is no such property as bird
	 */
	@RequiredUnless(property = "bird")
	private String frog;

	@Optional
	private String toad;

	@Optional
	private String snake;

	// private RequiredUnlessArguments argumentBean = new
	// RequiredUnlessArguments();

	public String getFrog() {
		return frog;
	}

	public void setFrog(final String frog) {
		this.frog = frog;
	}

	public String getToad() {
		return toad;
	}

	public void setToad(final String toad) {
		this.toad = toad;
	}

	public String getSnake() {
		return snake;
	}

	public void setSnake(final String snake) {
		this.snake = snake;
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void test1() throws CmdLineException {
		new CommandLineHandler(this);
	}
}
