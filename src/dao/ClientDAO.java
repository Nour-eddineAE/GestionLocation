package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("INSERT INTO `client` (`id`, `nom`, `prenom`, `adresse`, `numTel`, `image`, `permisScannee`) VALUES (NULL, ?, ?, ?, ?, ?, ?)");
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
}
