//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.09 at 01:38:12 PM EDT 
//

package org.javautil.xsd.columnmetadata;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for numericType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="numericType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="precision" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="scale" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="minValue" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="maxValue" type="{http://www.w3.org/2001/XMLSchema}double" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "numericType")
public class NumericType {

	@XmlAttribute
	protected BigInteger precision;
	@XmlAttribute
	protected BigInteger scale;
	@XmlAttribute
	protected Double minValue;
	@XmlAttribute
	protected Double maxValue;

	/**
	 * Gets the value of the precision property.
	 * 
	 * @return possible object is {@link BigInteger }
	 * 
	 */
	public BigInteger getPrecision() {
		return precision;
	}

	/**
	 * Sets the value of the precision property.
	 * 
	 * @param value
	 *            allowed object is {@link BigInteger }
	 * 
	 */
	public void setPrecision(final BigInteger value) {
		this.precision = value;
	}

	/**
	 * Gets the value of the scale property.
	 * 
	 * @return possible object is {@link BigInteger }
	 * 
	 */
	public BigInteger getScale() {
		return scale;
	}

	/**
	 * Sets the value of the scale property.
	 * 
	 * @param value
	 *            allowed object is {@link BigInteger }
	 * 
	 */
	public void setScale(final BigInteger value) {
		this.scale = value;
	}

	/**
	 * Gets the value of the minValue property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getMinValue() {
		return minValue;
	}

	/**
	 * Sets the value of the minValue property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setMinValue(final Double value) {
		this.minValue = value;
	}

	/**
	 * Gets the value of the maxValue property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getMaxValue() {
		return maxValue;
	}

	/**
	 * Sets the value of the maxValue property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setMaxValue(final Double value) {
		this.maxValue = value;
	}

}
