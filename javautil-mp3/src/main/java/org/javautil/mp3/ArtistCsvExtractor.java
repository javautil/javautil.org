package org.javautil.mp3;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.javautil.commandline.CommandLineHandler;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DisassociatedResultSetDataset;
import org.javautil.dataset.csv.DatasetCsvMarshaller;
import org.javautil.jdbc.datasources.SimpleDatasourcesFactory;

public class ArtistCsvExtractor {
	private final Logger logger = Logger.getLogger(getClass());
	private final String sql = "select upper(artist_name), "
			+ "count(*) song_count " + "from mp3 "
			+ "group by upper(artist_name) " + "order by upper(artist_name)";

	private DataSource datasource;

	private File outputFile;

	public ArtistCsvExtractor() {

	}

	public ArtistCsvExtractor(final ArtistCsvExtractorArguments args) {
		this.datasource = SimpleDatasourcesFactory.getDataSource(args
				.getDatasourceName());
		this.outputFile = args.getOutputFile();
	}

	public void afterPropertiesSet() {
		if (datasource == null) {
			throw new IllegalArgumentException("datasource is null");
		}
		if (outputFile == null) {
			throw new IllegalArgumentException("outputFile is null");
		}
	}

	public void process() {
		final Dataset dataset = getDataset();
		try {
			DatasetCsvMarshaller.writeToFile(dataset, outputFile);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Dataset getDataset() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rset;
		try {
			conn = datasource.getConnection();
			ps = conn.prepareStatement(sql);
			rset = ps.executeQuery();
			// rset = ps.getResultSet();
			final Dataset dataset = DisassociatedResultSetDataset
					.getDataset(rset);
			return dataset;
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (final SQLException e) {
					logger.error(e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (final SQLException e) {
					logger.error(e);
				}
			}

		}

	}

	public static void main(final String[] args) {
		final ArtistCsvExtractorArguments argHandler = new ArtistCsvExtractorArguments();
		final CommandLineHandler clh = new CommandLineHandler(argHandler);
		clh.evaluateArguments(args);
		final ArtistCsvExtractor extractor = new ArtistCsvExtractor(argHandler);
		extractor.process();
	}

}
