package org.javautil.address.marshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.javautil.address.UtAddressValidate;
import org.javautil.address.dao.AddressPersistence;
import org.javautil.address.interfaces.Address;
import org.javautil.address.interfaces.AuthoritativeAddress;
import org.javautil.address.interfaces.StandardizedAddress;
import org.javautil.persistence.PersistenceException;
import org.javautil.text.CSVWriter;
import org.javautil.text.CsvReader;
import org.javautil.text.SimpleDateFormatter;

public class UtAddrValidateCsvMarshaller implements AddressPersistence {

	private final Logger logger = Logger.getLogger(getClass());

	private CSVWriter outputMarshaller = new CSVWriter();
	private CsvReader inputMarshaller;
	private final boolean emitHeadings = true;
	private final SimpleDateFormatter dateFormatter = new SimpleDateFormatter(
			"yyyy/MM/dd");

	public void setInputFileName(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("fileName is null");
		}
		new File(fileName);

		try {
			FileInputStream fw = new FileInputStream(fileName);
			inputMarshaller = new CsvReader(fw);
			if (emitHeadings) {
				inputMarshaller.readLine();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Object[] read() {
		if (inputMarshaller == null) {
			throw new IllegalStateException("inputMarshaller is null");
		}
		List<String> objects;
		try {
			objects = inputMarshaller.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Object[] retval = objects == null ? null : objects.toArray();
		return retval;
	}

	public void setOutputFileName(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("fileName is null");
		}
		new File(fileName);

		try {
			FileWriter fw = new FileWriter(fileName);
			outputMarshaller = new CSVWriter(fw);
			outputMarshaller.setEmitEmptyStringsAsNull(true);
			if (emitHeadings) {
				outputMarshaller.write(getHeadings());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public void write(UtAddressValidate rec) {
		Object[] fields = toObjectArray(rec);
		try {
			outputMarshaller.write(fields);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Object[] getHeadings() {
		Object[] headings = new Object[] {
				"UtAddrValidateNbr",
				"RunNbr",
				"DataSrcNbr",
				"DataSrcPk",
				"Name", //
				"Raw Address1",
				"Raw Address2",
				"Raw City",
				"Raw State",
				"Raw CountryCode",
				"Raw PostalCode",
				//
				"Std PoBox", "Std StreetName", "Std StreetNumber",
				"Std StreetType", "Std SubunitCode",
				"Std SubunitType",
				"Std Address1",
				"Std Address2",
				"Std City",
				"Std State",
				"Std PostalCode",
				//
				"Auth Address1", "Auth Address2", "Auth City",
				"Auth State",
				"Auth PostalCode",
				"Auth Latitude",
				"Auth Longitude",
				"Auth AuthoritativeErrorMessage", //
				"StdAuth Address1", "StdAuth Address2", "StdAuth City",
				"StdAuth State", "StdAuth PostalCode",
				"StdAuth StandardizationTime",
				"StdAuth StandardizationErrorMessage", "AuthRqstCd" };
		return headings;

	}

	@Override
	public void getForRunNbr(long runNbr) throws PersistenceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public ArrayList<UtAddressValidate> getNext(int count)
			throws PersistenceException {
		UtAddressValidate rec;
		ArrayList<UtAddressValidate> recs = new ArrayList<UtAddressValidate>();
		for (int i = 0; i < count && ((rec = getNext()) != null); i++) {
			recs.add(rec);
		}
		return recs;
	}

	@Override
	public UtAddressValidate getNext() {
		UtAddressValidate record = null;
		Object[] objects = read();
		if (objects != null) {
			record = toUtAddressValidate(objects);
		}
		return record;
	}

	/**
	 * Closes both the input and output marshallers.
	 * 
	 * @param addr
	 * @return
	 */
	public Object[] toObjectArray(UtAddressValidate addr) {
		Object objects[] = new Object[] {
				addr.getUtAddrValidateNbr(),
				addr.getRunNbr(),
				addr.getDataSrcNbr(),
				addr.getDataSrcPk(),
				addr.getName(),
				//
				addr.getRawAddress().getAddress1(),
				addr.getRawAddress().getAddress2(),
				addr.getRawAddress().getCity(),
				addr.getRawAddress().getState(),
				addr.getRawAddress().getCountryCode(),
				addr.getRawAddress().getPostalCode(),
				//
				addr.getStdAddress().getPoBox(),
				addr.getStdAddress().getStreetName(),
				addr.getStdAddress().getStreetNumber(),
				addr.getStdAddress().getStreetType(),
				addr.getStdAddress().getSubunitCode(),
				addr.getStdAddress().getSubunitType(),
				addr.getStdAddress().getAddress1(),
				addr.getStdAddress().getAddress2(),
				addr.getStdAddress().getCity(),
				addr.getStdAddress().getState(),
				addr.getStdAddress().getPostalCode(),
				//
				addr.getAuthAddress().getAddress1(),
				addr.getAuthAddress().getAddress2(),
				addr.getAuthAddress().getCity(),
				addr.getAuthAddress().getState(),
				addr.getAuthAddress().getPostalCode(),
				addr.getAuthAddress().getLatitude(),
				addr.getAuthAddress().getLongitude(),
				addr.getAuthAddress().getAuthoritativeErrorMessage(),
				//
				addr.getStdAuthAddress().getAddress1(),
				addr.getStdAuthAddress().getAddress2(),
				addr.getStdAuthAddress().getCity(),
				addr.getStdAuthAddress().getState(),
				addr.getStdAuthAddress().getPostalCode(),
				addr.getStdAuthAddress().getStandardizationTime(),
				addr.getStdAuthAddress().getStandardizationErrorMessage(),
				addr.getAuthRqstCd() };
		return objects;
	}

	public UtAddressValidate toUtAddressValidate(Object[] objects) {
		int index = 0;
		UtAddressValidate addr = new UtAddressValidate();
		addr.setUtAddrValidateNbr(new Long((String) objects[index++]));
		addr.setRunNbr(new Integer((String) objects[index++]));
		addr.setDataSrcNbr(new Integer((String) objects[index++]));
		addr.setDataSrcPk(new Long((String) objects[index++]));
		addr.setName((String) objects[index++]);
		//
		Address raw = addr.getRawAddress();
		raw.setAddress1(getString(objects, "Raw Address1", index++));
		raw.setAddress2((String) objects[index++]);
		raw.setCity((String) objects[index++]);
		raw.setState((String) objects[index++]);
		raw.setCountryCode((String) objects[index++]);
		raw.setPostalCode((String) objects[index++]);
		//
		StandardizedAddress std = addr.getStdAddress();
		std.setPoBox((String) objects[index++]);
		std.setStreetName((String) objects[index++]);
		std.setStreetNumber((String) objects[index++]);
		std.setStreetType((String) objects[index++]);
		std.setSubunitCode((String) objects[index++]);
		std.setSubunitType((String) objects[index++]);
		std.setAddress1(getString(objects, "Std Address1", index++));
		std.setAddress2(getString(objects, "Std Address2", index++));
		std.setCity((String) objects[index++]);
		std.setState((String) objects[index++]);
		std.setPostalCode((String) objects[index++]);
		//
		AuthoritativeAddress auth = addr.getAuthAddress();
		auth.setAddress1(getString(objects, "Auth Address1", index++));
		auth.setAddress2(getString(objects, "Auth Address2", index++));
		auth.setCity(getString(objects, "Auth City", index++));
		auth.setState(getString(objects, "Auth State", index++));
		auth.setPostalCode(getString(objects, "Auth Postal Code", index++));
		auth.setLatitude(getDouble(objects, "Auth Latitude", index++));
		auth.setLongitude(getDouble(objects, "Auth Longitude", index++));
		auth.setAuthoritativeErrorMessage(getString(objects,
				"Authoritative Error Message", index++));
		//
		StandardizedAddress authstd = addr.getStdAuthAddress();
		authstd.setAddress1(getString(objects, "AuthStd address1", index++));
		authstd.setAddress2(getString(objects, "AuthStd address2", index++));
		authstd.setCity(getString(objects, "AuthStd city", index++));
		authstd.setState(getString(objects, "AuthStd state", index++));
		authstd.setPostalCode(getString(objects, "AuthStd Postal Cd", index++));

		Date d = getDate(objects, "StdAuth Standardization Time", index++);
		if (d != null) {
			addr.setStdTs(new Timestamp(d.getTime()));
		}

		authstd.setStandardizationErrorMessage(getString(objects,
				"Auth Std Err Msg", index++));
		addr.setAuthRqstCd(getString(objects, "AuthRqstCd", index++));
		return addr;

	}

	String getString(Object[] objects, String fieldName, int index) {
		String retval = null;
		if (index < objects.length) {
			retval = (String) objects[index];

			if (logger.isDebugEnabled()) {
				logger.debug("Field: " + fieldName + " Index: " + index
						+ " Value: '" + retval + "'");
			}
		}
		return retval;
	}

	Double getDouble(Object[] objects, String fieldName, int index) {

		Double retval = null;
		if (index < objects.length) {
			String fieldValue = null;
			fieldValue = (String) objects[index];
			if (fieldValue != null && fieldValue.length() > 0) {
				retval = new Double(fieldValue);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Field: " + fieldName + " Index: " + index
						+ " Value: '" + retval + "'");
			}
		}
		return retval;
	}

	Date getDate(Object[] objects, String fieldName, int index) {
		Date date = null;
		if (index < objects.length) {
			String s = ((String) objects[index]);
			if (s != null && s.length() > 0) {
				try {
					date = dateFormatter.parse(s);
				} catch (ParseException e) {
					throw new RuntimeException("while parsing '" + s + "'", e);
				}
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Field: " + fieldName + " Index: " + index
						+ " Value: '" + s + "'" + " Date: " + date);
			}
		}
		return date;
	}

	public String getHeaderString(UtAddressValidate rec) {
		StringBuilder builder = new StringBuilder();
		builder.append("\"utAddrValidateNbr\",");
		builder.append("\"runNbr\",");
		builder.append("\"dataSrcNbr\",");
		builder.append("\"dataSrcPk\",");
		builder.append("\"name\",");
		builder.append("\"rawAddr1\",");
		builder.append("\"rawAddr2\",");
		builder.append("\"rawAddr3\",");
		builder.append("\"rawCity\",");
		builder.append("\"rawStateCd\",");
		builder.append("\"rawCntryCd\",");
		builder.append("\"rawPostalCd\",");
		builder.append("\"stdPoBox\",");
		builder.append("\"stdStreetName\",");
		builder.append("\"stdStreetNbr\",");
		builder.append("\"stdStreetType\",");
		builder.append("\"stdSubunitCd\",");
		builder.append("\"stdSubunitType\",");
		builder.append("\"stdAddr1\",");
		builder.append("\"stdAddr2\",");
		builder.append("\"stdCity\",");
		builder.append("\"stdStateCd\",");
		builder.append("\"stdPostalCd\",");
		builder.append("\"authAddr1\",");
		builder.append("\"authAddr2\",");
		builder.append("\"authCity\",");
		builder.append("\"authStateCd\",");
		builder.append("\"authPostalCd\",");
		builder.append("\"authRqstCd\",");
		builder.append("\"authLatitude\",");
		builder.append("\"authLongitude\",");
		builder.append("\"stdAuthAddr1\",");
		builder.append("\"stdAuthAddr2\",");
		builder.append("\"stdAuthCity\",");
		builder.append("\"stdAuthStateCd\",");
		builder.append("\"stdAuthPostalCd\",");
		builder.append("\"stdTs\",");
		builder.append("\"stdErrMsg\",");
		builder.append("\"authErrMsg\",");
		builder.append("\"stdAuthErrMsg\",");
		builder.append("\"srcComment\",");
		builder.append("\"srcInvalidFlg\"");
		return builder.toString();
	}

	public void close() {
		if (outputMarshaller != null) {
			try {
				outputMarshaller.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if (inputMarshaller != null) {
			try {
				inputMarshaller.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}

	@Override
	public void insert(Iterable<UtAddressValidate> rows) throws SQLException {
		for (UtAddressValidate row : rows) {
			write(row);
		}

	}

	@Override
	public ArrayList<UtAddressValidate> update(
			Collection<UtAddressValidate> addresses)
			throws PersistenceException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(UtAddressValidate a) throws PersistenceException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void dispose() {
		try {
			outputMarshaller.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			inputMarshaller.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
