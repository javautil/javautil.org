package org.javautil.text;

import static org.javautil.text.ToStringStyleFlags.DONT_EMIT_IDENTITY_HASH_CODE;
import static org.javautil.text.ToStringStyleFlags.DONT_EMIT_STATICS;
import static org.javautil.text.ToStringStyleFlags.DONT_EMIT_TRANSIENTS;
import static org.javautil.text.ToStringStyleFlags.USE_SHORT_CLASS_NAME;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Simple way to get an object formatted as a String.
 * 
 * Transients are by default not emitted but don't use the transient qualifier
 * as a means to suppress if you plan on using HashCodeBuilder or EqualsBuilder
 * to build a hash and the field is actually part of the identifier.
 * 
 * 
 * Configuration is primarily through the flags see
 * 
 * @see ToStringStyleFlags
 * @see ConfigurableToStringStyle
 * 
 *      Sample usage
 * 
 *      <pre>
 * <code>
 * AsString as = new AsString(DONT_USE_IDENTITY_HASH_CODE,SUPPRESS_TIME);
 * logger.debug(as.toString(object);
 * </code>
 * </pre>
 * 
 *      todo document default values for Configurable
 * 
 * @author jjs at dbexperts dot com
 */

public class AsString {
	private ConfigurableToStringStyle style = new ConfigurableToStringStyle(
			DONT_EMIT_IDENTITY_HASH_CODE, USE_SHORT_CLASS_NAME,
			DONT_EMIT_TRANSIENTS, DONT_EMIT_STATICS);

	// private ReflectionToStringBuilder builder = new
	// ReflectionToStringBuilder(style);

	/**
	 * Fields to be suppressed, use for passwords, etc.
	 */
	private String[] excludeFieldNames;

	public AsString(final ToStringStyleFlags... flags) {
		style.setFlags(flags);

	}

	public String toString(final Object object) {
		String string = null;
		// TODO should not instantiate every time
		if (excludeFieldNames != null) {
			final ReflectionToStringBuilder localBuilder = new ReflectionToStringBuilder(
					object, style, null, null, style.getEmitTransients(),
					style.getEmitStatics());
			localBuilder.setExcludeFieldNames(excludeFieldNames);
			string = localBuilder.toString();
		} else {
			string = ReflectionToStringBuilder.toString(object, style,
					style.getEmitTransients(), style.getEmitStatics());
		}
		return string;

	}

	public ConfigurableToStringStyle getStyle() {
		return style;
	}

	public void setStyle(final ConfigurableToStringStyle style) {
		this.style = style;
	}

	public String[] getExcludeFieldNames() {
		return excludeFieldNames;
	}

	public void setExcludeFieldNames(final String[] excludeFieldNames) {
		this.excludeFieldNames = excludeFieldNames;
	}
}
