package org.javautil.mp3;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.jaudiotagger.tag.id3.ID3v24Frames;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.javautil.text.StringCleaner;
import org.javautil.text.StringCleanerImpl;

public class Mp3MetadataPopulatorImpl implements Mp3MetadataProcessor {
	private final Logger logger = Logger.getLogger(getClass());

	private final int maxFileNameLength = 255;

	private long fileCount;

	private final StringCleaner stringCleaner = new StringCleanerImpl();

	private boolean oneOrMoreFieldsCleaned = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.mp3.Mp3MetadataProcessor#process(org.javautil.mp3.MP3MetaData
	 * )
	 */
	@Override
	public void process(final Mp3Metadata mp3Meta) throws CannotReadException,
			IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException {
		if (mp3Meta == null) {
			throw new IllegalArgumentException("mp3Meta is null");
		}
		if (mp3Meta.getFileName() == null) {
			throw new IllegalArgumentException("file is null");
		}
		oneOrMoreFieldsCleaned = false;
		checkFileName(mp3Meta);
		logFile(mp3Meta);
		final File file = new File(mp3Meta.getFileName());
		fileCount++;
		if (logger.isDebugEnabled()) {
			logger.debug("processing " + fileCount + " "
					+ mp3Meta.getFileName());
		}
		final MP3File mp3File = (MP3File) AudioFileIO.read(file);

		if (mp3File.hasID3v1Tag()) {
			final ID3v1Tag tag = mp3File.getID3v1Tag();
			mp3Meta.setLeadArtist1(tag.getFirst(FieldKey.ALBUM_ARTIST));
			mp3Meta.setAlbumTitle1(tag.getFirst(FieldKey.ALBUM));
			mp3Meta.setSongComment1(tag.getFirstComment());
			mp3Meta.setGenre1(tag.getFirstGenre());
			mp3Meta.setSongTitle1(tag.getFirstTitle());
			mp3Meta.setYearReleased1(tag.getFirstYear());

			// mp3Meta.setAuthorComposer1(tag.getFirst(ID3v24Frames.FRAME_ID_COMPOSER));
			// mp3Meta.setSongLyric2(v24Tag.getFirst(ID3v24Frames.FRAME_ID_LYRICS));
		}
		if (mp3File.hasID3v2Tag()) {
			final ID3v24Tag v24Tag = mp3File.getID3v2TagAsv24();
			mp3Meta.setLeadArtist2(v24Tag
					.getFirst(ID3v24Frames.FRAME_ID_ARTIST));
			mp3Meta.setAlbumTitle2(v24Tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM));
			mp3Meta.setYearReleased2(v24Tag
					.getFirst(ID3v24Frames.FRAME_ID_YEAR));
			mp3Meta.setSongComment2(v24Tag
					.getFirst(ID3v24Frames.FRAME_ID_COMMENT));
			mp3Meta.setGenre2(v24Tag.getFirst(ID3v24Frames.FRAME_ID_GENRE));
			mp3Meta.setSongTitle2(v24Tag.getFirst(ID3v24Frames.FRAME_ID_TITLE));
			mp3Meta.setYearReleased2(v24Tag
					.getFirst(ID3v24Frames.FRAME_ID_YEAR));

			mp3Meta.setAuthorComposer2(v24Tag
					.getFirst(ID3v24Frames.FRAME_ID_COMPOSER));
			// mp3Meta.setSongLyric2(v24Tag.getFirst(ID3v24Frames.FRAME_ID_LYRICS));
			mp3Meta.setTrack2(v24Tag.getFirst(ID3v24Frames.FRAME_ID_TRACK));
		}
	}

	// TODO should probably check for non printable characters and the like.
	void checkFileName(final Mp3Metadata mp3Meta) {

		if (mp3Meta.getFileName().length() > maxFileNameLength) {
			throw new InvalidFileNameException("name '" + mp3Meta.getFileName()
					+ "' exceeds max supported length of " + maxFileNameLength);
		}
	}

	void logFile(final Mp3Metadata meta) {
		final File file = new File(meta.getFileName());

		if (!file.exists()) {
			throw new IllegalArgumentException("no such file: '"
					+ file.getName());
		}
		if (!file.canRead()) {
			throw new IllegalArgumentException("can't read '" + file.getName()
					+ "'");
		}
	}

	/**
	 * @return the oneOrMoreFieldsCleaned
	 */
	public boolean isOneOrMoreFieldsCleaned() {
		return oneOrMoreFieldsCleaned;
	}
}
