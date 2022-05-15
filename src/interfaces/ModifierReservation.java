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
import controller.ReservationController;
import controller.ReservationController.filtre;
import exceptions.InvalidDate;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.JCheckBox;

public class ModifierReservation {

	private JFrame frmCreerReservation;
	private JTable reserv_table;
	
	private String dateDepart, dateRetour;
	private Color mainColor;
	private Color secondaryColor;
	private boolean isValid;
	private boolean isCanceled;
	private String codeReserv;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModifierReservation window = new ModifierReservation();
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
	public ModifierReservation() {
		mainColor = new Color(75, 0, 130);
		secondaryColor = new Color(224, 199, 242);
		initialize();
	}
	
	public ModifierReservation(JTable reserv_table,String codeReserv, String dateDep, String dateRet, boolean isValid, boolean isCanceled) {
		this.dateDepart = dateDep;
		this.dateRetour = dateRet;
		this.isValid = isValid;
		this.isCanceled = isCanceled;
		this.codeReserv = codeReserv;

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
		frmCreerReservation.setBounds(100, 100, 1053, 444);
		frmCreerReservation.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmCreerReservation.getContentPane().setLayout(null);
		
		JLabel dateDepart_lbl = new JLabel("Date depart:");
		dateDepart_lbl.setBounds(227, 35, 124, 28);
		frmCreerReservation.getContentPane().add(dateDepart_lbl);
		
		JLabel dateRetour_lbl = new JLabel("Date Retour:");
		dateRetour_lbl.setBounds(227, 150, 124, 28);
		frmCreerReservation.getContentPane().add(dateRetour_lbl);
		
		//Creation des panel de choix de date
		dateDep();
		dateRet();
		
		
		JCheckBox valide_box = new JCheckBox("Valid\u00E9");
		valide_box.setSelected(isValid);
		valide_box.setBounds(227, 307, 270, 21);
		frmCreerReservation.getContentPane().add(valide_box);
		
		JCheckBox annul_box = new JCheckBox("Annul\u00E9");
		annul_box.setSelected(isCanceled);
		annul_box.setBounds(516, 307, 270, 21);
		frmCreerReservation.getContentPane().add(annul_box);
		
		JButton sauvegarder_btn = new JButton("Sauvegarder");
		sauvegarder_btn.setForeground(new Color(255, 255, 255));
		sauvegarder_btn.setBackground(mainColor);
		sauvegarder_btn.setBounds(869, 350, 109, 34);
		sauvegarder_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ReservationController.modifyReservation(codeReserv, dateDepart, dateRetour, valide_box.isSelected(), annul_box.isSelected());
					ReservationController.fetchAll(reserv_table, filtre.Tous);
					OperationEffectue op = new OperationEffectue();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					OperationEchouee op = new OperationEchouee(e1.getMessage());
					e1.printStackTrace();
					return;
				} catch (InvalidDate e1) {
					OperationEchouee op = new OperationEchouee(e1.getMessage());
					e1.printStackTrace();
					return;
				}
			}
		});
		frmCreerReservation.getContentPane().add(sauvegarder_btn);
		
		JButton Annuler_btn = new JButton("Annuler");
		Annuler_btn.setForeground(new Color(255, 255, 255));
		Annuler_btn.setBackground(mainColor);
		Annuler_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmCreerReservation.dispose();
			}
		});
		Annuler_btn.setBounds(29, 350, 109, 34);
		frmCreerReservation.getContentPane().add(Annuler_btn);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void dateDep() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		JPanel dateDepInput = new JPanel();
		dateDepInput.setBounds(227, 73, 559, 67);
		frmCreerReservation.getContentPane().add(dateDepInput);
		dateDepInput.setLayout(null);
		
		JComboBox annee_comboBox = new JComboBox();
		annee_comboBox.setBackground(secondaryColor);
		annee_comboBox.setModel(new DefaultComboBoxModel(new String[] {Integer.toString(currentYear), Integer.toString(currentYear+1), Integer.toString(currentYear+2), Integer.toString(currentYear+3), Integer.toString(currentYear+4)}));
		annee_comboBox.setBounds(41, 27, 124, 27);
		annee_comboBox.setSelectedItem(dateDepart.split("-")[0]);
		dateDepInput.add(annee_comboBox);
		
		JComboBox jour_comboBox = new JComboBox();
		jour_comboBox.setBounds(388, 27, 124, 27);
		jour_comboBox.setBackground(secondaryColor);
		dateDepInput.add(jour_comboBox);
		
		JComboBox mois_comboBox = new JComboBox();
		
		mois_comboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		mois_comboBox.setBounds(212, 27, 124, 27);
		mois_comboBox.setBackground(secondaryColor);
		mois_comboBox.setSelectedItem(dateDepart.split("-")[1]);
		dateDepInput.add(mois_comboBox);
		
		setupDayChooser(annee_comboBox, mois_comboBox, jour_comboBox);
		jour_comboBox.setSelectedItem(dateDepart.split("-")[2]);
		dateDepart = annee_comboBox.getSelectedItem() + "-" + mois_comboBox.getSelectedItem() + "-" + jour_comboBox.getSelectedItem();
		
		for(JComboBox box : new JComboBox[]{annee_comboBox, jour_comboBox, mois_comboBox} ) {
			
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
		dateRetInput.setBounds(227, 188, 559, 67);
		frmCreerReservation.getContentPane().add(dateRetInput);
		
		JComboBox annee_comboBox = new JComboBox();
		annee_comboBox.setModel(new DefaultComboBoxModel(new String[] {Integer.toString(currentYear), Integer.toString(currentYear+1), Integer.toString(currentYear+2), Integer.toString(currentYear+3), Integer.toString(currentYear+4)}));
		annee_comboBox.setBounds(42, 27, 124, 27);
		annee_comboBox.setBackground(secondaryColor);
		annee_comboBox.setSelectedItem(dateRetour.split("-")[0]);
		dateRetInput.add(annee_comboBox);
		
		JComboBox jour_comboBox = new JComboBox();
		jour_comboBox.setBounds(391, 27, 124, 27);
		jour_comboBox.setBackground(secondaryColor);
		dateRetInput.add(jour_comboBox);
		
		JComboBox mois_comboBox = new JComboBox();
		mois_comboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		mois_comboBox.setBounds(212, 27, 124, 27);
		mois_comboBox.setBackground(secondaryColor);
		mois_comboBox.setSelectedItem(dateRetour.split("-")[1]);
		dateRetInput.add(mois_comboBox);
		
		setupDayChooser(annee_comboBox, mois_comboBox, jour_comboBox);
		jour_comboBox.setSelectedItem(dateRetour.split("-")[2]);
		dateRetour = annee_comboBox.getSelectedItem() + "-" + mois_comboBox.getSelectedItem() + "-" + jour_comboBox.getSelectedItem();
		
		for(JComboBox box : new JComboBox[]{annee_comboBox, jour_comboBox, mois_comboBox} ) {
			
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
}
