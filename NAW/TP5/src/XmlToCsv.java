import java.io.BufferedWriter;
import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlToCsv extends DefaultHandler{
	
	String node;
	BufferedWriter bw ;
	File file = new File("isfv2.csv");
	String res="Region;Departement;Code Postale;Ville;Nb de redevables;Patrimoine moyen;Impot moyen\n";
	String region = "";
	String dep = "";
	
	public void startElement(String uri, String localName, String qName, Attributes a){
	   System.out.println("---------------------------------------------");
	   System.out.println("qname = " + qName);
	   node = qName; 
	   
	   if(qName.equals("region")){
		   region = a.getValue("nom");
	   }
	   if(qName.equals("departement")){
		   dep = a.getValue("nom");
	   } 
	   if(qName.equals("commune")){
		   res += region+";" + dep +";"+ a.getValue("nom") + ";";
	   }

	   
	   
	   
	}
	public  void endElement(String uri, String localName, String qName) {
		
		node= "";
	}
	public void characters(char[] caracteres, int debut, int longueur) throws SAXException {
		
	/*	System.out.println("******************************************");
		String str = new String(caracteres,debut,longueur);
		System.out.println("node's data : " + node + " : " + str);
		*/
		//System.out.println("node : " + node + "   "  );
		if(node.equals("nombre-redevable") || node.equals("patrimoine-moyen") 
				|| node.equals("impot-moyen") ){
			
			res+= new String (caracteres,debut,longueur)+";" ;
			//System.out.println(node + " : " + new String(caracteres,debut,longueur) );
		}
		if(node.equals("code-postale")){
			res += new String (caracteres,debut,longueur) + "\n";
		}
	}	  
	public void endDocument() {  
		System.out.println("end");
	} 
	public void startDocument() { 
		
		System.out.println("start");
	}
	
	public static void main(String[] args) {
        
		try{
	      SAXParserFactory factory = SAXParserFactory.newInstance();
	      SAXParser saxParser = factory.newSAXParser();
	      XmlToCsv x = new XmlToCsv();
	      saxParser.parse(new File("monFichier.xml"), x);
	      System.out.println(x.res);
	    } catch (SAXException e) {	
	      // prise en charge des erreurs SAX 
	    } catch (Throwable t) {
	      // autres erreurs 
	    } 
	}
	
	
	/**
	 * 	switch (node) {
		case "nombre-redevable":
			
			break;
		case "patrimoine-moyen":
		
			break;
		case "impot-moyen":
			
			break;
		case "code-postale":
			ordre[0]= node;
			break;
		default:
			break;
		}*/
	
}