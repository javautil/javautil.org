package org.javautil.mp3;

public class ArtistSuspect {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("ArtistSuspect [rawName=" + rawName + ", rawNameCount="
				+ rawNameCount + ", tokenString=" + tokenString + "]");
		return sb.toString();
	}

	private String rawName;

	private int rawNameCount;

	private String[] tokens;

	private String tokenString;

	public ArtistSuspect() {

	}

	public ArtistSuspect(final String rawName, final int rawNameCount) {
		super();
		this.rawName = rawName;
		this.rawNameCount = rawNameCount;
	}

	public void populateTokenString() {
		// TODO check rawName null
		if (tokens == null) {
			tokenString = null;
		} else {
			// TODO check rawName null
			final StringBuilder b = new StringBuilder(rawName.length());

			boolean needsSpace = false;
			for (final String word : tokens) {
				if (needsSpace) {
					b.append(" ");
				}
				b.append(word);
				needsSpace = true;
			}
			tokenString = b.toString();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rawName == null) ? 0 : rawName.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ArtistSuspect other = (ArtistSuspect) obj;
		if (rawName == null) {
			if (other.rawName != null) {
				return false;
			}
		} else if (!rawName.equals(other.rawName)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the rawName
	 */
	public String getRawName() {
		return rawName;
	}

	/**
	 * @param rawName
	 *            the rawName to set
	 */
	public void setRawName(final String rawName) {
		this.rawName = rawName;
	}

	/**
	 * @return the rawNameCount
	 */
	public int getRawNameCount() {
		return rawNameCount;
	}

	/**
	 * @param rawNameCount
	 *            the rawNameCount to set
	 */
	public void setRawNameCount(final int rawNameCount) {
		this.rawNameCount = rawNameCount;
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

	/**
	 * @return the tokenString
	 */
	public String getTokenString() {
		return tokenString;
	}

	/**
	 * @param tokenString
	 *            the tokenString to set
	 */
	public void setTokenString(final String tokenString) {
		this.tokenString = tokenString;
	}

}
