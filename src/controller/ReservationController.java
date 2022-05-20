package controller;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import dao.ReservationDAO;
import interfaces.CreerReservation;
import interfaces.ModifierReservation;
import model.Reservation;
import model.Reservation.filtre;
import view.ReservationPanel;


/**
 * Controlleur qui gére l'interaction entre la BD et l'interface
 * @author Abd-AB
 * @version 1.0
 */

public class ReservationController {
	private ReservationPanel reserv_panel;
	private CreerReservation creer_reserv;
	private ModifierReservation mod_reserv;
	
	/**
	 * Constructeur par defaut
	 */
	public ReservationController() {
	}
	
	/**
	 * Constructeur pour associer le panel des reservation à son controlleur
	 * @param reserv_panel
	 */
	public ReservationController(ReservationPanel reserv_panel) {
		this.reserv_panel = reserv_panel;
	}
	
	/**
	 * Constructeur pour associer l'interface de creation des reservations aux controlleur
	 * @param reserv_panel
	 * @param creer_reserv
	 */
	public ReservationController(ReservationPanel reserv_panel, CreerReservation creer_reserv) {
		this.creer_reserv = creer_reserv;
		this.reserv_panel = reserv_panel;
	}
	
	/**
	 * Constructeur pour associer l'interface de modification de reservation aux controlleur
	 * @param reserv_panel
	 * @param mod_reserv
	 */
	public ReservationController(ReservationPanel reserv_panel, ModifierReservation mod_reserv) {
		this.mod_reserv = mod_reserv;
		this.reserv_panel = reserv_panel;
	}
	
	/**
	 * Methode pour actualiser le tableau des reservations
	 */
	public void ActualiserTableau() {
		filtre fil = (filtre) reserv_panel.getReserv_filtre().getSelectedItem();
		ArrayList<Reservation> list_reserv = ReservationDAO.fetchAll(fil);
		reserv_panel.getReserv_model().loadReservation(list_reserv);
	}
	
	/**
	 * Methode qui recupere les donnés a partir de l'interface CreerReservation et les verifie, puis il les enregistre dans la BD
	 */
	public void CreerReservation() {
		int indexClient = creer_reserv.getReserv_client_table().getSelectedRow();
		int indexVehicule = creer_reserv.getReserv_client_table().getSelectedRow();
		if(indexClient < 0) {
			creer_reserv.getWarning_lbl().setText("<html>Veuillez Selectionner un client.</html>");
			return;
		}
//		if(indexVehicule < 0) {
//			creer_reserv.getWarning_lbl().setText("<html>Veuillez Selectionner une Vehicule.</html>");
//			return;
//		}
		
		int codeClient = (int) creer_reserv.getReserv_client_table().getValueAt(indexClient, 0);
		//String codeVehicule = (String) creer_reserv.getReserv_vehi_table().getValueAt(indexVehicule, 0);
		String codeVehicule = "ABC";
		Date dateDep, dateRet;
		
		//Si la valeur de date est erroné
		try {
			dateDep = creer_reserv.getDateDepart();			
		} catch (IllegalArgumentException e1) {
			creer_reserv.getWarning_lbl().setText("Veillez choisir une date de depart.");
			return;
		}
		
		//Si la valeur de date est erroné
		try {
			dateRet = creer_reserv.getDateRetour();
		} catch (IllegalArgumentException e1) {
			creer_reserv.getWarning_lbl().setText("Veillez choisir une date de retour.");
			return;
		}
		
		
		if(checkDates(dateDep, dateRet)) {
			if(ReservationDAO.isReservationDateAvailable(codeVehicule, dateDep, dateRet, -1)) {
				//si vehicule disponible pendant cette date creer reservation
				ReservationDAO.createReservation(codeClient, codeVehicule, dateDep, dateRet);	
				JOptionPane.showConfirmDialog(null, "Operation Effectuée.", "Operation Effectuée", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				creer_reserv.getWarning_lbl().setText("");
				
				ActualiserTableau();
			}
			else
				creer_reserv.getWarning_lbl().setText("<html>La vehicule est déja reservé pendant cette interval choisi.</html>");
		} else {
			creer_reserv.getWarning_lbl().setText("<html>La date depart doit etre avant la date de retour</html>");
		}
	}
	
	
	/**
	 * Methode qui recupere les donnés a partir de l'interface ModifierReservation et les verifie, puis il les enregistre dans la BD
	 */
	public void ModifierReservation() {
		
		Date dateDep, dateRet;
		
		//Si la valeur de date est erroné
		try {
			dateDep = mod_reserv.getDateDepart();			
		} catch (IllegalArgumentException e1) {
			mod_reserv.getWarning_lbl().setText("Veillez choisir une date de depart.");
			return;
		}
		
		//Si la valeur de date est erroné
		try {
			dateRet = mod_reserv.getDateRetour();
		} catch (IllegalArgumentException e1) {
			mod_reserv.getWarning_lbl().setText("Veillez choisir une date de retour.");
			return;
		}
		
		String codeVehicule = mod_reserv.getCodeVehicule();
		int codeReserv = mod_reserv.getCodeReserv();
		boolean isValid = mod_reserv.isValid();
		boolean isCanceled = mod_reserv.isCanceled();
		
		if(checkDates(dateDep, dateRet)) {
			if(ReservationDAO.isReservationDateAvailable(codeVehicule, dateDep, dateRet, codeReserv)) {
				//si vehicule disponible pendant cette date creer reservation
				ReservationDAO.modifyReservation(codeReserv, dateDep, dateRet, isValid, isCanceled);
				JOptionPane.showConfirmDialog(null, "Operation Effectuée.", "Operation Effectuée", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				mod_reserv.getWarning_lbl().setText("");
				
				ActualiserTableau();
			}
			else
				mod_reserv.getWarning_lbl().setText("<html>La vehicule est déja reservé pendant cette interval choisi.</html>");
		} else {
			mod_reserv.getWarning_lbl().setText("<html>La date depart doit etre avant la date de retour</html>");
		}
		
	}
	/**
	 * Methode qui recuperere la reservation selectionnée dans le tableau des reservations
	 * et le supprime.
	 */
	public void SupprimerReservation() {
		int index = reserv_panel.getReserv_table().getSelectedRow();
		if(index < 0) {
			//Si l'utilisateur n'a pas choisi une reservation
			reserv_panel.getReserv_warning_lbl().setText("<html>Veuillez choisir une reservation à supprimer.</html>");
			return;
		}
		reserv_panel.getReserv_warning_lbl().setText("");
		
		int result = JOptionPane.showConfirmDialog(null, "Êtes vous sûr?", "Verification", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(result == JOptionPane.YES_OPTION) {
			//si l'utilisateur a choisi oui -> supprimer reservation de la bd
			ReservationDAO.deleteReservation(Integer.parseInt((String) reserv_panel.getReserv_table().getValueAt(index, 0)));
		}
		
		ActualiserTableau();
	}
	
	/**
	 * Methode qui recupére l'identifiant de la reservation a partir de barre de recherche, et rempli le tableau avec l'entree correspondant
	 */
	public void RechercherReservation() {
		String input = reserv_panel.getReserv_field().getText();
		
		//verifier si l'entree est seulement des entier et qu'il n'est pas vide
		if(input.isEmpty() || !input.matches("^[0-9]+$")) {
			reserv_panel.getReserv_warning_lbl().setText("<html>Veuillez entrer un code valid.</html>");
			return;
		}
		
		ArrayList<Reservation> list_reserv = ReservationDAO.findReservation(Integer.parseInt(input));
		reserv_panel.getReserv_warning_lbl().setText("");
		reserv_panel.getReserv_model().loadReservation(list_reserv);
	}
	
	/**
	 * Methode qui verifie si deux dates sont en ordre
	 * @param dateDep
	 * @param dateRet
	 * @return true si dateDep < dateRet, false sinon
	 */
	public boolean checkDates(Date dateDep, Date dateRet) {
		LocalDate d1 = dateDep.toLocalDate();
		LocalDate d2 = dateRet.toLocalDate();
		Duration d = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
		
		return !(d.toSeconds() <= 0);
	}
}
