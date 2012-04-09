package org.javautil.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

/**
 * Provides utility methods for use with any resource directory.
 * 
 * @author bcm
 */
public class ResourceHelper implements InitializingBean {

	private final Log logger = LogFactory.getLog(getClass());

	private ResourceDirectory resourceDirectory;

	/**
	 * java beans support constructor
	 */
	public ResourceHelper() {
	}

	/**
	 * preferred constructor
	 * 
	 * @param resourceDirectory
	 */
	public ResourceHelper(final ResourceDirectory resourceDirectory) {
		if (resourceDirectory == null) {
			throw new IllegalArgumentException("resourceDirectory is null");
		}
		this.resourceDirectory = resourceDirectory;
	}

	/**
	 * Populates an internal tree representation of all child resources.
	 * 
	 * @param parent
	 * @param root
	 * @param dirName
	 * @return root of the tree
	 * @throws IOException
	 */
	protected Directory populateTree(final Directory parent,
			final ResourceDirectory root, final String dirName,
			final List<String> done) throws IOException {
		final Directory node = new Directory();
		node.name = dirName;
		node.parent = parent;
		node.resources = root.getResources(false);
		node.directories = new ArrayList<Directory>();
		final Collection<ResourceDirectory> dirs = root
				.getResourceDirectories(false);
		if (logger.isDebugEnabled()) {
			logger.debug(node.getPath() + " has " + dirs.size() + " children");
		}
		for (final ResourceDirectory dir : dirs) {
			final String childPath = node.getChild(dir.getName());
			if (logger.isDebugEnabled()) {
				final StringBuilder message = new StringBuilder();
				message.append("populating tree for " + childPath);
				if (parent != null) {
					message.append(" from " + node.getPath());
				}
				logger.debug(message);
			}
			if (done.contains(childPath)) {
				throw new IllegalStateException("resource \"" + childPath
						+ "\" appears more than once in the resource tree. "
						+ " It is a child of resource + \"" + node.getPath()
						+ "\"; this is not allowed as it will cause"
						+ " an infinite loop; other directories found: "
						+ Arrays.toString(done.toArray()));
			}
			done.add(childPath);
			node.directories.add(populateTree(node, dir, dir.getName(), done));
		}
		return node;
	}

	/**
	 * Gets a map of a resources known to the resolver. All resources are found,
	 * beginning at the rootPath. The key is a relative path to the resource
	 * from the original ResourceDirectory (that could be used to fetch the
	 * resource again from the same ResourceDirectory) and the map value is the
	 * resource itself.
	 * 
	 * @return path mapping
	 * @throws IOException
	 */
	public Map<String, Resource> getPathToResourceMapping() throws IOException {
		final Directory tree = populateTree(null, resourceDirectory, "",
				new ArrayList<String>());
		final Map<String, Resource> pathMap = new LinkedHashMap<String, Resource>();
		final List<Directory> unprocessed = new ArrayList<Directory>();
		unprocessed.add(tree);
		while (unprocessed.size() > 0) {
			final Directory current = unprocessed.remove(0);
			if (current.directories != null) {
				unprocessed.addAll(current.directories);
			}
			for (final Resource resource : current.resources) {
				final String name = current.getChild(resource.getFilename());
				pathMap.put(name, resource);
			}
		}
		return pathMap;
	}

	/**
	 * Internal class for use with collecting the available directory tree.
	 */
	private class Directory {

		Directory parent;
		String name;
		Collection<Resource> resources;
		Collection<Directory> directories;

		public String getPath() {
			final StringBuilder path = new StringBuilder();
			Directory current = this;
			while (current != null) {
				if (current.name.length() > 0) {
					if (current.parent != null) {
						path.insert(0, "/");
					}
					path.insert(0, current.name);
				}
				current = current.parent;
			}
			return path.toString();
		}

		public String getChild(final String child) {
			final StringBuilder path = new StringBuilder(getPath());
			if (path.length() > 0 && !path.toString().endsWith("/")) {
				path.append("/");
			}
			path.append(child);
			return path.toString();
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (resourceDirectory == null) {
			throw new IllegalStateException("resourceDirectory is null");
		}
	}

}
