import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class TpSimple {
	
	private Connection co;
	private PreparedStatement ps1;
	private CallableStatement cS1;
	
	public TpSimple(String name,String mdp){
		 try {
		      Class.forName("oracle.jdbc.OracleDriver");

		      String url = "jdbc:oracle:thin:@oracle.fil.univ-lille1.fr:1521:filora";
		      String user = name;
		      String passwd = mdp;

		      co = DriverManager.getConnection(url, user, passwd);
		      System.out.println("Connexion effective !");         
		      ps1 = co.prepareStatement("Select * from CARON.TABLE_TEST where texte like ?");
		      cS1 = co.prepareCall("{? = call CARON.inserer_ligne(?)}");
		    } catch (Exception e) {
		      e.printStackTrace();
		    }      
	}
	
	
	public void seDeco(){
		try {
			co.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void lireTable(){
		try {
			Statement state = co.createStatement();
			ResultSet rs = state.executeQuery("Select * from CARON.TABLE_TEST");
			ResultSetMetaData rsM = rs.getMetaData();
			for(int i = 1; i<= rsM.getColumnCount();i++){
				System.out.print(rsM.getColumnName(i).toUpperCase()+ "|");
			}
			System.out.println();
			while(rs.next()){
				for(int i = 1;i <= rsM.getColumnCount();i++){
					System.out.print(rs.getObject(i).toString()+ "|");
				}
				System.out.println();
			}
			state.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void lireTableUp(String s){
		try {
			ps1.setString(1, s);
			ResultSet rs = ps1.executeQuery();
			ResultSetMetaData rsM = rs.getMetaData();
			for(int i = 1; i<= rsM.getColumnCount();i++){
				System.out.print(rsM.getColumnName(i).toUpperCase()+ "|");
			}
			System.out.println();
			while(rs.next()){
				for(int i = 1;i <= rsM.getColumnCount();i++){
					System.out.print(rs.getObject(i).toString()+ "|");
				}
				System.out.println();
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public int insererLigne(String s){
		int i = -1;
		
		try {
			cS1.setString(2,s );
			cS1.registerOutParameter(1, java.sql.Types.INTEGER);
			cS1.execute();
			i = cS1.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return i;
	}
	
	public static void main(String[] arg){
		TpSimple tp = new TpSimple("quentin", Mdp.mdp);
		System.out.println(tp.insererLigne("bob"));
		tp.lireTable();
		//tp.lireTableUp("%H%");
		tp.seDeco();
	}
	
}
