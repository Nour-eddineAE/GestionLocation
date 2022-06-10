package controller;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import dao.vehiculeDAO;
import interfaces.MainInterface;
import model.Vehicule;
import view.AddNewVehicule;
import view.AllVehiculeInfoPanel;
import view.ChangeExistingVehicle;
import view.VehiculePanel;

import java.security.MessageDigest;
import java.sql.Date;
import java.time.LocalDate;

public class VehiculeController {
	private static JTable vehiculeTable;
	private static MainInterface window;
	private static VehiculePanel vehiculePanel;
	private static MessageDigest md;
	 
	 
	 
	// METRHODE AUTOCOMPLETING
	public static void autoCompleting(String a) {
		ArrayList<Vehicule> vList = vehiculeDAO.findVehiculeAutoCompleting(a);//RETOURNE UNE ARRAYLIST
		vehiculePanel.getTableModel().loadVehicules(vList);//ATTACHE LA LISTE A LA TABLE DU PANEL VEHICULEPANEL
	}
	//AFFICHER TOUT LES ENREGISTREMENT
	public static void fetchAll() {
		ArrayList<Vehicule> vList = vehiculeDAO.fetchAll();
		vehiculePanel.getTableModel().loadVehicules(vList);
	}
	// METHODE AJOUTER UN NOUVEAU VEHICULE
	public static void addVehicule( ) {
		try {
			AddNewVehicule newVehicule = new AddNewVehicule(window);
			window.addToMainPanel(newVehicule,"newVehicule");
			window.showOnMainPanel("newVehicule");
			newVehicule.getDisponible().setSelected(true);
			newVehicule.getLbl_disp().setVisible(true);
			newVehicule.getLbl_indisp().setVisible(false);
			newVehicule.getYcomboBox().setSelectedIndex(0);//ON FORCE LE CHOIX D'UNE DATE POUR QU'ON EVITE LA SEVAUGARDE SANS CHOISIR UNE
			newVehicule.getMcomboBox().setSelectedIndex(0);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	//METHODE SAUVEAGARDER L'ENREGISTREMENT
	public static void saveNewVehicule(AddNewVehicule ANV,Vehicule v) {
		
		if(v.getCarburant().matches("[a-zA-Z]*") && v.getMatricule().matches("[a-zA-Z 0-9]*")&&v.getMarque().matches("[a-zA-Z 0-9]*")&&v.getType().matches("[a-zA-Z]*")) {
				if(vehiculeDAO.createVehicule(v)) {
					VehiculeController.fetchAll();
					JOptionPane.showMessageDialog(null,"Vehicule ajouté", "Ajouté avec succés", JOptionPane.INFORMATION_MESSAGE);
					window.showOnMainPanel("vehicule");//revenir au menu principal
			}else
				JOptionPane.showMessageDialog(null,"Ajout echoué", "Probléme d'ajout", JOptionPane.ERROR_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(null,"Seulement les alphabets sont permis pour les champs(type,carburant),"
					+ "les alphabets et les nombres  pour les champs(matricule,marque) ", "Incorrect input", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	//MRTHODE ANNULER L'ENREGISTREMENT/CHANGEMENT
	public static void cancel(MainInterface MI) {
		MI.showOnMainPanel("vehicule");	//revenir au menu precedent
		ArrayList<Vehicule> vList = vehiculeDAO.fetchAll();
		vehiculePanel.getTableModel().loadVehicules(vList);
	}
	//METHODE QUI AFFICHE TOUS LES INFORMATIONS D'UNE VEHICULE
	public static void DisplayAllVehiculeInfo(String matricule) {
		// TODO Auto-generated method stub
		AllVehiculeInfoPanel AVIP=new AllVehiculeInfoPanel(window);
		window.addToMainPanel(AVIP, "AllVInfo");
		window.showOnMainPanel("AllVInfo");
		Vehicule V=vehiculeDAO.findVehicule(vehiculeTable.getModel().getValueAt(vehiculeTable.getSelectedRow(),0).toString());
		AVIP.getLbl_matricuule().setText(V.getMatricule());
		AVIP.getLbl_Marque().setText(V.getMarque());
		AVIP.getLbl_type().setText(V.getType());
		AVIP.getLbl_carburant().setText(V.getCarburant());
		AVIP.getLbl_kilometrage().setText(V.getKilometrage()+" KM");
		AVIP.getLblDMC().setText(V.getDMC()+"");
		AVIP.getLbl_codeParking().setText(V.getNomPark());
		AVIP.getLbl_PLocation().setText(V.getPrixLocation()+" DH");
		if(V.getDisponible())
			AVIP.getLbl_disponibilite().setText("Oui");
		else
			AVIP.getLbl_disponibilite().setText("Non");
	}
	
	// METHODE QUI SUPPRIME L'ENREGISTREMENT SELECTIONNE
	public static void removeVehicule() {
		// TODO Auto-generated method stub
		int i = vehiculeTable.getSelectedRow();
		try {
			if (i!=-1) {
				int result = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiement supprimer ce vehicule?", "Confirmer la suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(result == JOptionPane.YES_OPTION) {
					String id =vehiculeTable.getModel().getValueAt(i, 0).toString();
					vehiculeDAO.removeVehicule(id);
					VehiculeController.fetchAll();
					JOptionPane.showMessageDialog(null,"Vehicule supprimé avec succés", "Suppression", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else
				JOptionPane.showMessageDialog(null,"Aucun vehicule n'est selectionné", "Echec de suppression", JOptionPane.ERROR_MESSAGE);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(), "Echec de suppression", JOptionPane.ERROR_MESSAGE);
		}
	}
	//METHODE QUI LANCE LE PANEL D EMODIFICATION
	public static void changeVehicle() {
		// TODO Auto-generated method stub
		int index=VehiculeController.vehiculeTable.getSelectedRow();
		if(index>=0) {
			Vehicule v=vehiculeDAO.findVehicule(vehiculeTable.getModel().getValueAt(index,0).toString());
			ChangeExistingVehicle CEV =new ChangeExistingVehicle(window);
			CEV.getImmatriculationVehicule().setText(v.getMatricule().toString());
			CEV.getMarqueVehicule().setText(v.getMarque().toString());
			CEV.getTypeVehicule().setText(v.getType().toString());
			CEV.getCarburant().setText(v.getCarburant().toString());
			CEV.getKilometrage().setText(v.getKilometrage()+"");
			LocalDate LD=(Date.valueOf(v.getDMC().toString())).toLocalDate();//ON EVITE EXTRAIRE L'ANNEE, LE MOIS ET LA DATE DIRECTEMENT AVEC LES MTHDS GET...(depricated)
			CEV.getYcomboBox().setSelectedItem( LD.getYear()+"");
			CEV.getMcomboBox().setSelectedItem( LD.getMonthValue()+"");
			CEV.getDcomboBox().setSelectedItem( LD.getDayOfMonth()+"");
			CEV.getParkComboBox().setSelectedItem(v.getNomPark());;
			CEV.getPrixLocation().setText(v.getPrixLocation()+"");
			CEV.getDisponible().setSelected(v.getDisponible());
			VehiculeController.changeDisponibility(CEV);
			window.addToMainPanel(CEV, "changeVehicle");
			window.showOnMainPanel("changeVehicle");
			ChangeExistingVehicle.setOldId(vehiculeTable.getModel().getValueAt(index,0).toString());
		}
		else
			JOptionPane.showMessageDialog(null,"Aucun vehicule n'est selectionné", "Echec de modification", JOptionPane.ERROR_MESSAGE);
	}
	
	//METHODE QUI ENREGISTRE LES MIDIFICATION SD'UN VEHICULE
	public static void saveChanges(Vehicule v,String oldId) {
		// TODO Auto-generated method stub
		
		if(vehiculeDAO.verifyVehicle(v.getMatricule())&&!(v.getMatricule().equals(oldId))) {//SI LA MATRICULE EXITE DEJA MAIS DIFFERENT DE L'ANCIEN MATRICULE
			JOptionPane.showMessageDialog(null,"the field 'matricule' exists already, change it then click save", "Id problem", JOptionPane.ERROR_MESSAGE);
		}else {
			if(v.getCarburant().matches("[a-zA-Z]*") && v.getMatricule().matches("[a-zA-Z 0-9]*")&&v.getMarque().matches("[a-zA-Z 0-9]*")&&v.getType().matches("[a-zA-Z]*")) {
				if(vehiculeDAO.ChangeVehicle(v,oldId)) {
					VehiculeController.fetchAll();
					JOptionPane.showMessageDialog(null,"Vehicule modifié ", "Modification avec succés", JOptionPane.INFORMATION_MESSAGE);
					window.showOnMainPanel("vehicule");//REVENIR AU MENU VEHICULE
				}else
					JOptionPane.showMessageDialog(null,"Modification echoué", "Probléme de modification", JOptionPane.ERROR_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"Seulement les alphabets sont permis pour les champs(type,carburant),"
						+ "les alphabets et les nombres  pour les champs(matricule,marque) ", "Incorrect input", JOptionPane.ERROR_MESSAGE);
			}
		}

	}
	//FONCTION QUI RETROURNE LES NOMS DES PARKINGS
		public static ArrayList<String> ParksNames(){
			return vehiculeDAO.nomPark();
		}
	
	
	
	
	
	
	
	
	
	
	
	
	// FONCTIONS POUR LE CONTROLE DES CHAMPS RENSEIGNES
	@SuppressWarnings("deprecation")
	public static boolean empty(AddNewVehicule ANV) {//VERIFIE SI LES CHAMPS DU NOUVEAU VEHICULE SONT VIDES
		//[ON NE PEUT UTILISER DIRECTEMENT UN OBJET VEHCULE COMME ARGUMENT DE CETTE FONCTION CAR LA MTHD IsEmpty() N'EST PAS DEFINIE POUR CERTAIN ATTRIBUTS]
		if(ANV.getImmatriculationVehicule().getText().isEmpty()||ANV.getMarqueVehicule().getText().isEmpty()||ANV.getTypeVehicule().getText().isEmpty()||
				ANV.getCarburant().getText().isEmpty()||ANV.getKilometrage().getText().isEmpty()||ANV.getPrixLocation().getText().isEmpty()||ANV.getDisponible().getText().isEmpty())
			return true;
		else return false;
	}
	@SuppressWarnings("deprecation")
	public static boolean empty(ChangeExistingVehicle CEV) {//VERIFIE SI  LES CHAMPS DU PANEL ChangeExistingVehicle SONT VIDES
		//[ON NE PEUT UTILISER DIRECTEMENT UN OBJET VEHCULE COMME ARGUMENT DE CETTE FONCTION CAR LA MTHD IsEmpty() N'EST PAS DEFINIE POUR CERTAIN ATTRIBUTS]
		if(CEV.getImmatriculationVehicule().getText().isEmpty()||CEV.getMarqueVehicule().getText().isEmpty()||CEV.getTypeVehicule().getText().isEmpty()||
				CEV.getCarburant().getText().isEmpty()||CEV.getKilometrage().getText().isEmpty()||CEV.getPrixLocation().getText().isEmpty()||CEV.getDisponible().getText().isEmpty())
			return true;
		else return false;
	}
	public static void changeDisponibility(AddNewVehicule ANV) {//CETTE METHODE AFFICHE OU NON LES LABEL DISPONIBLE/INDISPONIBLE SELON L'ETAT DE CHECKBOX
		if(ANV.getDisponible().isSelected()) {
			ANV.getLbl_disp().setVisible(true);
			ANV.getLbl_indisp().setVisible(false);
		}
		else {
			ANV.getLbl_disp().setVisible(false);
			ANV.getLbl_indisp().setVisible(true);
		}
	}
	public static void changeDisponibility(ChangeExistingVehicle CEV) {
		// TODO Auto-generated method stub
		if(CEV.getDisponible().isSelected()) {
			CEV.getLbl_disp().setVisible(true);
			CEV.getLbl_indisp().setVisible(false);
		}
		else {
			CEV.getLbl_disp().setVisible(false);
			CEV.getLbl_indisp().setVisible(true);
		}
	}
	
	public static void emptyAddFields(AddNewVehicule ANV) {//CETTE METHODE VIDE TOU LES CHAMPS RENSEIGNES PAR L'UTILISATEUR
		ANV.getImmatriculationVehicule().setText("");
		ANV.getMarqueVehicule().setText("");
		ANV.getTypeVehicule().setText("");
		ANV.getCarburant().setText("");
		ANV.getKilometrage().setText("");
		ANV.getParkComboBox().setSelectedIndex(0);
		ANV.getPrixLocation().setText("");
		ANV.getLbl_disp().setVisible(true);
		ANV.getLbl_indisp().setVisible(false);
		ANV.getDisponible().setSelected(true);
		ANV.getYcomboBox().setSelectedIndex(0);//ON FORCE LE CHOIX D'UNE DATE POUR QU'ON EVITE LA SEVAUGARDE SANS CHOISIR UNE
		ANV.getMcomboBox().setSelectedIndex(0);
	}
	public static void emptyVehicleFields(ChangeExistingVehicle CEV) {//CETTE METHODE VIDE TOU LES CHAMPS RENSEIGNES PAR L'UTILISATEUR
		CEV.getImmatriculationVehicule().setText("");
		CEV.getMarqueVehicule().setText("");
		CEV.getTypeVehicule().setText("");
		CEV.getCarburant().setText("");
		CEV.getKilometrage().setText("");
		CEV.getParkComboBox().setSelectedIndex(0);;
		CEV.getPrixLocation().setText("");
		CEV.getLbl_disp().setVisible(true);
		CEV.getLbl_indisp().setVisible(false);
		CEV.getDisponible().setSelected(true);
		CEV.getYcomboBox().setSelectedIndex(0);//ON FORCE LE CHOIX D'UNE DATE POUR QU'ON EVITE LA SEVAUGARDE SANS CHOISIR UNE
		CEV.getMcomboBox().setSelectedIndex(0);
		
	}
	// setters
	public static void setWindow(MainInterface window) {
		VehiculeController.window=window;
	}
	public static void setTable(JTable vehiculeTable){
		VehiculeController.vehiculeTable=vehiculeTable;
	}
	public static void setPanel(VehiculePanel vehiculePanel) {
		VehiculeController.vehiculePanel=vehiculePanel;
	}

	
	
	
	
	
	
	
}
