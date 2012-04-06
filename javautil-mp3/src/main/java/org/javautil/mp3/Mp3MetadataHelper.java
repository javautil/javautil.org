package org.javautil.mp3;

import org.javautil.mp3.formatter.Mp3BaseMetadataFormatter;

// TODO need interface?
public class Mp3MetadataHelper {

	private final Mp3BaseMetadataFormatter formatter = new Mp3BaseMetadataFormatter();
	private Mp3Metadata metaData = null;

	public Mp3MetadataHelper(final Mp3Metadata metaData) {
		if (metaData == null) {
			throw new IllegalArgumentException("metaData is null");
		}
		this.metaData = metaData;
	}

	public String getSongLyric() {
		return formatter.getSongLyric();
	}

	public String getSongTitle() {
		return formatter.getSongTitle();
	}

	public String getTrack() {
		return formatter.getTrack();
	}

	public String getYearReleased() {
		return formatter.getYearReleased();
	}

	public String getAlbumTitle() {
		return formatter.getAlbumTitle();
	}

	public String getAuthorComposer() {
		return formatter.getAuthorComposer();
	}

	public String getComment() {
		return formatter.getComment();
	}

	public String getGenre() {
		return formatter.getGenre();
	}

	public String getLeadArtist() {
		return formatter.getLeadArtist();

	}

	public String getFileName() {
		return metaData.getFileName();
	}

}
