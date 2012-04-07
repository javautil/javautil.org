package org.javautil.lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.junit.Test;

public class ExceptionListExceptionTest {
	private Logger logger = Logger.getLogger(getClass());
	@Test
	public void test() throws ExceptionListException {
		ExceptionListException exceptions = new ExceptionListException();
		exceptions.throwIfWarranted();
	}

	@Test(expected=ExceptionListException.class)
	public void test2() throws ExceptionListException {
		ExceptionListException exceptions = new ExceptionListException();
		File f = new File("~~~~~~~");
		
			try {
				FileInputStream fos =new FileInputStream(f);
			} catch (FileNotFoundException e) {
				exceptions.add(e);
			}
		
		exceptions.throwIfWarranted();
	}
	
	@Test
	public void test3() throws ExceptionListException {
		ExceptionListException exceptions = new ExceptionListException();
		File f = new File("~~~~~~~");
		
			try {
				FileInputStream fos =new FileInputStream(f);
			} catch (FileNotFoundException e) {
				exceptions.add(e);
			}
		f = new File("!##");
		try {
			FileInputStream fos = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			exceptions.add(e);
		}
		String s = exceptions.toString();
		logger.debug(s);
		// TODO doesn't test anything
	}
}
