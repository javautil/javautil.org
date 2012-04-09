package org.javautil.file;

import java.io.IOException;

public interface FileInfoListener {
	public void processFileInfo(FileInfo fileInfo) throws IOException;
}
