package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import connectionManager.ConnectionManager;
import exceptions.InvalidDate;
import model.Client;
import model.Reservation;
import model.Reservation.filtre;

public class ReservationDAO {
	
	/**
	 * Recherche tous les reservations stockées dans la base de donnees
	 * @param fil
	 * @return list of reservations 
	 */
	public static ArrayList<Reservation> fetchAll (filtre fil) {
		String query= "SELECT * FROM reservation ";
		switch(fil) {
			case Tous:
				break;
			case Valide:
				//Select seulement les reservations validees et non Annulees
				query += "WHERE isValid = true AND isCanceled = false ";
				break;
			case Non_valide:
				//Select seulement les reservations non validees et non Annulees
				query += "WHERE isValid = false AND isCanceled = false ";
				break;
			case Annule:
				//Select Seulement les reservations annulees
				query += "WHERE isCanceled = true ";
				break;
		}
		query += "ORDER BY dateReservation DESC;";
		ResultSet result = ConnectionManager.execute(query);
		ArrayList<Reservation> reservList = new ArrayList<Reservation>();
		
		try {
			while (result.next()) {
				
				//Creer nouvelle Reservation
				Reservation r = new Reservation();
				
				r.setClient(new Client());
				r.setCodeReservation(result.getInt("codeReservation"));
				
				r.getClient().setCodeClient(result.getInt("codeClient"));
				r.setCodeVehicule(result.getString("codeVehicule"));
				
				r.setDateDepart(result.getDate("dateDepReservation"));
				r.setDateRetour(result.getDate("dateRetReservation"));
				
				r.setValid(result.getBoolean("isValid"));
				r.setCanceled(result.getBoolean("isCanceled"));
				
				//Rechercher les informations liees au client associee à la reservation
				query = "SELECT * FROM client WHERE codeClient = ?;";
				PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(query);
				ps.setInt(1, r.getClient().getCodeClient());
				
				ResultSet result2 = ps.executeQuery();
				if(result2.next()) {
					Client c = r.getClient();
					c.setNom(result2.getString("nomClient"));
					c.setPrenom(result2.getString("prenomClient"));
				}
				
				//Ajouter la reservation a la list
				reservList.add(r);
			}			
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		
		return reservList;
	}

	
	/**
	 * Methode qui recherche une reservation par son code
	 * @param codeReservation
	 * @return ArrayList<Reservation> qui contient une seule reservation aux max.
	 */
	public static ArrayList<Reservation> findReservation(int codeReservation) {
		String query = "SELECT * FROM reservation, client WHERE reservation.codeClient = client.codeClient AND codeReservation = ?;";
		PreparedStatement preparedSt;
		
		ArrayList<Reservation> reservList = new ArrayList<Reservation>();
		
		try {
			preparedSt = ConnectionManager.getConnection().prepareStatement(query);
			preparedSt.setInt(1, codeReservation);
			ResultSet result = preparedSt.executeQuery();
			while (result.next()) {
				//Creer nouvelle Reservation
				Reservation r = new Reservation();
				
				r.setClient(new Client());
				r.setCodeReservation(result.getInt("codeReservation"));
				
				r.getClient().setCodeClient(result.getInt("codeClient"));
				r.setCodeVehicule(result.getString("codeVehicule"));
				
				r.setDateDepart(result.getDate("dateDepReservation"));
				r.setDateRetour(result.getDate("dateRetReservation"));
				
				r.setValid(result.getBoolean("isValid"));
				r.setCanceled(result.getBoolean("isCanceled"));
				
				//Rechercher les informations liees au client associee à la reservation
				query = "SELECT * FROM client WHERE codeClient = ?;";
				PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(query);
				ps.setInt(1, r.getClient().getCodeClient());
				
				ResultSet result2 = ps.executeQuery();
				if(result2.next()) {
					Client c = r.getClient();
					c.setNom(result2.getString("nomClient"));
					c.setPrenom(result2.getString("prenomClient"));
				}
				
				//Ajouter la reservation a la list
				reservList.add(r);
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		
		return reservList;
	}
	
	
	/**
	 * Methode qui créee une nouvelle reservation dans la bd
	 * @param codeClient
	 * @param codeVoiture
	 * @param dateDep
	 * @param dateRet
	 */
	public static void createReservation(int codeClient, String codeVoiture, Date dateDep, Date dateRet){
		
		String query = "INSERT INTO `reservation`"
				+ " (`codeReservation`, `dateReservation`, `dateDepReservation`, `dateRetReservation`, `isValid`, `isCanceled`, `codeClient`, `codeVehicule`)"
				+ " VALUES (NULL, CURRENT_DATE(), ?, ?, '0', '0', ?, ?);";
		PreparedStatement prepared;
		try {
			prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setDate(1, dateDep);
			prepared.setDate(2, dateRet);
			prepared.setInt(3, codeClient);
			prepared.setString(4, codeVoiture);
			prepared.execute();
			
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	/**
	 * Supprime la reservation qui correspond aux codeReserv passé comme argument
	 * @param codeReserv
	 */
	public static void deleteReservation (int codeReserv) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("DELETE FROM reservation WHERE codeReservation = ?");
			prepared.setInt(1, codeReserv);
			prepared.execute();
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	/**
	 * Methode qui verifie si une reservation intersect avec une autre
	 * @param codeVehi
	 * @param dateDep
	 * @param dateRet
	 * @return boolean: true si la date est disponible, false sinon
	 */
	public static boolean isReservationDateAvailable(String codeVehi, Date dateDep, Date dateRet){
		
		//query that return a list of reservations that are in the specified periode
		String query = "SELECT codeReservation "
					 + "FROM reservation, vehicule "
					 + "WHERE reservation.codeVehicule = vehicule.codeMatricule "
					 + "AND vehicule.codeMatricule = ? "
					 + "AND (dateDepReservation <= ? AND dateRetReservation >= ?);";
		PreparedStatement preparedSt;
		
		try {
			preparedSt = ConnectionManager.getConnection().prepareStatement(query);
			preparedSt.setString(1, codeVehi);
			preparedSt.setDate(2, dateRet);
			preparedSt.setDate(3, dateDep);
			ResultSet result = preparedSt.executeQuery();
			
			//if there is no reservation return true; 
			return !result.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
