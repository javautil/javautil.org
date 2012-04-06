package org.javautil.mp3.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.javautil.mp3.ArtistSuspect;
import org.javautil.mp3.Mp3;
import org.javautil.mp3.Mp3Metadata;
import org.javautil.mp3.ddl.CreateDatabaseObjects;
import org.javautil.mp3.formatter.Mp3MetadataAccess;
import org.javautil.mp3.formatter.Mp3MetadataAccessor;
import org.springframework.beans.factory.InitializingBean;

public class Mp3PersisterJdbc extends AbstractPersistence implements
		Mp3Persistence, InitializingBean {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private Connection conn;

	private PreparedStatement insertStatement;

	// TODO should do batching.

	private CreateDatabaseObjects ddlRunner;
	// TODO figure out what to do with this
	//
	private final Mp3MetadataAccessor accessor = new Mp3MetadataAccess();
	// TODO do one with bind names
	private final String insertText = "insert into mp3("
			+ "mp3_id,"
			+ "file_name, "
			// "bit_rate, "
			+ " album_title, song_title, "
			+ "author,    artist_name,   genre,       song_comment, "
			+ "track, year_released) values (mp3_id_seq.nextval,?,?,?,?,?,?,?,?,?)";

	private final String updateText = "update mp3 " + "set  album_title = ?, "
			+ "song_title = ?, " + "author = ?,    " + "artist_name = ?,   "
			+ "genre = ?,       " + "song_comment = ?, " + "track = ?, "
			+ "year_released = ? " + "where file_name = ?";

	private final String selectText = "select mp3_id, file_name, bit_rate, album_title, song_title, author "
			+ " artist, genre, song_comment, track, year_released from mp3";

	private final String byTitle = " where song_title = ?";

	private final int maxFileNameLength = 255;

	private final int maxTagLength = 60;

	private PreparedStatement updateStatement;

	private DataSource datasource;

	public Mp3PersisterJdbc() {

	}

	public Mp3PersisterJdbc(final Connection conn) {
		if (conn == null) {
			throw new IllegalArgumentException("conn was null");
		}
		this.conn = conn;
	}

	@Override
	public void afterPropertiesSet() {
		if (datasource == null) {
			throw new IllegalStateException("datasource is null");
		}

		try {
			conn = datasource.getConnection();
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}

		if (ddlRunner != null) {
			ddlRunner.createObjects();
		}
	}

	/**
	 * @return the ddlRunner
	 */
	public CreateDatabaseObjects getDdlRunner() {
		return ddlRunner;
	}

	/**
	 * @param ddlRunner
	 *            the ddlRunner to set
	 */
	public void setDdlRunner(final CreateDatabaseObjects ddlRunner) {
		this.ddlRunner = ddlRunner;
	}

	public void insert(final Mp3Metadata metaData) {
		prepareInsert();
		accessor.setMetadata(metaData);
		final String fileName = accessor.getFileName();
		if ((fileName != null) && (fileName.length() > maxFileNameLength)) {
			throw new IllegalArgumentException("file name is longer than "
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

		try {
			insertStatement.setObject(1, fileName);
			insertStatement.setObject(2, albumTitle);
			insertStatement.setObject(3, songTitle);
			insertStatement.setObject(4, author);
			insertStatement.setObject(5, artist);
			insertStatement.setObject(6, genre);
			insertStatement.setObject(7, songComment);
			insertStatement.setObject(8, track);
			insertStatement.setObject(9, yearReleased);
			insertStatement.executeUpdate();
		} catch (final SQLException sqe) {
			throw new RuntimeException(sqe);
		}
	}

	public void update(final Mp3Metadata mp3data) throws SQLException {
		prepareUpdate();
		accessor.setMetadata(mp3data);
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
			// TODO what is this?
			throw new RuntimeException(sqe);
			// GenericSQLException gse = GenericSQLException.getGeneric(conn,
			// sqe);
			//
			// String state = sqe.getSQLState();
			// int errorCode = sqe.getErrorCode();
			//
			// logger.error(sqe.getMessage() + newline + "error code " +
			// errorCode + " while processing '" + fileName + "'" + newline
			// + "state: " + state);
			// throw sqe;
		}
		// logger.info("inserted and committed");
	}

	private void prepareInsert() {
		if (insertStatement == null) {
			try {
				insertStatement = conn.prepareStatement(insertText);
			} catch (final SQLException e) {
				throw new RuntimeException(e);
			}
		}

	}

	private void prepareUpdate() throws SQLException {
		if (updateStatement == null) {
			updateStatement = conn.prepareStatement(updateText);
		}
	}

	@Override
	public void save(final Mp3Metadata mp3data) {
		insert(mp3data);
	}

	@Override
	public void flushAndCommit() {
		try {
			conn.commit();
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void dispose() {
		try {

			commit();
			if (insertStatement != null) {
				insertStatement.close();
			}
			conn.close();
			conn = null;
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public void setDatasource(final DataSource datasource) {
		this.datasource = datasource;

	}

	@Override
	void commit() {
		try {
			conn.commit();
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}

	}

	Mp3 getRow(final ResultSet rset) {
		Mp3 mp3 = null;

		try {
			if (rset.next()) {
				mp3 = new Mp3();
				mp3.setMp3Id(rset.getInt("mp3_id"));
				mp3.setFileName(rset.getString("file_name"));
				mp3.setBitRate(rset.getInt("bit_rate"));
				mp3.setAlbumTitle(rset.getString("album_title"));
				mp3.setSongTitle(rset.getString("song_title"));
				mp3.setAuthor(rset.getString("author"));
				mp3.setArtistName(rset.getString("artist"));
				mp3.setGenre(rset.getString("genre"));
				mp3.setSongComment(rset.getString("song_comment"));
				mp3.setTrack(rset.getString("track"));
				mp3.setYearReleased(rset.getString("year_released"));
			}
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}
		return mp3;
	}

	Collection<Mp3> fetchPreparedStatement(final PreparedStatement ps) {
		final List<Mp3> results = new ArrayList<Mp3>();
		try {
			final ResultSet rset = ps.executeQuery();
			Mp3 mp3 = null;
			while ((mp3 = getRow(rset)) != null) {
				results.add(mp3);
			}
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (final SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return results;
	}

	// TODO discuss caching
	@Override
	public Collection<Mp3> getByTitle(final String songTitle) {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(selectText + byTitle);
			ps.setString(1, songTitle);
			return fetchPreparedStatement(ps);
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (final SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}

	}

	@Override
	public Collection<Mp3> getAll() {
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(selectText);
			return fetchPreparedStatement(ps);
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (final SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Override
	public List<ArtistSuspect> getArtistSuspects() {
		// TODO Auto-generated method stub
		return null;
	}

}
