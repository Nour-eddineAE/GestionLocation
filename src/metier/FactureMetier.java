package metier;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

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

import model.Facture;

public interface FactureMetier {
	public static void createPdf(Facture f){	
			
			String path = ".\\factures\\facture_"+f.getCodeFacture()+".pdf";
			PdfWriter writer = null;
			try {
				writer = new PdfWriter(path);
			} catch (FileNotFoundException e) {
				JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Creation PDF", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
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
					new Cell().add(new Paragraph(f.getContrat().getReservation().getClient().getNomClient()))
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
					new Cell().add(new Paragraph(Integer.toString(f.getCodeFacture())))
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
					new Cell().add(new Paragraph(f.getContrat().getReservation().getClient().getPrenomClient()))
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
					new Cell().add(new Paragraph(f.getDateFacture().toString()))
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
					new Cell().add(new Paragraph(Integer.toString(f.getContrat().getReservation().getClient().getCodeClient())))
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
					new Cell().add(new Paragraph(Integer.toString(f.getContrat().getCodeContrat())))
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
					//TODO change code vehicule into vehicule object
					new Cell().add(new Paragraph(f.getContrat().getReservation().getVehicule().getCodeVehicule()))
						.setFontSize(7f)
						.setTextAlignment(TextAlignment.CENTER)
						.setBorder(Border.NO_BORDER)
					);
			location.addCell(
					new Cell().add(new Paragraph(f.getContrat().getDateContrat().toString()))
						.setFontSize(7f)
						.setTextAlignment(TextAlignment.CENTER)
						.setBorder(Border.NO_BORDER)
					);
			location.addCell(
					new Cell().add(new Paragraph(f.getContrat().getDateEcheance().toString()))
						.setFontSize(7f)
						.setTextAlignment(TextAlignment.CENTER)
						.setBorder(Border.NO_BORDER)
					);
			location.addCell(
					new Cell().add(new Paragraph(Integer.toString(f.getContrat().getReservation().getVehicule().getPrixLocation())+"dh"))
						.setFontSize(7f)
						.setTextAlignment(TextAlignment.CENTER)
						.setBorder(Border.NO_BORDER)
					);
			location.addCell(
					new Cell().add(new Paragraph(Integer.toString(f.getMontant())+"dh"))
						.setFontSize(7f)
						.setTextAlignment(TextAlignment.CENTER)
						.setBorder(Border.NO_BORDER)
					);
			
			doc.add(location);
			
			
			
			doc.close();
			
			try {
				Desktop.getDesktop().open(new File(path));
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Ouverture PDF", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
}
