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

public class ManagerLivre {
	private Connection connexion;
	
	private PreparedStatement  rechercherLesLivres;
	
	/**
	 * associer la connexion permet d'initialiser les preparedStaement
	 * 
	 */
	public void setConnection (Connection c) throws AppliException {
		connexion = c;
		try {
			rechercherLesLivres = connexion.prepareStatement("SELECT * FROM TP_Livre l1 LEFT JOIN TP_Personne p1 ON l1.id_emprunte = p1.id LEFT JOIN TP_PERSONNE p2 ON l1.id_reserve = p2.id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/** 
	 * retourne la liste de tous les livres tries par titre
	 * a chaque livre sont associes son emprunteur et la personne qui a reserve
	 */
	
	public List <Livre> getLesLivres() throws AppliException {
		ArrayList<Livre> res = new ArrayList<>();
	
		try {
			ResultSet rs = rechercherLesLivres.executeQuery();
			
			while(rs.next()){
				int id = rs.getInt(1);
				String nom = rs.getString(2);
				Livre l = new Livre(id,nom);
				Personne p = new Personne(rs.getInt(6),rs.getString(7), rs.getString(8));
				l.setEmprunte(p);
				res.add(l);
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return res;
		
	}
}
