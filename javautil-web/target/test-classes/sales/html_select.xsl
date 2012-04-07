<?xml version="1.0" encoding="iso-8859-1"?>
<!-- 

	Generic XSL Stylesheet form transforming a name/value pair query into a
	HTML select list.

	Sample:

	Input XML Data:	
	<xml>
		<row>
			<label>foo</label>
			<value>1</value>
		</row>
		<row>
			<label>bar</label>
			<value>2</value>
		</row>
	</xml>

	Transform Result:
	<select name="value">
		<option value="1">foo</option>
		<option value="2">bar</option>
	</select>

 -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="multiple" select="'false'" />
	<xsl:param name="name" select="'value'" />
	<xsl:param name="id" select="''" />
	
	<xsl:output method="html" />

	<xsl:template match="/*">
	
		<xsl:element name="select">
 			<xsl:attribute name="name">
 				<xsl:value-of select="$name" />
 			</xsl:attribute>
 			<xsl:if test="$id != ''"> 
 				<xsl:attribute name="id">
	 				<xsl:value-of select="$id" />
 				</xsl:attribute>
 			</xsl:if>
 			<xsl:choose>
	 			<xsl:when test="$multiple != 'false'">				
					<xsl:attribute name="multiple">
						<xsl:value-of select="'multiple'" />
					</xsl:attribute>
					<xsl:attribute name="size">
						<xsl:value-of select="'10'" />
					</xsl:attribute>				
				</xsl:when>
				<xsl:otherwise>
					<option value="">--- No Selection ---</option>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:for-each select="*">
				<xsl:variable name="optionLabel" select="label" />
				<xsl:variable name="optionValue" select="value" />
				<xsl:element name="option">
					<xsl:attribute name="value"><xsl:value-of select="$optionValue" /></xsl:attribute>
					<xsl:value-of select="$optionLabel" />
				</xsl:element>
			</xsl:for-each>
		</xsl:element>
	
	</xsl:template>

</xsl:stylesheet>