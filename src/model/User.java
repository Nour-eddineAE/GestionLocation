package model;

public class User {
	private int matricule;
	private String prenom;
	private String nom;
	private String telephone;
	private String adresse;
	private boolean statut;
	private boolean isAdmin;
	private String username;
	private String password ;
	
	
	public User(int a ,String b , String c ,String d ,String e , boolean f, boolean g, String h, String i ) {
		this.matricule=a;
		this.prenom=b;
		this.nom=c;
		this.telephone=d;
		this.adresse=e;
		this.statut=f;
		this.isAdmin=g;
		this.username=h;
		this.password=i;
		
	}
	
	
	
	
	//GETTERS
	public int getMatricule() {
		return matricule;
	}
	public String getPrenom() {
		return prenom;
	}
	public String getNom() {
		return nom;
	}
	public String getTelephone() {
		return telephone;
	}
	public String getAdresse() {
		return adresse;
	}
	public boolean isStatut() {
		return statut;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}
