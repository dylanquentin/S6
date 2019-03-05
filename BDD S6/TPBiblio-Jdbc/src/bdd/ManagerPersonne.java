package bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * objet qui fait la liaison avec la base de données pour accéder aux données 
 * de type Personne
 * nécessite une connexion pour s'initialiser
 */

@SuppressWarnings("unused")
public class ManagerPersonne {
	
	private Connection connexion;
	
	private PreparedStatement  rechercherLesPersonnes;
	
	/**
	 * associer la connexion permet d'initialiser les preparedStaement
	 * necesaires aux requetes
	 */
	public void setConnection (Connection c) throws AppliException {
		// A COMPLETER
				
	}
	
	/**
	 *  retourne la liste des personnes ordonnee par nom
	 * declenche AppliException en cas de pb
	 */
	
	public List <Personne> getLesPersonnes () throws AppliException {
		// A COMPLETER
		return new ArrayList<Personne>();
		
	}
}
