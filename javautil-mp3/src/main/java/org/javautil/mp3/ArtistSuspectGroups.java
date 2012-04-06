package org.javautil.mp3;

import java.util.TreeMap;

import org.apache.log4j.Logger;

public class ArtistSuspectGroups extends TreeMap<String, ArtistSuspectGroup> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(getClass());

	// private Map<String, ArtistSuspectGroup>suspectGroupByTokenStringMap = new
	// TreeMap<String,ArtistSuspectGroup>();

	public void add(final ArtistSuspect suspect) {

		if ((suspect.getTokenString() != null)
				&& (suspect.getTokenString().length() > 0)) {
			if (logger.isDebugEnabled()) {
				logger.debug("getting '" + suspect.getTokenString() + "'");
			}
			ArtistSuspectGroup group = get(suspect.getTokenString());
			if (group == null) {
				group = new ArtistSuspectGroup(suspect.getTokenString());
			}
			group.add(suspect);
			put(group.getTokenString(), group);

		}

	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (final String text : keySet()) {
			sb.append(text);
			sb.append(":\n");
			final ArtistSuspectGroup group = get(text);
			sb.append(group);
			sb.append("\n");
		}
		final String retval = sb.toString();
		return retval;
	}
}
