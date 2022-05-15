package interfaces;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.ClientController;
import controller.FactureController;
import controller.ReservationController;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.CardLayout;
import java.util.LinkedHashMap;
import java.awt.Button;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import controller.ReservationController.filtre;

public class MainInterface {

	private JFrame frame;
	private Color mainColor;
	private Color secondaryColor;
	private Color highlight;
	
	private int navFontSize;
	private CardLayout cl;
	private JPanel mainPanel;
	private String currentPane;
	private LinkedHashMap<String, JLabel> navItemList;
	// client panel field
	private JTable clienttable;
	private JTextField clienttextField;
	
	// reservation panel
	private JTable reserv_table;
	private JTextField reserv_field;
	private JTable facture_table;
	private JTextField facture_field;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainInterface window = new MainInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainInterface() {
		initialize();
//		ClientController.fetchAll(clienttable);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setEnabled(false);
		frame.setResizable(false);
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		mainColor = new Color(75, 0, 130);
		secondaryColor = new Color(224, 199, 242);
		highlight = new Color(202, 168, 227);
		navFontSize = 12;
		
		JPanel titleBar = new JPanel();
		titleBar.setBackground(mainColor);
		titleBar.setBounds(0, 0, 986, 102);
		frame.getContentPane().add(titleBar);
		titleBar.setLayout(null);
		
		JLabel logoPlaceHolder = new JLabel("LOGO here");
		logoPlaceHolder.setHorizontalAlignment(SwingConstants.CENTER);
		logoPlaceHolder.setForeground(new Color(255, 255, 255));
		logoPlaceHolder.setFont(new Font("Tahoma", Font.BOLD, 24));
		logoPlaceHolder.setBounds(333, 17, 315, 75);
		titleBar.add(logoPlaceHolder);
		
		JPanel sideBar = new JPanel();
		sideBar.setBounds(0, 100, 234, 563);
		sideBar.setBackground(secondaryColor);
		frame.getContentPane().add(sideBar);
		sideBar.setLayout(null);
		
		JPanel navigation = new JPanel();
		navigation.setBounds(0, 80, 234, 388);
		sideBar.add(navigation);
		navigation.setBackground(secondaryColor);
		navigation.setLayout(new GridLayout(8, 1, 0, 0));
		
		navItemList = new LinkedHashMap<String,JLabel>();
		navItemList.put("client", new JLabel("Gestion de clients"));
		navItemList.put("reserv", new JLabel("Gestion des reservations"));
		navItemList.put("contrat", new JLabel("Gestion des contrats"));
		navItemList.put("facture", new JLabel("Gestion des factures"));
		navItemList.put("sanction", new JLabel("Gestion des sanctions"));
		navItemList.put("vehicule", new JLabel("Gestion des vehicules"));
		navItemList.put("parking", new JLabel("Gestion des parkings"));
		navItemList.put("user", new JLabel("Gestion des utilisateurs"));
		
		for(String item : navItemList.keySet()) {
			JLabel lab = navItemList.get(item);
			setupNavItem(lab, item);
			navigation.add(lab);
		}
		
		mainPanel = new JPanel();
		mainPanel.setBounds(244, 106, 732, 547);
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(new CardLayout(0, 0));
		cl = (CardLayout) mainPanel.getLayout();
		
		JPanel client = new JPanel();
		mainPanel.add(client, "client");
		
		JPanel parking = new JPanel();
		mainPanel.add(parking, "parking");
		
		JLabel lblNewLabel_1 = new JLabel("Parking");
		parking.add(lblNewLabel_1);
		
		//Panel des RESERVATIONS ---------------------------------------------------------------------
		
		createReservationPanel();
		
		//END PANEL RESERVATIONS ---------------------------------------------------------------------
		JPanel contrats = new JPanel();
		mainPanel.add(contrats, "contrat");
		
		JLabel lblContrats = new JLabel("contrats");
		contrats.add(lblContrats);
		
		//Panel des factures -------------------------------------------------------------------------
		
		createFacturePanel();
		
		//END Panel des factures ---------------------------------------------------------------------
		
		JPanel sanctions = new JPanel();
		mainPanel.add(sanctions, "sanction");
		
		JLabel sanctions_1 = new JLabel("sanctions");
		sanctions.add(sanctions_1);
		
		JPanel vehicules = new JPanel();
		mainPanel.add(vehicules, "vehicule");
		
		JLabel lblVehicules = new JLabel("vehicules");
		vehicules.add(lblVehicules);
		
		JPanel utilisateurs = new JPanel();
		mainPanel.add(utilisateurs, "user");
		
		JLabel lblUtilisateurs = new JLabel("utilisateurs");
		utilisateurs.add(lblUtilisateurs);
		client.setLayout(null);
		
		// Client Panel generation 
		//*********************************************************************************************************************
		JScrollPane clientscrollPane = new JScrollPane();
		clientscrollPane.setBounds(10, 58, 574, 478);
		client.add(clientscrollPane);
		
		clienttable = new JTable();
		clienttable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Object[] clientcolumns = {"id", "nom", "prenom", "numTel"};
		DefaultTableModel clientmodel = new DefaultTableModel();
		clientmodel.setColumnCount(4);
		clientmodel.setColumnIdentifiers(clientcolumns);
		clienttable.setModel(clientmodel);
		clientscrollPane.setViewportView(clienttable);
		
		JButton clientbtnNewButton = new JButton("Rechercher");
		clientbtnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string = clienttextField.getText();
				ClientController.findClient(string, clienttable);
				clienttextField.setText("");
			}
		});
		clientbtnNewButton.setBounds(594, 11, 128, 36);
		client.add(clientbtnNewButton);
		
		JButton clientbtnNewButton_1 = new JButton("Nouveau Client");
		clientbtnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreerNouveauClient nouveauClient = new CreerNouveauClient();
				ClientController.fetchAll(clienttable);
			}
		});
		clientbtnNewButton_1.setBounds(594, 76, 128, 36);
		client.add(clientbtnNewButton_1);
		
		clienttextField = new JTextField();
		clienttextField.setBounds(10, 11, 574, 36);
		client.add(clienttextField);
		clienttextField.setColumns(10);
		
		JButton clientbtnNewButton_2 = new JButton("Modifier");
		clientbtnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = clienttable.getSelectedRow();
				ModifierClient.setId(clienttable.getModel().getValueAt(index, 0).toString());
				if (index >= 0) {
				ModifierClient window = new ModifierClient();
				window.setTextField(clienttable.getModel().getValueAt(index, 1).toString());
				window.setTextField_1(clienttable.getModel().getValueAt(index, 2).toString());
				window.setTextField_2(clienttable.getModel().getValueAt(index, 3).toString());
				}
			}
		});
		clientbtnNewButton_2.setBounds(594, 170, 128, 36);
		client.add(clientbtnNewButton_2);
		
		JButton clientbtnNewButton_3 = new JButton("Actualiser");
		clientbtnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientController.fetchAll(clienttable);
			}
		});
		clientbtnNewButton_3.setBounds(594, 264, 128, 36);
		client.add(clientbtnNewButton_3);
		
		JButton clientbtnNewButton_4 = new JButton("Supprimer");
		clientbtnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = clienttable.getSelectedRow();
				ClientController.deleteClient(clienttable.getModel().getValueAt(index, 0).toString());
				ClientController.fetchAll(clienttable);
			}
		});
		clientbtnNewButton_4.setBounds(594, 217, 128, 36);
		client.add(clientbtnNewButton_4);
		
		JButton clientbtnNewButton_5 = new JButton("Afficher");
		clientbtnNewButton_5.setBounds(594, 123, 128, 36);
		client.add(clientbtnNewButton_5);
		//*********************************************************************************************************************
		
		cl.show(mainPanel, "facture");
		
	}
	
	private void setupNavItem(JLabel lab, String name) {
		lab.setOpaque(true);
		lab.setHorizontalAlignment(SwingConstants.CENTER);
		lab.setFont(new Font("Tahoma", Font.BOLD, navFontSize));
		lab.setBackground(secondaryColor);
		lab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				//on hover change color 
				lab.setBackground(highlight);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				//if mouse exit && not the curret pane change color
				
				//[ NOTE ] add condition to check if it's the current pane!
				if(currentPane != name)
					lab.setBackground(secondaryColor);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				cl.show(mainPanel, name);
				currentPane = name;
				for(String item: navItemList.keySet()) {
					if(item != name) {
						JLabel tmp = navItemList.get(item);
						tmp.setBackground(secondaryColor);
					}
				}
			}
		});
	}
	
	private void createReservationPanel() {
		
		JPanel reservations = new JPanel();
		mainPanel.add(reservations, "reserv");
		reservations.setLayout(null);
		
		JLabel reserv_warning_lbl = new JLabel("");
		reserv_warning_lbl.setForeground(Color.RED);
		reserv_warning_lbl.setBounds(517, 57, 185, 88);
		reservations.add(reserv_warning_lbl);
		
		JScrollPane reserv_scroll = new JScrollPane();
		reserv_scroll.setBounds(23, 57, 484, 462);
		reservations.add(reserv_scroll);
		
		reserv_table = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column){  
		          return false;  
		    };
		};
		
		reserv_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reserv_scroll.setViewportView(reserv_table);
		ReservationController.fetchAll(reserv_table, filtre.Tous);
		
		JComboBox reserv_filtre = new JComboBox();
		reserv_filtre.setModel(new DefaultComboBoxModel(filtre.values()));
		reserv_filtre.setMaximumRowCount(4);
		reserv_filtre.setBounds(517, 432, 185, 21);
		reservations.add(reserv_filtre);
		
		JButton reserv_actualiser_btn = new JButton("Actualiser");
		reserv_actualiser_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReservationController.fetchAll(reserv_table, (filtre)reserv_filtre.getSelectedItem());
				
				//Reset warning label on succesful reload
				reserv_warning_lbl.setText("");
			}
		});
		reserv_actualiser_btn.setBounds(517, 463, 185, 56);
		reservations.add(reserv_actualiser_btn);
		
		JLabel filtre_lbl = new JLabel("Filtre :");
		filtre_lbl.setBounds(517, 401, 185, 21);
		reservations.add(filtre_lbl);
		
		JButton newReserv_btn = new JButton("Nouveau reservation");
		newReserv_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Open reservation creation window
				CreerReservation newReserv = new CreerReservation(reserv_table);
				
				//Reset warning label on succesful operation
				reserv_warning_lbl.setText("");
			}
		});
		newReserv_btn.setBounds(517, 155, 185, 56);
		reservations.add(newReserv_btn);
		
		JButton delReserv_btn = new JButton("Supprimer reservation");
		delReserv_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = reserv_table.getSelectedRow();
				if(index < 0) {
					// if user hasnt selected any row :
					reserv_warning_lbl.setText("<html>Veuillez Selectionner une reservation à supprimer.</html>");
					// ^ html tag is for automatic text wrapping
					return;
				} //else
				
				
				// Verification prompt, ask user if he really wants to delete element
				int result = JOptionPane.showConfirmDialog(reservations, "Etes vous sûr?","Verification", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				//if user wants to delete element
				if(result == JOptionPane.YES_OPTION) {
					String codeReserv = (String) reserv_table.getValueAt(index, 0);
					ReservationController.deleteReservation(codeReserv);
					ReservationController.fetchAll(reserv_table, (filtre)reserv_filtre.getSelectedItem());	
				}
				//Reset warning label on succesful operation
				reserv_warning_lbl.setText("");
			}
		});
		delReserv_btn.setBounds(517, 225, 185, 56);
		reservations.add(delReserv_btn);
		
		JButton modReserv_btn = new JButton("Modifier reservation");
		modReserv_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = reserv_table.getSelectedRow();
				if(index < 0) {
					// if user hasnt selected any row :
					reserv_warning_lbl.setText("<html>Veuillez Selectionner une reservation à modifier.</html>");
					// ^ html tag is for automatic text wrapping
					return;
				}
				String codeReserv = reserv_table.getValueAt(index, 0).toString();
				String dateDep = reserv_table.getValueAt(index, 4).toString();
				String dateRet = reserv_table.getValueAt(index, 5).toString();
				boolean isValid = (boolean)reserv_table.getValueAt(index, 6);
				boolean isCanceled = (boolean)reserv_table.getValueAt(index, 7);
				
				//Open reservation modification window
				ModifierReservation newReserv = new ModifierReservation(reserv_table, codeReserv, dateDep, dateRet, isValid, isCanceled);
				
				//Reset warning label on succesful operation
				reserv_warning_lbl.setText("");
			}
		});
		modReserv_btn.setBounds(517, 291, 185, 56);
		reservations.add(modReserv_btn);
		
		reserv_field = new JTextField();
		reserv_field.setBounds(23, 10, 371, 37);
		reservations.add(reserv_field);
		reserv_field.setColumns(10);
		
		JButton searchReserv_btn = new JButton("Rechercher");
		searchReserv_btn.setBounds(404, 10, 103, 37);
		searchReserv_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codeReserv = reserv_field.getText();
				if(codeReserv.isEmpty()) {
					reserv_warning_lbl.setText("<html>Veuillez Saisir un code à rechercher.</html>");
					return;
				}
				//search for reservation
				ReservationController.findReservation(codeReserv, reserv_table);
				
				//Reset warning label on succesful operation
				reserv_warning_lbl.setText("");
			}
		});
		reservations.add(searchReserv_btn);
	}

	private void createFacturePanel() {
		JPanel factures = new JPanel();
		mainPanel.add(factures, "facture");
		factures.setLayout(null);
		
		JScrollPane facture_scroll = new JScrollPane();
		facture_scroll.setBounds(10, 57, 467, 480);
		factures.add(facture_scroll);
		
		facture_table = new JTable();
		facture_scroll.setViewportView(facture_table);
		
		facture_field = new JTextField();
		facture_field.setToolTipText("");
		facture_field.setBounds(10, 10, 333, 36);
		factures.add(facture_field);
		facture_field.setColumns(10);
		
		JButton searchFacture_btn = new JButton("Rechercher");
		searchFacture_btn.setBounds(353, 10, 124, 36);
		factures.add(searchFacture_btn);
		
		JButton newFacture_btn = new JButton("Nouvelle facture");
		newFacture_btn.setBounds(501, 220, 198, 43);
		factures.add(newFacture_btn);
		
		JButton dltFacture_btn = new JButton("Supprimer");
		dltFacture_btn.setBounds(501, 326, 198, 43);
		factures.add(dltFacture_btn);
		
		JButton modFacture_btn = new JButton("Modifier");
		modFacture_btn.setBounds(501, 274, 198, 43);
		factures.add(modFacture_btn);
		
		JButton facture_actualiser_btn = new JButton("Actualiser");
		facture_actualiser_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FactureController.fetchAll(facture_table);
			}
		});
		facture_actualiser_btn.setBounds(501, 494, 198, 43);
		factures.add(facture_actualiser_btn);
		
	}
}
