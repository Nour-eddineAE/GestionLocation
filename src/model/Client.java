package model;

public class Client {
	private int codeClient;
	private String nomClient;
	private String prenomClient;
	private String addresseClient;
	private String numTelClient;
	private String image;
	private String permisScannee;
	
	public Client(int codeClient, String nomClient, String prenomClient, String addresseClient, String numTelClient,
			String image, String permisScannee) {
		super();
		this.codeClient = codeClient;
		this.nomClient = nomClient;
		this.prenomClient = prenomClient;
		this.addresseClient = addresseClient;
		this.numTelClient = numTelClient;
		this.image = image;
		this.permisScannee = permisScannee;
	}

	public int getCodeClient() {
		return codeClient;
	}

	public void setCodeClient(int codeClient) {
		this.codeClient = codeClient;
	}

	public String getNomClient() {
		return nomClient;
	}

	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	public String getPrenomClient() {
		return prenomClient;
	}

	public void setPrenomClient(String prenomClient) {
		this.prenomClient = prenomClient;
	}

	public String getAddresseClient() {
		return addresseClient;
	}

	public void setAddresseClient(String addresseClient) {
		this.addresseClient = addresseClient;
	}

	public String getNumTelClient() {
		return numTelClient;
	}

	public void setNumTelClient(String numTelClient) {
		this.numTelClient = numTelClient;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPermisScannee() {
		return permisScannee;
	}

	public void setPermisScannee(String permisScannee) {
		this.permisScannee = permisScannee;
	}

	public Client(String nomClient, String prenomClient, String addresseClient, String numTelClient, String image,
			String permisScannee) {
		super();
		this.nomClient = nomClient;
		this.prenomClient = prenomClient;
		this.addresseClient = addresseClient;
		this.numTelClient = numTelClient;
		this.image = image;
		this.permisScannee = permisScannee;
	}

}
