package org.javautil.jdbc.metadata.renderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.javautil.jdbc.metadata.Schema;
import org.javautil.jdbc.metadata.Table;

/**

 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class SchemaToXml  {

	private final Element root = DocumentHelper.createElement("schema");
	private final Schema schema;
    private boolean supressSchemaName = false;
	public SchemaToXml(final Schema schema,boolean supressSchemaName) {
		this.schema = schema;
		this.supressSchemaName = supressSchemaName;
	}
	
	public Document getAsDocument() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("schema");
        root.add(getAsElement());
        return document;
	}

	public Element getAsElement()  {
		process();
		return root;
	}
	
	public String getAsString(Schema schema) {
		return getAsString(schema,null,true);
	}
	
	public String getAsString(Schema schema,String name,boolean suppressSchemaName)  {
		SchemaToXml ste = new SchemaToXml(schema, suppressSchemaName);
		Document doc  = ste.getAsDocument();
		OutputStreamWriter w = new OutputStreamWriter(System.out);
        OutputFormat format = OutputFormat.createPrettyPrint();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
        XMLWriter writer = new XMLWriter( os, format );
        writer.write( doc );
        os.flush();
        String retval = os.toString();
        os.close();
        return retval;
        } catch (IOException ioe) {
        	throw new RuntimeException(ioe);
        }
        
	}

	private void process()  {

	     for (final Table table : schema.getTables().values()) {
	    	 final TableXML tte = new TableXML(table,supressSchemaName);
	           root.add(tte.getAsElement());
	        }
	}


 }
