package model;

import java.sql.Date;

public class Contrat {
	private int codeContrat;
	private Reservation reservation;
	private Date dateContrat;
	private Date dateEcheance;

	public Contrat() {
	}
	
	
	//Getters & Setters
	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Date getDateContrat() {
		return dateContrat;
	}

	public void setDateContrat(Date dateContrat) {
		this.dateContrat = dateContrat;
	}

	public Date getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public int getCodeContrat() {
		return codeContrat;
	}

	public void setCodeContrat(int codeContrat) {
		this.codeContrat = codeContrat;
	}
	
	
	
}
