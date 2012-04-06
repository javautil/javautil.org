//package org.javautil.mp3;
//
//import java.io.File;
//
//import org.apache.log4j.Logger;
//import org.farng.mp3.MP3File;
//import org.farng.mp3.id3.AbstractID3v1;
//import org.farng.mp3.id3.AbstractID3v2;
//import org.javautil.text.StringCleaner;
//import org.javautil.text.StringCleanerImpl;
//
//public class Mp3MetadataPopulator implements Mp3MetadataProcessor {
//	private final Logger logger = Logger.getLogger(getClass());
//
//	private final int maxFileNameLength = 255;
//
//	private long fileCount;
//
//	private final StringCleaner stringCleaner = new StringCleanerImpl();
//
//	private boolean oneOrMoreFieldsCleaned = false;
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * org.javautil.mp3.Mp3MetadataProcessor#process(org.javautil.mp3.MP3MetaData
//	 * )
//	 */
//	@Override
//	public void process(final Mp3Metadata mp3Meta)
//			throws MetadataPopulationException {
//		if (mp3Meta == null) {
//			throw new IllegalArgumentException("mp3Meta is null");
//		}
//		if (mp3Meta.getFileName() == null) {
//			throw new IllegalArgumentException("file is null");
//		}
//		oneOrMoreFieldsCleaned = false;
//		checkFileName(mp3Meta);
//		logFile(mp3Meta);
//		MP3File mp3 = null;
//		final File file = new File(mp3Meta.getFileName());
//		fileCount++;
//		if (logger.isDebugEnabled()) {
//			logger.debug("processing " + fileCount + " "
//					+ mp3Meta.getFileName());
//		}
//		try {
//			mp3 = getMP3File(file);
//
//			mp3Meta.setBitRate(mp3.getBitRate());
//
//			final AbstractID3v1 tag3V1 = mp3.getID3v1Tag();
//			if (tag3V1 != null) {
//				mp3Meta.setAlbumTitle1(trim(tag3V1.getAlbumTitle()));
//				mp3Meta.setLeadArtist1(trim(tag3V1.getLeadArtist()));
//				mp3Meta.setSongComment1(trim(tag3V1.getSongComment()));
//				mp3Meta.setGenre1(trim(tag3V1.getSongGenre()));
//				mp3Meta.setSongTitle1(trim(tag3V1.getSongTitle()));
//				mp3Meta.setYearReleased1(trim(tag3V1.getYearReleased()));
//			}
//			//
//			final AbstractID3v2 tag3V2 = mp3.getID3v2Tag();
//			if (tag3V2 != null) {
//				mp3Meta.setAlbumTitle2(trim(tag3V2.getAlbumTitle()));
//				mp3Meta.setAuthorComposer2(trim(tag3V2.getAuthorComposer()));
//				tag3V2.getLeadArtist();
//				mp3Meta.setLeadArtist2(trim(tag3V2.getLeadArtist()));
//				mp3Meta.setGenre2(trim(tag3V2.getSongGenre()));
//				mp3Meta.setSongComment2(trim(tag3V2.getSongComment()));
//				mp3Meta.setSongLyric2(trim(tag3V2.getSongLyric()));
//				mp3Meta.setSongTitle2(trim(tag3V2.getSongTitle()));
//				mp3Meta.setTrack2(trim(tag3V2.getTrackNumberOnAlbum()));
//				mp3Meta.setYearReleased2(trim(tag3V2.getYearReleased()));
//			}
//
//		} catch (final Exception e) {
//			String causeMessage = e.getCause() == null ? "" : "\n" + e.getCause().getMessage();
//			final String message = "while processing " + file.getAbsolutePath()
//					+ "\n" + e.getMessage() + causeMessage;
//			logger.error(message);
//			throw new MetadataPopulationException(message, e.getCause());
//		}
//	}
//
//	/**
//	 * Separate function for profiling purposes.
//	 * 
//	 * @param file
//	 * @return
//	 * @throws Exception
//	 */
//	MP3File getMP3File(final File file) throws Exception {
//		return new MP3File(file, false);
//	}
//
//	// TODO should probably check for non printable characters and the like.
//	void checkFileName(final Mp3Metadata mp3Meta) {
//
//		if (mp3Meta.getFileName().length() > maxFileNameLength) {
//			throw new InvalidFileNameException("name '" + mp3Meta.getFileName()
//					+ "' exceeds max supported length of " + maxFileNameLength);
//		}
//	}
//
//	void logFile(final Mp3Metadata meta) {
//		final File file = new File(meta.getFileName());
//
//		if (!file.exists()) {
//			throw new IllegalArgumentException("no such file: '"
//					+ file.getName());
//		}
//		if (!file.canRead()) {
//			throw new IllegalArgumentException("can't read '" + file.getName()
//					+ "'");
//		}
//	}
//
//	/**
//	 * TODO this is cut and pasted from MpeMetaDataFormatter
//	 * 
//	 * @param x
//	 * @return
//	 */
//	private String trim(final String x) {
//	
//			byte [] bytes = x.getBytes();
//			if (bytes.length >= 2) {
//				if (bytes[0] == 0xff ) {
//				    logger.warn("whoa");	
//				}
//			}
//		
//		String returnValue = x;
//		if (x != null) {
//			returnValue = x.trim();
//
//			String cleaned = stringCleaner.clean(returnValue);
//			cleaned = cleaned.trim();
//			if (cleaned.length() == 0) {
//				returnValue = null;
//			} else {
//				if (cleaned != returnValue) {
//					returnValue = cleaned;
//					oneOrMoreFieldsCleaned = true;
//					logger.info("Converted '" + x + "' to '" + cleaned + "'");
//				}
//			}
//		}
//		return returnValue;
//	}
//
//	/**
//	 * @return the oneOrMoreFieldsCleaned
//	 */
//	public boolean isOneOrMoreFieldsCleaned() {
//		return oneOrMoreFieldsCleaned;
//	}
// }
