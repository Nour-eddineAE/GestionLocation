package view;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import controller.VehiculeController;
import interfaces.MainInterface;
import model.VehiculeTableModel;
import java.awt.Rectangle;

public class VehiculePanel extends JPanel{


		// ATTRIBUTS DE LA CLASSE VEHICULEPANEL
		private CardLayout cl;
		private static VehiculeTableModel vTable ;
		private JTable vehiculeTable;
		private static LinkedHashMap<String, JLabel> navItemList;
		
		//CONSTRUCTEUR DU PANEL
		public VehiculePanel(MainInterface mainInterface) {
			setBounds(new Rectangle(0, 0, 732, 547));
			this.cl = (CardLayout) mainInterface.getMainPanel().getLayout();
			this.setLayout(null);
			
		// ZONE D'AFFICHAGE
			JScrollPane VScrollPane = new JScrollPane();
			VScrollPane.setBounds(10, 60, 585, 482);
			this.add(VScrollPane);
			vTable = new VehiculeTableModel();
			vehiculeTable=new JTable(vTable);
			VScrollPane.setViewportView(vehiculeTable);
			vehiculeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			vehiculeTable.setSelectionBackground(viewSettings.SECONDARY);
			
		// ZONE DE RECHERCHE[UTILISE LA METHODE AUTOCOMPLETING A CHAQUE CLIQUE]
			JTextField chercherVehicule = new JTextField();
			chercherVehicule.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {// affiche les resultats qui commencent par le mot que l'utilisateur tappe
					VehiculeController.autoCompleting(chercherVehicule.getText());
				}
			});
			chercherVehicule.setBounds(10, 16, 585, 33);
			this.add(chercherVehicule);
			chercherVehicule.setColumns(10);
			
		//BOUTON AJOUTER UN NOUVEAU VEHICULE
			JButton addVehicule = new JButton("Ajouter ");
			addVehicule.setBackground(viewSettings.SECONDARY);
			addVehicule.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AddNewVehicule.setPanel(VehiculePanel.this);
					VehiculeController.setWindow(mainInterface);// pour pouvoir instancier le panelAddNewVehicule
					VehiculeController.addVehicule();
				}
			});
			addVehicule.setBounds(605, 222, 117, 50);
			this.add(addVehicule);
			
		//BOUTON SUPPRIMER UN VEHICULE ECXISTANT
			
			JButton removeVehicule = new JButton("Suppimer");
			removeVehicule.setBackground(viewSettings.SECONDARY);
			removeVehicule.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//VehiculeController.setPanel(UserPanel.this);
					VehiculeController.setTable(vehiculeTable);
					VehiculeController.removeVehicule();
				}
			});
			removeVehicule.setBounds(605, 299, 117, 50);
			this.add(removeVehicule);
			
		//BOUTON MODIFIER LES ATTRIBUTS D'UN VEHICULE EXISTANT
			
			JButton changeVehicule = new JButton("Modifier");
			changeVehicule.setBackground(viewSettings.SECONDARY);
			changeVehicule.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VehiculeController.setPanel(VehiculePanel.this);
					VehiculeController.setTable(vehiculeTable);
					VehiculeController.setWindow(mainInterface);
					VehiculeController.changeVehicle();
				}
			});
			changeVehicule.setBounds(605, 136, 117, 50);
			this.add(changeVehicule);
			this.setLayout(null);
			
		//BOUTON AFFICHER TOUT LES ATTRIBUTS D'UN ENREGISTREMENT
			JButton afficher = new JButton("Afficher Plus");
			afficher.setBackground(viewSettings.SECONDARY);
			afficher.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (vehiculeTable.getSelectedRow()<0)
						JOptionPane.showMessageDialog(null,"Selectionner un vehicule", "Aucun vehicule n'est selectionné", JOptionPane.WARNING_MESSAGE);
					else {
						VehiculeController.setWindow(mainInterface);
						VehiculeController.setTable(vehiculeTable);
						VehiculeController.DisplayAllVehiculeInfo(vehiculeTable.getModel().getValueAt(vehiculeTable.getSelectedRow(),0).toString());
					}
				}
			});
			afficher.setBounds(605, 378, 117, 50);
			this.add(afficher);
			
			
			
		//AFFCIHER TOUT LES ENREGISTREMENT DES QU'ON CLIQUE SUR VEHICULE DANS LA BARRE DE NAVIGATION
			navItemList.get("vehicule").addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					VehiculeController.setPanel(VehiculePanel.this);
					VehiculeController.fetchAll();
					mainInterface.showOnMainPanel("vehicule");
				}
			});
		}
		//SETTERS 
		public static void setNavList(LinkedHashMap<String, JLabel> a) {
			VehiculePanel.navItemList=a;
		}
		//GETTERS
		public VehiculeTableModel getTableModel() { 
			return this.vTable;
		}
	
}
