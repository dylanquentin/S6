<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">
	<xsl:output method="html" encoding="UTF-8"/>
	<xsl:template match="election2017">
		<html>
			<head>
				<title>Résultats PRT1 Ville par Ville</title>
			</head>
			<body>
				<xsl:for-each select="ville">
					<h4>
						<xsl:value-of select="@nom"/>
					</h4>
					<xsl:for-each select="bureau">
						<h5>
							<xsl:value-of select="@RefBureau"/>
						</h5>
						<table border="1pt">
							<tbody>
								<xsl:for-each select="scrutin[@idS = 'PR1T']/VoixCandidat">
									<tr>
										<th>
											<xsl:value-of select="@numero"/>
										</th>
										<th>
											<xsl:value-of select="./text()"/>
										</th>
									</tr>
								</xsl:for-each>
							</tbody>
						</table>
					</xsl:for-each>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="@*|text()"/>
</xsl:stylesheet>
