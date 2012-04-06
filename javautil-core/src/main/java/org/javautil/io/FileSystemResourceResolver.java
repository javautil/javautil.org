package org.javautil.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * A resource directory for locating files inside of the filesystem. This class
 * resolves resources like the FileSystemResourceLoader class from spring,
 * except that it allows relative file resolution to a root path. This resolver
 * will only return files and directories that return true from the method
 * isHiddenFile. Please note that the url prefixes classpath:// and file://
 * should not be present in the resource names.
 * 
 * @See {@link ResourceDirectory}
 * @See {@link ResourceLoader}
 */
public class FileSystemResourceResolver extends FileSystemResourceLoader
		implements ResourceDirectory, InitializingBean {

	private File rootPath = new File(".");

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * Default bean constructor
	 */
	public FileSystemResourceResolver() {
	}

	/**
	 * Preferred constructor
	 * 
	 * @param rootPath
	 */
	public FileSystemResourceResolver(final File rootPath) {
		this.rootPath = rootPath;
	}

	/**
	 * Used by the InitializingBean interface
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (rootPath == null) {
			throw new IllegalStateException("baseDirectory is null");
		}
	}

	/**
	 * This method should not be present in the spring FileSystemResourceLoader,
	 * we prevent it from running here just in case it is confusing.
	 */
	@Override
	public void setClassLoader(final ClassLoader classLoader) {
		throw new UnsupportedOperationException("use setRootPath instead");
	}

	/**
	 * This method should not be present in the spring FileSystemResourceLoader,
	 * we prevent it from running here just in case it is confusing.
	 */
	@Override
	public ClassLoader getClassLoader() {
		throw new UnsupportedOperationException("use setRootPath instead");
	}

	/**
	 * Used by the FileSystemResourceLoader class to resolve resources. The
	 * method is overridden to introduce the relative path.
	 * 
	 * @return resource
	 */
	@Override
	public Resource getResource(final String relativePath) {
		if (relativePath.startsWith("file://")) {
			throw new IllegalArgumentException("the url prefix file:// is not "
					+ "allowed");
		}
		if (relativePath.startsWith("classpath://")) {
			throw new IllegalArgumentException("the url prefix "
					+ "classpath:// is not " + "allowed");
		}
		try {
			File resource;
			resource = new File(rootPath, relativePath);
			final String location = resource.getCanonicalPath();
			if (logger.isDebugEnabled()) {
				logger.debug("rootPath=" + rootPath.getCanonicalPath() + ", "
						+ "relativePath=" + relativePath + ", resource="
						+ location);
			}
			if (!location.startsWith(rootPath.getCanonicalPath())) {
				throw new IllegalArgumentException("cannot get parent files "
						+ "of the rootPath");
			}
			return super.getResource("file://" + location);
		} catch (final IOException e) {
			throw new RuntimeException("error resolving " + relativePath, e);
		}
	}

	/**
	 * Returns the name of the root path
	 * 
	 * @return name
	 */
	@Override
	public String getName() {
		return rootPath.getName();
	}

	/**
	 * Returns all found resources at the root path.
	 * 
	 * @param recursive
	 * @throws IOException
	 */
	@Override
	public Collection<Resource> getResources(final boolean recursive)
			throws IOException {
		return findResources("/", recursive, false);
	}

	/**
	 * Returns all found resources (files) at a relative path.
	 * 
	 * @param relativePath
	 * @param recursive
	 * @throws IOException
	 */
	@Override
	public Collection<Resource> getResources(final boolean recursive,
			final String resource) throws IOException {
		return findResources(resource, recursive, false);
	}

	/**
	 * Returns all found resource directories at a relative path.
	 * 
	 * @param relativePath
	 * @param recursive
	 * @throws IOException
	 */
	@Override
	public Collection<ResourceDirectory> getResourceDirectories(
			final boolean recursive, final String resource) throws IOException {
		return asDirectories(findResources(resource, recursive, true));
	}

	/**
	 * Returns all found resource directories at the root path.
	 * 
	 * 
	 * @param recursive
	 * @throws IOException
	 */
	@Override
	public Collection<ResourceDirectory> getResourceDirectories(
			final boolean recursive) throws IOException {
		return asDirectories(findResources("/", recursive, true));
	}

	/**
	 * Returns the resource directory at a given path. If the directory does not
	 * exist, an IOException is thrown.
	 * 
	 * @param relativePath
	 * @throws IOException
	 */
	@Override
	public ResourceDirectory getResourceDirectory(final String relativePath)
			throws IOException {
		final File directory = findDirectory(relativePath);
		return new FileSystemResourceResolver(directory);
	}

	/**
	 * Internal method to translate resources into resource directories.
	 * 
	 * @param resources
	 * @return resource directories
	 */
	private Collection<ResourceDirectory> asDirectories(
			final Collection<Resource> resources) {
		final Collection<ResourceDirectory> directories = new ArrayList<ResourceDirectory>();
		try {
			for (final Resource resource : resources) {
				final File directory = resource.getFile();
				directories.add(new FileSystemResourceResolver(directory));
			}
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		return directories;
	}

	/**
	 * Internal method for listing resources from a directory. See also the
	 * isHiddenFile method.
	 * 
	 * @param directory
	 * @param recursive
	 * @param directories
	 * @return files
	 */
	private Collection<Resource> listResources(final File directory,
			final boolean recursive, final boolean directories) {
		final Collection<Resource> resources = new LinkedHashSet<Resource>();
		final Collection<File> files = listFiles(directory, recursive,
				directories);
		for (final File file : files) {
			if (!isHiddenFile(file, directories)) {
				resources.add(new FileSystemResource(file));
			}
		}
		return resources;
	}

	/**
	 * Internal method for finding desired resources in a path.
	 * 
	 * @param relativePath
	 * @param recursive
	 * @param directories
	 * @return resources
	 * @throws IOException
	 */
	protected Collection<Resource> findResources(final String relativePath,
			final boolean recursive, final boolean directories)
			throws IOException {
		final File directory = findDirectory(relativePath);
		if (logger.isDebugEnabled()) {
			logger.debug("findResources in directory " + directory
					+ ", recursive=" + recursive + ", directories="
					+ directories);
		}
		final Collection<Resource> resources = listResources(directory,
				recursive, directories);
		if (logger.isTraceEnabled()) {
			logger.trace(getFindResourcesLogMessage(directory, recursive,
					directories, resources));
		}
		return resources;
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
	public String getFindResourcesLogMessage(final File directory,
			final boolean recursive, final boolean directories)
			throws IOException {
		final Collection<Resource> resources = listResources(directory,
				recursive, directories);
		return getFindResourcesLogMessage(directory, recursive, directories,
				resources);
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
	protected String getFindResourcesLogMessage(final File directory,
			final boolean recursive, final boolean directories,
			final Collection<Resource> resources) throws IOException {
		final StringBuilder buffy = new StringBuilder();
		buffy.append(resources.size() + " resources found ");
		if (recursive) {
			buffy.append("recursively ");
		}
		buffy.append("for directory \"" + directory.getPath() + "\"");
		try {
			if (resources.size() > 0) {
				buffy.append(", resources:\n");
				for (final Resource resourceItem : resources) {
					buffy.append("\t");
					buffy.append(resourceItem.getURI());
					buffy.append("\n");
				}
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		return buffy.toString();
	}

	/**
	 * Internal method for listing files that will be used as resources.
	 * 
	 * @param rootDirectory
	 * @param recursive
	 * @param directories
	 * @return files
	 */
	private Collection<File> listFiles(final File rootDirectory,
			final boolean recursive, final boolean directories) {
		final Collection<File> files = new ArrayList<File>();
		final List<File> directoriesToProcess = new ArrayList<File>();
		directoriesToProcess.add(rootDirectory);
		while (directoriesToProcess.size() > 0) {
			final File dir = directoriesToProcess.remove(0);
			if (directories && !dir.equals(rootDirectory)) {
				files.add(dir);
			}
			final File[] directoryFiles = dir.listFiles();
			for (final File file : directoryFiles) {
				final boolean isDir = file.isDirectory();
				if (recursive && isDir) {
					directoriesToProcess.add(file);
				}
				if (directories == isDir) {
					files.add(file);
				}
			}
		}
		return files;
	}

	/**
	 * Returns a file relative to the root directory; throwing IOExceptions
	 * where files cannot be resolved.
	 * 
	 * @param relativePath
	 * @return directory
	 * @throws IOException
	 */
	protected File findDirectory(final String relativePath) throws IOException {
		File directory = null;
		if (relativePath.equals("/")) {
			directory = rootPath;
		} else {
			directory = new File(rootPath, relativePath);
		}
		final String location = directory.getAbsolutePath();
		if (!location.startsWith(rootPath.getAbsolutePath())) {
			throw new IllegalArgumentException("cannot get parent files "
					+ "of the rootPath");
		}
		if (!directory.exists()) {
			throw new FileNotFoundException("no such directory "
					+ directory.getAbsolutePath());
		}
		if (!directory.canRead()) {
			throw new IOException("cannot read directory "
					+ directory.getAbsolutePath());
		}
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException(directory.getPath()
					+ " is not a directory");
		}
		return directory;
	}

	/**
	 * Method for determining whether or not a file should be shown. This is
	 * helpful as filesystems in general tend to hide certain files by default,
	 * and this behavior is most always often desired from programs.
	 * 
	 * @param file
	 * @param findDirectoriesInsteadOfFiles
	 * @return true if hidden
	 */
	protected boolean isHiddenFile(final File file,
			final boolean findDirectoriesInsteadOfFiles) {
		final File parentFile = file.getParentFile();
		final String name = file.getName();
		final String parentName = parentFile.getName();
		final boolean isDir = file.isDirectory();
		final boolean isCvsDir = name.equals("CVS") && isDir;
		final boolean isDotFile = name.startsWith(".");
		final boolean ignoreFile = isDotFile || isCvsDir;
		final boolean parentIsDir = parentFile.isDirectory();
		final boolean parentIsCvsDir = parentName.equals("CVS") && parentIsDir;
		final boolean ignoreParentFile = parentIsCvsDir
				|| isDir != findDirectoriesInsteadOfFiles;
		return ignoreFile || ignoreParentFile;
	}

	/**
	 * Returns the root path for resolving resources.
	 * 
	 * This is the base directory from which all other resources are relative.
	 * 
	 * Following the Spring convention a leading "/" on a path is ignored.
	 * 
	 * @return root directory
	 */
	public File getRootPath() {
		return rootPath;
	}

	/**
	 * Assigns a root path for resolving resources.
	 * 
	 * @param rootPath
	 */
	public void setRootPath(final File rootPath) {
		this.rootPath = rootPath;
	}
}