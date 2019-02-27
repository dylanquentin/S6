package xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Dom2 {

	
	public static void main(String[] arg){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			File isf = new File("isf.xml");
			Document isfXml = builder.parse(isf);
			Element root = isfXml.getDocumentElement();
			XPathFactory xpf = XPathFactory.newInstance();
			XPath path = xpf.newXPath();
			
			
			/*� le nombre de villes (de plus de 20000 habitants) de chaque region ayant
				plus de 50 assujettis `a l�ISF
			 */
			System.out.println("*****Nombre de ville par region*****");
			XPathExpression exp1 = path.compile("/isf/region");
			NodeList nodeList = (NodeList) exp1.evaluate(root, XPathConstants.NODESET);
			XPathExpression res;
			for(int i=0; i< nodeList.getLength();i++){
				String param = ((Element) nodeList.item(i)).getAttribute("nom");
				res = path.compile("count(/isf/region[@nom=\""+ param +
						"\"]/departement/commune)");
				System.out.println("R�gion : " + param + " Nombre de commune : " +
						 (int) (double) res.evaluate(root, XPathConstants.NUMBER));
			}

		
			/* � le nombre de villes (de plus de 20000 habitants) de chaque departement
				ayant plus de 50 assujettis `a l�ISF*/
			System.out.println("*****Nombre de ville par d�partement*****");
			XPathExpression exp2 = path.compile("/isf/region/departement");
			NodeList nodeList2 = (NodeList) exp2.evaluate(root, XPathConstants.NODESET);
			XPathExpression res2;
			for(int i=0; i< nodeList2.getLength();i++){
				String param = ((Element) nodeList2.item(i)).getAttribute("nom");
				res2 = path.compile("count(/isf/region/departement[@nom=\""+ param +
						"\"]/commune)");
				System.out.println("Departement : " + param + " Nombre de commune : " +
						 (int) (double) res2.evaluate(root, XPathConstants.NUMBER));
			}
			
			//� la ville (de plus de 20000 habitants) ayant la plus forte moyenne d�ISF

			XPathExpression exp3 = path.compile("//commune[not(impotMoyen < //commune/impotMoyen)]/impotMoyen");
			Node villeMax = (Node) exp3.evaluate(root,XPathConstants.NODE);
			System.out.println("*****Ville ayant la plus forte moyenne : " +  ((Element) villeMax.getParentNode()).getAttribute("nom")
					+" avec un impot moyen de  " +(String) exp3.evaluate(root, XPathConstants.STRING)+ "*****");
			
			//� le nombre total d�assujettis dans des villes (de plus de 20000 habitants)

			XPathExpression exp4 = path.compile("sum(//nbDeRedevables)");
			
			int a = (int) (double) exp4.evaluate(root, XPathConstants.NUMBER);
			System.out.println("*****Nombre total d'assujettis dans les villes : "+ a+ "  *****");
			
			
			
			/* la moyenne des montants
				d�ISF par region */
			
			System.out.println("*****Moyenne des montants d'ISF par r�gion*****");
			XPathExpression exp5 = path.compile("/isf/region");
			NodeList nodeList5 = (NodeList) exp5.evaluate(root, XPathConstants.NODESET);
			XPathExpression expNbDeRed;
			XPathExpression communes;
			for(int i=1; i<= nodeList5.getLength();i++){
			
				
				expNbDeRed = path.compile("sum(/isf/region["+i+"]/departement/commune/nbDeRedevables)");
				int nbAssujetiParRegion = (int) (double) expNbDeRed.evaluate(root, XPathConstants.NUMBER);
				
				int impotTotal = 0;

				
				XPathExpression departementsExp = path.compile("/isf/region["+i+"]/departement");
				NodeList departementsNL = (NodeList) departementsExp.evaluate(root, XPathConstants.NODESET);
				for(int k=1 ; k <= departementsNL.getLength();k++){
					communes = path.compile("/isf/region["+i+"]/departement["+k+"]/commune");
					NodeList nodeList6 = (NodeList) communes.evaluate(root, XPathConstants.NODESET);
					for(int j=1 ;j <= nodeList6.getLength();j++){

						XPathExpression impotMoyenEx = path.compile("/isf/region["+i+"]/departement["+k+"]/commune["+j+"]/impotMoyen");
						int impotMoyenParVille = (int) (double) impotMoyenEx.evaluate(root,XPathConstants.NUMBER);
						XPathExpression nbDeRedEx = path.compile("/isf/region["+i+"]/departement["+k+"]/commune["+j+"]/nbDeRedevables");
						int nbAsParVille = (int) (double) nbDeRedEx.evaluate(root,XPathConstants.NUMBER);
						impotTotal += nbAsParVille * impotMoyenParVille;
					}
				}
				
					

				
				
				System.out.println("Impot moyen dans la r�gion : " + ((Element) nodeList5.item(i-1)).getAttribute("nom") +
						" = " + (int) (impotTotal/nbAssujetiParRegion));
			
				
			
			}
			
			
		} catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
			System.out.println(e.getCause());
		}
		
	}
}
