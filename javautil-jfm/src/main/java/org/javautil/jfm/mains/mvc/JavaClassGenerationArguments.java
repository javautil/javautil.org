package org.javautil.jfm.mains.mvc;

import org.javautil.commandline.annotations.Optional;
import org.javautil.jfm.mains.FreeMarkerGeneratorArguments;
import org.javautil.jfm.model.JFMModel;

@JFMModel
public class JavaClassGenerationArguments extends FreeMarkerGeneratorArguments {

	@Optional
	private String className;

	@Optional
	private String packageName;

	public String getClassName() {
		return className;
	}

	public void setClassName(final String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(final String packageName) {
		this.packageName = packageName;
	}

}
