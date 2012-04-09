package org.javautil.field.object;

public interface ObjectDefinition {

	public abstract Object getObject(String externalRepresentation) throws ObjectParseException;

	public boolean validate(String externalRepresentation);
}