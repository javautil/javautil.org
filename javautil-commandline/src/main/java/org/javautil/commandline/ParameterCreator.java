package org.javautil.commandline;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import jcmdline.BooleanParam;
import jcmdline.CmdLineException;
import jcmdline.DateParam;
import jcmdline.FileParam;
import jcmdline.IntParam;
import jcmdline.Parameter;
import jcmdline.StringParam;

import org.apache.log4j.Logger;
import org.javautil.commandline.annotations.AcceptableValues;
import org.javautil.commandline.annotations.DirectoryExists;
import org.javautil.commandline.annotations.DirectoryReadable;
import org.javautil.commandline.annotations.DirectoryWritable;
import org.javautil.commandline.annotations.Exclusive;
import org.javautil.commandline.annotations.FieldValue;
import org.javautil.commandline.annotations.FileExists;
import org.javautil.commandline.annotations.FileReadable;
import org.javautil.commandline.annotations.FileWritable;
import org.javautil.commandline.annotations.Hidden;
import org.javautil.commandline.annotations.MultiValue;
import org.javautil.commandline.annotations.Optional;
import org.javautil.commandline.annotations.Required;
import org.javautil.commandline.annotations.RequiredUnless;
import org.javautil.commandline.annotations.Requires;
import org.javautil.lang.SystemProperties;
import org.javautil.lang.reflect.ReflectUtils;
import org.javautil.text.AsString;

public class ParameterCreator {

	/**
	 * The ResourceBundle that describes the application and the fields in the
	 * argument bean.
	 */
	private ResourceBundle resourceBundle;
	private String resourceBundlePrefix = "";
	private final Logger logger = Logger.getLogger(getClass());
	private static final String newline = SystemProperties.newline;
	private final Map<String, Parameter> parametersByTag = new TreeMap<String, Parameter>();
	/**
	 * The annotated bean that has the fields to be set from the command line.
	 */
	private final Object argumentBean;

	public ParameterCreator(final Object argumentBean) {
		if (argumentBean == null) {
			throw new IllegalArgumentException("argumentBean is null");
		}
		this.argumentBean = argumentBean;
		resourceBundle = getResourceBundle();
	}

	public ParameterCreator(final Object argumentBean,
			final ResourceBundle resourceBundle,
			final String resourceBundlePrefix) {
		this(argumentBean);
		// if (argumentBean == null) {
		// throw new IllegalArgumentException("argumentBean is null");
		// }
		if (resourceBundle == null) {
			throw new IllegalArgumentException("resourceBundle is null");
		}

		this.resourceBundle = resourceBundle;
		this.resourceBundlePrefix = resourceBundlePrefix;

		// this.argumentBean = argumentBean;

	}

	public Map<String, Parameter> getParametersByTag() {
		// if (parametersByTag == null) {
		// generateParametersForArgumentBean();
		// }
		return parametersByTag;
	}

	/**
	 * sets the parameters defined in the ArgumentsObject.
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws CmdLineException
	 */
	public Map<String, Parameter> generateParametersForArgumentBean() {
		if (argumentBean == null) {
			throw new IllegalStateException("argumentBean has not been set");
		}
		final Set<Parameter> ret = new HashSet<Parameter>();

		Class<? extends Object> clazz = getArgumentsClass();
		if (clazz == null) {
			throw new NullPointerException("argumentsClass is null");
		}
		if (logger.isDebugEnabled()) {
			showParams("class", ret.getClass(), ret);
		}
		addParameters(generateParametersForClass(clazz));
		showParams("class", clazz, ret);
		while ((clazz.getSuperclass() != null) && !clazz.equals(Object.class)) {
			clazz = clazz.getSuperclass();
			final Set<Parameter> params = generateParametersForClass(clazz);
			if (logger.isDebugEnabled()) {
				showParams("superclass", clazz, params);
			}
			for (final Parameter param : params) {
				if (ret.contains(param)) {
					throw new IllegalArgumentException(
							"repeated field among class/superclass hierarchy named "
									+ param.getTag());
				}
			}
			addParameters(params);
		}
		// addParameterFile();
		return parametersByTag;
	}

	public Set<Parameter> generateParametersForClass(
			final Class<? extends Object> clazz)
			throws IllegalArgumentException {
		if (logger.isDebugEnabled()) {
			logger.debug("generating parameters for " + clazz.getName());
		}
		final Field[] fields = clazz.getDeclaredFields();
		final Set<Parameter> ret = new LinkedHashSet<Parameter>(fields.length);
		for (final Field field : fields) {
			final boolean hasRequiredAnnotation = field
					.getAnnotation(Required.class) != null;
			final boolean hasRequiredUnlessAnnotation = field
					.getAnnotation(RequiredUnless.class) != null;
			final boolean hasRequiresAnnotation = field
					.getAnnotation(Requires.class) != null;
			final boolean hasOptionalAnnotation = field
					.getAnnotation(Optional.class) != null;
			if (!field.isSynthetic()
					&& (hasRequiredAnnotation || hasRequiredUnlessAnnotation
							|| hasRequiresAnnotation || hasOptionalAnnotation)) {
				Parameter parm;
				try {
					parm = createParameterForField(field);
				} catch (final CmdLineException e) {
					throw new IllegalArgumentException(e);
				}
				ret.add(parm);
				logger.debug("adding parameter (from arguments object): "
						+ clazz.getName() + " " + field.getName());
			}
		}
		return ret;
	}

	public Object getArgumentBean() {
		return argumentBean;
	}

	/**
	 * Creates a parameter for an annotated field.
	 * 
	 * Called for every field in the ArgumentBean.
	 * 
	 * @param params
	 * @param field
	 * @throws CmdLineException
	 */
	public Parameter createParameterForField(final Field field)
			throws CmdLineException {
		if (resourceBundle == null) {
			throw new NullPointerException(
					"resourceBundle is null; call setResourceBundle() with a non-null "
							+ "resource bundle prior to parsing command line");
		}
		final String description = getFieldDescription(field);
		// String description = resourceBundle
		// .getString(getResourceBundlePropertyPrefix() + field.getName()
		// + ".description");
		field.setAccessible(true);
		final boolean hasOptionalAnnotation = hasAnnotation(field,
				Optional.class);
		final boolean hasRequiredAnnotation = hasAnnotation(field,
				Required.class);
		final boolean hasExclusiveAnnotation = hasAnnotation(field,
				Exclusive.class);
		final boolean hasFieldValueAnnotation = hasAnnotation(field,
				FieldValue.class);
		final boolean hasMultiValueAnnotation = hasAnnotation(field,
				MultiValue.class);
		final boolean hasHiddenAnnotation = hasAnnotation(field, Hidden.class);
		final boolean hasRequiredUnlessAnnotation = hasAnnotation(field,
				RequiredUnless.class);
		if (hasRequiredUnlessAnnotation) {
			validateHasRequiredUnlessAnnotatedField(field);
		}
		final boolean hasRequiresAnnotation = validateHasRequiresAnnotatedField(field);

		if (hasOptionalAnnotation && hasRequiredAnnotation) {
			throw new IllegalArgumentException("field " + field.getName()
					+ " of class " + getArgumentsClass().getName()
					+ " is annotated as both required and optional");
		}
		// TODO jjs why? just ignore it
		if (!hasOptionalAnnotation && !hasRequiredAnnotation
				&& !hasRequiredUnlessAnnotation && !hasExclusiveAnnotation
				&& !hasRequiresAnnotation) {
			throw new IllegalArgumentException("field " + field.getName()
					+ " of class " + getArgumentsClass().getName()
					+ " must have a valid "
					+ Optional.class.getPackage().getName()
					+ " annotation assigned to it");
		}
		final boolean optional = !hasRequiredAnnotation;

		final String name = field.getName();
		Parameter param = null;
		if (hasFieldValueAnnotation && hasMultiValueAnnotation) {
			throw new IllegalStateException(
					"argument property \""
							+ name
							+ "\" has both "
							+ "FieldValue and MultiValue annotations; this is not allowed");
		} else if (hasFieldValueAnnotation || hasMultiValueAnnotation) {
			final FieldValue fieldValue = field.getAnnotation(FieldValue.class);
			final MultiValue multiValue = field.getAnnotation(MultiValue.class);
			final ParamType paramType = fieldValue != null ? fieldValue.type()
					: multiValue.type();
			// TODO what is this FILe is here and then in case statement
			final boolean isFileOrDirectory = isFileField(field)
					|| isDirectoryField(field);
			if (isFileOrDirectory) {
				param = newFileParam(field, name, description, optional);
			} else {

				switch (paramType) {

				case INTEGER:
					param = new IntParam(name, description, optional);
					break;
				case STRING:
					param = new StringParam(name, description, optional);
					break;
				// case FILE:
				// param = newFileParam(field, name, description, optional);
				// break;
				case BOOLEAN:
					param = new BooleanParam(name, description, optional);
					break;
				case DATE:
					param = new DateParam(name, description, optional);
					break;
				case FILE:
					param = new FileParam(name, description, optional);
					break;
				default:
					final String message = "not yet implemented "
							+ (fieldValue == null ? " null " : fieldValue
									.type());
					throw new IllegalArgumentException(message);
				}
			}
		} else if (Collection.class.isAssignableFrom(field.getType())) {
			param = new StringParam(name, description, optional);
		} else if (String.class.isAssignableFrom(field.getType())) {
			param = new StringParam(name, description, optional);
		} else if (Integer.class.isAssignableFrom(field.getType())) {
			param = new IntParam(name, description, optional);
		} else if (Integer.TYPE.isAssignableFrom(field.getType())) {
			param = new IntParam(name, description, optional);
		}
		// else if (Long.class.isAssignableFrom(field.getType())) {
		// param = new IntParam(name, description, optional);
		// } else if (Long.TYPE.isAssignableFrom(field.getType())) {
		// param = new IntParam(name, description, optional);
		// }
		else if (Boolean.class.isAssignableFrom(field.getType())) {
			param = new BooleanParam(name, description, optional);
		} else if (Boolean.TYPE.isAssignableFrom(field.getType())) {
			param = new BooleanParam(name, description, optional);
		} else if (Date.class.isAssignableFrom(field.getType())) {
			param = new DateParam(name, description, optional);
		} else if (File.class.isAssignableFrom(field.getType())) {
			param = newFileParam(field, name, description, optional);
		} else {
			throw new IllegalArgumentException("Unsupported field type " + name
					+ " of type " + field.getType().getName());
		}
		setAcceptableValues(param, field);
		if (hasMultiValueAnnotation
				|| Collection.class.isAssignableFrom(field.getType())) {
			param.setMultiValued(true);
		}
		// TODO test
		if (hasHiddenAnnotation) {
			param.setHidden(true);
		}
		return param;
	}

	public String getFieldDescription(final Field field,
			final ResourceBundle resourceBundle) {
		String description = null;
		try {
			description = resourceBundle.getString(getResourceBundlePrefix()
					+ field.getName() + ".description");
		} catch (final MissingResourceException mre) {
			description = "No description for '" + field.getName() + "' found";
		}
		return description;
	}

	/**
	 * Checks that the specified field is valid at the time the Parameter is
	 * being created.
	 * 
	 * @param field
	 * @throws CmdLineException
	 * 
	 */
	private void validateHasRequiredUnlessAnnotatedField(final Field field)
			throws CmdLineException {
		// TODO Auto-generated method stub
		logger.debug("validateHasRequiredUnlessAnnotatedField "
				+ field.getName());
		final RequiredUnless requiredUnlessAnnotation = field
				.getAnnotation(RequiredUnless.class);
		if (requiredUnlessAnnotation != null) {
			final String requiresFieldName = requiredUnlessAnnotation
					.property();
			logger.debug("requiredUnlessFieldName " + requiresFieldName);
			final Field requiresField = ReflectUtils.getFieldByName(
					argumentBean.getClass(), requiresFieldName);
			if (requiresField == null) {
				throw new CmdLineException("a field named " + requiresFieldName
						+ " was not found on " + getArgumentsClass().getName()
						+ " as defined in the "
						+ RequiredUnless.class.getSimpleName()
						+ " annotation on field " + field.getName());
			}
		}
	}

	/**
	 * Checks that the specified field is valid at the time the Parameter is
	 * being created.
	 * 
	 * @param field
	 * @throws CmdLineException
	 * @return boolean true if field is annotated with <code>Requires</code>
	 */
	boolean validateHasRequiresAnnotatedField(final Field field)
			throws CmdLineException {
		// TODO Auto-generated method stub
		logger.debug("validateHasRequiredUnlessAnnotatedField "
				+ field.getName());
		final Requires requiresAnnotation = field.getAnnotation(Requires.class);
		final boolean hasRequiresAnnotation = requiresAnnotation != null;
		if (hasRequiresAnnotation) {
			final String requiresFieldName = requiresAnnotation.property();
			logger.debug("requiresFieldName " + requiresFieldName);
			final Field requiresField = ReflectUtils.getFieldByName(
					argumentBean.getClass(), requiresFieldName);
			if (requiresField == null) {
				throw new CmdLineException("a field named " + requiresFieldName
						+ " was not found on " + getArgumentsClass().getName()
						+ " as defined in the "
						+ RequiredUnless.class.getSimpleName()
						+ " annotation on field " + field.getName());
			}
		}
		return hasRequiresAnnotation;
	}

	/**
	 * Modifies the specified param constraining it to the list of provided
	 * acceptable values
	 * 
	 * @param param
	 * @param field
	 */
	void setAcceptableValues(final Parameter param, final Field field) {
		final AcceptableValues acceptableValuesAnnotation = field
				.getAnnotation(AcceptableValues.class);
		if (acceptableValuesAnnotation != null) {
			final String[] acceptableValues = acceptableValuesAnnotation
					.values();
			if (acceptableValues.length == 0) {
				throw new IllegalArgumentException(
						"no acceptable values provided for field "
								+ field.getName());
			}
			param.setAcceptableValues(acceptableValues);
		}

	}

	FileParam newFileParam(final Field field, final String name,
			final String description, final boolean optional) {
		final FileParam fp = new FileParam(name, description, optional);
		int attributes = 0xffff;
		final boolean directoryMode = false;
		final boolean fileMode = false;
		// TODO used to check file and Directory parameter conflicts, make sure
		// still does
		attributes = andAttribute(field, attributes, FileParam.IS_FILE,
				FileExists.class);
		attributes = andAttribute(field, attributes, FileParam.IS_READABLE,
				FileReadable.class);
		attributes = andAttribute(field, attributes, FileParam.IS_WRITEABLE,
				FileWritable.class);
		attributes = andAttribute(field, attributes, FileParam.IS_READABLE,
				DirectoryReadable.class);
		attributes = andAttribute(field, attributes, FileParam.IS_WRITEABLE,
				DirectoryWritable.class);
		if (directoryMode) {
			attributes = andAttribute(field, attributes, FileParam.IS_DIR,
					DirectoryWritable.class);
		}
		if (fileMode) {
			attributes = andAttribute(field, attributes, FileParam.IS_FILE,
					FileWritable.class);
		}
		attributes = andAttribute(field, attributes, FileParam.EXISTS,
				DirectoryExists.class);

		if (fileMode && directoryMode) {
			throw new IllegalArgumentException(
					"annotations added incorrectly;"
							+ " use directory or file annotations but not both on the same field "
							+ "while processing " + field + " " + name);
		}
		if (attributes != 0) {
			fp.setAttributes(attributes);
		}
		if (logger.isDebugEnabled()) {
			final AsString as = new AsString();
			logger.debug(as.toString(fp));
		}
		return fp;
	}

	/**
	 * Examines the field and returns the bit and of the attributes and bitMask
	 * if the field has the specified annotation.
	 * 
	 * @param field
	 * @param attributes
	 * @param bitMask
	 * @param annotationClass
	 * @return
	 */
	int andAttribute(final Field field, final int attributes,
			final int bitMask, final Class<? extends Annotation> annotationClass) {
		int retval = attributes;
		StringBuilder sb = null;
		if (logger.isDebugEnabled()) {
			sb = new StringBuilder();
			sb.append(field.getName());
			sb.append(" before ");
			sb.append(annotationClass.getSimpleName());
			sb.append(" ");
			sb.append(Integer.toHexString(attributes));
		}
		if (field.getAnnotation(annotationClass) != null) {
			if (logger.isDebugEnabled())
			logger.debug("setting " + annotationClass.getName());

			retval = attributes & bitMask;
		}
		if (logger.isDebugEnabled()) {
			sb.append(" ");
			sb.append(Integer.toHexString(retval));
			if (retval != attributes) {
				logger.debug(sb.toString());
			} else {
				logger.debug("mismo  " + sb.toString());
			}
		}
		return retval;

	}

	public String getFieldDescription(final Field field) {
		String description = null;
		try {
			description = resourceBundle.getString(getResourceBundlePrefix()
					+ field.getName() + ".description");
		} catch (final MissingResourceException mre) {
			description = "No description for '" + field.getName() + "' found";
		}
		return description;
	}

	boolean hasAnnotation(final Field field, final Class annotationClass) {
		return field.getAnnotation(annotationClass) != null;
	}

	/**
	 * This only checks that the annotations declare that this is a field.
	 * 
	 * @param field
	 * @return
	 */
	public boolean isFileField(final Field field) {
		final boolean isFile = (field.getAnnotation(FileExists.class) != null)
				|| (field.getAnnotation(FileReadable.class) != null)
				|| (field.getAnnotation(FileWritable.class) != null);

		return isFile;
	}

	private void showParams(final String message,
			final Class<? extends Object> clazz, final Set<Parameter> parms) {
		if (clazz == null) {
			throw new IllegalArgumentException("clazz is null");
		}
		if (parms == null) {
			throw new IllegalArgumentException("parms is null");
		}
		final StringBuilder b = new StringBuilder();
		b.append(message);
		b.append(newline);
		b.append(clazz.getName());
		b.append(newline);
		if (logger.isDebugEnabled()) {
			for (final Parameter parm : parms) {
				b.append(parm.getTag());
			}
			b.append(newline);
		}
	}

	/**
	 * This only checks that the annotations declare that this is a field.
	 * 
	 * @param field
	 * @return
	 */
	public boolean isDirectoryField(final Field field) {
		final boolean isFile = (field.getAnnotation(DirectoryExists.class) != null)
				|| (field.getAnnotation(DirectoryReadable.class) != null)
				|| (field.getAnnotation(DirectoryWritable.class) != null);

		return isFile;
	}

	void addParameters(final Collection<Parameter> params) {
		for (final Parameter param : params) {
			addParameter(param);
		}
	}

	public ResourceBundle getResourceBundle() {

		if (resourceBundle == null) {

			final String implicitPropertyPath = inferPropertyPath(argumentBean
					.getClass());
			resourceBundle = ResourceBundle.getBundle(implicitPropertyPath);
		}
		return resourceBundle;
	}

	// private void addParameterFile() {
	//
	// Parameter parmFile = new
	// FileParam("parameterFile","the file from which to read the parms",
	// FileParam.IS_FILE & FileParam.IS_READABLE,
	// FileParam.OPTIONAL,
	// FileParam.SINGLE_VALUED);
	// parametersByTag.put(parmFile.getTag(),parmFile);
	// }

	/**
	 * Expects by convention that the property file for the argument bean will
	 * have the same path as the argument bean following resource naming
	 * conventions.
	 * 
	 * Therefore if the name of the class is com.my.example then the resource
	 * file should be com/my/example.properties
	 * 
	 * @param clazz
	 * @return
	 */
	public String inferPropertyPath(final Class<?> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("clazz is null");
		}

		final String toPath = clazz.getName().replace(".", "/");
		return toPath;
	}

	void addParameter(final Parameter param) {
		if (getParameter(param.getTag()) != null) {
			throw new IllegalArgumentException("repeated parameter named "
					+ param.getTag());
		}
		parametersByTag.put(param.getTag(), param);
	}

	public boolean isParameterSet(final String tagName) {
		final Parameter parameter = getParameter(tagName);
		final boolean isSet = (parameter != null) && parameter.isSet();
		if (logger.isDebugEnabled()) {
			logger.debug("parameter " + tagName + " is set " + isSet);
		}
		return isSet;
	}

	public Parameter getParameter(final String tagName) {
		final Parameter param = parametersByTag.get(tagName);
		return param;
	}

	private Class<? extends Object> getArgumentsClass() {
		return argumentBean.getClass();
	}

	public String getResourceBundlePrefix() {
		return resourceBundlePrefix;
	}

	public void setResourceBundlePrefix(final String resourceBundlePrefix) {
		this.resourceBundlePrefix = resourceBundlePrefix;
	}

	public void setResourceBundle(final ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

}
