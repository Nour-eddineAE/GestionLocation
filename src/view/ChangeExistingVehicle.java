package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.Year;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import controller.VehiculeController;
import interfaces.MainInterface;
import model.Vehicule;

public class ChangeExistingVehicle extends JPanel{

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
	private static  String oldId;
	

	private static VehiculePanel vehiculePanel;
	// CONSTRUCTOR
	public ChangeExistingVehicle(MainInterface mainInterface) {
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
		JLabel lblNewLabel = new JLabel("Modification d'un vehicule existant");
		lblNewLabel.setBounds(116, 11, 549, 46);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblNewLabel);
		
		JLabel Matricule_lbl = new JLabel("Immatriculation:");
		Matricule_lbl.setBounds(70, 67, 262, 27);
		Matricule_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(Matricule_lbl);
		
		ImmatriculationVehicule = new JTextField();
		ImmatriculationVehicule.setBounds(70, 96, 262, 27);
		ImmatriculationVehicule.setColumns(10);
		this.add(ImmatriculationVehicule);
		
		JLabel marque_lbl = new JLabel("Marque:");
		marque_lbl.setBounds(366, 71, 142, 19);
		marque_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(marque_lbl);
		
		marqueVehicule = new JTextField();
		marqueVehicule.setBounds(366, 96, 279, 27);
		marqueVehicule.setColumns(10);
		this.add(marqueVehicule);
		
		JLabel type_lbl = new JLabel("Type:");
		type_lbl.setBounds(70, 133, 262, 13);
		type_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(type_lbl);
		
		typeVehicule = new JTextField();
		typeVehicule.setBounds(70, 156, 262, 27);
		typeVehicule.setColumns(10);
		this.add(typeVehicule);
		
		JLabel lblNewLabel_1_1_3 = new JLabel("Carburant:");
		lblNewLabel_1_1_3.setBounds(366, 133, 142, 13);
		lblNewLabel_1_1_3.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(lblNewLabel_1_1_3);
		
		carburant = new JTextField();
		carburant.setBounds(366, 156, 279, 27);
		carburant.setColumns(10);
		this.add(carburant);
		
	// BOUTON ENREGISTRER  VEHICULE
		JButton saveChanges = new JButton("Enregistrer");
		saveChanges.setBackground(viewSettings.SECONDARY);
		saveChanges.setBounds(531, 479, 159, 42);
		saveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!VehiculeController.empty(ChangeExistingVehicle.this)) {
					try {
						Vehicule V = new Vehicule(ImmatriculationVehicule.getText(),
								marqueVehicule.getText(),
								typeVehicule.getText(),
								carburant.getText(),
								Long.parseLong(kilometrage.getText()),
								Date.valueOf(YcomboBox.getSelectedItem() +"-"+McomboBox.getSelectedItem()+"-"+DcomboBox.getSelectedItem()),
								Integer.parseInt(parkComboBox.getSelectedItem().toString()),
								Integer.parseInt(prixLocation.getText())
								,disponible.isSelected());
						VehiculeController.saveChanges(V,ChangeExistingVehicle.oldId);
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
		this.add(saveChanges);
		
	// BOUTON POUR VIDER TOUT LES CHAMPS	
		JButton emptyAllFields = new JButton("Effacer tout");
		emptyAllFields.setBackground(viewSettings.SECONDARY);
		emptyAllFields.setBounds(284, 481, 159, 42);
		emptyAllFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VehiculeController.emptyVehicleFields(ChangeExistingVehicle.this);
			}
		});
		this.add(emptyAllFields);
		
	// BOUTON POUR ANNULER L'AJOUT	
		JButton CancelChanging = new JButton("Annuler");
		CancelChanging.setBackground(viewSettings.SECONDARY);
		CancelChanging.setBounds(27, 481, 159, 42);
		CancelChanging.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VehiculeController.cancel(ChangeExistingVehicle.this.mainInterface);//Annuler l'enregistrement
			}
		});
		this.add(CancelChanging);
		
		JLabel lblNewLabel_1_1_5_1 = new JLabel("Kilom\u00E9trage:");
		lblNewLabel_1_1_5_1.setBounds(70, 204, 262, 13);
		lblNewLabel_1_1_5_1.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(lblNewLabel_1_1_5_1);
		
		kilometrage = new JTextField();
		kilometrage.setBounds(70, 223, 262, 27);
		kilometrage.setColumns(10);
		this.add(kilometrage);
		
		JLabel lblNewLabel_1_1_5_2 = new JLabel("Code park:");
		lblNewLabel_1_1_5_2.setBounds(71, 278, 142, 19);
		lblNewLabel_1_1_5_2.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(lblNewLabel_1_1_5_2);
		
		disponible = new JCheckBox("Disponibilit\u00E9:");
		disponible.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VehiculeController.changeDisponibility(ChangeExistingVehicle.this);
			}
		});
		disponible.setSelected(true);
		disponible.setIconTextGap(70);
		disponible.setHorizontalTextPosition(SwingConstants.LEFT);
		disponible.setHorizontalAlignment(SwingConstants.LEFT);
		disponible.setBounds(366, 278, 189, 27);
		add(disponible);
		
		lbl_disp = new JLabel("Disponible");
		lbl_disp.setBounds(478, 305, 120, 37);
		add(lbl_disp);
		
		lbl_indisp = new JLabel("Indisponible");
		lbl_indisp.setBounds(478, 305, 124, 37);
		add(lbl_indisp);
		
		JLabel lblNewLabel_1_1_5_2_1 = new JLabel("Date de mise en circulation:");
		lblNewLabel_1_1_5_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1_5_2_1.setBounds(70, 365, 202, 19);
		add(lblNewLabel_1_1_5_2_1);
		
		YcomboBox = new JComboBox();
		YcomboBox.setBackground(viewSettings.SECONDARY);
		YcomboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		YcomboBox.setModel(new DefaultComboBoxModel(new String[] {"2022","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010",
																"2009","2008","2007","2006","2005","2004","2003","2002","2001","2000","1999","1998","1997","1996",
																"1995","1994","1993","1992","1991","1990","1989","1988","1987","1986","1985","1984","1983","1982",
																"1981","1980","1979","1978","1977","1976","1975","1974","1973","1972","1971","1970","1969","1968",
																"1967","1966","1965","1964","1963","1962","1961","1960"}));
		YcomboBox.setBounds(144, 432, 110, 27);
		add(YcomboBox);
		
		McomboBox = new JComboBox();
		McomboBox.setBackground(viewSettings.SECONDARY);
		McomboBox.setBounds(310, 432, 110, 27);
		McomboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		add(McomboBox);
		
		DcomboBox = new JComboBox();
		DcomboBox.setBackground(viewSettings.SECONDARY);
		DcomboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		DcomboBox.setBounds(472, 432, 110, 27);
		DayCBSetModel(YcomboBox,McomboBox,DcomboBox);//RENSEIGNER LE JOUR SELON L'ANNEE ET LE MOIS QUE L'UTILISATEUR A CHOISI
		add(DcomboBox);
		
		JLabel annee_lbl = new JLabel("Ann\u00E9e");
		annee_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		annee_lbl.setBounds(144, 394, 110, 33);
		add(annee_lbl);
		
		JLabel mois_lbl = new JLabel("Mois");
		mois_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		mois_lbl.setBounds(311, 397, 109, 30);
		add(mois_lbl);
		
		JLabel jour_lbl = new JLabel("Jour");
		jour_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		jour_lbl.setBounds(472, 397, 110, 31);
		add(jour_lbl);
		
		JLabel prix_lvl = new JLabel("Prix location:");
		prix_lvl.setHorizontalAlignment(SwingConstants.LEFT);
		prix_lvl.setBounds(366, 204, 142, 13);
		add(prix_lvl);
		
		prixLocation = new JTextField();
		prixLocation.setColumns(10);
		prixLocation.setBounds(366, 223, 279, 27);
		add(prixLocation);
		
		parkComboBox = new JComboBox();
		parkComboBox.setBackground(viewSettings.SECONDARY);
		parkComboBox.setBounds(70, 307, 154, 35);
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
					case "1": case"3": case"5": case"7": case "8": case "10": case "12": 
						jour.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9",
																			"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
																			"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", 
																			"30", "31"}));
						break;
					case "2":
						if(Year.isLeap( Integer.parseInt((String)annee.getSelectedItem()))) {
							jour.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9",
																				"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
																				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29"}));
						}else {
							jour.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9",
																				"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
																				"20", "21", "22", "23", "24", "25", "26", "27", "28"}));
							}
						break;
					default:
						jour.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9",
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
	public JComboBox getParkComboBox() {
		return parkComboBox;
	}




//SETTERS
//	public void setTable(JTable t) {
//		this.table=t;//TO PASS THE TABLE AS A PARAMETER SO WE CAN INVOKE  fetchAll()
//	}
	public static void setPanel(VehiculePanel p) {// POUR POUVOIR REVENIR AU MENU PRECEDENT
		ChangeExistingVehicle.vehiculePanel=p;
	}
	public static void setOldId(String oldId) {
		ChangeExistingVehicle.oldId=oldId;
	}
}
