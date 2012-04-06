package com.dbexperts.oracle.docgenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.dbexperts.logging.LoggingConfigurator;

// todo write package name first
// todo allow tab expansion
public class TexDocGenerator {
	private Logger logger = Logger.getLogger(getClass());
	private static final String paramTag = DocGeneratorMessages.getString("TexDocGenerator.paramTag");
	private static final String codeBeginTag = DocGeneratorMessages.getString("TexDocGenerator.codeBeginTag");
	private static final String codeEndTag = DocGeneratorMessages.getString("TexDocGenerator.codeEndTag");
	private static final String functionTag = DocGeneratorMessages.getString("TexDocGenerator.functionTag");
	private static final String procedureTag = DocGeneratorMessages.getString("TexDocGenerator.procedureTag");
	private static final String itemTag = DocGeneratorMessages.getString("TexDocGenerator.itemTag");
	private static final String listOpenTag = DocGeneratorMessages.getString("TexDocGenerator.listOpenTag");
	private static final String listCloseTag = DocGeneratorMessages.getString("TexDocGenerator.listCloseTag");
	private static final String emitItemizeClose = DocGeneratorMessages.getString("TexDocGenerator.emitItemizeClose");
	private static final String emitItemizeOpen = DocGeneratorMessages.getString("TexDocGenerator.emitItemizeOpen");
	private static final String emitItemClose = DocGeneratorMessages.getString("TexDocGenerator.emitListItemClose");
	private static final String emitItemOpen = DocGeneratorMessages.getString("TexDocGenerator.emitListItemOpen");
	private static final String emitParmOpen = DocGeneratorMessages.getString("TexDocGenerator.emitParmOpen");
	private static final String emitParmClose = DocGeneratorMessages.getString("TexDocGenerator.emitParmClose");
	private static final String emitCodeOpen = DocGeneratorMessages.getString("TexDocGenerator.emitCodeOpen");
	private static final String emitCodeClose = DocGeneratorMessages.getString("TexDocGenerator.emitCodeClose");
	private static final String emitFunctionOpen = DocGeneratorMessages.getString("TexDocGenerator.emitFunctionOpen");
	private static final String emitFunctionClose = DocGeneratorMessages.getString("TexDocGenerator.emitFunctionClose");

	private String line;
	private DocumentCommentParser dcp;
	private static final String newline = System.getProperty("line.separator"); //$NON-NLS-1$
	private FileReader fr;
	private Writer writer = new PrintWriter(System.out);
	private File outfile;

	public TexDocGenerator(File inputFile) throws FileNotFoundException {
		if (inputFile == null) {
			throw new IllegalArgumentException("inputFile is null"); //$NON-NLS-1$
		}
		if (!inputFile.canRead()) {
			throw new IllegalArgumentException("can't read '" + inputFile.getAbsolutePath() + "'"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		fr = new FileReader(inputFile);
		dcp = new DocumentCommentParser(fr);

	}

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.err.println("usage: TexDocGenerator inputFile "); //$NON-NLS-1$
			System.exit(1);
		}

		TexDocGenerator tdg = new TexDocGenerator(new File(args[0]));
		if (args.length == 2) {
			tdg.setOutputFile(new File(args[1]));
		}
		tdg.configureLogging();
		tdg.process();
	}

	private void setOutputFile(File f) throws IOException {
		writer = new FileWriter(f);
		outfile = f;

	}

	private void setWriter(Writer w) {
		if (w == null) {
			throw new IllegalArgumentException("w is null");
		}
		writer = w;

	}

	private void configureLogging() {
		LoggingConfigurator.configureMinimalConsole(LoggingConfigurator.CLASS_LINE_MESSAGE, Level.DEBUG);
		Logger.getLogger(DocumentCommentParser.class).setLevel(Level.DEBUG);
	}

	private void process() throws IOException {
		DocumentComment dc;
		while ((dc = dcp.getDocumentComment()) != null) {
			processComment(dc);
		}
		fr.close();
		writer.close();
	}

	private void processComment(DocumentComment comment) throws IOException {
		while (true) {
			String directive = comment.getDirective();
			if (paramTag.equals(directive)) { //$NON-NLS-1$
				processParam(comment);
				break;
			}
			if (codeBeginTag.equals(directive)) { //$NON-NLS-1$
				processCode(comment);
				break;
			}
			if (codeEndTag.equals(directive)) { //$NON-NLS-1$
				writeln(emitCodeClose); //$NON-NLS-1$
				break;
			}
			if (functionTag.equals(directive) || procedureTag.equals(directive)) { //$NON-NLS-1$ //$NON-NLS-2$
				writeln(emitFunctionOpen + comment.getPayload()); //$NON-NLS-1$
				break;
			}
			if (listOpenTag.equals(directive)) { //$NON-NLS-1$
				writeln(emitItemizeOpen); //$NON-NLS-1$
				break;
			}
			if (itemTag.equals(directive)) { //$NON-NLS-1$
				writeln(emitItemOpen + comment.getPayload()); //$NON-NLS-1$
				break;
			}
			if (listCloseTag.equals(directive)) { //$NON-NLS-1$
				writeln(emitItemizeClose); //$NON-NLS-1$
				break;
			}
			if (directive == null) {
				writeln(comment.getPayload());
				break;
			}
			logger.warn("unknown directive '" + directive + "'"); //$NON-NLS-1$ //$NON-NLS-2$
			break;
		}
	}

	private void processCode(DocumentComment dc) throws IOException {
		writeln(emitCodeOpen); //$NON-NLS-1$
		DocumentComment comment;
		while ((comment = dcp.getDocumentComment()) != null) {
			String directive = comment.getDirective();
			if (directive != null) {
				writeln(emitCodeClose);
				if (!codeEndTag.equals(directive)) {
					logger.error("found non end-code directive '" + directive + "'" + comment.getLineNumber() + " " + line);
				}
				break;
			}
			writeln(comment.getPayloadWithWhitespace());
		}
	}

	private void writeln(String text) throws IOException {
		write(text);
		write(newline);
	}

	private void write(String text) throws IOException {
		logger.info(">> " + text); //$NON-NLS-1$

		writer.write(text);
	}

	private void processParam(DocumentComment comment) throws IOException {
		String payload = comment.getPayload();
		String[] words = payload.split(" "); //$NON-NLS-1$
		if (words.length < 1) {
			logger.error("no argument to param"); //$NON-NLS-1$
		} else {
			write(emitParmOpen + words[0] + emitParmClose + newline);
		}
	}
}
