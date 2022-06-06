package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import connectionManager.ConnectionManager;
import model.*;

public interface FactureDAO {
	
	/**
	 * recherche tous les factures stockees dans la base de donees
	 * @return list des factures
	 */
	public static ArrayList<Facture> fetchAll () {
		String query= "SELECT * "
				+ "FROM facture, contrat, client, reservation, vehicule "
				+ "WHERE facture.codeContrat = contrat.codeContrat "
				+ "AND contrat.codeReservation = reservation.codeReservation "
				+ "AND reservation.codeClient = client.codeClient "
				+ "AND reservation.codeVehicule = vehicule.codeMatricule "
				+ "ORDER BY dateFacture DESC;";
		
		ResultSet result = ConnectionManager.execute(query);
		
		ArrayList<Facture> fact_list = new ArrayList<Facture>();
		
		try {
			while (result.next()) {
				Facture f = new Facture();
				f.setCodeFacture(result.getInt("codeFacture"));
				f.setDateFacture(result.getDate("dateFacture"));
				f.setMontant(result.getInt("montantFacture"));
				
				Client client = new Client();
				client.setNomClient(result.getString("nomClient"));
				client.setPrenomClient(result.getString("prenomClient"));
				client.setCodeClient(result.getInt("codeClient"));
				
				Vehicule vehicule = new Vehicule();
				vehicule.setCodeVehicule(result.getString("codeVehicule"));
				vehicule.setPrixLocation(result.getInt("prixLocation"));
				
				Reservation reserv = new Reservation();
				reserv.setClient(client);
				reserv.setVehicule(vehicule);
				
				Contrat contrat = new Contrat();
				contrat.setCodeContrat(result.getInt("codeContrat"));
				contrat.setReservation(reserv);
				contrat.setDateEcheance(result.getDate("dateEcheance"));
				contrat.setDateContrat(result.getDate("dateContrat"));
				
				f.setContrat(contrat);
				
				fact_list.add(f);
			}
			
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Facture", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		
		return fact_list;
	}
	
	/**
	 * Methode qui cherche une facture avec codeFacture correspondant dans la BD
	 * @param codeFacture
	 * @return ArrayList<Facture> qui contient une facture
	 */
	public static ArrayList<Facture> findFacture(int codeFacture) {
		String query= "SELECT * "
				+ "FROM facture, contrat, client, reservation, vehicule "
				+ "WHERE facture.codeContrat = contrat.codeContrat "
				+ "AND contrat.codeReservation = reservation.codeReservation "
				+ "AND reservation.codeClient = client.codeClient "
				+ "AND codeFacture = ? "
				+ "ORDER BY dateFacture DESC;";
		
		ArrayList<Facture> fact_list = new ArrayList<Facture>();
		
		try {
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(query);
			ps.setInt(1, codeFacture);
			
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				Facture f = new Facture();
				f.setCodeFacture(result.getInt("codeFacture"));
				f.setDateFacture(result.getDate("dateFacture"));
				f.setMontant(result.getInt("montantFacture"));
				
				Client client = new Client();
				client.setNomClient(result.getString("nomClient"));
				client.setPrenomClient(result.getString("prenomClient"));
				client.setCodeClient(result.getInt("codeClient"));
				
				Vehicule vehicule = new Vehicule();
				vehicule.setCodeVehicule(result.getString("codeVehicule"));
				vehicule.setPrixLocation(result.getInt("prixLocation"));
				
				Reservation reserv = new Reservation();
				reserv.setClient(client);
				reserv.setVehicule(vehicule);
				
				Contrat contrat = new Contrat();
				contrat.setCodeContrat(result.getInt("codeContrat"));
				contrat.setReservation(reserv);
				contrat.setDateEcheance(result.getDate("dateEcheance"));
				contrat.setDateContrat(result.getDate("dateContrat"));
				
				f.setContrat(contrat);
				
				fact_list.add(f);
			}
			
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Recherche Facture", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		
		return fact_list;
	}
	
	/**
	 * Supprime la facture qui correspond aux codeFacture passé comme argument
	 * @param codeFacture
	 */
	public static void deleteFacture (int codeFacture) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("DELETE FROM facture WHERE codeFacture = ?");
			prepared.setInt(1, codeFacture);
			prepared.execute();
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Supression Facture", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Methode qui cree une facture dans la bd a partir d'un code de contrat
	 * @param codeContrat
	 */
	public static void createFacture(int codeContrat){
			
		//Query pour inserer une facture en calculant le montant automatiquement
		String query = "INSERT INTO `facture` (`codeFacture`,`dateFacture`, `montantFacture`, `codeContrat`) "
				     + "VALUES ("
				     + "	NULL,"
				     + " 	CURRENT_DATE(),"
				     + "	(SELECT DATEDIFF(dateEcheance, dateContrat)*prixLocation "
				     + "	 FROM contrat, vehicule"
				     + "	 WHERE contrat.codeMatricule = vehicule.codeMatricule"
				     + "	 AND contrat.codeContrat = ?),"
				     + "	?);";
		
		PreparedStatement prepared;
		try {
			prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setInt(1, codeContrat);
			prepared.setInt(2, codeContrat);
			prepared.execute();
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Creation Facture", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Methode qui recherche une facture associe a une contrat
	 * @param codeContrat
	 */
	
	public static Facture findFactureByContrat(int codeContrat) {
		String query= "SELECT * "
				+ "FROM facture, contrat, client, reservation, vehicule "
				+ "WHERE facture.codeContrat = contrat.codeContrat "
				+ "AND contrat.codeReservation = reservation.codeReservation "
				+ "AND reservation.codeClient = client.codeClient "
				+ "AND contrat.codeContrat = ? "
				+ "ORDER BY dateFacture DESC;";
		
		Facture f = new Facture();
		
		try {
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(query);
			ps.setInt(1, codeContrat);
			
			ResultSet result = ps.executeQuery();
			if (result.next()) {
				f.setCodeFacture(result.getInt("codeFacture"));
				f.setDateFacture(result.getDate("dateFacture"));
				f.setMontant(result.getInt("montantFacture"));
				
				Client client = new Client();
				client.setNomClient(result.getString("nomClient"));
				client.setPrenomClient(result.getString("prenomClient"));
				client.setCodeClient(result.getInt("codeClient"));
				
				Vehicule vehicule = new Vehicule();
				vehicule.setCodeVehicule(result.getString("codeVehicule"));
				vehicule.setPrixLocation(result.getInt("prixLocation"));
				
				Reservation reserv = new Reservation();
				reserv.setClient(client);
				reserv.setVehicule(vehicule);
				
				Contrat contrat = new Contrat();
				contrat.setCodeContrat(result.getInt("codeContrat"));
				contrat.setReservation(reserv);
				contrat.setDateEcheance(result.getDate("dateEcheance"));
				contrat.setDateContrat(result.getDate("dateContrat"));
				
				
				f.setContrat(contrat);
				
			}
			
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Recherche Facture par code Contrat", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		
		return f;
	}
}
