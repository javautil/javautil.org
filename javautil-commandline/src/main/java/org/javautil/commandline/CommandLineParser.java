package org.javautil.commandline;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jcmdline.CmdLineException;
import jcmdline.PosixCmdLineParser;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author bcm
 */
public class CommandLineParser extends PosixCmdLineParser {

	private static final String FROM_PROPERTIES = "--from-properties";

	private boolean ignoreUnrecognizedOptions = true;

	private final Log logger = LogFactory.getLog(getClass());

	@Override
	public void parse(final String[] clargs, final Map opts, final List args)
			throws CmdLineException {
		try {
			super.parse(clargs, opts, args);
		} catch (final CmdLineException e) {
			if (!ignoreUnrecognizedOptions
					|| !e.getMessage().endsWith("is not a valid option.")) {
				throw e;
			}
		}
	}

	public boolean isIgnoreUnrecognizedOptions() {
		return ignoreUnrecognizedOptions;
	}

	public void setIgnoreUnrecognizedOptions(
			final boolean ignoreUnrecognizedOptions) {
		this.ignoreUnrecognizedOptions = ignoreUnrecognizedOptions;
	}

	// public String[]
	// parseFileArgumentsFromPropertiesFile(Map<String,Parameter> parms,
	// String[] args) {
	// List<String> newArgs = new LinkedList<String>();
	// boolean nextArgIsProperties = false;
	// for (String arg : args) {
	// if (nextArgIsProperties || arg.startsWith(FROM_PROPERTIES)) {
	// if (logger.isDebugEnabled()) {
	// logger.debug("parsing ");
	// }
	// PropertiesConfiguration config;
	// String filePath = null;
	//
	// if (nextArgIsProperties) {
	// nextArgIsProperties = false;
	// filePath = arg;
	// } else {
	// if (arg != null && arg.startsWith(FROM_PROPERTIES)) {
	// filePath = arg.replace(FROM_PROPERTIES, "");
	// } else {
	// nextArgIsProperties = true;
	// }
	// }
	//
	// if (filePath != null) {
	// config = getPropertiesConfiguration(filePath);
	// newArgs.addAll(constructArgumentStringFromProperties(config));
	// }
	// } else {
	// newArgs.add(arg);
	// }
	// }
	// return newArgs.toArray(new String[newArgs.size()]);
	// }

	PropertiesConfiguration getPropertiesConfiguration(
			final String configFileName) {
		if (logger.isDebugEnabled()) {
			logger.debug("path name is '" + configFileName + "'");
		}
		final PropertiesConfiguration config = new PropertiesConfiguration();
		config.setDelimiterParsingDisabled(true);
		final File configFile = new File(configFileName);
		config.setFile(configFile);
		try {
			config.load();
		} catch (final ConfigurationException e) {
			throw new RuntimeException("error while opening properties file: "
					+ configFile, e);
		}
		return config;
	}

	public String[] parseArgumentsAsParameters(final String[] args) {
		final List<String> newArgs = new LinkedList<String>();
		boolean nextArgIsProperties = false;
		if (logger.isDebugEnabled()) {
			logger.debug("args " + args.length);
		}

		String filePath = null;
		for (final String arg : args) {
			if (logger.isDebugEnabled()) {
				logger.debug("looking at arg '" + arg + "'");
			}
			if (nextArgIsProperties || arg.startsWith(FROM_PROPERTIES)) {
				if (nextArgIsProperties) {
					nextArgIsProperties = false;
					filePath = arg;
					if (logger.isDebugEnabled()) {
						logger.debug("filePath " + filePath);
					}
				} else {
					if ((arg != null) && arg.startsWith(FROM_PROPERTIES)) {
						filePath = getFilePath(arg);
					}
					if (filePath == null) {
						nextArgIsProperties = true;
					}
				}
				if (filePath != null) {
					final PropertiesConfiguration config = getPropertiesConfiguration(filePath);
					newArgs.addAll(constructArgumentStringFromProperties(config));
				}
			} else {
				newArgs.add(arg);
			}

		}
		return newArgs.toArray(new String[newArgs.size()]);
	}

	String getFilePath(final String arg) {
		String filePath = arg.replace(FROM_PROPERTIES, "");
		if ((filePath.length() > 0) && (filePath.charAt(0) == '=')) {
			filePath = filePath.substring(1);
			logger.debug("filePath left trimmed to '" + filePath + "'");
		} else {
			logger.debug("no start with =" + arg);
			if (logger.isDebugEnabled()) {
				logger.debug("setting nextArgIsProperties to true");
			}

		}
		if (filePath.length() == 0) {
			filePath = null;
		}
		return filePath;
	}

	@SuppressWarnings("unchecked")
	public List<String> constructArgumentStringFromProperties(
			final PropertiesConfiguration props) {
		final List<String> arguments = new LinkedList<String>();
		final Iterator<String> it = props.getKeys();
		while (it.hasNext()) {
			final String key = it.next();
			final List<String> values = props.getList(key);
			for (final String value : values) {
				arguments.add("-" + key + "=" + value);
			}
		}
		return arguments;
	}

}
