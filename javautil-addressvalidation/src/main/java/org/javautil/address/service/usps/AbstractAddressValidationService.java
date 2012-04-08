package org.javautil.address.service.usps;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.javautil.address.USAddressStandardizer;
import org.javautil.address.UtAddressValidate;
import org.javautil.address.beans.StandardAddressBean;
import org.javautil.address.dao.AddressPersistence;
import org.javautil.address.exception.AddressStandardizationException;
import org.javautil.address.geo.AddressGeoRequest;
import org.javautil.address.geo.GeoProcessingException;
import org.javautil.address.interfaces.Address;
import org.javautil.address.interfaces.AuthoritativeAddress;
import org.javautil.address.interfaces.StandardizedAddress;
import org.javautil.address.service.AddressValidationServiceArguments;
import org.javautil.address.usps.AddressValidationException;
import org.javautil.address.usps.UspsValidationServicePropertyHelper;
import org.javautil.persistence.PersistenceAction;
import org.javautil.persistence.PersistenceException;
import org.javautil.util.EnvironmentProperty;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * Abstract AbstractAddressValidationService class.
 * </p>
 * 
 * @author jjs
 * @version $Id: AbstractAddressValidationService.java,v 1.2 2012/03/04 12:31:17
 *          jjs Exp $
 */
public abstract class AbstractAddressValidationService implements
		AddressValidationService {

	protected final Logger logger = Logger.getLogger(getClass());
	private static final String newline = System.getProperty("line.separator");
	private AddressValidationServiceArguments arguments;
	private ArrayList<UtAddressValidate> addresses = new ArrayList<UtAddressValidate>();
	private boolean hasNext = true;
	private final int batchSize = 5;
	@Autowired
	private AddressPersistence persister;
	private int processedCount;
	// private int uncommittedCount = 0;
	private UspsAddressValidationRequest validator;
	private final UspsValidationServicePropertyHelper helper = new UspsValidationServicePropertyHelper();
	protected boolean serviceMode = false;
	private final USAddressStandardizer standardizer = new USAddressStandardizer();
	private final ArrayList<UtAddressValidate> updateAuthList = new ArrayList<UtAddressValidate>();
	/**
	 * This is the account number provided by the USPS for their online
	 * validation service
	 */
	private String uspsURL;
	private ArrayList<Address> changedStandards = new ArrayList<Address>();
	private AddressGeoRequest geoRequest = null;
	private String geoUserId = null;
	private String geoPassword = null;
	private int uncommittedCount;
	private int commitRecordCount;

	static final String GEO_USER_ID = "GEO_USER_ID";

	static final String GEO_PASSWORD = "GEO_PASSWORD";

	/**
	 * <p>
	 * Constructor for AbstractAddressValidationService.
	 * </p>
	 */
	public AbstractAddressValidationService() {
		super();
	}

	// public Exception getException(final Exception e) {
	// return exception;
	// }

	private void init() // throws InvalidEnvironmentException
	{

		if (arguments.getUspsAcct() == null) {
			arguments.setUspsAcct(helper.getUserId());
			if (arguments.getUspsAcct() == null) {
				final String message = "Unable to determine USPS Account"; // should
																			// be
																			// in
																			// helper
				throw new IllegalStateException(message);
			}
		}
		if (uspsURL == null) {
			uspsURL = helper.getTestUrl();

		}
		validator = new UspsAddressValidationRequest(uspsURL,
				arguments.getUspsAcct());
		if (arguments.isGeoCode()) {
			geoUserId = EnvironmentProperty.getMandatoryProperty(GEO_USER_ID);
			geoPassword = EnvironmentProperty
					.getMandatoryProperty(GEO_PASSWORD);
			geoRequest = new AddressGeoRequest(geoUserId, geoPassword);
		}

	}

	/**
	 * <p>
	 * validateAddresses.
	 * </p>
	 * 
	 * @throws org.javautil.persistence.PersistenceException
	 *             if any.
	 * @throws org.javautil.address.usps.AddressValidationException
	 *             if any.
	 * @throws java$sql$SQLException
	 *             if any.
	 */
	public void validateAddresses() throws PersistenceException,
			AddressValidationException {

		while (hasNext) {
			addresses = persister.getNext(batchSize);
			if (addresses.size() < batchSize) {
				hasNext = false;
			}
			setUncommittedCount(getUncommittedCount() + addresses.size()); // TODO
																			// some
																			// records
																			// might
																			// not
																			// have
																			// been
																			// modified,
																			// do
																			// this
																			// in
																			// the
																			// update
																			// area
			processBatch();
			processedCount += addresses.size();
			conditionalCommit();
		}

		wrapUp();

	}

	/**
	 * <p>
	 * conditionalCommit.
	 * </p>
	 */
	protected void conditionalCommit() {
	}

	/**
	 * <p>
	 * Getter for the field <code>processedCount</code>.
	 * </p>
	 * 
	 * @return a int.
	 */
	public int getProcessedCount() {
		return processedCount;
	}

	/**
	 * <p>
	 * Setter for the field <code>processedCount</code>.
	 * </p>
	 * 
	 * @param processedCount
	 *            a int.
	 */
	public void setProcessedCount(int processedCount) {
		this.processedCount = processedCount;
	}

	// TODO consider merging with dispose
	/**
	 * <p>
	 * wrapUp.
	 * </p>
	 */
	protected abstract void wrapUp();

	/**
	 * Standardization is cheap. Standardize each address and add to
	 * changedStandards if it changed.
	 * 
	 * The change could be a result in changes to the raw address or the
	 * standardization code.
	 */
	private void standardize() {
		changedStandards = new ArrayList<Address>();
		updateAuthList.clear();
		// standardize every address and add to standards
		for (final UtAddressValidate addr : addresses) {
			final Address raw = addr.getRawAddress();
			// ugly but expedient
			final StandardizedAddress oldStd = addr.getStdAddress();
			final StandardAddressBean newStd = new StandardAddressBean(oldStd);

			try {
				standardizer.standardize(raw, newStd);
			} catch (final AddressStandardizationException msae) {
				newStd.setStandardizationErrorMessage(msae.getMessage());
			}
			if (!oldStd.equals(newStd)) {
				addr.setStdAddress(newStd);
				changedStandards.add(newStd);
				updateAuthList.add(addr);
				addr.setPersistenceAction(PersistenceAction.UPDATE);
				addr.setGetAuthoritive(true);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("After standardization: \n" + addr);
			}
		}
	}

	private void getAuthoritative() throws AddressValidationException {
		if (logger.isDebugEnabled()) {
			logger.debug("about to validate " + changedStandards.size()
					+ " addresses ");
		}
		final List<AuthoritativeAddress> authoritative = validator
				.validate(changedStandards);
		if (authoritative.size() != updateAuthList.size()) {
			final StringBuilder b = new StringBuilder();
			b.append("authoritive size " + authoritative.size()
					+ " updateAuthList.size() " + updateAuthList.size()
					+ " changedStandards.size " + changedStandards.size());
			b.append(" auth " + newline);
			for (final AuthoritativeAddress addr : authoritative) {
				b.append(addr);
				b.append(newline);
			}
			for (final UtAddressValidate uav : updateAuthList) {
				b.append(uav);
				b.append(newline);
			}
			throw new IllegalStateException("logic error " + b.toString());
		}
		int i = 0;
		for (final AuthoritativeAddress a : authoritative) {
			updateAuthList.get(i).setAuthAddress(a);
			i++;
		}
	}

	private void getGeoLocation() throws AddressValidationException {
		final Double ZERO = new Double(0.0);
		for (final UtAddressValidate addr : addresses) {
			final AuthoritativeAddress aa = addr.getAuthAddress();
			if (aa.getAuthoritativeErrorMessage() == null
					&& aa.getState() != null) {
				try {
					if (aa.getLatitude() == null) {
						if (addr.getStdAddress().getPoBox() == null) {
							geoRequest.setGeoInfo(aa);
						} else {
							// for now set lat and long to zero to mark as a
							// problem
							aa.setLatitude(ZERO);
							aa.setLongitude(ZERO);
							logger.debug("skipping geo");
						}
					}
				} catch (final GeoProcessingException e) {
					logger.warn("could not get geo for " + aa.getFormatted()); // for
																				// now
																				// the
																				// fact
																				// that
																				// geo
																				// spatial
																				// is
																				// null
																				// should
																				// be
																				// sufficient
				}
			}
		}
	}

	private void processBatch() throws AddressValidationException,
			PersistenceException {
		standardize();
		getAuthoritative();

		if (arguments.isGeoCode()) {
			getGeoLocation();
		}
		persistChanges();
		addresses.clear();
	}

	protected abstract void persistChanges();

	// This was originally a threaded thing.
	// TOOD should throw the exception
	/**
	 * <p>
	 * run.
	 * </p>
	 */
	public void run() {
		try {
			logger.info("starting");
			validateAddresses();
			logger.info("done");

		} catch (final PersistenceException e) {
			setException(e);
			logger.error(e.getMessage());
		} catch (final AddressValidationException e) {
			setException(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setException(final Exception e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.address.service.usps.AddressValidationService#process(org
	 * .javautil.address.service.AddressValidationServiceArguments)
	 */
	@Override
	public void process(final AddressValidationServiceArguments arguments)
			throws PersistenceException, AddressValidationException {
		this.arguments = arguments;

		init();
		validateAddresses();
	}

	/**
	 * <p>
	 * Getter for the field <code>geoPassword</code>.
	 * </p>
	 * 
	 * @return the geoPassword
	 */
	public String getGeoPassword() {
		return geoPassword;
	}

	/**
	 * <p>
	 * Setter for the field <code>geoPassword</code>.
	 * </p>
	 * 
	 * @param geoPassword
	 *            the geoPassword to set
	 */
	public void setGeoPassword(final String geoPassword) {
		this.geoPassword = geoPassword;
	}

	/**
	 * <p>
	 * Getter for the field <code>geoUserId</code>.
	 * </p>
	 * 
	 * @return the geoUserId
	 */
	public String getGeoUserId() {
		return geoUserId;
	}

	/**
	 * <p>
	 * Setter for the field <code>geoUserId</code>.
	 * </p>
	 * 
	 * @param geoUserId
	 *            the geoUserId to set
	 */
	public void setGeoUserId(final String geoUserId) {
		this.geoUserId = geoUserId;
	}

	/**
	 * <p>
	 * Getter for the field <code>uncommittedCount</code>.
	 * </p>
	 * 
	 * @return a int.
	 */
	public int getUncommittedCount() {
		return uncommittedCount;
	}

	/**
	 * <p>
	 * Setter for the field <code>uncommittedCount</code>.
	 * </p>
	 * 
	 * @param uncommittedCount
	 *            a int.
	 */
	public void setUncommittedCount(int uncommittedCount) {
		this.uncommittedCount = uncommittedCount;
	}

	/**
	 * <p>
	 * Getter for the field <code>validator</code>.
	 * </p>
	 * 
	 * @return a
	 *         {@link org.javautil.address.service.usps.UspsAddressValidationRequest}
	 *         object.
	 */
	public UspsAddressValidationRequest getValidator() {
		return validator;
	}

	/**
	 * <p>
	 * Setter for the field <code>validator</code>.
	 * </p>
	 * 
	 * @param validator
	 *            a
	 *            {@link org.javautil.address.service.usps.UspsAddressValidationRequest}
	 *            object.
	 */
	public void setValidator(UspsAddressValidationRequest validator) {
		this.validator = validator;
	}

	/**
	 * <p>
	 * Getter for the field <code>commitRecordCount</code>.
	 * </p>
	 * 
	 * @return a int.
	 */
	public int getCommitRecordCount() {
		return commitRecordCount;
	}

	/**
	 * <p>
	 * Setter for the field <code>commitRecordCount</code>.
	 * </p>
	 * 
	 * @param commitRecordCount
	 *            a int.
	 */
	public void setCommitRecordCount(int commitRecordCount) {
		this.commitRecordCount = commitRecordCount;
	}

	/**
	 * <p>
	 * Getter for the field <code>addresses</code>.
	 * </p>
	 * 
	 * @return a {@link java.util.ArrayList} object.
	 */
	public ArrayList<UtAddressValidate> getAddresses() {
		return addresses;
	}

	/**
	 * <p>
	 * Setter for the field <code>addresses</code>.
	 * </p>
	 * 
	 * @param addresses
	 *            a {@link java.util.ArrayList} object.
	 */
	public void setAddresses(ArrayList<UtAddressValidate> addresses) {
		this.addresses = addresses;
	}

	/**
	 * <p>
	 * Getter for the field <code>persister</code>.
	 * </p>
	 * 
	 * @return a {@link org.javautil.address.dao.AddressPersistence} object.
	 */
	public AddressPersistence getPersister() {
		return persister;
	}

	/**
	 * <p>
	 * Setter for the field <code>persister</code>.
	 * </p>
	 * 
	 * @param persister
	 *            a {@link org.javautil.address.dao.AddressPersistence} object.
	 */
	@Override
	public void setPersister(AddressPersistence persister) {
		this.persister = persister;
	}

}
