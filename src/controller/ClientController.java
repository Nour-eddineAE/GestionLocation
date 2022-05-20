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
	
	public static void fetchAll (JTable table) {
		ArrayList<Client> list = ClientDAO.actualiserClient();
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("Id");
		dtm.addColumn("nom");
		dtm.addColumn("prenom");
		dtm.addColumn("num Tel");
		table.setModel(dtm);
		Iterator itr = list.iterator();
		
		while (itr.hasNext()) {
			Client client = (Client)itr.next();
			Object[] object = {client.getCodeClient(), client.getNomClient(), client.getPrenomClient(), client.getNumTelClient()};
			dtm.addRow(object);
		}
		
	}
	
	public static boolean creatClient (Client client) {
		return ClientDAO.creerClient(client);
	}
	
	public static void findClientByName (String nom, JTable table) {
		ArrayList<Client> list = ClientDAO.findClient(nom);
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("Id");
		dtm.addColumn("nom");
		dtm.addColumn("prenom");
		dtm.addColumn("num Tel");
		table.setModel(dtm);
		Iterator itr = list.iterator();
		
		while (itr.hasNext()) {
			Client client = (Client)itr.next();
			Object[] object = {client.getCodeClient(), client.getNomClient(), client.getPrenomClient(), client.getNumTelClient()};
			dtm.addRow(object);
		}
		
	}
	
	public static Client findClientByCode (int code) {
		Client client = ClientDAO.chercherClient(code);
		return client;
	}
	
	public static void deleteClient (String id) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("DELETE FROM `client` WHERE `client`.`codeClient` = ?");
			prepared.setString(1, id);
			prepared.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean modifyClient (Client client) {
		return ClientDAO.modifierClient(client);
	}

}
