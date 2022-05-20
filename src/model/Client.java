package model;

public class Client {
	private String nom, prenom;
	private int codeClient;
	
	public Client() {
	}

	public Client(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}

	
	
	//Getters et Setters
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getCodeClient() {
		return codeClient;
	}

	public void setCodeClient(int codeClient) {
		this.codeClient = codeClient;
	}
	
	
	
}
