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

public class ManagerPersonne {
	
	private Connection connexion;
	
	private PreparedStatement  rechercherLesPersonnes;
	
	/**
	 * associer la connexion permet d'initialiser les preparedStaement
	 * necesaires aux requetes
	 */
	public void setConnection (Connection c) throws AppliException {
		connexion = c;
		
		try {
			rechercherLesPersonnes = connexion.prepareStatement("Select * from TP_Personne");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *  retourne la liste des personnes ordonnee par nom
	 * declenche AppliException en cas de pb
	 */
	
	public List <Personne> getLesPersonnes () throws AppliException {
	
		ArrayList<Personne> res = new ArrayList<>();
		try {
			ResultSet rs = rechercherLesPersonnes.executeQuery();
			
			while(rs.next()){
				int id = rs.getInt(1);
				String nom = rs.getString(2);
				String prenom = rs.getString(3);
				res.add(new Personne(id,nom,prenom));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return res;		
	}
}
