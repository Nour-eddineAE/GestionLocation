package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import connectionManager.ConnectionManager;

public class FactureController {
	public static void fetchAll (JTable table) {
		String query= "SELECT * "
					+ "FROM facture, contrat, client, reservation "
					+ "WHERE facture.codeContrat = contrat.codeContrat "
					+ "AND contrat.codeReservation = reservation.codeReservation "
					+ "AND reservation.codeClient = client.codeClient;";
		ResultSet result = ConnectionManager.execute(query);
		
		DefaultTableModel dtm = prepareTable(table);
		
		try {
			while (result.next()) {
				int codeFacture = result.getInt("codeFacture");
				String nomClient = result.getString("nomClient");
				String prenomClient = result.getString("prenomClient");
				int codeContrat = result.getInt("codeContrat");;
				int montant = result.getInt("montantFacture");
				
				Object[] row = {};
				
				dtm.addRow(row);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DefaultTableModel prepareTable(JTable table) {
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("N° Facture");
		dtm.addColumn("Prenom Client");
		dtm.addColumn("Nom Client");
		dtm.addColumn("N° Contrat");
		dtm.addColumn("Montant");
		dtm.setRowCount(0);
		table.setModel(dtm);
		return dtm;
	}
}
