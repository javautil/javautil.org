package org.javautil.document;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 *         TODO this is not referenced
 * 
 */
public interface ReportGenerator {

	public abstract void generate(String datasourceName,
			Map<String, Object> parms, OutputStream os) throws IOException;

}
