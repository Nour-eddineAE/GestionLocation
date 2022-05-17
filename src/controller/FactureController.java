package controller;


import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import connectionManager.ConnectionManager;

public class FactureController {
	public static void fetchAll (JTable table) {
		String query= "SELECT * "
					+ "FROM facture, contrat, client, reservation "
					+ "WHERE facture.codeContrat = contrat.codeContrat "
					+ "AND contrat.codeReservation = reservation.codeReservation "
					+ "AND reservation.codeClient = client.codeClient "
					+ "ORDER BY dateFacture DESC;";
		
		ResultSet result = ConnectionManager.execute(query);
		
		DefaultTableModel dtm = prepareTable(table);
		
		try {
			while (result.next()) {
				int codeFacture = result.getInt("codeFacture");
				String nomClient = result.getString("nomClient");
				String prenomClient = result.getString("prenomClient");
				int codeContrat = result.getInt("codeContrat");;
				int montant = result.getInt("montantFacture");
				
				Object[] row = {codeFacture, prenomClient, nomClient, codeContrat, montant};
				
				dtm.addRow(row);
			}
			
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void findFacture(String codeFact, JTable table) {
		String query = "SELECT * "
				+ "FROM facture, contrat, client, reservation "
				+ "WHERE facture.codeContrat = contrat.codeContrat "
				+ "AND contrat.codeReservation = reservation.codeReservation "
				+ "AND reservation.codeClient = client.codeClient "
				+ "AND codeFacture = ?;";
		PreparedStatement preparedSt;
		DefaultTableModel dtm = prepareTable(table);
		
		try {
			preparedSt = ConnectionManager.getConnection().prepareStatement(query);
			preparedSt.setString(1, codeFact);
			ResultSet result = preparedSt.executeQuery();
			while (result.next()) {
				int codeFacture = result.getInt("codeFacture");
				String nomClient = result.getString("nomClient");
				String prenomClient = result.getString("prenomClient");
				int codeContrat = result.getInt("codeContrat");;
				int montant = result.getInt("montantFacture");
				
				Object[] row = {codeFacture, prenomClient, nomClient, codeContrat, montant};
				
				dtm.addRow(row);
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void createFacture(int codeContrat) throws SQLException{
		
		//Query pour inserer une facture en calculant le montant automatiquement
		String query = "INSERT INTO `facture` (`codeFacture`,`dateFacture`, `montantFacture`, `codeContrat`) "
				     + "VALUES ("
				     + "	NULL,"
				     + " 	CURRENT_DATE(),"
				     + "	(SELECT DATEDIFF(dateEcheance, dateContrat)*prixLocation "
				     + "	 FROM contrat, vehicule"
				     + "	 WHERE contrat.codeMatricule = vehicule.codeMatricule"
				     + "	 AND contrat.codeContrat = ?),"
				     + "	?);";
		
		PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
		prepared.setInt(1, codeContrat);
		prepared.setInt(2, codeContrat);
		prepared.execute();
	}
	
	public static void deleteFacture(int codeFacture) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement("DELETE FROM facture WHERE codeFacture = ?");
			prepared.setInt(1, codeFacture);
			prepared.execute();
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
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
	
	public static void createPdf(int codeContrat) throws IOException {
		
		//Fonction pour creer un pdf de facture a partir du contrat associée (on fait appel a la methode directement aprés la creation d'une facture d'un contrat)
		
		int codeFacture = 0, codeClient = 0, montant = 0, prixLocation = 0;
		String nomClient = null, prenomClient = null, dateFacture = null, codeVehicule = null;
		String dateContrat = null, dateEcheance = null;
		
		String query= "SELECT * "
				+ "FROM facture, contrat, client, reservation, vehicule "
				+ "WHERE facture.codeContrat = contrat.codeContrat "
				+ "AND contrat.codeReservation = reservation.codeReservation "
				+ "AND reservation.codeClient = client.codeClient "
				+ "AND contrat.codeMatricule = vehicule.codeMatricule "
				+ "AND contrat.codeContrat = ? "
				+ "ORDER BY dateFacture DESC;";
		
		
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setInt(1, codeContrat);
			
			ResultSet result = prepared.executeQuery();
			if (result.next()) {
				codeFacture = result.getInt("codeFacture");
				nomClient = result.getString("nomClient");
				prenomClient = result.getString("prenomClient");
				montant = result.getInt("montantFacture");
				dateFacture = result.getString("dateFacture");
				codeClient = result.getInt("codeClient");
				codeVehicule = result.getString("codeMatricule");
				dateContrat = result.getString("dateContrat");
				dateEcheance = result.getString("dateEcheance");
				prixLocation = result.getInt("prixLocation");
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		
		String path = ".\\factures\\facture_"+codeFacture+".pdf";
		PdfWriter writer = new PdfWriter(path);
		PdfDocument pdfDoc = new PdfDocument(writer);
		
		pdfDoc.setDefaultPageSize(PageSize.A4);
		
		Document doc = new Document(pdfDoc);
		
		float columnWidth[] = {450, 150};
		
		Table header = new Table(columnWidth);
		header.setBackgroundColor(new DeviceRgb(75, 0, 130))
				.setFontColor(new DeviceRgb(255,255,255));
		
		header.addCell(
				 new Cell().add(new Paragraph("Facture").setPadding(15f))
				.setTextAlignment(TextAlignment.CENTER)
				.setVerticalAlignment(VerticalAlignment.MIDDLE)
				.setFontSize(16f)
				.setBorder(Border.NO_BORDER)
				);
		
		header.addCell(
				 new Cell().add(new Paragraph("Gestion de Location\nICE: 123456789\nEnsa AGADIR\nG.INFO\nAbdelwahed Ait Brik\nMohamed Nait Moussa\n Nour-Eddine Ait Elhadj").setPadding(15f))
				 .setTextAlignment(TextAlignment.LEFT)
				.setVerticalAlignment(VerticalAlignment.MIDDLE)
				.setFontSize(7f)
				.setBorderLeft(new SolidBorder(new DeviceRgb(224, 199, 242), 1.5f))
				);
		
		doc.add(header);
		
		float colWidth[] = {80, 300, 80, 100};
		
		Table clientInfo = new Table(colWidth);
		
		clientInfo.setMargins(60f, 0, 60f, 0);
		
		//title
		clientInfo.addCell(
				new Cell(0,4).add(new Paragraph("Informations: ").setBold())
					.setFontSize(9f)
					.setBorder(Border.NO_BORDER)
					.setBorderLeft(new SolidBorder(new DeviceRgb(224, 199, 242), 1.5f))
				);
		
		//first row
		clientInfo.addCell(
				new Cell().add(new Paragraph("Nom: ").setBold())
					.setFontSize(7f)
					.setBorder(Border.NO_BORDER)
					.setBorderLeft(new SolidBorder(new DeviceRgb(224, 199, 242), 1.5f))
				);
		
		clientInfo.addCell(
				new Cell().add(new Paragraph(nomClient))
					.setFontSize(7f)
					.setBorder(Border.NO_BORDER)
				);
		
		clientInfo.addCell(
				new Cell().add(new Paragraph("N° Facture:").setBold())
					.setFontSize(7f)
					.setBorder(Border.NO_BORDER)
					.setBorderLeft(new SolidBorder(new DeviceRgb(224, 199, 242), 1.5f))
				);
		
		clientInfo.addCell(
				new Cell().add(new Paragraph(Integer.toString(codeFacture)))
					.setFontSize(7f)
					.setBorder(Border.NO_BORDER)
				);
		
		//Second row
		clientInfo.addCell(
				new Cell().add(new Paragraph("Prenom:").setBold())
					.setFontSize(7f)
					.setBorder(Border.NO_BORDER)
					.setBorderLeft(new SolidBorder(new DeviceRgb(224, 199, 242), 1.5f))
				);
		
		clientInfo.addCell(
				new Cell().add(new Paragraph(prenomClient))
					.setFontSize(7f)
					.setBorder(Border.NO_BORDER)
				);
		
		clientInfo.addCell(
				new Cell().add(new Paragraph("Date Facture: ").setBold())
					.setFontSize(7f)
					.setBorder(Border.NO_BORDER)
					.setBorderLeft(new SolidBorder(new DeviceRgb(224, 199, 242), 1.5f))
				);
		
		clientInfo.addCell(
				new Cell().add(new Paragraph(dateFacture))
					.setFontSize(7f)
					.setBorder(Border.NO_BORDER)
				);
		
		//Third Row
		clientInfo.addCell(
				new Cell().add(new Paragraph("N° Client:").setBold())
					.setFontSize(7f)
					.setBorder(Border.NO_BORDER)
					.setBorderLeft(new SolidBorder(new DeviceRgb(224, 199, 242), 1.5f))
				);
		
		clientInfo.addCell(
				new Cell().add(new Paragraph(Integer.toString(codeClient)))
					.setFontSize(7f)
					.setBorder(Border.NO_BORDER)
				);
		clientInfo.addCell(
				new Cell().add(new Paragraph("N° Contrat: ").setBold())
					.setFontSize(7f)
					.setBorder(Border.NO_BORDER)
					.setBorderLeft(new SolidBorder(new DeviceRgb(224, 199, 242), 1.5f))
				);
		
		clientInfo.addCell(
				new Cell().add(new Paragraph(Integer.toString(codeContrat)))
					.setFontSize(7f)
					.setBorder(Border.NO_BORDER)
				);
		
		
		doc.add(clientInfo);
		
		float locationCol[] = {120, 120, 120, 120, 120};
		Table location = new Table(locationCol);
		
		location.addCell(
				new Cell().add(new Paragraph("Vehicule"))
					.setFontSize(9f)
					.setTextAlignment(TextAlignment.CENTER)
					.setBorder(Border.NO_BORDER)
					.setBackgroundColor(new DeviceRgb(224, 199, 242))
				);
		location.addCell(
				new Cell().add(new Paragraph("Date Contrat"))
					.setFontSize(9f)
					.setTextAlignment(TextAlignment.CENTER)
					.setBorder(Border.NO_BORDER)
					.setBackgroundColor(new DeviceRgb(224, 199, 242))
				);
		location.addCell(
				new Cell().add(new Paragraph("Date Echeance"))
					.setFontSize(9f)
					.setTextAlignment(TextAlignment.CENTER)
					.setBorder(Border.NO_BORDER)
					.setBackgroundColor(new DeviceRgb(224, 199, 242))
				);
		location.addCell(
				new Cell().add(new Paragraph("Prix Location (par jour)"))
					.setFontSize(9f)
					.setTextAlignment(TextAlignment.CENTER)
					.setBorder(Border.NO_BORDER)
					.setBackgroundColor(new DeviceRgb(224, 199, 242))
				);
		location.addCell(
				new Cell().add(new Paragraph("Montant"))
					.setFontSize(9f)
					.setTextAlignment(TextAlignment.CENTER)
					.setBorder(Border.NO_BORDER)
					.setBackgroundColor(new DeviceRgb(224, 199, 242))
				);
		
		//Second Row
		location.addCell(
				new Cell().add(new Paragraph(codeVehicule))
					.setFontSize(7f)
					.setTextAlignment(TextAlignment.CENTER)
					.setBorder(Border.NO_BORDER)
				);
		location.addCell(
				new Cell().add(new Paragraph(dateContrat))
					.setFontSize(7f)
					.setTextAlignment(TextAlignment.CENTER)
					.setBorder(Border.NO_BORDER)
				);
		location.addCell(
				new Cell().add(new Paragraph(dateEcheance))
					.setFontSize(7f)
					.setTextAlignment(TextAlignment.CENTER)
					.setBorder(Border.NO_BORDER)
				);
		location.addCell(
				new Cell().add(new Paragraph(Integer.toString(prixLocation)+"dh"))
					.setFontSize(7f)
					.setTextAlignment(TextAlignment.CENTER)
					.setBorder(Border.NO_BORDER)
				);
		location.addCell(
				new Cell().add(new Paragraph(Integer.toString(montant)+"dh"))
					.setFontSize(7f)
					.setTextAlignment(TextAlignment.CENTER)
					.setBorder(Border.NO_BORDER)
				);
		
		doc.add(location);
		
		
		
		doc.close();
		
		Desktop.getDesktop().open(new File(path));
	}

}
