package org.javautil.io;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class ResourceUtils {

	public static Resource asResource(final String path) {
		Resource ret = null;
		try {
			if (path.startsWith("http:") || path.startsWith("https:")) {
				ret = new UrlResource(path);
			} else if (path.startsWith("classpath:")) {
				ret = new ClassPathResource(path.substring(10));
			} else if (path.startsWith("file:")) {
				ret = new FileSystemResource(path.substring(5));
			} else {
				throw new IllegalArgumentException("unknown path type for "
						+ "resource: " + path);
			}
		} catch (final Exception e) {
			throw new RuntimeException("error getting resource from path: "
					+ path);
		}
		return ret;
	}

}
