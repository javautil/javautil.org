package org.javautil.commandline.annotations;

import org.javautil.commandline.BaseArgumentBean;

public class RequiredUnlessArguments extends BaseArgumentBean {

	@RequiredUnless(property = "toad")
	private String frog;

	@Optional
	private String toad;

	@Optional
	private String snake;

	/**
	 * @return the snake
	 */
	public String getSnake() {
		return snake;
	}

	/**
	 * @param snake
	 *            the snake to set
	 */
	public void setSnake(final String snake) {
		this.snake = snake;
	}

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
}
