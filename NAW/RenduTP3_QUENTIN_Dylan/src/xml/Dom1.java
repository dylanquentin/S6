package xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
 
public class Dom1{
	
	public ArrayList<String> giveRegion(List<String[]> param){

		ArrayList<String> region = new ArrayList<>();
		for(String[] s : param){
			if(!region.contains(s[0])){
				region.add(s[0]);
			}
		}
		return region;
	}
	
	public ArrayList<String> giveListDepartementParRegion (List<String[]> param, String region){
		ArrayList<String> departements = new ArrayList<>();
		for(String[] s : param){
			
			if(s[0].equals(region) && !departements.contains(s[1])){
				
				departements.add(s[1]);
			}
		}
		return departements;
	}
	
	public ArrayList<String> giveListCommuneParDepartement (List<String[]> param, String departement){
		ArrayList<String> communes = new ArrayList<>();
		for(String[] s : param){
            String communeEtCode = s[2]+ " " + s[3];

			if(s[1].equals(departement) && !communes.contains(communeEtCode)){
				
				communes.add(communeEtCode);
			}
		}
		return communes;
	}
	
	
	public ArrayList<String[]> giveListDataParCommune (List<String[]> param, String commune){
		ArrayList<String[]> res = new ArrayList<>();
		for(String[] s : param){
            String communeEtCode = s[2]+ " " + s[3];

			if(commune.equals(communeEtCode)){
				String dataToAdd[] = new String[3];
				dataToAdd[0]= s[4];
				dataToAdd[1]= s[5];
				dataToAdd[2]= s[6];
				res.add(dataToAdd);
			}
		}
		return res;
	}
	
   public static void main(String argv[]) {
	  char SEPARATOR = ';';
 
      try {
 
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
 
        // élément de racine
        Document doc = docBuilder.newDocument();
        Element racine = doc.createElement("isf");
        doc.appendChild(racine);
 
        
        Dom1 create = new Dom1();

        
        List<String> lines = create.readFile(new File("isfcom2017.csv"));
        List<String[]> data = new ArrayList<String[] >(lines.size());
        String sep = new Character(SEPARATOR).toString();
      
    	
        for (String line : lines) {
            String[] oneData = line.split(sep);
            data.add(oneData);
        }
        ArrayList<String> listRegion = create.giveRegion(data);
        for(String[] s : data){
        	for(String a : s){
        		a.replaceAll(" ", "").replaceAll("'", "");
        	}
        }
        for (String regions : listRegion) {
        	Element eRegion = doc.createElement("region");
        	eRegion.setAttribute("nom", regions);
        	ArrayList<String> departements = create.giveListDepartementParRegion(data,regions);
        	for(String departement : departements){
        		Element eDepartement = doc.createElement("departement");
        		eDepartement.setAttribute("nom", departement);
            	ArrayList<String> communes = create.giveListCommuneParDepartement(data,departement);
            	for(String commune : communes){
            		Element eCommune = doc.createElement("commune");
            		eCommune.setAttribute("nom", commune);
            		ArrayList<String[]> dataCommunes = create.giveListDataParCommune(data,commune);
            		for(String[] dataCommune : dataCommunes){
            			Element eNbR = doc.createElement("nbDeRedevables");
            			eNbR.setTextContent(dataCommune[0]);;
            			eCommune.appendChild(eNbR);
            			Element patM = doc.createElement("patrimoineMoyen");
            			patM.setTextContent(dataCommune[1]);;
            			eCommune.appendChild(patM);
            			Element impotM = doc.createElement("impotMoyen");
            			impotM.setTextContent(dataCommune[2]);;
            			eCommune.appendChild(impotM);
            		}
            		eDepartement.appendChild(eCommune);
            	}

        		eRegion.appendChild(eDepartement);
        	}
			racine.appendChild(eRegion);
		}

        
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult resultat = new StreamResult(new File("isf.xml"));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(source, resultat);
 
        System.out.println("Fichier sauvegardé avec succès!");
 
     } catch (ParserConfigurationException pce) {
         pce.printStackTrace();
     } catch (TransformerException tfe) {
         tfe.printStackTrace();
     }
  }

	 public static ArrayList<String> readFile(File file) {

	        ArrayList<String> result = new ArrayList<String>();

	        FileReader fr;
			try {
				fr = new FileReader(file);
			
		        BufferedReader br = new BufferedReader(fr);
	
		        for (String line = br.readLine(); line != null; line = br.readLine()) {
		            result.add(line);
		        }
	
		        br.close();
		        fr.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return result;
	    }


}