package org.javautil.mp3.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.javautil.mp3.Mp3Metadata;
import org.javautil.mp3.formatter.Mp3MetadataAccess;

public class Mp3Persister {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private final Connection conn;

	private PreparedStatement insertStatement;

	private final String insertText = "insert into mp3("
			+ "file_name, "
			+
			// "bit_rate, "
			" album_title, song_title, "
			+ "author,    artist,   genre,       song_comment, "
			+ "track, year_released) values (?,?,?,?,?,?,?,?,?)";

	private final String updateText = "update mp3 " + "set  album_title = ?, "
			+ "song_title = ?, " + "author = ?,    " + "artist = ?,   "
			+ "genre = ?,       " + "song_comment = ?, " + "track = ?, "
			+ "year_released = ? " + "where file_name = ?";

	private final int maxFileNameLength = 255;

	private final int maxTagLength = 60;

	private PreparedStatement updateStatement;

	public Mp3Persister(final Connection conn) {
		if (conn == null) {
			throw new IllegalArgumentException("conn was null");
		}
		this.conn = conn;
	}

	public void createMp3() throws SQLException {
		final String createText = "create table mp3( "
				+ "    file_name varchar2(255) not null, "
				+ "    bit_Rate  int, " + "    album_Title varchar2(60), "
				+ "    song_title  varchar2(60), "
				+ "    author      varchar2(60), "
				+ "    artist      varchar2(60), "
				+ "    genre       varchar2(60), "
				+ "    song_comment varchar2(60),"
				+ "    track        varchar2(8), "
				+ "    year_released varchar2(8))";

		final String pkText = "alter table mp3 add constraint mp3_pk primary key (file_name)";

		Statement s = conn.createStatement();
		try {
			s.execute(createText);
			logger.info("table created " + createText);
			// s.close();
		} catch (final SQLException e) {
			logger.warn(e.getMessage());
		}
		s = conn.createStatement();
		try {
			s.execute(pkText);
			logger.info("pk created " + pkText);
			s.close();
		} catch (final SQLException e) {
			logger.warn(e.getMessage());
		}
	}

	public void insert(final Mp3Metadata mp3data) throws SQLException {
		prepareInsert();
		final String fileName = mp3data.getFileName();
		final Mp3MetadataAccess accessor = new Mp3MetadataAccess();
		accessor.setMetadata(mp3data);
		if ((fileName != null) && (fileName.length() > maxFileNameLength)) {
			throw new IllegalArgumentException("filen name is longer than "
					+ maxFileNameLength);
		}
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

		insertStatement.setObject(1, fileName);

		insertStatement.setObject(2, albumTitle);
		insertStatement.setObject(3, songTitle);
		insertStatement.setObject(4, author);
		insertStatement.setObject(5, artist);
		insertStatement.setObject(6, genre);
		insertStatement.setObject(7, songComment);
		insertStatement.setObject(8, track);
		insertStatement.setObject(9, yearReleased);
		try {
			insertStatement.executeUpdate();
		} catch (final SQLException sqe) {
			// TODO restore
			// GenericSQLException gse = GenericSQLException.getGeneric(conn,
			// sqe);
			// if (gse.equals(GenericSQLException.DUP_VAL_ON_INDEX)) {
			// update(mp3data);
			// } else {
			// String state = sqe.getSQLState();
			// int errorCode = sqe.getErrorCode();
			//
			// logger.error(sqe.getMessage() + newline + "error code " +
			// errorCode + " while processing '" + fileName + "'"
			// + newline + "state: " + state);
			throw sqe;
			// }
		}

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

	public void update(final Mp3Metadata mp3data) throws SQLException {
		// TODO we are doing the extract all over again
		final Mp3MetadataAccess accessor = new Mp3MetadataAccess();
		accessor.setMetadata(mp3data);
		prepareUpdate();
		final String fileName = mp3data.getFileName();
		if ((fileName != null) && (fileName.length() > maxFileNameLength)) {
			throw new IllegalArgumentException("filen name is longer than "
					+ maxFileNameLength);
		}
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

		updateStatement.setObject(1, albumTitle);
		updateStatement.setObject(2, songTitle);
		updateStatement.setObject(3, author);
		updateStatement.setObject(4, artist);
		updateStatement.setObject(5, genre);
		updateStatement.setObject(6, songComment);
		updateStatement.setObject(7, track);
		updateStatement.setObject(8, yearReleased);
		updateStatement.setObject(9, fileName);
		try {
			updateStatement.executeUpdate();
		} catch (final SQLException sqe) {
			// GenericSQLException gse = GenericSQLException.getGeneric(conn,
			// sqe);
			//
			// String state = sqe.getSQLState();
			// int errorCode = sqe.getErrorCode();
			//
			// logger.error(sqe.getMessage() + newline + "error code " +
			// errorCode + " while processing '" + fileName + "'" + newline
			// + "state: " + state);
			throw sqe;
		}
		// logger.info("inserted and committed");
	}

	private void prepareInsert() throws SQLException {
		if (insertStatement == null) {
			insertStatement = conn.prepareStatement(insertText);
		}

	}

	private void prepareUpdate() throws SQLException {
		if (updateStatement == null) {
			updateStatement = conn.prepareStatement(updateText);
		}
	}
}
