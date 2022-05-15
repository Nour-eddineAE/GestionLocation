package interfaces;

import java.awt.EventQueue;

import java.util.Calendar;
import java.time.Year;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import controller.ClientController;
import controller.ReservationController;
import controller.ReservationController.filtre;
import exceptions.InvalidDate;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ListSelectionModel;

public class CreerReservation {

	private JFrame frmCreerReservation;
	private JTable reserv_client_table;
	private JTable reserv_table;
	
	private String dateDepart = "null-null-null", dateRetour = "null-null-null";
	private JTable serv_vehi_table;
	private Color mainColor;
	private Color secondaryColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreerReservation window = new CreerReservation();
					window.frmCreerReservation.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreerReservation() {
		mainColor = new Color(75, 0, 130);
		secondaryColor = new Color(224, 199, 242);
		initialize();
	}
	public CreerReservation(JTable reserv_table) {
		this.reserv_table = reserv_table;
		mainColor = new Color(75, 0, 130);
		secondaryColor = new Color(224, 199, 242);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCreerReservation = new JFrame();
		frmCreerReservation.getContentPane().setBackground(new Color(255, 255, 255));
		frmCreerReservation.setResizable(false);
		frmCreerReservation.setVisible(true);
		frmCreerReservation.setTitle("Creer reservation");
		frmCreerReservation.setBounds(100, 100, 1053, 648);
		frmCreerReservation.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmCreerReservation.getContentPane().setLayout(null);
		
		JScrollPane reserv_client_Scroll = new JScrollPane();
		reserv_client_Scroll.setBounds(29, 58, 452, 150);
		frmCreerReservation.getContentPane().add(reserv_client_Scroll);
		
		JLabel warning_lbl = new JLabel("");
		warning_lbl.setForeground(Color.RED);
		warning_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		warning_lbl.setBounds(218, 548, 559, 34);
		frmCreerReservation.getContentPane().add(warning_lbl);
		
		//to make table cells uneditable
		reserv_client_table = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column){  
		          return false;  
		    };
		};
		
		reserv_client_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reserv_client_table.setBackground(new Color(255, 255, 255));
		reserv_client_Scroll.setViewportView(reserv_client_table);
		
		JLabel choice_lbl = new JLabel("Choisir un client :");
		choice_lbl.setBounds(29, 22, 347, 34);
		frmCreerReservation.getContentPane().add(choice_lbl);
		
		JLabel dateDepart_lbl = new JLabel("Date depart:");
		dateDepart_lbl.setBounds(218, 275, 124, 28);
		frmCreerReservation.getContentPane().add(dateDepart_lbl);
		
		JLabel dateRetour_lbl = new JLabel("Date Retour:");
		dateRetour_lbl.setBounds(218, 390, 124, 28);
		frmCreerReservation.getContentPane().add(dateRetour_lbl);
		
		JButton reserv_client_actualiser = new JButton("Actualiser");
		reserv_client_actualiser.setBackground(secondaryColor);
		reserv_client_actualiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//To refill client table
				ClientController.fetchAll(reserv_client_table);
			}
		});
		reserv_client_actualiser.setBounds(29, 227, 168, 34);
		frmCreerReservation.getContentPane().add(reserv_client_actualiser);
		
		ClientController.fetchAll(reserv_client_table);
		
		//Creation des panels de choix de date
		dateDep();
		dateRet();
		
		JScrollPane reserv_vehi_Scroll = new JScrollPane();
		reserv_vehi_Scroll.setBounds(526, 58, 452, 150);
		frmCreerReservation.getContentPane().add(reserv_vehi_Scroll);
		
		//to make table cells uneditable
		serv_vehi_table = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column){  
		          return false;  
		    };
		};
		serv_vehi_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reserv_vehi_Scroll.setViewportView(serv_vehi_table);
		
		JButton reserv_vehi_actualiser = new JButton("Actualiser");
		reserv_vehi_actualiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//To refill vehicle table
			}
		});
		reserv_vehi_actualiser.setBackground(secondaryColor);
		reserv_vehi_actualiser.setBounds(526, 227, 168, 34);
		frmCreerReservation.getContentPane().add(reserv_vehi_actualiser);
		
		JButton sauvegarder_btn = new JButton("Sauvegarder");
		sauvegarder_btn.setForeground(new Color(255, 255, 255));
		sauvegarder_btn.setBackground(mainColor);
		sauvegarder_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = reserv_client_table.getSelectedRow();
				if(index < 0) {
					//si aucun client n'est choisi
					warning_lbl.setText("<html>Veuillez choisir un client.</html>");
					return;
				}
				//TODO: check if user hasnt chosen any vehicle
				
				String codeClient = Integer.toString((int)reserv_client_table.getValueAt(index, 0));
				warning_lbl.setText("");
				try {
					if(ReservationController.isReservationDateAvailable("ABC", dateDepart, dateRetour))
						ReservationController.createReservation(codeClient, "ABC", dateDepart, dateRetour);
					else {
						warning_lbl.setText("<html>Vehicule déja reservé durant la période choisi.</html>");
						return;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					//OperationEchouee op = new OperationEchouee(e1.getMessage());
					JOptionPane.showConfirmDialog(null, e1.getMessage(), "Erreur", JOptionPane.OK_OPTION ,JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
					return;
				} catch (InvalidDate e1) {
					//OperationEchouee op = new OperationEchouee(e1.getMessage());
					warning_lbl.setText(e1.getMessage());
					JOptionPane.showConfirmDialog(null, e1.getMessage(), "Erreur", JOptionPane.DEFAULT_OPTION ,JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
					return;
				} 
				
				//refill main table
				ReservationController.fetchAll(reserv_table, filtre.Tous);
				warning_lbl.setText("");
				JOptionPane.showConfirmDialog(null, "Operation Effectuee", "Operation Effectuee", JOptionPane.DEFAULT_OPTION ,JOptionPane.INFORMATION_MESSAGE);
			}
		});
		sauvegarder_btn.setBounds(869, 548, 109, 34);
		frmCreerReservation.getContentPane().add(sauvegarder_btn);
		
		JButton Annuler_btn = new JButton("Annuler");
		Annuler_btn.setForeground(new Color(255, 255, 255));
		Annuler_btn.setBackground(mainColor);
		Annuler_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//fermez la fenetre
				frmCreerReservation.dispose();
			}
		});
		Annuler_btn.setBounds(29, 548, 109, 34);
		frmCreerReservation.getContentPane().add(Annuler_btn);
		
		JLabel choice_lbl_1 = new JLabel("Choisir une voiture:");
		choice_lbl_1.setBounds(526, 22, 347, 34);
		frmCreerReservation.getContentPane().add(choice_lbl_1);
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void dateDep() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		JPanel dateDepInput = new JPanel();
		dateDepInput.setBounds(218, 313, 559, 67);
		frmCreerReservation.getContentPane().add(dateDepInput);
		dateDepInput.setLayout(null);
		
		JComboBox annee_comboBox = new JComboBox();
		annee_comboBox.setBackground(secondaryColor);
		annee_comboBox.setModel(new DefaultComboBoxModel(new String[] {Integer.toString(currentYear), Integer.toString(currentYear+1), Integer.toString(currentYear+2), Integer.toString(currentYear+3), Integer.toString(currentYear+4)}));
		annee_comboBox.setBounds(41, 27, 124, 27);
		dateDepInput.add(annee_comboBox);
		
		JComboBox jour_comboBox = new JComboBox();
		jour_comboBox.setBounds(388, 27, 124, 27);
		jour_comboBox.setBackground(secondaryColor);
		dateDepInput.add(jour_comboBox);
		
		JComboBox mois_comboBox = new JComboBox();
		
		mois_comboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		mois_comboBox.setBounds(212, 27, 124, 27);
		mois_comboBox.setBackground(secondaryColor);
		dateDepInput.add(mois_comboBox);
		
		setupDayChooser(annee_comboBox, mois_comboBox, jour_comboBox);
		
		for(JComboBox box : new JComboBox[]{annee_comboBox, jour_comboBox, mois_comboBox} ) {
			//Foreach combo box update date value on change
			box.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dateDepart = annee_comboBox.getSelectedItem() + "-" + mois_comboBox.getSelectedItem() + "-" + jour_comboBox.getSelectedItem();
				}
			});
			
		}
		
		JLabel anneeDep_lbl = new JLabel("Ann\u00E9e");
		anneeDep_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		anneeDep_lbl.setBounds(79, 10, 45, 13);
		dateDepInput.add(anneeDep_lbl);
		
		JLabel moisDep_lbl = new JLabel("Mois");
		moisDep_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		moisDep_lbl.setBounds(250, 10, 45, 13);
		dateDepInput.add(moisDep_lbl);
		
		JLabel jourDep_lbl = new JLabel("Jour");
		jourDep_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		jourDep_lbl.setBounds(429, 10, 45, 13);
		dateDepInput.add(jourDep_lbl);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void dateRet() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		JPanel dateRetInput = new JPanel();
		dateRetInput.setLayout(null);
		dateRetInput.setBounds(218, 428, 559, 67);
		frmCreerReservation.getContentPane().add(dateRetInput);
		
		JComboBox annee_comboBox = new JComboBox();
		annee_comboBox.setModel(new DefaultComboBoxModel(new String[] {Integer.toString(currentYear), Integer.toString(currentYear+1), Integer.toString(currentYear+2), Integer.toString(currentYear+3), Integer.toString(currentYear+4)}));
		annee_comboBox.setBounds(42, 27, 124, 27);
		annee_comboBox.setBackground(secondaryColor);
		dateRetInput.add(annee_comboBox);
		
		JComboBox jour_comboBox = new JComboBox();
		jour_comboBox.setBounds(391, 27, 124, 27);
		jour_comboBox.setBackground(secondaryColor);
		dateRetInput.add(jour_comboBox);
		
		JComboBox mois_comboBox = new JComboBox();
		mois_comboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		mois_comboBox.setBounds(212, 27, 124, 27);
		mois_comboBox.setBackground(secondaryColor);
		dateRetInput.add(mois_comboBox);
		
		setupDayChooser(annee_comboBox, mois_comboBox, jour_comboBox);
		for(JComboBox box : new JComboBox[]{annee_comboBox, jour_comboBox, mois_comboBox} ) {
			//Foreach combo box update date value on change
			box.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dateRetour = annee_comboBox.getSelectedItem() + "-" + mois_comboBox.getSelectedItem() + "-" + jour_comboBox.getSelectedItem();
				}
			});
			
		}
		
		JLabel anneeDep_lbl = new JLabel("Ann\u00E9e");
		anneeDep_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		anneeDep_lbl.setBounds(88, 10, 45, 13);
		dateRetInput.add(anneeDep_lbl);
		
		JLabel moisDep_lbl = new JLabel("Mois");
		moisDep_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		moisDep_lbl.setBounds(259, 10, 45, 13);
		dateRetInput.add(moisDep_lbl);
		
		JLabel jourDep_lbl = new JLabel("Jour");
		jourDep_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		jourDep_lbl.setBounds(438, 10, 45, 13);
		dateRetInput.add(jourDep_lbl);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setupDayChooser(JComboBox annee, JComboBox mois, JComboBox jour) {
		mois.addActionListener(new ActionListener() {
			// les valeurs de comboBox des jours depend de mois et l'annee
			
			public void actionPerformed(ActionEvent e) {
				switch((String)mois.getSelectedItem()) {
					case "1": case"3": case"5": case"7": case "8": case "10": case "12": 
						jour.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09",
								"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
								"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", 
								"30", "31"}));
						break;
					case "2":
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
}
