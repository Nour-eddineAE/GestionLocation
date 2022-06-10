package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import controller.ReservationController;
import interfaces.MainInterface;
import model.Reservation;
import model.Reservation.filtre;
import model.ReservationTableModel;
import model.Vehicule;

public class ReservationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable reserv_table;
	private JComboBox reserv_filtre;
	private ReservationTableModel reserv_model = new ReservationTableModel();
	private ReservationController cont;
	private JLabel reserv_warning_lbl;
	private JTextField reserv_field;

	private CardLayout cl;

	private ReservationPanel self = this;

	public ReservationPanel(MainInterface mInterface) {

		cl = (CardLayout) mInterface.getMainPanel().getLayout();

		this.setLayout(null);

		reserv_warning_lbl = new JLabel("");
		reserv_warning_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		reserv_warning_lbl.setBounds(512, 57, 185, 88);
		reserv_warning_lbl.setForeground(Color.RED);

		JScrollPane reserv_scroll = new JScrollPane();
		reserv_scroll.setBounds(23, 57, 483, 462);

		reserv_table = new JTable(reserv_model);

		reserv_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reserv_table.setSelectionBackground(viewSettings.SECONDARY);
		reserv_scroll.setViewportView(reserv_table);

		reserv_filtre = new JComboBox();
		reserv_filtre.setBackground(viewSettings.SECONDARY);
		
		reserv_filtre.setBounds(522, 432, 193, 21);
		reserv_filtre.setModel(new DefaultComboBoxModel(filtre.values()));
		reserv_filtre.setMaximumRowCount(4);

		JButton reserv_actualiser_btn = new JButton("Actualiser");
		reserv_actualiser_btn.setBackground(viewSettings.SECONDARY);
		reserv_actualiser_btn.setBounds(522, 463, 193, 56);
		reserv_actualiser_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cont.ActualiserTableau();
				reserv_warning_lbl.setText("");
			}
		});


		JLabel filtre_lbl = new JLabel("Filtre :");
		filtre_lbl.setBounds(522, 401, 193, 21);

		JButton newReserv_btn = new JButton("Nouvelle reservation");
		newReserv_btn.setBackground(viewSettings.SECONDARY);
		newReserv_btn.setBounds(522, 155, 193, 56);
		newReserv_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Open reservation creation panel
				cl.show(mInterface.getMainPanel(), "newReserv");
				//Reset warning label on succesful operation
				reserv_warning_lbl.setText("");
			}
		});

		JButton delReserv_btn = new JButton("Supprimer reservation");
		delReserv_btn.setBackground(viewSettings.SECONDARY);
		delReserv_btn.setBounds(522, 225, 193, 56);
		delReserv_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cont.SupprimerReservation();
			}
		});

		JButton modReserv_btn = new JButton("Modifier reservation");
		modReserv_btn.setBackground(viewSettings.SECONDARY);
		modReserv_btn.setBounds(522, 291, 193, 56);
		modReserv_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = reserv_table.getSelectedRow();
				if(index < 0) {
					// if user hasnt selected any row :
					reserv_warning_lbl.setText("<html>Veuillez Selectionner une reservation à modifier.</html>");
					// ^ html tag is for automatic text wrapping
					return;
				}

				Reservation r = new Reservation();

				r.setCodeReservation(Integer.parseInt((String) reserv_table.getValueAt(index, 0)));
				r.setVehicule(new Vehicule());
				r.getVehicule().setCodeVehicule((String) reserv_table.getValueAt(index, 3));
				r.setDateDepart(Date.valueOf((String) reserv_table.getValueAt(index, 4)));
				r.setDateRetour(Date.valueOf((String) reserv_table.getValueAt(index, 5)));
				r.setValid(Boolean.parseBoolean((String) reserv_table.getValueAt(index, 6)));
				r.setCanceled(Boolean.parseBoolean((String) reserv_table.getValueAt(index, 7)));

				//Open reservation modification window
				//ModifierReservation newReserv = new ModifierReservation(self, r);
				ModifierReserPanel modR = new ModifierReserPanel(mInterface, r, cont);
				cont.setReservModPanel(modR);
				mInterface.getMainPanel().add(modR, "modReserv");
				cl.show(mInterface.getMainPanel(), "modReserv");

				//Reset warning label on succesful operation
				reserv_warning_lbl.setText("");
			}
		});

		reserv_field = new JTextField();
		reserv_field.setBounds(23, 10, 371, 37);
		reserv_field.setColumns(10);

		JButton searchReserv_btn = new JButton("Rechercher");
		searchReserv_btn.setBackground(viewSettings.MAIN);
		searchReserv_btn.setForeground(viewSettings.WHITE);
		searchReserv_btn.setBounds(404, 10, 103, 37);
		searchReserv_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cont.RechercherReservation();
			}
		});
		setLayout(null);
		add(reserv_warning_lbl);
		add(reserv_scroll);
		add(reserv_filtre);
		add(reserv_actualiser_btn);
		add(filtre_lbl);
		add(newReserv_btn);
		add(delReserv_btn);
		add(modReserv_btn);
		add(reserv_field);
		add(searchReserv_btn);
	}


	//Getters
	public JTable getReserv_table() {
		return reserv_table;
	}

	public JComboBox getReserv_filtre() {
		return reserv_filtre;
	}

	public ReservationTableModel getReserv_model() {
		return reserv_model;
	}

	public JLabel getReserv_warning_lbl() {
		return reserv_warning_lbl;
	}

	public JTextField getReserv_field() {
		return reserv_field;
	}

	//Setter
	public void setReservController(ReservationController reservCont) {
		this.cont = reservCont;
	}

}
