package view;

import java.awt.CardLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import controller.ContratController;
import interfaces.MainInterface;
import model.ReservationTableModel;

public class AddNewContract extends JPanel{
	private static ContratPanel contratPanel;
	private static ReservationTableModel rtm = new ReservationTableModel();
	private CardLayout cl;
	private static JTable reservationTable;
	JTextField chercherReservation;
	
	public AddNewContract(MainInterface mainInterface) {
		setBounds(new Rectangle(0, 0, 732, 547));
		this.cl = (CardLayout) mainInterface.getMainPanel().getLayout();
		this.setLayout(null);
		
// ZONE D'AFFICHAGE
		JScrollPane RScrollPane = new JScrollPane();
		RScrollPane.setBounds(0, 60, 595, 503);
		this.add(RScrollPane);
		reservationTable=new JTable(rtm);
		RScrollPane.setViewportView(reservationTable);
		reservationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
// ZONE DE RECHERCHE[UTILISE LA METHODE AUTOCOMPLETING A CHAQUE CLIQUE]
		chercherReservation = new JTextField();
		chercherReservation.addKeyListener(new KeyAdapter() {
			@Override
				public void keyReleased(KeyEvent e) {// affiche les resultats qui commencent par l'identifiant reservation  que l'utilisateur tappe
				ContratController.setPanel(AddNewContract.this);
				ContratController.autoCompleting1();
				}
		});
		chercherReservation.setBounds(10, 16, 585, 33);
		this.add(chercherReservation);
		chercherReservation.setColumns(10);
					
//BOUTON ANNULER L'AJOUT DU CONTRAT
		JButton CancelAdding = new JButton("Annuler");
		CancelAdding.setBackground(viewSettings.SECONDARY);
		CancelAdding.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainInterface.showOnMainPanel("contrat");
			}
		});
		CancelAdding.setBounds(605, 306, 117, 50);
		this.add(CancelAdding);
					
//BOUTON SAUVEGARDER LE NOUVEAU CONTRAT
					
		JButton boutonCreer = new JButton("Cr\u00E9er");
		boutonCreer.setBackground(viewSettings.SECONDARY);
		boutonCreer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContratController.setReservationTable(reservationTable);
				ContratController.createContract();
			}
		});
		boutonCreer.setBounds(605, 136, 117, 50);
		this.add(boutonCreer);
		this.setLayout(null);
		
	}
	
//GETTERS
	public static JTable getReservationTable() {
		return AddNewContract.reservationTable;
	}
	public static void setPanel(ContratPanel contratPanel) {
		AddNewContract.contratPanel=contratPanel;
	}
	public JTextField getChercherReservation() {
		return chercherReservation;
	}
	
	public static  ReservationTableModel getRTableModel(){
		return rtm;
	}
}
