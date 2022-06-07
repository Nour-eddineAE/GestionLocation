package controller;

import java.awt.CardLayout;
import java.util.ArrayList;

import dao.SanctionDAO;
import interfaces.MainInterface;
import metier.SanctionMetier;
import model.Client;
import model.Contrat;
import model.Sanction;
import view.SanctionInfoPanel;
import view.SanctionPanel;

public class SanctionController {
	
	private SanctionPanel sPanel;
	private SanctionInfoPanel sInfoPanel;
	private MainInterface mInterface;
	private CardLayout cl;
	
	/**
	 * Default Constructor
	 */
	public SanctionController() {
	}
	
	/**
	 * Contstructeur qui associe le panel des sanctions au controlleur
	 * @param sPanel
	 */
	public SanctionController(SanctionPanel sPanel, SanctionInfoPanel sInfoPanel, MainInterface mInterface) {
		this.sPanel = sPanel;
		this.sInfoPanel = sInfoPanel;
		this.mInterface = mInterface;
		
		this.cl = (CardLayout) mInterface.getMainPanel().getLayout();
		
		this.sPanel.setSanctionController(this);
		this.sInfoPanel.setSanctionController(this);
	}
	
	/**
	 * Methode qui actualise le tableau des sanctions
	 */
	public void Actualiser() {
		ArrayList<Sanction> sList = SanctionDAO.fetchAll();
		sPanel.getSanctionTableModel().loadSanctions(sList);
	}
	
	/**
	 * methode qui affiche plus d'informations sur les sanctions d'un client
	 */
	public void moreInfo() {
		int index = sPanel.getSanctionTable().getSelectedRow();
		if(index < 0) {
			sPanel.getWarningLabel().setText("<html>Veuiller selectionner un client pour afficher plus d'informations.</html>");
			return;
		}
		
		String codeClient = (String) sPanel.getSanctionTable().getValueAt(index, 0);
		String nom = (String) sPanel.getSanctionTable().getValueAt(index, 1);
		String prenom = (String) sPanel.getSanctionTable().getValueAt(index, 2);
		
		sInfoPanel.getClient_nom_lbl().setText(nom);
		sInfoPanel.getClient_prenom_lbl().setText(prenom);
		sInfoPanel.getClient_nbr_lbl().setText(codeClient);
		
		ArrayList<Contrat> cList = SanctionDAO.getContracts(Integer.parseInt(codeClient));

		cl.show(mInterface.getMainPanel(), "sanctionInfo");
		
		sInfoPanel.loadContrats(cList);
	}
	
	public void reglerSanction() {
		int index = sPanel.getSanctionTable().getSelectedRow();
		if(index < 0) {
			sPanel.getWarningLabel().setText("<html>Veuiller selectionner un client pour regler ses sanctions.</html>");
			return;
		}
		
		int codeClient = Integer.parseInt((String) sPanel.getSanctionTable().getValueAt(index, 0));
		
		//Creer PDF
		String nom = (String) sPanel.getSanctionTable().getValueAt(index, 1);
		String prenom = (String) sPanel.getSanctionTable().getValueAt(index, 2);
		
		Client c = new Client();
		c.setNomClient(nom);
		c.setPrenomClient(prenom);
		c.setCodeClient(codeClient);
		
		Sanction s = new Sanction();
		s.setClient(c);
		s.setContratList(SanctionDAO.getContracts(codeClient));
		s.setMontant(Integer.parseInt((String) sPanel.getSanctionTable().getValueAt(index, 3)));
		
		SanctionMetier.createPdf(s);
		
		SanctionDAO.reglerSanction(codeClient);
		Actualiser();
	}
	
	/**
	 * Methode pour retourner a l'interface des sanctions
	 */
	public void back() {
		cl.show(mInterface.getMainPanel(), "sanction");
		Actualiser();
	}
}
