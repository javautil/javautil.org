package org.javautil.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.lang.ClassUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * A resource directory for locating resources in the classpath. This class
 * resolves resources like the DefaultResourceLoader class from spring, except
 * that it allows relative file resolution to a root path. Please note that the
 * url prefixes classpath:// and file:// should not be present in the resource
 * names.
 * 
 * @See {@link ResourceDirectory}
 * @See {@link ResourceLoader}
 */
public class ClassPathResourceResolver extends DefaultResourceLoader implements
		ResourceDirectory, InitializingBean {

	private Class<? extends Object> resolvingClass = null;

	private String rootPath;

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * Default constructor for beans support.
	 */
	public ClassPathResourceResolver() {
	}

	/**
	 * Preferred Constructor.
	 * 
	 * @param resolvingClass
	 */
	public ClassPathResourceResolver(
			final Class<? extends Object> resolvingClass) {
		if (resolvingClass == null) {
			throw new IllegalArgumentException("resolvingClass is null");
		}
		this.resolvingClass = resolvingClass;
	}

	/**
	 * Additional Constructor for using a specified rootPath.
	 */
	public ClassPathResourceResolver(final String rootPath) {
		setRootPath(rootPath);
	}

	/**
	 * Additional Constructor for using a default path under for relative
	 * resolution.
	 */
	public ClassPathResourceResolver(final ClassLoader classLoader,
			final String _rootPath) {
		setClassLoader(classLoader);
		String rootPath = _rootPath;
		if (rootPath != null && rootPath.startsWith("/")) {
			rootPath = rootPath.substring(1, rootPath.length());
		}
		logger.debug("created with rootPath=" + rootPath);
		setRootPath(rootPath);
	}

	/**
	 * Needed for InitializingBean support.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		final ClassLoader defaultClassLoader = org.springframework.util.ClassUtils
				.getDefaultClassLoader();
		if (resolvingClass != null) {
			setClassLoader(resolvingClass.getClassLoader());
		}
		// set a default rootPath from the package name of the resolving class
		if (rootPath == null && resolvingClass != null) {
			rootPath = resolvingClass.getPackage().getName().replace('.', '/');
		}
		// fallback to a default rootPath
		if (rootPath == null) {
			rootPath = "";
		}
		if (getClassLoader() == null) {
			setClassLoader(defaultClassLoader);
		}
	}

	/**
	 * Internal method to aid in getting the name of a child resource of the
	 * rootPath.
	 */
	protected String deriveResourcePath(final String resourceName) {
		if (resourceName == null) {
			throw new IllegalArgumentException("resourceName is null");
		}
		if (rootPath == null) {
			throw new IllegalStateException(
					"rootPath is null, did you call afterPropertiesSet ?");
		}
		final StringBuilder buffy = new StringBuilder(rootPath);
		if (!resourceName.startsWith("/")) {
			buffy.append("/");
		}
		buffy.append(resourceName);
		if (buffy.charAt(buffy.length() - 1) == '/') {
			buffy.setLength(buffy.length() - 1);
		}
		return buffy.toString();
	}

	/**
	 * Used by the DefaultResourceLoader class to resolve resources. The method
	 * is overridden to introduce the relative path.
	 * 
	 * @return resource
	 */
	@Override
	public Resource getResource(final String relativePath) {
		if (rootPath == null) {
			throw new IllegalStateException(
					"rootPath is null, did you call afterPropertiesSet ?");
		}
		if (relativePath.startsWith("classpath://")) {
			throw new IllegalArgumentException("the url prefix classpath:// "
					+ " is not allowed");
		}
		if (relativePath.startsWith("file://")) {
			throw new IllegalArgumentException("the url prefix file:// "
					+ " is not allowed");
		}
		try {
			final String resourcePath = deriveResourcePath(relativePath);
			final Resource resource = super.getResource("classpath:/"
					+ resourcePath);
			String uri = null;
			final boolean exists = resource.exists();
			if (exists) {
				if (logger.isDebugEnabled()) {
					logger.debug("resource is " + resource);
				}
				uri = resource.getURI().toString();
				if (logger.isDebugEnabled()) {
					logger.debug("uri is " + uri);
				}
			}
			if ((exists && !uri.contains(rootPath))
					|| (!exists && resourcePath.contains(".."))) {
				throw new IllegalArgumentException("cannot get parent files "
						+ "of the rootPath");
			}
			return resource;
		} catch (final IOException e) {
			throw new RuntimeException("error resolving " + relativePath, e);
		}
	}

	/**
	 * Returns the name of the last part of the classpath
	 * 
	 * @return name
	 */
	@Override
	public String getName() {
		String path = deriveResourcePath("");
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		final String[] paths = path.split("/");
		return paths[paths.length - 1];
	}

	/**
	 * @see {@link ResourceDirectory#getResourceDirectories}
	 */
	@Override
	public Collection<ResourceDirectory> getResourceDirectories(
			final boolean recursive, final String relativePath)
			throws IOException {
		final Collection<String> resourceNames = findResourceNames(
				relativePath, recursive, true);
		return asDirectories(resourceNames);
	}

	/**
	 * @see {@link ResourceDirectory#getResourceDirectories}
	 */
	@Override
	public Collection<ResourceDirectory> getResourceDirectories(
			final boolean recursive) throws IOException {
		final Collection<String> resourceNames = findResourceNames("",
				recursive, true);
		return asDirectories(resourceNames);
	}

	/**
	 * @see {@link ResourceDirectory#getResourceDirectory}
	 */
	@Override
	public ResourceDirectory getResourceDirectory(final String relativePath)
			throws IOException {
		final String resourcePath = deriveResourcePath(relativePath);
		final ClassPathResourceResolver resourceDirectory = new ClassPathResourceResolver(
				getClassLoader(), resourcePath);
		return resourceDirectory;
	}

	/**
	 * @see {@link ResourceDirectory#getResources}
	 */
	@Override
	public Collection<Resource> getResources(final boolean recursive)
			throws IOException {
		final Collection<String> resources = findResourceNames("", recursive,
				false);
		return asResources(resources);
	}

	/**
	 * @see {@link ResourceDirectory#getResources}
	 */
	@Override
	public Collection<Resource> getResources(final boolean recursive,
			final String relativePath) throws IOException {
		final Collection<String> resources = findResourceNames(relativePath,
				recursive, false);
		return asResources(resources);
	}

	/**
	 * Internal method to translate resources into resource directories.
	 * 
	 * @param path
	 * @param resources
	 * @return resource directories
	 */
	protected Collection<ResourceDirectory> asDirectories(
			final Collection<String> classpaths) {
		final ArrayList<ResourceDirectory> resourceDirectories = new ArrayList<ResourceDirectory>();
		for (final String classpath : classpaths) {
			final ClassPathResourceResolver resourceDirectory = new ClassPathResourceResolver(
					getClassLoader(), classpath);
			resourceDirectories.add(resourceDirectory);
		}
		return resourceDirectories;
	}

	/**
	 * Internal method for converting resource names to ClassPathResource
	 * objects.
	 * 
	 * @param resourceNames
	 * @return resources
	 */
	protected Collection<Resource> asResources(
			final Collection<String> resourceNames) {
		final List<Resource> resources = new ArrayList<Resource>();
		final ClassLoader classLoader = getClassLoader();
		for (final String resourceName : resourceNames) {
			resources.add(new ClassPathResource(
					deriveResourcePath(resourceName), classLoader));
		}
		return resources;
	}

	/**
	 * Internal method for searching the classpath for matching resources. This
	 * is fairly intensive for large classpaths, and is not optimized at the
	 * time of this writing.
	 * 
	 * TODO review
	 * 
	 * @param resourceName
	 * @param recursive
	 * @param directories
	 * @return resourceNames
	 */
	protected Collection<String> findResourceNames(final String resourceName,
			final boolean recursive, final boolean directories) {
		if (logger.isDebugEnabled()) {
			logger.debug("findResourceNames for resourceName: " + resourceName
					+ ", recursive= " + recursive + ", " + "directories= "
					+ directories);
		}
		final Collection<String> resourceNames = listResourceNames(
				resourceName, recursive, directories);
		// log the results
		if (logger.isTraceEnabled()) {
			try {
				logger.trace(getFindResourcesLogMessage(resourceName,
						recursive, directories, resourceNames));
			} catch (final IOException e) {
				throw new RuntimeException("error while listing resources", e);
			}
		}
		return resourceNames;
	}

	/**
	 * Useful for logging the files that the resource loader found without
	 * actually returning the files themselves.
	 * 
	 * @param directory
	 * @param recursive
	 * @param directories
	 * @return
	 * @throws IOException
	 */
	public String getFindResourcesLogMessage(final String classpath,
			final boolean recursive, final boolean directories)
			throws IOException {
		final Collection<String> resourceNames = listResourceNames(classpath,
				recursive, directories);
		return getFindResourcesLogMessage(classpath, recursive, directories,
				resourceNames);
	}

	/**
	 * Provides internal log message to reference which resources were found.
	 * 
	 * @param directory
	 * @param recursive
	 * @param directories
	 * @param resources
	 * @return message
	 * 
	 * @throws IOException
	 */
	protected String getFindResourcesLogMessage(final String classpath,
			final boolean recursive, final boolean directories,
			final Collection<String> resourceNames) throws IOException {
		final StringBuilder buffy = new StringBuilder();
		try {
			buffy.append(resourceNames.size() + " resources found ");
			if (recursive) {
				buffy.append("recursively ");
			}
			buffy.append("using classloader path \"" + classpath + "\"");
			if (resourceNames.size() > 0) {
				buffy.append(" resource names:\n");
				for (final String resourceName : resourceNames) {
					buffy.append("\t");
					buffy.append(resourceName);
					buffy.append("\n");
				}
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		return buffy.toString();
	}

	protected Collection<String> listResourceNames(final String resourcePath,
			final boolean recursive, final boolean directories) {
		Collection<String> resourceNames = new ArrayList<String>();
		String path = deriveResourcePath(resourcePath);
		if (path.startsWith("/")) {
			path = path.substring(1, path.length());
		}
		logger.debug("beginning classpath search for path: " + path);
		// get the resources from the classpath, removing any leading slashes
		// and only including directories when
		try {
			final Map<String, Set<Object>> resourcesMap = ClassUtils
					.findResources(getClassLoader(), null,
							path.length() == 0 ? null : path, null, null);
			for (String resource : resourcesMap.keySet()) {
				resource = resource.startsWith("/") ? resource.substring(1,
						resource.length()) : resource;
				if (directories || !resource.endsWith("/")) {
					resourceNames.add(resource);
				}
			}
		} catch (final Exception e) {
			logger.error("error while looking for resources in classpath", e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("collected " + resourceNames.size()
					+ " unfiltered resources");
		}
		// collect only either files or directories from the resource paths
		if (directories) {
			resourceNames = findDirectoriesForResources(resourceNames, path,
					recursive);
		} else {
			resourceNames = findFilesForResources(resourceNames, path,
					recursive);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("accepted " + resourceNames.size()
					+ " resources from unfiltered resources");
		}
		return resourceNames;
	}

	/**
	 * Internal method to get files from resource names.
	 * 
	 * @param resources
	 * @param parent
	 * @param recursive
	 * @return files
	 */
	protected Collection<String> findFilesForResources(
			final Collection<String> resources, final String parent,
			final boolean recursive) {
		final Set<String> files = new LinkedHashSet<String>();
		for (final String resource : resources) {
			// we are only interested in resources that are below the parent
			final boolean matchesParent = resource.startsWith(parent + "/");
			boolean matches = false;
			String localResource = resource;
			if (matchesParent) {
				localResource = resource.substring(parent.length() + 1);
				matches = true;
			}
			if (parent.length() == 0) {
				matches = true;
			}
			if (matches) {
				if (recursive) {
					files.add(localResource);
				} else if (parent.length() == 0
						|| localResource.indexOf("/") == -1) {
					files.add(localResource);
				}
			}
		}
		return files;
	}

	/**
	 * Internal method to derive the available classpath "directories" from
	 * resource names. NOTE: this does not include empty folders in the
	 * classpath at the current time.
	 * 
	 * @param resources
	 * @return directories
	 */
	protected Collection<String> findDirectoriesForResources(
			final Collection<String> resources, final String rootPath,
			final boolean recursive) {
		final Set<String> directories = new LinkedHashSet<String>();
		for (final String resource : resources) {
			final int ndx = resource.lastIndexOf("/");
			boolean recursionPassed = recursive;
			if (ndx != -1) {
				final String resourcePath = resource.substring(0, ndx);
				if (!resourcePath.startsWith(this.rootPath)) {
					recursionPassed = false;
				} else if (!recursive) {
					String rest = "";
					logger.debug("rootPath is " + rootPath);
					logger.debug("resource is " + resourcePath);
					if (rootPath.length() != 0
							&& resourcePath.startsWith(rootPath + "/")) {
						rest = resourcePath.substring(rootPath.length());
					}
					if (rest.startsWith("/")) {
						rest = rest.substring(1);
					}
					logger.debug("rest is " + rest);
					recursionPassed = rest.split("/").length == 1;
				}
				if (!resourcePath.equals(rootPath) && (recursionPassed)) {
					directories.add(resourcePath);
				}
			}
		}
		return directories;
	}

	public Class<? extends Object> getResolvingClass() {
		return resolvingClass;
	}

	public void setResolvingClass(final Class<? extends Object> resolvingClass) {
		this.resolvingClass = resolvingClass;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(final String rootPath) {
		this.rootPath = rootPath;
	}

}
