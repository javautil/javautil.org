package org.javautil.text;

import java.io.Serializable;

/**
 * 
 * @author jjs at dbexperts dot com
 * 
 */

// TODO document
public enum ToStringStyleFlags implements Serializable {

	/**
	 * Complement to USE_CLASS_NAME
	 * 
	 * @see #EMIT_CLASS_NAME
	 */
	DONT_EMIT_CLASS_NAME,
	/**
	 * todo this doesn't do anything
	 */
	DONT_EMIT_STATICS,
	/**
	 * 
	 */
	DONT_EMIT_TRANSIENTS,
	/**
	 * Complement to EMIT_FIELD_NAMES
	 * 
	 * @see #EMIT_FIELD_NAMES
	 */
	DONT_EMIT_FIELD_NAMES,
	/**
	 * 
	 */
	DONT_EMIT_IDENTITY_HASH_CODE,
	/**
	 * 
	 */
	DONT_USE_SHORT_CLASS_NAME,
	/**
	 * 
	 */
	EMIT_CLASS_NAME,
	/**
	 * Collection reading is much easier todo implement
	 */
	EMIT_COLLECTION_AS_CSV_PER_FIRST_ENTRY,
	/**
	 * TODO this doesn't do anything
	 */
	EMIT_STATICS,
	/**
	 * 
	 */
	EMIT_TRANSIENTS,
	/**
	 * If true, use the no time date format in the style.
	 */
	SUPPRESS_TIME,
	/**
	 * Use the shortened Date format if the date to be formatted is midnight.
	 * 
	 * Saves some space and makes the output more readable.
	 */
	SUPPRESS_TIME_IF_MIDNIGHT,
	/**
	 * 
	 */
	USE_DEFAULT_DATE_FORMAT,

	/**
	 * Prefix field names
	 * 
	 */
	EMIT_FIELD_NAMES,

	/**
	 * 
	 */
	EMIT_IDENTITY_HASH_CODE,
	/**
	 * 
	 */
	USE_ISO_DATE_TIME_FORMAT,
	/**
	 * 
	 */
	USE_SHORT_CLASS_NAME;

	// SHOW_ARRAY_CONTENT, DONT_SHOW_ARRAY_CONTENT

}
