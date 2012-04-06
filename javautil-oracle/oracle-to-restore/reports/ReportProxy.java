package com.dbexperts.oracle.reports;



	import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

	public class ReportProxy {

		private String address ;
		
		private URL url ;
		
		public InputStream getInputStream(String address) throws IOException {
			this.address = address;
			this.url = new URL(address);
			URLConnection conn = url.openConnection();
			String type = conn.getContentType();
			InputStream is = url.openStream();
			if (! "application/pdf".equals(type)) { 
				logResponse(is);
			}	

			
			return is;
		}
		
		public void writeToOutputStream(String address, OutputStream os) throws IOException {
	
			InputStream is = getInputStream(address);
			int bytes;
			byte[] buff = new byte[8192];
			while ((bytes = is.read(buff)) > -1) {
				os.write(buff,0,bytes);
			}
			os.flush();
			
			is.close();
			os.close();
		}
		
	
	
	
	private void logResponse(InputStream is) throws IOException {
		byte[] buff = new byte[8192];
		int bytes;
		ByteArrayOutputStream baos  = new ByteArrayOutputStream();
		while ((bytes =  is.read(buff)) > -1) {
			baos.write(buff,0,bytes);
		}
		is.close();
		throw new IllegalStateException(baos.toString());
	}
}
