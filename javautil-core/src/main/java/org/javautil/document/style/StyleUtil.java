package org.javautil.document.style;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;

/**
 * Useful static helper methods for working with Style and StyleDefinition
 * objects. When used in conjunction with spring and a StyleParser, this class
 * provides methods for quickly creating styles from an external xml
 * configuration file.
 * 
 * @author bcm
 */
public abstract class StyleUtil {

	/***
	 * Reads definitions from a context, expecting all definitions to extends
	 * the StyleDefinition class.
	 * 
	 * @param context
	 * @return
	 */
	public static Set<StyleDefinition> getStyleDefinitionsFromContext(
			final ApplicationContext context) {
		if (context == null) {
			throw new IllegalArgumentException("applicationContext is null");
		}
		final Set<StyleDefinition> styleDefs = new LinkedHashSet<StyleDefinition>();
		final String[] names = context
				.getBeanNamesForType(StyleDefinition.class);
		for (final String name : names) {
			StyleDefinition styleDef = (StyleDefinition) context.getBean(name);
			try {
				styleDef = (StyleDefinition) styleDef.clone();
			} catch (final CloneNotSupportedException e) {
				throw new IllegalStateException(e);
			}
			styleDef.setName(name);
			styleDefs.add(styleDef);
		}
		return styleDefs;
	}

	/**
	 * Builds a DocumentStyles object from a set of definitions using the
	 * specified parser.
	 * 
	 * @param styleDefs
	 * @param parser
	 * @return
	 */
	public static DocumentStyles parseStyles(
			final Collection<StyleDefinition> styleDefs,
			final StyleParser parser) {
		final DocumentStyles styles = new DocumentStyles();
		for (final StyleDefinition styleDef : styleDefs) {
			final Style style = parser.parse(styleDef);
			if (styles.containsKey(style.getName())) {
				throw new IllegalStateException("style definition name \""
						+ style.getName() + "\" occurs more than once");
			}
			styles.put(style.getName(), style);
		}
		return styles;
	}

	/**
	 * Shortcut to building DocumentStyles using a spring context and a
	 * specified parser.
	 * 
	 * @param context
	 * @param styleParser
	 * @return
	 */
	public static DocumentStyles getStylesFromContext(
			final ApplicationContext context, final StyleParser styleParser) {
		final Set<StyleDefinition> definitions = getStyleDefinitionsFromContext(context);
		return parseStyles(definitions, styleParser);
	}

}
