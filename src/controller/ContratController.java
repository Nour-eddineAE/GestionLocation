package controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import dao.ContratDAO;
import dao.ReservationDAO;
import interfaces.MainInterface;
import model.Contrat;
import model.Reservation;
import model.Reservation.filtre;
import view.AddNewContract;
import view.ChangeExistingContrat;
import view.ContratPanel;

public class ContratController {
	private static JTable contratTable;
	private static JTable reservationTable;
	private static MainInterface window;
	private static ContratPanel contratPanel;
	private static AddNewContract addNewContract;
// METHODE AUTOCOMPLETING version contrats
	public static void autoCompleting(int codeContrat) {
		ArrayList<Contrat> cList = ContratDAO.findContratAutoCompleting(codeContrat);//RETOURNE UNE ARRAYLIST
		contratPanel.getTableModel().loadContracts(cList);//ATTACHE LA LISTE A LA TABLE DU PANEL ContratPANEL
	}
// METHODE AUTOCOMPLETING version reservations
	public static void autoCompleting1() {
		if(!ContratController.addNewContract.getChercherReservation().getText().equals("")) {
			try {
				ArrayList<Reservation> reserv = ReservationDAO.findUserAutoCompleting(Integer.parseInt(ContratController.addNewContract.getChercherReservation().getText()));
				AddNewContract.getRTableModel().loadReservations(reserv);
			}catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(null,"veuillez entrer un nombre", "Input error", JOptionPane.ERROR_MESSAGE);
			}
		}else {
			ContratController.displayReservation();
		}
	}
//METHODE QUI AFFICHE LA LISTE DES RESERVATION
	public static void displayReservation() {
		 ArrayList<Reservation> reserv = ReservationDAO.fetchAll(filtre.Non_valide) ;
		 AddNewContract.getRTableModel().loadReservations(reserv);
	}
		
//METHODE QUI AFFICHE LA LISTE DES CONTRATS 
	//AFFICHER TOUT LES ENREGISTREMENT
	public static void fetchAll() {
		ArrayList<Contrat> cList = ContratDAO.fetchAll();
		ContratPanel.getTableModel().loadContracts(cList);
	}
//METHODE QUI CHANGE LA DATE D'ECHEANCE D'UN CONTRAT
	public static void changeContrat() {
		int index=ContratController.contratTable.getSelectedRow();
		if(index>=0) {
			Contrat c=ContratDAO.findContract(Integer.parseInt(contratTable.getModel().getValueAt(index,0).toString()));
			ChangeExistingContrat CEC =new ChangeExistingContrat(window);
			Date date =c.getDateEcheance();
			LocalDate LD=date.toLocalDate();//ON EVITE EXTRAIRE L'ANNEE, LE MOIS ET LA DATE DIRECTEMENT AVEC LES MTHDS GET...(depricated)
			CEC.getYcomboBox().setSelectedItem( LD.getYear()+"");
			CEC.getMcomboBox().setSelectedItem( LD.getMonthValue()+"");
			CEC.getDcomboBox().setSelectedItem( LD.getDayOfMonth()+"");
			window.addToMainPanel(CEC, "changeContrat");
			window.showOnMainPanel("changeContrat");
			ChangeExistingContrat.setOldId(Integer.parseInt(contratTable.getModel().getValueAt(index,0).toString()));
		}
		else
			JOptionPane.showMessageDialog(null,"Aucun contrat n'est selectionné", "Echec de modification", JOptionPane.ERROR_MESSAGE);
	}
// METHODE QUI ENREGISTRE LE CHANGEMENT
	public static void saveChanges(Date date ,int oldId) {
		if(ContratDAO.ChangeContrat(date,oldId)) {
			ContratController.fetchAll();
			JOptionPane.showMessageDialog(null,"Contrat modifié ", "Modification avec succés", JOptionPane.INFORMATION_MESSAGE);
			window.showOnMainPanel("contrat");//REVENIR AU MENU	CONTRAT
		}else
			JOptionPane.showMessageDialog(null,"Modification echoué", "Probléme de modification", JOptionPane.ERROR_MESSAGE);
	}
// METHODE QUI ANNULE LE CHANGEMENT
	public static void cancel(MainInterface mainInterface) {
			mainInterface.showOnMainPanel("contrat");
			ContratController.fetchAll();	
	}
//METHODE QUI AFFICHE LES RESERVATION DONT L'UTILISATEUR DOIT CHOISR UNE ET LUI AFFECTER UN CONTRAT 	
	public static void addContrat() {
		AddNewContract ANC=new AddNewContract(window);
		window.addToMainPanel(ANC,"newContrat");
		window.showOnMainPanel("newContrat");
		ContratController.displayReservation();
		}
//METHODE QUI CREE UN CONTRAT SELON LA RESERVATION CHOISIE
	public static void createContract() {
		int index=reservationTable.getSelectedRow();
		if(index>=0) {
			int i=Integer.parseInt(reservationTable.getValueAt(index,0).toString());
			ArrayList<Reservation> RV=ReservationDAO.findReservation(i);
			Reservation reserv=RV.get(0);
			Contrat contrat=new Contrat(Date.valueOf(java.time.LocalDate.now().toString()),reserv.getDateRetour(),reserv);
			ContratDAO.createContrat(contrat);
			ReservationDAO.setReservationValid(i);//VALIDER LA RESERVATION
			ContratController.fetchAll();
			ContratController.displayReservation();
			JOptionPane.showMessageDialog(null,"Contrat creé avec succés", "Creation avec succés", JOptionPane.INFORMATION_MESSAGE);
			window.showOnMainPanel("contrat");
		}else {
			JOptionPane.showMessageDialog(null,"Aucune reservation n'est selectionnée", "Ajout echoué", JOptionPane.ERROR_MESSAGE);
		}	
	}
//SUPPRIMER UN ENREGISTREMENT
	public static void removeContrat() {
		int i = contratTable.getSelectedRow();
		try {
			if (i!=-1) {
				int result = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiement supprimer ce contrat?", "Confirmer la suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(result == JOptionPane.YES_OPTION) {
					String id =contratTable.getModel().getValueAt(i, 0).toString();
					ContratDAO.removeContrat(id);
					ContratController.fetchAll();
					JOptionPane.showMessageDialog(null,"Contrat supprimé avec succés", "Suppression", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else
				JOptionPane.showMessageDialog(null,"Aucun contrat n'est selectionné", "Echec de suppression", JOptionPane.ERROR_MESSAGE);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(), "Echec de suppression", JOptionPane.ERROR_MESSAGE);
		}
	}
		
	public static void emptyContratFields(ChangeExistingContrat CEC) {
		CEC.getYcomboBox().setSelectedIndex(0);
		CEC.getMcomboBox().setSelectedIndex(0);
		CEC.getDcomboBox().setSelectedIndex(0);
	}
	
// setters
	public static void setWindow(MainInterface window) {
		ContratController.window=window;
	}
	public static void setTable(JTable contratTable){
		ContratController.contratTable=contratTable;
	}
	public static void setPanel(ContratPanel ContratPanel) {
		ContratController.contratPanel=ContratPanel;
	}
	public static void setReservationTable(JTable RT) {
		ContratController.reservationTable=RT;
	}
	public static void setPanel(AddNewContract addNewContract) {
		ContratController.addNewContract=addNewContract;	
	}
	
		
}
