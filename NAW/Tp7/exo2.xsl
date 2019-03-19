<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:output method="html" encoding="UTF-8"/>
	<xsl:template match="isf">	
	</xsl:template>
	
	<xsl:template name="region">
		<li><input type='checkbox'/>  
		<select/>
		<ul>
			<xsl:apply-templates />
		</ul>
	</xsl:template>
	
	<xsl:template name="departement">
		<li><input type='checkbox'/>  
		<select/>
		<ul>
			<xsl:apply-templates />
		</ul>
	</xsl:template>

	<xsl:template name="commune">
		<li><input type='checkbox'/>  
		<select/>
		<ul>
			<select/>
		</ul>
	</xsl:template>
	
</xsl:stylesheet>
