package org.javautil.persist.hibernate;

import static org.junit.Assert.*;

import org.javautil.hibernate.persist.DaoImpl;
import org.junit.Test;

public class DaoImplTest {
	@Test
	public void test() {
		DaoImpl dao = new DaoImpl(this.getClass());
		String className = dao.getSimpleClassName();
		assertEquals("DaoImplTest", className);
	}

}
