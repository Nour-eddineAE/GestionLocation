package interfaces;

import java.awt.EventQueue;

import java.util.Calendar;
import java.time.Year;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import controller.ReservationController;
import model.Reservation;
import view.ReservationPanel;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JCheckBox;

public class ModifierReservation {

	private JFrame frmModReservation;
	private ReservationPanel reserv_panel;
	
	private String dateDepart, dateRetour, codeVehicule;
	private Color mainColor;
	private Color secondaryColor;
	private boolean isValid;
	private boolean isCanceled;
	private int codeReserv;
	
	private ReservationController cont;
	private JLabel warning_lbl;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModifierReservation window = new ModifierReservation();
					window.frmModReservation.setVisible(true);
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
	
	public ModifierReservation(ReservationPanel reserv_panel, Reservation r) {
		this.dateDepart = r.getDateDepart().toString();
		this.dateRetour = r.getDateRetour().toString();
		this.isValid = r.isValid();
		this.isCanceled = r.isCanceled();
		this.reserv_panel = reserv_panel;
		this.codeReserv = r.getCodeReservation();
		this.codeVehicule = r.getCodeVehicule();
		
		this.cont = new ReservationController(reserv_panel, this);
		mainColor = new Color(75, 0, 130);
		secondaryColor = new Color(224, 199, 242);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmModReservation = new JFrame();
		frmModReservation.getContentPane().setBackground(new Color(255, 255, 255));
		frmModReservation.setResizable(false);
		frmModReservation.setVisible(true);
		frmModReservation.setTitle("Creer reservation");
		frmModReservation.setBounds(100, 100, 1053, 471);
		frmModReservation.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmModReservation.getContentPane().setLayout(null);
		
		JLabel dateDepart_lbl = new JLabel("Date depart:");
		dateDepart_lbl.setBounds(225, 48, 124, 28);
		frmModReservation.getContentPane().add(dateDepart_lbl);
		
		JLabel dateRetour_lbl = new JLabel("Date Retour:");
		dateRetour_lbl.setBounds(225, 163, 124, 28);
		frmModReservation.getContentPane().add(dateRetour_lbl);
		
		//Creation des panel de choix de date
		dateDep();
		dateRet();
		
		
		JCheckBox valide_box = new JCheckBox("Valid\u00E9");
		valide_box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isValid = valide_box.isSelected();
			}
		});
		valide_box.setSelected(isValid);
		valide_box.setBounds(225, 320, 270, 21);
		frmModReservation.getContentPane().add(valide_box);
		
		JCheckBox annul_box = new JCheckBox("Annul\u00E9");
		annul_box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isCanceled = annul_box.isSelected();
			}
		});
		annul_box.setSelected(isCanceled);
		annul_box.setBounds(514, 320, 270, 21);
		frmModReservation.getContentPane().add(annul_box);
		
		JButton sauvegarder_btn = new JButton("Sauvegarder");
		sauvegarder_btn.setForeground(new Color(255, 255, 255));
		sauvegarder_btn.setBackground(mainColor);
		sauvegarder_btn.setBounds(869, 379, 109, 34);
		sauvegarder_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cont.ModifierReservation();
			}
		});
		frmModReservation.getContentPane().add(sauvegarder_btn);
		
		JButton Annuler_btn = new JButton("Annuler");
		Annuler_btn.setForeground(new Color(255, 255, 255));
		Annuler_btn.setBackground(mainColor);
		Annuler_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmModReservation.dispose();
			}
		});
		Annuler_btn.setBounds(29, 379, 109, 34);
		frmModReservation.getContentPane().add(Annuler_btn);
		
		JLabel codeReserv_lbl = new JLabel("N\u00B0 reservation:");
		codeReserv_lbl.setBounds(225, 25, 160, 13);
		frmModReservation.getContentPane().add(codeReserv_lbl);
		
		JLabel codeReservValue_lbl = new JLabel(Integer.toString(codeReserv));
		codeReservValue_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		codeReservValue_lbl.setBounds(326, 25, 160, 13);
		frmModReservation.getContentPane().add(codeReservValue_lbl);
		
		warning_lbl = new JLabel("");
		warning_lbl.setForeground(Color.RED);
		warning_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		warning_lbl.setBounds(225, 378, 559, 35);
		frmModReservation.getContentPane().add(warning_lbl);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void dateDep() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		JPanel dateDepInput = new JPanel();
		dateDepInput.setBounds(225, 86, 559, 67);
		frmModReservation.getContentPane().add(dateDepInput);
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
		dateRetInput.setBounds(225, 201, 559, 67);
		frmModReservation.getContentPane().add(dateRetInput);
		
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

	
	//Getters
	public Date getDateDepart() {
		return Date.valueOf(dateDepart);
	}
	

	public int getCodeReserv() {
		return codeReserv;
	}

	public JLabel getWarning_lbl() {
		return warning_lbl;
	}

	public Date getDateRetour() {
		return Date.valueOf(dateRetour);
	}

	public boolean isValid() {
		return isValid;
	}

	public boolean isCanceled() {
		return isCanceled;
	}
	public String getCodeVehicule() {
		return codeVehicule;
	}
	
}
