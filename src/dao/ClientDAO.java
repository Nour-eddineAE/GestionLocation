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

	public static ArrayList<Client> actualiserClient () {
		String query = "SELECT * FROM client";
		ResultSet result = ConnectionManager.execute(query);
		ArrayList<Client> list = new ArrayList<Client>();
		try {
			while (result.next()) {
				Client client = new Client(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7));
				list.add(client);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showConfirmDialog(null,
					e.getMessage(), "Attention",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			
		}
		return list;
	}
	
	public static boolean creerClient (Client client) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("INSERT INTO `client` (`codeClient`, `nomClient`, `prenomClient`, `adresseClient`, `telClient`, `imageClient`, `permisScanneeClient`) VALUES (NULL, ?, ?, ?, ?, ?, ?)");
			prepared.setString(1, client.getNomClient());
			prepared.setString(2, client.getPrenomClient());
			prepared.setString(3, client.getAddresseClient());
			prepared.setString(4, client.getNumTelClient());
			prepared.setString(5, client.getImage());
			prepared.setString(6, client.getPermisScannee());
			prepared.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Echoue", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
	}
	
	public static Client chercherClient (int code) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("SELECT * FROM client WHERE codeClient LIKE ?");
			prepared.setString(1, ""+code);
			ResultSet result = prepared.executeQuery();
			if (result.next()) {
				Client client = new Client(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7));
				return client;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Echoue", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	
	public static ArrayList<Client> findClient (String nom) {
		try {
			ArrayList<Client> list = new ArrayList<Client>();
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("SELECT * FROM client WHERE nomClient LIKE ?");
			prepared.setString(1, nom);
			ResultSet result = prepared.executeQuery();
			while(result.next()) {
				list.add(new Client (Integer.parseInt(result.getString(1).toString()),result.getString(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6),result.getString(7)));
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean modifierClient (Client client) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("UPDATE `client` SET `nomClient` = ?, `prenomClient` = ?, `adresseClient` = ?, `telClient` = ?, `imageClient` = ?, `permisScanneeClient` = ? WHERE `client`.`codeClient` = ?");
			prepared.setString(1, client.getNomClient());
			prepared.setString(2, client.getPrenomClient());
			prepared.setString(3, client.getAddresseClient());
			prepared.setString(4, client.getNumTelClient());
			prepared.setString(5, client.getImage());
			prepared.setString(6, client.getPermisScannee());
			prepared.setInt(7, client.getCodeClient());
			prepared.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static void supprimerClient (int code) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("DELETE FROM `client` WHERE `client`.`codeClient` = ?");
			prepared.setInt(1, code);
			prepared.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
