package view;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import controller.VehiculeController;
import interfaces.MainInterface;
import model.Vehicule;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;
import java.sql.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class AddNewVehicule  extends JPanel{
	// ATTRIBUTS DE LA CLASSE ADDNEWVEHICULE
	private MainInterface mainInterface;
	private JTable table;
	private CardLayout cl;
	private JTextField ImmatriculationVehicule;
	private JTextField marqueVehicule;
	private JTextField typeVehicule;
	private JTextField carburant;
	private JTextField kilometrage;
	private JComboBox YcomboBox;
	private JComboBox McomboBox;
	private JComboBox DcomboBox;
	private JComboBox parkComboBox;
	
	private JTextField prixLocation;
	private JCheckBox disponible;
	private JLabel lbl_disp ;
	private JLabel lbl_indisp;
	

	private static VehiculePanel vehiculePanel;
	// CONSTRUCTOR
	public AddNewVehicule(MainInterface mainInterface) {
		setLayout(null);
		setBounds(new Rectangle(0, 0, 732, 547));
		this.mainInterface = mainInterface;
		this.cl = (CardLayout) mainInterface.getMainPanel().getLayout();
		initialize();
	}
	// METHODE INITIALIZE QUI CREE UN PANEL
	@SuppressWarnings("unchecked")
	private void initialize() {
		setLayout(null);
		setLayout(null);
		JLabel lblNewLabel = new JLabel("Ajout d'un nouveau v\u00E9hicule");
		lblNewLabel.setBounds(116, 11, 549, 46);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblNewLabel);
		
		JLabel matricule_lbl = new JLabel("Immatriculation:");
		matricule_lbl.setBounds(97, 67, 90, 20);
		matricule_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(matricule_lbl);
		
		ImmatriculationVehicule = new JTextField();
		ImmatriculationVehicule.setBounds(97, 91, 249, 27);
		ImmatriculationVehicule.setColumns(10);
		this.add(ImmatriculationVehicule);
		
		JLabel marque_lbl = new JLabel("Marque:");
		marque_lbl.setBounds(401, 72, 81, 20);
		marque_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(marque_lbl);
		
		marqueVehicule = new JTextField();
		marqueVehicule.setBounds(401, 91, 242, 27);
		marqueVehicule.setColumns(10);
		this.add(marqueVehicule);
		
		JLabel type_lbl = new JLabel("Type:");
		type_lbl.setBounds(97, 141, 120, 20);
		type_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(type_lbl);
		
		typeVehicule = new JTextField();
		typeVehicule.setBounds(97, 161, 249, 27);
		typeVehicule.setColumns(10);
		this.add(typeVehicule);
		
		JLabel carburant_lbl = new JLabel("Carburant:");
		carburant_lbl.setBounds(401, 141, 112, 20);
		carburant_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(carburant_lbl);
		
		carburant = new JTextField();
		carburant.setBounds(401, 161, 242, 27);
		carburant.setColumns(10);
		this.add(carburant);
		
	// BOUTON ENREGISTRER  VEHICULE
		JButton saveNewVehicule = new JButton("Enregistrer");
		saveNewVehicule.setBackground(viewSettings.SECONDARY);
		saveNewVehicule.setBounds(544, 486, 159, 37);
		saveNewVehicule.setFont(new Font("Tahoma", Font.BOLD, viewSettings.FONTSIZE));
		saveNewVehicule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!VehiculeController.empty(AddNewVehicule.this)) {
					try {
						@SuppressWarnings("deprecation")
						Vehicule V = new Vehicule(ImmatriculationVehicule.getText(),
								marqueVehicule.getText(),
								typeVehicule.getText(),
								carburant.getText(),
								Long.parseLong(kilometrage.getText()),
								Date.valueOf(YcomboBox.getSelectedItem() +"-"+McomboBox.getSelectedItem()+"-"+DcomboBox.getSelectedItem()),
								Integer.parseInt(parkComboBox.getSelectedItem().toString()),
								Integer.parseInt(prixLocation.getText()),
								disponible.isSelected());
						VehiculeController.saveNewVehicule(AddNewVehicule.this,V);
//						AddUser.this.mainInterface.setMainPanel(AddUser.this.userPanel,"user");	//revenir au menu precedent
					}catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null,ex.getMessage(), "Entrer un nombre", JOptionPane.WARNING_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"Un ou plusieurs champs sont vides", "Champs vides", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		this.add(saveNewVehicule);
		
	// BOUTON POUR VIDER TOUT LES CHAMPS	
		JButton deleteNewVehiculeFields = new JButton("Effacer tout");
		deleteNewVehiculeFields.setBackground(viewSettings.SECONDARY);
		deleteNewVehiculeFields.setBounds(284, 486, 159, 37);
		deleteNewVehiculeFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VehiculeController.emptyAddFields(AddNewVehicule.this);
			}
		});
		this.add(deleteNewVehiculeFields);
		
	// BOUTON POUR ANNULER L'AJOUT	
		JButton CancelNewVehiculeCreation = new JButton("Annuler");
		CancelNewVehiculeCreation.setBackground(viewSettings.SECONDARY);
		CancelNewVehiculeCreation.setBounds(28, 486, 159, 37);
		CancelNewVehiculeCreation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VehiculeController.cancel(AddNewVehicule.this.mainInterface);//Annuler l'enregistrement
			}
		});
		this.add(CancelNewVehiculeCreation);
		
		JLabel kilometrage_lbl = new JLabel("Kilom\u00E9trage:");
		kilometrage_lbl.setBounds(96, 215, 121, 20);
		kilometrage_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(kilometrage_lbl);
		
		kilometrage = new JTextField();
		kilometrage.setBounds(97, 240, 249, 27);
		kilometrage.setColumns(10);
		this.add(kilometrage);
		
		JLabel code_park_lbl = new JLabel("Code park:");
		code_park_lbl.setBounds(97, 295, 112, 20);
		code_park_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(code_park_lbl);
		
		disponible = new JCheckBox("Disponibilit\u00E9:");
		disponible.setBackground(viewSettings.SECONDARY);
		disponible.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VehiculeController.changeDisponibility(AddNewVehicule.this);
			}
		});
		disponible.setSelected(true);
		disponible.setIconTextGap(70);
		disponible.setHorizontalTextPosition(SwingConstants.LEFT);
		disponible.setHorizontalAlignment(SwingConstants.LEFT);
		disponible.setBounds(401, 314, 189, 27);
		add(disponible);
		
		lbl_disp = new JLabel("Disponible");
		lbl_disp.setBounds(523, 347, 120, 27);
		add(lbl_disp);
		
		lbl_indisp = new JLabel("Indisponible");
		lbl_indisp.setBounds(523, 347, 124, 27);
		add(lbl_indisp);
		
		JLabel date_miseCirc_lbl = new JLabel("Date de mise en circulation:");
		date_miseCirc_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		date_miseCirc_lbl.setBounds(97, 354, 202, 37);
		add(date_miseCirc_lbl);
		
		YcomboBox = new JComboBox();
		YcomboBox.setBackground(viewSettings.SECONDARY);
		YcomboBox.setModel(new DefaultComboBoxModel(new String[] {"2022","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010",
																"2009","2008","2007","2006","2005","2004","2003","2002","2001","2000","1999","1998","1997","1996",
																"1995","1994","1993","1992","1991","1990","1989","1988","1987","1986","1985","1984","1983","1982",
																"1981","1980","1979","1978","1977","1976","1975","1974","1973","1972","1971","1970","1969","1968",
																"1967","1966","1965","1964","1963","1962","1961","1960"}));
		YcomboBox.setBounds(138, 430, 110, 27);
		add(YcomboBox);
		
		McomboBox = new JComboBox();
		McomboBox.setBackground(viewSettings.SECONDARY);
		McomboBox.setBounds(304, 430, 110, 27);
		McomboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		add(McomboBox);
		
		DcomboBox = new JComboBox();
		DcomboBox.setBackground(viewSettings.SECONDARY);
		DcomboBox.setBounds(466, 430, 110, 27);
		DayCBSetModel(YcomboBox,McomboBox,DcomboBox);//RENSEIGNER LE JOUR SELON L'ANNEE ET LE MOIS QUE L'UTILISATEUR A CHOISI
		add(DcomboBox);
		
		JLabel annee_lbl = new JLabel("Ann\u00E9e");
		annee_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		annee_lbl.setBounds(138, 392, 110, 33);
		add(annee_lbl);
		
		JLabel mois_lbl = new JLabel("Mois");
		mois_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		mois_lbl.setBounds(305, 395, 109, 30);
		add(mois_lbl);
		
		JLabel jour_lbl = new JLabel("Jour");
		jour_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		jour_lbl.setBounds(466, 395, 110, 31);
		add(jour_lbl);
		
		JLabel prixLocation_lbl = new JLabel("Prix location:");
		prixLocation_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		prixLocation_lbl.setBounds(401, 215, 90, 20);
		add(prixLocation_lbl);
		
		prixLocation = new JTextField();
		prixLocation.setColumns(10);
		prixLocation.setBounds(401, 240, 242, 27);
		add(prixLocation);
		
		parkComboBox = new JComboBox();
		parkComboBox.setBackground(viewSettings.SECONDARY);
		parkComboBox.setBounds(97, 314, 154, 27);
		add(parkComboBox);
		parkComboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", 
				"30", "31","32","33","34","35","36","37","38","39","40"}));
	}
	
	
	
	//METHODE RENSEIGNANT LE DAY COMBOBOX SELON LE MOIS ET L'ANNEE
	private void DayCBSetModel(JComboBox annee, JComboBox mois, JComboBox jour) {
		mois.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				switch((String)mois.getSelectedItem()) {
					case "01": case"03": case"05": case"07": case "08": case "10": case "12": 
						jour.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09",
																			"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
																			"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", 
																			"30", "31"}));
						break;
					case "02":
						if(Year.isLeap( Integer.parseInt((String)annee.getSelectedItem()))) {
							jour.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09",
																				"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
																				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29"}));
						}else {
							jour.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09",
																				"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
																				"20", "21", "22", "23", "24", "25", "26", "27", "28"}));
							}
						break;
					default:
						jour.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09",
																			"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
																			"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", 
																			"30"}));
					break;
				}		
			}
		});
	}
	

	// GETTERS
	public JTextField getImmatriculationVehicule() {
		return this.ImmatriculationVehicule;
	}
	public JTextField getMarqueVehicule() {
		return marqueVehicule;
	}
	public JTextField getTypeVehicule() {
		return typeVehicule;
	}
	public JTextField getCarburant() {
		return carburant;
	}
	public JTextField getKilometrage() {
		return kilometrage;
	}
	public JComboBox getYcomboBox() {
		return YcomboBox;
	}
	public JComboBox getParkComboBox() {
		return parkComboBox;
	}
	public JComboBox getMcomboBox() {
		return McomboBox;
	}
	public JComboBox getDcomboBox() {
		return DcomboBox;
	}
	
	public JTextField getPrixLocation() {
		return prixLocation;
	}
	public JCheckBox getDisponible() {
		return disponible;
	}
	public JLabel getLbl_disp() {
		return lbl_disp;
	}
	public JLabel getLbl_indisp() {
		return lbl_indisp;
	}





//SETTERS
//	public void setTable(JTable t) {
//		this.table=t;//TO PASS THE TABLE AS A PARAMETER SO WE CAN INVOKE  fetchAll()
//	}
	public static void setPanel(VehiculePanel p) {// POUR POUVOIR REVENIR AU MENU PRECEDENT
		AddNewVehicule.vehiculePanel=p;
	}

}
