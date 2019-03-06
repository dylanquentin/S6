package bdd;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * objet qui propose tous les services necessaires  pour la gestion de la bibliotheque:
 * operations (emprunter, reserver, restituer)
 */

public class ManagerBiblio {
	
	private java.sql.Connection laConnexion ;

	private Statement stmt ; // zone de requete
	private CallableStatement csEmprunter, csRestituer ; // pour les procedures stockees
	

	/**
	 * Constructeur vide 
	 */
	public ManagerBiblio() {
		super();
	}


	/**
	 * la connection passee en parametre permet d'initialiser les objects necessaires 
	 * a la réalisation des opérations : callableStatement,...
	 * @throws AppliException si l'initialisation pose probleme
	 */
	public void setConnection( Connection connection) throws AppliException{
		laConnexion = connection;
		
		try {
			//stmt = laConnexion.prepareStatement("");
			csEmprunter = laConnexion.prepareCall("{call PAQ_BIBLIO.Emprunter(? , ?) }");
			csRestituer = laConnexion.prepareCall("{call PAQ_BIBLIO.Restituer(?) }");
		} catch (SQLException e) {
			e.printStackTrace();
		}	};


	/**
	 * @param livre un livre dans la base
	 * @param personne une personne dans la base
	 * @throws AppliException si l'emprunt n'est pas possible :  le livre est déjà  emprunté ou réservé par une autre personne, 
	 * ou la personne a déjà  emprunté 3 livres
	 * Réalise l'emprunt du livre par la personne 
	 */
	public void emprunter(Livre livre, Personne personne) throws AppliException{
		try {
			csEmprunter.setInt(1, livre.getId());
			csEmprunter.setInt(2, personne.getId());
			csEmprunter.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	/**
	 * @param livre un livre dans la base
	 * @throws AppliException si la restitution n'est pas possible : il n'est pas emprunté
	 * Réalise la restitution du livre (en sortie il n'est plus emprunté)
	 */
	public void restituer(Livre livre) throws AppliException{
		//A Completer
	}

	
	
	/**
	 * @param livre, un livre dans la base
	 * @param personne, une personne dans la base
	 * @throws AppliException si un des paramètres n'est pas défini
	 * @throws AppliException si la réservation n'est pas possible : le livre est déjà réservé par une autre personne, 
	 * ou il n'est pas emprunté, ou il est déjà  emprunté par cette personne, 
	 * ou la personne a déjà  3 réservations
	 */
	
	 
	public void reserver (Livre livre, Personne personne) throws AppliException {
		//A completer
	}


}
