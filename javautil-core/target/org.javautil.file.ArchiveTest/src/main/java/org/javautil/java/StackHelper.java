package org.javautil.java;

/**
 * @deprecated
 * 
 */
@Deprecated
public class StackHelper {

	/**
	 * getUnqualifiedClassName
	 * 
	 * todo jjs this sucks what idiot wrote this? Returns the name of the class
	 * that called clazz.
	 * 
	 * @param dataSource
	 * @return
	 */
	public static String getCallerUnqualifiedClassName(final Object o) {
		final StackTraceElement se = getCaller(o);
		String unqualifiedName = null;
		if (se != null) {
			unqualifiedName = ClassHelper.getUnqualifiedClassName(se
					.getClassName());
		}
		return unqualifiedName;
	}

	public static StackTraceElement getCaller(final Object o) {
		final String className = o.getClass().getName();
		final StackTraceElement[] stack = Thread.currentThread()
				.getStackTrace();
		StackTraceElement caller = null;
		// could be zero if using jet
		if (stack.length > 0) {
			int depth = 0;
			while (depth < stack.length) {
				if (stack[depth].getClassName().equals(className)) {
					depth++;
					break;
				}
				depth++;
			}
			caller = stack[depth];
		}
		return caller;
	}
}