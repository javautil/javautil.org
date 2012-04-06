package org.javautil.lang.reflect;

/**
 * Determines the "unwrapped class" or the real java class that was proxied when
 * cglib/javassist proxies are involved. If no proxy is detected, no unwrapping
 * will be performed.
 * 
 * todo what is this for? todo can we have some examples and tests?
 * 
 * @author bcm
 */
public class ProxyHelper {

	private Class<? extends Object> clazz = null;

	private final String CGLIB_PREFIX = "$$EnhancerByCGLIB$$";

	private final String JAVASSIST_PREFIX = "_$$_javassist_";

	public ProxyHelper(final Class<? extends Object> clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return unwrapped class as a string
	 */
	public String getUnwrappedClassname() {
		String classname = clazz.getName();
		int ndx = classname.indexOf(CGLIB_PREFIX);
		if (ndx != -1) {
			classname = clazz.getName().substring(0, ndx);
		}
		ndx = classname.indexOf(JAVASSIST_PREFIX);
		if (ndx != -1) {
			classname = clazz.getName().substring(0, ndx);
		}
		return classname;
	}

	/**
	 * @return unwrapped class
	 */
	public Class<? extends Object> getUnwrappedClass() {
		try {
			return Class.forName(getUnwrappedClassname());
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}
