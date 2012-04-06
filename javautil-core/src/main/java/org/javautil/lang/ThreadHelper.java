package org.javautil.lang;

import org.javautil.java.ClassHelper;

/**
 * todo review ClassHelper and StackHelper functionality
 * 
 * @author jjs
 * 
 */
public class ThreadHelper {

	// getStackTrace
	//
	// 0 this call
	// 1 caller
	//

	public static String getMethodName() {
		return Thread.currentThread().getStackTrace()[1].getMethodName();
	}

	/**
	 * 
	 * @return
	 */
	public static String getCallerMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}

	/**
	 * The caller's classname stripped of the package
	 * 
	 * @return
	 */
	public static String getCallerSimpleClassName() {
		final StackTraceElement el = Thread.currentThread().getStackTrace()[2];
		final String retval = ClassHelper.getUnqualifiedClassName(el
				.getClassName());
		return retval;
	}

	public static String getCallerClassName() {
		final StackTraceElement el = Thread.currentThread().getStackTrace()[2];
		final String retval = el.getClassName();
		return retval;
	}

	public static String getCallerNoPackageClassName() {
		return Thread.currentThread().getStackTrace()[2].getClassName()
				.replaceAll(".*\\.", "");
	}

	public static String getCallerNoPackageClassNameMethod() {
		final StackTraceElement el = Thread.currentThread().getStackTrace()[2];
		final String noPackageClassName = el.getClassName().replaceAll(".*\\.",
				"");
		final String methodName = el.getMethodName();
		final String retval = noPackageClassName + "." + methodName;
		return retval;

	}

	public static String getAsString(final StackTraceElement[] stackTrace) {
		final StringBuilder b = new StringBuilder();
		for (final StackTraceElement ste : stackTrace) {
			b.append(ste.toString());
			b.append(SystemProperties.newline);
		}
		return b.toString();
	}

	public static String getStackTraceAsString() {
		final StackTraceElement[] stack = Thread.currentThread()
				.getStackTrace();
		final StackTraceElement[] callerStack = new StackTraceElement[stack.length - 1];
		System.arraycopy(stack, 1, callerStack, 0, callerStack.length);
		return getAsString(callerStack);
	}
}
