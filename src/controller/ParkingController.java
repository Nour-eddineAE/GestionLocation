package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import connectionManager.ConnectionManager;

public class ParkingController {

	public static void fetchAll(JTable parkingtable) {
		String query = "SELECT * FROM parking";
		ResultSet result = ConnectionManager.execute(query);
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("Id");
		dtm.addColumn("designation");
		dtm.addColumn("nombre");
		parkingtable.setModel(dtm);
		try {
			while (result.next()) {
				Object[] object = {result.getString("id"), result.getString("designation"), result.getString("nombre")};
				dtm.addRow(object);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void findParking(String string, JTable table) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("SELECT * FROM parking WHERE designation LIKE ?");
			prepared.setString(1, string);
			ResultSet result = prepared.executeQuery();
			DefaultTableModel dtm = new DefaultTableModel();
			dtm.addColumn("Id");
			dtm.addColumn("designation");
			dtm.addColumn("nombre");
			table.setModel(dtm);
			try {
				while (result.next()) {
					Object[] object = {result.getString("id"), result.getString("designation"), result.getString("nombre")};
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

	public static void deleteParking(String string) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("DELETE FROM `parking` WHERE `parking`.`id` = ?");
			prepared.setString(1, string);
			prepared.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static boolean creatParking (String designation, int nombre) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("INSERT INTO `parking` (`id`, `designation`, `nombre`) VALUES (NULL, ?, ?)");
			prepared.setString(1, designation);
			prepared.setLong(2, nombre);
			prepared.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	public static boolean modifyParking(String id, String designation, int nombre) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("UPDATE `parking` SET `designation` = ?, `nombre` = ? WHERE `parking`.`id` = ?");
			prepared.setString(1, designation);
			prepared.setLong(2, nombre);
			prepared.setString(3, id);
			prepared.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
