package org.javautil.mp3.formatter;

import org.javautil.mp3.Mp3Metadata;

public class Mp3MetadataAccess implements Mp3MetadataAccessor {
	static final String newline = System.getProperty("line.separator");

	private Mp3Metadata meta;

	public Mp3MetadataAccess() {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.mp3.formatter.MP3MetadataAccessor#getSongLyric(org.javautil
	 * .mp3.MP3Metadata)
	 */
	@Override
	public String getSongLyric() {
		return preferred(meta.getSongLyric1(), meta.getSongLyric2());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.mp3.formatter.MP3MetadataAccessor#getSongTitle(org.javautil
	 * .mp3.MP3Metadata)
	 */
	@Override
	public String getSongTitle() {
		return preferred(meta.getSongTitle1(), meta.getSongTitle2());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.mp3.formatter.MP3MetadataAccessor#getTrack(org.javautil.
	 * mp3.MP3Metadata)
	 */
	@Override
	public String getTrack() {
		return preferred(meta.getTrack1(), meta.getTrack2());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.mp3.formatter.MP3MetadataAccessor#getYearReleased(org.javautil
	 * .mp3.MP3Metadata)
	 */
	@Override
	public String getYearReleased() {
		return preferred(meta.getYearReleased1(), meta.getYearReleased2());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.mp3.formatter.MP3MetadataAccessor#getAlbumTitle(org.javautil
	 * .mp3.MP3Metadata)
	 */
	@Override
	public String getAlbumTitle() {
		return preferred(meta.getAlbumTitle1(), meta.getAlbumTitle2());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.mp3.formatter.MP3MetadataAccessor#getAuthorComposer(org.
	 * javautil.mp3.MP3Metadata)
	 */
	@Override
	public String getAuthorComposer() {
		return preferred(meta.getAuthorComposer1(), meta.getAuthorComposer2());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.mp3.formatter.MP3MetadataAccessor#getComment(org.javautil
	 * .mp3.MP3Metadata)
	 */
	@Override
	public String getComment() {
		return preferred(meta.getSongComment1(), meta.getSongComment2());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.mp3.formatter.MP3MetadataAccessor#getGenre(org.javautil.
	 * mp3.MP3Metadata)
	 */
	@Override
	public String getGenre() {
		return preferred(meta.getGenre1(), meta.getGenre2());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.mp3.formatter.MP3MetadataAccessor#getLeadArtist(org.javautil
	 * .mp3.MP3Metadata)
	 */
	@Override
	public String getLeadArtist() {
		return preferred(meta.getLeadArtist1(), meta.getLeadArtist2());
	}

	/**
	 * @return the meta
	 */
	public Mp3Metadata getMeta() {
		return meta;
	}

	/**
	 * @param meta
	 *            the meta to set
	 */
	@Override
	public void setMetadata(final Mp3Metadata meta) {
		this.meta = meta;
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return meta.getFileName();
	}

	@Override
	public int getBitRate() {
		return meta.getBitRate();
	}

}