package org.javautil.address.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.javautil.address.UtAddressValidate;
import org.javautil.address.marshaller.UtAddrValidateCsvMarshaller;
import org.javautil.persistence.PersistenceException;

/**
 * <p>
 * AddressValidationCsvPersister class.
 * </p>
 * 
 * @author jjs
 * @version $Id: AddressValidationCsvPersister.java,v 1.2 2012/03/04 12:31:14
 *          jjs Exp $
 */
public class AddressValidationCsvPersister implements AddressPersistence {

	private UtAddrValidateCsvMarshaller inputMarshaller = new UtAddrValidateCsvMarshaller();
	private UtAddrValidateCsvMarshaller outputMarshaller = new UtAddrValidateCsvMarshaller();

	/** {@inheritDoc} */
	@Override
	public void getForRunNbr(long runNbr) throws PersistenceException {
		throw new UnsupportedOperationException(
				"Not supported for non database operations at this time");

	}

	public void afterPropertiesSet() {
		if (inputMarshaller == null) {
			throw new IllegalArgumentException("inputMarshaller is null");
		}

		if (outputMarshaller == null) {
			throw new IllegalArgumentException("outputMarshaller is null");
		}
	}

	public void setInputFile(File inFile) {
		if (inFile == null) {
			throw new IllegalArgumentException("inFile is null");
		}
		inputMarshaller.setInputFileName(inFile.getPath());
	}

	public void setOutputFile(File outFile) {
		if (outFile == null) {
			throw new IllegalArgumentException("outFile is null");
		}
		outputMarshaller.setOutputFileName(outFile.getPath());
	}

	/** {@inheritDoc} */
	@Override
	public ArrayList<UtAddressValidate> getNext(int count)
			throws PersistenceException {
		ArrayList<UtAddressValidate> retval = new ArrayList<UtAddressValidate>();
		for (int i = 0; i < count; i++) {
			UtAddressValidate record = getNext();
			if (record == null) {
				break;
			} else {
				retval.add(record);
			}
		}
		return retval;
	}

	/** {@inheritDoc} */
	@Override
	public UtAddressValidate getNext() throws PersistenceException {
		return inputMarshaller.getNext();
	}

	/** {@inheritDoc} */
	@Override
	public void insert(Iterable<UtAddressValidate> rows) {
		for (UtAddressValidate row : rows) {
			outputMarshaller.write(row);
		}

	}

	/** {@inheritDoc} */
	@Override
	public ArrayList<UtAddressValidate> update(
			Collection<UtAddressValidate> addresses) {
		throw new UnsupportedOperationException("Not supported");
	}

	/** {@inheritDoc} */
	@Override
	public void update(UtAddressValidate a) throws PersistenceException {
		throw new UnsupportedOperationException("Not supported");

	}

	/** {@inheritDoc} */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public UtAddrValidateCsvMarshaller getInputMarshaller() {
		return inputMarshaller;
	}

	public void setInputMarshaller(UtAddrValidateCsvMarshaller inputMarshaller) {
		this.inputMarshaller = inputMarshaller;
	}

	public UtAddrValidateCsvMarshaller getOutputMarshaller() {
		return outputMarshaller;
	}

	public void setOutputMarshaller(UtAddrValidateCsvMarshaller outputMarshaller) {
		this.outputMarshaller = outputMarshaller;
	}
}
