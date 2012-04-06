package org.javautil.mp3.formatter;

import org.javautil.mp3.Mp3Metadata;

public class Mp3BaseMetadataFormatter {
	static final String newline = System.getProperty("line.separator");

	private Mp3Metadata meta;

	public Mp3BaseMetadataFormatter() {
		super();
	}

	protected String asPair(final String name, final String value) {
		String returnValue = "";
		if ((value != null) && (value.trim().length() > 0)) {
			returnValue = name + ": '" + value + "'" + newline;
		}

		return returnValue;
	}

	// TODO document
	private String preferred(final String v1, final String v2) {
		String returnValue = null;
		if (v1 == null) {
			if (v2 != null) {
				returnValue = v2;
			}
		} else {
			if (v2 == null) {
				returnValue = v1;
			} else {
				if (v2.startsWith(v1)) {
					returnValue = v2;
				} else {
					// now what??
					returnValue = v2;
				}
			}
		}
		return returnValue;
	}

	public String getSongLyric() {
		return preferred(meta.getSongLyric1(), meta.getSongLyric2());
	}

	public String getSongTitle() {
		return preferred(meta.getSongTitle1(), meta.getSongTitle2());
	}

	public String getTrack() {
		return preferred(meta.getTrack1(), meta.getTrack2());
	}

	public String getYearReleased() {
		return preferred(meta.getYearReleased1(), meta.getYearReleased2());
	}

	public String getAlbumTitle() {
		return preferred(meta.getAlbumTitle1(), meta.getAlbumTitle2());
	}

	public String getAuthorComposer() {
		return preferred(meta.getAuthorComposer1(), meta.getAuthorComposer2());
	}

	public String getComment() {
		return preferred(meta.getSongComment1(), meta.getSongComment2());
	}

	public String getGenre() {
		return preferred(meta.getGenre1(), meta.getGenre2());
	}

	public String getLeadArtist() {
		return preferred(meta.getLeadArtist1(), meta.getLeadArtist2());
	}

	public String getDirectoryName() {
		return meta.getDirectoryName();
	}

	public String getFileName() {
		return meta.getFileName();
	}

	public String getSongLyric(final Mp3Metadata metaData) {
		return preferred(meta.getSongLyric1(), meta.getSongLyric2());
	}
}