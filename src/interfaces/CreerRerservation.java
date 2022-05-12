package interfaces;

import java.awt.EventQueue;

import java.util.Calendar;
import java.time.Year;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import controller.ClientController;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreerRerservation {

	private JFrame frmCreerReservation;
	private JTable reserv_client_table;
	
	private String dateDepart, dateRetour;
	private JTable serv_vehi_table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreerRerservation window = new CreerRerservation();
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
	public CreerRerservation() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCreerReservation = new JFrame();
		frmCreerReservation.setResizable(false);
		frmCreerReservation.setTitle("Creer reservation");
		frmCreerReservation.setBounds(100, 100, 1053, 648);
		frmCreerReservation.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmCreerReservation.getContentPane().setLayout(null);
		
		JScrollPane reserv_client_Scroll = new JScrollPane();
		reserv_client_Scroll.setBounds(29, 58, 452, 150);
		frmCreerReservation.getContentPane().add(reserv_client_Scroll);
		
		reserv_client_table = new JTable();
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
		reserv_client_actualiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientController.fetchAll(reserv_client_table);
			}
		});
		reserv_client_actualiser.setBounds(29, 227, 168, 34);
		frmCreerReservation.getContentPane().add(reserv_client_actualiser);
		
		ClientController.fetchAll(reserv_client_table);
		
		//Creation des panel de choix de date
		dateDep();
		dateRet();
		
		JScrollPane reserv_vehi_Scroll = new JScrollPane();
		reserv_vehi_Scroll.setBounds(526, 58, 452, 150);
		frmCreerReservation.getContentPane().add(reserv_vehi_Scroll);
		
		serv_vehi_table = new JTable();
		reserv_vehi_Scroll.setViewportView(serv_vehi_table);
		
		JButton reserv_vehi_actualiser = new JButton("Actualiser");
		reserv_vehi_actualiser.setBounds(526, 227, 168, 34);
		frmCreerReservation.getContentPane().add(reserv_vehi_actualiser);
		
		JButton sauvegarder_btn = new JButton("Sauvegarder");
		sauvegarder_btn.setBounds(526, 548, 109, 34);
		frmCreerReservation.getContentPane().add(sauvegarder_btn);
		
		JButton Annuler_btn = new JButton("Annuler");
		Annuler_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmCreerReservation.dispose();
			}
		});
		Annuler_btn.setBounds(372, 548, 109, 34);
		frmCreerReservation.getContentPane().add(Annuler_btn);
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void dateDep() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		JPanel dateDepInput = new JPanel();
		dateDepInput.setBounds(218, 313, 559, 67);
		frmCreerReservation.getContentPane().add(dateDepInput);
		dateDepInput.setLayout(null);
		
		JComboBox annee_comboBox = new JComboBox();
		annee_comboBox.setModel(new DefaultComboBoxModel(new String[] {Integer.toString(currentYear), Integer.toString(currentYear+1), Integer.toString(currentYear+2), Integer.toString(currentYear+3), Integer.toString(currentYear+4)}));
		annee_comboBox.setBounds(41, 27, 124, 27);
		dateDepInput.add(annee_comboBox);
		
		JComboBox jour_comboBox = new JComboBox();
		jour_comboBox.setBounds(388, 27, 124, 27);
		dateDepInput.add(jour_comboBox);
		
		JComboBox mois_comboBox = new JComboBox();
		
		mois_comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		mois_comboBox.setBounds(212, 27, 124, 27);
		dateDepInput.add(mois_comboBox);
		
		setupDayChooser(annee_comboBox, mois_comboBox, jour_comboBox);
		jour_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateDepart = annee_comboBox.getSelectedItem() + "-" + mois_comboBox.getSelectedItem() + "-" + jour_comboBox.getSelectedItem();
			}
		});
		
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
		dateRetInput.add(annee_comboBox);
		
		JComboBox jour_comboBox = new JComboBox();
		jour_comboBox.setBounds(391, 27, 124, 27);
		dateRetInput.add(jour_comboBox);
		
		JComboBox mois_comboBox = new JComboBox();
		mois_comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		mois_comboBox.setBounds(212, 27, 124, 27);
		dateRetInput.add(mois_comboBox);
		
		setupDayChooser(annee_comboBox, mois_comboBox, jour_comboBox);
		jour_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateRetour = annee_comboBox.getSelectedItem() + "-" + mois_comboBox.getSelectedItem() + "-" + jour_comboBox.getSelectedItem();
			}
		});
		
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
}
