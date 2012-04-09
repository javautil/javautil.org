package org.javautil.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
// TODO more documentation on this class and how it works - cvw
// todo camm need documentation
// todo every public method should throw only IllegalArgumentException or
// RuntimeException the rest are irrecoverable anyway
public class Crypto {
	// private static Logger logger = Logger.getLogger(Crypto.class);

	private static final String transformation = "DES/CBC/PKCS5Padding";

	private static final byte MASK = 0x01;

	public Crypto() {
	}

	// todo write some unit tests
	public static String encryptToHex(final String plainText,
			final String password) {

		return toHex(encrypt(plainText, fixPassword(password)));
	}

	// todo break into IllegalArgumentException and RuntimeException
	public static String decryptFromHex(final String hexText,
			final String password) {

		final byte[] bytes = decrypt(fromHexStringToByteArray(hexText),
				fixPassword(password));
		return new String(bytes);
	}

	public static byte[] encrypt(final String plainText, final String password) {

		final SecretKey key = getKey(fixPassword(password));
		final Cipher cipher = getEncryptionCipher(key);
		final byte[] bytes = encrypt(cipher, plainText);
		return bytes;

	}

	public static byte[] decrypt(final byte[] cipherText, final String password) {

		SecretKey key;
		try {
			key = getKey(fixPassword(password));
			final DataInputStream dis = new DataInputStream(
					new ByteArrayInputStream(cipherText));
			final IvParameterSpec vector = getIvParameterSpec(dis);
			final Cipher cipher = getDecryptionCipher(key, vector);

			final byte[] bytes = decryptStream(cipher, dis);
			return bytes;
		} catch (final InvalidKeyException e) {
			throw new IllegalArgumentException("Invalid key" + e.getMessage());

		} catch (final NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		} catch (final IOException e) {
			throw new IllegalStateException(e);
		} catch (final NoSuchPaddingException e) {
			throw new IllegalStateException(e);
		} catch (final InvalidAlgorithmParameterException e) {
			throw new IllegalStateException(e);
		}

	}

	/**
	 * Password must be at least 8 characters for DES, let's just pad it out
	 * regardless
	 * 
	 * @param password
	 * @return
	 */
	private static String fixPassword(final String password) {
		String retval = password;
		// if (password.length() < 8) {
		retval = password + "&(*(SDF.123,^";
		// }
		return retval;
	}

	private static SecretKey getKey(final String password) {
		final byte[] desKeyData = password.getBytes();
		DESKeySpec desKeySpec;
		try {
			desKeySpec = new DESKeySpec(desKeyData);
			final SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DES");
			final SecretKey desKey = keyFactory.generateSecret(desKeySpec);
			return desKey;
		} catch (final Exception e) {
			throw new RuntimeException(e);

		}

	}

	private static Cipher getEncryptionCipher(final SecretKey key)

	{
		if (key == null) {
			throw new IllegalArgumentException("key is null");
		}
		// use des
		final Cipher des;
		try {
			des = Cipher.getInstance(transformation);
			des.init(Cipher.ENCRYPT_MODE, key);
		} catch (final Exception e) {

			throw new RuntimeException(e);
		}
		return des;
	}

	private static Cipher getDecryptionCipher(final SecretKey key,
			final IvParameterSpec vector) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException {
		if (key == null) {
			throw new IllegalArgumentException("key is null");
		}

		final Cipher des = Cipher.getInstance(transformation);
		des.init(Cipher.DECRYPT_MODE, key, vector);
		return des;
	}

	private static byte[] encrypt(final Cipher cipher, final String text) {
		final byte[] initializationVector = cipher.getIV();
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(initializationVector.length);

			dos.write(initializationVector);
			//
			final byte[] input = new byte[64];
			final ByteArrayInputStream bais = new ByteArrayInputStream(
					text.getBytes());
			byte[] output;
			//
			int bytesRead;
			while ((bytesRead = bais.read(input)) != -1) {
				output = cipher.update(input, 0, bytesRead);
				if (output != null) {
					baos.write(output);
				}
			}

			output = cipher.doFinal();
			if (output != null) {
				baos.write(output);
			}
			baos.flush();
			baos.close();

			final byte[] retval = baos.toByteArray();
			return retval;
		} catch (final IOException e) {
			throw new RuntimeException(e);
		} catch (final IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		} catch (final BadPaddingException e) {
			throw new RuntimeException(e);

		}
	}

	private static byte[] decryptStream(final Cipher cipher,
			final DataInputStream din) {
		final byte[] input = new byte[64];
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int bytesRead;
		try {
			while ((bytesRead = din.read(input)) != -1) {
				final byte[] output = cipher.update(input, 0, bytesRead);
				if (output != null) {
					baos.write(output);
				}
			}
			final byte[] output = cipher.doFinal();
			if (output != null) {
				baos.write(output);
			}
			din.close();
			baos.flush();
			baos.close();
			return baos.toByteArray();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		} catch (final IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		} catch (final BadPaddingException e) {
			throw new RuntimeException(e);
		}

	}

	private static IvParameterSpec getIvParameterSpec(final DataInputStream din)
			throws IOException {
		final int initializationVectorSize = din.readInt();
		final byte[] initializationVector = new byte[initializationVectorSize];
		din.readFully(initializationVector);
		final IvParameterSpec ivps = new IvParameterSpec(initializationVector);
		return ivps;
	}

	/**
	 * This nasty little hack only works because the vector length is less than
	 * 16M
	 * 
	 * @param bytes
	 * @return
	 */
	private static String toHex(final byte[] bytes) {
		if (bytes == null) {
			throw new IllegalArgumentException("bytes is null");
		}
		if (bytes.length < 1) {
			throw new IllegalArgumentException("empty array");
		}
		// make sure the first byte isn't zero, leading zeros get swallowed
		// we know that this always sets it
		bytes[0] = (byte) (bytes[0] ^ MASK);

		final BigInteger bi = new BigInteger(bytes);
		final String s = bi.toString(16);
		return s;

	}

	private static byte[] fromHexStringToByteArray(final String hex) {
		final BigInteger bi = new BigInteger(hex, 16);
		final byte[] b = bi.toByteArray();
		// reverse the leading zero op from to Hex
		b[0] = (byte) (b[0] ^ MASK);
		return b;

	}

}
