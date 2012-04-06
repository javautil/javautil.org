package org.javautil.address.service;

import java.io.File;

import org.javautil.commandline.annotations.Optional;
import org.javautil.commandline.annotations.Required;
import org.javautil.commandline.annotations.RequiredUnless;

/**
 * <p>
 * AddressValidationServiceArguments class.
 * </p>
 * 
 * @author jjs
 * @version $Id: AddressValidationServiceArguments.java,v 1.2 2012/03/04
 *          12:31:18 jjs Exp $
 */
public class AddressValidationServiceArguments {

	@RequiredUnless(property = "noDatasource")
	private String inputFileName;

	@RequiredUnless(property = "noDatasource")
	private String outputFileName;

	@Optional
	private Boolean noDatasource;

	@RequiredUnless(property = "noDatasource")
	// @Required
	private String dataSourceName;

	@Required
	private Integer runNbr;

	@Optional
	private File configFile;

	@Optional
	private String geoCode;

	@Required
	private String uspsAcct;

	@RequiredUnless(property = "noDatasource")
	// @Required
	private String dataSourceFile;

	/**
	 * <p>
	 * Getter for the field <code>configFile</code>.
	 * </p>
	 * 
	 * @return a {@link java.io.File} object.
	 */
	public File getConfigFile() {
		return configFile;
	}

	/**
	 * <p>
	 * Getter for the field <code>dataSourceFile</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getDataSourceFile() {
		return dataSourceFile;
	}

	/**
	 * <p>
	 * Getter for the field <code>dataSourceName</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getDataSourceName() {
		return dataSourceName;
	}

	/**
	 * <p>
	 * Getter for the field <code>geoCode</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getGeoCode() {
		return geoCode;
	}

	/**
	 * <p>
	 * Getter for the field <code>noDatasource</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.Boolean} object.
	 */
	public Boolean getNoDatasource() {
		return noDatasource;
	}

	/**
	 * <p>
	 * Getter for the field <code>runNbr</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.Integer} object.
	 */
	public Integer getRunNbr() {
		return runNbr;
	}

	/**
	 * <p>
	 * Getter for the field <code>uspsAcct</code>.
	 * </p>
	 * 
	 * @return a {@link java.lang.String} object.
	 */
	public String getUspsAcct() {
		return uspsAcct;
	}

	/**
	 * <p>
	 * isGeoCode.
	 * </p>
	 * 
	 * @return a boolean.
	 */
	public boolean isGeoCode() {
		boolean rc = false;
		if (geoCode != null && geoCode.equalsIgnoreCase("true")) {
			rc = true;
		}
		return rc;
	}

	/**
	 * <p>
	 * isNoDatasource.
	 * </p>
	 * 
	 * @return a boolean.
	 */
	public boolean isNoDatasource() {
		return Boolean.TRUE.equals(noDatasource);
	}

	/**
	 * <p>
	 * Setter for the field <code>configFile</code>.
	 * </p>
	 * 
	 * @param configFile
	 *            a {@link java.io.File} object.
	 */
	public void setConfigFile(final File configFile) {
		this.configFile = configFile;
	}

	/**
	 * <p>
	 * Setter for the field <code>dataSourceFile</code>.
	 * </p>
	 * 
	 * @param dataSourceFile
	 *            a {@link java.lang.String} object.
	 */
	public void setDataSourceFile(final String dataSourceFile) {
		this.dataSourceFile = dataSourceFile;
	}

	/**
	 * <p>
	 * Setter for the field <code>dataSourceName</code>.
	 * </p>
	 * 
	 * @param dataSourceName
	 *            a {@link java.lang.String} object.
	 */
	public void setDataSourceName(final String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	/**
	 * <p>
	 * Setter for the field <code>geoCode</code>.
	 * </p>
	 * 
	 * @param geoCode
	 *            a {@link java.lang.String} object.
	 */
	public void setGeoCode(final String geoCode) {
		this.geoCode = geoCode;
	}

	/**
	 * <p>
	 * Setter for the field <code>noDatasource</code>.
	 * </p>
	 * 
	 * @param noDatasource
	 *            a {@link java.lang.Boolean} object.
	 */
	public void setNoDatasource(final Boolean noDatasource) {
		this.noDatasource = noDatasource;
	}

	/**
	 * <p>
	 * Setter for the field <code>runNbr</code>.
	 * </p>
	 * 
	 * @param runNbr
	 *            a {@link java.lang.Integer} object.
	 */
	public void setRunNbr(final Integer runNbr) {
		this.runNbr = runNbr;
	}

	/**
	 * <p>
	 * Setter for the field <code>uspsAcct</code>.
	 * </p>
	 * 
	 * @param uspsAcct
	 *            a {@link java.lang.String} object.
	 */
	public void setUspsAcct(final String uspsAcct) {
		this.uspsAcct = uspsAcct;
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	// public void processArguments(String[] args) {
	// CommandLineHandler clh = new CommandLineHandler(this);
	// clh.parse(args);
	// }
}
