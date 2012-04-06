package org.javautil.mp3.ddl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

// TODO why?  Use a script or hibernate
public class CreateDatabaseObjectsJdbc implements CreateDatabaseObjects {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private DataSource datasource;

	static final String createText = "create table mp3( "
			+ " mp3_id number(9) not null,"
			+ " file_name varchar2(255) not null, " + "  bit_Rate  int, "
			+ "    album_Title varchar2(60), "
			+ "    song_title  varchar2(60), "
			+ "    author      varchar2(60), "
			+ "    artist_name varchar2(60), "
			+ "    genre       varchar2(60), "
			+ "    song_comment varchar2(60),"
			+ "    track        varchar2(8), "
			+ "    year_released varchar2(8)," + "    artist_id     number(9)"
			+ ")";

	static final String sequenceText = "create sequence mp3_id_seq";

	static final String pkText = "alter table mp3 add constraint mp3_pk primary key (file_name)";

	public CreateDatabaseObjectsJdbc() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.mp3.CreateDatabaseObjects#createObjects()
	 */
	@Override
	public void createObjects() {
		Statement s;
		try {
			final Connection conn = datasource.getConnection();
			s = conn.createStatement();
			s.execute(createText);
			logger.info("table created " + createText);
			s.execute(pkText);
			logger.info("pk created " + pkText);
			s.execute(sequenceText);
			logger.info("seq created " + sequenceText);
			conn.close();
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void dropObjects() {
		Statement s;
		try {
			final Connection conn = datasource.getConnection();
			s = conn.createStatement();
			String sql = null;
			try {
				s.execute(sql = "drop table mp3");
				logger.info(sql);
				s.execute(sql = "drop sequence mp3_id_seq");
				logger.info(sql);
			} catch (final SQLException sqe) {
				logger.warn(sqe.getMessage());
			}
			conn.close();
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * @return the dataSource
	 */
	public DataSource getDatasource() {
		return datasource;
	}

	/**
	 * @param dataSource
	 *            the dataSource to set
	 */
	public void setDatasource(final DataSource dataSource) {
		this.datasource = dataSource;
	}

}
