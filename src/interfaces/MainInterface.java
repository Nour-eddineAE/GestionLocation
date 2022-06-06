package interfaces;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import controller.ClientController;
import controller.ReservationController;
import controller.FactureController;
import view.CreerFacturePanel;
import view.CreerReservPanel;
import view.FacturePanel;
import view.ReservationPanel;

public class MainInterface {

	private JFrame frmGestionDeLocation;
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

	//CONTROLLERS @ABD-AB
	private ReservationController reservController;
	private FactureController factureController;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainInterface window = new MainInterface();
					window.frmGestionDeLocation.setVisible(true);
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
		frmGestionDeLocation = new JFrame();
		frmGestionDeLocation.setIconImage(Toolkit.getDefaultToolkit().getImage(MainInterface.class.getResource("/icons/icons8-garage-80.png")));
		frmGestionDeLocation.setTitle("Gestion de location");
		frmGestionDeLocation.setResizable(false);
		frmGestionDeLocation.getContentPane().setEnabled(false);
		frmGestionDeLocation.setBounds(100, 100, 1000, 700);
		frmGestionDeLocation.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		mainColor = new Color(75, 0, 130);
		secondaryColor = new Color(224, 199, 242);
		highlight = new Color(202, 168, 227);
		navFontSize = 12;

		JPanel sideBar = new JPanel();
		sideBar.setBounds(0, 94, 234, 569);
		sideBar.setBackground(secondaryColor);

		JPanel navigation = new JPanel();
		navigation.setBounds(0, 80, 234, 388);
		navigation.setBackground(secondaryColor);
		navigation.setLayout(new GridLayout(8, 1, 0, 0));

		navItemList = new LinkedHashMap<>();
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

		ReservationPanel reservPanel = new ReservationPanel(this);
		mainPanel.add(reservPanel, "reserv");

		CreerReservPanel createReservPanel = new CreerReservPanel(this);
		mainPanel.add(createReservPanel, "newReserv");


		//END PANEL RESERVATIONS ---------------------------------------------------------------------
		JPanel contrats = new JPanel();
		mainPanel.add(contrats, "contrat");

		JLabel lblContrats = new JLabel("contrats");
		contrats.add(lblContrats);

		//Panel des factures @ABD-AB-------------------------------------------------------------------------

		FacturePanel factures = new FacturePanel(this);
		mainPanel.add(factures, "facture");

		//END Panel des factures ---------------------------------------------------------------------
		
		
		//Panel des sanctions ---------------------------------------------------------------------
		
		JPanel sanctions = new JPanel();
		mainPanel.add(sanctions, "sanction");

		JLabel sanctions_1 = new JLabel("sanctions");
		sanctions.add(sanctions_1);
		
		//END Panel des Sanctions ---------------------------------------------------------------------
		
		
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
			@Override
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
			@Override
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
			@Override
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
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientController.fetchAll(clienttable);
			}
		});
		clientbtnNewButton_3.setBounds(594, 264, 128, 36);
		client.add(clientbtnNewButton_3);

		JButton clientbtnNewButton_4 = new JButton("Supprimer");
		clientbtnNewButton_4.addActionListener(new ActionListener() {
			@Override
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
		frmGestionDeLocation.getContentPane().setLayout(null);

		JPanel titleBar = new JPanel();
		titleBar.setBounds(0, 0, 986, 102);
		titleBar.setBackground(mainColor);

		JLabel logoPlaceHolder = new JLabel("LOGO here");
		logoPlaceHolder.setBounds(333, 17, 315, 75);
		logoPlaceHolder.setHorizontalAlignment(SwingConstants.CENTER);
		logoPlaceHolder.setForeground(new Color(255, 255, 255));
		logoPlaceHolder.setFont(new Font("Tahoma", Font.BOLD, 24));
		frmGestionDeLocation.getContentPane().add(titleBar);
		titleBar.setLayout(null);
		titleBar.add(logoPlaceHolder);
		frmGestionDeLocation.getContentPane().add(sideBar);
		sideBar.setLayout(null);
		sideBar.add(navigation);
		frmGestionDeLocation.getContentPane().add(mainPanel);


		//PANEL CREATION FACTURE @ABD-AB -------------------------------------------------------------------------------------------
		CreerFacturePanel creerFacturePanel = new CreerFacturePanel(this);
		mainPanel.add(creerFacturePanel, "newFacture");



		//Association des panels aux controlleurs @ABD-AB
		factureController = new FactureController(factures, creerFacturePanel);
		factureController.ActualiserTableau();

		reservController = new ReservationController(reservPanel, createReservPanel);

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


	//GETTERS
	public JPanel getMainPanel() {
		return this.mainPanel;
	}
	public FactureController getFactureController() {
		return factureController;
	}
}
