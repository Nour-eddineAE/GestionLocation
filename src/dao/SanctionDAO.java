package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import connectionManager.ConnectionManager;
import log.LogMgr;
import model.*;

public interface SanctionDAO {
	
	/**
	 * Methode qui recherche tous les clients sanctionnees et le montant a payer
	 * @return ArrayList<Sanction>
	 */
	public static ArrayList<Sanction> fetchAll(){
		String query = "SELECT client.codeClient, nomClient, prenomClient, SUM(DATEDIFF(dateRetActuel, dateEcheance) * 2000) AS montantSanction "
				+ "FROM contrat, client, reservation "
				+ "WHERE dateRetActuel IS NOT NULL "
				+ "AND dateRetActuel > dateEcheance "
				+ "AND contrat.codeReservation = reservation.codeReservation "
				+ "AND reservation.codeClient = client.codeClient "
				+ "AND sanctionRegle = 0 "
				+ "GROUP BY codeClient";
		
		ResultSet result = ConnectionManager.execute(query);
		
		ArrayList<Sanction> sList = new ArrayList<Sanction>();
		
		try {
			while(result.next()) {
				Sanction s = new Sanction();
				s.setMontant(result.getInt("montantSanction"));
				
				Client c = new Client();
				c.setCodeClient(result.getInt("codeClient"));
				c.setNomClient(result.getString("nomClient"));
				c.setPrenomClient(result.getString("prenomClient"));
				
				s.setClient(c);
				
				sList.add(s);
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur Sanction", "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Sanction", e);
		}
		
		return sList;
	}
	
	
	/**
	 * Methode qui recherche tous les contrats d'une sanction d'un client
	 * @param codeClient
	 * @return ArrayList<Contrat>
	 */
	
	public static ArrayList<Contrat> getContracts(int codeClient) {
		ArrayList<Contrat> cList = new ArrayList<Contrat>();
		
		String query = "SELECT *, DATEDIFF(dateRetActuel, dateEcheance) * 2000 AS montantSanction "
		+ "FROM contrat, client, reservation "
		+ "WHERE dateRetActuel IS NOT NULL "
		+ "AND dateRetActuel > dateEcheance "
		+ "AND contrat.codeReservation = reservation.codeReservation "
		+ "AND reservation.codeClient = client.codeClient "
		+ "AND client.codeClient = ? "
		+ "AND sanctionRegle = 0";

		PreparedStatement ps;
		try {
			ps = ConnectionManager.getConnection().prepareStatement(query);
			ps.setInt(1, codeClient);
			
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				Contrat cont = new Contrat();
				cont.setCodeContrat(result.getInt("codeContrat"));
				cont.setDateContrat(result.getDate("dateContrat"));
				cont.setDateEcheance(result.getDate("dateEcheance"));
				cont.setDateRetActuel(result.getDate("dateRetActuel"));
				cont.setMontantSanction(result.getInt("montantSanction"));
				
				Vehicule v = new Vehicule();
				v.setCodeVehicule(result.getString("codeMatricule"));
				
				Reservation reserv = new Reservation();
				reserv.setVehicule(v);
				
				cont.setReservation(reserv);
				
				cList.add(cont);
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur Recherche Contrats de Sanction", "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Recherche Contrats de Sanction", e);
		}
		return cList;
	}
	/**
	 * Methode qui regle les Sanctions d'un client
	 * @param codeClient
	 */
	public static void reglerSanction(int codeClient) {
		String query = "UPDATE contrat "
				+ "SET sanctionRegle = 1 "
				+ "WHERE dateRetActuel IS NOT NULL "
				+ "AND dateRetActuel > dateEcheance "
				+ "AND contrat.codeReservation = (SELECT codeReservation from reservation WHERE reservation.codeClient = ?) "
				+ "AND sanctionRegle = 0;";
		
		try {
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(query);
			ps.setInt(1, codeClient);
			ps.execute();
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur Reglement Sanction", "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Reglement Sanction", e);
		}
		
	}
}
