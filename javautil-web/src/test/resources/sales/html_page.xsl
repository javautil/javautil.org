<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="multiple" select="'false'" />
	<xsl:param name="name" select="'value'" />
	<xsl:param name="id" select="''" />
	
	<xsl:output method="html" />

	<xsl:template match="/*">
	
		<html>
			<head>
				<title>This is an HTML Page</title>
			</head>
			<body>
			
				<!-- Copy the current node -->
			    <xsl:copy>
			      <!-- Including any attributes it has and any child nodes -->
			      <xsl:apply-templates select="@*|node()"/>
			    </xsl:copy>
				
			</body>
		</html>
	
	</xsl:template>

	<!-- Whenever you match any node or any attribute -->
	  <xsl:template match="node()|@*">
	    <!-- Copy the current node -->
	    <xsl:copy>
	      <!-- Including any attributes it has and any child nodes -->
	      <xsl:apply-templates select="@*|node()"/>
	    </xsl:copy>
	  </xsl:template>


</xsl:stylesheet>