package controller;

import java.util.ArrayList;

import dao.ReservationDAO;
import model.Reservation;
import model.Reservation.filtre;
import view.ReservationPanel;

public class tempReservationController {
	private ReservationPanel reserv_panel;
	
	public tempReservationController() {
	}
	
	public tempReservationController(ReservationPanel reserv_panel) {
		this.reserv_panel = reserv_panel;
	}
	
	public void ActualiserTableau() {
		filtre fil = (filtre) reserv_panel.getReserv_filtre().getSelectedItem();
		ArrayList<Reservation> list_reserv = ReservationDAO.fetchAll(fil);
		reserv_panel.getReserv_model().loadReservation(list_reserv);
	}
	
}
