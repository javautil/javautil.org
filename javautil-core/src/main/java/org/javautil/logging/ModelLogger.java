package org.javautil.logging;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.logging.Log;

public class ModelLogger {

	private final Log logger;

	private final int maxCollectionSize = 5;

	public ModelLogger(final Log logger) {
		this.logger = logger;
	}

	public void trace(final String modelName,
			final Map<String, ? extends Object> model) {
		if (logger.isTraceEnabled()) {
			logger.trace(buildModelString(modelName, model));
		}
	}

	public void debug(final String modelName,
			final Map<String, ? extends Object> model) {
		if (logger.isDebugEnabled()) {
			logger.debug(buildModelString(modelName, model));
		}
	}

	public void warn(final String modelName,
			final Map<String, ? extends Object> model) {
		if (logger.isWarnEnabled()) {
			logger.warn(buildModelString(modelName, model));
		}
	}

	public void info(final String modelName,
			final Map<String, ? extends Object> model) {
		if (logger.isInfoEnabled()) {
			logger.info(buildModelString(modelName, model));
		}
	}

	public void error(final String modelName,
			final Map<String, ? extends Object> model) {
		if (logger.isErrorEnabled()) {
			logger.error(buildModelString(modelName, model));
		}
	}

	public void fatal(final String modelName,
			final Map<String, ? extends Object> model) {
		if (logger.isFatalEnabled()) {
			logger.fatal(buildModelString(modelName, model));
		}
	}

	protected String buildModelString(final String modelName,
			final Map<String, ? extends Object> model) {
		final StringBuilder buffy = new StringBuilder();
		buffy.append("model for " + modelName + "\n");
		final TreeSet<String> sortedKeys = new TreeSet<String>(model.keySet());
		for (final Object modelAttributeName : sortedKeys) {
			final Object modelObject = model.get(modelAttributeName);
			buffy.append("\t" + modelAttributeName + "=");
			logModelObject(modelObject, buffy, 1);
			buffy.append("\n");
		}
		return buffy.toString();
	}

	@SuppressWarnings("unchecked")
	protected void logModelObject(final Object modelObject,
			final StringBuilder buffy, final int depth) {
		if (modelObject == null) {
			buffy.append("null");
		} else if (modelObject instanceof Collection) {
			final Collection<Object> collection = (Collection<Object>) modelObject;
			logModelCollection(collection, buffy, depth);
		} else if (modelObject instanceof Map) {
			final Map<Object, Object> map = (Map<Object, Object>) modelObject;
			logModelMap(map, buffy, depth);
		} else if (modelObject.getClass().getPackage().getName()
				.startsWith("java")) {
			buffy.append(modelObject);
			// depth 2 is the depth of the model objects directly beneath the
			// root
		} else if (depth == 2) {
			final Method[] methods = modelObject.getClass().getMethods();
			final Map<Object, Object> beanModel = new TreeMap<Object, Object>();
			for (final Method method : methods) {
				if (Modifier.isPublic(method.getModifiers())
						&& method.getName().startsWith("get")
						&& method.getDeclaringClass().equals(
								modelObject.getClass())) {
					final String modelProperty = Character.toLowerCase(method
							.getName().charAt(3))
							+ method.getName().substring(4);
					Object modelValue = null;
					try {
						modelValue = method.invoke(modelObject, null);
					} catch (final Exception e) {
						throw new RuntimeException(e);
					}
					beanModel.put(modelProperty, modelValue);
				}
			}
			logModelMap(beanModel, buffy, depth);
		} else {
			buffy.append(modelObject);
		}
	}

	protected void logModelMap(final Map<Object, Object> map,
			final StringBuilder buffy, final int depth) {
		Iterator<Object> iterator = map.keySet().iterator();
		buffy.append("{");
		int printedCount = 0;
		while (iterator != null && iterator.hasNext()) {
			if (!Events.isRegistered("quickmodel")
					|| printedCount < maxCollectionSize) {
				if (printedCount != 0) {
					buffy.append(", ");
					if (Events.isRegistered("prettymodel")) {
						buffy.append("\n");
						for (int i = 0; i < depth; i++) {
							buffy.append("\t");
						}
					}
				}
				final Object key = iterator.next();
				logModelObject(key, buffy, depth + 1);
				buffy.append(": ");
				final Object value = map.get(key);
				logModelObject(value, buffy, depth + 1);
				printedCount++;
			} else {
				buffy.append(", ");
				if (Events.isRegistered("prettymodel")) {
					buffy.append("\n");
					for (int i = 0; i < depth; i++) {
						buffy.append("\t");
					}
				}
				buffy.append("..." + (map.size() - 5) + " more, " + map.size()
						+ " total");
				iterator = null;
			}
		}
		buffy.append("}");
	}

	protected void logModelCollection(final Collection<Object> collection,
			final StringBuilder buffy, final int depth) {
		Iterator<Object> iterator = collection.iterator();
		buffy.append("[");
		int printedCount = 0;
		while (iterator != null && iterator.hasNext()) {
			if (!Events.isRegistered("quickmodel")
					|| printedCount < maxCollectionSize) {
				if (printedCount != 0) {
					buffy.append(", ");
					if (Events.isRegistered("prettymodel")) {
						buffy.append("\n");
						for (int i = 0; i < depth; i++) {
							buffy.append("\t");
						}
					}
				}
				final Object object = iterator.next();
				logModelObject(object, buffy, depth + 1);
				printedCount++;
			} else {
				buffy.append(", ");
				if (Events.isRegistered("prettymodel")) {
					buffy.append("\n");
					for (int i = 0; i < depth; i++) {
						buffy.append("\t");
					}
				}
				buffy.append("..." + (collection.size() - 5) + " more, "
						+ collection.size() + " total");
				iterator = null;
			}
		}
		buffy.append("]");
	}

}
