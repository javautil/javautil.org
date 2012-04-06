package org.javautil.reflect;

public class Class1 {
	private String stringFieldWithSetter;
	private String stringFieldWithoutSetter;

	Long longNumberField;
	long longField;
	Integer integerField;
	private Integer privateIntegerField;
	int intField;
	Short shortNumber;
	short shortField;
	Byte byteNumber;

	byte byteField;

	public Integer getPrivateIntegerField() {
		return privateIntegerField;
	}

	public void setPrivateIntegerField(Integer value) {
		this.privateIntegerField = value;
	}

	public String getStringFieldWithSetter() {
		return stringFieldWithSetter;
	}

	public void setStringFieldWithSetter(String stringFieldWithSetter) {
		this.stringFieldWithSetter = stringFieldWithSetter;
	}

	public String getStringFieldWithoutSetter() {
		return stringFieldWithoutSetter;
	}

	@Override
	public String toString() {
		return "Class1 [stringFieldWithSetter=" + stringFieldWithSetter
				+ ", longNumberField=" + longNumberField + ", longField="
				+ longField + ", integerField=" + integerField
				+ ", privateIntegerField=" + privateIntegerField
				+ ", intField=" + intField + ", shortNumber=" + shortNumber
				+ ", shortField=" + shortField + ", byteNumber=" + byteNumber
				+ ", byteField=" + byteField + "]";
	}

}