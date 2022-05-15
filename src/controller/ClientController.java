package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import connectionManager.ConnectionManager;

public class ClientController {
	
	public static void fetchAll (JTable table) {
		String query = "SELECT * FROM client";
		ResultSet result = ConnectionManager.execute(query);
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("Code");
		dtm.addColumn("Nom");
		dtm.addColumn("Prenom");
		dtm.addColumn("Num Tel");
		table.setModel(dtm);
		try {
			while (result.next()) {
				Object[] object = {result.getInt("codeClient"), result.getString("nomClient"), result.getString("prenomClient"), result.getString("telClient")};
				dtm.addRow(object);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean creatClient (String nom, String prenom, String numTel) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("INSERT INTO `client` (`id`, `nom`, `prenom`, `numTel`) VALUES (NULL, ?, ?, ?)");
			prepared.setString(1, nom);
			prepared.setString(2, prenom);
			prepared.setString(3, numTel);
			prepared.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	public static void findClient (String nom, JTable table) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("SELECT * FROM client WHERE nom LIKE ?");
			prepared.setString(1, nom);
			ResultSet result = prepared.executeQuery();
			DefaultTableModel dtm = new DefaultTableModel();
			dtm.addColumn("Id");
			dtm.addColumn("nom");
			dtm.addColumn("prenom");
			dtm.addColumn("num Tel");
			table.setModel(dtm);
			try {
				while (result.next()) {
					Object[] object = {result.getString("id"), result.getString("nom"), result.getString("prenom"), result.getString("numTel")};
					dtm.addRow(object);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void deleteClient (String id) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("DELETE FROM `client` WHERE `client`.`id` = ?");
			prepared.setString(1, id);
			prepared.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean modifyClient (String id, String nom, String prenom, String numTel) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("UPDATE `client` SET `nom` = ?, `prenom` = ?, `numTel` = ? WHERE `client`.`id` = ?");
			prepared.setString(1, nom);
			prepared.setString(2, prenom);
			prepared.setString(3, numTel);
			prepared.setString(4, id);
			prepared.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
