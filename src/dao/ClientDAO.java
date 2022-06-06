package dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import connectionManager.ConnectionManager;
import model.Client;

public class ClientDAO {

	public static ArrayList<Client> actualiserClient() {
		// la requete a executer
		String query = "SELECT * FROM client ORDER BY `client`.`nomClient` ASC";
		// execution de la requete
		ResultSet result = ConnectionManager.execute(query);
		ArrayList<Client> list = new ArrayList<Client>();
		try {
			while (result.next()) {
				Client client = new Client(Integer.parseInt(result.getString(1)), result.getString(2),
						result.getString(3), result.getString(4), Long.parseLong(result.getString(5)),
						result.getString(6), result.getString(7));
				list.add(client);
			}

		} catch (SQLException e) {
			// genere erreur si le tableau client dans la base de donnée est non validé
			e.getMessage();

		}
		return list;
	}

	public static boolean creerClient(Client client) {
		try {
			// preparer la requete sql et apres l'executer
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(
					"INSERT INTO `client` (`codeClient`, `nomClient`, `prenomClient`, `adresseClient`, `telClient`, `imageClient`, `permisScanneeClient`) VALUES (NULL, ?, ?, ?, ?, ?, ?)");
			prepared.setString(1, client.getNomClient());
			prepared.setString(2, client.getPrenomClient());
			prepared.setString(3, client.getAddresseClient());
			prepared.setLong(4, client.getNumTelClient());
			prepared.setString(5, client.getImage());
			prepared.setString(6, client.getPermisScannee());
			// executer la requete
			prepared.execute();
			return true;
		} catch (SQLException e) {
			// genere erreur si le tableau client dans la base de donnée est non validé
			e.getMessage();
			return false;
		}

	}

	// cette fonction pour trouver une client a partir de son code
	public static Client chercherClient(int code) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection()
					.prepareStatement("SELECT * FROM client WHERE codeClient LIKE ?");
			prepared.setString(1, "" + code);
			ResultSet result = prepared.executeQuery();
			while (result.next()) {
				Client client = new Client(Integer.parseInt(result.getString(1)), result.getString(2),
						result.getString(3), result.getString(4), Long.parseLong(result.getString(5)),
						result.getString(6), result.getString(7));
				return client;
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return null;
	}

	// cette fonction pour trouver une client a partir de son nom
	public static ArrayList<Client> findClient(String nom) {
		try {
			ArrayList<Client> list = new ArrayList<Client>();
			PreparedStatement prepared = ConnectionManager.getConnection()
					.prepareStatement("SELECT * FROM client WHERE nomClient LIKE ?");
			prepared.setString(1, nom);
			ResultSet result = prepared.executeQuery();
			while (result.next()) {
				list.add(new Client(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3),
						result.getString(4), Long.parseLong(result.getString(5)), result.getString(6),
						result.getString(7)));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean modifierClient(Client client) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(
					"UPDATE `client` SET `nomClient` = ?, `prenomClient` = ?, `adresseClient` = ?, `telClient` = ?, `imageClient` = ?, `permisScanneeClient` = ? WHERE `client`.`codeClient` = ?");
			prepared.setString(1, client.getNomClient());
			prepared.setString(2, client.getPrenomClient());
			prepared.setString(3, client.getAddresseClient());
			prepared.setLong(4, client.getNumTelClient());
			prepared.setString(5, client.getImage());
			prepared.setString(6, client.getPermisScannee());
			prepared.setInt(7, client.getCodeClient());
			prepared.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void supprimerClient(int code) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection()
					.prepareStatement("DELETE FROM `client` WHERE `client`.`codeClient` = ?");
			prepared.setInt(1, code);
			prepared.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet chercherVehicule(String code) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(
					"SELECT codeMatricule, marqueVehicule, dateDepReservation, dateRetReservation FROM vehicule, reservation, client WHERE reservation.codeVehicule=vehicule.codeMatricule AND reservation.codeClient=client.codeClient AND reservation.codeClient=? AND reservation.isValid=1");
			prepared.setString(1, code);
			ResultSet result = prepared.executeQuery();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
