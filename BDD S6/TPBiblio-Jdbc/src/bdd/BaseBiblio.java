package bdd;

import java.sql.SQLException;

/**
 * objet qui permet la connexion/deconnection à la BDD  
 * initialise les services avec la connexion et permet l'accès à ces services
 */
public class BaseBiblio {
	private java.sql.Connection laConnexion ;

	private ManagerLivre managerLivre ; // les livres de la bibliothèque
	private ManagerPersonne managerPersonne ; // les usagers de la bibliothèque
	private ManagerBiblio managerBiblio;

	/**
	 * Constructeur : crée les managers
	 */
	public BaseBiblio() {
		 managerLivre = new ManagerLivre();
		 managerPersonne = new ManagerPersonne();
		 managerBiblio = new ManagerBiblio();
	}


	/**
	 * @param login nom de login oracle
	 * @param password mot de passe associé
	 * @throws AppliException si la connexion se passe mal
	 * si la connection courante est ouverte, on la ferme
	 * On crée une connection  à la base de données avec le login et le password fournis, 
	 * et on passe la connexion aux différents managers pourqu'ils s'initialisent
	 */
	public void seConnecter(String login, String password) throws AppliException{
		// a completer
	}

	/**
	 * @throws AppliException si la deconnexion se passe mal
	 * on se déconnecte à  la base de données après avoir validé la transaction.
	 */
	public void seDeconnecter() throws AppliException{
		try {
			if (this.laConnexion != null){
			  this.laConnexion.commit();
			  this.laConnexion.close(); // entraine la fermeture des statements
			// la methode close() sur une connexion déjà  fermée n'entraine pas d'erreur
			}
		} catch (SQLException e) {
			throw new AppliException ("ERREUR : Pb à  la déconnexion - Erreur Oracle " + e.getErrorCode());
		}  
	}


	public ManagerLivre getManagerLivre() {
		return managerLivre;
	}


	public ManagerPersonne getManagerPersonne() {
		return managerPersonne;
	}


	public ManagerBiblio getManagerBiblio() {
		return managerBiblio;
	}
	
	


}
