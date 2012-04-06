package org.javautil.mp3;

import java.util.Map;
import java.util.TreeMap;

public class ArtistSuspectGroup {
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ArtistSuspectGroup [tokenString=" + tokenString
				+ ", byRawNameMap=" + byRawNameMap + "]";
	}

	private String tokenString;

	private Map<String, ArtistSuspect> byRawNameMap = new TreeMap<String, ArtistSuspect>();

	public ArtistSuspectGroup(final String tokenString) {
		if (tokenString == null) {
			throw new IllegalArgumentException("tokenString is null");
		}
		this.tokenString = tokenString;
	}

	public void add(final ArtistSuspect suspect) {
		if (suspect == null) {
			throw new IllegalArgumentException("suspect is null");
		}
		byRawNameMap.put(suspect.getRawName(), suspect);
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

	/**
	 * @return the byRawNameMap
	 */
	public Map<String, ArtistSuspect> getByRawNameMap() {
		return byRawNameMap;
	}

	/**
	 * @param byRawNameMap
	 *            the byRawNameMap to set
	 */
	public void setByRawNameMap(final Map<String, ArtistSuspect> byRawNameMap) {
		this.byRawNameMap = byRawNameMap;
	}

}
