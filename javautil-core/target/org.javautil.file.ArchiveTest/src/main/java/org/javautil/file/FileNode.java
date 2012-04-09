package org.javautil.file;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Container for information about a file.
 */

public class FileNode {
	private static final String className = "com.javautil.util.FileNode";
	private static final Logger logger = Logger.getLogger(className);

	private final File file;
	private long lastModifiedCache;
	private final ArrayList<FileNode> children = new ArrayList<FileNode>();
	private final FileNode parent = null;

	public FileNode(final File file, final FileNode parent) {
		logger.debug("file: " + file + " parent " + parent);
		this.file = file;
		refresh();
	}

	public FileNode(final String fileName, final FileNode parent) {
		logger.setLevel(Level.INFO);
		logger.debug("fileName: " + fileName + " parent " + parent);
		this.file = new File(fileName);
		refresh();
	}

	/**
	 * Adds a child node after ensuring that the child is not the parent or any
	 * ancestor of the parent.
	 * 
	 * If a violation is found an IllegalStateException is thrown.
	 */
	public void addChild(final File file) throws java.io.IOException {
		final FileNode node = new FileNode(file, this);

		// check to make sure that we are not in Arkansas

		for (FileNode ancestor = this; ancestor != null; ancestor = ancestor.parent) {
			if (ancestor.file.getCanonicalFile().equals(file)) {
				throw new java.lang.IllegalStateException("Attempt to add "
						+ file + " as child to " + this.file
						+ " violating ancestor " + ancestor.file);
			}
		}

		children.add(node);
	}

	/**
	 * Returns true if this node or any of its children, recursively, have a
	 * modified date stamp different from the file modification time.
	 */
	public boolean hasModifiedNodes() {
		return modifiedNodes().length > 0;
	}

	/**
	 * Returns true if the lastModifiedCacheDate is different from that of the
	 * file.
	 * 
	 * This returns true even if the cached date is after the that of the file.
	 * The logic here is that somebody tried to update the timestamp with unix
	 * touch command or something similar and messed up.
	 * 
	 * Applies only to this node.
	 */
	public boolean needsRefresh() {
		return this.lastModifiedCache != file.lastModified();
	}

	public void refresh() {
		this.lastModifiedCache = file.lastModified();
	}

	private FileNode[] modifiedNodes() {
		// declarations

		// HashMap is used to throw away the duplicate reporting of a node
		// generated during recursive processing
		final HashMap<FileNode, FileNode> nodes = new HashMap<FileNode, FileNode>();

		FileNode[] fileNodes;
		// check this node
		if (this.lastModifiedCache != file.lastModified()) {
			nodes.put(this, this);
		}
		// recursively process all of the children
		final Iterator<FileNode> it = children.iterator();
		while (it.hasNext()) {
			final FileNode node = it.next();
			final FileNode[] mods = node.modifiedNodes();
			for (final FileNode mod : mods) {
				nodes.put(mod, mod);
			}
		}
		// construct return
		fileNodes = new FileNode[nodes.size()];
		final Iterator<FileNode> nodeIt = nodes.values().iterator();
		for (int ndx = 0; nodeIt.hasNext(); ndx++) {
			final FileNode node = nodeIt.next();
			fileNodes[ndx] = node;
		}
		// return
		return fileNodes;
	}

}