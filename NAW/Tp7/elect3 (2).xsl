<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">
	<xsl:output method="html" encoding="UTF-8" />
	<xsl:template match="election2017">
	<html>
		<head>
			<title>Résultats Présidentielle 1er Tour</title>
		</head>
		<body>
		<table border="1pt">
			<tbody>
			    <xsl:for-each select="scrutins/presidentielles[@idS='PR1T']/candidat">
				<tr>
					<th><xsl:value-of select="nom/text()"></xsl:value-of></th>
					<xsl:variable name="n" select="numero/text()"></xsl:variable>
					<th><xsl:value-of select="sum(//ville/bureau/scrutin[@idS='PR1T']/VoixCandidat[@numero=$n]/text())"></xsl:value-of></th>
				</tr>
				</xsl:for-each>
			</tbody>
		</table>
		</body>
	</html>
	</xsl:template>
</xsl:stylesheet>
