package org.javautil.field.object;

import org.apache.log4j.Logger;

public class IntegerObjectDefinition extends AbstractObjectDefinition {

	private final Logger logger = Logger.getLogger(getClass());

	public IntegerObjectDefinition() {
	}

	// TODO add ranges for validation
	@Override
	public Integer getObject(final String externalRepresentation) throws ObjectParseException {
		try {
			return new Integer(externalRepresentation);
		} catch (final Exception e) {
			throw new ObjectParseException(externalRepresentation, e);
		}
	}


}
