package org.javautil.jfm.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.jfm.ClassUtils;
import org.javautil.text.StringUtils;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateModelException;

/**
 * Builds a model object capable of storing annotated JFM fields in freemarker.
 * 
 * @author bcm-javautil
 */
public class JFMModelBuilder {

	private final Log logger = LogFactory.getLog(getClass());

	public Object buildModelObject(final Object jfmAnnotatedBean)
			throws TemplateModelException {
		final BeansWrapper wrapper = new BeansWrapper();
		wrapper.setSimpleMapWrapper(true);
		final Map<String, Object> modelProperties = getAnnotatedModelMap(jfmAnnotatedBean);
		return wrapper.wrap(modelProperties);
	}

	public Map<String, Object> getAnnotatedModelMap(
			final Object jfmAnnotatedBean) {
		final Map<String, Object> model = new HashMap<String, Object>();
		final Stack<Class<? extends Object>> classes = new Stack<Class<? extends Object>>();
		classes.add(jfmAnnotatedBean.getClass());
		while (classes.size() > 0) {
			final Class<? extends Object> clazz = classes.pop();
			boolean autoExpose = false;
			if (clazz.getAnnotation(JFMModel.class) != null) {
				autoExpose = true;
			}
			final Field[] fields = clazz.getDeclaredFields();
			for (final Field field : fields) {
				if (!ClassUtils.isJavaStandardEditionOrExtensionClass(clazz)) {
					field.setAccessible(true);
					addToModel(jfmAnnotatedBean, field, model, autoExpose);
				}
			}
			for (final Class<? extends Object> otherClass : clazz
					.getInterfaces()) {
				classes.add(otherClass);
			}
			if (clazz.getSuperclass() != null) {
				classes.add(clazz.getSuperclass());
			}
		}
		return model;
	}

	protected void addToModel(final Object bean, final Field field,
			final Map<String, Object> model, final boolean autoExpose) {
		String modelPath = field.getName();
		final JFMModelProperty modelProperty = field
				.getAnnotation(JFMModelProperty.class);
		if (modelProperty != null && modelProperty.name() != null) {
			modelPath = modelProperty.name();
		}
		if (modelProperty != null || autoExpose) {
			if (model.containsKey(modelPath)) {
				throw new IllegalStateException("JFMModelProperty or "
						+ "JFMModel annotation includes two or more "
						+ "fields on class hierarchy for class "
						+ bean.getClass().getName() + " mapped to the same "
						+ "name: " + modelPath + "; \n\nmodel:\n"
						+ StringUtils.asString(model));
			}
			try {
				model.put(modelPath, field.get(bean));
			} catch (final IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			logger.debug("added object " + bean + " to model as " + modelPath);
		}
	}

}
