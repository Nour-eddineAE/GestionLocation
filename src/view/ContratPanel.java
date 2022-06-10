package view;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import controller.ContratController;
import interfaces.MainInterface;
import model.ContractTableModel;

public class ContratPanel extends JPanel {
// ATTRIBUTS DE LA CLASSE ContratEPANEL
	private CardLayout cl;
	private static ContractTableModel cTable ;
	private JTable contratTable;
	private static LinkedHashMap<String, JLabel> navItemList;
			
//CONSTRUCTEUR DU PANEL
	public ContratPanel(MainInterface mainInterface) {
		setBounds(new Rectangle(0, 0, 732, 547));
		this.cl = (CardLayout) mainInterface.getMainPanel().getLayout();
		this.setLayout(null);
				
// ZONE D'AFFICHAGE
		JScrollPane CScrollPane = new JScrollPane();
		CScrollPane.setBounds(0, 60, 595, 503);
		this.add(CScrollPane);
		cTable = new ContractTableModel();
		contratTable=new JTable(cTable);
		CScrollPane.setViewportView(contratTable);
		contratTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
// ZONE DE RECHERCHE[UTILISE LA METHODE AUTOCOMPLETING A CHAQUE CLIQUE]
		JTextField chercherContrat = new JTextField();
		chercherContrat.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {// affiche les resultats qui commencent par le mot que l'utilisateur tappe
				ContratController.autoCompleting(Integer.parseInt(chercherContrat.getText()));
			}
		});
		chercherContrat.setBounds(0, 16, 595, 33);
		this.add(chercherContrat);
		chercherContrat.setColumns(10);
				
//BOUTON AJOUTER UN NOUVEAU CONTRAT
		JButton addContrat = new JButton("Ajouter ");
		addContrat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddNewContract.setPanel(ContratPanel.this);
				ContratController.setWindow(mainInterface);// pour pouvoir instancier le panelAddNewContrat
				ContratController.addContrat();
			}
		});
		addContrat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addContrat.setBounds(605, 222, 117, 50);
		this.add(addContrat);
				
//BOUTON SUPPRIMER UN CONTRAT ECXISTANT
				
		JButton removeContrat = new JButton("Suppimer");
		removeContrat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContratController.setPanel(ContratPanel.this);
				ContratController.setTable(contratTable);
				ContratController.removeContrat();
			}
		});
		removeContrat.setBounds(605, 299, 117, 50);
		this.add(removeContrat);
				
//BOUTON MODIFIER LES ATTRIBUTS D'UN Contrat EXISTANT
				
		JButton changeContrat = new JButton("Modifier");
		changeContrat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContratController.setPanel(ContratPanel.this);
				ContratController.setTable(contratTable);
				ContratController.setWindow(mainInterface);
				ContratController.changeContrat();
			}
		});
		changeContrat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		changeContrat.setBounds(605, 136, 117, 50);
		this.add(changeContrat);
		this.setLayout(null);
		
//BOUTON AFFICHER TOUT LES ATTRIBUTS D'UN ENREGISTREMENT
		JButton afficher = new JButton("Actualiser");
		afficher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContratController.fetchAll();
			}
		});
		afficher.setFont(new Font("Tahoma", Font.PLAIN, 14));
		afficher.setBounds(605, 378, 117, 50);
		this.add(afficher);
				
				
				
//AFFCIHER TOUT LES ENREGISTREMENT DES QU'ON CLIQUE SUR CONTRAT DANS LA BARRE DE NAVIGATION
		navItemList.get("contrat").addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ContratController.setPanel(ContratPanel.this);
				ContratController.fetchAll();
				mainInterface.showOnMainPanel("contrat");
			}
		});
	}
//SETTERS 
	public static void setNavList(LinkedHashMap<String, JLabel> a) {
		ContratPanel.navItemList=a;
	}
	public static ContractTableModel getTableModel() { 
		return cTable;
	}

}
