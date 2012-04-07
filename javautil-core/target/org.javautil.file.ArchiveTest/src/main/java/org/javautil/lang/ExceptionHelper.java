package org.javautil.lang;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

public class ExceptionHelper {

	public static String asString(Exception exception) {
		String string = 
		exception.getMessage() + "\n" + getStackTraceString(exception);
		return string;
	}
	
	public static String getStackTraceString(Exception e) {
		ByteArrayOutputStream baos =new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(baos);
		e.printStackTrace(pw);
		pw.flush();
		String message= baos.toString();
		return message;
	}
	
}
