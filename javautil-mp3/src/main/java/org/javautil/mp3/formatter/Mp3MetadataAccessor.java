package org.javautil.mp3.formatter;

import org.javautil.mp3.Mp3Metadata;

public interface Mp3MetadataAccessor {

	public void setMetadata(Mp3Metadata meta);

	// s/b URL
	// public abstract String getFileName();

	public abstract String getSongLyric();

	public abstract String getSongTitle();

	public abstract String getTrack();

	public abstract String getYearReleased();

	public abstract String getAlbumTitle();

	public abstract String getAuthorComposer();

	public abstract String getComment();

	public abstract String getGenre();

	public abstract String getLeadArtist();

	public String getFileName();

	public int getBitRate();

}