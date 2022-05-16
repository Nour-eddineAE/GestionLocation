package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import connectionManager.ConnectionManager;
import exceptions.InvalidDate;

import java.sql.Date;
import java.sql.PreparedStatement;

public class ReservationController {
	public enum filtre{
		Tous,
		Valide,
		Non_valide,
		Annule
	};
	public static void fetchAll (JTable table, filtre fil) {
		String query= "SELECT * FROM reservation, client WHERE reservation.codeClient = client.codeClient ";
		switch(fil) {
			case Tous:
				break;
			case Valide:
				//Select only valid an not canceled reservations
				query += "AND isValid = true AND isCanceled = false ";
				break;
			case Non_valide:
				//Select only non valid an not canceled reservations
				query += "AND isValid = false AND isCanceled = false ";
				break;
			case Annule:
				//Select canceled reservations
				query += "AND isCanceled = true ";
				break;
		}
		query += "ORDER BY dateReservation DESC;";
		ResultSet result = ConnectionManager.execute(query);
		
		DefaultTableModel dtm = prepareTable(table);
		
		try {
			while (result.next()) {
				String codeReserv = result.getString("codeReservation");
				String nomClient = result.getString("nomClient");
				String prenomClient = result.getString("prenomClient");
				String vehicule = result.getString("codeVehicule");
				Date dateDepart = result.getDate("dateDepReservation");
				Date dateRetour = result.getDate("dateRetReservation");
				boolean isValid = result.getBoolean("isValid");
				boolean isCanceled = result.getBoolean("isCanceled");
				Object[] row = {codeReserv, nomClient, prenomClient, vehicule, dateDepart, dateRetour, isValid, isCanceled};
				
				dtm.addRow(row);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteReservation (String codeReserv) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("DELETE FROM reservation WHERE codeReservation = ?");
			prepared.setString(1, codeReserv);
			prepared.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void findReservation(String codeReservation, JTable table) {
		String query = "SELECT * FROM reservation, client WHERE reservation.codeClient = client.codeClient AND codeReservation = ?;";
		PreparedStatement preparedSt;
		
		DefaultTableModel dtm = prepareTable(table);
		try {
			preparedSt = ConnectionManager.getConnection().prepareStatement(query);
			preparedSt.setString(1, codeReservation);
			ResultSet result = preparedSt.executeQuery();
			while (result.next()) {
				String codeReserv = result.getString("codeReservation");
				String nomClient = result.getString("nomClient");
				String prenomClient = result.getString("prenomClient");
				String vehicule = result.getString("codeVehicule");
				Date dateDepart = result.getDate("dateDepReservation");
				Date dateRetour = result.getDate("dateRetReservation");
				boolean isValid = result.getBoolean("isValid");
				boolean isCanceled = result.getBoolean("isCanceled");
				Object[] row = {codeReserv, nomClient, prenomClient, vehicule, dateDepart, dateRetour, isValid, isCanceled};
				
				dtm.addRow(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createReservation(String codeClient, String codeVoiture, String dateDep, String dateRet) throws SQLException, InvalidDate {
		
		//check if dates are valid and non of the values is null
		checkDates(dateDep, dateRet);
		
		String query = "INSERT INTO `reservation`"
				+ " (`codeReservation`, `dateReservation`, `dateDepReservation`, `dateRetReservation`, `isValid`, `isCanceled`, `codeClient`, `codeVehicule`)"
				+ " VALUES (NULL, CURRENT_DATE(), ?, ?, '0', '0', ?, ?);";
		PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
		prepared.setString(1, dateDep);
		prepared.setString(2, dateRet);
		prepared.setString(3, codeClient);
		prepared.setString(4, codeVoiture);
		prepared.execute();
	}
	public static void modifyReservation(String codeReservation, String dateDep, String dateRet, boolean isValid, boolean isCanceled) throws SQLException, InvalidDate{
		
		//check if dates are valid and non of the values is null
		checkDates(dateDep, dateRet);
		
		String query = "UPDATE `reservation`"
				+ " SET `dateDepReservation` = ?,"
				+ " `dateRetReservation` = ?,"
				+ " `isCanceled` = ?,"
				+ " `isValid` = ?"
				+ " WHERE `reservation`.`codeReservation` = ?;";
		PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
		prepared.setString(1, dateDep);
		prepared.setString(2, dateRet);
		prepared.setString(3, isCanceled? "1":"0");
		prepared.setString(4, isValid? "1":"0");
		prepared.setString(5, codeReservation);
		prepared.execute();
	}
	
	public static void checkDates(String dateDep, String dateRet) throws InvalidDate {
		//check if dates are valid and non of the values is null
		if(dateDep.split("-")[0].equals("null") || dateDep.split("-")[1].equals("null") || dateDep.split("-")[2].equals("null")
		  || dateRet.split("-")[0].equals("null") || dateRet.split("-")[1].equals("null")|| dateRet.split("-")[2].equals("null")) {
			throw new InvalidDate("Veuillez choisir une date.");
		}
		
		LocalDate d1 = LocalDate.parse(dateDep);
		LocalDate d2 = LocalDate.parse(dateRet);
		Duration d = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
		
		if(d.toSeconds() <= 0)
			throw new InvalidDate("La date de retour doit être aprés la date de depart.\ndateDep: "+dateDep+", dateRet: "+dateRet+".");
	}
	
	public static boolean isReservationDateAvailable(String codeVehi, String dateDep, String dateRet){
		
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
			preparedSt.setString(2, dateRet);
			preparedSt.setString(3, dateDep);
			ResultSet result = preparedSt.executeQuery();
			
			//if there is no reservation return true; 
			return !result.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static DefaultTableModel prepareTable(JTable table) {
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("Code Reservation");
		dtm.addColumn("Nom Client");
		dtm.addColumn("Prenom Client");
		dtm.addColumn("Vehicule");
		dtm.addColumn("Date Depart");
		dtm.addColumn("Date Retour");
		dtm.addColumn("Validée");
		dtm.addColumn("Annulée");
		dtm.setRowCount(0);
		table.setModel(dtm);
		return dtm;
	}
}
