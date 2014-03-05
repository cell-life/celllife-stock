package org.celllife.stockout.application.service.user;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SecurityServiceUtils {


	/*
	public static String getCurrentSession() {
		SecurityContext context = SecurityContextHolder.getContext();

		if (context == null) {
			return null;
		}
		Authentication authentication = context.getAuthentication();

		try {
			return SessionRegistryUtils.obtainSessionIdFromAuthentication(authentication);
		} catch (IllegalArgumentException ex) {// Do nothing
			Object session = getCurrentDetails();
			if (session instanceof String)
				return session.toString();
		}
		return null;
	}

	public static Object getCurrentDetails() {
		SecurityContext context = SecurityContextHolder.getContext();

		if (context == null)
			return null;
		Authentication authentication = context.getAuthentication();

		if (authentication == null)
			return null;

		return authentication.getDetails();
	}
	*/

	
	/**
	 * This method will hash <code>strToEncode</code> using the preferred algorithm (SHA-1)
	 * 
	 * @param strToEncode string to encode
	 * @return the SHA-1 encryption of a given string
	 * @throws NoSuchAlgorithmException 
	 * @throws NoSuchAlgorithmException if the specified algorithm (SHA1) does not exist
	 * @throws UnsupportedEncodingException if the specified encoding (UTF-8) does not exist
	 */
	public static String encodeString(String strToEncode) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String algorithm = "SHA1";
		MessageDigest md = MessageDigest.getInstance(algorithm);
		byte[] input = strToEncode.getBytes("UTF-8");
		return hexString(md.digest(input));	
	}

	/**
	 * Used to generate password salts
	 * @return a secure random token.
	 */
	public static String getRandomToken() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Random rng = new Random();
		return encodeString(Long.toString(System.currentTimeMillis()) + Long.toString(rng.nextLong()));
	}
	
	/**
	 * Convenience method to convert a byte array to a string.
	 */
	private static String hexString(byte[] b) {
		StringBuffer buf = new StringBuffer();
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		int len = b.length;
		int high = 0;
		int low = 0;
		for (int i = 0; i < len; i++) {
			high = ((b[i] & 0xf0) >> 4);
			low = (b[i] & 0x0f);
			buf.append(hexChars[high]);
			buf.append(hexChars[low]);
		}

		return buf.toString();
	}
}
