package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.sql.Date;
import connectionManager.ConnectionManager;

public class TempContratController {
	public static void fetchAll (JTable table) {
		String query= "SELECT * "
					+ "FROM contrat C, client, reservation "
					+ "WHERE C.codeReservation = reservation.codeReservation "
					+ "AND reservation.codeClient = client.codeClient "
					+ "AND NOT EXISTS(SELECT * FROM facture WHERE C.codeContrat = facture.codeContrat);";
		// We dont need to order by date because they are ordered by id which auto increment when we add a new date in
		
		ResultSet result = ConnectionManager.execute(query);
		
		DefaultTableModel dtm = prepareTable(table);
		
		try {
			while (result.next()) {
				int codeContrat = result.getInt("codeContrat");;
				String nomClient = result.getString("nomClient");
				String prenomClient = result.getString("prenomClient");
				Date dateContrat = result.getDate("dateContrat");
				Date dateEcheance = result.getDate("dateEcheance");
				String codeVehicule = result.getString("codeMatricule");
				
				Object[] row = {codeContrat, prenomClient, nomClient, dateContrat, dateEcheance, codeVehicule};
				
				dtm.addRow(row);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DefaultTableModel prepareTable(JTable table) {
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("N° Contrat");
		dtm.addColumn("Prenom Client");
		dtm.addColumn("Nom Client");
		dtm.addColumn("Date Contrat");
		dtm.addColumn("Date Echeance");
		dtm.addColumn("Vehicule");
		dtm.setRowCount(0);
		table.setModel(dtm);
		return dtm;
	}
}
