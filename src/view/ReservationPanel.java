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

public class ReservationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable reserv_table;
	private JComboBox reserv_filtre;
	private ReservationTableModel reserv_model = new ReservationTableModel();
	private tempReservationController cont = new tempReservationController(this);


	public ReservationPanel() {
		this.setLayout(null);
		
		JLabel reserv_warning_lbl = new JLabel("");
		reserv_warning_lbl.setForeground(Color.RED);
		reserv_warning_lbl.setBounds(517, 57, 185, 88);
		this.add(reserv_warning_lbl);
		
		JScrollPane reserv_scroll = new JScrollPane();
		reserv_scroll.setBounds(23, 57, 484, 462);
		this.add(reserv_scroll);
		
		reserv_table = new JTable(reserv_model);
		
		reserv_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reserv_scroll.setViewportView(reserv_table);
		//ReservationController.fetchAll(reserv_table, filtre.Tous);
		
		reserv_filtre = new JComboBox();
		reserv_filtre.setModel(new DefaultComboBoxModel(filtre.values()));
		reserv_filtre.setMaximumRowCount(4);
		reserv_filtre.setBounds(517, 432, 185, 21);
		this.add(reserv_filtre);
		
		JButton reserv_actualiser_btn = new JButton("Actualiser");
		reserv_actualiser_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cont.ActualiserTableau();
				
				reserv_warning_lbl.setText("");
			}
		});
		reserv_actualiser_btn.setBounds(517, 463, 185, 56);
		this.add(reserv_actualiser_btn);
		
		JLabel filtre_lbl = new JLabel("Filtre :");
		filtre_lbl.setBounds(517, 401, 185, 21);
		this.add(filtre_lbl);
		
		JButton newReserv_btn = new JButton("Nouveau reservation");
		newReserv_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Open reservation creation window
				CreerReservation newReserv = new CreerReservation(reserv_table);
				
				//Reset warning label on succesful operation
				reserv_warning_lbl.setText("");
			}
		});
		newReserv_btn.setBounds(517, 155, 185, 56);
		this.add(newReserv_btn);
		
		JButton delReserv_btn = new JButton("Supprimer reservation");
		delReserv_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int index = reserv_table.getSelectedRow();
				if(index < 0) {
					// if user hasnt selected any row :
					reserv_warning_lbl.setText("<html>Veuillez Selectionner une reservation à supprimer.</html>");
					// ^ html tag is for automatic text wrapping
					return;
				} //else
				
				
				// Verification prompt, ask user if he really wants to delete element
				int result = JOptionPane.showConfirmDialog(null, "Etes vous sûr?","Verification", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				//if user wants to delete element
				if(result == JOptionPane.YES_OPTION) {
					String codeReserv = (String) reserv_table.getValueAt(index, 0);
					ReservationController.deleteReservation(codeReserv);
					//ReservationController.fetchAll(reserv_table, (filtre)reserv_filtre.getSelectedItem());	
				}
				//Reset warning label on succesful operation
				reserv_warning_lbl.setText("");
			}
		});
		delReserv_btn.setBounds(517, 225, 185, 56);
		this.add(delReserv_btn);
		
		JButton modReserv_btn = new JButton("Modifier reservation");
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
		modReserv_btn.setBounds(517, 291, 185, 56);
		this.add(modReserv_btn);
		
		JTextField reserv_field = new JTextField();
		reserv_field.setBounds(23, 10, 371, 37);
		this.add(reserv_field);
		reserv_field.setColumns(10);
		
		JButton searchReserv_btn = new JButton("Rechercher");
		searchReserv_btn.setBounds(404, 10, 103, 37);
		searchReserv_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codeReserv = reserv_field.getText();
				if(codeReserv.isEmpty()) {
					reserv_warning_lbl.setText("<html>Veuillez Saisir un code à rechercher.</html>");
					return;
				}
				//search for reservation
				ReservationController.findReservation(codeReserv, reserv_table);
				
				//Reset warning label on succesful operation
				reserv_warning_lbl.setText("");
			}
		});
		this.add(searchReserv_btn);
	}
	
	public JTable getReserv_table() {
		return reserv_table;
	}

	public JComboBox getReserv_filtre() {
		return reserv_filtre;
	}

	public ReservationTableModel getReserv_model() {
		return reserv_model;
	}
	
	
	
}
