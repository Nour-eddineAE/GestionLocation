package model;

import java.sql.Date;

public class Contrat {
	private int codeContrat;
	private Reservation reservation;
	private Date dateContrat;
	private Date dateEcheance;
	

	private Date dateRetActuel;
	
	private int montantSanction;

	public Contrat() {
	}
	public Contrat(int codeContrat, Date dateContrat, Date dateEcheance, Date dateRetour) {
		this.codeContrat = codeContrat;
		this.dateContrat = dateContrat;
		this.dateEcheance = dateEcheance;
		this.dateRetActuel=dateRetour;
	}
	public Contrat( Date dateContrat, Date dateEcheance,Reservation reservation) {
		this.dateContrat = dateContrat;
		this.dateEcheance = dateEcheance;
		this.reservation=reservation;
	}
	public int getCodeContrat() {
		return codeContrat;
	}
	public Reservation getReservation() {
		return reservation;
	}
	public Date getDateContrat() {
		return dateContrat;
	}
	public Date getDateEcheance() {
		return dateEcheance;
	}
public void setCodeContrat(int codeContrat) {
		this.codeContrat = codeContrat;
	}
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	public void setDateContrat(Date dateContrat) {
		this.dateContrat = dateContrat;
	}
	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}
	public void setMontantSanction(int montantSanction) {
		this.montantSanction = montantSanction;
	}
	//	public Date getDateRetActuel() {
//		return dateRetActuel;
//	}
	public int getMontantSanction() {
		return montantSanction;
	}
	public Date getDateRetActuel() {
		return dateRetActuel;
	}
	public void setDateRetActuel(Date dateRetActuel) {
		this.dateRetActuel = dateRetActuel;
	}
		
	
	
}
