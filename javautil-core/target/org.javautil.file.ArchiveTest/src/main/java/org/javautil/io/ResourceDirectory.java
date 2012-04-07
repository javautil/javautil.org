package org.javautil.io;

import java.io.IOException;
import java.util.Collection;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Extends ResourceLoader interface from spring to include resource lookup
 * capability. When looking up resource names or returning resources,
 * ResourceLoader implementations are to use relative paths. The root path of
 * the resolution is not exposed in the interface, but is expected to be set by
 * default, as the method getResources does not require a relativePath argument.
 * 
 * @See {@link ResourceLoader}
 * @See {@link Resource}
 */
public interface ResourceDirectory extends ResourceLoader {

	/**
	 * The name of the resource that represents the current directory or path.
	 * For filesystems and urls, this would typically be the directory name.
	 * 
	 * @return name
	 */
	public String getName();

	/**
	 * Discovers resources at the root path. Only resources will be returned,
	 * not directories or paths that contain other resources. This search should
	 * also optionally recurse into these directories and paths.
	 * 
	 * @throws {@link IOException}
	 * @param recursive
	 * @return resources
	 */
	public Collection<Resource> getResources(boolean recursive)
			throws IOException;

	/**
	 * Discovers resources at a given relative path. Only resources will be
	 * returned, not directories or paths that contain other resources. This
	 * search should also optionally recurse into these directories and paths.
	 * 
	 * @throws {@link IOException}
	 * @param relativePath
	 * @param recursive
	 * @return resources
	 */
	public Collection<Resource> getResources(boolean recursive,
			String relativePath) throws IOException;

	/**
	 * Discovers other resource directories at the root path. No resources will
	 * be returned, This search should also optionally recurse into these
	 * directories and paths.
	 * 
	 * @throws {@link IOException}
	 * @param recursive
	 * @return resourceDirectories
	 */
	public Collection<ResourceDirectory> getResourceDirectories(
			boolean recursive, String relativePath) throws IOException;

	/**
	 * Discovers other resource directories at a given relative path. No
	 * resources will be returned, This search should also optionally recurse
	 * into these directories and paths.
	 * 
	 * @throws {@link IOException}
	 * @param recursive
	 * @return resourceDirectories
	 */
	public Collection<ResourceDirectory> getResourceDirectories(
			boolean recursive) throws IOException;

	/**
	 * Returns another resource directory at the given relative path. If the
	 * relative path does exist or cannot be accessed, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @throws IllegalArgumentException
	 * @throws {@link IOException}
	 * @param relativePath
	 * @return resourceDirectory
	 */
	public ResourceDirectory getResourceDirectory(String relativePath)
			throws IOException;
}
