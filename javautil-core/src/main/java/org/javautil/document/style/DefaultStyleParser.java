package org.javautil.document.style;

import java.awt.Font;

import javax.swing.UIManager;

/**
 * A default implementation of StyleParser that creates styles evaluating style
 * definition values against enumerations and booleans. The parser will fail
 * fast at any unexpected property value. Null style property values are allowed
 * and considered unset.
 * 
 * This class is named DefaultStyleParser instead of StyleParserImpl because it
 * provides a default set of methods that can be easily overridden to add custom
 * features to alter the style parsing process.
 * 
 * @author bcm
 * 
 */
public class DefaultStyleParser implements StyleParser {

	private static final Font DEFAULT_FONT = UIManager.getFont("Label.font");

	@Override
	public Style parse(final StyleDefinition definition) {
		final StyleImpl style = new StyleImpl();
		setName(definition, style);
		setBackgroundColor(definition, style);
		setFontColor(definition, style);
		setFont(definition, style);
		setFontAttributes(definition, style);
		setFormatMask(definition, style);
		setHorizontalAlignment(definition, style);
		setVerticalAlignment(definition, style);
		setBorders(definition, style);
		return style;
	}

	protected void setBorders(final StyleDefinition definition,
			final StyleImpl style) {
		style.setBorders(SimpleBorder.parse(definition));
	}

	/**
	 * Sets the style name.
	 * 
	 * Sets the name on the style from the name on the definition.
	 * 
	 * @param definition
	 * @param style
	 */
	protected void setName(final StyleDefinition definition,
			final StyleImpl style) {
		style.setName(trim(definition.getName()));
	}

	/**
	 * Sets background color.
	 * 
	 * Override this method to assign the background color.
	 * 
	 * @param definition
	 * @param style
	 */
	protected void setBackgroundColor(final StyleDefinition definition,
			final StyleImpl style) {
		final String bgColor = trim(definition.getBackgroundColor());
		style.setBackgroundColor(bgColor == null ? ColorUtil.TRANSPARENT
				: ColorUtil.parseColor(bgColor));
	}

	/**
	 * Sets font color.
	 * 
	 * Override this method to assign the font color.
	 * 
	 * @param definition
	 * @param style
	 */
	protected void setFontColor(final StyleDefinition definition,
			final StyleImpl style) {
		final String fontColor = trim(definition.getFontColor());
		style.setFontColor(fontColor == null ? ColorUtil.TRANSPARENT
				: ColorUtil.parseColor(fontColor));
	}

	/**
	 * Sets font face, font height, font weight, font style.
	 * 
	 * Override this method to assign the font.
	 * 
	 * @param definition
	 * @param style
	 */
	protected void setFont(final StyleDefinition definition,
			final StyleImpl style) {

		// face
		String fontFace = DEFAULT_FONT.getFontName();
		if (definition.getFontFace() != null) {
			fontFace = trim(definition.getFontFace());
		}

		// size
		int fontHeight = DEFAULT_FONT.getSize();
		if (definition.getFontHeight() != null) {
			final String _fontHeight = trim(definition.getFontHeight());
			fontHeight = Integer.parseInt(_fontHeight);
		}

		final String _fontWeight = trim(upperCase(definition.getFontWeight()));
		int fontArg = Font.PLAIN;

		// weight
		if ("BOLD".equals(_fontWeight)) {
			fontArg = fontArg |= Font.BOLD;
		} else if ("NORMAL".equals(_fontWeight)) {
			// for completeness, even though no logic is performed
		} else if (_fontWeight != null) {
			throw new IllegalArgumentException("unexpected fontWeight value: "
					+ _fontWeight + "; NORMAL or BOLD is expected");
		}
		// style
		final String _fontStyle = trim(upperCase(definition.getFontStyle()));
		if (("ITALIC").equals(_fontStyle)) {
			fontArg = fontArg |= Font.ITALIC;
		} else if ("NORMAL".equals(_fontStyle)) {
			// for completeness, even though no logic is performed
		} else if (_fontStyle != null) {
			throw new IllegalArgumentException("unexpected fontStyle value: "
					+ _fontStyle + "; NORMAL or ITALIC is expected");
		}
		final Font font = new Font(fontFace, fontArg, fontHeight);
		style.setFont(font);
	}

	/**
	 * Sets underline and wordwrap font attributes.
	 * 
	 * todo jjs why is this named this?
	 * 
	 * Override this method to assign the underlin and wordwap font attributes
	 * 
	 * @param definition
	 * @param style
	 */
	protected void setFontAttributes(final StyleDefinition definition,
			final StyleImpl style) {
		final FontAttributesImpl attr = new FontAttributesImpl();
		// underline
		final String _underline = trim(upperCase(definition
				.getFontUnderlineStyle()));
		if (_underline != null) {
			attr.setUnderlineStyle(FontUnderlineStyle.valueOf(_underline)
					.toString());
		}
		// word wrap
		final String _wordWrap = trim(lowerCase(definition.getWordWrap()));
		if (_wordWrap != null) {
			boolean wordWrap = false;
			try {
				wordWrap = Boolean.parseBoolean(_wordWrap);
			} catch (final Exception e) {
				throw new IllegalArgumentException("wordWrap should be true "
						+ "or false");
			}
			attr.setWordWrap(String.valueOf(wordWrap));
		}
		style.setFontAttributes(attr);
	}

	/**
	 * Sets formats mask Override this method to assign the format mask
	 * 
	 * @param definition
	 * @param style
	 */
	protected void setFormatMask(final StyleDefinition definition,
			final StyleImpl style) {
		style.setFormatMask(trim(definition.getFormatMask()));
	}

	/**
	 * Sets horizontal alignment Override this method to assign the horizontal
	 * alignment
	 * 
	 * @param definition
	 * @param style
	 */
	protected void setHorizontalAlignment(final StyleDefinition definition,
			final StyleImpl style) {
		final String _halign = trim(upperCase(definition
				.getHorizontalAlignment()));
		style.setHorizontalAlignment(_halign == null ? null
				: HorizontalAlignment.valueOf(_halign));
	}

	/**
	 * Sets vertical alignment Override this method to assign the vertical
	 * alignment
	 * 
	 * @param definition
	 * @param style
	 */
	protected void setVerticalAlignment(final StyleDefinition definition,
			final StyleImpl style) {
		final String _valign = trim(upperCase(definition.getVerticalAlignment()));
		style.setVerticalAlignment(_valign == null ? null : VerticalAlignment
				.valueOf(_valign));
	}

	/**
	 * Ensure no white spaces surrounding value
	 * 
	 * @param string
	 * @return
	 */
	private String trim(final String string) {
		return string == null ? null : string.trim();
	}

	/**
	 * Converts String to upper case and replaces spaces with underscores.
	 * 
	 * Uppercase with null check, replacing spaces with underscore for purposes
	 * of equating values to constant values defined in enum types.
	 * 
	 * @param string
	 * @return
	 */
	private String upperCase(final String string) {
		return string == null ? null : string.toUpperCase().replace(' ', '_');
	}

	/**
	 * Lowercase with null check, replacing spaces with underscore for purposes
	 * of equating values to constant values defined in enum types.
	 * 
	 * @param string
	 * @return
	 */
	private String lowerCase(final String string) {
		return string == null ? null : string.toLowerCase().replace(' ', '_');
	}

}
