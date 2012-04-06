<?xml version="1.0" encoding="iso-8859-1"?>
<!-- 
	
	AmCharts - Line & Area Graph - XSL Stylesheet
	
	This stylesheet creates a flash line graph from an XML resultset.
	
	AmCharts Line Graph - http://www.amcharts.com/line/

	This line and area chart is a new approach to usability of this 
	kind of charts. Your site visitor will be able to zoom the period
	of his interest, turn on/off value balloons, show and hide graphs.
	You can have simple, stacked and 100% stacked lines and areas.
	You will be able to customize every small detail of your chart and
	make it look exactly as you want.
	
	author: Bryan C Mason, bryan.mason@custdata.com
 -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- the path to the amline flash file on the web -->
<xsl:param name="chartFlashFile" select="'/flash/amline-1.5.2.swf'" />

<!-- an optional amline settings xml file to drive the chart settings -->
<xsl:param name="chartSettingsFile" select="''" />

<!-- the title that appears atop the graph -->
<xsl:param name="chartTitle" />

<!-- can be line, area, or stackedArea -->
<xsl:param name="graphType" select="'line'" />

	<xsl:output method="html"/>

	<!-- this is the big daddy stylesheet, where the work is actually done -->
	<xsl:include href="include/amcharts-base.xsl"/>

	<!-- this stylesheet handles processing of the query data -->
	<xsl:include href="include/amcharts-series.xsl"/>
 
	<!-- this template handles processing of the query data -->
	<xsl:template match="/">

		<!-- determine if we have preformatted chart data available,
		     if so, store it as such, otherwise, an empty string -->
		<xsl:variable name="chartData">
			<xsl:choose>		
				<!-- when the root node of the XML data is "chart", then we are to 
					 assume that the XML data is ready to go into the chart-data section -->
				<xsl:when test="name(./*[1]) = 'chart'">													
					<xsl:for-each select="./*[1]">
						<xsl:call-template name="chart-node" />
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="''" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
				
		<xsl:call-template name="emit-chart">
			<xsl:with-param name="chartFlashFile" select="$chartFlashFile" />
			<xsl:with-param name="chartSettingsFile" select="$chartSettingsFile" />
			<xsl:with-param name="chartSettingsText" select="''" />
			<xsl:with-param name="chartDataText" select='$chartData' />
		</xsl:call-template>
		
	</xsl:template>

	<!-- needed for use by the amcharts-base.xml when no settings file -->
	<xsl:template name="build-chart-settings">

		<xsl:variable name="documentRootNodeName" select="name(./*[1])" />
						
		<!-- the number of points on the graph per line -->
		<xsl:variable name="numberOfPoints">
			<xsl:value-of select="count(./*[1]/*)" />
		</xsl:variable>
		
		<!-- dynamically predetermine the number of x-axis gridlines that
		     we will be showing, we set a ceiling here also -->
		<xsl:variable name="maxShownXGridLines">25</xsl:variable>
		<xsl:variable name="numberOfXGridLines">
			<xsl:choose>
				<xsl:when test="$numberOfPoints > $maxShownXGridLines">
					<xsl:value-of select="$maxShownXGridLines" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$numberOfPoints" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
	
		<!-- true when not every point has a corresponding X gridline -->
		<xsl:variable name="missingXGridLines">
			<xsl:value-of select="$numberOfPoints > $maxShownXGridLines" /> 
		</xsl:variable>
	
		<!-- graph type can setup our graphs as different kinds of line/area graphs -->
		<xsl:variable name="fillAreaAlpha">
			<xsl:choose>						
				<xsl:when test="$graphType = 'area'">30</xsl:when>
				<xsl:when test="$graphType = 'stackedArea'">60</xsl:when>
				<xsl:when test="$graphType = 'areaStacked'">60</xsl:when>
				<xsl:otherwise>0</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="lineAlpha">
			<xsl:choose>						
				<xsl:when test="$graphType = 'line'">85</xsl:when>
				<xsl:otherwise>0</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="lineWidth">
			<xsl:choose>
				<xsl:when test="$graphType = 'line'">2</xsl:when>
				<xsl:otherwise>1</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<!-- we need to apply the amCharts specific graph type from our graphType -->
		<xsl:variable name="amChartsGraphType">
			<xsl:choose>
				<xsl:when test="$graphType = 'stackedArea'">stacked</xsl:when>
				<xsl:when test="$graphType = 'areaStacked'">stacked</xsl:when>
				<xsl:otherwise>line</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<!-- build the dynamic settings from the collected data -->
		<xsl:call-template name="settings-html">
			<xsl:with-param name="amChartsGraphType" select="$amChartsGraphType" />
			<xsl:with-param name="numberOfXGridLines" select="$numberOfXGridLines" />
			<xsl:with-param name="chartFlashFile" select="$chartFlashFile" />
			<xsl:with-param name="missingXGridLines" select="$missingXGridLines" />
			<xsl:with-param name="chartTitle" select="$chartTitle" />
			<xsl:with-param name="documentRootNodeName" select="$documentRootNodeName" />
			<xsl:with-param name="fillAreaAlpha" select="$fillAreaAlpha" />
			<xsl:with-param name="lineWidth" select="$lineWidth" />
			<xsl:with-param name="lineAlpha" select="$lineAlpha" />
		</xsl:call-template>

	</xsl:template>

</xsl:stylesheet>