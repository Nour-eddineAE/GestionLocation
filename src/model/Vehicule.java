package model;

import java.sql.Date;

public class Vehicule {
	private String Matricule;
	private String Marque;
	private String Type;
	private String Carburant;
	private long Kilometrage;
	private Date DMC;//date de mise en circulation,
	private int CodePark;
	private double PrixLocation;
	private boolean disponible;
	
	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	// constructor
	public Vehicule(String a, String  b ,String  c ,String d, long  e,Date f,int g ,double h,boolean i) {
		this.Matricule=a;
		this.Marque=b;
		this.Type=c;
		this.Carburant=d;
		this.Kilometrage=e;
		this.DMC=f;
		this.CodePark=g;
		this.PrixLocation=h;
		this.disponible=i;
	}
	
	//Getters & Setters
	
	public String getMatricule() {
		return Matricule;
	}


	public String getMarque() {
		return Marque;
	}


	public String getType() {
		return Type;
	}


	public String getCarburant() {
		return Carburant;
	}


	public long getKilometrage() {
		return Kilometrage;
	}


	public Date getDMC() {
		return DMC;
	}


	public int getCodePark() {
		return CodePark;
	}


	public double getPrixLocation() {
		return PrixLocation;
	}

	public boolean getDisponible() {
		return this.disponible;
	}
	
	public String getCodeVehicule() {
		return codeVehicule;
	}

	public void setCodeVehicule(String codeVehicule) {
		this.codeVehicule = codeVehicule;
	}

	public int getPrixLocation() {
		return prixLocation;
	}

	public void setPrixLocation(int prixLocation) {
		this.prixLocation = prixLocation;
	}	
}
