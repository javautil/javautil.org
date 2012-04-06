package org.javautil.mp3.formatter;

import org.apache.log4j.Logger;
import org.javautil.mp3.Mp3;
import org.javautil.mp3.Mp3Metadata;

public class Mp3MapperImpl implements Mp3Mapper {

	private final Logger logger = Logger.getLogger(getClass());
	private final Mp3MetadataAccessor accessor = new Mp3MetadataAccess();
	private final int maxTagLength = 60;

	@Override
	public Mp3 toMp3(final Mp3Metadata metaData) {
		accessor.setMetadata(metaData);

		final String fileName = accessor.getFileName();
		final String albumTitle = trim("albumTitle", accessor.getAlbumTitle(),
				maxTagLength);
		final String songTitle = trim("songTitle", accessor.getSongTitle(),
				maxTagLength);
		final String author = trim("author", accessor.getAuthorComposer(),
				maxTagLength);
		final String artist = trim("artist", accessor.getLeadArtist(),
				maxTagLength);
		final String genre = trim("genre", accessor.getGenre(), maxTagLength);
		final String songComment = trim("comment", accessor.getComment(),
				maxTagLength);
		final String track = trim("track", accessor.getTrack(), 8);
		final String yearReleased = trim("year", accessor.getYearReleased(), 4);

		final Mp3 mp3 = new Mp3();
		mp3.setFileName(fileName);
		mp3.setAlbumTitle(albumTitle);
		mp3.setSongTitle(songTitle);
		mp3.setAuthor(author);
		mp3.setArtistName(artist);
		mp3.setGenre(genre);
		mp3.setSongComment(songComment);
		mp3.setTrack(track);
		mp3.setYearReleased(yearReleased);
		mp3.setBitRate(accessor.getBitRate());
		return mp3;

	}

	public String trim(final String tagName, final String text,
			final int maxLength) {
		String returnValue = text;
		if ((text != null) && (text.length() > maxLength)) {
			returnValue = text.substring(0, maxLength - 1);
			logger.warn("trimming " + tagName + " from '" + text + "'"
					+ " to '" + returnValue + "'");
		}
		return returnValue;
	}

}
