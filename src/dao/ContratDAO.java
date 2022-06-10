package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import connectionManager.ConnectionManager;
import model.Contrat;

public class ContratDAO {
	
//METHODE RETOURNANT UNE LISTE TOUS LES ENREGISTREMENT
	public static ArrayList<Contrat> fetchAll () {
		String query= "SELECT * "
				+ "FROM  contrat ";
		
		ResultSet result = ConnectionManager.execute(query);
		ArrayList<Contrat> contrat_list = new ArrayList<Contrat>();
		try {
			while (result.next()) {
			Contrat c=new Contrat(result.getInt("codeContrat"), 
						result.getDate("dateContrat"), 
						result.getDate("dateEcheance"),
						result.getDate("dateRetActuel"));
				contrat_list.add(c);
			}
				
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "contrat display error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		return contrat_list;
	}
		
// METHODE RECHERCHANT LE CONTRAT PAR SON MATRICULE
	public static Contrat findContract(int codeContrat) {
		String query="SELECT *"
				+" FROM contrat"
				+" WHERE  codeContrat LIKE ?;";
		Contrat c=null;
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setInt(1, codeContrat);
			ResultSet result = prepared.executeQuery();
			try {
				while (result.next()) {
					c=new Contrat(result.getInt("codeContrat"), 
							result.getDate("dateContrat"), 
							result.getDate("dateEcheance"),
							result.getDate("dateRetActuel"));
				}
				
			} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Ereur de recherche", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}	
		} catch (SQLException e) {
		JOptionPane.showConfirmDialog(null, e.getMessage(), "erreur de recherche", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		e.printStackTrace();
			}
		return c;	
	}
//VERIFIER SI UN CONTRAT EXISTE EN TESTANT SUR SON codeContrat LE RETOUR EST UN BOOLEAN
	public static boolean verifyContract(int codeContrat) {
		String query="SELECT *"
				+" FROM contrat"
				+" WHERE codeContrat LIKE ?";
				
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setInt(1,codeContrat);
			ResultSet result = prepared.executeQuery();
			if(result.next())
				return true;
			else 
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
//METHODE QUI RETOURNE LES CONTRATS  CORRESPONDANT AU CRITERE DE RECHERCHE 
	public static ArrayList<Contrat>  findContratAutoCompleting (int codeContrat) {
		String query="SELECT *"
					+" FROM contrat"
					+" WHERE  codeContrat like ?;";
		ArrayList<Contrat> contrat_list = new ArrayList<Contrat>();
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, codeContrat+"%");
			ResultSet result = prepared.executeQuery();
			try {
				while (result.next()) {
					Contrat c=new Contrat(result.getInt("codeContrat"), 
							result.getDate("dateContrat"), 
							result.getDate("dateEcheance"),
							result.getDate("dateRetActuel"));
					contrat_list.add(c);
				}
					
			} catch (SQLException e) {
				JOptionPane.showConfirmDialog(null, e.getMessage(), "contrat display error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}
				
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "contrat display error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return contrat_list;
	}
// MEHTODE QUI AJOUTE UN contrat A  LA BASE DE DONNEES 
	public static boolean createContrat(Contrat contrat) {
		String query="INSERT INTO `contrat` ( `dateContrat`, `dateEcheance`,`codeReservation`) VALUES ( ?, ?,?);";
		PreparedStatement prepared;
		try {
			prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setDate(1, contrat.getDateContrat());
			prepared.setDate(2, contrat.getDateEcheance());
			prepared.setInt(3, contrat.getReservation().getCodeReservation());
			prepared.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Creation contrat", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
//METHODE QUI ENVOI UNE REQUETE UPDATE AFIN DE CHANGER LES ATTRIBUTS D'UN CONTRAT
	public static boolean ChangeContrat(Date date,int oldCodeContrat) {
		String query="UPDATE `contrat`"
				+" SET `dateEcheance`=? "
				+" WHERE (`codeContrat` = ?);";
		PreparedStatement prepared;
		try {
			prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setDate(1,date);
			prepared.setInt(2,oldCodeContrat);
			prepared.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Creation contrat", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		
		return false;
	}

// METHODE SUPPRIMANT UN CONTRAT A L'AIDE DE SON MATRICULE
	public static boolean removeContrat(String id) {
		String query="DELETE FROM `contrat`"
					+" WHERE (`codeContrat` = ?);";
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, id);
			prepared.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Supression contrat", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
}
