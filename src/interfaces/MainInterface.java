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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.CardLayout;
import java.util.LinkedHashMap;
import view.ReservationPanel;

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
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainColor = new Color(75, 0, 130);
		secondaryColor = new Color(224, 199, 242);
		highlight = new Color(202, 168, 227);
		navFontSize = 12;
		
		JPanel titleBar = new JPanel();
		titleBar.setBounds(0, 0, 986, 102);
		titleBar.setBackground(mainColor);
		
		JLabel logoPlaceHolder = new JLabel("LOGO here");
		logoPlaceHolder.setBounds(333, 17, 315, 75);
		logoPlaceHolder.setHorizontalAlignment(SwingConstants.CENTER);
		logoPlaceHolder.setForeground(new Color(255, 255, 255));
		logoPlaceHolder.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JPanel sideBar = new JPanel();
		sideBar.setBounds(0, 100, 234, 563);
		sideBar.setBackground(secondaryColor);
		
		JPanel navigation = new JPanel();
		navigation.setBounds(0, 80, 234, 388);
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
		mainPanel.setLayout(new CardLayout(0, 0));
		cl = (CardLayout) mainPanel.getLayout();
		
		JPanel client = new JPanel();
		mainPanel.add(client, "client");
		
		JPanel parking = new JPanel();
		mainPanel.add(parking, "parking");
		
		JLabel lblNewLabel_1 = new JLabel("Parking");
		parking.add(lblNewLabel_1);
		
		//Panel des RESERVATIONS ---------------------------------------------------------------------
		
		//createReservationPanel();
		ReservationPanel reservPanel = new ReservationPanel();
		mainPanel.add(reservPanel, "reserv");
		reservPanel.setLayout(null);
		
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
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(sideBar);
		sideBar.setLayout(null);
		sideBar.add(navigation);
		frame.getContentPane().add(mainPanel);
		frame.getContentPane().add(titleBar);
		titleBar.setLayout(null);
		titleBar.add(logoPlaceHolder);
		
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
	
	private void createFacturePanel() {
		JPanel factures = new JPanel();
		mainPanel.add(factures, "facture");
		factures.setLayout(null);
		
		JScrollPane facture_scroll = new JScrollPane();
		facture_scroll.setBounds(10, 57, 493, 480);
		factures.add(facture_scroll);
		
		facture_table = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column){  
		          return false;  
		    };
		};
		facture_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		facture_scroll.setViewportView(facture_table);
		
		FactureController.fetchAll(facture_table);
		
		JLabel facture_warning_lbl = new JLabel("");
		facture_warning_lbl.setForeground(Color.RED);
		facture_warning_lbl.setBounds(535, 57, 164, 113);
		factures.add(facture_warning_lbl);
		
		facture_field = new JTextField();
		facture_field.setBounds(10, 10, 353, 36);
		factures.add(facture_field);
		facture_field.setColumns(10);
		
		JButton searchFacture_btn = new JButton("Rechercher");
		searchFacture_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(facture_field.getText().isEmpty()) {
					facture_warning_lbl.setText("<html>Veuillez saisir un code avant de rechercher.</html>");
					return;
				}
				facture_warning_lbl.setText("");
				FactureController.findFacture(facture_field.getText(), facture_table);
			}
		});
		searchFacture_btn.setBounds(373, 10, 130, 36);
		factures.add(searchFacture_btn);
		
		JButton newFacture_btn = new JButton("Nouvelle facture");
		newFacture_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreerFacture newFacture = new CreerFacture(facture_table);
				facture_warning_lbl.setText("");
			}
		});
		newFacture_btn.setBounds(535, 220, 164, 43);
		factures.add(newFacture_btn);
		
		JButton dltFacture_btn = new JButton("Supprimer");
		dltFacture_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = facture_table.getSelectedRow();
				if(index < 0) {
					//if user hasnt selected a row
					facture_warning_lbl.setText("<html>Veuillez selectionner une facture à supprimer.</html>");
					return;
				}
				//else reset warning label on success
				facture_warning_lbl.setText("");
				int result = JOptionPane.showConfirmDialog(null, "Êtes vous sûr?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(result == JOptionPane.YES_OPTION) {
					int codeFact = (int) facture_table.getValueAt(index, 0);
					FactureController.deleteFacture(codeFact);
					FactureController.fetchAll(facture_table);
				}
			}
		});
		dltFacture_btn.setBounds(535, 273, 164, 43);
		factures.add(dltFacture_btn);
		
		JButton facture_actualiser_btn = new JButton("Actualiser");
		facture_actualiser_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FactureController.fetchAll(facture_table);
				facture_warning_lbl.setText("");
			}
		});
		facture_actualiser_btn.setBounds(535, 494, 164, 43);
		factures.add(facture_actualiser_btn);
		
		
		
	}
}
