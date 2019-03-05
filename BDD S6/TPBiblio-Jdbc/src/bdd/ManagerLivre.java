package bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * objet qui fait la liaison avec la base de données pour accéder aux données de type Livre
 * nécessite une connexion pour s'initialiser
 */

@SuppressWarnings("unused")
public class ManagerLivre {
	private Connection connexion;
	
	private PreparedStatement  rechercherLesLivres;
	
	/**
	 * associer la connexion permet d'initialiser les preparedStaement
	 * 
	 */
	public void setConnection (Connection c) throws AppliException {
		// A FAIRE
				
	}
	
	/** 
	 * retourne la liste de tous les livres tries par titre
	 * a chaque livre sont associes son emprunteur et la personne qui a reserve
	 */
	
	public List <Livre> getLesLivres() throws AppliException {
		//A  Faire
		return new ArrayList<Livre>();
		
	}
}
