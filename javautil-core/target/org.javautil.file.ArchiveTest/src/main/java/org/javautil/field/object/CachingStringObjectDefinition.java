package org.javautil.field.object;

import java.util.WeakHashMap;

import org.apache.log4j.Logger;

public class CachingStringObjectDefinition extends AbstractObjectDefinition implements ObjectDefinition {

	private final Logger logger = Logger.getLogger(getClass());
	
	private WeakHashMap<String,String> map =new WeakHashMap<String,String>();

	public CachingStringObjectDefinition() {
	}

	@Override
	public String getObject(String externalRepresentation) throws ObjectParseException {
				String cached = map.get(externalRepresentation);
				if (cached == null) {
					cached = externalRepresentation;
					map.put(externalRepresentation,externalRepresentation);
				}
				return cached;
	}


}
