package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import connectionManager.ConnectionManager;
import model.Client;
import model.Reservation;
import model.Reservation.filtre;

public class ReservationDAO {
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
}
