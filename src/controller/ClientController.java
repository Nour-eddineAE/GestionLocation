package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import connectionManager.ConnectionManager;
import dao.ClientDAO;
import model.Client;

public class ClientController {

	public static void fetchAll(JTable table) {
		// rederiger le travaille de recherche au couche DAO
		ArrayList<Client> list = ClientDAO.actualiserClient();
		// preparer le model
		DefaultTableModel dtm = preparerModel(list);
		table.setModel(dtm);
	}

	public static boolean creatClient(Client client) {
		// rederiger le travaille d'interaction avec base de donn�e au couche DAO
		return ClientDAO.creerClient(client);
	}

	public static void findClientByName(String nom, JTable table) {
		ArrayList<Client> list = ClientDAO.findClient(nom);
		DefaultTableModel dtm = preparerModel(list);
		table.setModel(dtm);
	}

	public static Client findClientByCode(int code) {
		Client client = ClientDAO.chercherClient(code);
		return client;
	}

	public static void deleteClient(String id) {
		ClientDAO.supprimerClient(Integer.parseInt(id));
	}

	public static boolean modifyClient(Client client) {
		return ClientDAO.modifierClient(client);
	}

	public static DefaultTableModel preparerModel(ArrayList<Client> list) {
		// preparer le model du tableau
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("Id");
		dtm.addColumn("nom");
		dtm.addColumn("prenom");
		dtm.addColumn("num Tel");

		Iterator itr = list.iterator();

		// remplir le model par les informations extraites de base de donn�es
		while (itr.hasNext()) {
			Client client = (Client) itr.next();
			Object[] object = { client.getCodeClient(), client.getNomClient(), client.getPrenomClient(),
					client.getNumTelClient() };
			dtm.addRow(object);
		}

		return dtm;
	}

	public static void findVehicule(JTable table, String code) {
		ResultSet result = ClientDAO.chercherVehicule(code);
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("Matricule");
		dtm.addColumn("Marque");
		dtm.addColumn("Date de d�part");
		dtm.addColumn("Date de retour");
		try {
			while (result.next()) {
				Object[] object = { result.getString(1), result.getString(2), result.getString(3),
						result.getString(4) };
				dtm.addRow(object);
			}
			table.setModel(dtm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
