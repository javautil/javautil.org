package org.javautil.mp3;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.javautil.commandline.CommandLineHandler;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.DataType;
import org.javautil.dataset.Dataset;
import org.javautil.dataset.DatasetMetadataImpl;
import org.javautil.dataset.MatrixDataset;
import org.javautil.dataset.MutableDatasetMetadata;
import org.javautil.dataset.csv.DatasetCsvMarshaller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ArtistCsvListExtractor {
	private final Logger logger = Logger.getLogger(getClass());

	private SessionFactory sessionFactory;

	private final String sql = "select artist_name, " + "count(*) ref_count "
			+ "from mp3 " + "group by artist_name " + "order by artist_name";

	private ArtistCsvExtractorSpringArguments arguments;

	public ArtistCsvListExtractor() {

	}

	public ArtistCsvListExtractor(
			final ArtistCsvExtractorSpringArguments arguments) {
		this.arguments = arguments;
	}

	public void afterPropertiesSet() {
		if (sessionFactory == null) {
			throw new IllegalStateException("sessionFactory is null");
		}
		if (arguments == null) {
			throw new IllegalStateException("arguments is null");
		}
	}

	List<Object[]> executeQuery() {
		final Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		final SQLQuery query = session.createSQLQuery(sql);
		// TODO and then we have the Spring version
		final List<Object[]> rowList = query.list();
		session.close();
		return rowList;

	}

	MutableDatasetMetadata getMetadata() {
		final ColumnMetadata artistNameMeta = new ColumnMetadata("Artist Name",
				DataType.STRING);
		final ColumnMetadata refMeta = new ColumnMetadata("Ref Count",
				DataType.NUMERIC);
		final ArrayList<ColumnMetadata> metaColumns = new ArrayList<ColumnMetadata>();
		metaColumns.add(artistNameMeta);
		metaColumns.add(refMeta);
		final DatasetMetadataImpl meta = new DatasetMetadataImpl(metaColumns);
		return meta;
	}

	public void process() {
		afterPropertiesSet();
		final List<Object[]> rows = executeQuery();
		final Dataset dataset = new MatrixDataset(getMetadata(), rows);
		try {
			DatasetCsvMarshaller
					.writeToFile(dataset, arguments.getOutputFile());
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(final String[] args) {
		final ArtistCsvExtractorSpringArguments argHandler = new ArtistCsvExtractorSpringArguments();
		final CommandLineHandler clh = new CommandLineHandler(argHandler);
		clh.evaluateArguments(args);
		final ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				argHandler.getApplicationContextFile().getPath());
		final ArtistCsvListExtractor extractor = (ArtistCsvListExtractor) applicationContext
				.getBean("extractor");
		extractor.setArguments(argHandler);
		extractor.process();
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @return the arguments
	 */
	public ArtistCsvExtractorSpringArguments getArguments() {
		return arguments;
	}

	/**
	 * @param arguments
	 *            the arguments to set
	 */
	public void setArguments(final ArtistCsvExtractorSpringArguments arguments) {
		this.arguments = arguments;
	}

}
