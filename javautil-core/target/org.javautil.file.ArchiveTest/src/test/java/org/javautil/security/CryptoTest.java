package org.javautil.security;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * 
 * @author jjs
 * 
 */
public class CryptoTest {
	private final Logger logger = Logger.getLogger(getClass());

	private final String cipher = "PA$$WORD";

	@Test
	public void testCrypto() {
		// TODO new term
		final String encryptThis = "FOCUS10";

		logger.debug("Attempting to encrypt '" + encryptThis
				+ "' to hex with the cipher " + cipher);

		final String encryptedValue = Crypto.encryptToHex(encryptThis, cipher);

		logger.info("Encrypted value: " + encryptedValue);

		logger.info("Decrypting with the cipher " + cipher);
		final String decrypted = Crypto.decryptFromHex(encryptedValue, cipher);
		logger.info("Decrypted value: " + decrypted);
	}

}
