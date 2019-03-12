import java.sql.*;
import java.io.*;

public class Election {
	private Connection db;
	private int barre = 5; // barre des 5%
	private Statement state,select;
	private PreparedStatement state2, updateSiege;
	
	/* cr´eation objet Election, connexion `a la base */

	public Election(String user, String password) throws SQLException, ClassNotFoundException {

	      String url = "jdbc:oracle:thin:@oracle.fil.univ-lille1.fr:1521:filora";
	    

	      db = DriverManager.getConnection(url, user, password);
		state = db.createStatement();
		select = db.createStatement();
		state2 = db.prepareStatement("Select sum(nbVoix) from Election where nbVoix > ?");
		updateSiege = db.prepareStatement("Update Election set nbSieges = ? where liste = ? ");
	}

	/* fin de connexion */
	public void fin() {
		try {
			db.close();
		} catch (SQLException e) {
			System.err.println("pb de connexion : " + e.getMessage());
		}
	}

	/*
	 * calcul de la r´epartition des si`eges, met `a jour la colonne "nbSieges"
	 * de la table "Election"
	 */
	public void calculSieges(int nbSiegesAPourvoir) throws SQLException {
		int nbSuffrages = this.getNbSuffrages();
		int seuil = (nbSuffrages * this.barre) / 100;
		int nbSuffragesUtiles = this.getNbSuffragesUtiles(seuil);
		int quotientElectoral = nbSuffragesUtiles / nbSiegesAPourvoir;
		this.repartitionSieges(seuil, quotientElectoral);
		this.repartitionResteSieges(seuil, nbSiegesAPourvoir);
	}

	/* calcul du nombre de suffrages exprim´es */
	private int getNbSuffrages() throws SQLException{
		ResultSet rsNbSuff = state.executeQuery("Select sum(nbVoix) from Election");
		rsNbSuff.next();
		return rsNbSuff.getInt(1);
	}
	
	/* calcul du nombre de suffrages utiles */
	private int getNbSuffragesUtiles(int seuil) throws SQLException{ 	
		state2.setInt(1, seuil);
		ResultSet rsUtiles = state2.executeQuery();
		rsUtiles.next();		
		return rsUtiles.getInt(1);
	}
	
		/* premi`ere r´epartition des si`eges en fonction du quotient ´electoral */
	private void repartitionSieges(int seuil, int quotientElectoral) throws SQLException{
		ResultSet rsSelect = select.executeQuery("Select * from Election");
		while(rsSelect.next()){
			if(rsSelect.getInt(2) > seuil){
				int nbSiege = rsSelect.getInt(2) / quotientElectoral;
				updateSiege.setInt(1, nbSiege);
				updateSiege.setString(2, rsSelect.getString(1));
				updateSiege.executeQuery();
			}
		}
	}
	
		/* r´epartition finale des si`eges encore `a pourvoir */
	private void repartitionResteSieges(int seuil, int nbSiegesAPourvoir) throws SQLException{
		
	}
	
	public static void main(String args[]) throws SQLException, ClassNotFoundException {
	Election election = null ;
	try {
		election=new Election("quentin",Mdp.mdp) ; // login et mot de passe
		election.calculSieges(6) ; // 6 si`eges sont `a pourvoir
	}catch(SQLException e){// on traite l’exception
		e.printStackTrace();
	}finally{ 
		if (election != null) election.fin() ;
	} // quoiqu’il arrive on se d´econnecte
	}
}