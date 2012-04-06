package org.javautil.model;

import java.util.Map;
/**
 * 
 * @author mnr
 *
 * Select list item used in spring freemarker rendering
 * Select list nameValues is made as <String, String> type because freemarker rendering expects this type  
 */
public class SelectItem {
	
	/**
	 * Select item label/description
	 */
	private String descr; 
	
	/**
	 * Value selected by the user from the screen
	 */
	private String selectedValue;
	
	/**
	 * Labels and values of the select list
	 */
	private Map<String, String> nameValues;
	
	/**
	 * Id of the select item  
	 */
	
	private Integer id;
	
	private String dataType;
	
	private String name;

	/**
	 * @return the descr
	 */
	public String getDescr() {
		return descr;
	}

	/**
	 * @param descr the descr to set
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}

	/**
	 * @return the selectedValue
	 */
	public String getSelectedValue() {
		return selectedValue;
	}

	/**
	 * @param selectedValue the selectedValue to set
	 */
	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	/**
	 * @return the nameValues
	 */
	public Map<String, String> getNameValues() {
		return nameValues;
	}

	/**
	 * @param nameValues the nameValues to set
	 */
	public void setNameValues(Map<String, String> nameValues) {
		this.nameValues = nameValues;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
