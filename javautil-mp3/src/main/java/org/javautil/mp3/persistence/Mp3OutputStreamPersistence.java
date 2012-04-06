package org.javautil.mp3.persistence;

import java.io.OutputStream;

public interface Mp3OutputStreamPersistence extends Mp3Persistence {

	void setOutputStream(OutputStream os);
}
