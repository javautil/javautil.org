package org.javautil.field;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.javautil.field.object.ObjectDefinition;
import org.javautil.field.object.ObjectParseException;



public class ObjectDefinitions extends LinkedHashMap<String, ObjectDefinition> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3681944057159091894L;
	private Logger logger = Logger.getLogger(getClass());
	public ArrayList<Object> getObjects(List<String> externalRepresentations) throws ObjectParseException {
		ArrayList<Object> returnValue = new ArrayList<Object>();
		if (externalRepresentations.size() != size()) {
			throw new IllegalArgumentException("arguments size " + externalRepresentations.size() + " definitions size " + size());
		}
		String[] representations = (String[]) externalRepresentations.toArray(new String[externalRepresentations.size()]);
		int i = 0;
		for (ObjectDefinition def : values()) {
			if (logger.isDebugEnabled()) {
				logger.debug("definition " + def.toString() + " external: " + representations[i]);
			}
			try {
				Object o = def.getObject(representations[i]);
				returnValue.add(o);
				i++;
			} catch (ObjectParseException e) {
				throw new ObjectParseException("while processing externalRepresentation " + i + " " + representations[i] + " with " + def.toString(),e);
			}
		}
		return returnValue;
	}
	
	public Object getObject(String definitionName, String externalRepresentation) throws ObjectParseException {
		ObjectDefinition od = get(definitionName);
		if (od == null) {
			StringBuilder sb = new StringBuilder();
			for (String string : keySet()) {
				sb.append("'");
				sb.append(string);
				sb.append("' ");
				
			}
			throw new IllegalArgumentException("no definition by name '" + definitionName + "' in " + sb);
			
		}
		return od.getObject(externalRepresentation);
	}
}
