package org.javautil.mp3;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.javautil.collections.ArrayComparator;
import org.javautil.text.StringSimilarity;
import org.javautil.text.WordExtractor;
import org.javautil.text.WordExtractorImpl;

public class ArtistGrouper {

	private final Logger logger = Logger.getLogger(getClass());

	private final WordExtractor extractor = new WordExtractorImpl();

	private HashSet<String> excluded = new HashSet<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add("AND");
			add("THE");
		}
	};

	private final Map<String[], ArtistTagged> artists = new TreeMap(
			new ArrayComparator());

	// private Map<String[], Map<String,ArtistSuspect> >suspectsByTokens = new
	// TreeMap<String[],Map<String,ArtistSuspect>>(new ArrayComparator());

	private final ArtistSuspectGroups groups = new ArtistSuspectGroups();
	private Collection<ArtistSuspect> suspects;

	private final Map<String, String[]> concatenatedStringsMap = new HashMap<String, String[]>();

	public ArtistGrouper() {
		extractor.setExcludeWords(excluded);
	}

	/**
	 * @return the suspects
	 */
	public Collection<ArtistSuspect> getSuspects() {
		return suspects;
	}

	/**
	 * @param suspects
	 *            the suspects to set
	 */
	public void setSuspects(final Collection<ArtistSuspect> suspects) {
		this.suspects = suspects;
	}

	// TODO option to not bother if done
	ArtistSuspect prepareSuspect(final ArtistSuspect suspect) {
		final String[] words = extractor.getWords(suspect.getRawName());
		suspect.setTokens(words);
		suspect.populateTokenString();
		return suspect;
	}

	void setSuspects(final ArtistSuspect suspect) {

	}

	void addArtist(final Artist artist) {
		if (artist == null) {
			throw new IllegalArgumentException("artist is null");
		}
		final ArtistTagged artistTagged = new ArtistTagged(artist);
		final String[] tokens = extractor.getWords(artist.getArtistName());
		artists.put(tokens, artistTagged);
	}

	ArtistTagged getArtist(final String[] tokens) {
		return artists.get(tokens);
	}

	public Map<String, ArtistSuspectGroup> getGroupsWithMultipleSuspects() {
		final Map<String, ArtistSuspectGroup> map = new TreeMap<String, ArtistSuspectGroup>();
		if (groups.size() == 0) {
			getSuspectGroupByTokenStringMap();
		}
		for (final String tokenString : groups.keySet()) {
			final ArtistSuspectGroup group = groups.get(tokenString);
			if (group.getByRawNameMap().size() > 1) {
				map.put(tokenString, group);
			}
		}
		return map;
	}

	// TODO THIS IS IT
	ArtistSuspectGroups getSuspectGroupByTokenStringMap() {
		// prepare suspect must have been called first
		groups.clear();
		new StringSimilarity();
		for (final ArtistSuspect suspect : suspects) {
			prepareSuspect(suspect);
			if ((suspect.getTokenString() != null)
					&& (suspect.getTokenString().length() > 0)) {
				// if (logger.isDebugEnabled()) {
				// logger.debug("getting '" + suspect.getTokenString() + "'");
				// }
				// ArtistSuspectGroup group =
				// groups.get(suspect.getTokenString());
				// if (group == null) {
				// group = new ArtistSuspectGroup(suspect.getTokenString());
				// }
				// group.add(suspect);
				groups.add(suspect);
			}
		}
		return groups;
	}

	// public void processSimilar() {
	// populateConcatenatedStringsMap();
	// Collection<String> concatenatedStrings = concatenatedStringsMap.keySet();
	// StringSimilarity similar = new StringSimilarity();
	// for (String string : concatenatedStrings) {
	// Set<String> candidates =
	// similar.getSimilarStringSet(string,concatenatedStrings,.80f, 2);
	// }
	// }

	// void populateSuspectsByTokens() {
	// for (ArtistSuspect s : suspects) {
	// String[] words = s.getTokens();
	// if (words == null) {
	// words = extractor.getWords(s.getRawName());
	// s.setTokens(words);
	// }
	// s.populateTokenString();
	// Map<String, ArtistSuspect> artistsInGroup = suspectsByTokens.get(words);
	// if (artistsInGroup == null) {
	// artistsInGroup = new TreeMap<String,ArtistSuspect>();
	// }
	// artistsInGroup.put(s.getRawName(),s);
	// suspectsByTokens.put(words,artistsInGroup);
	// }
	// }
	//
	// public String dumpSuspectsByTokens() {
	// StringBuilder b = new StringBuilder();
	// for (String[] keyWords : suspectsByTokens.keySet()) {
	// for (String word : keyWords) {
	// b.append(word);
	// b.append(" \n");
	// }
	// Map<String,ArtistSuspect> suspects = suspectsByTokens.get(keyWords);
	// for (ArtistSuspect s: suspects.values()) {
	// b.append("  ");
	// b.append(s);
	// b.append("\n");
	// }
	// }
	// return b.toString();
	// }
	/**
	 * @return the excluded
	 */
	public HashSet<String> getExcluded() {
		return excluded;
	}

	/**
	 * @param excluded
	 *            the excluded to set
	 */
	public void setExcluded(final HashSet<String> excluded) {
		this.excluded = excluded;
	}

	// /**
	// * @return the artists
	// */
	// public Map<String[], ArtistTagged> getArtists() {
	// return artists;
	// }
	//
	// /**
	// * @param artists the artists to set
	// */
	// public void setArtists(Map<String[], ArtistTagged> artists) {
	// this.artists = artists;
	// }
	//
	// // }

}
