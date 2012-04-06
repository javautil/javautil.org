package org.javautil.commandline;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import jcmdline.BooleanParam;
import jcmdline.CmdLineException;
import jcmdline.CmdLineHandler;
import jcmdline.DateParam;
import jcmdline.DefaultCmdLineHandler;
import jcmdline.FileParam;
import jcmdline.Parameter;
import jcmdline.VersionCmdLineHandler;

import org.apache.log4j.Logger;
import org.javautil.commandline.annotations.Exclusive;
import org.javautil.commandline.annotations.MultiValue;
import org.javautil.commandline.annotations.RequiredUnless;
import org.javautil.commandline.annotations.Requires;
import org.javautil.lang.reflect.IntrospectedFieldHelper;
import org.javautil.lang.reflect.ReflectUtils;

/**
 * A class that facilitates the declarative definition of program arguments
 * using java annotations.
 * 
 * Steps:
 * <ul>
 * <li>Write an annotated Argument Bean
 * <li>Create one or more resource files (base and then language specific) to
 * describe the arguments
 * </ul>
 * 
 * 
 * Annotations
 * 
 * 
 * TODO RequiredUnless Annotation that references a non existent field does not
 * throw an error unless the argument is specified
 */
public class CommandLineHandler {

	private final Logger logger = Logger.getLogger(this.getClass());
	private static final String APPLICATION_NAME = "application.name";

	private static final String APPLICATION_DESCRIPTION = "application.description";

	private static final String APPLICATION_HELP_TEXT = "application.helpText";
	private final DefaultCommandLineErrorHandler errorHandler = new DefaultCommandLineErrorHandler();

	/**
	 * An annotated bean that holds the definition of parameters and the
	 * parameter values.
	 */
	private Object argumentsObject = null;

	private static final String newline = System.getProperty("line.separator");
	/**
	 * 
	 * Version doesn't mean much if it is maintained by RCS/CVS as this is for
	 * the file containing the Revision: tag.
	 */
	private String applicationVersion = null;

	private String applicationName = null;

	private String applicationHelpText = null;

	private String applicationDescription = "No application description is available";

	/**
	 * The resource bundle that holds the application and argument descriptions.
	 * 
	 * Each field should have an entry such as
	 * 
	 * <pre>
	 * fieldName.description=The description for the argument with the name fieldName
	 */
	private ResourceBundle resourceBundle = null;

	private String resourceBundlePropertyPrefix = "";

	private boolean ignoreUnrecognizedOptions = false;

	private CmdLineHandler cmd = null;

	private CommandLineParser parser = null;

	private boolean dieOnParseError = true;

	private final ParameterCreator parameterCreator;

	private final Map<String, Parameter> parametersByTag;

	public CommandLineHandler(final Object argumentsBean) {

		if (argumentsBean == null) {
			errorHandler.handleException("argumentsBean is null");
		}
		this.argumentsObject = argumentsBean;
		parameterCreator = new ParameterCreator(argumentsBean);
		parametersByTag = parameterCreator.generateParametersForArgumentBean();

	}

	/**
	 * @see #dieOnParseError
	 * @return
	 */
	public boolean isDieOnParseError() {
		return dieOnParseError;
	}

	/**
	 * If the argument is true, when the arguments are parsed System.exit will
	 * be called if the arguments don't satisfy the requirements.
	 * 
	 * @param dieOnParseError
	 */
	public void setDieOnParseError(final boolean dieOnParseError) {
		this.dieOnParseError = dieOnParseError;
	}

	/**
	 * Splits the command on space boundaries and then calls
	 * 
	 * @see evaluateAguuments(String[] commandLineArguments) TODO needs to call
	 *      a better class that splits on white space and recognizes and honors
	 *      quotes
	 * @param argumentString
	 * @return
	 * @throws CmdLineException
	 */
	public boolean evaluateArgumentsString(final String argumentString)
			throws CmdLineException {
		// TODO replace with the more robust version
		final String[] args = argumentString.split(" ");
		return evaluateArguments(args);
	}

	/**
	 * Parses the array of strings as received by the main method of a typical
	 * program
	 * 
	 * @param commandLineArguments
	 * @throws CmdLineException
	 */
	public boolean evaluateArguments(final String[] commandLineArguments)
			throws InvalidArgumentException {

		if (this.argumentsObject == null) {
			throw new IllegalStateException("argumentsObject is null");
		}
		if (commandLineArguments == null) {
			throw new IllegalArgumentException("commandLineArguments is null");
		}

		createCommandHandler();
		// TODO make modern
		final String[] opts = parser
				.parseArgumentsAsParameters(commandLineArguments);
		showArguments(opts);
		cmd.setDieOnParseError(dieOnParseError);
		final boolean parseResult = cmd.parse(opts);
		logger.debug("parseResult " + parseResult);
		if (cmd.getParseError() != null) {
			errorHandler.handleException(cmd.getParseError());
		}
		if (!parseResult && (cmd.getParseError() == null)) {
			logger.warn("parseResult " + parseResult + " parseError "
					+ cmd.getParseError());
		}
		applyParameterValues();
		try {
			checkExclusivesAndRequires();
		} catch (final CmdLineException e) {
			errorHandler.handleException(e.getMessage());
		}
		return parseResult;
	}

	// // TODO what is this really doing?
	// public void setArguments(Object arguments) throws
	// CmdLineException {
	// this.argumentsObject = arguments;
	// try {
	// //applyParametersFromArgumentsObject();
	// for (Parameter p : parametersByTag.values()) {
	// if (logger.isDebugEnabled()) {
	// logger.debug("clearing parameter: " + p.getTag());
	// }
	// // need to clear old values if the parameters are re-used, the
	// // API thinks that if a parameter is set, it is being specified
	// // twice in the command-line arguments (incorrect!)
	//
	// if (p instanceof AbstractParameter) {
	// Field f = AbstractParameter.class.getDeclaredField("set");
	// f.setAccessible(true);
	// f.set(p, false);
	// f = AbstractParameter.class.getDeclaredField("values");
	// f.setAccessible(true);
	// f.set(p, new ArrayList<Object>());
	// }
	// }
	// } catch (SecurityException e) {
	// throw new IllegalStateException(e);
	// } catch (NoSuchFieldException e) {
	// throw new IllegalStateException(e);
	// } catch (IllegalAccessException e) {
	// throw new IllegalStateException(e);
	// }
	// }

	private void createCommandHandler() {
		final Parameter[] parameters = parametersByTag.values().toArray(
				new Parameter[parametersByTag.size()]);
		parser = new CommandLineParser();
		// todo this is weird
		final String appDescr = getApplicationDescription() == null ? "No Application Description is available "
				: getApplicationDescription();
		final String appName = getApplicationName();
		final DefaultCmdLineHandler dflt = new DefaultCmdLineHandler(appName,
				appDescr, parameters, null, parser);
		cmd = dflt;
		cmd.setDieOnParseError(dieOnParseError);
		errorHandler.setCmdLineHandler(cmd);
		if (applicationVersion != null) {
			final VersionCmdLineHandler ver = new VersionCmdLineHandler(
					applicationVersion, dflt);
			cmd = ver;
		}

		parser.setIgnoreUnrecognizedOptions(ignoreUnrecognizedOptions);
	}

	/**

	 */
	@SuppressWarnings("unchecked")
	private void applyParameterValues() {
		for (final Parameter parameter : parametersByTag.values()) {
			final IntrospectedFieldHelper introspection = new IntrospectedFieldHelper(
					argumentsObject, parameter.getTag());
			introspection.setRequireGetter(true);
			introspection.setRequireSetter(true);
			final Field field = ReflectUtils.getFieldByName(
					argumentsObject.getClass(), parameter.getTag());
			if (field == null) {
				throw new IllegalStateException("no field on "
						+ argumentsObject.getClass().getName() + " named "
						+ parameter.getTag());
			}
			final boolean hasMultiValueAnnotation = field
					.getAnnotation(MultiValue.class) != null;
			if (hasMultiValueAnnotation
					|| Collection.class.isAssignableFrom(field.getType())) {
				multiValueAssign(parameter, field, introspection);

			} else if (parameter instanceof BooleanParam) {
				final BooleanParam p = (BooleanParam) parameter;
				if (p.isSet()) {
					introspection.invokeSetter(p.isTrue() ? true : false);
				}
			} else if (parameter instanceof FileParam) {
				final FileParam p = (FileParam) parameter;
				if (p.getValue() != null) {
					introspection.invokeSetter(p.getFile());
				}
			} else if (parameter instanceof DateParam) {
				final DateParam d = (DateParam) parameter;
				if (d.getValue() != null) {
					introspection.invokeSetter(d.getDate());
				}
			} else if (parameter.getValue() != null) {
				introspection.invokeSetter(parameter.getValue());
			}
		}
	}

	/**
	 * Sets the bean properties from the parameter values for multivalued
	 * parameters.
	 * 
	 * @param parameter
	 * @param field
	 * @param introspection
	 */
	void multiValueAssign(final Parameter parameter, final Field field,
			final IntrospectedFieldHelper introspection) {
		if (parameter.getValues().size() > 0) {
			final MultiValue multiValue = field.getAnnotation(MultiValue.class);
			// TODO if the annotation
			if (multiValue.type() == ParamType.FILE) {
				// FileParam fileParam = (FileParam) parameter;
				final Collection<String> filepaths = parameter.getValues();
				if (filepaths.size() > 0) {
					final List<File> files = new ArrayList<File>();
					for (final String filepath : filepaths) {
						files.add(new File(filepath));
					}
					introspection.invokeSetter(files);
				}
			} else if (multiValue.type() == ParamType.DATE) {
				final DateParam dateParam = (DateParam) parameter;
				final Date[] d = dateParam.getDates();
				if (d.length > 0) {
					final List<Date> dates = Arrays.asList(d);
					introspection.invokeSetter(dates);
				}
			} else {
				final Collection<String> values = parameter.getValues();
				if (values.size() > 0) {
					introspection.invokeSetter(values);
				}
			}
		}
	}

	/**
	 * Get the fields in the arguments class.
	 * 
	 * @throws CmdLineException
	 * 
	 */
	private void checkExclusivesAndRequires() throws CmdLineException {

		final Map<Class<? extends Object>, Collection<Field>> fieldsByClass = ReflectUtils
				.getFieldsInClass(getArgumentsClass());
		for (final Class<? extends Object> declaringClass : fieldsByClass
				.keySet()) {
			final Collection<Field> fields = fieldsByClass.get(declaringClass);
			for (final Field field : fields) {
				checkFieldExclusivesAndRequires(field);
			}
		}

	}

	/**
	 * TODO revisit explanation Ensures that a required option is present in the
	 * class and that the required field has been specified in the command line.
	 * 
	 * @param field
	 * @throws CmdLineException
	 */
	private void checkRequiredUnless(final Field field) throws CmdLineException {
		if (field.getAnnotation(RequiredUnless.class) != null) {

			final RequiredUnless requires = field
					.getAnnotation(RequiredUnless.class);
			final String requiredUnlessProperty = requires.property();
			Field requiresField = null;

			try {
				requiresField = ReflectUtils.getField(getArgumentsClass(),
						requiredUnlessProperty);
			} catch (final SecurityException e) {
				throw new IllegalStateException(e);
			} catch (final NoSuchFieldException e) {
				// I dont know how to make this happen, if I reference a field
				// that doesn't exist
				// * get a null requiresField value
				throw new IllegalStateException(e);
			}
			if (requiresField == null) {
				throw new CmdLineException("a field named "
						+ requiredUnlessProperty
						+ //
						" was not found on " + getArgumentsClass().getName()
						+ //
						" as defined in the "
						+ RequiredUnless.class.getSimpleName() //
						+ " annotation on field " + field.getName());
			}
			requiresField.setAccessible(true);

			if (!isParameterSet(requiredUnlessProperty)) {
				cmd.exitUsageError("parameter " + field.getName()
						+ " must be specified when the parameter "
						+ requiresField.getName() + " is not specified");
			}
		}
	}

	public void checkRequires(final boolean isSet, final Field field)
			throws CmdLineException {
		// get the Required Annotation for the field
		final Requires requires = field.getAnnotation(Requires.class);
		final String requiresProperty = requires.property();
		Field requiredField = null;
		try {
			requiredField = ReflectUtils.getField(getArgumentsClass(),
					requiresProperty);
			requiredField.setAccessible(true);
		} catch (final NoSuchFieldException e) {
			throw new CmdLineException("a field named " + requiresProperty
					+ " was not found on " + getArgumentsClass().getName()
					+ " as defined in the " + Requires.class.getSimpleName()
					+ " annotation on field " + field.getName());
		}
		if (isSet && !isParameterSet(requiresProperty)) {
			cmd.exitUsageError("parameter " + requiredField.getName()
					+ " is also required when the parameter " + field.getName()
					+ " is specified ");
		}
	}

	protected void checkFieldExclusivesAndRequires(final Field field)
			throws CmdLineException {

		field.setAccessible(true);
		final boolean isSet = isParameterSet(field.getName());

		if (!isSet) {
			checkRequiredUnless(field);
		} else {
			checkExclusive(field);
		}
		if (field.getAnnotation(Requires.class) != null) {
			checkRequires(isSet, field);
		}

	}

	private Field getArgumentField(final String fieldName,
			final Class<Exclusive> annotation, final String referencedByField) {
		Field f;
		try {
			f = ReflectUtils.getField(getArgumentsClass(), fieldName);
		} catch (final NoSuchFieldException e) {
			throw new IllegalStateException("a field named " + fieldName
					+ " was not found on " + getArgumentsClass().getName()
					+ " as defined in the "
					+ annotation.getClass().getSimpleName()
					+ " annotation on field " + referencedByField);
		}
		return f;
	}

	/**
	 * 
	 * @param field
	 */
	private void checkExclusive(final Field field) {
		final Exclusive exclusive = field.getAnnotation(Exclusive.class);
		if (exclusive != null) {
			final String mutuallyExclusiveArgumentName = exclusive.property();
			final Field exclusiveField = getArgumentField(exclusive.property(),
					Exclusive.class, mutuallyExclusiveArgumentName);

			if (isParameterSet(mutuallyExclusiveArgumentName)) {
				final String message = "parameter " + field.getName()
						+ " can only be specified when the parameter "
						+ exclusiveField.getName() + " is not specified";
				cmd.exitUsageError(message);
			}
		}
	}

	public Parameter getParameter(final String tagName) {
		final Parameter param = parametersByTag.get(tagName);
		return param;
	}

	public boolean isParameterSet(final String tagName) {
		final Parameter parameter = getParameter(tagName);
		final boolean isSet = (parameter != null) && parameter.isSet();
		if (logger.isDebugEnabled()) {
			logger.debug("parameter " + tagName + " is set " + isSet);
		}
		return isSet;
	}

	public String getFieldDescription(final Field field) {
		String description = null;
		try {
			description = resourceBundle
					.getString(getResourceBundlePropertyPrefix()
							+ field.getName() + ".description");
		} catch (final MissingResourceException mre) {
			description = "No description for '" + field.getName() + "' found";
		}
		return description;
	}

	public Object getArguments() {
		return argumentsObject;
	}

	public String getResourceValue(final String resourceName,
			final String defaultValue) {
		String returnValue = defaultValue == null ? "" : defaultValue;
		if (getResourceBundle() != null) {
			if (resourceName == null) {
				throw new IllegalArgumentException("resourceName '"
						+ resourceName + "' is not defined in the resource "
						+ "bundle and has no default value");
			}
			try {
				returnValue = getResourceBundle().getString(resourceName);
			} catch (final MissingResourceException mealReadyToEat) {
				returnValue = defaultValue;
			}
		}
		return returnValue;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public void setApplicationVersion(final String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}

	public String getApplicationName() {
		String ret = getResourceValue(APPLICATION_NAME, applicationName);
		if (ret == null) {
			ret = "";
		}
		return ret;
	}

	public void setApplicationName(final String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationHelpText() {
		String ret = getResourceValue(APPLICATION_HELP_TEXT,
				applicationHelpText);
		if ((ret == null) || (ret.length() < 1)) {
			logger.warn("could not locate resource '" + APPLICATION_HELP_TEXT);
			ret = "No additional help information is available";
		}
		return ret;
	}

	public void setApplicationHelpText(final String applicationHelpText) {
		this.applicationHelpText = applicationHelpText;
	}

	/**
	 * 
	 * @return the application description as set in see
	 *         {@link #setApplicationDescription(String)} if it is not null else
	 *         get it from the resource
	 * 
	 */
	public String getApplicationDescription() {
		String ret = getResourceValue(APPLICATION_DESCRIPTION,
				applicationDescription);
		if (ret == null) {
			ret = " ";
		}
		return ret;
	}

	public void setApplicationDescription(final String applicationDescription) {
		this.applicationDescription = applicationDescription;
	}

	public boolean isIgnoreUnrecognizedOptions() {
		return ignoreUnrecognizedOptions;
	}

	public void setIgnoreUnrecognizedOptions(
			final boolean ignoreUnrecognizedOptions) {
		this.ignoreUnrecognizedOptions = ignoreUnrecognizedOptions;
	}

	public void setResourceBundlePropertyPrefix(
			final String resourceBundlePropertyPrefix) {
		this.resourceBundlePropertyPrefix = resourceBundlePropertyPrefix;
	}

	public String getResourceBundlePropertyPrefix() {
		return resourceBundlePropertyPrefix;
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public void setResourceBundle(final ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	private Class<? extends Object> getArgumentsClass() {
		return argumentsObject.getClass();
	}

	public void throwIllegalArgumentException() {
		errorHandler.setThrowIllegalArgumentException(true);
	}

	void showArguments(final String[] arguments) {
		final StringBuilder b = new StringBuilder();
		b.append("Arguments" + newline);
		for (final String arg : arguments) {
			b.append("'" + arg + "'" + newline);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(b.toString());
		}
	}
}
