package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import connectionManager.ConnectionManager;
import log.LogMgr;
import model.Client;
import model.Contrat;
import model.Reservation;
import model.Reservation.filtre;
import model.Vehicule;

public interface ReservationDAO {
	
	/**
	 * Recherche tous les reservations stock�es dans la base de donnees
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
				r.setVehicule(new Vehicule());
				r.setCodeReservation(result.getInt("codeReservation"));
				
				r.getClient().setCodeClient(result.getInt("codeClient"));
				r.getVehicule().setCodeVehicule(result.getString("codeVehicule"));
				
				r.setDateDepart(result.getDate("dateDepReservation"));
				r.setDateRetour(result.getDate("dateRetReservation"));
				
				r.setValid(result.getBoolean("isValid"));
				r.setCanceled(result.getBoolean("isCanceled"));
				
				//Rechercher les informations liees au client associee � la reservation
				query = "SELECT * FROM client WHERE codeClient = ?;";
				PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(query);
				ps.setInt(1, r.getClient().getCodeClient());
				
				ResultSet result2 = ps.executeQuery();
				if(result2.next()) {
					Client c = r.getClient();
					c.setNomClient(result2.getString("nomClient"));
					c.setPrenomClient(result2.getString("prenomClient"));
				}
				
				query = "SELECT * FROM vehicule WHERE Immatriculation LIKE ?;";
				ps = ConnectionManager.getConnection().prepareStatement(query);
				ps.setString(1, query);
				result2 = ps.executeQuery();
				if(result2.next()) {
					Vehicule v = r.getVehicule();
					v.setPrixLocation(result2.getInt("prixLocation"));
				}
				
				//Ajouter la reservation a la list
				reservList.add(r);
			}			
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur Reservation", "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Reservation", e);
		}
		
		return reservList;
	}

	/**methode qui retourne la liste des reservations correspondant au critere de recherche*/
	public static ArrayList<Reservation> findUserAutoCompleting(int codeReservation) {
		String query="SELECT *"
				+" FROM reservation"
				+" WHERE  codeReservation like ?;";
	ArrayList<Reservation> reserv_list = new ArrayList<Reservation>();
	try {
		PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
		prepared.setString(1, codeReservation+"%");
		ResultSet result = prepared.executeQuery();
		try {
			while (result.next()) {
				Reservation r = new Reservation();
				r.setClient(new Client());
				r.setVehicule(new Vehicule());
				r.setCodeReservation(result.getInt("codeReservation"));
				
				r.getClient().setCodeClient(result.getInt("codeClient"));
				r.getVehicule().setCodeVehicule(result.getString("codeVehicule"));
				
				r.setDateDepart(result.getDate("dateDepReservation"));
				r.setDateRetour(result.getDate("dateRetReservation"));
				
				r.setValid(result.getBoolean("isValid"));
				r.setCanceled(result.getBoolean("isCanceled"));
				//Rechercher les informations liees au client associee � la reservation
				query = "SELECT * FROM client WHERE codeClient = ?;";
				PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(query);
				ps.setInt(1, r.getClient().getCodeClient());
				
				ResultSet result2 = ps.executeQuery();
				if(result2.next()) {
					Client c = r.getClient();
					c.setNomClient(result2.getString("nomClient"));
					c.setPrenomClient(result2.getString("prenomClient"));
				}
				reserv_list.add(r);
			}
			
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "vehicule display error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		
	} catch (SQLException e) {
		JOptionPane.showConfirmDialog(null, e.getMessage(), "vehicule display error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		e.printStackTrace();
	}
	
	return reserv_list;
	}
	
	/**
	 * Methode qui recherche une reservation par son code
	 * @param codeReservation
	 * @return ArrayList<Reservation> qui contient une seule reservation aux max.
	 */
	public static ArrayList<Reservation> findReservation(int codeReservation) {
		String query = "SELECT * FROM reservation WHERE codeReservation = ?;";
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
				r.setVehicule(new Vehicule());
				r.setCodeReservation(result.getInt("codeReservation"));
				
				r.getClient().setCodeClient(result.getInt("codeClient"));
				r.getVehicule().setCodeVehicule(result.getString("codeVehicule"));
				
				r.setDateDepart(result.getDate("dateDepReservation"));
				r.setDateRetour(result.getDate("dateRetReservation"));
				
				r.setValid(result.getBoolean("isValid"));
				r.setCanceled(result.getBoolean("isCanceled"));
				
				//Rechercher les informations liees au client associee � la reservation
				query = "SELECT * FROM client WHERE codeClient = ?;";
				PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(query);
				ps.setInt(1, r.getClient().getCodeClient());
				
				ResultSet result2 = ps.executeQuery();
				if(result2.next()) {
					Client c = r.getClient();
					c.setNomClient(result2.getString("nomClient"));
					c.setPrenomClient(result2.getString("prenomClient"));
				}
				
				query = "SELECT * FROM vehicule WHERE Immatriculation LIKE ?;";
				ps = ConnectionManager.getConnection().prepareStatement(query);
				ps.setString(1, query);
				result2 = ps.executeQuery();
				if(result2.next()) {
					Vehicule v = r.getVehicule();
					v.setPrixLocation(result2.getInt("prixLocation"));
				}
				
				
				//Ajouter la reservation a la list
				reservList.add(r);
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur Recherche Reservation", "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Recherche Reservation", e);
		}
		
		return reservList;
	}
	/**METHODE QUI VALIDE UNE RESERVATION*/
	public static void setReservationValid(int codeReservation) {
		String query = "UPDATE `reservation`"
				+ " SET  `isValid` = 1"
				+ " WHERE `reservation`.`codeReservation` = ?;";
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setInt(1, codeReservation);
			prepared.execute();
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur Validation de la Reservation", "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Modification Reservation", e);
		}
	}

	/**
	 * Methode qui cr�ee une nouvelle reservation dans la bd
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
			JOptionPane.showConfirmDialog(null, "Erreur Creation Reservation", "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Creation Reservation", e);
		}
	}
	
	/**
	 * M�thode qui modifie une r�servation dans la BD
	 * @param codeReservation
	 * @param dateDep
	 * @param dateRet
	 * @param isValid
	 * @param isCanceled
	 * @throws SQLException
	 * @throws InvalidDate
	 */
	public static void modifyReservation(int codeReservation, Date dateDep, Date dateRet, boolean isValid, boolean isCanceled){
		
		String query = "UPDATE `reservation`"
				+ " SET `dateDepReservation` = ?,"
				+ " `dateRetReservation` = ?,"
				+ " `isCanceled` = ?,"
				+ " `isValid` = ?"
				+ " WHERE `reservation`.`codeReservation` = ?;";
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setDate(1, dateDep);
			prepared.setDate(2, dateRet);
			prepared.setBoolean(3, isCanceled);
			prepared.setBoolean(4, isValid);
			prepared.setInt(5, codeReservation);
			prepared.execute();
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur Modification Reservation", "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Modification Reservation", e);
		}
	}
	/**
	 * Supprime la reservation qui correspond aux codeReserv pass� comme argument
	 * @param codeReserv
	 */
	public static void deleteReservation (int codeReserv) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("DELETE FROM reservation WHERE codeReservation = ?");
			prepared.setInt(1, codeReserv);
			prepared.execute();
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur Supression Reservation", "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Supression Reservation", e);
		}
	}
	
	
	
	/**
	 * Methode qui verifie si une reservation intersect avec une autre 
	 * 
	 * @param codeVehi
	 * @param dateDep
	 * @param dateRet
	 * @param codeReserv (veuillez utiliser codeReserv < 0 pour la creation d'une nouvelle reservation)
	 * @return boolean: true si la date est disponible, false sinon
	 */
	public static boolean isReservationDateAvailable(String codeVehi, Date dateDep, Date dateRet, int codeReserv){
		
		//query that return a list of reservations that are in the specified periode
		String query = "SELECT codeReservation "
					 + "FROM reservation, vehicule "
					 + "WHERE reservation.codeVehicule = vehicule.Immatriculation "
					 + "AND vehicule.Immatriculation = ? "
					 + "AND (dateDepReservation <= ? AND dateRetReservation >= ?)";
		
		if(codeReserv > 0) {
			//Pour la modification : exlusion de la reservation qu'on veut modifier
			query += " AND codeReservation <> ?;";
		}
		PreparedStatement preparedSt;
		
		try {
			preparedSt = ConnectionManager.getConnection().prepareStatement(query);
			preparedSt.setString(1, codeVehi);
			preparedSt.setDate(2, dateRet);
			preparedSt.setDate(3, dateDep);
			if(codeReserv > 0) {
				preparedSt.setInt(4, codeReserv);
			}
			ResultSet result = preparedSt.executeQuery();
			
			//if there is no reservation return true; 
			return !result.next();
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur Validation Date Reservation", "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Validation Date Reservation", e);
		}
		return false;
	}


	
}
