package org.javautil.lang.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class ClassCacheEntry {

	private final Logger logger = Logger.getLogger(getClass());
	// TODO should populate Methods
	private final FieldCache fields = new FieldCache();
	private final Class<?> clazz;
	private final HashMap<String, ArrayList<Method>> accessorMethodsByName = new HashMap<String, ArrayList<Method>>();

	// TODO there could be more than one setter with different argument types
	public HashMap<String, ArrayList<Method>> getAccessorMethodsByName() {
		return accessorMethodsByName;
	}

	public ClassCacheEntry(Class<?> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("clazz is null");
		}
		this.clazz = clazz;
		populateMethods();
	}

	/**
	 * Populate with any method that starts with "set" and takes one argument or
	 * starts with "get" and takes no arguments.
	 * 
	 * It is possible that an entry is placed which is not an accessor as there
	 * might be no such field.
	 */
	private void populateMethods() {
		final Method[] methods = clazz.getMethods();
		for (Method meth : methods) {
			String name = meth.getName();
			String prefix = name.substring(0, 2);
			if (("set".equals(prefix) && meth.getTypeParameters().length == 1)
					|| ("get".equals(prefix) && meth.getTypeParameters().length == 0)) {

				ArrayList<Method> meths = accessorMethodsByName.get(name);
				if (methods == null) {
					meths = new ArrayList<Method>();
				}
				meths.add(meth);
				accessorMethodsByName.put(name, meths);
			}
		}
	}

	public void populate() {
		Field[] clazzFields = clazz.getDeclaredFields();
		if (logger.isDebugEnabled()) {
			logger.debug("clazzFields size " + clazzFields.length);
		}
		for (Field field : clazzFields) {
			if (logger.isDebugEnabled()) {
				logger.debug("found field " + field);
			}
			String fieldName = field.getName();
			FieldCacheEntry entry = new FieldCacheEntry(this, field);
			fields.put(fieldName, entry);
		}
	}

	public FieldCache getFields() {
		return fields;
	}

	public Class<?> getClazz() {
		return clazz;
	}
}
