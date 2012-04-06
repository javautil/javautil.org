package org.javautil.address;

// Todo make this the correct name

	public class UtAddressValidateHelper
	{

	        public void toArray( UtAddressValidate row)  {
	                Object [] fields = new Object [] {
	 row.getUtAddrValidateNbr(),
	 row.getRunNbr(),
	 row.getDataSrcNbr(),
	 row.getDataSrcPk(),
	 row.getName(),
	 row.getRawAddress().getAddress1(),
	 row.getRawAddress().getAddress2(),
	 row.getRawAddress().getCity(),
	 row.getRawAddress().getState(),
	 row.getRawAddress().getCountryCode(),
	 row.getRawAddress().getPostalCode(),
	 row.getStdAddress().getPoBox(),
	 row.getStdAddress().getStreetName(),
	 row.getStdAddress().getStreetNumber(),
	 row.getStdAddress().getStreetType(),
	 row.getStdAddress().getSubunitCode(),
	 row.getStdAddress().getSubunitType(),
	 row.getStdAddress().getAddress1(),
	 row.getStdAddress().getAddress2(),
	 row.getStdAddress().getCity(),
	 row.getStdAddress().getState(),
	 row.getStdAddress().getPostalCode(),
	 row.getAuthAddress().getAddress1(),
	 row.getAuthAddress().getAddress2(),
	 row.getAuthAddress().getCity(),
	 row.getAuthAddress().getState(),
	 row.getAuthAddress().getPostalCode(),
	 row.getAuthRqstCd(),
	 row.getAuthAddress().getLatitude(),
	 row.getAuthAddress().getLongitude(),
	 row.getStdAuthAddress().getAddress1(),
	 row.getStdAuthAddress().getAddress2(),
	 row.getStdAuthAddress().getCity(),
	 row.getStdAuthAddress().getState(),
	 row.getStdAuthAddress().getPostalCode(),
	 row.getStdTs(),
	 row.getStdAddress().getStandardizationErrorMessage(),
	 row.getAuthAddress().getAuthoritativeErrorMessage(),
	 row.getStdAuthAddress().getStandardizationErrorMessage()
	};
	        }
}
