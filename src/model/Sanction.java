package model;

import java.util.ArrayList;

public class Sanction {
	
	private Client client;
	private int montant;
	
	private ArrayList<Contrat> cList;
	
	/**
	 * Default constructor
	 */
	public Sanction() {
	}
	
	public Sanction(Client c, int montant) {
		this.client = c;
		this.montant = montant;
	}
	
	//getters
	public Client getClient() {
		return this.client;
	}
	
	public int getMontant() {
		return this.montant;
	}
	
	public ArrayList<Contrat> getContratList(){
		return this.cList;
	}
	
	//setters
	public void setClient(Client c) {
		this.client = c;
	}
	public void setMontant(int montant) {
		this.montant = montant;
	}
	public void addContrat(Contrat contrat) {
		if(cList == null) {
			cList = new ArrayList<Contrat>();
		}
		cList.add(contrat);
	}
	
	public void setContratList(ArrayList<Contrat> cList) {
		this.cList = cList;
	}
}
