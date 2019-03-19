<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">
	<xsl:output method="html" encoding="UTF-8"/>
	<xsl:template match="election2017">
		<html>
			<head>
				<title>Résultats Présidentielle 1er Tour</title>
			</head>
			<body>
				<xsl:for-each select="ville">
					<table border="1">
						<tbody>
							<br></br>
							<xsl:variable name="v" select="@nom"/>
							<tr >
								<th colspan="2">
									<xsl:value-of select="$v"></xsl:value-of>
								</th>
							</tr>
							
							<xsl:for-each select="../scrutins/presidentielles[@idS='PR1T']/candidat">
								<tr>
									<th>
										<xsl:value-of select="nom/text()"/>
									</th>
									<xsl:variable name="n" select="numero/text()"/>
									<th>
										<xsl:value-of select="sum(//ville[@nom=$v]/bureau/scrutin[@idS='PR1T']/VoixCandidat[@numero=$n]/text())"/>
									</th>
								</tr>
							</xsl:for-each>
							
						</tbody>
					</table>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
