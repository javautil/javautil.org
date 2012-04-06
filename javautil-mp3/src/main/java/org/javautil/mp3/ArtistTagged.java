package org.javautil.mp3;

public class ArtistTagged {

	private Artist artist;

	private String[] tokens;

	public ArtistTagged(final Artist artist) {
		super();
		this.artist = artist;
	}

	/**
	 * @return the artist
	 */
	public Artist getArtist() {
		return artist;
	}

	/**
	 * @param artist
	 *            the artist to set
	 */
	public void setArtist(final Artist artist) {
		this.artist = artist;
	}

	/**
	 * @return the tokens
	 */
	public String[] getTokens() {
		return tokens;
	}

	/**
	 * @param tokens
	 *            the tokens to set
	 */
	public void setTokens(final String[] tokens) {
		this.tokens = tokens;
	}

}
