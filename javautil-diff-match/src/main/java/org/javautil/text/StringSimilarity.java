package org.javautil.text;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import org.apache.log4j.Logger;

import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Diff;


public class StringSimilarity {

	// TODO add a feature to exclude the original String on exact match a = b or
	// a.equals(b)

	private final Logger logger = Logger.getLogger(getClass());

	public int getDiffCount(final String base, final String other) {
		final diff_match_patch dmp = new diff_match_patch();
		final LinkedList<Diff> diffs = dmp.diff_main(base, other);
		final int diffCount = dmp.diff_levenshtein(diffs);
		return diffCount;
	}

	public float getMatchRatio(final String base, final String other,
			final float diffCount) {
		float ratio = 0.0f;
		if (base == null || base.length() == 0) {
			if (other != null && other.length() > 0) {
				ratio = 0f;
			}
		} else {
			ratio = (float) 1.0 - (diffCount / base.length());
		}
		return ratio;
	}

	public float getMatchRatio(final String base, final String other) {
		final float diffCount = getDiffCount(base, other);
		return getMatchRatio(base, other, diffCount);
	}

	public boolean isSimilar(final String example, final String string,
			final float ratio, final int maxDiffCount) {
		final int diffCount = getDiffCount(example, string);
		final float matchRatio = getMatchRatio(example, string, diffCount);
		boolean returnValue = false;
		if (matchRatio >= ratio || diffCount <= maxDiffCount) {
			returnValue = true;
		}
		return returnValue;
	}

	public Set<String> getSimilarStringSet(final String example,
			final Collection<String> strings, final float matchRatio,
			final int maxDiffCount) {
		final HashSet<String> stringSet = new HashSet<String>();
		if (strings != null) {
			for (final String string : strings) {
				final int diffCount = getDiffCount(example, string);
				final float ratio = getMatchRatio(example, string, diffCount);
				// TODO clean
				if (logger.isDebugEnabled()) {
					final String message = "'" + example + "' '" + string
							+ "' ratio " + ratio + " diffCount " + diffCount;
					logger.debug(message);
				}
				if (ratio > matchRatio || diffCount <= maxDiffCount) {
					stringSet.add(string);
				}
			}
		}

		return stringSet;

	}
}
