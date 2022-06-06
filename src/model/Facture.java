package model;

import java.sql.Date;

public class Facture {
	private int codeFacture;
	private Date dateFacture;
	private int montant;
	
	//private Client client;
	private Contrat contrat;
	
	/**
	 * Constructeur par defaut
	 */
	public Facture() {
	}
	
	//Getters & Setters
	public int getCodeFacture() {
		return codeFacture;
	}
	public void setCodeFacture(int codeFacture) {
		this.codeFacture = codeFacture;
	}
	public Date getDateFacture() {
		return dateFacture;
	}
	public void setDateFacture(Date dateFacture) {
		this.dateFacture = dateFacture;
	}
	public int getMontant() {
		return montant;
	}
	public void setMontant(int montantFacture) {
		this.montant = montantFacture;
	}
	public Contrat getContrat() {
		return contrat;
	}
	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}
	
	
}
