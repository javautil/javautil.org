package org.javautil.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.javautil.lang.ExceptionListException;

public class FileComparator implements Comparator<File> {
	private	FileInputStream os1;
	private	FileInputStream os2;

	private ExceptionListException exceptions = new ExceptionListException();
	@Override
	public int compare(File o1, File o2)  {
		try {
			os1 = new FileInputStream(o1);
			os2 = new FileInputStream(o2);
			InputStreamComparator isc = new InputStreamComparator();
			int retval = isc.compare(os1,os2);
			return retval;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			dispose();
		}

	}

	void dispose()   {
		if (os1 != null) {
			try {
				os1.close();
			} catch (IOException e) {
				exceptions.add(e);
			} 
		}
		if (os2 != null) {
				try {
					os2.close();
				}
				catch (IOException io) {
					exceptions.add(io);
				}
			}
		exceptions.throwRuntimeExceptionIfWarranted();
	}


}
