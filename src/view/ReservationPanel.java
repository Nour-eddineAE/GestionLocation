package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import controller.ReservationController;
import controller.tempReservationController;
import interfaces.CreerReservation;
import interfaces.ModifierReservation;
import model.ReservationTableModel;
import model.Reservation.filtre;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ReservationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable reserv_table;
	private JComboBox reserv_filtre;
	private ReservationTableModel reserv_model = new ReservationTableModel();
	private tempReservationController cont = new tempReservationController(this);
	private JLabel reserv_warning_lbl;
	private JTextField reserv_field;
	
	private ReservationPanel self = this;

	public ReservationPanel() {
		
		reserv_warning_lbl = new JLabel("");
		reserv_warning_lbl.setBounds(512, 57, 185, 88);
		reserv_warning_lbl.setForeground(Color.RED);
		
		JScrollPane reserv_scroll = new JScrollPane();
		reserv_scroll.setBounds(23, 57, 483, 462);
		
		reserv_table = new JTable(reserv_model);
		
		reserv_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reserv_scroll.setViewportView(reserv_table);
		//ReservationController.fetchAll(reserv_table, filtre.Tous);
		
		reserv_filtre = new JComboBox();
		reserv_filtre.setBounds(512, 432, 185, 21);
		reserv_filtre.setModel(new DefaultComboBoxModel(filtre.values()));
		reserv_filtre.setMaximumRowCount(4);
		
		JButton reserv_actualiser_btn = new JButton("Actualiser");
		reserv_actualiser_btn.setBounds(512, 463, 185, 56);
		reserv_actualiser_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cont.ActualiserTableau();
				reserv_warning_lbl.setText("");
			}
		});
		
		JLabel filtre_lbl = new JLabel("Filtre :");
		filtre_lbl.setBounds(512, 401, 185, 21);
		
		JButton newReserv_btn = new JButton("Nouveau reservation");
		newReserv_btn.setBounds(512, 155, 185, 56);
		newReserv_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Open reservation creation window
				CreerReservation newReserv = new CreerReservation(self);
				
				//Reset warning label on succesful operation
				reserv_warning_lbl.setText("");
			}
		});
		
		JButton delReserv_btn = new JButton("Supprimer reservation");
		delReserv_btn.setBounds(512, 225, 185, 56);
		delReserv_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cont.SupprimerReservation();
			}
		});
		
		JButton modReserv_btn = new JButton("Modifier reservation");
		modReserv_btn.setBounds(512, 291, 185, 56);
		modReserv_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = reserv_table.getSelectedRow();
				if(index < 0) {
					// if user hasnt selected any row :
					reserv_warning_lbl.setText("<html>Veuillez Selectionner une reservation à modifier.</html>");
					// ^ html tag is for automatic text wrapping
					return;
				}
				String codeReserv = reserv_table.getValueAt(index, 0).toString();
				String dateDep = reserv_table.getValueAt(index, 4).toString();
				String dateRet = reserv_table.getValueAt(index, 5).toString();
				boolean isValid = (boolean)reserv_table.getValueAt(index, 6);
				boolean isCanceled = (boolean)reserv_table.getValueAt(index, 7);
				
				//Open reservation modification window
				ModifierReservation newReserv = new ModifierReservation(reserv_table, codeReserv, dateDep, dateRet, isValid, isCanceled);
				
				//Reset warning label on succesful operation
				reserv_warning_lbl.setText("");
			}
		});
		
		reserv_field = new JTextField();
		reserv_field.setBounds(23, 10, 371, 37);
		reserv_field.setColumns(10);
		
		JButton searchReserv_btn = new JButton("Rechercher");
		searchReserv_btn.setBounds(404, 10, 103, 37);
		searchReserv_btn.addActionListener(new ActionListener() {
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
	
	
	
}
