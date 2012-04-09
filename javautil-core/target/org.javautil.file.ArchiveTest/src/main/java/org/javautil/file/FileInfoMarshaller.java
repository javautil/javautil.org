package org.javautil.file;

import java.io.IOException;

public interface FileInfoMarshaller {

	public abstract void processFileInfo(final FileInfo fileInfo)
			throws IOException;

}