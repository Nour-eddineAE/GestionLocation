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
import javax.swing.JOptionPane;
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
import controller.SanctionController;
import controller.FactureController;
import view.ClientMainView;
import view.CreerFacturePanel;
import view.CreerReservPanel;
import view.FacturePanel;
import view.ReservationPanel;
import view.SanctionInfoPanel;
import view.SanctionPanel;
import view.UserPanel;
import view.VehiculePanel;
import controller.ParkingController;


public class MainInterface {

	JFrame frame;
	private Color mainColor;
	private Color secondaryColor;
	private Color highlight;

	private int navFontSize;
	private CardLayout cl;
	private JPanel mainPanel;
	private String currentPane;
	private LinkedHashMap<String, JLabel> navItemList;
	
	//CONTROLLERS @ABD-AB
	private ReservationController reservController;
	private FactureController factureController;
	private SanctionController sanctionController;
	private JTable parkingtable;
	private JTextField parkingtextField;
	private JTable contrat_table;

	private boolean isAdmin;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainInterface window = new MainInterface();
					window.frame.setVisible(true);
					window.frame.setLocationRelativeTo(null);
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
		// ClientController.fetchAll(clienttable);
		ParkingController.fetchAll(parkingtable);
	}
	
	public MainInterface(boolean isAdmin) {
		this.isAdmin = isAdmin;
		initialize();
		
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 * 
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainInterface.class.getResource("/icons/icons8-garage-80.png")));
		frame.setTitle("Gestion de location");
		frame.setResizable(false);
		frame.getContentPane().setEnabled(false);
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		mainColor = new Color(75, 0, 130);
		secondaryColor = new Color(224, 199, 242);
		highlight = new Color(202, 168, 227);
		navFontSize = 12;
		frame.getContentPane().setLayout(null);

		JPanel titleBar = new JPanel();
		titleBar.setBackground(mainColor);
		titleBar.setBounds(0, 0, 986, 102);
		frame.getContentPane().add(titleBar);
		titleBar.setLayout(null);

		JLabel logoPlaceHolder = new JLabel("Gestion De Location");
		logoPlaceHolder.setHorizontalAlignment(SwingConstants.CENTER);
		logoPlaceHolder.setForeground(new Color(255, 255, 255));
		logoPlaceHolder.setFont(new Font("Tahoma", Font.BOLD, 24));
		logoPlaceHolder.setBounds(333, 17, 315, 75);
		titleBar.add(logoPlaceHolder);

		JPanel sideBar = new JPanel();
		sideBar.setBounds(0, 94, 234, 569);
		sideBar.setBackground(secondaryColor);
		
		
		JPanel navigation = new JPanel();
		navigation.setBounds(0, 80, 234, 388);
		navigation.setBackground(secondaryColor);
		navigation.setLayout(new GridLayout(8, 1, 0, 0));
		
		frame.getContentPane().add(sideBar);
		sideBar.setLayout(null);
		sideBar.add(navigation);
		
		mainPanel = new JPanel();
		mainPanel.setBounds(234, 102, 752, 561);
		mainPanel.setLayout(new CardLayout(0, 0));
		cl = (CardLayout) mainPanel.getLayout();

		// Client Panel generation
		ClientMainView client = new ClientMainView(getMainPanel());
		mainPanel.add(client, "client");

		JPanel parking = new JPanel();
		mainPanel.add(parking, "parking");

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

		//Panel des factures @ABD-AB-------------------------------------------------------------------

		FacturePanel factures = new FacturePanel(this);
		mainPanel.add(factures, "facture");
		
		CreerFacturePanel creerFacturePanel = new CreerFacturePanel(this);
		mainPanel.add(creerFacturePanel, "newFacture");

		//END Panel des factures ----------------------------------------------------------------------
		
		
		//Panel des sanctions -------------------------------------------------------------------------
		
		SanctionPanel sanctions = new SanctionPanel();
		mainPanel.add(sanctions, "sanction");
		
		SanctionInfoPanel sanctionInfo = new SanctionInfoPanel();
		mainPanel.add(sanctionInfo, "sanctionInfo");
		
		//END Panel des Sanctions ---------------------------------------------------------------------

		
		cl.show(mainPanel, "facture");
		frame.getContentPane().add(mainPanel);
		

		//Association des panels aux controlleurs @ABD-AB
		factureController = new FactureController(factures, creerFacturePanel);
		reservController = new ReservationController(reservPanel, createReservPanel);
		sanctionController = new SanctionController(sanctions, sanctionInfo, this);
		

		// Parking Panel generation
		setupParkingPanel(parking);
		
		
		navItemList = new LinkedHashMap<String, JLabel>();
		navItemList.put("client", new JLabel("Gestion de clients"));
		navItemList.put("reserv", new JLabel("Gestion des reservations"));
		navItemList.put("contrat", new JLabel("Gestion des contrats"));
		navItemList.put("facture", new JLabel("Gestion des factures"));
		navItemList.put("sanction", new JLabel("Gestion des sanctions"));
		navItemList.put("vehicule", new JLabel("Gestion des vehicules"));
		navItemList.put("parking", new JLabel("Gestion des parkings"));
		navItemList.put("user", new JLabel("Gestion des utilisateurs"));

		for(String item : navItemList.keySet()) {
			if(!isAdmin && item.equals("user")) continue;
			JLabel lab = navItemList.get(item);
			setupNavItem(lab, item);
			navigation.add(lab);
		}

		VehiculePanel.setNavList(navItemList);
		VehiculePanel vehicules = new VehiculePanel(this);
		mainPanel.add(vehicules, "vehicule");
		vehicules.setLayout(null);
		
		JLabel lblVehicules = new JLabel("vehicules");
		vehicules.add(lblVehicules);
		
		UserPanel.setNavList(navItemList);
		UserPanel utilisateurs = new UserPanel(this);
		mainPanel.add(utilisateurs, "user");
		utilisateurs.setLayout(null);
	}

	private void setupNavItem(JLabel lab, String name) {
		lab.setOpaque(true);
		lab.setHorizontalAlignment(SwingConstants.CENTER);
		lab.setFont(new Font("Tahoma", Font.BOLD, navFontSize));
		lab.setBackground(secondaryColor);
		lab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// on hover change color
				lab.setBackground(highlight);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// if mouse exit && not the curret pane change color

				// [ NOTE ] add condition to check if it's the current pane!
				if (currentPane != name)
					lab.setBackground(secondaryColor);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				cl.show(mainPanel, name);
				currentPane = name;
				for (String item : navItemList.keySet()) {
					if (item != name) {
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
	private void setupParkingPanel(JPanel panel) {
		panel.setLayout(null);
		JScrollPane parkingscrollPane = new JScrollPane();
		parkingscrollPane.setBounds(10, 58, 574, 478);
		panel.add(parkingscrollPane);

		parkingtable = new JTable();
		parkingtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Object[] parkingcolumns = { "id", "nom", "prenom" };
		DefaultTableModel parkingmodel = new DefaultTableModel();
		parkingmodel.setColumnCount(3);
		parkingmodel.setColumnIdentifiers(parkingcolumns);
		parkingtable.setModel(parkingmodel);
		parkingscrollPane.setViewportView(parkingtable);

		JButton parkingbtnNewButton = new JButton("Rechercher");
		parkingbtnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string = parkingtextField.getText();
				ParkingController.findParking(string, parkingtable);
				parkingtextField.setText("");
			}
		});
		parkingbtnNewButton.setBounds(594, 11, 128, 36);
		panel.add(parkingbtnNewButton);

		JButton parkingbtnNewButton_1 = new JButton("Nouveau Parking");
		parkingbtnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreerNouveauParking window = new CreerNouveauParking(parkingtable);
			}
		});
		parkingbtnNewButton_1.setBounds(594, 76, 128, 36);
		panel.add(parkingbtnNewButton_1);

		JButton parkingbtnNewButton_5 = new JButton("Afficher");
		parkingbtnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = parkingtable.getSelectedRow();
				if (index >= 0) {

				} else {
					JOptionPane.showConfirmDialog(null, "Tu dois selectionner un element du tableau pour l'afficher!",
							"Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		parkingbtnNewButton_5.setBounds(594, 123, 128, 36);
		panel.add(parkingbtnNewButton_5);

		parkingtextField = new JTextField();
		parkingtextField.setBounds(10, 11, 574, 36);
		panel.add(parkingtextField);
		parkingtextField.setColumns(10);

		JButton parkingbtnNewButton_2 = new JButton("Modifier");
		parkingbtnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = parkingtable.getSelectedRow();
				if (index >= 0) {
					ModifierParking.setId(parkingtable.getModel().getValueAt(index, 0).toString());
					ModifierParking window = new ModifierParking(parkingtable, index);
				} else {
					JOptionPane.showConfirmDialog(null, "Tu dois selectionner un element du tableau pour le modifier!",
							"Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		parkingbtnNewButton_2.setBounds(594, 170, 128, 36);
		panel.add(parkingbtnNewButton_2);

		JButton parkingbtnNewButton_3 = new JButton("Actualiser");
		parkingbtnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ParkingController.fetchAll(parkingtable);
			}
		});
		parkingbtnNewButton_3.setBounds(594, 217, 128, 36);
		panel.add(parkingbtnNewButton_3);

		JButton parkingbtnNewButton_4 = new JButton("Supprimer");
		parkingbtnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = parkingtable.getSelectedRow();
				if (index >= 0) {
					int result = JOptionPane.showConfirmDialog(null, "Avez-vous Sure?", "Confirmation",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						ParkingController.deleteParking(parkingtable.getModel().getValueAt(index, 0).toString());
						ParkingController.fetchAll(parkingtable);
					}
				} else {
					JOptionPane.showConfirmDialog(null, "Tu dois selectionner un element du tableau pour le supprimer!",
							"Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		parkingbtnNewButton_4.setBounds(594, 264, 128, 36);
		panel.add(parkingbtnNewButton_4);
	}
	// SETTERS  

	// AJOUTE LES PANELS AU MAINPANEL
	public void addToMainPanel(JPanel Panel,String key) {
		this.mainPanel.add(Panel,key);
		Panel.setLayout(null);
	}
	public void showOnMainPanel(String key) {
		cl.show(mainPanel,key);
	}
}
