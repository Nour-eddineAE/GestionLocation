package interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTabbedPane;
import java.awt.CardLayout;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.beans.PropertyChangeEvent;

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
		
		/*
		JLabel clients_lbl = new JLabel("Gestion de clients");
		
		JLabel reservations_lbl = new JLabel("Gestion des reservations");
		
		JLabel contrats_lbl = new JLabel("Gestion des contrats");
		
		JLabel factures_lbl = new JLabel("Gestion des factures");
		
		JLabel sanctions_lbl = new JLabel("Gestion des sanctions");
		
		JLabel vehicules_lbl = new JLabel("Gestion des vehicules");
		
		JLabel parking_lbl = new JLabel("Gestion des parkings");
		
		JLabel utilisateurs_lbl = new JLabel("Gestion des utilisateurs");
		*/
		
		//Add all labels to list so we can modify their properties in the method below [Respect name Order Above]
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
		
		JLabel lblNewLabel = new JLabel("Gestion des clients");
		client.add(lblNewLabel);
		
		JPanel parking = new JPanel();
		mainPanel.add(parking, "parking");
		
		JLabel lblNewLabel_1 = new JLabel("Parking");
		parking.add(lblNewLabel_1);
		
		JPanel reservations = new JPanel();
		mainPanel.add(reservations, "reserv");
		
		JLabel lblNewLabel_2 = new JLabel("Reservations");
		reservations.add(lblNewLabel_2);
		
		JPanel contrats = new JPanel();
		mainPanel.add(contrats, "contrat");
		
		JLabel lblContrats = new JLabel("contrats");
		contrats.add(lblContrats);
		
		JPanel factures = new JPanel();
		mainPanel.add(factures, "facture");
		
		JLabel lblFactures = new JLabel("factures");
		factures.add(lblFactures);
		
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
}
