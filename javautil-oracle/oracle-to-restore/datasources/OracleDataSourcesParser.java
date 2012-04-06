package com.dbexperts.oracle.datasources;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.dbexperts.datasources.DataSourceInstantiationException;
import com.dbexperts.datasources.DataSourcesParser;
import com.dbexperts.datasources.DataSourcesParserInstantiationException;
import com.dbexperts.datasources.jaxb.DatasourceType;
import com.dbexperts.datasources.jaxb.DatasourcesType;
import com.dbexperts.datasources.jaxb.OracleConnectionType;
import com.dbexperts.jdbc.DataSources;
import com.dbexperts.oracle.datasource.OracleDataSourceWrapper;

//http://java.sun.com/developer/technicalArticles/WebServices/jaxb/
/**
 * Constructs DataSources from an XML file compliant with DataSources.xsd or from
 * or JAXB elements that correspond to such a file.
 *
 *
 * @see http://dbexperts.com/xsd/DataSources.xsd
 *
 * @author jjs
 */
public class OracleDataSourcesParser implements DataSourcesParser {

	private final DataSources dataSources = new DataSources();
	private final Logger logger = Logger.getLogger(getClass());
	private File configurationFile;
	private static final String newline = System.getProperty("line.separator");
	private static final long MILLION = 1000000L;
	public OracleDataSourcesParser() {

	}

	// todo blowup on duplicate source name
	public OracleDataSourcesParser(final File f) throws DataSourcesParserInstantiationException {
		try {
			processFile(f);
			dataSources.setConfigurationFile(f);
		} catch (final Exception e) {
			throw new DataSourcesParserInstantiationException(e);

		}
	}

	public void createDataSources(final JAXBElement<DatasourcesType> dsElement) throws DataSourceInstantiationException {
		final DatasourcesType dsType = dsElement.getValue();

		final List<DatasourceType> dss = dsType.getDatasource();
		for (final DatasourceType ds : dss) {
			final String dsName = ds.getName();
			final com.dbexperts.jdbc.JdbcDataSource d = new com.dbexperts.jdbc.JdbcDataSource();
			try {

				d.setName(ds.getName());

				d.setDriver(ds.getDriver().getProperty());
				d.setUrl(ds.getUrl().getProperty());
				d.setUsername(ds.getUsername().getProperty());
				d.setPassword(ds.getPassword().getProperty());
			} catch (final Exception iee) {
				throw new DataSourceInstantiationException("while processing '" + dsName + "' " + iee.getMessage());
			}

			dataSources.addDataSource(dsName, d);
		}

	}

	public void createOracleDataSources(final JAXBElement<DatasourcesType> dsElement) throws DataSourceInstantiationException {
		final DatasourcesType dsType = dsElement.getValue();

		final List<OracleConnectionType> dss = dsType.getOracleDataSource();
		for (final OracleConnectionType ds : dss) {
			try {
				final OracleDataSourceWrapper ods = new OracleDataSourceWrapper();
				final String dsName = ds.getName();
				ods.setName(dsName);
				ods.setConfigurationFile(configurationFile);
				final String driverType = ds.getDriverType();
				ods.setAbandonedConnectionTimeout(ds.getCacheProperties().getAbandonedConnectionTimeout());

				ods.setCallback(ds.getCacheProperties().getConnectionCallbackClassname());
				ods.setConnectionCacheName(ds.getName());
				ods.setConnectionCachingEnabled(true);
				ods.setConnectionWaitTimeout(ds.getCacheProperties().getConnectionWaitTimeout());
				ods.setInactivityTimeout(ds.getCacheProperties().getInactivityTimeout());
				ods.setInitialLimit(ds.getCacheProperties().getInitialLimit());
				ods.setLoginTimeout(ds.getCacheProperties().getLoginTimeout());
				ods.setLowerThresholdLimit(ds.getCacheProperties().getLowerThresholdLimit());
				ods.setMaxLimit(ds.getCacheProperties().getMaxLimit());
				ods.setMaxStatementsLimit(ds.getMaxStatementsLimit());

				//ods.setMinLimit(ds.getCacheProperties().getMinLimit());
				ods.setPassword(ds.getPassword().getProperty());
				ods.setPortNumber(ds.getPortNumber().getProperty());
				ods.setPropertyCheckInterval(ds.getCacheProperties().getPropertyCheckInterval());
				ods.setServerName(ds.getServerName().getProperty());
				ods.setServiceName(ds.getServiceName().getProperty());
				ods.setTimeToLiveTimeout(ds.getCacheProperties().getTimeToLiveTimeout());
				ods.setUser(ds.getUser().getProperty());

				ods.setValidateConnection(ds.getCacheProperties().isValidateConnection());

				dataSources.addDataSource(dsName, ods);
			} catch (final Exception e) {
				throw new DataSourceInstantiationException(e);
			}
		}

	}

	@SuppressWarnings( { "unchecked" })
	public void processFile(final File f) throws DataSourceInstantiationException {
		//    DatasourcesType dsp = new DatasourcesType();
		JAXBContext jc;
		try {
			this.configurationFile = f;
			final long startNano = System.nanoTime();
			// todo this is expensive make static
			jc = JAXBContext.newInstance("com.dbexperts.datasources.jaxb");
			final Unmarshaller unmarshaller = jc.createUnmarshaller();
			final long unmarshallerNano = System.nanoTime();
			final JAXBElement<DatasourcesType> dsElement = (JAXBElement<DatasourcesType>) unmarshaller.unmarshal(f);
			final long unmarshalledNano = System.nanoTime();

			if (logger .isDebugEnabled()) {
				final StringBuilder b = new StringBuilder();
				final long unmarshallerElapsedMilli = (unmarshallerNano - startNano) / MILLION;
				final long unmarshalledElapsedMilli = (unmarshalledNano - unmarshallerNano) / MILLION;
				b.append("unmarshaller instantiation milli " + unmarshallerElapsedMilli + newline);
				b.append("unmarshalledElapsed milli " + unmarshalledElapsedMilli + newline);
				final String message = b.toString();
				logger.debug(message);
			}
			createDataSources(dsElement);
			createOracleDataSources(dsElement);
		} catch (final JAXBException e) {
			throw new DataSourceInstantiationException(e);
		}

	}

	public DataSources getDataSources() {
		return dataSources;
	}
	//    /**
	//     * @param args the command line arguments
	//     * @throws InvalidEnvironmentException
	//     * @throws DataSourceInstantiationException
	//     */
	//    public static void main(String[] args) throws JAXBException, DataSourceInstantiationException {
	//    	DataSourcesParser parser = new DataSourcesParser();
	//    	parser.processFile(new File("/common/scratch/dataSources.xml"));
	//    }
}
