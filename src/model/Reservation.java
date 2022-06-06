package model;

import java.sql.Date;

public class Reservation {
	private int codeReservation;
	private Date dateReservation, dateDepart, dateRetour;
	private boolean isValid, isCanceled;

	//les clefs externes
	private Client client;
	private Vehicule vehicule;

	// enumerateur pour les filtres des reservations
	public enum filtre{
		Tous,
		Valide,
		Non_valide,
		Annule
	}


	// constructeur par defaut
	public Reservation() {
	}

	//constructeur
	public Reservation(int codeReservation, Date dateReservation, Date dateDepart, Date dateRetour, boolean isValid,
			boolean isCanceled) {
		this.codeReservation = codeReservation;
		this.dateReservation = dateReservation;
		this.dateDepart = dateDepart;
		this.dateRetour = dateRetour;
		this.isValid = isValid;
		this.isCanceled = isCanceled;
	}

	// Getters et Setters
	public Date getDateReservation() {
		return dateReservation;
	}
	public void setDateReservation(Date dateReservation) {
		this.dateReservation = dateReservation;
	}

	public Date getDateDepart() {
		return dateDepart;
	}
	public void setDateDepart(Date dateDepart) {
		this.dateDepart = dateDepart;
	}

	public Date getDateRetour() {
		return dateRetour;
	}
	public void setDateRetour(Date dateRetour) {
		this.dateRetour = dateRetour;
	}

	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public boolean isCanceled() {
		return isCanceled;
	}
	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}



	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public int getCodeReservation() {
		return codeReservation;
	}

	public void setCodeReservation(int codeReservation) {
		this.codeReservation = codeReservation;
	}

	public Vehicule getVehicule() {
		return vehicule;
	}

	public void setVehicule(Vehicule vehicule) {
		this.vehicule = vehicule;
	}



}
