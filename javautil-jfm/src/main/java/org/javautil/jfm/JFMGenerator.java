package org.javautil.jfm;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import freemarker.template.TemplateException;

/**
 * Implementors generate content to an outputstream using a rendering engine.
 * 
 * @author bcm-javautil
 */
public interface JFMGenerator extends InitializingBean {

	public void setTemplateReader(Reader reader);

	public Reader getTemplateReader();

	public void setModel(Map<String, Object> model);

	public Map<String, Object> getModel();

	public void generate(OutputStream outputStream) throws TemplateException,
			IOException;
}
